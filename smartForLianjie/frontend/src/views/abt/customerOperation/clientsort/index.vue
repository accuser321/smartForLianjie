<!--
 * @Author: 刘格优
 * @Date: 2019-11-13 11:53:41
 * @LastEditors: 刘格优
 * @LastEditTime: 2020-03-16 11:55:39
 -->
<template>
  <div class="content">
    <div class="topbox">
      <div class="topsearch">
        <div class="searchbox clearfix">
          <div class="search setmid fl">
            <div :style="'background: url('+getStorage('ossurl','')+getStorage('imgs',{}).search+');background-repeat: no-repeat;background-size: 0.6rem;background-position-y: center;'"
                 class="searchimg">
            </div>
            <input type="text"
                   ref="search"
                   v-model="searchValue"
                   placeholder="请输入姓名"
                   @keyup.enter="getflkh" />
          </div>
          <div class="cancel grayfont"
               @click="getflkh">搜索</div>
          <div class="iconbox"
               @click="openchoose">
            <md-icon name="profession"
                     size="lg"></md-icon>
          </div>
          <div style="clear:both;"></div>
        </div>
      </div>
      <md-tab-bar class="tabbar"
                  v-model="current"
                  :items="type"
                  @change="onChange" />
    </div>
    <div class="tabcontent">
      <!-- 通讯录 -->
      <div v-show="current == 'address'">
        <div v-show="nonedata"
             class="nodata_style"
             @click="gobw">
          <div><img class="nodata_img"
                 src="@/assets/abt/img/nodata.png"
                 alt=""
                 @click="gobw" /></div>
          <div class="nodata_text">没有客户进入你的通讯录，转发文章到朋友圈获取客户信息，进行客户管理!</div>
          <div class="nodata_btn">立即获客</div>
        </div>
        <txlpage v-show="!nonedata"
                 :khlist="khlist"
                 :current="current"
                 :nonedata="nonedata"
                 :listFinished="listFinished"
                 @loadMorechange="loadMorechange"
                 class="scroll1"
                 ref="zhujian1"></txlpage>
      </div>
      <!-- 最近访客 -->
      <div v-show="current == 'visitors'">
        <div v-show="nonedata">
          <div v-show="nonedata"
               class="nodata_style"
               @click="gobw">
            <div><img class="nodata_img"
                   src="@/assets/abt/img/nodata.png"
                   alt=""
                   @click="gobw" /></div>
            <div class="nodata_text">最近没有人看过你哦!转发文章或名片让客户认识你!</div>
            <div class="nodata_btn">立即获客</div>
          </div>
          <!--<img class="nodataimg"-->
          <!--src="@/assets/dr/khwj2.jpg"-->
          <!--alt=""-->
          <!--@click="gobw" />-->
        </div>
        <txlpage v-show="!nonedata"
                 :khlist="khlist"
                 :current="current"
                 :nonedata="nonedata"
                 :listFinished="listFinished"
                 @loadMorechange="loadMorechange"
                 class="scroll2"
                 ref="zhujian2"></txlpage>
      </div>
      <!-- 准客户 -->
      <div v-show="current == 'prospect'">
        <div v-show="nonedata">
          <div v-show="nonedata"
               class="nodata_style"
               @click="gobw">
            <div><img class="nodata_img"
                   src="@/assets/abt/img/nodata.png"
                   alt=""
                   @click="gobw" /></div>
            <div class="nodata_text">没有准客户？<br />进入通讯录,点击客户头像,客户信息轻松维护!<br />记得点选“准客户”标签哦!</div>
            <div class="nodata_btn">转发爆文</div>
          </div>
          <!--<img class="nodataimg"-->
          <!--src="@/assets/dr/khwj3.jpg"-->
          <!--alt=""-->
          <!--@click="gobw" />-->
        </div>
        <txlpage v-show="!nonedata"
                 :khlist="khlist"
                 :current="current"
                 :nonedata="nonedata"
                 class="scroll3"
                 :listFinished="listFinished"
                 @loadMorechange="loadMorechange"
                 ref="zhujian3"></txlpage>
      </div>
      <!-- 朋友（接口字段沿用 colleagues / getColleague） -->
      <div v-show="current == 'colleagues'">
        <div v-show="nonedata"
             class="nodata_style">
          <div v-show="nonedata"
               class="nodata_style"
               @click="gobw">
            <div><img class="nodata_img"
                   src="@/assets/abt/img/nodata.png"
                   alt=""
                   @click="gobw" /></div>
            <div class="nodata_text">你的朋友列表竟然是空的<br />点击下方按钮<br />分享你的AI名片<br />邀小伙伴们一起轻松展业吧!</div>
            <div class="nodata_btn">立即前往</div>
          </div>
          <!--<img class="nodataimg"-->
          <!--src="@/assets/dr/khwj4.jpg"-->
          <!--alt=""-->
          <!--@click="gomp" />-->
        </div>
        <txlpage v-show="!nonedata"
                 :khlist="khlist"
                 :current="current"
                 :nonedata="nonedata"
                 class="scroll4"
                 :listFinished="listFinished"
                 @loadMorechange="loadMorechange"
                 ref="zhujian4"></txlpage>
      </div>
    </div>
    <md-popup v-model="bqchoose"
              position="bottom"
              :mask-closable="false">
      <md-popup-title-bar title="选择标签"
                          describe=""
                          ok-text="确定"
                          cancel-text="取消"
                          large-radius
                          @confirm="tochoose()"
                          @cancel="hidePopUp()"></md-popup-title-bar>
      <div class="md-example-popup md-example-popup-bottom">
        <div class="list">
          <div class="bqbox"
               v-for="(item, index) in bqlist"
               :key="index">
            <p>{{ item.tagname }}</p>
            <div class="bqdiv">
              <span v-for="(eve, i) in item.children"
                    :key="i"
                    @click="chselabes(eve)"
                    :class="{ active: eve.show }">
                {{ eve.tagname }}
              </span>
            </div>
          </div>
        </div>
      </div>
    </md-popup>
  </div>
</template>

<script>
import txlpage from './txlpage/index'
import {
  selectKHBQ,
  getColleague,
  getCommunicationKH,
  getRecentlyKH,
  getStandardKH
} from '@/api/abt/customerOperation/clientsort/index'
import { getStorage } from '@/lib/util'
export default {
  data () {
    return {
      searchValue: '',
      jumptype: '',
      bqchoose: false,
      bqlist: [],
      choosebqlist: [],
      khlist: [],
      current: 'visitors',
      type: [
        { label: '最近访客', name: 'visitors' },
        { label: '通讯录', name: 'address' },
        { label: '准客户', name: 'prospect' },
        { label: '朋友', name: 'colleagues' }
      ],
      nonedata: false,
      listpageNo: 1,
      listsize: 10,
      listtotal: 0,
      listtotalPage: 0,
      loadflag: false,
      listFinished: false
    }
  },
  components: {
    txlpage
  },
  created () {
    // this.gettxl()
    this.getfk()
  },
  mounted () {
    document.querySelector('.scroll1').style.height = `${document.body
      .clientHeight - 100}px`
    document.querySelector('.scroll2').style.height = `${document.body
      .clientHeight - 100}px`
    document.querySelector('.scroll3').style.height = `${document.body
      .clientHeight - 100}px`
    document.querySelector('.scroll4').style.height = `${document.body
      .clientHeight - 100}px`
  },
  methods: {
    getflkh () { //搜索功能
      this.listpageNo = 1
      this.listFinished = false
      this.loadflag = false
      // this.$refs.zhujian.$refs.scrollView.finishLoadMore()
      if (this.current === 'address') { //通讯录
        this.$refs.zhujian1.$refs.scrollView.finishLoadMore()
      } else if (this.current === 'visitors') {//最近访客
        this.$refs.zhujian2.$refs.scrollView.finishLoadMore()
      } else if (this.current === 'prospect') {//准客户
        this.$refs.zhujian3.$refs.scrollView.finishLoadMore()
      } else if (this.current === 'colleagues') {//朋友（历史字段名：colleagues）
        this.$refs.zhujian4.$refs.scrollView.finishLoadMore()
      }
      this.khlist = []
      if (this.current === 'address') {
        this.gettxl()
      } else if (this.current === 'visitors') {
        this.getfk()
      } else if (this.current === 'prospect') {
        this.getzkh()
      } else if (this.current === 'colleagues') {
        this.getts()
      }
    },
    onChange (item, index, prevIndex) {
      this.nonedata = false
      this.listpageNo = 1
      this.khlist = []
      this.listFinished = false
      this.loadflag = false
      // this.$refs.zhujian.$refs.scrollView.finishLoadMore()
      if (this.current === 'address') {
        this.$refs.zhujian1.$refs.scrollView.finishLoadMore()
      } else if (this.current === 'visitors') {
        this.$refs.zhujian2.$refs.scrollView.finishLoadMore()
      } else if (this.current === 'prospect') {
        this.$refs.zhujian3.$refs.scrollView.finishLoadMore()
      } else if (this.current === 'colleagues') {
        this.$refs.zhujian4.$refs.scrollView.finishLoadMore()
      }
      // console.log('--------------切换中--------------')
      // console.log('切换时总页数' + this.listtotalPage)
      // console.log('切换时当前页' + this.listpageNo)
      // console.log('切换时加载状态' + this.loadflag)
      // console.log('切换时是否停止加载' + this.listFinished)
      if (item.name === 'address') {
        this.gettxl()
      } else if (item.name === 'visitors') {
        this.getfk()
      } else if (item.name === 'prospect') {
        this.getzkh()
      } else if (item.name === 'colleagues') {
        this.getts()
      }
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
            this.listpageNo = ++this.listpageNo
            if (this.current === 'address') {
              this.$refs.zhujian1.$refs.scrollView.finishLoadMore()
            } else if (this.current === 'visitors') {
              this.$refs.zhujian2.$refs.scrollView.finishLoadMore()
            } else if (this.current === 'prospect') {
              this.$refs.zhujian3.$refs.scrollView.finishLoadMore()
            } else if (this.current === 'colleagues') {
              this.$refs.zhujian4.$refs.scrollView.finishLoadMore()
            }
            // this.$refs.zhujian.$refs.scrollView.finishLoadMore()
            // console.log('--------------上拉加载请求更多中--------------')
            // console.log('加载时总页数' + this.listtotalPage)
            // console.log('加载时当前页' + this.listpageNo)
            // console.log('加载时加载状态' + this.loadflag)
            // console.log('加载时是否停止加载' + this.listFinished)
            if (this.current === 'address') {
              this.gettxl()
            } else if (this.current === 'visitors') {
              this.getfk()
            } else if (this.current === 'prospect') {
              this.getzkh()
            } else if (this.current === 'colleagues') {
              this.getts()
            }
          }
        }
      }
    },
    getbq () { //获取标签列表
      selectKHBQ().then(res => {
        this.bqlist = res.data.data
        this.bqlist.forEach((item, index) => {
          item.children.forEach((ite, i) => {
            this.$set(ite, 'show', false)
            this.choosebqlist.forEach((chooseitem, chooseindex) => {
              if (this.choosebqlist[chooseindex] === ite.id) {
                ite.show = true
              }
            })
          })
        })
        this.bqchoose = true
      })
    },
    openchoose () { //打开标签选择
      this.getbq()
    },
    tochoose () {
      this.choosebqlist = []
      this.bqlist.forEach((item, index) => {
        item.children.forEach((ite, i) => {
          if (ite.show) {
            this.choosebqlist.push(ite.id)
          }
        })
      })
      if (this.current === 'address') {
        this.khlist = []
        this.gettxl()
      } else if (this.current === 'visitors') {
        this.khlist = []
        this.getfk()
      } else if (this.current === 'prospect') {
        this.khlist = []
        this.getzkh()
      } else if (this.current === 'colleagues') {
        this.khlist = []
        this.getts()
      }
      this.bqchoose = false
    },
    hidePopUp (type) {
      this.bqchoose = false
    },
    chselabes (item) {
      item.show = !item.show
    },
    gettxl () { //获取通讯录列表
      getCommunicationKH({
        page: this.listpageNo,
        size: this.listsize,
        name: this.searchValue, //姓名
        labid: this.choosebqlist //标签
      }).then(res => {
        this.khlist =
          this.khlist == []
            ? res.data.data.rows
            : this.khlist.concat(res.data.data.rows)
        this.listtotal = res.data.data.total
        this.listtotalPage = res.data.data.totalpage
        if (this.khlist.length == 0) {
          this.nonedata = true
        } else {
          this.nonedata = false
        }
        // console.log('--------------请求数据中--------------')
        // console.log('请求数据中总页数' + this.listtotalPage)
        // console.log('请求数据中当前页' + this.listpageNo)
        if (this.listtotalPage <= this.listpageNo) {
          this.listFinished = true
        } else {
          this.listFinished = false
          // this.$refs.zhujian.$refs.scrollView.finishLoadMore()
          if (this.current === 'address') {
            this.$refs.zhujian1.$refs.scrollView.finishLoadMore()
          } else if (this.current === 'visitors') {
            this.$refs.zhujian2.$refs.scrollView.finishLoadMore()
          } else if (this.current === 'prospect') {
            this.$refs.zhujian3.$refs.scrollView.finishLoadMore()
          } else if (this.current === 'colleagues') {
            this.$refs.zhujian4.$refs.scrollView.finishLoadMore()
          }
        }
        this.loadflag = true
        // console.log('请求数据中加载状态' + this.loadflag)
        // console.log('请求数据中是否停止加载' + this.listFinished)
      })
    },
    getfk () { //获取最近访客
      getRecentlyKH({
        page: this.listpageNo,
        size: this.listsize,
        name: this.searchValue, //姓名
        labid: this.choosebqlist
      }).then(res => {
        this.khlist =
          this.khlist == []
            ? res.data.data.rows
            : this.khlist.concat(res.data.data.rows)
        this.listtotal = res.data.data.total
        this.listtotalPage = res.data.data.totalpage
        if (this.khlist.length == 0) {
          this.nonedata = true
        } else {
          this.nonedata = false
        }
        // console.log('--------------请求数据中--------------')
        // console.log('请求数据中总页数' + this.listtotalPage)
        // console.log('请求数据中当前页' + this.listpageNo)
        if (this.listtotalPage <= this.listpageNo) {
          this.listFinished = true
        } else {
          this.listFinished = false
          // this.$refs.zhujian.$refs.scrollView.finishLoadMore()
          if (this.current === 'address') {
            this.$refs.zhujian1.$refs.scrollView.finishLoadMore()
          } else if (this.current === 'visitors') {
            this.$refs.zhujian2.$refs.scrollView.finishLoadMore()
          } else if (this.current === 'prospect') {
            this.$refs.zhujian3.$refs.scrollView.finishLoadMore()
          } else if (this.current === 'colleagues') {
            this.$refs.zhujian4.$refs.scrollView.finishLoadMore()
          }
        }
        this.loadflag = true
        // console.log('请求数据中加载状态' + this.loadflag)
        // console.log('请求数据中是否停止加载' + this.listFinished)
        // eslint-disable-next-line eqeqeq
      })
    },
    getzkh () {
      getStandardKH({ //查询准客户列表
        page: this.listpageNo,
        size: this.listsize,
        name: this.searchValue,  //姓名
        labid: this.choosebqlist,//标签列表
      }).then(res => {
        this.khlist =
          this.khlist == []
            ? res.data.data.rows
            : this.khlist.concat(res.data.data.rows)
        this.listtotal = res.data.data.total
        this.listtotalPage = res.data.data.totalpage
        if (this.khlist.length == 0) {
          this.nonedata = true
        } else {
          this.nonedata = false
        }
        // console.log('--------------请求数据中--------------')
        // console.log('请求数据中总页数' + this.listtotalPage)
        // console.log('请求数据中当前页' + this.listpageNo)
        if (this.listtotalPage <= this.listpageNo) {
          this.listFinished = true
        } else {
          this.listFinished = false
          // this.$refs.zhujian.$refs.scrollView.finishLoadMore()
          if (this.current === 'address') {
            this.$refs.zhujian1.$refs.scrollView.finishLoadMore()
          } else if (this.current === 'visitors') {
            this.$refs.zhujian2.$refs.scrollView.finishLoadMore()
          } else if (this.current === 'prospect') {
            this.$refs.zhujian3.$refs.scrollView.finishLoadMore()
          } else if (this.current === 'colleagues') {
            this.$refs.zhujian4.$refs.scrollView.finishLoadMore()
          }
        }
        this.loadflag = true
        // console.log('请求数据中加载状态' + this.loadflag)
        // console.log('请求数据中是否停止加载' + this.listFinished)
        // eslint-disable-next-line eqeqeq
      })
    },
    getts () {
      getColleague({ // 获取朋友列表（接口名保留 getColleague，避免影响后端）
        // page: 1,
        // size: 10,
        page: this.listpageNo,
        size: this.listsize,
        name: this.searchValue,//姓名
        labid: this.choosebqlist ,//标签ID
      }).then(res => {
        this.khlist =
          this.khlist == []
            ? res.data.data.rows
            : this.khlist.concat(res.data.data.rows)
        this.listtotal = res.data.data.total
        this.listtotalPage = res.data.data.totalpage
        if (this.khlist.length == 0) {
          this.nonedata = true
        } else {
          this.nonedata = false
        }
        // console.log('--------------请求数据中--------------')
        // console.log('请求数据中总页数' + this.listtotalPage)
        // console.log('请求数据中当前页' + this.listpageNo)
        if (this.listtotalPage <= this.listpageNo) {
          this.listFinished = true
        } else {
          this.listFinished = false
          // this.$refs.zhujian.$refs.scrollView.finishLoadMore()
          if (this.current === 'address') {
            this.$refs.zhujian1.$refs.scrollView.finishLoadMore()
          } else if (this.current === 'visitors') {
            this.$refs.zhujian2.$refs.scrollView.finishLoadMore()
          } else if (this.current === 'prospect') {
            this.$refs.zhujian3.$refs.scrollView.finishLoadMore()
          } else if (this.current === 'colleagues') {
            this.$refs.zhujian4.$refs.scrollView.finishLoadMore()
          }
        }
        this.loadflag = true
        // console.log('请求数据中加载状态' + this.loadflag)
        // console.log('请求数据中是否停止加载' + this.listFinished)
        // eslint-disable-next-line eqeqeq
      })
    },
    gobw () { //跳转到获客爆文
      this.$router.push('/Hkbw')
    },
    gomp () {
      this.$router.push('/visitingcard')
    }
  }
}
</script>
<style scoped lang="stylus">
@import './index.styl';
</style>
