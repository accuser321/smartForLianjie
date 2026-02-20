<template>
    <div class="set_content">
        <div class="wd_change">
            <div class="change_item">
                <div class="ci_left">
                <span>当前手机号</span>
                </div>
                <div class="ci_right">
                    <span>{{phone}}</span>
                </div>
            </div>
            <div class="change_item solidTop">
                <div class="ci_left">
                <span>新手机号</span>
                </div>
                <div class="ci_right">
                    <input v-model="form.phone"
                           type="text"
                           placeholder="请输入新的手机号" />
                </div>
            </div>
            <div class="change_item">
                <div class="ci_left">
                <span>验证码</span>
                </div>
                <div class="ci_right">
                    <div>
                        <input v-model="form.num"
                            type="text"
                            placeholder="请输入验证码" />
                    </div>
                    <div class="sendYZM">
                        <span v-if="showSend" @click="getNum">发送</span>
                        <span v-else>{{count}}s后重试</span>
                    </div>

                </div>
            </div>
        </div>
        <div class="btn" @click="submit">
            <div class="quitBtn">提交</div>
        </div>
    </div>
</template>
<script>
import {
  upEmpnoPhone,
  changePhone
} from '@/api/basic/index'
import { Toast } from 'mand-mobile'
export default {
  data () {
    return {
      form: {
        phone: '',
        num: ''
      },
      count: '',
      timer: null,
      showSend: true,
      phone: ''
    }
  },
  created () {
    this.phone = this.$route.query.phone
  },
  methods: {
    getNum () {
      if (this.form.phone == '') {
        Toast.info('请输入手机号!')
        return false
      }
      if (this.phone == this.form.phone) {
        Toast.info('新手机号与老手机号一致!')
        return false
      }
      // var reg = /^[1][0-9][0-9]{9}$/
      // if (!reg.test(this.form.phone)) {
      //     Toast.info('请输入正确的手机号!')
      //     return false
      // }
      Toast.loading('发送中...')
      changePhone({ phone: this.form.phone }).then(res => {
        Toast.hide()
        const TIME_COUNT = 60
        if (!this.timer) {
          this.count = TIME_COUNT
          this.showSend = false
          this.timer = setInterval(() => {
            if (this.count > 0 && this.count <= TIME_COUNT) {
              this.count--
            } else {
              this.showSend = true
              clearInterval(this.timer)
              this.timer = null
            }
          }, 1000)
        }
        Toast.info('发送成功')
      })
    },
    submit () {
      if (this.form.phone == '') {
        Toast.info('请输入手机号!')
        return false
      }
      if (this.form.num == '') {
        Toast.info('请输入验证码!')
        return false
      }
      upEmpnoPhone(this.form).then(res => {
        Toast.info(res.data.data)
        this.phone = this.form.phone
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
                display flex
                justify-content flex-end
                align-items center
                input {
                    overflow: hidden;
                    font-size: 40px;
                    border: none;
                    text-align: right;
                    outline: none;
                }
                .sendYZM {
                    text-align right
                    width 200px
                    span {
                        font-size:40px;
                        font-family:PingFang SC;
                        font-weight:500;
                        color:rgba(78,133,248,1);
                    }
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
