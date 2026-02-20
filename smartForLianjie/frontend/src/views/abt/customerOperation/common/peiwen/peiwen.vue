<!--
 * @Author: 刘格优
 * @Date: 2019-11-09 19:41:34
 * @LastEditors: 刘格优
 * @LastEditTime: 2020-03-12 15:00:07
 -->
<template>
  <div>
    <div class="box"
         @click="choosepw">
      <div class="tips">
        <img src="@/assets/abt/img/pw.png"
             alt=""
             class="img" />
        配文
      </div>
    </div>
    <md-popup v-model="readgo"
              position="bottom">
      <div class="popbox">
        <div class="text">
          <div class="top">
            <span class="pw">朋友圈配文</span>
            <span class="close"
                  @click="closepw">关闭</span>
          </div>
          <p>{{ libdesc }}</p>
          <div class="button"
               @click="onCopywx(libdesc)">复制</div>
        </div>
      </div>
    </md-popup>
  </div>
</template>

<script>
import Clipboard from 'clipboard'
import { Toast } from 'mand-mobile'
export default {
  props: {
    libdesc: String
  },
  data () {
    return {
      readgo: false
    }
  },

  methods: {
    onCopywx (mes) {
      var clipboard = new Clipboard('.button', {
        text: function () {
          return mes
        }
      })
      clipboard.on('success', e => {
        Toast.succeed('复制成功')
      })
    },
    choosepw () {
      this.readgo = true
    },
    closepw () {
      this.readgo = false
    }
  }
}
</script>
<style scoped lang="stylus">
.img {
  width: 0.6rem;
  height: 0.6rem;
  position: absolute;
  left: 0.3rem;
  top: 0.15rem;
}

.popbox {
  height: 9rem;
  background-color: #fff;
  overflow-y: auto;
  padding: 0 20px;
}

.button {
  width: 80%;
  height: 1.3rem;
  text-align: center;
  line-height: 1.3rem;
  font-size: 0.5rem;
  background: color-primary;
  color: #fff;
  font-weight: 600;
  position: absolute;
  bottom: 0.4rem;
  left: 10%;
  border-radius: 0.15rem;
}

.text p {
  margin-top: 0.5rem;
  font-size: 0.45rem;
}

.top {
  height: 1.5rem;
  line-height: 1.5rem;
  font-size: 18px;
  color: black;
  text-align: center;
  border-bottom: 1px solid #efefef;
}

.top span {
  display: inline-block;
  font-size: 0.47rem;
}

.close {
  float: right;
  padding-right: 10px;
  font-size: 18px;
  color: color-primary;
}

.pw {
  position: relative;
  left: 20px;
}
</style>
