<template>
  <div class="container">
    <div class="logo">
      <img src="@/assets/nhlogin/nhlogo.png"
           alt />
    </div>
    <div v-if="httptype=='qd'"
         class="loginform">
      <md-field>
        <md-input-item type="text"
                       v-model="phoneorempno"
                       placeholder="请输入工号"
                       @change="phoneorempno=$transform.transform(phoneorempno)"></md-input-item>
        <md-input-item v-model="password"
                       type="password"
                       placeholder="请输入密码"></md-input-item>
        <md-button :loading="btnloading"
                   @click="nhlogin"
                   :inactive="phoneorempno&&password ? false : true">登录</md-button>
      </md-field>
    </div>
    <div v-else
         class="loginform">
      <md-field>
        <md-input-item v-if="page === 'login'"
                       type="text"
                       v-model="phoneorempno"
                       placeholder="手机号或者工号"
                       @change="phoneorempno=$transform.transform(phoneorempno)"></md-input-item>
        <md-input-item v-if="page === 'yzm' || page === 'register'"
                       type="phone"
                       v-model="phone"
                       placeholder="手机号"></md-input-item>
        <md-input-item v-if="page === 'login'"
                       v-model="password"
                       type="password"
                       placeholder="密码"></md-input-item>
        <div class="yzm"
             v-if="page === 'yzm' || page === 'register'">
          <md-input-item type="text"
                         maxlength="4"
                         v-model="yzm"
                         placeholder="短信验证码"> </md-input-item>
          <div class="yambtn"
               :style="yzmstyle"
               @click="getyzm"
               id="getyzm1">{{djsbt?('('+ second+')'+yzmbtext):yzmbtext}}</div>
        </div>
        <md-button :loading="btnloading"
                   @click="nhlogin"
                   :inactive="((page === 'login' && (phoneorempno == '' || password == '' || !isxz)) ? true : false)||(((page === 'yzm' || page === 'register') && (phone == '' || yzm == '' || !isxz)) ? true : false)">{{btntext}}</md-button>
        <div class="switch">
          <a class="switchlogin"
             @click="changetoyzm">{{yzmandpasswordtext}}</a>
          <a class="switchlogin"
             v-if="page !== 'register'"
             @click="changetoregister">注册</a>
        </div>
        <div class="xyword">
        <p class="frontpic">
          <img src="@/assets/nh/zq.png"
               alt=""
               v-if="!isxz"
               @click="isxz = !isxz">
          <img src="@/assets/nh/zh.png"
               alt=""
               v-else
               @click="isxz = !isxz">
          点击注册同意<span @click="toshow('https://saasms.oss-cn-beijing.aliyuncs.com/root/Sys_Source/nhpdf/%E4%BC%9A%E5%91%98%E7%AE%A1%E7%90%86%E5%88%B6%E5%BA%A6.pdf')">【汇立天下会员服务管理制度】</span>
        <span @click="toshow('https://saasms.oss-cn-beijing.aliyuncs.com/root/Sys_Source/nhpdf/%E4%BC%9A%E5%91%98%E6%9C%8D%E5%8A%A1%E5%8D%8F%E8%AE%AE.pdf')">【汇立天下会员服务协议】</span>
          <span class="zuihou"
                @click="toshow('https://saasms.oss-cn-beijing.aliyuncs.com/root/Sys_Source/nhpdf/%E9%9A%90%E7%A7%81%E6%94%BF%E7%AD%96%E5%A3%B0%E6%98%8E.pdf')">【隐私政策声明】</span>
        </p>
      </div>
      </md-field>
    </div>
    <md-landscape v-model="showFullScreen"
                  full-screen
                  class="landscape">
      <div class="pdf">
        <div>
          <pdf v-for="i in numPages"
               :key="i"
               :page="i"
               :src="filepath"
               style="width: 100%; height: auto;"
               @num-pages="pageCount=$event"></pdf>
        </div>
      </div>

    </md-landscape>
  </div>
</template>
<script>
import { Toast } from 'mand-mobile'
import { getregisteryzm, register, getInit, getmsgloginyzm, yzmlogin, login , getUserInfo } from '@/api/basic'
import { setStorage, getStorage } from '@/lib/util'
import pdf from 'vue-pdf'
export default {
  components: {
    pdf,
  },
  data () {
    return {
      showFullScreen: false,
      showFullScreen: false,
      filepath: '',
      pageCount: 0,
      numPages: 0,
      isxz: false,
      phone: '',
      phoneorempno: '',
      password: '',
      yzm: '',
      page: 'login',
      yzmbtext: '获取验证码',
      second: 59,
      djsbt: false,
      btntext: '登录',
      yzmandpasswordtext: '切换验证码登录',
      timer: null,
      yzmstyle: '',
      btnloading: false,
      httptype: ''
    }
  },
  created () {
    console.log(this.$transform.transform('ertrr'))
    console.log(window.location.hostname.split('.')[0])
    this.httptype = window.location.hostname.split('.')[0] //获取域名
  },
  methods: {
    getyzm () {
      if(!this.isxz){
        Toast.info('请勾选同意相关协议')
      }else{
        if (this.phone === '') {
        Toast.info('请填写手机号')
      } else {
        const PHONE = { phone: this.phone }
        const GET_YZM = {
          'register': () => getregisteryzm(PHONE),  //验证码注册
          'yzm': () => getmsgloginyzm(PHONE)
        }
        GET_YZM[this.page]().then(()=>{
          this.djsbt = true
          this.yzmbtext = '秒后重试'
          this.yzmstyle = 'background-color:#C1BEC1;color:white;pointer-events:none'
          this.timer = setInterval(() => {
          this.second -= 1
          if (this.second === 0) {
             this.changeloginmethod()
          }
         }, 1000)
        })
      }
      }
    },
    changetoyzm () {
      this.btntext = '登录'
      if (this.yzmandpasswordtext === '切换密码登录') {
        this.yzmandpasswordtext = '切换验证码登录'
        this.changeloginmethod()
        this.page = 'login'
      } else {
        this.changeloginmethod()
        this.page = 'yzm'
        this.yzmandpasswordtext = '切换密码登录'
      }
    },
    changetoregister () {
      this.page = 'register'
      this.btntext = '注册'
      this.changeloginmethod()
    },
    nhlogin () {
      const form_list = { phone: this.phone, code: this.yzm }
      const form_login = { empno: this.phoneorempno, password: this.password, type: 'N' } //2020-4.10
      // const form_login = { empno: this.phoneorempno, password: this.password, type: 'N' } //2020-4.10
      const LOGIN_AND_REGISTER = {
        'register': () => register(form_list), //注册
        'yzm': () => yzmlogin(form_list),   //验证码登录
        'login': () => login(form_login)    //登录
      }
      this.btnloading = true
      LOGIN_AND_REGISTER[this.page]().then(res => {
        this.btnloading = false
        setStorage('ossurl','https://hxywtest.oss-cn-shenzhen.aliyuncs.com/');
        getUserInfo().then(res=>{
          console.log("数据")
          console.log(res)
           console.log(res.data.data.data.user)
            setStorage('u_s', res.data.data.data.user)
            this.$router.push({ path: '/' })
        })
        // getInit().then(res => {      //获取菜单
        //   let { ketel, kfqrcode } = res.data.data
        //   setStorage('u_s', res.data.data.user)
        //   setStorage('m_l', res.data.data.menulist)
        //   setStorage('ossurl', res.data.data.ossurl)
        //   setStorage('comname', res.data.data.comname)
        //   setStorage('banner', res.data.data.banner)
        //   setStorage('bar', res.data.data.bar)
        //   setStorage('kfInfo', { ketel, kfqrcode })
        //   setStorage('qrcode', res.data.data.qrcode)
        //   setStorage('website', res.data.data.website)
        //   setStorage('flag', res.data.data.user.promotionprice)
        //   console.log(res.data.data);
        //   debugger;
        //   this.httptype == 'qd' ? this.$router.push({ path: '/main' }) : this.$router.push({ path: '/' })
        // //   console.log("路由");
        // //   console.log(this.$router);
        // //  debugger;
        // })
        //  this.httptype == 'qd' ? this.$router.push({ path: '/main' }) : this.$router.push({ path: '/' })
     
        //  debugger;
       
      }).finally(() => { this.btnloading = false })
    },
    changeloginmethod () {
      this.djsbt = false
      clearInterval(this.timer)
      this.yzmbtext = '获取验证码'
      this.second = 59
      this.phone = ''
      this.password = ''
      this.yzm = ''
      this.yzmstyle =
        'background-color:#F4F1F2;color:rgba(80,125,175,1);pointer-events:auto'
    },
    toshow (file) {
      this.numPages = 0
      this.$nextTick(() => {
        setTimeout(() => {
          this.filepath = pdf.createLoadingTask(file)
          this.filepath.then(pdf => {
            console.log(pdf)
            this.numPages = pdf.numPages
          })
          this.showFullScreen = true
        }, 500);
      })
    },
  }
}
</script>
<style lang="stylus" scoped>
.container {
  width: 100vw;
  height: 100vh;
  background: white;
}

.logo {
  height: 28vh;
  display: flex;
  background-color: white;
  justify-content: center;
  align-items: center;

  img {
    width: 44vw;
    height: 11.55vw;
    margin-top: 50px;
  }
}

.switch {
  display: flex;
  justify-content: space-between;
}

.switchlogin {
  font-family: PingFang SC;
  color: rgba(80, 125, 175, 1);
  font-size: 38px;
}

/deep/ .md-input-item-input {
  background: #f2f2f2;
  font-size: 40px;
  margin: 36px 0;
  padding: 0 40px;
  border-radius: 12px;
}

/deep/ .md-field-item-content::before {
  width: 0;
}

/deep/ .md-button {
  margin: 36px 0;
  background-color: #BD1A23;
  height: 120px;
  border-radius: 12px;
  color: white;
}

/* 验证码 */
/deep/ .yzm {
  display: flex;
  align-items: center;

  .md-field-item {
    position: relative;
    width: 75%;

    .md-input-item-input {
      border-radius: 12px 0 0 12px;
    }
  }

  .yambtn {
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 30px;
    width: 25%;
    height: 1rem;
    background-color: pink;
    border-radius: 0 12px 12px 0;
    background: #F4F1F2;
    color: rgba(80, 125, 175, 1);
    border-left: 1px solid lightgray;
  }
}
/* 协议 */
.xyword{
  display flex
  justify-content center
  margin-top 50px
  p{
    font-size 36px
    color: rgba(80, 125, 175, 1)
  }
  .frontpic{
    img{
      width 36px
      height 36px
    }
    span{
     font-size 36px
     color: rgba(80, 125, 175, 1)
    }
  }
}
</style>
