<!--
 * @Descripttion:
 * @version:
 * @Author: 贾峰
 * @Date: 2020-01-13 10:10:12
 * @LastEditors: 贾峰
 * @LastEditTime: 2020-03-12 18:27:19
 -->
<template>
    <div>
        <md-field>
        <md-field-item disabled title="建议反馈" class="tjmes" />
        <md-cell-item v-for="(item,index) in explainlist" :key="index" :title="'建议'+(index+1)+':'">
          <div slot="right">
            <md-icon :name="item.status === '0'?'time':'right'" size="lg" color="orange"></md-icon>
            <span> {{item.status === '0'? ' 未处理':'已处理'}} </span>
          </div>
          <p slot="children">{{item.tsreason}}</p>
        </md-cell-item>
              <textarea
                maxlength="200"
                v-model="remark"
                style="border-top:1px dashed gray;border-radius:5px;background-color:white;width: 100%;height: 160px;padding: 10px;resize: none;margin-top: 20px"
                placeholder="请输入建议...(最多可输入200个字)"
                class="remark"
              />
        </md-field>
        <md-button
          :loading="submitstatus"
          @click="submit"
          :disabled="btnstatus"
          >{{  !btnstatus ? (remark !== '' ? '确认并提交' : '提交') : '已提交' }}</md-button
        >
    </div>
</template>
<script>
import { Toast } from 'mand-mobile'
import { addComplain,complainlist} from '@/api/basic'
export default {
  data () {
    return {
      remark: '',
      submitstatus: false,
      btnstatus: false,
      explainlist:[]
    }
  },
  created() {
    this.getlist()
  },
  methods: {
    getlist(){
      complainlist().then(res=>{
      this.explainlist = res.data.data
    })
    },
    submit () {
      if (this.remark === '') {
        Toast.failed('请填写建议')
        return false
      }
      this.submitstatus = true
      addComplain({ tsreason: this.remark }).then(res => { Toast.succeed('建议提交成功'); this.submitstatus = false; this.btnstatus = true;this.getlist()})
    }
  }
}
</script>
<style lang="stylus" scoped>
/deep/ .tjmes {
  .md-field-item-title {
    font-size 35px
    padding-left: 0;
    font-weight: bold;
  }
}
/deep/ .md-button{
        background-color color-primary
        height 100px
        border-radius 8px
        width 90vw
        margin 0 5vw
        color white
        margin-top 80px
    }
    .remark:focus{
        box-shadow: 0 4px 8px rgba(0,0,0,0.175);
    }
</style>
