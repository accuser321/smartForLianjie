<template>
    <div class="set_content">
        <div class="wd_change">
            <div class="change_item">
                <div class="ci_left">
                <span>当前账号</span>
                </div>
                <div class="ci_right">
                    <span>{{phone}}</span>
                    <!-- <input v-model="form.cardempname"
                           type="text"
                           placeholder="请输入验证码" /> -->
                </div>
            </div>
            <div class="change_item">
                <div class="ci_left">
                <span>旧密码</span>
                </div>
                <div class="ci_right">
                    <input v-model="form.oldpwd"
                           type="text"
                           v-if="showOldPwd"
                           placeholder="请输入原始密码" />
                    <input v-model="form.oldpwd"
                           type="password"
                           v-else
                           placeholder="请输入原始密码" />
                    <md-icon name="visible"
                             size="lg"
                             class="seePwd"
                             @click="showPwd"></md-icon>
                </div>
            </div>
            <div class="change_item solidTop">
                <div class="ci_left">
                <span>设置新密码</span>
                </div>
                <div class="ci_right">
                    <input v-model="form.pwd"
                           type="text"
                           v-if="showNewPwd"
                           placeholder="请设置新的登录密码" />
                    <input v-model="form.pwd"
                           type="password"
                           v-else
                           placeholder="请设置新的登录密码" />
                    <md-icon name="visible"
                             size="lg"
                             class="seePwd"
                             @click="showNPwd"></md-icon>
                </div>
            </div>
            <div class="change_item">
                <div class="ci_left">
                <span>确认新密码</span>
                </div>
                <div class="ci_right">
                    <input v-model="form.pwds"
                           type="text"
                           v-if="showNewPwd2"
                           placeholder="请再次输入密码" />
                    <input v-model="form.pwds"
                           type="password"
                           v-else
                           placeholder="请再次输入密码" />
                    <md-icon name="visible"
                             size="lg"
                             class="seePwd"
                             @click="showNPwd2"></md-icon>
                </div>
            </div>
        </div>
        <div class="btn" @click="submit">
            <div class="quitBtn">确定</div>
        </div>
    </div>
</template>
<script>
import { upEmpnoPwd, quitlogin } from '@/api/basic/index'
import { Toast } from 'mand-mobile'
export default {
  data () {
    return {
      phone: '',
      form: {
        oldpwd: '', // 旧密码
        pwd: '', // 新密码
        pwds: '' // 确认密码
      },
      showOldPwd: false,
      showNewPwd: false,
      showNewPwd2: false
    }
  },
  created () {
    this.phone = this.$route.query.phone
  },
  methods: {
    showPwd (val) {
      this.showOldPwd = !this.showOldPwd
    },
    showNPwd (val) {
      this.showNewPwd = !this.showNewPwd
    },
    showNPwd2 (val) {
      this.showNewPwd2 = !this.showNewPwd2
    },
    submit () {
      if (this.form.pwd.length - 6 < 0) {
        Toast.info('请至少输入6位密码')
        return
      }
      upEmpnoPwd(this.form).then(res => {
        Toast.succeed('保存成功')
        quitlogin().then(() => {
          Toast.succeed('退出成功')
          sessionStorage.clear()
          localStorage.clear()
          this.$router.push({ path: '/login' })
        })
      })
    }
  }
}
</script>
<style lang="stylus" scoped>
.set_content {
    background-color #F6F6F6
    .wd_change {
        //   border-top 50px solid #F6F6F6
        background-color #FFFEFF
        //   padding-bottom 150px
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
                input {
                    overflow: hidden;
                    font-size: 40px;
                    border: none;
                    text-align: right;
                    outline: none;
                }
                .seePwd {
                    padding-left 20px
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
.solidTop {
  border-top 50px solid #F6F6F6
}
</style>
