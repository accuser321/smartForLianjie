<!--
 * @Author: 刘格优
 * @Date: 2019-11-05 14:46:16
 * @LastEditors  : 刘格优
 * @LastEditTime : 2019-12-26 00:38:06
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
      <empcard :userinfo="userinfo"></empcard>
      <abtreport :sno="libnoinfo.sno"
                 :stitle="libnoinfo.stitle"></abtreport>
    </div>
    <peiwen :libdesc="libnoinfo.sdesc"></peiwen>
    <div v-if="libnoinfo.readonly == '0'">
      <div class="tips xgwz"
           @click="edit">
        修改文章
      </div>
    </div>
    <md-popup v-model="bqchoose"
              position="bottom"
              :mask-closable="false">
      <md-popup-title-bar title="请选择文章所属标签类型："
                          describe="为精准获取客户需求，您可选择1-2个标签"
                          ok-text="确定"
                          large-radius
                          @confirm="tochoose()"></md-popup-title-bar>
      <div class="md-example-popup md-example-popup-bottom">
        <div class="list">
          <ul class="clearfix">
            <li class="label_list"
                v-for="(item, index) in labellist"
                :key="index"
                :data-ins="item.tagcode"
                @click="choosebq($event)">
              {{ item.tagname }}
            </li>
          </ul>
        </div>
      </div>
    </md-popup>
  </div>
</template>

<script>
import {
  selectOneBySno,
  selectKJFL
} from '@/api/abt/customerOperation/common/index'
import { updateBq } from '@/api/abt/customerOperation/hkbw/index'

// import { selectUserCard } from '@/api/abt/customerOperation/visitingcard/index'
import { DoRecord } from '@/api/abt/customerOperation/common'
import empcard from '@/views/abt/customerOperation/common/empcard/empcard'
import peiwen from '@/views/abt/customerOperation/common/peiwen/peiwen'
import { wechatshare } from '@/lib/wechat_share'
import { getStorage } from '@/lib/util'
import initWebSocket from '@/mixins/websock'
import config from '@/config'
import { Toast } from 'mand-mobile'
// 举报/建议
import abtreport from '@/views/abt/customerOperation/common/report/report'
export default {
  mixins: [initWebSocket],
  components: {
    empcard,
    peiwen,
    abtreport
  },
  data () {
    return {
      websock: null,
      sno: '',
      libnoinfo: {
        fbtime: ''
      },
      userinfo: {},
      empno: '',
      suserid: '',
      ossurl: '',
      user: {},
      bqchoose: false,
      labellist: []
    }
  },
  created () {
    this.ossurl = getStorage('ossurl', '')
    this.user = getStorage('u_s', {})
    this.sno = this.$route.query.sno
    this.empno = this.$route.query.empno
    this.suserid = this.$route.query.suserid
    this.getdetail()
    this.getuserinfo()
    this.getbq()
  },
  destroyed: function () {
    this.websocketclose() // 页面销毁时关闭websocket连接
  },
  methods: {
    getdetail () {
      selectOneBySno({ btagcode: '1', sno: this.sno, flag: '1' }).then(res => {
        this.libnoinfo = res.data.data
        if (this.libnoinfo.bq.length == 0) {
          this.bqchoose = true
        }
        this.behaviorRecord()
        // eslint-disable-next-line no-undef
        $('.content').load(this.ossurl + this.libnoinfo.conthttp)
        let shareurl = `${config.redirect_uri}/Empshare?sno=${this.libnoinfo.sno}&empno=${this.empno}&suserid=${this.suserid}&orytype=${this.user.rytype}&type=10`
        let fxstr = {
          suserid: '',
          empno: this.empno,
          otype: 2,
          btagcode: 10,
          stagcode: this.libnoinfo.stagcode,
          sno: this.libnoinfo.sno,
          osno: this.libnoinfo.osno,
          rytype: this.user.rytype
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
        // this.$nextTick(() => {
        //   document.title = this.libnoinfo.stitle
        //   console.log(document.title)
        // })
        setTimeout(() => {
          document.title = this.libnoinfo.stitle
          console.log(document.title)
        }, 1000)
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
        rytype: this.user.rytype
        // labidlist: this.libnoinfo.bq
      }
      DoRecord(data).then(res => {
        let id = res.data.data.id
        let comid = this.user.comid
        this.initWebSocket(id, comid) // 开启websocket连接
      })
    },
    getuserinfo () {
      selectUserCard({
        empno: this.empno,
        flag: '0',
        type: this.user.rytype
      }).then(res => {
        this.userinfo = res.data.data
      })
    },
    edit () {
      this.$router.push({
        path: `/editarticle?sno=${this.sno}&empno=${this.empno}&suserid=${this.user.userid}`
      })
    },
    getbq () {
      selectKJFL({ tagcode: 'L002' }).then(res => {
        this.labellist = res.data.data
      })
    },
    choosebq () {
      let len = document.getElementsByClassName('choose_active').length
      let e = event.currentTarget
      if (e.getAttribute('tab')) {
        e.classList.remove('choose_active')
        e.removeAttribute('tab')
      } else {
        if (len < 2) {
          e.classList.add('choose_active')
          e.setAttribute('tab', 'true')
        } else {
          Toast.info('最多选择两个标签 !')
        }
      }
    },
    tochoose () {
      let ele = document.getElementsByClassName('choose_active')
      let len = ele.length
      if (len === 0) {
        Toast.info('请选择标签')
      } else {
        Toast.loading('加载中...')
        let mylabid = []
        for (let i = 0; i < len; i++) {
          mylabid.push(ele[i].getAttribute('data-ins'))
        }
        let data = { bq: mylabid, sno: this.sno }
        updateBq(data).then(res => {
          Toast.hide()
          this.bqchoose = false
          Toast.info(res.data.msg)
        })
      }
    }
  }
}
</script>
<style scoped lang="stylus">
@import './article.styl';
</style>
