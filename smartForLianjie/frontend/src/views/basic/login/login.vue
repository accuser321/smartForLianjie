<!--
 * @Author: 王鹏
 * @Date: 2019-10-29 16:24:06
 * @LastEditors: 王广婷
 * @LastEditTime: 2020-03-23 17:19:27
 -->
<template>
  <div class="container">
    <div class="logo">
      <img :src="sys_info.applogo"
           alt=""
           :class="sys_info.applogo?'':'img_style'">
    </div>
    <div ref="W"
         v-if="sys_info.userange=='W'">
      <md-field>
        <md-input-item v-model="Wform.empno"
                       name="empno"
                       v-validate="'required'"
                       :error="errors.first('empno')"
                       placeholder="请输入工号"
                       @change="Wform.empno = $transform.transform(Wform.empno)">
          <template #left>
            <i class="iconfont iconyonghu" />
          </template>
        </md-input-item>
      </md-field>
      <md-field>
        <md-input-item :type="shiftKey ? 'text' :'password'"
                       v-model="Wform.password"
                       name="password"
                       v-validate="'required'"
                       :error="errors.first('password')"
                       placeholder="请输入密码">
          <template #left>
            <i class="iconfont iconmima" />
          </template>
          <template #right>
            <md-icon @click="shiftKey = !shiftKey"
                     :name="shiftKey ? 'visible' :'invisible'"
                     size="lg" />
          </template>
        </md-input-item>
      </md-field>
    </div>
    <div ref="N"
         v-else-if="sys_info.userange=='N'">
      <md-field>
        <md-input-item v-model="Nform.empno"
                       name="empno"
                       v-validate="'required'"
                       :error="errors.first('empno')"
                       placeholder="请输入工号">
          <template #left>
            <i class="iconfont iconyonghu" />
          </template>
        </md-input-item>
      </md-field>
      <md-field>
        <md-input-item :type="shiftKey ? 'text' :'password'"
                       v-model="Nform.password"
                       name="password"
                       v-validate="'required'"
                       :error="errors.first('password')"
                       placeholder="请输入密码">
          <template #left>
            <i class="iconfont iconmima" />
          </template>
          <template #right>
            <md-icon @click="shiftKey = !shiftKey"
                     :name="shiftKey ? 'visible' :'invisible'"
                     size="lg" />
          </template>
        </md-input-item>
      </md-field>
    </div>
    <md-tabs v-else v-model="current">
      <md-tab-pane ref="W"
                   class="content"
                   :name="Wform.type"
                   label="外勤">
        <div v-if="current=='W'">
          <md-field>
            <md-input-item v-model="Wform.empno"
                           name="empno"
                           v-validate="'required'"
                           :error="errors.first('empno')"
                           placeholder="请输入工号"
                           @change="Wform.empno = $transform.transform(Wform.empno)">
              <template #left>
                <i class="iconfont iconyonghu" />
              </template>
            </md-input-item>
          </md-field>
          <md-field>
            <md-input-item :type="shiftKey ? 'text' :'password'"
                           v-model="Wform.password"
                           name="password"
                           v-validate="'required'"
                           :error="errors.first('password')"
                           placeholder="请输入密码">
              <template #left>
                <i class="iconfont iconmima" />
              </template>
              <template #right>
                <md-icon @click="shiftKey = !shiftKey"
                         :name="shiftKey ? 'visible' :'invisible'"
                         size="lg" />
              </template>
            </md-input-item>
          </md-field>
        </div>
      </md-tab-pane>
      <md-tab-pane ref="N"
                   class="content"
                   :name="Nform.type"
                   label="内勤">
        <div v-if="current=='N'">
          <md-field>
            <md-input-item v-model="Nform.empno"
                           name="empno"
                           v-validate="'required'"
                           :error="errors.first('empno')"
                           placeholder="请输入工号">
              <template #left>
                <i class="iconfont iconyonghu" />
              </template>
            </md-input-item>
          </md-field>
          <md-field>
            <md-input-item :type="shiftKey ? 'text' :'password'"
                           v-model="Nform.password"
                           name="password"
                           v-validate="'required'"
                           :error="errors.first('password')"
                           placeholder="请输入密码">
              <template #left>
                <i class="iconfont iconmima" />
              </template>
              <template #right>
                <md-icon @click="shiftKey = !shiftKey"
                         :name="shiftKey ? 'visible' :'invisible'"
                         size="lg" />
              </template>
            </md-input-item>
          </md-field>
        </div>
      </md-tab-pane>
    </md-tabs>
    <div class="btn">
      <md-button type="primary"
                 :loading="toLogin"
                 @click="doLogin">确定</md-button>
    </div>
  </div>
</template>
<script>
import { InputItem, Field } from 'mand-mobile'
import { login, getInit } from '@/api/basic'
import { setStorage, getStorage } from '@/lib/util'
import { mapGetters } from 'vuex'

export default {
  components: {
    [InputItem.name]: InputItem,
    [Field.name]: Field
  },
  data () {
    return {
      Wform: {
        empno: '',
        password: '',
        type: 'W'
      },
      Nform: {
        empno: '',
        password: '',
        type: 'N'
      },
      shiftKey: false,
      toLogin: false,
      current: 'W',
      sys_info: {}
    }
  },
  created () {
    this.sys_info = getStorage('sys_info', {})
    this.sys_info.userange ? (this.sys_info.userange == 'ALL' ? '' : this.current = this.sys_info.userange) : ''
  },
  computed () {
    mapGetters(['user'])
  },
  watch: {
    current () {
      this.Wform = { empno: '', password: '', type: 'N' }
      this.Nform = { empno: '', password: '', type: 'N' }
    }
  },
  methods: {
    doLogin () {
      this.$validator.validate().then(valid => {
        if (valid) {
          this.toLogin = true
          login(this.current == 'W' ? this.Wform : this.Nform).then(() => {
            //  debugger;
            /*
            * 修改用户信息
            * */
             getUserInfo().then(res=>{
              setStorage('u_s', res.data.data.data.user)
             this.$router.push({ path: '/' })
            })
            // getInit().then(res => {
            //   setStorage('u_s', res.data.data.user)
            //   setStorage('m_l', res.data.data.menulist)
            //   setStorage('ossurl', res.data.data.ossurl)
            //   setStorage('msossurl', res.data.data.msossurl)
            //   setStorage('comname', res.data.data.comname)
            //   this.$router.push({ path: '/' })
            // })
            //  this.$router.push({ path: '/' })
          }).finally(() => {
            this.toLogin = false
          })
        }
      })
    }
  }
}
</script>
<style lang="stylus" scoped>
.container {
  padding: 120px 75px;
}

.logo {
  width: 90%;
  height: 200px;
  margin: auto auto 140px auto;
  .img_style {
    opacity: 0;
  }
}

.md-field {
  padding: 20px;
}

.iconfont {
  font-size: 18px;
}

.md-field-item-content::before {
  border-bottom-color: #E2E2E2;
}

.btn {
  margin-top: 100px;
}

.md-example-child-tabs, .content {
  padding: 12px 0;
  font-size: 28px;
  background: #FFF;
  padding: 20px;
  line-height: 1.5;
  box-sizing: border-box;
}

.md-tabs-content {
  min-height: 200px;
  background: #FFF;
}
  /deep/ .md-button.block{
    height: 1.2rem;
  }
  /deep/ .md-tab-bar-item{
    font-size: 0.43rem;
  }
  /deep/ .md-input-item .md-field-item-content{
    height: 1.3rem;
  }
  /deep/ .md-input-item-input, .md-input-item-fake{
    font-size: 0.43rem;
  }
</style>
