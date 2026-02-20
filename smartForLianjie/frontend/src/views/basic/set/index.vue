<template>
  <div>
    <div class="logo_bg">
      <div class="logo"><img :src="applogo"
             alt=""
             :class="applogo?'':'img_style'"></div>
    </div>
    <div class="md-example-child md-example-child-cell-item md-example-child-cell-item-0">
      <md-field>
        <md-cell-item title="版本信息"
                      addon="V1.0">
          <template #left>
            <i class="iconfont iconxinxishuoming" />
          </template>
        </md-cell-item>
        <md-cell-item title="合同查看"
                      arrow
                      @click="gethtck()">
          <template #left>
            <i class="iconfont iconicon-test" />
          </template>
        </md-cell-item>
        <md-cell-item title="建议反馈"
                      arrow
                      @click="$router.push('/advice')">
          <template #left>
            <i class="iconfont iconyaoqingdaoshi" />
          </template>
        </md-cell-item>
        <md-cell-item title="清除缓存"
                      arrow
                      @click="clearInfo">
          <template #left>
            <i class="iconfont iconqingchu" />
          </template>
        </md-cell-item>
      </md-field>
    </div>
    <div class="footer">
      <md-button type="primary"
                 @click="quitLogin">解除绑定</md-button>
    </div>
    <!-- <div class="pdfbox"
         v-if="pdfshow">
      <iframe :src="ossurl+pichttp"
              frameborder="0"
              name="_blank"
              :width="deviceWidth"
              :height="deviceHeight"
              scrolling="auto"
              style="-webkit-overflow-scrolling:touch"></iframe>
    </div> -->

    <md-landscape v-model="showFullScreen"
                  full-screen
                  class="landscape">
      <div class="Iconx">
        <md-icon name="clear"
                 size="lg"
                 class="Iconclear"
                 @click="close"></md-icon>
      </div>
      <div class="pdf"
           style="padding-top: 1rem;">
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
import pdf from 'vue-pdf'
import { Dialog, Toast } from 'mand-mobile'
import { quitlogin, gethtck } from '@/api/basic'
import { getStorage } from '@/lib/util'
export default {
  name: 'index',
  components: {
    pdf,
  },
  data () {
    return {
      applogo: '',
      ossurl: '',
      // deviceWidth: '',
      // deviceHeight: '',
      // pichttp: '',
      showFullScreen: false,
      filepath: '',
      pageCount: 0, // pdf文件总页数
      numPages: 0,
    }
  },
  created () {
    // this.deviceWidth = document.documentElement.clientWidth
    // this.deviceHeight = document.documentElement.clientHeight
    this.ossurl = getStorage('ossurl', '')
    this.applogo = getStorage('sys_info', {}).applogo
  },
  methods: {
    gethtck () {
      gethtck().then(res => {
        this.numPages = 0
        this.$nextTick(() => {
          setTimeout(() => {
            this.filepath = pdf.createLoadingTask(this.ossurl + res.data.data)
            this.filepath.then(pdf => {
              this.numPages = pdf.numPages
            })
            this.showFullScreen = true
          }, 500);
        })
      })
    },
    close () {
      this.showFullScreen = false
    },
    quitLogin () {
      Dialog.confirm({
        content: '确定解除绑定吗',
        confirmText: '确定',
        onConfirm: () => {
          quitlogin().then(() => {
            Toast.succeed('解除成功')
            sessionStorage.clear()
            localStorage.clear()
            this.$router.push({ path: '/login' })
          })
        }
      })
    },
    clearInfo () {
      sessionStorage.clear()
      localStorage.clear()
      this.$router.push({ path: '/' })
    }
  }
}
</script>

<style lang="stylus" scoped>
.logo_bg {
  background: #fff;
  padding: 1.5rem;
  margin-bottom: 0.3rem;

  .logo {
    width: 90%;
    margin: auto;
    .img_style {
      opacity: 0;
    }
  }
}

/deep/ .md-cell-item-body {
  min-height: 2rem;
}

.footer {
  width: 80%;
  margin-left: 10%;
  overflow: hidden;
  border-radius: 10px;
  margin-bottom: 10%;
  margin-top: 10%;
}

/deep/ .md-button {
  height: 1rem;
}
/deep/ .md-field {
  padding-bottom: 0;
  padding-top: 0;
}

.landscape /deep/ .md-landscape-close {
  display: none !important;
}

.Iconx {
  margin-top: 0.2rem;
  margin-right: 0.3rem;
  right: 0;
  position: absolute;
}

.Iconclear {
  color: rgba(148, 148, 148, 0.6) !important;
  font-size: 0.7rem !important;
}
</style>
