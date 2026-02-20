<!--
 * @Author: 王广婷
 * @Date: 2020-02-27 13:31:38
 * @LastEditors: 王广婷
 * @LastEditTime: 2020-03-24 11:51:13
 -->
<template>
    <div class="div_box">
        <div v-if="type=='activity'" v-html="content">{{a}}</div>
        <div v-else id="banner-con"></div>
    </div>
</template>
<script>
import { getrmhddetail } from '@/api/basic'
import config from '@/config'
const { pcossurl } = config
export default {
  data () {
    return {
      content: '',
      type: 'activity'
    }
  },
  created () {
    // eslint-disable-next-line no-unused-expressions
    this.$route.query.type ? this.type = this.$route.query.type : ''
    if (this.type == 'banner') {
      this.$nextTick(() => {
        $('#banner-con').load(`${pcossurl}${this.$route.query.bannerhttp}`)
      })
    } else {
      this.getrmhddetail()
    }
  },
  methods: {
    getrmhddetail () {
      getrmhddetail({ activityno: this.$route.query.activityno }).then(res => {
        this.content = res.data.data.text
      })
    }
  }
}
</script>
<style lang="stylus" scoped>
.div_box{
    padding 0.3rem
}
</style>
