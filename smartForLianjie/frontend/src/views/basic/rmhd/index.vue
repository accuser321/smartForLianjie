<!--
 * @Author: 王广婷
 * @Date: 2020-02-27 13:31:38
 * @LastEditors: 王广婷
 * @LastEditTime: 2020-04-01 11:00:07
 -->
<template>
    <div class="div_box">
        <div v-if="rmhdList.length==0" class="imgbox"><img src="@/assets/image/null.png" alt=""></div>
        <ul v-else>
            <li v-for="(rmhd,index) in rmhdList" :key="index" class="li_box">
              <img class="li_img" :src="ossurl+rmhd.img" alt="" @click="to(rmhd)">
              <div class="img_text">{{rmhd.title}}</div>
            </li>
        </ul>
    </div>
</template>
<script>
import { getrmhdlist } from '@/api/basic'
import { getStorage } from '@/lib/util'
export default {
  data () {
    return {
      rmhdList: [],
      tjcode: '',
      ossurl: getStorage('ossurl', '')
    }
  },
  created () {
    this.tjcode = getStorage('u_s', {}).jglower ? getStorage('u_s', {}).jglower : getStorage('u_s', {}).tjcode
    this.getRmhdList()
  },
  methods: {
    getRmhdList () {
      getrmhdlist({ tjcode: this.tjcode, page: 1, size: 10, flag: 1 }).then(res => {
        this.rmhdList = res.data.data.rows
      })
    },
    to (rmhd) {
      rmhd.urltype == 'inner' ? this.$router.push({ path: '/rmhddetail', query: { activityno: rmhd.activityno } }) : location.replace(`${rmhd.url}`)
    }
  }
}
</script>
<style lang="stylus" scoped>
.div_box{
    padding 0.3rem
    .imgbox{
      width 70%
      margin 30% auto
    }
    .li_box{
      position relative
      .li_img{
        // box-shadow: 0 20px 20px #e1e1e1;
        // box-shadow: 0 20px #e1e1e1,10px 0px #e1e1e1,-10px 0px #e1e1e1,0px -10px #e1e1e1;//四面都有阴影
        box-shadow: 0 20px 20px #e1e1e1,7px 0px 70px #e1e1e1,-7px 0px 7px #e1e1e1;
        border-radius: 0.2rem
        margin-bottom 40px
        max-height 3.5rem
      }
      .img_text{
        background: #000
        position: absolute
        height: 0.8rem
        width: 100%
        top: 2.5rem
        z-index: 999!important
        opacity: 0.6
        color: #ffffff
        text-align: center
        line-height: 0.8rem
      }
    }
}
</style>
