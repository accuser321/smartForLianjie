/*
 * @Author: 王鹏
 * @Date: 2019-10-29 17:25:38
 * @LastEditors: 王鹏
 * @LastEditTime: 2019-11-01 10:23:05
 */
import FastClick from 'fastclick'

if ('addEventListener' in document && 'ontouchstart' in window) {
  FastClick.prototype.focus = function (targetElement) {
    targetElement.focus()
  }
  document.addEventListener('DOMContentLoaded', function () {
    FastClick.attach(document.body)
  }, false)
}
