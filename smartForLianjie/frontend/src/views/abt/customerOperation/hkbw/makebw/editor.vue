<!--
 * @Author: 刘格优
 * @Date: 2019-11-06 17:35:51
 * @LastEditors: 刘格优
 * @LastEditTime: 2020-03-18 11:11:39
 -->
<template>
  <div class="acontent" style="overflow:scroll">
    <div style="margin-bottom:20px">
      <div class="fxcontent clearfix">
        <div class="fenleinav">
          <div class="slider"
               ref="slider"
               id="slider">
            <div class="fenlei">
              <span class="fenspan"
                     style="width:100%"
                    @click="luck(0)"
                    :class="{ luckcss: 0 == tabclass }">微信文章链接导入</span>
            </div>
            <!-- <div class="fenlei">
              <span class="fenspan"
                    @click="luck(1)"
                    :class="{ luckcss: 1 == tabclass }">文章制作</span>
            </div> -->
          </div>
        </div>
      </div>
      <div class="import"
           v-show="tabclass == 0">
        <div class="aurl">
          <div class="url">
            <textarea name="url"
                      id=""
                      placeholder="请将已复制的【微信文章链接】，黏贴到此处"
                      v-model="message"
                      class="importextarea"></textarea>
          </div>
          <!-- <div class="czzn"
               @click="tozy">
            <span>查看操作指引</span>
            <md-icon name="arrow-right"
                     size="lg"></md-icon>
            <md-icon name="arrow-right"
                     size="lg"></md-icon>
          </div> -->
          <div class="list">
            <ul class="clearfix">
              <li class="label_list"
                  v-for="(item, index) in labellist"
                  :key="index"
                  :data-ins="item.tagcode"
                  @click="choose($event)">
                {{ item.tagname }}
              </li>
            </ul>
          </div>
        </div>
        <div class="abuts">
          <div class="clear makebtn"
               @click="clear">清空链接</div>
          <div class="make makebtn"
               @click="daoru">生成文章</div>
        </div>
      </div>
      <div class="editor"
           v-show="tabclass == 1">
        <div class="card"
             style="margin:0;">
          <textarea name="libtitle"
                    v-model="title"
                    placeholder="请输入标题"
                    class="maketextarea"></textarea>
        </div>
        <div id="contentEditor"
             v-html="content"></div>
        <div class="makebut"
             @click="edit">保存并发布</div>
      </div>
    </div>

    <div v-show="czzy"
         class="img_box">
      <div class="sec"
           style="height:100%;overflow-y:hidden;">
        <md-icon name="wrong"
                 size="lg"
                 class="closezy"
                 @click="delczzy()"></md-icon>
        <div class="allimg">
          <img src="@/assets/abt/img/czzy1.jpg"
               alt="" />
          <img src="@/assets/abt/img/czzy2.jpg"
               alt="" />
          <img src="@/assets/abt/img/czzy3.jpg"
               alt="" />
          <img src="@/assets/abt/img/czzy4.jpg"
               alt="" />
        </div>
      </div>
    </div>
    <!-- 文章制作选择标签 -->
    <md-popup v-model="bqchoose"
              position="bottom"
              :mask-closable="false">
      <md-popup-title-bar title="选择标签"
                          describe=""
                          ok-text="确定"
                          cancel-text="取消"
                          large-radius
                          @confirm="tochoose()"
                          @cancel="hidePopUp()"></md-popup-title-bar>
      <div class="md-example-popup md-example-popup-bottom">
        <div class="list">
          <ul class="clearfix">
            <li class="label_list"
                v-for="(item, index) in labellist"
                :key="index"
                :data-ins="item.tagcode"
                @click="choosebq($event)">
              {{ item.tagname }}
            </li>
          </ul>
        </div>
      </div>
    </md-popup>
    <md-dialog v-model="showVideo">
      <div>
        <input type="file"
               @change="upVideo" />
      </div>
    </md-dialog>
  </div>
</template>

<script>
import Eleditor from 'Eleditor'
import {
  selectKJFL,
  GetwxConfig,
  uploadBase64Img
} from '@/api/abt/customerOperation/common/index'
import { importWZ, insertWZ } from '@/api/abt/customerOperation/hkbw/index'
import { Toast } from 'mand-mobile'
import { getConfig, wxpicUpload } from '@/lib/wxapi'
import { getStorage } from '@/lib/util'
import config from '@/config'
export default {
  data () {
    return {
      ossurl: '',
      tabclass: 0,
      message: '',
      title: '',
      content: '点击此处编辑内容',
      labellist: [],
      contentdata: '',
      czzy: false,
      bqchoose: false,
      showVideo: false,
      // artEditor: null,
      videostr: ''
    }
  },
  created () {
    this.getbq()
    this.ossurl = getStorage('ossurl', '')
  },
  mounted () {
    var artEditor = new Eleditor({
      el: '#contentEditor',
      toolbars: [
        {
          id: 'insertText', // 插入
          tag: 'p,span', // 绑定标签，按钮只对这些标签生效
          name: '插入'
        },
        {
          id: 'editText', // 编辑
          tag: 'p,span',
          name: '修改'
        },
        // 自定义一个按钮对象
        {
          id: 'insertShop', // 不能重复，此id可用于定义按钮样式，例如创建后按钮类为Eleditor-insertShop
          // tag: 'img', //指定标签显示按钮，逗号分隔，默认不指定
          name: '插入图片',
          handle: function (_select, _controll) {
            // console.log(artEditor)
            // console.log(this.ossurl)
            // console.log(getStorage('ossurl', ''))
            wxpicUpload().then(res => {    //插入图片
              artEditor.saveState()
              // alert(12);
              // console.log(artEditor.saveState());
              var data = res
              var str = ''
              for (var i = 0; i < data.length; i++) {
                str +=
                  "<p style='margin-left: 8px;margin-right: 8px;margin-bottom:5px;'><img src='" +
                  getStorage('ossurl', '') +
                  data[i] +
                  "'style='width:100%'/></p>"
              }
              // console.log(str)
              artEditor.getEditNode().before(str)
            })
          }
        },
        'deleteThis', // 删除
        'cancel' // 取消
      ]
    })
    $('.Eleditor-placeholder').click()
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
    getConfig(wxpath) //获取微信配置
  },

  methods: {
    upVideo (e) {
      let file = e.target.files || e.dataTransfer.files
      if (!file.length) {
        return
      }
      this.uploadVideo(file[0])
    },
    uploadVideo (file) {
      // let that = this
      console.log(this)
      let reader = new FileReader()
      reader.onload = e => {
        let data = {
          file: e.target.result,
          type: 'WZ'
        }
        uploadBase64Img(data).then(res => {
          this.videostr = res.data.data.path
        })
      }
      reader.readAsDataURL(file)
      this.artEditor.saveState()
      var str = ''
      str +=
        "<p style='margin-left: 8px;margin-right: 8px;margin-bottom:5px;'><video controls src='" +
        this.ossurl +
        this.videostr[0]['url'] +
        "'style='width:100%'></video></p>"
      this.artEditor.getEditNode().before(str)
      // console.log('111')
    },
    getbq () {
      selectKJFL({ tagcode: 'L002' }).then(res => { //下拉框
        this.labellist = res.data.data
      })
    },
    luck (index) { //链接导入或文章制作
      this.tabclass = index;
    },
    tozy () { //查看操作指引
      this.czzy = true
    },
    delczzy () {//关闭操作指引
      this.czzy = false
    },
    choose () {//选择标签
      let len = document.getElementsByClassName('rate_active').length
      let e = event.currentTarget
      if (e.getAttribute('tab')) {
        e.classList.remove('rate_active')
        e.removeAttribute('tab')
      } else {
        if (len < 2) {
          e.classList.add('rate_active')
          e.setAttribute('tab', 'true')
        } else {
          Toast.info('最多选择两个标签 !')
        }
      }
    },
    choosebq () { //选择标签
      let len = document.getElementsByClassName('choose_active').length
      let e = event.currentTarget
      if (e.getAttribute('tab')) {
        e.classList.remove('choose_active')
        e.removeAttribute('tab')
      } else {
        if (len < 2) {
          e.classList.add('choose_active')
          e.setAttribute('tab', 'true')
        } else {
          Toast.info('最多选择两个标签 !')
        }
      }
    },
    clear () { //清空链接
      this.message = ''
    },
    daoru () { //导入
      let ele = document.getElementsByClassName('rate_active')
      let len = ele.length
      if (this.message == '') {
        Toast.info('请填写链接')
        return
      }
      if (len === 0) {
        Toast.info('请选择标签')
      } else {
        Toast.loading('加载中...')
        let mylabid = []
        for (let i = 0; i < len; i++) {
          mylabid.push(ele[i].getAttribute('data-ins'))
        }
        // console.log(mylabid);
        let data = { bq: mylabid, url: this.message }
        importWZ(data).then( //文章导入
          res => {
            Toast.hide()
            this.$router.push('/Emplist')
          }
        )
      }
    },
    edit () { //保存并发布
      if (this.title == '') {
        Toast.info('请先填写标题')
        return
      }
      var Content = $('#contentEditor').html()
      var Contents = Content.replace('点击此处编辑内容', '')
      var dd = Contents.replace(/<\/?.+?>/g, '')
      var d = dd.replace(/ /g, '')
      var dds = d.replace(/[ ]|[&nbsp;]/g, '')
      var desc = dds.substring(0, 20)
      if (Contents.indexOf('<img') == '-1' && desc == '') {
        Toast.info('请输入内容')
        return
      }
      this.contentdata = "<div style='padding:0 20px;'>" + Contents + '</div>'
      this.bqchoose = true
    },
    tochoose () {
      let ele = document.getElementsByClassName('choose_active')
      let len = ele.length
      if (len === 0) {
        Toast.info('请选择标签')
      } else {
        Toast.loading('加载中...')
        let mylabid = []
        for (let i = 0; i < len; i++) {
          mylabid.push(ele[i].getAttribute('data-ins'))
        }
        let data = { bq: mylabid, title: this.title, text: this.contentdata }
        insertWZ(data).then( //文章制作
          res => {
            Toast.hide()
            this.$router.push('/Emplist')
          },
          res => {
            this.bqchoose = false
          }
        )
      }
    },
    hidePopUp (type) { //隐藏标签弹框
      this.bqchoose = false
    }
  }
}
</script>
<style scoped lang="stylus">
@import './editor.styl';
</style>
