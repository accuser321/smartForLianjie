<template>
  <div class="v-simple-cropper">
    <slot>
      <button @click="upload">上传图片</button>
    </slot>
    <input class="file" ref="file" type="file" accept="image/*" @change="uploadChange" />
    <div class="v-cropper-layer" ref="layer">
      <div class="layer-header">
        <button class="cancel" @click="cancelHandle">取消</button>
        <button class="confirm" @click="confirmHandle">裁剪</button>
      </div>
      <img ref="cropperImg">
    </div>
  </div>
</template>
<script>
import Cropper from 'cropperjs'
import 'cropperjs/dist/cropper.min.css'
import { uploadBase64Img } from '@/api/abt/customerOperation/common'
import { bankCardOcr } from '@/api/basic'
import { Toast } from 'mand-mobile'
export default {
  name: 'v-simple-cropper',
  props: {
    initParam: Object,
    successCallback: {
      type: Function,
      default: () => {}
    },
    successBank: {
      type: Function,
      default: () => {}
    }
  },
  data () {
    return {
      cropper: {},
      filename: ''
    }
  },
  mounted () {
    this.init(1 / 1)
  },
  methods: {
    // 初始化裁剪插件
    init () {
      let ratio = this.initParam['ratio']
      let cropperImg = this.$refs['cropperImg']
      this.cropper = new Cropper(cropperImg, {
        aspectRatio: ratio,
        dragMode: 'move'
      })
    },
    // 点击上传按钮
    upload () {
      this.$refs['file'].click()
    },
    // 选择上传图片
    uploadChange (e) {
      let file = e.target.files[0]
      this.filename = file['name']
      let URL = window.URL || window.webkitURL
      this.$refs['layer'].style.display = 'block'
      this.cropper.replace(URL.createObjectURL(file))
    },
    // 取消上传
    cancelHandle () {
      this.cropper.reset()
      this.$refs['layer'].style.display = 'none'
      this.$refs['file'].value = ''
    },
    confirmHandle () {
      Toast.loading('上传中...')
      let cropBox = this.cropper.getCropBoxData()
      let scale = this.initParam['scale'] || 1
      let cropCanvas = this.cropper.getCroppedCanvas({
        width: cropBox.width * scale,
        height: cropBox.height * scale
      })
      let imgData = cropCanvas.toDataURL('image/jpeg')
      let aaa = this.dataURLtoBlob(imgData)
      let bbb = this.blobToFile(aaa, 'bbb')
      let formData = new window.FormData()
      // formData.append('Authorization', token)
      // formData.append('fileType', this.initParam['fileType'])
      // formData.append('img', imgData)
      // formData.append('type', this.initParam['type'])
      formData.append('file', bbb)
      let data = {
        type: this.initParam['type'],
        file: imgData
      }
      bankCardOcr(formData).then(res => {
        this.successBank(res.data.data)
      })
      this.$refs['layer'].style.display = 'none'
      uploadBase64Img(data).then(res => {
        this.successCallback(res.data.data)
        this.cancelHandle()
        Toast.hide()
      })
    },
    // 将base64转换为blob
    dataURLtoBlob (dataurl) {
      var arr = dataurl.split(',')
      var mime = arr[0].match(/:(.*?);/)[1]
      var bstr = atob(arr[1])
      var n = bstr.length
      var u8arr = new Uint8Array(n)
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n)
      }
      return new Blob([u8arr], { type: mime })
    },
    // 将blob转换为file
    blobToFile (theBlob, fileName) {
      theBlob.lastModifiedDate = new Date()
      theBlob.name = fileName
      return theBlob
    }
  }
}
</script>
<style lang="stylus" scoped>
.v-simple-cropper
  touch-action: none;
  .file
    display: none
  .v-cropper-layer
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background: #fff;
    z-index: 99999;
    display: none;
    .layer-header
      position: absolute;
      top: 0;
      left: 0;
      z-index: 99999;
      background: #fff;
      width: 100%;
      height: 1.1rem;
      line-height: 1.1rem;
      padding: 0 .2rem;
      box-sizing: border-box;
    .cancel,.confirm
      line-height: 1.1rem;
      height: 1.1rem;
      font-size: .45rem;
      background: inherit;
      border: 0;
      outline: 0;
      float: left;
    .confirm
      float: right;
    img
      position: inherit!important;
      border-radius: inherit!important;
      float: inherit!important;
</style>
