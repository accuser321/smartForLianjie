<!--
 * @Author: 刘格优
 * @Date: 2019-11-05 14:46:16
 * @LastEditors: 侯依辰
 * @LastEditTime: 2019-12-03 11:29:52
 -->
<template>
  <div class="wzbox">
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
      <div class="libcontent">
        <div class="content"></div>
      </div>
      <div class="num">阅读：{{ libnoinfo.ydnum }}</div>
      <!-- <empcard :userinfo="userinfo"></empcard> -->
      <abtreport :sno="libnoinfo.sno"
                 :stitle="libnoinfo.stitle"></abtreport>
    </div>
    <peiwen :libdesc="libnoinfo.sdesc"></peiwen>
  </div>
</template>

<script>
import initWebSocket from '@/mixins/websock'
import {
  selectOneBySno,
  DoRecord
} from '@/api/abt/customerOperation/common/index'
import { ForwardWZ } from '@/api/abt/customerOperation/hkbw/index'   //文章转化变成我的
// import { selectUserCard } from '@/api/abt/customerOperation/visitingcard/index'
import { wechatshare } from '@/lib/wechat_share'
// import empcard from '@/views/abt/customerOperation/common/empcard/empcard'
import peiwen from '@/views/abt/customerOperation/common/peiwen/peiwen'
import { getStorage } from '@/lib/util'
import config from '@/config'
// 举报/建议
import abtreport from '@/views/abt/customerOperation/common/report/report'

// var isPageHide = false
// console.log(isPageHide)
// window.addEventListener('pageshow', function () {
//   if (isPageHide) {
//     console.log('pageshow')
//     window.location.reload()
//   }
// })
// window.addEventListener('pagehide', function () {
//   console.log('pagehide')
//   console.log(isPageHide)
//   isPageHide = true
//   // window.location.reload()
// })

export default {
  mixins: [initWebSocket],
  components: {
    // empcard,
    peiwen,
    abtreport
  },
  data () {
    return {
      websock: null,
      sno: '',
      suserid: '',
      empno: '',
      libnoinfo: {
        fbtime: ''
      },
      userinfo: {},
      ossurl: '',
      user: {}
    }
  },
  created () {
    this.ossurl = getStorage('ossurl', '')
    this.user = getStorage('u_s', {})
    this.sno = this.$route.query.sno
    this.suserid = this.$route.query.suserid
    this.empno = this.$route.query.empno
    this.getdetail()
    // this.getuserinfo()
  },
  destroyed: function () {
    this.websocketclose() // 页面销毁时关闭websocket连接
  },
  methods: {
    getdetail () {//获取素材详情
      selectOneBySno({ btagcode: '1', sno: this.sno, flag: '0' }).then(res => {
        this.libnoinfo = res.data.data;
        // console.log("数据")
        // console.log(res.data.data);
        window.document.title = this.libnoinfo.stitle
        this.behaviorRecord()  //行为记录
        // eslint-disable-next-line no-undef
        // console.log("数据100")
        $('.content').load(this.ossurl + this.libnoinfo.conthttp)
        let shareurl = `${config.redirect_uri}/Empshare?sno=${this.libnoinfo.sno}&empno=${this.empno}&suserid=${this.user.userid}&orytype=${this.user.rytype}&type=10`
      //  console.log("群")
      //  console.log(shareurl);
        let fxstr = {
          suserid: '',
          empno: this.empno,
          otype: 2,
          btagcode: 1,
          stagcode: this.libnoinfo.stagcode,
          sno: this.libnoinfo.sno,
          osno: this.libnoinfo.osno,
          rytype: this.user.rytype
          // labidlist: this.libnoinfo.bq,
          // isuse: 'y'
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
        btagcode: '1',
        stagcode: this.libnoinfo.stagcode,
        sno: this.sno,
        // sno: this.libnoinfo.sno,
        osno: this.libnoinfo.osno,
        rytype: this.user.rytype
        // labidlist: this.libnoinfo.bq
      }
      console.log("行为记录数据");
      console.log(data);
      DoRecord(data).then(res => {
        //  console.log(res);
        let id = res.data.data.id   //行为记录id
        let comid = this.user.comid   //	渠道编码
        this.initWebSocket(id, comid) // 开启websocket连接
      })
    },
    // getuserinfo () { //查询业务员名片
    //   selectUserCard({
    //     empno: this.empno,
    //     flag: '0',
    //     type: this.user.rytype
    //   }).then(res => {
    //     this.userinfo = res.data.data
    //   })
    // }
  }
}
</script>
<style scoped lang="stylus">
@import './index.styl';
</style>
