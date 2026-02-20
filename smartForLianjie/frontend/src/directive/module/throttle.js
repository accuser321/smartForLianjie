/*
 * @Author: 王广婷
 * @Date: 2019-08-16 12:05:52
 * @Last Modified by:   王广婷
 * @Last Modified time: 2019-08-16 12:05:52
 */

import Vue from 'vue'
import _ from 'lodash'

/**
 *
 * 使用第三方工具库提供的节流函数,利用Vue支持的自定义指令
 * 把节流函数封装成全局指令避免多次频繁引用第三方库。
 *
 */

Vue.directive('throttle', {
  inserted (el, binding) {
    el.addEventListener('click', _.throttle(binding.value, 1000))
  }
})
