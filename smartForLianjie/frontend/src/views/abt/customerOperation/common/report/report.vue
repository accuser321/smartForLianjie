<!--
 * @Author: 侯依辰
 * @Date: 2019-12-02 15:15:25
 * @LastEditors: 侯依辰
 * @LastEditTime: 2019-12-02 16:30:41
 -->

<template>
  <div class="aspan_bottom">
    本文来源于网络，不代表本公众号立场，若内容不规范或涉及违规，请立即
    <a style="color: #145FA6"
       @click='proposal'>举报/报错</a>
  </div>
</template>
<script>
export default {
  props: {
    sno: String,
    stitle: String
  },
  methods: {
    // 举报
    proposal () {
      this.$router.push({
        path: '/complain',
        query: { sno: this.sno, stitle: this.stitle }
      })
    }
  }
}
</script>

<style lang="stylus" scoped>
.aspan_bottom {
  width: 90%;
  margin: 0 auto;
  background-color: white;
  word-wrap: break-word;
  color: rgb(204, 204, 204);
  font-size: 0.4rem;
  padding-bottom: 0.3rem;
  text-indent: 0.8rem;
}
</style>
