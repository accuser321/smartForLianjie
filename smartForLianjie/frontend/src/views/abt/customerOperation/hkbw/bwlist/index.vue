<!--
 * @Author: 刘格优
 * @Date: 2019-11-05 14:44:35
 * @LastEditors  : 刘格优
 * @LastEditTime : 2019-12-25 20:05:58
 -->
<template>
  <div class="acontent">
    <!-- 文章标题 -->
    <div class="article-type-container tochscroll">
      <div class="article-type">
        <md-tab-bar v-model="current"
                    :items="labellist"
                    :maxLength="8"
                    @change="tabluck"
                    class="tabbar" />
      </div>
      <div class="fdj"
           @click="openSearch"></div>
    </div>
    <!-- 文章列表 -->
    <md-scroll-view class="scroll"
                    v-show="!isNull"
                    ref="listscrollView"
                    auto-reflow
                    :scrolling-x="false"
                    @end-reached="loadMorechange">
      <div class="wzcontent">
        <ul class="list-container aul">
          <li class="ali"
              v-for="(item, index) in wzlist"
              :key="index">
            <div class="card"
                 @click="getarticle(item.sno)">
              <div class="item-media">
                <div class="imgbox">
                  <div class="img"
                       :style="{
                      'background-image': 'url(' + ossurl + item.pichttp + ')'
                    }"></div>
                </div>
              </div>
              <div class="item-inner">
                <div v-if="item.stitle.length > 20">
                  <div class="Title"
                       v-html="item.stitle.slice(0, 20) + '...'"></div>
                </div>
                <div v-else>
                  <div class="Title"
                       v-html="item.stitle"></div>
                </div>
                <div class="desc">{{ item.sdesc }}</div>
                <div class="readlog gray">阅读&nbsp;{{ item.ydnum }}</div>
              </div>
            </div>
          </li>
        </ul>
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
    <!-- 搜索页面 -->
    <md-popup class="clearfix"
              id="searchPage"
              v-model="searchVisible"
              transition="md-zoom">
      <div class="searchbox clearfix">
        <div class="search setmid fl" :style="'background: url('+getStorage('ossurl','')+getStorage('imgs',{}).search+');background-repeat: no-repeat;background-size: 0.6rem;background-position-y: center;'">
          <input type="text"
                 ref="search"
                 v-model="keyword"
                 placeholder="搜你想要的"
                 @keyup.enter="searchenter" />
        </div>

        <div class="cancel grayfont"
             @click="searchenter">搜索</div>
        <div class="closeicon"
             @click="closeSearch">
          <md-icon name="close"
                   size="lg"></md-icon>
        </div>
        <div style="clear:both;"></div>
      </div>
      <md-scroll-view class="scroll"
                      v-show="!searchisNull"
                      ref="scrollView"
                      auto-reflow
                      :scrolling-x="false"
                      @end-reached="loadMore">
        <div class="wzcontent">
          <ul class="list-container aul">
            <li class="ali"
                v-for="(item, index) in searchwz"
                :key="index">
              <div class="card"
                   @click="getarticle(item.sno)">
                <div class="item-media">
                  <div class="imgbox">
                    <div class="img"
                         :style="{
                        'background-image': 'url(' + ossurl + item.pichttp + ')'
                      }"></div>
                  </div>
                </div>
                <div class="item-inner">
                  <div v-if="item.stitle.length > 20">
                    <div class="Title"
                         v-html="item.stitle.slice(0, 20) + '...'"></div>
                  </div>
                  <div v-else>
                    <div class="Title"
                         v-html="item.stitle"></div>
                  </div>
                  <div class="desc">{{ item.sdesc }}</div>
                  <div class="readlog gray">阅读&nbsp;{{ item.ydnum }}</div>
                </div>
              </div>
            </li>
          </ul>
        </div>
        <md-scroll-view-more slot="more"
                             :is-finished="loading" />
      </md-scroll-view>
      <div class="nodata"
           v-show="searchisNull">
        <img class="kongimg"
             src="@/assets/image/null.png"
             alt="" />
      </div>
    </md-popup>
    <div class="tips mywz"
         @click="tomywz">我的文章</div>
    <div class="tips"
         @click="makebw">制作爆文</div>
  </div>
</template>

<script>
import {
  selectKJFL,
  selectPage
} from '@/api/abt/customerOperation/common/index'
import loadMore from '@/mixins/loadmore'
import { getStorage } from '@/lib/util'
export default {
  mixins: [loadMore],
  data () {
    return {
      pageNo: 1,
      size: 6,
      total: 0,
      totalPage: 0,
      listpageNo: 1,
      listsize: 6,
      listtotal: 0,
      listtotalPage: 0,
      listFinished: false,
      current: 'new',
      tabclass: '',
      keyword: '',
      stagcode: '',
      loading: false,
      labellist: [
        {
          tagname: '最新',
          tagcode: 'new'
        }
      ],
      wzlist: [],
      searchwz: [],
      user: {},
      searchVisible: false,
      loadflag: false,
      ossurl: '',
      isNull: true,
      searchisNull: true
    }
  },

  created () {
    this.ossurl = getStorage('ossurl', '')
    this.user = getStorage('u_s', {})
    console.log("员工编号");
    console.log(this.user);
    this.getSelectList()
    this.getData()
  },

  methods: {
    getSelectList () {//获取头部标题
      selectKJFL({ tagcode: '1' }).then(res => {
        // console.log(res);
        this.labellist = this.labellist.concat(res.data.data)
        this.labellist.forEach((item, index) => {
          item.name = item.tagcode
          item.label = item.tagname
        })
      })
    },
    getData () { //获取文章列表
      let data = {
        page: this.listpageNo,
        size: this.listsize,
        btagcode: '1',
        stagcode: this.stagcode,
        flag: '0'
      }
      selectPage(data).then(res => {
        // console.log(res);
        this.wzlist =
          this.wzlist == []
            ? res.data.data.rows
            : this.wzlist.concat(res.data.data.rows)
        this.listtotal = res.data.data.total
        this.listtotalPage = res.data.data.totalpage
        if (this.wzlist.length == 0) {
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
          this.$refs.listscrollView.finishLoadMore()
        }
        this.loadflag = true
        // console.log('请求数据中加载状态' + this.loadflag)
        // console.log('请求数据中是否停止加载' + this.listFinished)
      })
    },
    loadMorechange () {
      if (this.loadflag) {
        if (this.listFinished) {
          return false
        } else {
          if (this.listtotalPage < this.listpageNo) {
            this.listFinished = true
          } else {
            this.loadflag = false
            this.$refs.listscrollView.finishLoadMore()
            this.listpageNo = ++this.listpageNo
            // console.log('--------------上拉加载请求更多中--------------')
            // console.log('加载时总页数' + this.listtotalPage)
            // console.log('加载时当前页' + this.listpageNo)
            // console.log('加载时加载状态' + this.loadflag)
            // console.log('加载时是否停止加载' + this.listFinished)
            this.getData()
          }
        }
      }
    },
    tabluck (item, index, prevIndex) {
      this.stagcode = item.name
      if (item.name == 'new') {
        this.stagcode = ''
      }
      this.listpageNo = 1
      this.wzlist = []
      this.listFinished = false
      this.loadflag = false
      this.$refs.listscrollView.finishLoadMore()
      // console.log('--------------切换中--------------')
      // console.log('切换时总页数' + this.listtotalPage)
      // console.log('切换时当前页' + this.listpageNo)
      // console.log('切换时加载状态' + this.loadflag)
      // console.log('切换时是否停止加载' + this.listFinished)
      this.getData()
    },
    getarticle (sno) {
      // console.log("数据接收")
      // console.log(sno);//文章编号
      // console.log(this.user.empno);//工号
      // console.log(this.user.userid);//10000040
      this.$router.push({
        path: `/Libwarticle?sno=${sno}&empno=${this.user.empno}&suserid=${this.user.userid}`
      })
    },
    openSearch () {
      this.searchwz = []
      this.keyword = ''
      this.searchVisible = true
      this.searchisNull = true
    },
    // 关闭搜索
    closeSearch () {
      this.searchVisible = false
    },
    searchenter (isInit = true) {
      this.searchwz = []
      let { pageNo, size } = this
      if (!isInit) {
        this.pageNo = ++pageNo
        this.$refs.scrollView.finishLoadMore()
      }
      let data = {
        page: this.pageNo,
        size,
        btagcode: '1',
        stagcode: '',
        flag: '0',
        stitle: this.keyword
      }
      selectPage(data).then(res => {
        this.searchwz =
          this.searchwz == []
            ? res.data.data.rows
            : this.searchwz.concat(res.data.data.rows)
        if (this.searchwz.length == 0) {
          this.searchisNull = true
        } else {
          this.searchisNull = false
        }
        this.total = res.data.data.total
        this.totalPage = res.data.data.totalpage
      })
    },
    tomywz () {
      this.$router.push('/Emplist')
    },
    makebw () {
      this.$router.push('/makebw')
    }
  }
}
</script>
<style scoped lang="stylus">
@import './index.styl';
</style>
