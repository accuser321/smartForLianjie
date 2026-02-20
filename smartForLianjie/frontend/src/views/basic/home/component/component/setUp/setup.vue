<template>
    <div class="set_content">
        <div class="wd_change">
            <div class="change_item" @click="toMyMsg">
                <div class="ci_left">
                <span>个人资料</span>
                </div>
                <div>
                <md-icon name="arrow-right" size="lg"></md-icon>
                </div>
            </div>
            <div class="change_item" @click="toRealName">
                <div class="ci_left">
                <span>实名认证</span>
                </div>
                <div class="ci_right">
                    <div class="cr_name">{{empname}}</div>
                    <!-- <div class="cr_authentication">{{sfzstatus == '0' ? '已实名':'未实名'}}</div> -->
                    <div class="cr_authentication">{{zyzstatus == '0' ? '已实名':'未实名'}}</div>
                    <div>
                        <md-icon name="arrow-right" size="lg"></md-icon>
                    </div>
                </div>
            </div>
            <div class="change_item" @click="toChangePassword">
                <div class="ci_left">
                <span>账户密码</span>
                </div>
                <div>
                <md-icon name="arrow-right" size="lg"></md-icon>
                </div>
            </div>
            <div class="change_item" @click="toPractisingQualification">
                <div class="ci_left">
                <span>执业资质</span>
                </div>
                <div class="ci_right">
                    <div class="cr_authentication">{{zyzstatus == '0' ? '已认证':'未认证'}}</div>
                    <div>
                        <md-icon name="arrow-right" size="lg"></md-icon>
                    </div>
                </div>
            </div>
            <div class="change_item" @click="toChangeBank">
                <div class="ci_left">
                <span>银行卡</span>
                </div>
                <div>
                <md-icon name="arrow-right" size="lg"></md-icon>
                </div>
            </div>
            <div class="change_item" @click="toChangePhoneNumber">
                <div class="ci_left">
                <span>更换手机号</span>
                </div>
                <div>
                <md-icon name="arrow-right" size="lg"></md-icon>
                </div>
            </div>
            <div class="change_item">
                <div class="ci_left">
                <span>是否展示推广费</span>
                </div>
                <div>
                <!-- <md-icon name="arrow-right" size="lg"></md-icon> -->
                <md-switch
                  v-model="flag"
                  @change="changePromotionFee(flag)"
                ></md-switch>
                </div>
            </div>
        </div>
        <div class="btn">
            <div class="quitBtn" @click="quitLogin">退出登录</div>
        </div>
    </div>
</template>
<script>
import { Dialog, Toast, Switch } from 'mand-mobile'
import { quitlogin, isShowPromotionprice } from '@/api/basic'
import { getStorage } from '@/lib/util'
export default {
  components: {
    [Switch.name]: Switch
  },
  data () {
    return {
      empname: '',
      empno: '',
      zyzstatus: '',
      sfzstatus: '',
      phone: '',
      flag: false
    }
  },
  created () {
    this.empname = this.$route.query.empname
    this.empno = this.$route.query.empno
    this.zyzstatus = this.$route.query.zyzstatus
    this.sfzstatus = this.$route.query.sfzstatus
    this.phone = this.$route.query.phone
    if (getStorage('flag', '') == '0') {
      this.flag = true
    } else {
      this.flag = false
    }
  },
  methods: {
    toMyMsg () {
      this.$router.push(`/myMsg`)
    },
    toRealName () {
      if (this.zyzstatus == '0') {
        this.$router.push(`/realName?empno=${this.empno}`)
      }
    },
    toPractisingQualification () {
      if (this.zyzstatus == '0') {
        this.$router.push(`/practisingQualification?empno=${this.empno}`)
      }
    },
    toChangePassword () {
      this.$router.push(`/changePassword?phone=${this.phone}`)
    },
    toChangeBank () {
      this.$router.push(`/changeBank?empno=${this.empno}`)
    },
    toChangePhoneNumber () {
      this.$router.push(`/changePhoneNumber?phone=${this.phone}`)
    },
    changePromotionFee (flag) {
      isShowPromotionprice({ flag: this.flag ? '0' : '1' }).then(res => {
        Toast.succeed(this.flag ? '已开启' : '已关闭')
        window.sessionStorage.setItem('flag', (this.flag ? '0' : '1'))
      })
    },
    quitLogin () {
      Dialog.confirm({
        content: '确定退出吗',
        confirmText: '确定',
        onConfirm: () => {
          quitlogin().then(() => {
            Toast.succeed('退出成功')
            sessionStorage.clear()
            localStorage.clear()
            this.$router.push({ path: '/login' })
          })
        }
      })
    }
  }
}
</script>
<style lang="stylus" scoped>
.set_content {
    background-color #F6F6F6
    .wd_change {
        background-color #FFFEFF
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
            .ci_right {
                display flex
                justify-content flex-end
                align-items center
                .cr_name {
                    font-size:45px;
                    font-family:PingFang SC;
                    font-weight:500;
                    color:rgba(56,56,56,1);
                }
                .cr_authentication {
                    font-size:30px;
                    font-family:PingFang SC;
                    font-weight:500;
                    color:rgba(255,103,103,1);
                    padding: 5px 10px
                    border: 1px solid #FEF0F5
                    border-radius 10px
                    margin 0px 20px
                }

            }
        }

    }
    .btn {
        text-align center
        padding-top 200px
        .quitBtn {
            padding: 30px 30px;
            margin: 80px auto;
            width: 90%;
            background-color: #BA1C21;
            font-size:40px;
            font-family:PingFang SC;
            font-weight:500;
            color:rgba(255,255,255,1);
            border-radius 10px
        }
    }
}

</style>
