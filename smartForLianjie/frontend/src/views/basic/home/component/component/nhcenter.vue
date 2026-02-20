<!--
 * @Author: 刘格优
 * @Date: 2020-02-26 15:28:16
 * @LastEditors: 王鹏
 * @LastEditTime: 2020-03-13 18:37:07
 -->

<template>
  <div @click="isMember">
    <div class="wd_title">
      <div class="wdt_top">
        <div class="wdt_left">
          <div class="l_img">
            <img :src="form.headimg?form.headimg: this.user.headimg"
                alt="">
          </div>
          <div class="l_msg">
            <div class="lm_top">
              <div class="lm_name">
                <span>{{form.empname?form.empname: this.user.empname}}</span>
              </div>
              <div class="top_authentication">
                <span>{{ form.zyzstatus == '0' ? '已认证':'未认证'}}</span>
              </div>
            </div>
            <div class="lm_bottom" v-show="this.user.rytype == 'W'">
              <span>ID:{{form.empno}}</span>
            </div>
          </div>
        </div>
        <div class="wdt_right" @click="toSetup">
          <img src="@/assets/nh/nhwx_sz.png"
              alt="">
        </div>
      </div>
      <div class="wdt_bottom" @click="toZz">
        <div class="bxzz_left">
          <div class="bxzz_img">
            <img src="@/assets/nh/bxzz.png" alt="">
          </div>
          <div class="bxzz_text">
            <span>保险执业资质</span>
          </div>
        </div>
        <div class="bxzz_right">
          <img  v-show="form.zyzstatus == '0'" src="@/assets/nh/zyzIcon.png" alt="">
        </div>
      </div>
    </div>
    <div class="wd_money">
      <div class="money_top">
        <div class="mt_item" @click="toPolicyDetails">
          <div class="item_num">
            <span class="red">{{form.wjs?form.wjs: '0.00'}}</span>
          </div>
          <div class="item_text">
            <span>未结算(元)</span>
          </div>
        </div>
        <div class="mt_item" @click="toPolicyDetails">
          <div class="item_num">
            <span>{{form.jsz?form.jsz: '0.00'}}</span>
          </div>
          <div class="item_text">
            <span>结算中(元)</span>
          </div>
        </div>
        <div class="mt_item" @click="toPolicyDetails">
          <div class="item_num">
            <span>{{form.yjs?form.yjs: '0.00'}}</span>
          </div>
          <div class="item_text">
            <span>可提现(元)</span>
          </div>
        </div>
        <div class="mt_item bjRight"  @click="toCashWithdrawal">
          <div class="item_num">
            <img src="@/assets/nh/tx.png" alt="">
          </div>
          <div class="item_text">
            <span>提现</span>
          </div>
        </div>
      </div>
      <div class="money_bottom">
        <div class="mb_item" @click="toMyScore">
          <div class="integral">
            <span>积分</span>
          </div>
          <div class="integralNum">
            <span>{{form.jfnum?form.jfnum: '0' }}</span>
          </div>
          <div>
            <md-icon name="arrow-right" size="lg" class="jfIcon"></md-icon>
          </div>
        </div>
        <div class="mb_item" @click="toYhqshow">
          <div class="integral">
            <span>优惠券</span>
          </div>
          <div class="integralNum">
            <span>{{form.yhnum?form.yhnum: '0'}}</span>
          </div>
          <div>
            <md-icon name="arrow-right" size="lg" class="jfIcon"></md-icon>
          </div>
        </div>
      </div>
    </div>
    <div class="wd_mine">
      <div class="mine_item" @click="toOrderform">
        <div>
          <img src="@/assets/nh/wddd.png" class="mineImg" alt="">
        </div>
        <div class="mineText">
          <span>我的订单</span>
        </div>
      </div>
      <div class="mine_item">
        <div>
          <img src="@/assets/nh/wdsc.png" class="mineImg" alt="">
        </div>
        <div class="mineText">
          <span>我的收藏</span>
        </div>
      </div>
      <div class="mine_item" @click="toMyTeam">
        <div>
          <img src="@/assets/nh/xbgl.png" class="mineImg" alt="">
        </div>
        <div class="mineText">
          <span>我的团队</span>
        </div>
      </div>
      <div class="mine_item" @click="toAchievement">
        <div>
          <img src="@/assets/nh/wdtd.png" class="mineImg" alt="">
        </div>
        <div class="mineText">
          <span>团队业绩</span>
        </div>
      </div>
    </div>
    <div class="wd_manage">
      <div class="manage_item" @click="$router.push('/newpolicy')">
        <div>
          <img src="@/assets/nh/bdgl.png" class="manageImg" alt="">
        </div>
        <div class="manageText"><span>新保保单</span></div>
      </div>
      <div class="manage_item" @click="$router.push('/renewal')">
        <div>
          <img src="@/assets/nh/xqbd.png" class="manageImg" alt="">
        </div>
        <div class="manageText"><span>续期保单</span></div>
      </div>
    </div>
    <div class="wd_change">
      <div class="change_item" @click="toArtificialUnderwriting('rghb')">
        <div class="ci_left">
          <span>人工核保</span>
        </div>
        <div>
          <md-icon name="arrow-right" size="lg"></md-icon>
        </div>
      </div>
      <div class="change_item" @click="toCustomerserver">
        <div class="ci_left">
          <span>客服中心</span>
        </div>
        <div>
          <md-icon name="arrow-right" size="lg"></md-icon>
        </div>
      </div>
      <div class="change_item" @click="toAdvice">
        <div class="ci_left">
          <span>意见反馈</span>
        </div>
        <div>
          <md-icon name="arrow-right" size="lg"></md-icon>
        </div>
      </div>
      <div class="change_item" @click="toArtificialUnderwriting('lp')">
        <div class="ci_left">
          <span>理赔服务</span>
        </div>
        <div>
          <md-icon name="arrow-right" size="lg"></md-icon>
        </div>
      </div>
      <!-- <div class="change_item" @click="">
        <div class="ci_left">
          <span>保全指引</span>
        </div>
        <div>
          <md-icon name="arrow-right" size="lg"></md-icon>
        </div>
      </div> -->
      <div class="change_item" @click="toWebsite">
        <div class="ci_left">
          <span>关于我们</span>
        </div>
        <div>
          <md-icon name="arrow-right" size="lg"></md-icon>
        </div>
      </div>
    </div>
    <div v-show="showZz" class="showZyImg">
      <div class="zyImg">
        <div>
          <img src="@/assets/nh/zyzz.png" class="manageImg" alt="">
        </div>
        <div class="zzMsg">
          <div class="itemTitle">
            <span>执业资质信息</span>
            <div class="code">
              <!-- <span>NO12345678900987654321123456</span> -->
              <span v-show="showZzform.certno">NO.{{showZzform.certno}}</span>
            </div>
          </div>
          <!-- <div class="zzMsgItem">
            <div class="showText">执业证编号</div>
            <div class="showNum">{{showZzform.certno}}</div>
          </div> -->
          <div class="zzMsgItem">
            <div class="showText">有效起期</div>
            <div class="showNum">{{showZzform.begdate}}</div>
          </div>
          <div class="zzMsgItem">
            <div class="showText">有效终期</div>
            <div class="showNum">{{showZzform.enddate}}</div>
          </div>
        </div>
        <div class="closeImg">
          <img src="@/assets/nh/sctp.png" @click="showZz = false" alt="">
        </div>
      </div>
    </div>
    <div><nhwx></nhwx></div>
    <md-landscape v-model="showPic">
      <div class="s-box">
        <div class="s_img">
          <img src="@/assets/image/centerqdjf.png">
        </div>
        <div class="s-bom">
          <div class="s-title">
            <div class="s-t1"><span><i class="iconfont iconzuobianhenggang" /></span></div>
            <div class="s-t2">
              <p> 奖励 </p>
            </div>
            <div class="s-t3"><span><i class="iconfont iconzuobianhenggang" /></span></div>
          </div>
          <div class="s-res">
            <div class="s-r1"><span>积分 </span></div>
            <div class="s-r2"><span>+ {{jf}} </span></div>
          </div>
          <div class="s-btn">
            <button  @click="btn" class="btn">收下</button>
          </div>
        </div>
      </div>
    </md-landscape>
  </div>
</template>

<script>
import { getEmpnoPersonal, getZYZMessage, getJf, getJfSum } from '@/api/basic/index'
import { getStorage } from '@/lib/util'
import nhwx from '@/views/basic/home/component/nhwx'
export default {
  components: { nhwx },
  data () {
    return {
      user: {},
      form: {},
      showZz: false,
      showZzform: {
        certno: '',
        begdate: '',
        enddate: ''
      },
      jfdata: {},
      showPic: false,
      jf: 0,
      JFNUM: 0,
      getjfnum: {}
    }
  },
  created () {
    this.user = getStorage('u_s', '')
    console.log(this.user)
    if (this.user.rytype == 'W') {
      this.getData()
      this.getqdjf()
    }
  },
  methods: {
    isMember () {
      // if (this.user.rytype != 'W') {
      //   this.$router.push({ path: '/diaoauth' })
      // }
    },
    getData () {
      getEmpnoPersonal().then(res => {
        this.form = res.data.data
      })
    },
    toZz () {
      if (this.form.zyzstatus == '0' && this.user.rytype == 'W') {
        getZYZMessage({ empno: this.form.empno }).then(res => {
          this.showZzform = res.data.data
          this.showZz = true
        })
        // this.$router.push(`/practisingQualification?empno=${this.form.empno}`)
      } else {
        this.$router.push({ path: '/diaoauth' })
      }
    },
    toSetup () {
      if (this.user.rytype == 'W') {
        this.$router.push(`/setup?empname=${this.form.empname}&empno=${this.form.empno}&phone=${this.form.phone}&zyzstatus=${this.form.zyzstatus}&sfzstatus=${this.form.sfzstatus}`)
      } else {
        this.$router.push({ path: '/diaoauth' })
      }
    },
    toPolicyDetails () {
      if (this.user.rytype == 'W') {
        this.$router.push({ path: `/policyDetails` })
      } else {
        this.$router.push({ path: '/diaoauth' })
      }
    },
    toCashWithdrawal () {
      if (this.user.rytype == 'W') {
        this.$router.push({ path: `/cashWithdrawal?allMount=${this.form.yjs}` })
      } else {
        this.$router.push({ path: '/diaoauth' })
      }
    },
    toMyScore () {
      if (this.user.rytype == 'W') {
        this.$router.push({ path: '/myScore' })
      } else {
        this.$router.push({ path: '/diaoauth' })
      }
    },
    toYhqshow () {
      if (this.user.rytype == 'W') {
        this.$router.push({ path: '/yhqshow' })
      } else {
        this.$router.push({ path: '/diaoauth' })
      }
    },
    toAdvice () {
      if (this.user.rytype == 'W') {
        this.$router.push({ path: '/advice' })
      } else {
        this.$router.push({ path: '/diaoauth' })
      }
    },
    toCustomerserver () {
      // if (this.user.rytype == 'W') {
      this.$router.push({ path: '/customerserver' })
      // }
    },
    toWebsite () {
      // if (this.user.rytype == 'W') {
      // 微官网
      this.$router.push({ path: '/aboutUs' })
      // }
    },
    toOrderform () {
      if (this.user.rytype == 'W') {
        this.$router.push({ path: '/orderform' })
      } else {
        this.$router.push({ path: '/diaoauth' })
      }
    },
    toMyTeam () {
      if (this.user.rytype == 'W') {
        this.$router.push({ path: '/myTeam' })
      } else {
        this.$router.push({ path: '/diaoauth' })
      }
    },
    toAchievement () {
      if (this.user.rytype == 'W') {
        this.$router.push({ path: '/businesstrack' })
      } else {
        this.$router.push({ path: '/diaoauth' })
      }
    },
    toArtificialUnderwriting (val) {
      if (this.user.rytype == 'W') {
        this.$router.push(`/artificialUnderwriting?type=${val}`)
      } else {
        this.$router.push({ path: '/diaoauth' })
      }
    },
    getqdjf () {
      getJf().then(res => {
        this.jfdata = res.data.data
        if (res.data.data === null) {
          this.jf = 0
        } else {
          this.jf = this.jfdata.JF
          // 判断今天第一次登录
          if (this.jfdata.state === 1 && getStorage('u_s', '').rytype != 'M' && getStorage('u_s', '').rytype != 'Y') {
            this.showPic = true
          }
        }
        this.getJfSum()
      })
    },
    getJfSum () {
      getJfSum().then(res => {
        this.getjfnum = res.data.data
        if (res.data.data === null) {
          this.JFNUM = 0
          this.form.jfnum = 0
        } else {
          this.JFNUM = this.getjfnum
          this.form.jfnum = this.getjfnum
        }
      })
    },
    btn () {
      this.showPic = false
    }
  }
}

</script>
<style lang="stylus" scoped>
.wd_title {
  background-color: #F6F6F6;
  padding: 106px 54px 0 54px;
  .wdt_top {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 30px;
    .wdt_left {
      display: flex;
      flex-wrap: wrap;
      .l_img {
        padding-right: 55px;
        img {
          width: 178px
          height 178px
          border-radius 50%
        }
      }
      .l_msg {
        .lm_top {
          display: flex;
          align-items: center;
          height 50%
          .lm_name {
            padding-right 30px
            span {
              font-size:55px;
              font-family:PingFang SC;
              font-weight:bold;
              color:rgba(48,49,51,1);
            }
          }
          .top_authentication {
            span {
              background-color: #FEE3E1;
              font-size:25px;
              font-family:PingFang SC;
              font-weight:500;
              color:rgba(231,149,119,1);
              padding: 5px 10px
            }
          }
        }
        .lm_bottom {
          padding-top 20px
          span {
            font-size:35px;
            font-family:PingFang SC;
            font-weight:500;
            color:rgba(152,152,152,1);
          }
        }
      }

    }
    .wdt_right {
      img {
        width: 50px
        height auto
      }
    }
  }
  .wdt_bottom {
    display: flex
    justify-content space-between
    align-items center
    padding 30px 60px
    // background-image: linear-gradient(#FCF2DC, #E3C79E);
    background:-webkit-linear-gradient(left,#FCF2DC,#E3C79E);
    background:-o-linear-gradient(right,#FCF2DC,#E3C79E);
    background:-moz-linear-gradient(right,#FCF2DC,#E3C79E);
    background:linear-gradient(to right,#FCF2DC,#E3C79E);
    border-top-left-radius 20px
    border-top-right-radius 20px
    .bxzz_left {
      display flex
      align-items center;
      .bxzz_img {
        padding-right 20px
        img {
          width 40px
          height auto
          display block
        }
      }
      .bxzz_text {
        span {
          font-size:40px;
          font-family:PingFang SC;
          font-weight:500;
          color:rgba(158,118,40,1);
        }
      }
    }
    .bxzz_right {
      span {
        font-size:45px;
        font-family:PingFang SC;
        font-weight:500;
        color:rgba(158,118,40,1);
        border 1px solid #E1BE8A
        padding 5px 25px
        border-radius 20px
      }
      img {
        width 40px
        height auto
        display block
      }
    }
  }

}
.wd_money {
  border-top-left-radius 30px
  border-top-right-radius 30px
  background-color #FFFEFF
  .money_top {
    display flex
    align-items center
    justify-content space-between
    // flex-wrap wrap
    padding 54px 54px 0px 54px
    border-bottom 1px solid #F6F6F6
    .mt_item {
      text-align center
      // padding-right 85px
      .red {
        color:rgba(255,77,41,1) !important
      }
      .item_num {
        span {
          font-size:45px;
          font-family:PingFang SC;
          font-weight:bold;
          color:rgba(56,56,56,1);
        }
        img {
          width 80px
          height auto
          display block
        }
      }
      .item_text {
        padding-top 30px
        padding-bottom 30px
        span {
          font-size:35px;
          font-family:PingFang SC;
          font-weight:500;
          color:rgba(152,152,152,1);
        }
      }
    }
    // .bjRight {
    //   padding-right 0px !important
    // }

  }
  .money_bottom {
    display flex
    justify-content space-between;
    padding 50px 200px
    .mb_item {
      display flex
      justify-content space-between;
      align-items center
      .integral {
        padding-right 15px
        span {
          font-size:40px;
          font-family:PingFang SC;
          font-weight:500;
          color:rgba(131,131,131,1);

        }
      }
      .integralNum {
        span {
          font-size:40px;
          font-family:PingFang SC;
          font-weight:bold;
          color:rgba(56,56,56,1);

        }
      }
      .jfIcon {
        font-size 45px
        color:rgba(131,131,131,1);
        display block
      }

    }
  }

}
.wd_mine {
  display flex
  justify-content space-between
  border-top 50px solid #F6F6F6
  background-color #FFFEFF
  padding 50px 50px
  .mine_item {
    text-align center
    .mineImg {
      height 80px
      width auto
    }
    .mineText {
      padding-top 20px
      font-size:35px;
      font-family:PingFang SC;
      font-weight:500;
      color:rgba(56,56,56,1);
    }

  }

}
.wd_manage {
  display flex
  justify-content space-between
  border-top 50px solid #F6F6F6
  background-color #FFFEFF
  padding 50px 100px
  .manage_item {
    display flex
    align-items center
    .manageImg {
      height 120px
      width auto
      display block
    }
    .manageText {
      padding-left 30px
      span {
        font-size:40px;
        font-family:PingFang SC;
        font-weight:bold;
        color:rgba(56,56,56,1);
      }
    }
  }

}
.wd_change {
  border-top 50px solid #F6F6F6
  background-color #FFFEFF
  padding-bottom 150px
  .change_item {
    display flex
    justify-content space-between
    border-bottom 1px solid #F6F6F6
    padding 50px 60px
    .ci_left {
      span {
        font-size:45px;
        font-family:PingFang SC;
        font-weight:500;
        color:rgba(56,56,56,1);
      }
    }
  }

}
.s-box {
  width: 100%;

  // height 650px;
  .s_img {
    img {
      display: block;
    }
  }

  .s-bom {
    width: 100%;
    background-color: #FFFFFF;
    height: 400px;
    border-bottom-left-radius: 18px;
    border-bottom-right-radius: 18px;
    border-top-left-radius: 18px;
    border-top-right-radius: 18px;
    position: relative;
    top: -20px;
  }

  .s-title {
    display: flex;
    justify-content: space-around;
    line-height: 70px;

    .s-t1 {
      width: 30%;
      text-align: right;
    }

    .s-t2 {
      width: 25%;
      color: #C49961;
      font-size: 0.4rem;
      text-align: center;
    }

    .s-t3 {
      width: 30%;
      text-align: left;
    }

    p {
      font-size: 0.4rem;
    }
  }

  .s-res {
    display: flex;
    justify-content: space-between;
    line-height: 0.7rem;
    margin-top: 60px;

    .s-r1 {
      width: 45%;
      color: #C49961;
      text-align: right;
    }

    .s-r2 {
      width: 45%;
      color: #C49961;
      text-align: left;
    }

    span {
      font-size: 0.5rem;
    }
  }

  .s-btn {
    display: flex;
    justify-content: center;
    text-align: center;
    margin-top: 70px;

    .btn {
      font-size: 28px;
      background-color: color-primary;
      color: #ffffff;
      font-size: 0.35rem;
      width: 50%;
      height: 60px;
      border-bottom-left-radius: 8px;
      border-bottom-right-radius: 8px;
      border-top-left-radius: 8px;
      border-top-right-radius: 8px;
    }
  }
}
.showZyImg {
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  z-index: 9999;
  padding: 250px 150px;
  background:rgba(0,0,0,0.7)
  .zyImg {
    position: relative;
    .zzMsg {
      position: absolute;
      top: 30%;
      width: 100%;
      .itemTitle {
        text-align: center;
        padding-bottom: 50px;
        span {
          font-size:50px;
          font-family:PingFang SC;
          font-weight:bold;
          color:rgba(56,56,56,1);
        }
        .code {
          margin-top: 20px;
          span {
            font-weight:500;
            font-size:35px !important;
          }
        }
      }
      .zzMsgItem {
        display: flex;
        justify-content: space-between;
        margin: 25px 50px;
        padding-bottom: 25px;
        border-bottom 1px solid #F6F6F6
        .showText {
          font-size:40px;
          font-family:PingFang SC;
          font-weight:bold;
          color:rgba(56,56,56,1);
        }
        .showNum {
          font-size:40px;
          font-family:PingFang SC;
          font-weight:500;
          color:rgba(152,152,152,1);
        }
      }
    }
    .closeImg {
      text-align: center;
      padding-top: 30px;
      img {
        width: 100px;
        height: auto;
      }
    }
  }
}
</style>
