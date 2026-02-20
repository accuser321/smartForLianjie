<!--
 * @Descripttion:
 * @version:
 * @Author: 贾峰
 * @Date: 2019-11-09 17:49:53
 * @LastEditors: 王广婷
 * @LastEditTime: 2020-04-01 10:58:43
 -->
<template>
  <div class="container">
    <div v-if="$store.state.app.isloading"
         class="md-example-child md-example-child-md-activity-indicator md-example-child-md-activity-indicator-0">
      <md-activity-indicator class="md-activity-indicator-css"
                             type="spinner"
                             vertical
                             color="light">加载中...</md-activity-indicator>
    </div>
    <div class="router_div" v-show="!$store.state.app.isloading">
      <router-view />
      <!-- <keep-alive include="sxhome">
        <router-view />
      </keep-alive> -->
    </div>
  </div>
</template>

<style lang="stylus" scoped>
@import './assets/css/base.css';

.md-button {
  height: 80px;
  line-height: 80px;
}

.md-result-image {
  width: 260px;
}

.md-popup-mask {
  z-index: 997;
}

.md-popup-box {
  z-index: 999;
}

.container {
  height: 100%;

  .router_div {
    height: 100%;
  }
}

.md-example-child-md-activity-indicator-0 {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  z-index: 1000 !important;
}

/deep/ .md-activity-indicator {
  height: 200px;
  width: 260px;
  margin: auto auto;
  background: rgba(0, 0, 0, 0.7) !important;
  border-radius: 8px;
}

.md-activity-indicator /deep/ .indicator-container {
  height: 100%;
}

.md-activity-indicator /deep/ .md-activity-indicator-svg {
  width: 100px !important;
  height: 100px !important;
}
</style>
