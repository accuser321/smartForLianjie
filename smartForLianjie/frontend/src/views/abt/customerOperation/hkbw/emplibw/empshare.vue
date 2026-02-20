<!--
 * @Author: 刘格优
 * @Date: 2019-11-05 14:46:16
 * @LastEditors  : 刘格优
 * @LastEditTime : 2019-12-27 11:36:24
 -->
<template>
  <div class="wzbox">
    <Consult @tozxzx="tozxzx"
             v-if="!isself"
             :empno="empno"
             :user="user">
    </Consult>
    <div class="wzcontent">
      <div class="card">
        <div class="cardtop">
          <p class="stitle">{{ libnoinfo.stitle }}</p>
          <p class="otherinfo">
            <span class="fbtime">{{ libnoinfo.fbtime.substr(0, 10) }}</span>
            <span class="yqr">{{ userinfo.cardempname }}邀您阅读</span>
          </p>
        </div>
      </div>
      <div class="userInfoBox"
           v-if="user.rytype != 'Y'">
        <div class="info">
          <img :src="userinfo.headimg"
               alt="" />
          <div class="infoDetail">
            <span>{{ userinfo.cardempname }}</span>
            <span>{{ userinfo.cardmobile }}</span>
          </div>
        </div>
        <div class="btn"
             v-if="!isself && user.rytype != 'Y'"
             @click="tomy()">
          <span>换成我的</span>
        </div>
      </div>
      <div class="libcontent">
        <div class="content"></div>
      </div>
      <div class="num">阅读：{{ libnoinfo.ydnum }}</div>
      <empcard :userinfo="userinfo"></empcard>
      <abtreport :sno="libnoinfo.sno"
                 :stitle="libnoinfo.stitle"></abtreport>
      <header class="bar bar-nav"
              v-if="!isself && user.rytype != 'Y'"
              @click="tomy">
        <div class="tomybtn">免费换成我的名片文章</div>
      </header>
    </div>
    <peiwen :libdesc="libnoinfo.sdesc"></peiwen>
  </div>
</template>

<script>
import {
  selectOneBySno,
  DoRecord
} from '@/api/abt/customerOperation/common/index'
import { BecomeWZ } from '@/api/abt/customerOperation/hkbw/index'
// import { selectUserCard } from '@/api/abt/customerOperation/visitingcard/index'
import { wechatshare } from '@/lib/wechat_share'
import empcard from '@/views/abt/customerOperation/common/empcard/empcard'
import peiwen from '@/views/abt/customerOperation/common/peiwen/peiwen'
import Consult from '@/views/abt/customerOperation/common/consulation/index'
// import { mapGetters } from 'vuex'
import config from '@/config'
// 举报/建议
import abtreport from '@/views/abt/customerOperation/common/report/report'
import { getStorage } from '@/lib/util'
import initWebSocket from '@/mixins/websock'
export default {
  mixins: [initWebSocket],
  components: {
    empcard,
    peiwen,
    Consult,
    abtreport
  },
  data () {
    return {
      websock: null,
      sno: '',
      suserid: '',
      empno: '',
      isself: false,
      libnoinfo: {
        fbtime: ''
      },
      userinfo: {},
      user: {},
      ossurl: '',
      orytype: ''
    }
  },
  created () {
    this.ossurl = getStorage('ossurl', '')
    this.user = getStorage('u_s', {})
    this.sno = this.$route.query.sno
    this.suserid = this.$route.query.suserid
    this.empno = this.$route.query.empno
    this.orytype = this.$route.query.orytype
    if (this.empno == this.user.empno) {
      this.isself = true
    }
    this.getdetail()
    // this.getuserinfo()
  },
  // computed: {
  //   ...mapGetters(['getossurl', 'getInfo'])
  // },
  destroyed: function () {
    this.websocketclose() // 页面销毁时关闭websocket连接
  },
  methods: {
    getdetail () {
      selectOneBySno({ btagcode: '1', sno: this.sno, flag: '1' }).then(res => {
        this.libnoinfo = res.data.data
        document.title = this.libnoinfo.stitle
        this.behaviorRecord()
        // eslint-disable-next-line no-undef
        $('.content').load(this.ossurl + this.libnoinfo.conthttp)
        let shareurl = `${config.redirect_uri}/Empshare?sno=${this.sno}&empno=${this.empno}&suserid=${this.user.userid}&orytype=${this.orytype}&type=10`
        let fxstr = {
          suserid: this.suserid,
          empno: this.empno,
          otype: 2,
          btagcode: 10,
          stagcode: this.libnoinfo.stagcode,
          sno: this.libnoinfo.sno,
          osno: this.libnoinfo.osno,
          rytype: this.orytype
          // labidlist: this.libnoinfo.bq,
          // isuse: 'n'
        }
        let path = ''
        if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
          // ios
          if (this.$route.query.frompage == 'source') {
            path = config.redirect_uri + '/source'
          } else {
            path = config.redirect_uri
          }
        } else {
          // android
          path = window.location.href
        }
        wechatshare(
          this.libnoinfo.stitle,
          this.libnoinfo.sdesc,
          this.ossurl + this.libnoinfo.pichttp,
          shareurl,
          path,
          fxstr
        )
      })
    },
    // 行为记录
    behaviorRecord () {
      let data = {
        suserid: this.suserid,
        empno: this.empno,
        otype: '1',
        btagcode: '10',
        stagcode: this.libnoinfo.stagcode,
        sno: this.sno,
        osno: this.libnoinfo.osno,
        rytype: this.orytype
        // labidlist: this.libnoinfo.bq
      }
      DoRecord(data).then(res => {
        let id = res.data.data.id
        let comid = this.user.comid
        this.initWebSocket(id, comid) // 开启websocket连接
      })
    },
    // getuserinfo () {
    //   selectUserCard({
    //     empno: this.empno,
    //     flag: '0',
    //     type: this.orytype
    //   }).then(res => {
    //     this.userinfo = res.data.data
    //   })
    // },
    tomy () {
      let data = {
        sno: this.sno,
        osno: this.libnoinfo.osno,
        empno: this.empno
      }
      BecomeWZ(data).then(res => {
        console.log(res.data.data)
        window.location.href = `${config.redirect_uri}/Empshare?sno=${res.data.data}&empno=${this.user.empno}&suserid=${this.user.userid}&orytype=${this.user.rytype}&type=10`
      })
    },
    tozxzx () {
      this.$router.push(
        `/Consult?askuserid=${this.user.userid}&empno=${this.empno}&emprytype=${this.orytype}&libno=${this.sno}&suserid=${this.suserid}&pagetype=10&news=1`
      )
    }
  }
}
</script>
<style scoped lang="stylus">
@import './share.styl';
</style>
