<template>
  <div class="content">
    <div class="item">
      <div class="itemLeft"><span>工号</span><span class="red">*</span></div>
      <div class="itemRight">
        <span>{{form.empno}}</span>
        <!-- <input v-model="form.empno"
               type="text"
               placeholder="请输入工号" /> -->
      </div>
    </div>
    <div class="item">
      <div class="itemLeft"><span>开户行</span><span class="red">*</span></div>
      <div class="itemRight">
        <input v-model="form.bankcode"
               type="text"
               placeholder="请输入开户行" />
      </div>
    </div>
    <div class="item">
      <div class="itemLeft"><span>支行</span><span class="red">*</span></div>
      <div class="itemRight">
        <input v-model="form.bankzh"
               type="text"
               placeholder="请输入支行" />
      </div>
    </div>
    <div class="item">
      <div class="itemLeft"><span>银行卡号</span><span class="red">*</span></div>
      <div class="itemRight">
        <input v-model="form.bankno"
               type="text"
               maxlength="19"
               placeholder="请输入银行卡号" />
      </div>
    </div>
    <div class="pic">
      <div class="p_title">银行卡影像<span class="red">*</span></div>
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
    <div class="btn">
      <div @click="save"
           class="saveBtn">保 存</div>
    </div>
  </div>
</template>
<script>
import { selectYHByEmpno, upYHKMessage } from '@/api/basic'
import { getStorage } from '@/lib/util'
// import simpleCropper from './component/simpleCropper'
import { Toast } from 'mand-mobile'
export default {
  // components: {
  //   simpleCropper
  // },
  data () {
    return {
      ossurl: '',
      user: '',
      form: {
        bankzh: '',
        souhttp: '', // 影像
        bankcode: '',
        bankno: '',
        empno: ''
      },
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
    this.user = getStorage('u_s', {})
    this.getData()
  },
  methods: {
    getData () {
      selectYHByEmpno({ empno: this.user.empno }).then(res => {
        this.form = res.data.data
      })
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
        this.form.bankcode = data.bank_name
        this.form.bankno = data.bank_card_number
      } else {
        Toast.info('未能识别出银行卡信息')
      }
    },
    // 上传图片成功回调
    uploadHandle (data) {
      this.form.souhttp = data.path
      Toast.succeed('上传成功')
    },
    save () {
      if (!this.form.souhttp) {
        Toast.console.failed('请先上传银行卡影像')
        return
      }
      if (!this.form.empno) {
        Toast.failed('请输入工号')
        return
      }
      if (!this.form.bankcode) {
        Toast.failed('请输入开户行')
        return
      }
      if (!this.form.bankzh) {
        Toast.failed('请输入支行')
        return
      }
      if (!this.form.bankno) {
        Toast.failed('请输入银行卡号')
        return
      }
      upYHKMessage(this.form).then(res => {
        Toast.succeed('保存成功')
        this.$router.push('/myCenter')
      })
    }
  }
}
</script>
<style lang="stylus" scoped>
.content {
  background-color: #fff;
  .item {
    display: flex;
    justify-content: space-between;
    padding: 50px 50px;
    border-bottom: 1px solid #efefef;

    span {
      font-size: 0.4rem;
    }

    input {
      overflow: hidden;
      font-size: 40px;
      border: none;
      text-align: right;
      outline: none;
    }
  }
  .pic {
    margin: 50px 50px
    height 600px
    .p_title {
      font-size: 40px;
    }
    .p_pic {
      padding-top 30px
    }
  }
  .btn {
    position: fixed;
    bottom: 0;
    width: 100%;

    .saveBtn {
      text-align: center;
      padding: 20px 30px;
      width: 100%;
      background-color: color-primary;
      color: #fff;
      border-radius: 10px;
      font-size: 40px;
    }
  }
}
.red {
  color red
}
.mainpic {
  width 100%
  height auto
}
</style>
