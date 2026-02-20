/*
 * @Author: 王鹏
 * @Date: 2019-09-12 11:53:23
 * @LastEditors: 王鹏
 * @LastEditTime: 2019-10-31 10:17:12
 */
import Vue from 'vue'
import VeeValidate, { Validator } from 'vee-validate'
import zh_CN from 'vee-validate/dist/locale/zh_CN'
import { CN } from './message'
import { extendRules } from './rules'
import { merge } from 'lodash'

const cn = merge(zh_CN, CN)
Vue.use(VeeValidate, {
  locale: 'cn',
  dictionary: {
    cn
  },
  events: 'input',
  fieldsBagName: 'fields',
  delay: 500
})

extendRules(Validator)
