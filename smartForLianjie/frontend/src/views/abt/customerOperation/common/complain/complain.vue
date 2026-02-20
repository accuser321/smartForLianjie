<template>
  <div class="content">
    <div class="list-block">
      <div class="wdtt">
        <div class="wdtt_top">
          <span></span>
          <div class="wdtt_desc">投诉内容</div>
        </div>
        <div class="wdtt_bottom">
          {{ stitle }}
        </div>
      </div>
      <div class="wdtt">
        <div class="wdtt_top">
          <span></span>
          <div class="wdtt_desc">
            上传图片
            <p style="color:#666;display:inline-block;">
              (点击下方图片上传或更改图片)
            </p>
          </div>
        </div>
        <div class="pic_box">
          <ul class="pic_boxul">
            <li class="pic_boxli"
                v-if="pic.url">
              <simple-cropper :initParam="uploadParam"
                              :successCallback="uploadHandle"
                              ref="cropper"
                              style="width:100%;height:100%;">
                <img :src="pic.url"
                     style="width:100%;height:100%;"
                     alt
                     @click="upload('cropper')" />
              </simple-cropper>
            </li>
            <li class="pic_boxli"
                v-if="!pic.url">
              <div class="header">
                <simple-cropper :initParam="uploadParam"
                                :successCallback="uploadHandle"
                                ref="cropper">
                  <img src="@/assets/abt/img/add.png"
                       alt
                       @click="upload('cropper')" />
                </simple-cropper>
              </div>
            </li>
          </ul>
        </div>
      </div>
      <!-- <div class="wdtt">
        <div class="wdtt_top">
          <span></span>
          <div class="wdtt_desc">投诉类型</div>
          <i style="color: #dd463f">*</i>
        </div>
        <div class="ase">
          <select style="font-size: 14px;"
                  v-model="reporttype">
            <option value=""
                    disabled
                    selected>请选择投诉类型</option>
            <option v-for="(item, index) in reasonlist"
                    :value="item.value"
                    :key="index">{{ item.label }}</option>
          </select>
        </div>
      </div> -->
      <div class="wdtt">
        <div class="wdtt_top">
          <span></span>
          <div class="wdtt_desc">投诉内容</div>
          <i style="color: #dd463f">*</i>
        </div>
        <div class="wdtt_bottom"
             style="position: relative;">
          <textarea name="remark"
                    v-model="remark"
                    @input="descInput"
                    placeholder="请输入建议内容"
                    maxlength="200"></textarea>
          <div class="textnum"
               style="position: absolute;bottom:3px;right:23px;">
            {{ remnant }}/200
          </div>
        </div>
      </div>
      <md-button :loading="submitstatus"
                 @click="submit"
                 :disabled="btnstatus">{{  !btnstatus ? (remark !== '' ? '确认并提交' : '提交') : '已提交' }}</md-button>
    </div>
  </div>
</template>

<script>
import { Toast } from 'mand-mobile'
import { getStorage } from '@/lib/util'
import { getreport } from '@/api/abt/customerOperation/common/index'
// import simpleCropper from './../../visitingcard/component/simpleCropper'
export default {
  // components: {
  //   simpleCropper
  // },
  data () {
    return {
      submitstatus: false,
      btnstatus: false,
      stitle: '',
      remark: '',
      reasonlist: [
        {
          value: 'TS',
          label: '投诉'
        },
        {
          value: 'JY',
          label: '建议'
        }
      ],
      reporttype: '',
      uploadParam: {
        ile: 'tsjy',
        type: 'tsjy',
        fileType: 'recruit', // 其他上传参数
        uploadURL: '/empno/getUpload', // 上传地址
        scale: 4, // 相对手机屏幕放大的倍数: 4倍
        full: true
      },
      pic: '',
      ossurl: '',
      remnant: '0',
      sno: ''
    }
  },
  created () {
    this.ossurl = getStorage('ossurl', '')
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      vm.stitle = vm.$route.query.stitle
      vm.sno = vm.$route.query.sno
    })
  },
  methods: {
    // 字数显示
    descInput () {
      this.remnant = this.remark.length
    },
    // 上传图片
    upload (ele) {
      this.$refs[ele].upload()
    },
    // 上传图片成功回调
    uploadHandle (data) {
      this.pic = data
      Toast.succeed('上传成功')
    },
    submit () {
      // if (!this.reporttype) {
      //   Toast.failed('请选择投诉类型')
      //   return
      // }
      if (!this.remark) {
        Toast.failed('请输入建议内容')
        return
      }
      this.submitstatus = true
      let data = {
        btagcode: '1',
        sno: this.sno,
        tsreason: this.remark,
        tspic: this.pic ? this.pic.path : ''
      }
      getreport(data).then(res => { //文章建议投诉POST
        if (res.data.code == '200') {
          Toast.succeed(res.data.msg)
          this.submitstatus = false;
          this.btnstatus = true
        } else {
          Toast.failed(res.data.msg)
        }
      })
    }
  }
}
</script>

<style scoped lang="stylus">
.content {
  width: 100%;
  height: 100%;
  background: #ffffff;

  .list-block {
    .wdtt {
      .wdtt_top {
        padding: 0.4rem 0.3rem;
        border-bottom: 1px solid #efefef;
        line-height: 40px;

        span {
          height: 0.5rem;
          background: color-primary;
          padding: 0 0.05rem;
        }

        .wdtt_desc {
          font-size: 0.4rem;
          margin-left: 10px;
          display: inline-block;
        }
      }

      .wdtt_bottom {
        padding: 0.2rem 0.3rem 0 0.3rem;
        line-height: 0.6rem;
      }

      .ase {
        border-bottom: 1px solid #efefef;
        width: 100%;
        padding: 0 0.3rem;

        select {
          -webkit-appearance: none;
          -moz-appearance: none;
          -ms-appearance: none;
          appearance: none;
          box-sizing: border-box;
          border: none;
          background: 0 0;
          border-radius: 0;
          box-shadow: none;
          display: block;
          margin: 0;
          width: 100%;
          height: 1.15rem;
          color: #3d4145;
          font-size: 0.85rem;
          font-family: inherit;
        }
      }

      textarea {
        height: 5rem;
        resize: none;
        line-height: 1.4;
        padding-top: 0.4rem;
        padding-bottom: 0.35rem;
        -webkit-appearance: none;
        -moz-appearance: none;
        -ms-appearance: none;
        appearance: none;
        box-sizing: border-box;
        border: none;
        background: 0 0;
        border-radius: 0;
        box-shadow: none;
        display: block;
        padding: 0 0 0 0.25rem;
        margin: 0;
        width: 100%;
        height: 2.15rem;
        color: #3d4145;
        font-size: 0.4rem;
        font-family: inherit;
        height: 2.5rem;
      }

      .pic_box {
        width: 100%;
        padding: 0 0.3rem;

        .pic_boxul {
          width: 100%;
          display: flex;
          padding-top: 0.3rem;

          .pic_boxli {
            width: 2rem;
            height: 2rem;
            margin-right: 0.1rem;
          }
        }
      }
    }

    /deep/ .md-button {
      background-color: color-primary;
      height: 100px;
      border-radius: 8px;
      width: 90vw;
      margin: 0 5vw;
      color: white;
      margin-top: 80px;
    }

    .remark:focus {
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.175);
    }
  }
}
</style>
