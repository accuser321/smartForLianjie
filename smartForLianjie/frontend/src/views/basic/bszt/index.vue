<!--
 * @Author: 刘格优
 * @Date: 2019-12-27 15:37:06
 * @LastEditors  : 刘格优
 * @LastEditTime : 2020-01-21 15:54:13
 -->

<template>
  <div class="container">
    <div class="contentbox">
      <div class="top">

      </div>
      <div class="content">
        <div class="toptitle">
          <span> 保司投保链接</span>
        </div>
        <ul>
          <li v-for="(item,index) in lists"
              :key="index"
              @click="totb(item.url)">
            <img :src="item.pic"
                 alt=""
                 class="img">
            <span class="wordbox">
              <span class="title">{{item.title}}</span>
              <span class="tbbtn">投保</span>
            </span>

          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      lists: [
        {
          pic: require('@/assets/dr/bn.png'),
          title: '新百年微投',
          url: 'https://bnjy.aeonlife.com.cn/frontser/bnwt/#/Login?branchType=8'
        },
        {
          pic: require('@/assets/dr/bn.png'),
          title: '百年微投',
          url: 'https://appservice.aeonlife.com.cn/bnwt/page/policy/login.html?agentCom=806'
        },
        {
          pic: require('@/assets/dr/ta.png'),
          title: '天安微投',
          url: 'http://wechatshop.tianan-life.com/bind/bindinput.jsp'
        },
        {
          pic: require('@/assets/dr/zy.png'),
          title: '中英微投',
          url: 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6cf55347da312f1a&redirect_uri=http%3A%2F%2Fxszc.aviva-cofco.com.cn%2Fwechatplatform%2Fservices%2Fagent%2Fquery%2FagentByWechatBase%3Fmodule%3Daclife%26appid%3Dwx6cf55347da312f1a%26redirect_url%3Dhttps%3A%2F%2Fproposal.aviva-cofco.com.cn%2Faclife%2Fapi%2Fcommon%2FtoIndex&response_type=code&scope=snsapi_base&state=abc251cc76a1f73f447259061be6b91ce0cdf3be510dcd7048d4fb0f5c1752b1&connect_redirect=1#wechat_redirect'
        }
      ]
    }
  },

  components: {},

  computed: {},

  methods: {
    totb (url) {
      window.location.href = url
    }
  }
}

</script>
<style scoped>
.container .contentbox {
  height: 100vh;
  background: #f7f7f7;
}
.top {
  height: 30%;
  background: url("~@/assets/dr/bszt.png");
  background-repeat: no-repeat;
  background-size: 100% 100%;
}
.img {
  width: 1.3rem;
  height: 1.3rem;
}
.content {
  width: 92%;
  margin: 0 auto;
  position: relative;
  bottom: 11%;
}
.content ul {
  background-color: white;
  padding-bottom: 1rem;
}
.content li {
  display: flex;
  align-items: center;
  align-items: center;
  height: 2.2rem;
  padding: 0 0.7rem;
}
.title {
  font-size: 0.4rem;
  margin-left: 0.4rem;
  width: 60%;
}
.tbbtn {
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  width: 1.8rem;
  height: 0.8rem;
  line-height: 1rem;
  background-color: #039e89;
  color: white;
  border-radius: 0.1rem;
}
.wordbox {
  border-bottom: 1px solid #eaeaea;
  display: flex;
  justify-content: center;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  height: 100%;
}
.toptitle {
  background: rgba(11, 169, 147, 0.5);
  /* color: white; */
  padding: 0.2rem 0.7rem 0.4rem;
  border-radius: 0.2rem 0.2rem 0 0;
}
.toptitle span {
  font-size: 0.4rem;
}
</style>
