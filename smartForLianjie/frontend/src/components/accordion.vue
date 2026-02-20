<!--
 * @Descripttion:
 * @version:
 * @Author: 贾峰
 * @Date: 2019-11-09 17:49:58
 * @LastEditors  : 贾峰
 * @LastEditTime : 2020-01-14 21:10:35
 -->
<template>
    <div class="containerc">
        <div class="title" @click="toggle">
            <slot></slot>
            <span v-if="ismore" class="iconfont iconmoreinfo-copy" :class="{rotate:isUnfold}"></span>
        </div>
        <div class="content" v-show="ismore" ref="show">
            <slot name="content"></slot>
        </div>
    </div>
</template>
<script>
export default {
  name: 'accordion',
  props: {
    ismore: {
      type: Boolean,
      default: true
    },
    index: Number
  },
  data () {
    return {
      isUnfold: true,
      hg: 0,
      issave: false
    }
  },
  mounted () {
    this.$nextTick(function () {
      this.hg = this.$refs.show.offsetHeight
      if (this.index !== 0) {
        this.toggle()
      }
    })
  },
  methods: {
    toggle () {
      // eslint-disable-next-line no-unused-expressions
      this.issave ? '' : this.hg = this.$refs.show.offsetHeight
      this.issave = true
      let wrapper = this.$refs.show.style
      this.isUnfold = !this.isUnfold
      if (this.isUnfold) {
        // wrapper.height = '0px'
        wrapper.height = this.hg + 'px'
      } else {
        wrapper.height = '0px'
        // wrapper.height = this.hg + 'px'
      }
    }
  }
}
</script>

<style scoped>
   .title{
       /* line-height:60px; */
       display: flex;
       justify-content: space-between;
       align-items: center;
       position: relative;
       font-size:30px;
   }
   .rotate::before{
        transform: rotate(-90deg);
   }
   .iconmoreinfo-copy{
       position:absolute;
       top:54%;
       transform:translateY(-50%);
       right:20px;
       color:#BBBBBB;
       font-size:48px;
   }
  .iconmoreinfo-copy::before{
      display:inline-block;
      transition:0.3s;
  }
  .content{
      transition:height .3s ease-in-out;
      will-change:height;
      overflow: hidden;
  }
</style>
