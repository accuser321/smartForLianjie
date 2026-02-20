<!--
 * @Author: 李波
 * @Date: 2020-02-11 17:57:52
 * @LastEditors: 王鹏
 * @LastEditTime: 2020-03-13 18:39:16
 -->
<template>
  <div class="container">
    <div class="tab_bottom">
      <md-tab-bar v-model="current"
                  :items="items"
                  :has-ink="false"
                  @change="$router.push($event.path)">
        <template slot="item"
                  slot-scope="{ item }">
          <div class="custom-item">
            <div class="icon">
              <img :src="current==item.name?item.src[0]:item.src[1]"
                   alt="">
            </div>
            <div class="text">
              <span v-text="item.label"></span>
            </div>
          </div>
        </template>
      </md-tab-bar>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      current: '/',
      items: [{ name: '/', label: '首页', src: [require('@/assets/nh/shouye-10.png'), require('@/assets/nh/shouye-14.png')], disabled: false, path: '/' },
      { name: '/product', label: '产品', src: [require('@/assets/nh/shouye-11.png'), require('@/assets/nh/shouye-15.png')], disabled: false, path: '/product' },
      { name: '/study', label: '学习', src: [require('@/assets/nh/shouye-12.png'), require('@/assets/nh/shouye-16.png')], disabled: false, path: '/study' },
      { name: '/nhcenter', label: '我的', src: [require('@/assets/nh/shouye-13.png'), require('@/assets/nh/shouye-17.png')], disabled: false, path: '/nhcenter' }]
    }
  },
  created () {
    this.current = this.$route.path
    this.items[2].disabled = true
  },
  methods: {}
}
</script>

<style lang="stylus" scoped>
.custom-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  flex: 1;
  padding-top: 20px;

  .icon {
    width: 0.6rem;
    height: 0.6rem;
    margin-bottom: 15px;
  }

  .text span {
    font-size: 30px;
  }
}

.tab_bottom {
  width: 100%;
  position: fixed;
  bottom: 0px;
  z-index: 999;
}
</style>
