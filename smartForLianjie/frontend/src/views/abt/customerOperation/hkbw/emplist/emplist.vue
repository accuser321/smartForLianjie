<!--
 * @Author: 刘格优
 * @Date: 2019-11-05 14:44:46
 * @LastEditors  : 刘格优
 * @LastEditTime : 2019-12-23 17:17:39
 -->
<template>
  <div class="acontent">
    <div v-show="!isNull"
         class="scrollbox">
      <md-scroll-view class="scroll"
                      ref="scrollView"
                      auto-reflow
                      :scrolling-x="false"
                      @end-reached="loadMore">
        <div class="wzcontent">
          <ul class="list-container aul"
              id="content">
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
                  <div class="readlog gray shareword">
                    <span><span class="redwords">{{ item.browsenum }}</span>位好友浏览</span>
                    <span><span class="redwords">{{ item.forwardnum }}</span>位好友分享</span>
                  </div>
                </div>
              </div>
              <div class="delbtn">
                <span class="btn btn1"
                      @click.stop="showFk(item, index)">去找他们</span>
                <span class="btn btn2"
                      @click.stop="delarticl(item, index)">删除</span>
              </div>
            </li>
          </ul>
        </div>
        <md-scroll-view-more slot="more"
                             :is-finished="loading" />
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
import { selectPage, deletesc } from '@/api/abt/customerOperation/common/index'
import loadMore from '@/mixins/loadmore'
import { getStorage } from '@/lib/util'
import { Toast } from 'mand-mobile'
export default {
  mixins: [loadMore],
  data () {
    return {
      pageNo: 1,
      size: 6,
      total: 0,
      totalPage: 0,
      loading: false,
      isNull: false,
      wzlist: [],
      user: {},
      ossurl: ''
    }
  },
  created () {
    this.ossurl = getStorage('ossurl', '')
    this.user = getStorage('u_s', {})
    this.getData()
  },
  components: {},

  methods: {
    //获取文章列表
    getData (isInit = true) {
      let { pageNo, size } = this
      if (!isInit) {
        this.pageNo = ++pageNo
        this.$refs.scrollView.finishLoadMore()
      }
      let data = {
        page: this.pageNo,
        size,
        btagcode: '1',
        stagcode: this.stagcode,
        flag: '1'
      }
      selectPage(data).then(res => {
        this.wzlist =
          this.wzlist == []
            ? res.data.data.rows
            : this.wzlist.concat(res.data.data.rows)
        if (this.wzlist.length > 0) {
          this.isNull = false
        } else {
          this.isNull = true
        }
        this.total = res.data.data.total
        this.totalPage = res.data.data.totalpage
      })
    },
    getarticle (sno) {//文章详情
      this.$router.push({
        path: `/Emparticle?sno=${sno}&empno=${this.user.empno}&suserid=${this.user.userid}`
      })
    },
    showFk (item, index) { //去找他们
      this.$router.push({
        path: '/looked',
        query: { recordtype: '10', sno: item.sno }
      })
    },
    delarticl (item, index) { //删除
      deletesc({ sno: item.sno }).then(res => {
        Toast.info(res.data.msg)
        this.wzlist.splice(index, 1)
      })
    }
  }
}
</script>
<style scoped lang="stylus">
@import './index.styl';
</style>
