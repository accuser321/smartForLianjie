<!--
 * @Author: 王鹏
 * @Date: 2020-03-18 10:35:42
 * @LastEditors: 王广婷
 * @LastEditTime: 2020-03-26 09:42:43
 -->
<template>
  <div class="conbox">
    <div class="dialogbox">
      <img src="@/assets/nh/rsimg.png" alt="">
      <div class="closeicon"
           @click="back"><img src="@/assets/nh/rsclose.png"
             alt=""></div>
      <div class="textbox">
        <p class="texttitle">职业认证</p>
        <p class="textcolor">您还没有完成执业资质认证哦</p>
        <p class="textcolor">认证后即可开始展业</p>
      </div>
      <div class="bottomtext">
        <md-button class="btn"
                   type="primary"
                   @click="gorz">现在认证</md-button>
      </div>
    </div>
  </div>
</template>
<script>
// import { showEmpInfo } from '@/api/agent/agent'
export default {
  data () {
    return {
      checkRytype: '',
      gotype: 'renzheng',
      applynum: ''
    }
  },
  created () {
    this.getemptype()
  },
  methods: {
    getemptype () {
      showEmpInfo().then((res) => {
        this.checkRytype = res.data.data.salesmanInfo.checkRytype
        this.applynum = res.data.data.salesmanInfo.applynum
        if (this.checkRytype == '3' || this.checkRytype == '4') {
          this.gotype = 'renzheng'
        } else if (this.checkRytype == '7') {
          this.gotype = 'study'
        } else {
          this.gotype = 'showinfo'
        }
      })
    },
    gorz () {
      if (this.gotype == 'renzheng') {
        this.$router.push('/professional')
      } else if (this.gotype == 'study') {
        this.$router.push(`/fundamentals?applynum=${this.applynum}`)
      } else {
        this.$router.push('/dzincome')
      }
    },
    back () {
      window.history.go(-1)
    }
  }
}
</script>
<style lang="stylus">
.conbox {
  width: 100%;
  height: 100%;
  background: #331f21;

  .dialogbox {
    width: 80%;
    margin: auto;
    position: relative;
    top: 20%;

    .closeicon {
      position: absolute;
      width: 60px;
      height: 60px;
      right: 0.3rem;
      top: 1.6rem;
    }

    .textbox{
      text-align center
      position: absolute;
      top: 45%;
      width: 100%;
      .texttitle{
        font-weight bold
        font-size 0.55rem
        margin-bottom 0.2rem
        color #2F2F2F
      }
      .textcolor{
        color #A0A0A0
      }
    }

    .bottomtext {
      color: #FFF3F6;
      font-size: 0.36rem;
      letter-spacing: 0.03rem;
      position: absolute;
      top: 80%;
      width: 100%;

      .btn {
        width: 80%;
        margin: auto;
        border-radius: 0.15rem;
      }
    }
  }
}
</style>
