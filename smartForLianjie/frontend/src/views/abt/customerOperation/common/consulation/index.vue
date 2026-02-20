<!--
 * @Author: 刘格优
 * @Date: 2019-11-29 18:24:38
 * @LastEditors: 刘格优
 * @LastEditTime: 2019-12-06 16:18:29
 -->

<template>
  <div class="tozxzx">
    <img :src="getStorage('ossurl','')+getStorage('imgs',{}).zxicon"
         alt=""
         @click="tozxzx" />
    <div class="wdread"
         v-if="unread != 0">{{ unread }}</div>
  </div>
</template>

<script>
// import { getKJChatNum } from '@/api/abt/customerOperation/zxzx/index'
import { getStorage } from '@/lib/util'
export default {
  props: {
    empno: String,
    user: Object
  },
  data () {
    return {
      unread: '',
      isself: false
    }
  },
  created () {
    this.getUnreadnum()
    if (this.empno == this.user.empno) {
      this.isself = true
    } else {
      this.isself = false
    }
  },
  components: {},

  computed: {},

  methods: {
    getUnreadnum () {
      let data = { userid: this.user.userid, empno: this.empno, type: this.isself ? '2' : '1' }
      // getKJChatNum(data).then(res => {
      //   this.unread = res.data.data
      // })
    },
    tozxzx () {
      this.$emit('tozxzx')
    }
  }
}
</script>
<style scoped>
.tozxzx {
  margin-right: -0.3rem;
  position: fixed;
  z-index: 10;
  bottom: 3.5rem;
  right: 0.5rem;
}
.tozxzx img {
  width: 12vw;
}
.wdread {
  position: absolute;
  top: -0.2rem;
  right: -0.1rem;
  background: red;
  padding: 0.02rem;
  border-radius: 50%;
  width: 0.6rem;
  height: 0.6rem;
  color: white;
  text-align: center;
  line-height: 0.6rem;
  font-size: 0.2rem;
  overflow: hidden;
}
</style>
