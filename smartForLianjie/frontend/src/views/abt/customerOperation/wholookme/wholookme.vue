<!--
 * @Author: 黄孝娟
 * @Date: 2019-11-14 15:15:34
 * @LastEditors  : 李波
 * @LastEditTime : 2020-01-11 21:47:26
 -->
<template>
  <div class="content">
    <div class="title"
         id="title">
      <ul class="titleUl">
        <li :class="item.blue ? 'blue' : ''"
            v-for="(item, index) in titleList"
            :key="index"
            @click="changeTags(index)">
          {{ item.label }}
        </li>
      </ul>
    </div>
    <div>
      <div v-if="showIndex == '0'">
        <div :style="'height:'+(height-titleHeight)+'px'">
          <md-scroll-view ref="scrollView"
                          auto-reflow
                          v-show="!isNull"
                          :scrolling-x="false"
                          @end-reached="loadMorechange">
            <div>
              <timeMsg :timeList="timeList"
                       :isSeeCard="isSeeCard"></timeMsg>
            </div>
            <md-scroll-view-more slot="more"
                                 :is-finished="listFinished" />
          </md-scroll-view>
          <div class="nodata"
               v-show="isNull">
            <img class="kongimg"
                 src="@/assets/image/null.png"
                 alt="" />
          </div>
        </div>
      </div>
      <div v-if="showIndex == '1'">
        <div>
          <div class="title1"
               id="title1">
            <ul>
              <li :style="{width:kbgshow&&kcpshow?'20%':(kbgshow||kcpshow)?'25%':'33%'}">
                <span :class="isSeeCard == '1' ? 'select' : ''"
                      @click="toSeeCard('9')">看名片</span>
              </li>
              <li :style="{width:kbgshow&&kcpshow?'20%':(kbgshow||kcpshow)?'25%':'33%'}">
                <span :class="isSeeCard == '2' ? 'select' : ''"
                      @click="toSeeCard('10')">看爆文</span>
              </li>
              <li :style="{width:kbgshow&&kcpshow?'20%':(kbgshow||kcpshow)?'25%':'33%'}">
                <span :class="isSeeCard == '3' ? 'select' : ''"
                      @click="toSeeCard('12')">看官网</span>
              </li>
              <li v-if="kcpshow"
                  :style="{width:kbgshow&&kcpshow?'20%':(kbgshow||kcpshow)?'25%':'33%'}">
                <span :class="isSeeCard == '5' ? 'select' : ''"
                      @click="toSeeCard('7,17')">看产品</span>
              </li>
              <li v-if="kbgshow"
                  :style="{width:kbgshow&&kcpshow?'20%':(kbgshow||kcpshow)?'25%':'33%'}">
                <span :class="isSeeCard == '4' ? 'select' : ''"
                      @click="toSeeCard('11,13,14,15')">看报告</span>
              </li>
              <!--  SCHJ XWNR 10 业务员文章
                            SCHJ XWNR 11 保单解析报告
                            SCHJ XWNR 12 官网
                            SCHJ XWNR 13 家庭报告
                            SCHJ XWNR 14 客户报告
                            SCHJ XWNR 15 NBS报告  -->
            </ul>
          </div>
          <div :style="'height:'+(height-titleHeight-title1Height)+'px'">
            <md-scroll-view ref="scrollView"
                            auto-reflow
                            v-show="!isNull"
                            :scrolling-x="false"
                            @end-reached="loadMorechange">
              <div>
                <timeMsg :timeList="timeList"
                         :isSeeCard="isSeeCard"></timeMsg>
              </div>
              <md-scroll-view-more slot="more"
                                   :is-finished="listFinished" />
            </md-scroll-view>
            <div class="nodata"
                 v-show="isNull">
              <img class="kongimg"
                   src="@/assets/image/null.png"
                   alt="" />
            </div>
          </div>
        </div>
      </div>
      <div v-if="showIndex == '2'">
        <md-scroll-view ref="scrollView"
                        auto-reflow
                        v-show="!isNull"
                        :scrolling-x="false"
                        @end-reached="loadMorechange">
          <div>
          </div>
          <md-scroll-view-more slot="more"
                               :is-finished="listFinished" />
        </md-scroll-view>
        <analysis :blueBorder="blueBorder"
                  @getReadnum="getReadnum"
                  @getNewkhnum="getNewkhnum"
                  :mestop="mestop"
                  :chartData="chartData"
                  :chartDatahd="chartDatahd"
                  :chartDatakh="chartDatakh"
                  :chartDatayd="chartDatayd"
                  :chartDatald="chartDatald"></analysis>
      </div>
    </div>
  </div>
</template>
<script>
import {
  selectTimeKHByEmpno,
  selectKHByOtype,
  selectanalysis,
  getkhlist,
  getreadlist,
  getYSLD
} from '@/api/abt/customerOperation/wholookme'
import timeMsg from './component/timemsg/timeMsg' //列表组件
import analysis from './component/analysis/index' //分析组件
import { getStorage } from '@/lib/util'
export default {
  components: {
    timeMsg,
    analysis
  },
  data () {
    return {
      height: '',
      swiperOption: {
        centeredSlides: true,
        slidesPerView: 1,
        autoplay: false,
        on: {}
      },
      titleList: [
        {
          label: '时间',
          blue: true
        },
        {
          label: '行为',
          blue: false
        },
        {
          label: '分析',
          blue: false
        }
      ],
      isSeeCard: '1',
      blueBorder: '7',
      timeList: {},
      mestop: {},
      // 客户15日活跃度
      chartData: {
        columns: ['time', 'num'],
        rows: []
      },
      // 客户与我的互动
      chartDatahd: {
        columns: ['type', 'num'],
        rows: []
      },
      // 新增客户数
      chartDatakh: {
        columns: ['time', 'num'],
        rows: []
      },
      // 阅读数
      chartDatayd: {
        columns: ['time', 'num'],
        rows: []
      },
      // 智能营销漏斗
      chartDatald: {
        columns: ['type', 'num'],
        rows: []
      },
      listpageNo: 1,
      listsize: 4,
      listtotal: 0,
      listtotalPage: 0,
      loadflag: false,
      listFinished: false,
      isNull: true,
      autoheight: false,
      showIndex: 0,
      kbgshow: false,
      kcpshow: false,
      sever: [],
      titleHeight: '',
      title1Height: ''
    }
  },
  created () {
    this.getTime()
    this.height = document.body.clientHeight
    this.sever = getStorage('service', [])
    // console.log(this.sever);
    this.sever.forEach((item, index) => {
      if ((item.servcode == 'ABD' && item.servstatus == '0') || (item.servcode == 'JHS' && item.servstatus == '0')) {
        this.kbgshow = true
      }
      if (item.servcode == 'HXSERVER' && item.servstatus == '0') {
        this.kcpshow = true
      }
    })
    console.log(this.kbgshow)
  },
  updated () {
    var o = document.getElementById("title")
    this.titleHeight = o.clientHeight || o.offsetHeight
    if (this.showIndex == '1') {
      var o1 = document.getElementById("title1")
      this.title1Height = o1.clientHeight || o1.offsetHeight || 0
    }
  },
  methods: {
    changeTags (index) { //切换标签
      this.showIndex = index
      for (var i = 0; i < this.titleList.length; i++) {
        if (i == index) {
          this.titleList[i].blue = true
          if (this.titleList[i].label === '时间') {
            this.listpageNo = 1
            this.timeList = {}
            this.listFinished = false
            this.loadflag = false
            this.$refs.scrollView.finishLoadMore()
            this.getTime()
          } else if (this.titleList[i].label === '行为') {
            this.toSeeCard('9')
          } else if (this.titleList[i].label === '分析') {
            this.getalllist()
          }
        } else {
          this.titleList[i].blue = false
        }
      }
    },
    loadMorechange () { //页面滚动加载
      if (this.loadflag) {
        if (this.listFinished) {
          return false
        } else {
          if (this.listtotalPage < this.listpageNo) {
            this.listFinished = true
          } else {
            this.listFinished = false
            this.loadflag = false
            this.listpageNo = ++this.listpageNo
            this.$refs.scrollView.finishLoadMore()
            if (this.showIndex == '0') {
              this.getTime()
            } else if (this.showIndex == 1) {
              if (this.isSeeCard == '1') {
                this.seeCard('9') // 看名片
              } else if (this.isSeeCard == '2') {
                this.seeCard('10') // 看爆文
              } else if (this.isSeeCard == '3') {
                this.seeCard('12') // 看官网
              } else if (this.isSeeCard = '5') {
                this.seeCard('7,17')
              } else {
                this.seeCard('11,13,14,15') // 看报告
              }
            }
          }
        }
      }
    },
    getTime () {
      let data = {
        size: this.listsize,
        page: this.listpageNo,
        userid: '',//客户id
      }
      selectTimeKHByEmpno(data).then(res => {  //时间访客列表
        let Data = res.data.data.rows
        if (this.timeList == {}) {
          this.timeList = Data
        } else {
          for (var key in Data) {
            if (this.timeList[key]) {
              this.timeList[key] = this.timeList[key].concat(Data[key])
            } else {
              this.$set(this.timeList, key, Data[key])
            }
          }
        }
        this.listtotal = res.data.data.total
        this.listtotalPage = res.data.data.totalpage
        if (this.listtotalPage <= this.listpageNo) {
          this.listFinished = true
        } else {
          this.listFinished = false
          this.$refs.scrollView.finishLoadMore()
        }
        this.loadflag = true
        if (Object.keys(this.timeList).length == 0) {
          this.isNull = true
        } else {
          this.isNull = false
        }
      })
    },
    toSeeCard (val) {  //行为记录
      this.listpageNo = 1
      this.timeList = {}
      this.listFinished = false
      this.loadflag = false
      this.$refs.scrollView.finishLoadMore()
      if (val === '9') {
        this.isSeeCard = '1'
      } else if (val === '10') {
        this.isSeeCard = '2'
      } else if (val === '12') {
        this.isSeeCard = '3' // 看官网
      } else if (val === '7,17') {
        this.isSeeCard = '5'
      } else {
        this.isSeeCard = '4' // 看报告
      }
      this.seeCard(val)
    },
    seeCard (val) {  //查看行为列表
      let data = {
        size: this.listsize,
        page: this.listpageNo,
        btagcode: val,  //行为来源 10业务员文章 9名片 12官网,11,13,14,15报告
      }
      selectKHByOtype(data).then(res => {
        let Data = res.data.data.rows
        if (this.timeList == {}) {
          this.timeList = Data
        } else {
          for (var key in Data) {
            if (this.timeList[key]) {
              this.timeList[key] = this.timeList[key].concat(Data[key])
            } else {
              this.$set(this.timeList, key, Data[key])
            }
          }
        }
        this.listtotal = res.data.data.total
        this.listtotalPage = res.data.data.totalpage
        if (this.listtotalPage <= this.listpageNo) {
          this.listFinished = true
        } else {
          this.listFinished = false
          this.$refs.scrollView.finishLoadMore()
        }
        this.loadflag = true
        if (Object.keys(this.timeList).length == 0) {
          this.isNull = true
        } else {
          this.isNull = false
        }
      })
    },

    // 分析总数
    getalllist () {
      this.getanalysis()
      this.getNewkhnum(7)
      this.getReadnum(7)
      this.getYSLDnum()
    },

    // 客户活跃度，客户与我的互动
    getanalysis () {
      selectanalysis().then(res => { //分析列表
        this.mestop = res.data.data['numList'] //列表
        this.chartData.rows = res.data.data['activeList'] //客户活跃度
        this.chartDatahd.rows = res.data.data['khInteractive'] //客户与我互动
      })
    },

    // 分析  新增客户数
    getNewkhnum (time) {
      getkhlist({ KHday: time }).then(res => {
        this.chartDatakh.rows = res.data.data.hkDaynum
      })
    },

    // 分析  阅读数
    getReadnum (time) {
      getreadlist({ Readday: time }).then(res => {
        this.chartDatayd.rows = res.data.data.rdDaynum
      })
    },

    getYSLDnum () {
      getYSLD().then(res => {
        // 智能营销漏斗
        this.chartDatald.rows = res.data.data
        this.chartDatald.rows.forEach((item, index) => {
          this.$set(item, 'numpeople', `${item.num}人`)
        })
      })
    }
  }
}
</script>
<style lang="stylus" scoped>
@import './index.styl';
</style>
