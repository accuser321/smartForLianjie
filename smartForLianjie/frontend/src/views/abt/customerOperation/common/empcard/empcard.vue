<!--
 * @Author: 刘格优
 * @Date: 2019-11-09 16:31:10
 * @LastEditors: 刘格优
 * @LastEditTime: 2020-03-05 14:37:30
 -->
<template>
  <div class="mpdiv">
    <div class="mpbox">
      <img class="head_pic"
           :src="userinfo.headimg"
           alt=""
           v-if="userinfo.headimg" />
      <img src="@/assets/abt/img/callme.png"
           alt=""
           class="callme" />
      <div class="card_company">
        <p class="username"
           v-if="userinfo.cardempname">
          {{ userinfo.cardempname }}
        </p>
      </div>
      <div class="card_info">
        <div class="left_info">
          <div class="card_item"
               v-if="userinfo.cardmobile">
            <div class="info_item">电话：{{ userinfo.cardmobile }}</div>
          </div>
          <div class="card_item"
               v-if="userinfo.email">
            <div class="info_item">邮箱：{{ userinfo.email }}</div>
          </div>
        </div>
        <div class="connect">
          <a :href="`tel:'${userinfo.cardmobile}`"
             v-if="userinfo.cardmobile"
             class="icon">
            <img src="@/assets/abt/img/tophone.png"
                 alt="" />
          </a>
          <div class="tabli wxtabli icon"
               style="display:inline-block"
               v-if="userinfo.wxnumber">
            <img src="@/assets/abt/img/wechat.png"
                 alt=""
                 @click="onCopywx(userinfo.wxnumber)" />
          </div>
        </div>
      </div>
      <div class="codeDes">
        {{ userinfo.pdesc ? userinfo.pdesc : '这个人很懒，什么都没留下~' }}
      </div>
      <div class="codePic">
        <img :src="'data:image/png;base64,'+userinfo.mpurl"
             alt="" />
        <div class="codeTitle">
          点击识别二维码，加入我们吧！
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Clipboard from 'clipboard'
import { Toast } from 'mand-mobile'
export default {
  props: {
    userinfo: Object
  },
  data () {
    return {
      // userinfo: {
      //   cardempname: '刘格优',
      //   cardmobile: '18238622535',
      //   wxnumber: '18238622535',
      //   email: '18238622535',
      //   pdesc: '从事保险行业多年，历经多次客户理赔，我越来越认识到自己背后的那份责任。如果您选择信任我，我将以绝对的专业和真诚，守护您和家人的美好未来！',
      //   tx: 'http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKzr9iaXeQVuuSVX2KfcZjicUpRXicNABDfjE7NobISdMSMMatTXkXvibsKoWBiaAliaU0xmj88Lic9LSGvQ/132'
      // }
    }
  },

  components: {},

  computed: {},

  methods: {
    onCopywx (mes) {
      var clipboard = new Clipboard('.tabli', {
        text: function () {
          return mes
        }
      })
      clipboard.on('success', e => {
        Toast.succeed('复制成功')
      })
    }
  }
}
</script>
<style scoped lang="stylus">
@import './index.styl';
</style>
