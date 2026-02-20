<!--
 * @Descripttion:
 * @version:
 * @Author: 李宗哲
 * @Date: 2020-01-09 17:12:51
 * @LastEditors: 刘格优
 * @LastEditTime: 2020-03-05 16:34:49
 -->
<template>
  <div class="content">
    <div class="productcontent"
         style="padding:1rem;">
      <div id="jumpshow"></div>
    </div>
  </div>
</template>

<script>
import { getxxtslist } from '@/api/basic/index'
import { getStorage } from '@/lib/util'
import loadMore from '@/mixins/loadmore'// 下拉加载的
import initWebSocket from '@/mixins/websock'
import config from '@/config'
const { redirect_uri } = config
export default {
  mixins: [loadMore, initWebSocket], // 下拉加载的
  data () {
    return {
      ossurl: '',
      pushid: ''
    }
  },
  created () {
    this.user = getStorage('u_s', {})
    this.ossurl = getStorage('ossurl', '')
    this.pushid = this.$route.query.pushid
    this.getclassinfo()
  },
  methods: {
    getclassinfo () {
      getxxtslist({ userId: this.user.userid + '', pushid: this.pushid }).then(res => {
        document.title = res.data.data.ptitle
        if (res.data.data.stagcode == '4') {
          $('#jumpshow').load(this.ossurl + res.data.data.pcontent)
        } else {
          location.href = res.data.data.pcontent
        }
      })
    }
  }
}
</script>
<style scoped lang="stylus">
.content {
  height: 100%;
  width: 100%;
}
</style>
