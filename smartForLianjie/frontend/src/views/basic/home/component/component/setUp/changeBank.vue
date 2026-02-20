<template>
    <div class="set_content">
        <div class="wd_change">
            <div class="change_item">
                <div class="ci_left">
                <span>工号</span>
                </div>
                <div class="ci_right">
                    <span>{{form.empno}}</span>
                </div>
            </div>
            <div class="change_item">
                <div class="ci_left">
                <span>开户银行</span>
                </div>
                <div class="ci_right">
                    <input v-model="bankname"
                           type="text"
                           @click="changeBank"
                           readonly
                           placeholder="请选择开户银行" />
                    <md-selector v-model="isSelectorShow"
                                :data="bankList"
                                :default-value="form.bankcode"
                                title="--请选择开户行--"
                                max-height="320px"
                                @choose="onSelectorChoose"></md-selector>
                </div>
            </div>
            <div class="change_item">
                <div class="ci_left">
                <span>开户支行</span>
                </div>
                <div class="ci_right">
                    <input v-model="form.bankzh"
                           type="text"
                           placeholder="请输入开户支行" />
                </div>
            </div>
            <div class="change_item">
                <div class="ci_left">
                <span>银行卡号</span>
                </div>
                <div class="ci_right">
                    <input v-model="form.bankno"
                           type="text"
                           maxlength="19"
                           placeholder="请输入银行卡号" />
                </div>
            </div>
            <div class="change_pic">
                <div class="ci_left">
                    <span>银行卡影像</span>
                </div>
                <simple-cropper :initParam="uploadParam"
                                :successCallback="uploadHandle"
                                :successBank="successSeeBank"
                                ref="cropper"
                                class="box">
                    <div class="p_pic" @click="upload('cropper')">
                        <img v-if="form.souhttp != ''" :src="ossurl+form.souhttp" alt="" class="mainpic" />
                        <img v-else src="@/assets/image/w_yhk.png" class="mainpic"/>
                    </div>
                </simple-cropper>
            </div>
        </div>
        <div class="btn" @click="submit">
            <div class="quitBtn">确定</div>
        </div>
    </div>
</template>
<script>
import { selectYHByEmpno, upYHKMessage } from '@/api/basic/index'
// import simpleCropper from './component/simpleCropper'
import { getStorage } from '@/lib/util'
import { Toast } from 'mand-mobile'
export default {
  // components: {
  //   simpleCropper
  // },
  data () {
    return {
      ossurl: '',
      form: {
        empno: '', //
        bankcode: '', //
        bankzh: '',
        bankno: '',
        souhttp: ''
      },
      bankList: [],
      isSelectorShow: false,
      bankname: '',
      uploadParam: {
        file: 'bank',
        type: 'bank',
        fileType: 'recruit', // 其他上传参数
        uploadURL: '/empno/getUpload', // 上传地址
        scale: 4, // 相对手机屏幕放大的倍数: 4倍
        ratio: 5 / 3
        // userid: localStorage.getItem(window.g.beforestr + '_userid'),
        // comid: localStorage.comid
      } // 上传头像初始化数据
    }
  },
  created () {
    this.ossurl = getStorage('ossurl', '')
    this.form.empno = this.$route.query.empno
    this.selectBankcode()
  },
  methods: {
    getData () {
      selectYHByEmpno({ empno: this.form.empno }).then(res => {
        let Data = res.data.data
        this.form.bankcode = Data.bankcode
        this.form.bankzh = Data.bankzh
        this.form.bankno = Data.bankno
        this.form.souhttp = Data.souhttp
        this.bankList.forEach((item, index) => {
          if (item.bankid == this.form.bankcode) {
            this.bankname = item.bankname
          }
        })
      })
    },
    // 银行卡下拉框
    selectBankcode () {
      selectBaseBankcode().then(res => {
        this.bankList = res.data.data
        this.bankList.forEach((item, index) => {
          this.$set(this.bankList[index], 'value', item.bankid)
          this.$set(this.bankList[index], 'text', item.bankname)
        })
      }).finally(() => {
        this.getData()
      })
    },
    changeBank () {
      this.isSelectorShow = true
    },
    onSelectorChoose ({ text, value }) {
      this.form.bankcode = value
      this.bankname = text
    },
    // 上传图片
    upload (ele) {
      if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
        console.log(this.$refs[ele].upload())
      }
      this.$refs[ele].upload()
    },
    // 识别银行卡成功回调
    successSeeBank (data) {
      if (data != '' && data != null) {
        Toast.succeed('银行卡信息识别成功')
        // this.form.bankcode = data.bank_name
        this.form.bankno = data.bank_card_number.replace(/\s/g, '')
        this.bankList.forEach((item, index) => {
          if (data.bank_card_number == item.bankname) {
            this.bankname = item.bankname
            this.form.bankcode = item.bankid
          }
          // if (item.bankid == this.form.bankcode) {
          //   this.bankname = item.bankname
          // }
        })
      } else {
        Toast.info('未能识别出银行卡信息')
      }
    },
    // 上传图片成功回调
    uploadHandle (data) {
      this.form.souhttp = data.path
      Toast.succeed('上传成功')
    },
    submit () {
      if (!this.form.souhttp) {
        Toast.failed('请先上传银行卡影像')
        return
      }
      if (!this.form.bankcode) {
        Toast.failed('请输入开户银行')
        return
      }
      if (!this.form.bankzh) {
        Toast.failed('请输入开户支行')
        return
      }
      if (!this.form.bankno) {
        Toast.failed('请输入银行卡号')
        return
      }
      upYHKMessage(this.form).then(res => {
        Toast.succeed('保存成功')
        // this.$router.push('/myCenter')
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
            }
        }
        .change_pic {
            padding 50px 60px
            .ci_left {
                padding-bottom 50px
                span {
                    font-size:45px;
                    font-family:PingFang SC;
                    font-weight:500;
                    color:rgba(56,56,56,1);
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
.mainpic {
  width 100%
  height auto
}
</style>
