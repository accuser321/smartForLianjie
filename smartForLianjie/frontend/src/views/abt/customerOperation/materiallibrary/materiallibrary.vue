<template>
  <div class="content">
    <transition name="search">
      <div class="searchbox"
           v-if="searchFlag">
        <!--  -->
        <div class="search1 setmid">
          <img :src="getStorage('ossurl','')+getStorage('imgs',{}).search" />
          <input type="text"
                 ref="search"
                 v-model="stitle"
                 placeholder="搜你想要的">
        </div>
        <!-- v-model="filters.title" -->
        <div class="search2"
             @click="select">搜索</div>
        <!-- @click='handleSearch' -->
        <div class="setmid"
             style="width: 20px;float: right;"
             @click="cancelsearch">
          <img src="@/assets/abt/img/close.png"
               style="width:17px;height: auto;vertical-align: middle;">
        </div>
        <div style='clear:both;'></div>
      </div>
    </transition>
    <div class="chooseBox">
      <div class="classificationBox">
        <div class="category choose"
             @click="handleCategory">{{categoryTitle}}
          <md-icon class="littileicon"
                   name="arrow-down"
                   color="black"
                   size="lg"></md-icon>
        </div>
        <div class="category choose"
             @click="handleType">{{typeTitle}}
          <md-icon class="littileicon"
                   name="arrow-down"
                   color="black"
                   size="lg"></md-icon>
        </div>
      </div>
      <div style="text-align:center;"
           @click="searchFlag=true">
        <img :src="getStorage('ossurl','')+getStorage('imgs',{}).search" style="width:24px;height: 24px;" alt="">
      </div>
    </div>
    <div class="list">
      <md-scroll-view class="scroll"
                      ref="scrollView"
                      v-show="!isNull"
                      auto-reflow
                      :scrolling-x="false"
                      @end-reached="loadMorechange">
        <div class="scroll">
          <div class="poster"
               v-for="(item,index) in imgLists"
               :key="index"
               @click="toDetail(item)">
            <img class="img"
                 :src="ossurl+item.bpichttp"
                 v-if="item.btagcode == '1'" />
            <img class="img"
                 :src="ossurl+item.pichttp"
                 v-else />
            <div class="tally"
                 v-if="item.btagcode == '1'">文章</div>
            <div class="tally"
                 v-if="item.btagcode == '2'">海报</div>
            <div class="tally"
                 v-if="item.btagcode == '4'">贺卡</div>
            <div v-if="item.btagcode == '1'"
                 class="articaltitle">{{item.stitle}}</div>
          </div>
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
    <md-popup v-model="isPopupShow"
              class="inner-popup"
              position="bottom">
      <md-popup-title-bar title="类别"
                          ok-text="确定"
                          :mask-closable="false"
                          @confirm="hidePopUp()"></md-popup-title-bar>
      <div class="md-example-popup md-example-popup-bottom">
        <div class="selectBody">
          <div v-for="(item, index) of categoryData"
               :key="index"
               @click="chooseCategory(item,index)"
               class="typelist">
            <span>{{item.label}}</span>
            <img v-if="item.isActive"
                 src="@/assets/abt/img/choose.png">
          </div>
        </div>
      </div>
    </md-popup>
    <md-popup v-model="isPopupShowType"
              class="inner-popup"
              position="bottom">
      <md-popup-title-bar title="类型"
                          ok-text="确定"
                          :mask-closable="false"
                          @confirm="hidePopUpType()"></md-popup-title-bar>
      <div class="md-example-popup md-example-popup-bottom">
        <div class="selectBody">
          <div v-for="(item, index) of typeData"
               :key="index"
               @click="chooseType(item,index)"
               class="typelist">
            <span>{{item.tagname}}</span>
            <img v-if="item.isActive"
                 src="@/assets/abt/img/choose.png">
          </div>
        </div>
      </div>
    </md-popup>
    <md-landscape v-model="showPic">
      <img :src="sctp">
    </md-landscape>
  </div>
</template>
<script>
import { selectKJFL, selectPage, useHB } from '@/api/abt/customerOperation/common'
import loadMorechange from '@/mixins/loadMorechange'
import { getStorage } from '@/lib/util'
import { Toast } from 'mand-mobile'
export default {
  mixins: [loadMorechange],
  data () {
    return {
      ossurl: '',
      user: '',
      searchFlag: false,
      height: '',
      imgLists: [],
      categoryData: [
        { value: '0', label: '全部', isActive: true },
        { value: '1', label: '文章', isActive: false },
        { value: '2', label: '海报', isActive: false },
        { value: '4', label: '贺卡', isActive: false }
        // {value: '06', label: '天气海报'},
      ],
      typeData: [
        {
          tagcode: '',
          tagname: '全部'
        }
      ],
      listpageNo: 1,
      listsize: 9,
      listtotal: 0,
      listtotalPage: 0,
      loadflag: false,
      listFinished: false,
      isNull: true,
      categoryTitle: '全部',
      typeTitle: '全部',
      btagcode: '0',
      stagcode: '',
      isPopupShow: false,
      isPopupShowType: false,
      showPic: false,
      sctp: '',
      stitle: ''
    }
  },
  created () {
    this.ossurl = getStorage('ossurl', '')
    this.user = getStorage('u_s', {})
    this.height = document.body.clientHeight
    this.getData()
  },
  mounted () {
    document.querySelector('.scroll').style.height = `${this.height}px`
  },
  methods: {
    getData () {
      let data = {
        stitle: this.stitle,
        page: this.listpageNo,
        size: this.listsize,
        btagcode: this.btagcode,
        stagcode: this.stagcode,
        flag: '0',
        dt: '1'
      }
      selectPage(data).then(res => {
        this.imgLists = this.imgLists == [] ? res.data.data.rows : this.imgLists.concat(res.data.data.rows)
        if (this.imgLists.length == 0) {
          this.isNull = true
        } else {
          this.isNull = false
        }
        this.listtotal = res.data.data.total
        this.listtotalPage = res.data.data.totalpage
        if (this.listtotalPage <= this.listpageNo) {
          this.listFinished = true
        }
        this.loadflag = true
      })
    },
    select () {
      this.listpageNo = 1
      this.listFinished = false
      if (!this.isNull) {
        this.$refs.scrollView.finishLoadMore()
      }
      this.isNull = false
      this.imgLists = []
      this.getData()
    },
    toDetail (item) {
      // 跳到文章页面
      if (item.btagcode == '1') {
        this.$router.push({
          path: `/Libwarticle?sno=${item.sno}&empno=${this.user.empno}&suserid=${this.user.userid}`
        })
      }
      // 生成海报
      if (item.btagcode == '2') {
        Toast.loading('生成中...')
        this.sctp = ''
        useHB({ sno: item.sno, flag: '2', rytype: this.user.rytype }).then(res => {
          this.sctp = 'data:image/png;base64,' + res.data.data
          this.showPic = true
          Toast.hide()
        })
      }
      // 跳到贺卡页面
      if (item.btagcode == '4') {
        this.$router.push(
          { name: 'carddetail', query: { sno: item.sno } }
        )
      }
    },
    cancelsearch () {
      this.searchFlag = false
      this.listpageNo = 1
      this.listFinished = false
      if (!this.isNull) {
        this.$refs.scrollView.finishLoadMore()
      }
      this.isNull = false
      this.imgLists = []
      this.stitle = ''
      this.getData()
    },
    handleCategory () {
      this.isPopupShow = true
    },
    hidePopUp () {
      this.isPopupShow = false
    },
    chooseCategory (val1, val2) {
      this.categoryData.forEach((item, index) => {
        item.isActive = false
      })
      this.categoryData[val2].isActive = true
      this.categoryTitle = this.categoryData[val2].label
      this.btagcode = this.categoryData[val2].value
      this.typeTitle = '全部'
      this.stagcode = ''
      this.typeData = [{ tagcode: '', tagname: '全部' }]
      if (val1.value != '') {
        selectKJFL(val1.value).then(res => {
          this.typeData = this.typeData.concat(res.data.data)
          console.log(this.typeData)
        })
      }
      this.listpageNo = 1
      this.listFinished = false
      if (!this.isNull) {
        this.$refs.scrollView.finishLoadMore()
      }
      this.isNull = false
      this.imgLists = []
      this.getData()
    },
    handleType () {
      this.isPopupShowType = true
      for (let i = 0; i < this.typeData.length; i++) {
        if (!this.typeData[i].isActive) {
          this.$set(this.typeData, 'isActive', false)
        } else {
          this.typeData[i].isActive = false
        }
        if (this.typeTitle == this.typeData[i].tagname) {
          this.typeData[i].isActive = true
        }
      }
    },
    hidePopUpType () {
      this.isPopupShowType = false
    },
    chooseType (val1, val2) {
      this.typeData.forEach((item, index) => {
        item.isActive = false
      })
      this.typeData[val2].isActive = true
      this.typeTitle = this.typeData[val2].tagname
      this.stagcode = this.typeData[val2].tagcode
      this.listpageNo = 1
      this.listFinished = false
      if (!this.isNull) {
        this.$refs.scrollView.finishLoadMore()
      }
      this.isNull = false
      this.imgLists = []
      this.getData()
    }
  }
}
</script>
<style lang="stylus" scoped>
@import './materiallibrary.styl';
</style>
