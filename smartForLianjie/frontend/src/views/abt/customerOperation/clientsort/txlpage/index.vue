<!--
 * @Author: 刘格优
 * @Date: 2019-11-15 14:23:10
 * @LastEditors  : 刘格优
 * @LastEditTime : 2020-02-10 15:45:22
 -->

<template>
  <div class="listbox">
    <md-scroll-view ref="scrollView"
                    auto-reflow
                    :scrolling-x="false"
                    @end-reached="loadMorechange">
      <div class="evelist">
        <ul>
          <li v-for="(item, index) in khlist"
              :key="index">
            <div class="libox">
              <div class="leftbox"
                   @click="tokhwj(item.userid)">
                <img :src="item.headimg"
                     alt=""
                     class="headimg" />
                <div class="pbox">
                  <p>
                    <span>{{ item.khname.length>7 ? item.khname.substr(0,5)+'...' :item.khname }}</span>
                  </p>
                  <p v-if="current == 'visitors'">{{ item.flushtime }}</p>
                  <p v-else>{{ item.intime }}</p>
                </div>
              </div>
              <p class="khphone">
                <a :href="'tel:' + item.phone">
                  <span>{{ item.phone }}</span>
                </a>
              </p>
              <div @click="tozxzx(item.userid)"
                   v-if="current != 'colleagues'">
                <img :src="getStorage('ossurl','')+getStorage('imgs',{}).xxicon"
                     alt=""
                     class="tozx" />
              </div>
              <div v-else
                   class="ywname">{{ item.ywname }}</div>
            </div>
          </li>
        </ul>
      </div>
      <md-scroll-view-more slot="more"
                           :is-finished="listFinished" />
    </md-scroll-view>
  </div>
</template>

<script>
import { getStorage } from '@/lib/util'
export default {
  props: {
    khlist: Array,
    current: String,
    nonedata: Boolean,

    listFinished: Boolean
  },
  data () {
    return {
      user: {}
    }
  },

  components: {},

  computed: {},
  created () {
    this.user = getStorage('u_s', {})
  },
  methods: {
    tokhwj (userid) { //(userid)客户ID进入客户挖掘页面
      this.$router.push({
        name: 'clientAnalysis',
        query: { customer: userid.toString() }
      })
    },
    tozxzx (userid) {
      this.$router.push(
        `Consult?askuserid=${userid}&empno=${this.user.empno}&emprytype=${this.user.rytype}`
      )
    },
    loadMorechange () {
      this.$emit('loadMorechange')
    }
  }
}
</script>
<style scoped lang="stylus">
@import './index.styl';
</style>
