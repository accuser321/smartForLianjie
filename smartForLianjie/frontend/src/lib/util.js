/*
 * @Author: 王鹏
 * @Date: 2019-10-29 16:31:49
 * @LastEditors  : 贾峰
 * @LastEditTime : 2020-01-15 10:05:26
 */

import { isObject, isString } from 'lodash'
import './utils.css'
const path = require('path')
export const readDirfiles = files => {
  const modules = {}
  files.keys().forEach(key => {
    const name = path.basename(key, '.vue')
    modules[name] = files(key).default || files(key)
  })
  return modules
}

/**
 *
 * 用于后台传回二进制数据处理成文件并下载
 *
 * @param {ArrayBuffer,blob} data  二进制数据
 * @param {String} type 指示资源的MIME类型
 * @param {String} name 指定下载的文件名字
 *
 * @return {undefined}
 *
 * @Author 王鹏
 */
export const downFileStream = (blob, name) => {
  const url = window.URL.createObjectURL(blob)
  const link = document.createElement('a')
  link.style.display = 'none'
  link.href = url
  link.setAttribute('download', name)
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
  window.URL.revokeObjectURL(url)
}

/**
 * 获取 Storage 中的数据
 *
 * @param {String} key
 * @param {*} Default  兼容获取空值的情况
 *
 * @return {*}
 *
 * @Author 王广婷
 */

export const getStorage = (key, Default = '') => {
  if (process.env.NODE_ENV === 'production') {
    let t = window.sessionStorage.getItem(key)
    t = isString(t) && t.indexOf('{') !== -1 ? JSON.parse(t) : t
    return t || Default
  } else {
    let t = window.localStorage.getItem(key)
    t = isString(t) && t.indexOf('{') !== -1 ? JSON.parse(t) : t
    return t || Default
  }
}

/**
 * 设置 Storage 中的数据
 *
 * @param {String} key
 * @param {*} data
 *
 * @return {undefined}
 *
 * @Author 王广婷
 */
export const setStorage = (key, data) => {
  if (process.env.NODE_ENV === 'production') {
    return window.sessionStorage.setItem(
      key,
      (isObject(data) && JSON.stringify(data)) || data
    )
  } else {
    return window.localStorage.setItem(
      key,
      (isObject(data) && JSON.stringify(data)) || data
    )
  }
}
/**
 * 清除 Storage 中的所有数据
 *
 * @param {String} key
 * @param {*} data
 *
 * @return {undefined}
 *
 * @Author 王广婷
 */
export const clearStorage = () => window.sessionStorage.clear()
/**
 * 清除 Storage 中的某一个数据
 *
 * @param {String} key
 * @param {*} data
 *
 * @return {undefined}
 *
 * @Author 王广婷
 */
export const rmStorage = key => window.sessionStorage.removeItem(key)
/* --------------------------------------------------------------------- */
/**
 *
 * 检查是否为图片文件
 *
 * @param {String} path 图片路径
 *
 * @return {Boolean}
 */

export function checkImgFile (path) {
  return /\.(png|jpe?g|gif|svg)$/i.test(path)
}

/**
 *
 * 创建图片路径
 *
 * @param {Object} file 文件对象
 *
 * @return {String}
 */

export function convertPath (file) {
  let url = window.URL || window.webkitURL
  return url.createObjectURL(file)
}

/**
 *
 * 兼容移动端弹框弹出后
 * 页面还可以滑动
 *
 */
export let ModalHelper = (function (bodyClass) {
  var scrollTop

  return {

    afterOpen: function () {
      scrollTop = document.scrollingElement.scrollTop

      document.body.classList.add(bodyClass)

      document.body.style.top = -scrollTop + 'px'
    },

    beforeClose: function () {
      document.body.classList.remove(bodyClass)

      document.scrollingElement.scrollTop = scrollTop
    }

  }
})('modal-open')
export function stopScroll () {
  var scrollTop = document.body.scrollTop || document.documentElement.scrollTop
  document.body.style.cssText += 'position:fixed;top:-' + scrollTop + 'px;'
  console.log(document.body.style.cssText)
}

export function canScroll () {
  var body = document.body
  body.style.position = ''
  var top = body.style.top
  document.body.scrollTop = document.documentElement.scrollTop = -parseInt(top)
  console.log(document.body.scrollTop, document.documentElement.scrollTop)
  body.style.top = ''
}

/**
 *
 * 数字格式化
 *
 * @param {number} number 输入数字.
 * @param {number} decimalDigit 小数点后最多位数，默认为2
 * @return {string} 加上单位后的数字
 */

export function addChineseUnit (number, decimalDigit) {
  decimalDigit = decimalDigit == null ? 2 : decimalDigit
  var integer = Math.floor(number)
  var digit = getDigit(integer)
  var unit = []
  if (digit > 3) {
    var multiple = Math.floor(digit / 8)
    if (multiple >= 1) {
      var tmp = Math.round(integer / Math.pow(10, 8 * multiple))
      unit.push(addWan(tmp, number, 8 * multiple, decimalDigit))
      for (var i = 0; i < multiple; i++) {
        unit.push('亿')
      }
      return unit.join('')
    } else {
      return addWan(integer, number, 0, decimalDigit)
    }
  } else {
    return number
  }
}

function addWan (integer, number, mutiple, decimalDigit) {
  var digit = getDigit(integer)
  if (digit > 3) {
    var remainder = digit % 8
    if (remainder >= 5) {
      remainder = 4
    }
    return Math.round(number / Math.pow(10, remainder + mutiple - decimalDigit)) / Math.pow(10, decimalDigit) + '万'
  } else {
    return Math.round(number / Math.pow(10, mutiple - decimalDigit)) / Math.pow(10, decimalDigit)
  }
}

function getDigit (integer) {
  var digit = -1
  while (integer >= 1) {
    digit++
    integer = integer / 10
  }
  return digit
}

/**
 *
 * 兼容移动端软键盘弹出
 * 影响使用fixed定位的布局
 *
 */

export function compatibleFixed () {
  let winHeight = window.innerHeight
  window.addEventListener('resize', function () {
    let selfHeight = this.innerHeight
    if (winHeight - selfHeight > 140) {
      document.querySelector('.zjimg').style.zIndex = '-1'
    } else {
      document.querySelector('.zjimg').style.zIndex = '1'
    }
  }, false)
}

// 翻译机构树
let a = 0
export const traverseTree = obj => {
  if (!obj) {
    return
  }
  obj.label = obj.name
  obj.value = obj.tjcode
  let options = []
  if (obj.children && obj.children.length > 0) {
    options = obj.children
    obj.children = {}
    obj.children.options = options
    obj.children.name = 'block' + a
    obj.children.label = '请选择'
    var i = 0
    for (i = 0; i < obj.children.options.length; i++) {
      traverseTree(obj.children.options[i])
    }
  }
}
