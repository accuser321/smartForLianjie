<!--
 * @Author: 刘格优
 * @Date: 2019-11-13 17:31:30
 * @LastEditors  : 刘格优
 * @LastEditTime : 2020-01-11 23:37:31
 -->

<template>
  <div class="allcontent">
    <div class="clicontent">
      <div id="topbox">
        <div class="topback">
          <div class="top clearfix">
            <img class="fl headimg" :src="form.headimg" />
            <div class="name fl">
              <div>{{ form.nickname }}</div>
              <div>已经成为你的客户</div>
            </div>
          </div>
        </div>
      </div>
      <md-tab-bar
        class="tabbar"
        v-model="current"
        :items="options"
        @change="onChange"
      />
    </div>
    <div class="tabcontent">
      <!-- 人脉 -->
      <div v-show="current == '1'">
        <analysis ref="chart1" :userid="userid" :sno="sno"></analysis>
      </div>
    </div>
    <!-- 选择标签弹框 -->
  </div>
</template>

<script>
import interact from './interact/index'
import aiana from './aiana/index'
import follow from './follow/index'
import analysis from './analysis/index'
import { getStorage } from '@/lib/util'
import {
  selectKHBQ,
  getCustomMessage,
  insertCustomBasemsg,
  insertCustomLabel,
  insertKHLabel
} from '@/api/abt/customerOperation/clientsort/index'
import { selectMJ } from '@/api/abt/customerOperation/common/index'
import { Toast } from 'mand-mobile'
import { cloneDeep } from 'lodash'
// import Consult from '@/views/abt/customerOperation/common/consulation/index';
export default {
  components: {
    interact,
    aiana,
    follow,
    analysis
  },
  data () {
    return {
      show: false,
      bqchoose: false,
      addlabname: false,
      bjVisible: false,
      isDatePickerShow: false,
      isSelectorShow: false,
      bqlist: [],
      zjlist: [],
      labelname: '',
      userid: '',
      selectorValue: '',
      user: {},
      empno: '',
      sno:"",
      form: {
        day: 0,
        headimg: '',
        nickname: '',
        intime: '',
        labelList: [],
        sex: '',
        mobile: '',
        cardtype: '',
        cardno: '',
        birthdate: '',
        ismarried: '',
        childnum: '',
        address: '',
        company: '',
        remark: ''
      },
      cardno: '',
      qttype: '',
      current: 1,
      options: [
        { name: 1, label: '人脉' },
        // { name: 2, label: 'AI分析' },
        // { name: 3, label: '互动' },
        // { name: 4, label: '跟进' }
      ],
      basicDialog: {
        open: false,
        btns: [
          {
            text: '取消',
            handler: this.onBasicCancel
          },
          {
            text: '确认',
            handler: this.onBasicConfirm
          }
        ]
      }
    }
  },
  watch: {
    cardno (newval) {
      if (!newval) {
        return
      }
      let sfznum = newval.split('')
      if (sfznum.length == '18') {
        this.form.birthdate =
          newval.substr(6, 4) +
          '-' +
          newval.substr(10, 2) +
          '-' +
          newval.substr(12, 2)
      }
    },
    current (v) {
      if (v == '2') {
        this.$nextTick(_ => {
          // console.log(this.$refs[`chart${v}`])
          this.$refs[`chart${v}`].$refs[`chart${v}`].echarts.resize()
          this.$refs[`chart${v}`].$refs['chart1'].echarts.resize()
          this.$refs[`chart${v}`].$refs['chart3'].echarts.resize()
          this.$refs[`chart${v}`].$refs['chart4'].echarts.resize()
          this.$refs[`chart${v}`].$refs['chart5'].echarts.resize()
        })
      }
    }
  },

  created () {
    this.userid = this.$route.query.customer
    this.sno=this.$route.query.sno
    this.user = getStorage('u_s', {})
    // this.userid = '10000016'
    this.getkhinfo()
  },

  mounted () {
    $(document).on('focusout', function () {
      setTimeout(() => {
        // 软键盘收起的事件处理
        window.scroll(0, 0)
      }, 300)
    })
  },

  methods: {
    getkhinfo () {  //获取用户消息
      getCustomMessage({ userid: this.userid }).then(res => {
        if (res.data.data) {
          // console.log("用户信息")
          // console.log(res.data.data.user)
          this.form = res.data.data.user
        //   if (this.form.childnum) {
        //     this.form.childnum = this.form.childnum.toString()
        //   }
        //   if (this.form.birthdate) {
        //     this.form.birthdate = this.form.birthdate.substr(0, 10)
        //   }
        //   this.cardno = this.form.cardno
        }
        // this.getzjtype(this.form.cardtype)
      })
    },
    savekuinfo () {
      let data = cloneDeep(this.form)
      data.userid = this.userid
      data.cardno = this.cardno
      delete data.labelList
      insertCustomBasemsg(data).then(res => {
        Toast.info('编辑成功')
        this.getkhinfo()
        this.bjVisible = false
      })
    },
    editinfo () {
      this.bjVisible = true
      this.getkhinfo()
    },
    getzjtype (type) {  //枚举下拉框
      selectMJ({ list: ['RSCARD'] }).then(res => {
        this.zjlist = res.data.data[0].value
        this.zjlist.forEach((item, index) => {
          item.value = item.code
          item.text = item.name
          if (type === item.code) {
            this.selectorValue = item.name
          }
        })
      })
    },
    tochoose () {
      let surebq = []
      this.bqlist.forEach((item, index) => {
        item.children.forEach((ite, i) => {
          if (ite.show) {
            let bqitem = {
              labid: ite.id,
              labtype: ite.parentId
            }
            surebq = surebq.concat(bqitem)
          }
        })
      })
      insertKHLabel({ userid: this.userid, array: surebq }).then(res => {
        Toast.info('添加成功')
        this.getkhinfo()
        this.bqchoose = false
      })
    },
    getbq () {
      selectKHBQ().then(res => {
        this.bqlist = res.data.data
        this.bqlist.forEach((item, index) => {
          if (item.tagname === '其他') {
            this.qttype = item.id
          }
          item.children.forEach((ite, i) => {
            this.$set(ite, 'show', false)
            if (this.form.labelList.length != '0') {
              this.form.labelList.forEach((i, ind) => {
                if (ite.id == i.labcode) {
                  this.$set(ite, 'show', true)
                }
              })
            }
          })
        })
        this.bqchoose = true
      })
    },
    hidePopUp (type) {
      this.bqchoose = false
    },
    chselabes (item) {
      item.show = !item.show
    },
    onBasicConfirm () {
      insertCustomLabel({
        userid: this.userid,
        labtype: this.qttype,
        labname: this.labelname
      }).then(res => {
        Toast.info('添加成功')
        this.getbq()
        this.addlabname = false
      })
    },
    onBasicCancel () {
      this.addlabname = false
    },
    open () {
      this.isDatePickerShow = true
    },
    onDatePickerConfirm (columnsValue) {
      this.form.birthdate = this.$refs.datePicker.getFormatDate('yyyy-MM-dd')
    },
    showSelector () {
      this.isSelectorShow = true
    },
    onSelectorChoose ({ value, text }) {
      this.selectorValue = text
      this.form.cardtype = value
    },
    onChange (item, index, prevIndex) {
      // Toast.info(`index: ${index}, prevIndex: ${prevIndex}, label: ${item.label}`)
    },
    tozxzx () {
      this.$router.push(
        `/Consult?askuserid=${this.userid}&empno=${this.user.empno}&emprytype=${this.user.rytype}`
      )
    }
  }
}
</script>
<style scoped lang="stylus">
@import './index.styl';
</style>
