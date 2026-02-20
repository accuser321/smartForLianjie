<!--
 * @Author: 王广婷
 * @Date: 2019-10-29 16:24:13
 * @LastEditors: 王广婷
 * @LastEditTime: 2020-03-27 09:24:26
 -->
<template>
  <div class="home_box" :style='`width:${width}px;height:${height}px;`'>
    <div class="ydt" v-if="isShow" :style='`width:${width}px;height:${height}px;`'>
      <!-- <div class="leadimg_box img_box"><img src="../../../assets/dr/zymc.png" alt=""></div> -->
      <div class="leadimg_box img_box"><img :src="leadimg" alt=""></div>
      <div class="close_box img_box"><img src="../../../assets/dr/wzdl.png" @click="isShow=false" alt=""></div>
    </div>
    <div class="home_nr" id="content">
      <component :is="who"></component>
    </div>
  </div>
</template>
<script>
import { readDirfiles, getStorage } from '@/lib/util'
export default {
  data () {
    return {
      who: '',
      routerList: [],
      width: 0,
      height: 0,
      isShow: false,
      leadimg: ''
      // leadimg: 'http://uatq.abtptoss.com/root/ICON/drbx/BMICON20200114214703545877.png'
    }
  },
  components: readDirfiles(require.context('./component', false, /\.vue$/)),
  beforeRouteEnter (to, from, next) {
    next(that => {
      from.path == '/login' ? that.isShow = true : ''
    })
  },
  created () {
    this.width = window.innerWidth
    this.height = window.innerHeight
    this.routerList = Object.keys(readDirfiles(require.context('./component', false, /\.vue$/)))
    this.leadimg = getStorage('sys_info', '') ? getStorage('sys_info', '').leadimg : ''
    let router = getStorage('sys_info', '') ? getStorage('sys_info', '').stylenum : ''
    this.who = this.routerList.includes(router) ? router : 'nhwx'
  }
}
</script>
<style lang="stylus" scoped>
  .home_box{
    width: 100%;
    height: 100%;
    position: relative;
  }
  .ydt {
    width: 100%;
    height: 100%;
    position: absolute;
    z-index: 1;
    .img_box{
      position: absolute;
    }
    .leadimg_box{
      height: 100%;
    }
    .close_box {
      position:fixed;
      bottom:200px;
      text-align:center;
      img{
        width: 50%;
      }
    }
  }
  .home_nr{
    width: 100%;
    position: absolute;
  }
</style>
