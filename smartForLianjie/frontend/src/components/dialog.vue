<template>
  <div>
    <transition enter-active-class="fade fadeIn"
                leave-active-class="fade fadeOut">
      <div class="mask"
           @click="maskClose"
           v-show="show"></div>
    </transition>
    <div class="w-dialog"
         v-show="show">
      <div class="close"
           @click="close"><span class="iconfont iconclose closebt"></span></div>
      <div class="header">
        <h2 class="title">{{title}}</h2>
      </div>
      <div class="body" :style="{'height': (istext ? '80%' :'100%')}">
        <div class="question"
             v-if="istext">
          <textarea v-model="text"
                    placeholder="请输入遇到的问题或建议(小于200字)..."
                    maxlength="200"></textarea>
        </div>
        <slot v-else />
      </div>
      <div class="footer"
           v-if="istext">
        <md-button size="large"
                   @click="submit"
                   type="primary">提交</md-button>
      </div>
    </div>
  </div>
</template>

<script>
import { stopScroll, canScroll, ModalHelper } from '@/lib/util'

export default {
  props: {
    title: {
      type: String,
      default: ''
    },
    istext: {
      type: Boolean,
      default: true
    },
    show: {
      type: Boolean,
      default: false
    },
    overlay: {
      type: Boolean,
      default: true
    },
    text: {
      type: String,
      default: ''
    }
  },
  methods: {
    submit () {
      this.$emit('confirm', this.text)
      this.close()
    },
    close () {
      this.$emit('update:show', false)
    },
    maskClose () {
      if (this.overlay) {
        this.$emit('update:show', false)
      }
    }
  },
  watch: {
    show (val) {
      if (val) {
        // stopScroll()
        // ModalHelper.afterOpen()
      } else {
        // canScroll()
        // ModalHelper.beforeClose()
      }
    }
  }
}
</script>

<style scoped>
.question {
  width: 100%;
  height: 100%;
  border-top: 1px solid #ececec;
  border-bottom: 1px solid #ececec;
}
.question textarea{
  font-size: 38px;
  background-color: #F5F4F7;
  border-radius: 10px
}
.footer {
  margin-top: 20px;
  padding: 0 40px;
}
.footer button {
  background: color-primary;
}
textarea {
  padding: 20px;
  width: 100%;
  height: 100%;
}
.body {
  padding: 30px 0 0;
  height: 96%;
  overflow-y: auto;
  overflow-x: hidden
}
.close {
  position: absolute;
  bottom: -140px;
  right: 46%;
  color: #fff;
}
.closebt{
  font-size: 80px
}
.header {
  margin: 0 48px;
  position: relative;
  text-align: center;
}
.header::before {
  content: "";
  border-bottom: 1px solid #d2d2d2;
  position: absolute;
  width: 100%;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  z-index: -1;
}
.title {
  line-height: 40px;
  text-align: center;
  display: inline-block;
  background: #fff;
  padding:0 20px;
  font-size: 40px
}
h2 {
  font-size: 36px;
}
.mask {
  position: fixed;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
  left: 0;
  top: 0;
  z-index: 996;
}
.w-dialog {
  position: absolute;
  width: 86vw;
  height: 60vh;
  background: #fff;
  border-radius: 20px;
  top: 45%;
  left: 50%;
  transform: translate3d(-50%, -50%, 0);
  z-index: 997;
  padding: 68px 28px;
  backface-visibility: hidden;
}
.fade {
  transition: opacity 0.8s;
}
.fadeIn {
  opacity: 1;
}
.fadeOut {
  opacity: 0;
}
</style>
