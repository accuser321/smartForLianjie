<template>
  <div class="container">
    <div class="article">
      <div class="articleimg"
           v-if="lib.pichttp || lib.iconhttp">
        <div class="img"
             v-if="recordtype == '10' || recordtype == '4'"
             :style="{ 'background-image': 'url(' + ossurl + lib.pichttp + ')' }"></div>
        <div class="img"
             v-if="recordtype !== '10' && recordtype !== '4' && recordtype !== '7'"
             :style="{ 'background-image': 'url(' + lib.pichttp + ')' }"></div>
        <div class="img"
             v-if="recordtype == '7'"
             :style="{ 'background-image': 'url(' + ossurl + lib.iconhttp + ')' }"></div>

      </div>
      <div class="articleimg"
           v-if="recordtype == '17'">
        <img src="@/assets/nh/nhwx-001.jpg"
             alt="">
      </div>
      <div class="articleimg"
           v-if="recordtype == '8' || recordtype == '16'">
        <div class="img" :style="{ 'background-image': 'url(' + msossurl + iconimg + ')' }"></div>

      </div>
      <div class="policydetail"
           v-if="recordtype == '11' || recordtype == '13' || recordtype == '14'">
        <div class="leftimg">
          <img :src="policycount.empinfo.headimg"
               alt="headpic">
        </div>
        <div class="rightdetail">
          <div class="right_txt"
               v-if="recordtype == '11'">{{ policycount.title + '保单解析报告' }}</div>
          <div class="right_txt"
               v-if="recordtype == '13'">{{ policycount.title + '的家庭解析报告' }}</div>
          <div class="right_txt"
               v-if="recordtype == '14'">{{ policycount.title + '的客户分析报告' }}</div>
          <p class="lookandshare"
             v-if="recordtype == '11'"> 已有
            <span class="red">{{ policycount.readnum }}</span>位好友阅读,<span class="red">{{ policycount.sharenum }}</span>位好友转发</p>
          <p class="lookandshare"
             v-if="recordtype == '13'"> 已有
            <span class="red">{{ policycount.readnum }}</span>位好友阅读,<span class="red">{{ policycount.sharenum }}</span>位好友转发</p>
          <p class="lookandshare"
             v-if="recordtype == '14'"> 已有
            <span class="red">{{ policycount.readnum }}</span>位好友阅读,<span class="red">{{ policycount.sharenum }}</span>位好友转发</p>
          <!-- <div class="gray history_tip"
             v-if="recordtype == '11'">
          已有
          <span class="red">{{ policycount.readnum }}</span>位好友阅读,<span class="red">{{ policycount.sharenum }}</span>位好友转发
        </div>
        <div class="gray history_tip"
             v-if="recordtype == '13'">
          已有
          <span class="red">{{ policycount.readnum }}</span>位好友阅读,<span class="red">{{ policycount.sharenum }}</span>位好友转发
        </div>
        <div class="gray history_tip"
             v-if="recordtype == '14'">
          已有
          <span class="red">{{ policycount.readnum}}</span>位好友阅读,<span class="red">{{ policycount.sharenum }}</span>位好友转发
        </div> -->
        </div>
      </div>
      <div class="artical_tit">
        <div class="history_tit"
             v-if="recordtype != '8' && recordtype != '16' && recordtype != '17'">{{ lib.stitle | libTitle(20) }}</div>
        <div class="history_tit"
             v-if="recordtype == '8' || recordtype == '16'">{{icontitle}}{{recordtype == '8'?'计划书':'产品对比报告'}}</div>
        <div class="history_tit"
             v-if="recordtype == '17'">{{user.empname+'的微店'}}</div>
        <div class="gray history_tip"
             v-if="recordtype == '10'">
          已有
          <span class="red">{{ lib.browsenum }}</span>位好友阅读,<span class="red">{{ lib.forwardnum }}</span>位好友转发
        </div>
        <div class="gray history_tip"
             v-if="recordtype == '9'">
          已有
          <span class="red">{{ lib.mpbrowsenum }}</span>位好友阅读,<span class="red">{{ lib.mpforwardnum }}</span>位好友转发
        </div>
        <div class="gray history_tip"
             v-if="recordtype == '12'">
          已有
          <span class="red">{{ lib.gwbrowsenum }}</span>位好友阅读,<span class="red">{{ lib.gwforwardnum }}</span>位好友转发
        </div>
        <div class="gray history_tip"
             v-if="recordtype == '15'">
          已有
          <span class="red">{{ lib.gwbrowsenum }}</span>位好友阅读,<span class="red">{{ lib.gwforwardnum }}</span>位好友转发
        </div>
        <div class="gray history_tip"
             v-if="recordtype == '7'">
          已有
          <span class="red">{{ lib.rdnum }}</span>位好友阅读,<span class="red">{{ lib.zfnum }}</span>位好友转发
        </div>
        <div class="gray history_tip"
             v-if="recordtype == '8'">
          已有
          <span class="red">{{ readnum }}</span>位好友阅读,<span class="red">{{ sharenum }}</span>位好友转发
        </div>
        <div class="gray history_tip"
             v-if="recordtype == '16'">
          已有
          <span class="red">{{ readnum }}</span>位好友阅读,<span class="red">{{ sharenum }}</span>位好友转发
        </div>
        <div class="gray history_tip"
             v-if="recordtype == '17'">
          已有
          <span class="red">{{policycount.readnum}}</span>位好友阅读,<span class="red">{{policycount.sharenum}}</span>位好友转发
        </div>
      </div>
    </div>
    <div class="btn">
      <ul class="tab">
        <li :class="{ active: actotype == '1' }"
            @click="luck('1')">
          谁看了我
        </li>
        <li :class="{ active: actotype == '2' }"
            @click="luck('2')">
          谁转发了
        </li>
      </ul>
    </div>
    <div v-show="!isNull">
      <md-scroll-view class="scroll"
                      ref="scrollView"
                      auto-reflow
                      :scrolling-x="false"
                      @end-reached="loadMorechange">
        <ul>
          <li class="list"
              v-for="(item, index) in newsList"
              :key="index">
            <div class="khlist">
              <div class="time">
                <span class="gray">{{ item.begtime.substr(0, 16) }}</span>
                <!-- v-if="recordtype=='10'" -->
                <!-- <div class="timekg"
                   v-else></div> -->
              </div>
              <div class="headimg">
                <img :src="item.headimg"
                     alt />
              </div>
              <div class="right">
                <div class="right_name">{{ item.khname }}</div>
                <div class="right_szd">
                  <p v-if="recordtype !== '9' && recordtype !== '6'">
                    所在地：{{
                      !item.custprovince
                        ? "未提供所在地"
                        : item.custprovince + " " + item.custcity
                    }}
                  </p>
                  <p v-else>
                    {{ actotype == "1" ? "查看" : "转发" }}了您的名片
                  </p>
                </div>
                <div class="right_ly">
                  <p v-if="item.flag == '0'">来源：朋友</p>
                  <p v-if="item.flag == '1'">来源：准客户</p>
                  <p v-if="item.flag == '2'">来源：好友</p>
                </div>
              </div>
              <div class="rightbtn">
                <div>
                  <div :class="{
                      disfindTa: item.flag == '0',
                      findTa: item.flag == '1' || item.flag == '2'
                    }"
                       @click="analysisInfo(item.khuserid)">
                    去找Ta
                  </div>
                  <div v-if="actotype == '1' && item.acttimes >= '0'"
                       class="acttimes red">
                    {{ item.acttimes | btime }}
                  </div>
                  <div v-if="actotype == '1' && item.acttimes == '-1'"
                       class="acttimes red">
                    正在阅读
                  </div>
                </div>
              </div>
            </div>
          </li>
        </ul>
        <md-scroll-view-more slot="more"
                             :is-finished="listFinished" />
      </md-scroll-view>
    </div>
    <div class="nodata"
         v-show="isNull">
      <img class="kongimg"
           src="@/assets/image/null.png"
           alt="" />
    </div>
  </div>
</template>
<script>
import { selectOneBySno } from '@/api/abt/customerOperation/common/index'
// import { selectUserCard, getpropictures, comshow } from '@/api/abt/customerOperation/visitingcard/index'
import { getlook } from '@/api/abt/customerOperation/looked/index'
import { getStorage } from '@/lib/util'
import loadMorechange from '@/mixins/loadMorechange'
// import { actionRecord } from '@/api/abd/family'
export default {
  mixins: [loadMorechange],
  data () {
    return {
      msossurl: '',
      actotype: '1',
      recordtype: '',
      sno: '',
      slibno: '',
      lib: { pichttp: '', stitle: '' },
      ossurl: '',
      // 加载更多
      listpageNo: 1,
      listsize: 6,
      listtotal: 0,
      listtotalPage: 0,
      listFinished: false,
      newsList: [],
      height: '',
      user: {},
      empno: '',
      btagcode: '',
      jhsno: '',
      isNull: false,
      gwpic: {},
      iconimg: '', // 计划书和产品对比显示图
      icontitle: '', // 计划书和产品对比显示title
      readnum: 0,
      sharenum: 0,
      policycount: {
        readnum: 0,
        sharenum: 0,
        empinfo: {},
        title: ''
      }
    }
  },
  filters: { //过滤器
    largeNum (value) {
      if (value) {
        if (value > 99) {
          return '99+'
        } else {
          return value
        }
      } else {
        return 0
      }
    },
    getdate (value) {
      if (value) {
        return value.substr(5, 5)
      }
    },
    gettime (value) {
      if (value) {
        return value.substr(11, 5)
      }
    },
    settime (value) {
      if (value) {
        if (value > 59) {
          let min = parseInt(value / 60)
          let second = value - min * 60
          if (second < 10) {
            second = '0' + second
          }
          return min + '分' + second + '秒'
        } else {
          return value + '秒'
        }
      } else {
        return '0秒'
      }
    },
    btime (value) {
      if (value) {
        let temp = parseInt(value / 60)
        if (temp > 0) {
          let min = temp
          let seconds = value % 60
          if (seconds > 0) {
            return min + '分' + seconds + '秒'
          } else {
            return min + '分'
          }
        } else {
          let seconds = value
          return seconds + '秒'
        }
      } else {
        return '0秒'
      }
    },
    libTitle (val, len) {
      if (val) {
        let str = ''
        val.length > len ? (str = val.substr(0, len) + '...') : (str = val)
        return str
      } else {
        return ''
      }
    }
  },
  beforeRouteEnter (to, from, next) {   //组件内部守卫
    next(vm => {
      vm.recordtype = vm.$route.query.recordtype
      switch (vm.recordtype) {
        case '10':
          // 文章
          vm.sno = vm.$route.query.sno
          vm.getArticle('10')
          break
        case '4':
          // 贺卡
          vm.sno = vm.$route.query.sno
          vm.getArticle('4')
          break
        case '6':
          // 保存电话
          vm.empno = vm.$route.query.empno
          vm.getuserinfo('6')
          break
        case '9':
          // 名片
          vm.empno = vm.$route.query.empno
          vm.getuserinfo('9')
          break
        case '12':
          // 官网
          vm.empno = vm.$route.query.empno
          vm.getuserinfo('12')
          break
        case '11':
          // 保单报告
          vm.empno = vm.$route.query.empno
          vm.getpolicyreport('11')
          break
        case '13':
          // 保单报告
          vm.empno = vm.$route.query.empno
          vm.getpolicyreport('13')
          break
        case '14':
          // 保单报告
          vm.empno = vm.$route.query.empno
          vm.getpolicyreport('14')
          break
        case '15':
          // 保单报告
          vm.empno = vm.$route.query.empno
          vm.getuserinfo('15')
          break
        case '16':
          // 产品对比报告
          vm.empno = vm.$route.query.empno
          vm.getproplist()
          break
        case '8':
          // 计划书
          vm.empno = vm.$route.query.empno
          vm.getproplist()
          break
        case '7':
          // 产品
          vm.sno = vm.$route.query.sno
          vm.getclassinfo()
          break
        case '17':
          // 门店
          vm.empno = vm.$route.query.empno
          vm.getpolicyreport('17')
          break
      }
      vm.ossurl = getStorage('ossurl', '')
      vm.msossurl = getStorage('msossurl', '')
      vm.getData()
    })
  },
  created () {
    this.height = document.body.clientHeight
    this.user = getStorage('u_s', {})
    this.gwpic = getStorage('sys_info', {})
    this.policycount.title = this.$route.query.remark
  },
  mounted () {
    document.querySelector('.scroll').style.height = `${this.height - 200}px`
  },
  methods: {
    getproplist () {
      getpropictures({ jhsno: this.$route.query.jhsno, type: this.recordtype }).then(res => {
        this.iconimg = res.data.data.iconhttp
        this.icontitle = res.data.data.prodname
        this.readnum = res.data.data.readnum
        this.sharenum = res.data.data.sharenum
      })
    },
    luck (index) {
      this.listpageNo = 1
      this.newsList = []
      this.listFinished = false
      this.loadflag = false
      this.actotype = index
      this.$refs.scrollView.finishLoadMore()
      // console.log('--------------切换中--------------')
      // console.log('切换时总页数' + this.listtotalPage)
      // console.log('切换时当前页' + this.listpageNo)
      // console.log('切换时加载状态' + this.loadflag)
      // console.log('切换时是否停止加载' + this.listFinished)
      this.getData()
    },
    getclassinfo () {
      comshowaa({ sclasscode: this.sno }).then(res => { //产品详情
        this.lib = res.data.data
        this.$set(this.lib, 'stitle', this.lib.prodname)
        this.iconimg = res.data.data.iconhttp
      })
    },
    getData () {
      let data = {
        page: this.listpageNo,
        size: this.listsize,
        sno: this.sno,
        type: this.actotype,
        recordtype: this.recordtype,
        userid: this.user.userid.toString()
      }
      getlook(data).then(res => {  //查看谁看了我
        // console.log("查看")
        // console.log(res);
        this.newsList =
          this.newsList == []
            ? res.data.data.rows
            : this.newsList.concat(res.data.data.rows)
        this.listtotal = res.data.data.total
        this.listtotalPage = res.data.data.totalpage
        if (this.newsList.length == 0) {
          this.isNull = true
        } else {
          this.isNull = false
        }
        // console.log('--------------请求数据中--------------')
        // console.log('请求数据中总页数' + this.listtotalPage)
        // console.log('请求数据中当前页' + this.listpageNo)
        if (this.listtotalPage <= this.listpageNo) {
          this.listFinished = true
        } else {
          this.listFinished = false
          this.$refs.scrollView.finishLoadMore()
        }
        this.loadflag = true
        // console.log('请求数据中加载状态' + this.loadflag)
        // console.log('请求数据中是否停止加载' + this.listFinished)
      })
    },
    // 文章 贺卡
    getArticle (recordtype) {
      if (recordtype == '4') {
        this.btagcode = '4'
      }
      if (recordtype == '10') {
        this.btagcode = '1'
      }
      selectOneBySno({
        btagcode: this.btagcode,
        sno: this.sno,
        flag: '1'
      }).then(res => {
        this.lib = res.data.data
      })
    },
    // 名片、官网、保存电话
    getuserinfo (recordtype) {
      selectUserCard({
        empno: this.empno,
        flag: '0',
        type: this.user.rytype
      }).then(res => {
        this.lib = res.data.data
        if (recordtype == '12') {
          this.lib.pichttp = this.gwpic.sharelogo
        } else {
          this.lib.pichttp = res.data.data.headimg
        }
        if (recordtype == '6') {
          this.lib.stitle = res.data.data.cardempname + '的名片'
        } else if (recordtype == '12') {
          this.lib.stitle = getStorage('comname', '')
        } else if (recordtype == '9') {
          this.lib.stitle = res.data.data.cardempname + '的名片'
        } else if (recordtype == '11') {
          this.lib.stitle = res.data.data.cardempname + '的保单解析报告'
        } else if (recordtype == '13') {
          this.lib.stitle = res.data.data.cardempname + '的家庭报告'
        } else if (recordtype == '14') {
          this.lib.stitle = res.data.data.cardempname + '的保单报告'
        } else if (recordtype == '15') {
          this.lib.stitle = res.data.data.cardempname + '的NBS报告'
        } else if (recordtype == '8') {
          this.lib.stitle = res.data.data.cardempname + '的计划书'
        } else if (recordtype == '16') {
          this.lib.stitle = res.data.data.cardempname + '的产品对比报告'
        }
      })
    },
    // 去找他
    analysisInfo (val) {
      this.$router.push({
        name: 'clientAnalysis',
        query: { customer: val.toString() ,sno:this.sno}
      })
    },
    getpolicyreport (recordtype) { //行为记录
      actionRecord({
        empno: this.user.empno,
        btagcode: recordtype,
        comid: this.user.comid,
        userid: this.user.userid,
        sno: this.$route.query.reportno }).then(res => {
        this.policycount.readnum = res.data.data.readnum
        this.policycount.sharenum = res.data.data.sharenum
        this.policycount.empinfo = res.data.data.empinfo
      })
    }
  }
}
</script>
<style lang="stylus" scoped>
@import './looked.styl';
</style>
