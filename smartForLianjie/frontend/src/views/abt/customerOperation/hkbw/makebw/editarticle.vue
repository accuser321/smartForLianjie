<!--
 * @Author: 刘格优
 * @Date: 2019-11-12 15:44:40
 * @LastEditors  : 刘格优
 * @LastEditTime : 2019-12-27 18:02:37
 -->
<template>
  <div class="content">
    <div class="card">
      <textarea name="libtitle"
                v-model="libnoinfo.stitle"></textarea>
    </div>
    <div id="contentEditor"></div>
    <div class="pay">
      <div class="back"
           @click="$router.go(-1)">返回</div>
      <div class="payment"
           @click="edit">保存</div>
    </div>
  </div>
</template>

<script>
import Eleditor from 'Eleditor'
import { selectOneBySno } from '@/api/abt/customerOperation/common/index'
import { updateWZ } from '@/api/abt/customerOperation/hkbw/index'
import { getConfig, wxpicUpload } from '@/lib/wxapi'
import config from '@/config'
import { getStorage } from '@/lib/util'
import { Toast } from 'mand-mobile'
export default {
  data () {
    return {
      title: '',
      libnoinfo: {},
      ossurl: '',
      empno: '',
      suserid: '',
      sno: ''
    }
  },
  created () {
    this.ossurl = getStorage('ossurl', '')
    this.sno = this.$route.query.sno
    this.empno = this.$route.query.empno
    this.suserid = this.$route.query.suserid
    this.getdetail()
  },
  mounted () {
    let wxpath = ''
    if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
      // ios
      if (this.$route.query.frompage == 'source') {
        wxpath = config.redirect_uri + '/source'
      } else {
        wxpath = config.redirect_uri
      }
    } else {
      // android
      wxpath = window.location.href
    }
    getConfig(wxpath)
    var artEditor = new Eleditor({
      el: '#contentEditor',
      placeHolder: '请输入内容',
      toolbars: [
        {
          id: 'insertText', // 插入
          tag: 'p,span,div,li,H1,H2,H3,H4,H5,H6', // 绑定标签，按钮只对这些标签生效
          name: '插入'
        },
        {
          id: 'editText', // 编辑
          tag: 'p,span,div,li,H1,H2,H3,H4,H5,H6',
          name: '修改'
        },
        // 自定义一个按钮对象
        {
          id: 'insertShop', // 不能重复，此id可用于定义按钮样式，例如创建后按钮类为Eleditor-insertShop
          // tag: 'img', //指定标签显示按钮，逗号分隔，默认不指定
          name: '插入图片',
          handle: function (_select, _controll) {
            wxpicUpload().then(res => {
              artEditor.saveState()
              var data = res
              var str = ''
              for (var i = 0; i < data.length; i++) {
                str +=
                  "<p style='margin-left: 8px;margin-right: 8px;margin-bottom:5px;'><img src='" +
                  getStorage('ossurl', '') +
                  data[i] +
                  "'style='width:100%'/></p>"
              }
              artEditor.getEditNode().before(str)
            })
          }
        },
        'deleteThis', // 删除
        'cancel' // 取消
      ]
    })
  },

  methods: {
    getdetail () {
      selectOneBySno({ btagcode: '1', sno: this.sno, flag: '1' }).then(res => {
        this.libnoinfo = res.data.data
        // eslint-disable-next-line no-undef
        $('#contentEditor').load(this.ossurl + this.libnoinfo.conthttp)
      })
    },
    edit () {
      let data = {
        sno: this.sno,
        osno: this.libnoinfo.osno,
        title: this.libnoinfo.stitle,
        text: $('#contentEditor').html(),
        bq: this.libnoinfo.bq
      }
      updateWZ(data).then(res => {
        Toast.info('编辑成功')
        this.$router.replace(
          `/Emparticle?sno=${this.sno}&empno=${this.empno}&suserid=${this.suserid}`
        )
      })
    }
  }
}
</script>
<style scoped lang="stylus">
@import './editarticle.styl';
</style>
