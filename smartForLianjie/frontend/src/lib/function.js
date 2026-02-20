import html2canvas from 'html2canvas'

//将html页面元素转为png图像
export function makeImg (data) {
  var url = ''
  return new Promise((resolve, reject) => {
    const opts = {
      useCORS: true,
      trainttest: true,
      allowTaint: false,
      scale: 2
    }
    html2canvas(data, opts).then(function (canvas) {
      console.log(canvas)
      url = canvas.toDataURL('image/jpeg', 0.5)
      resolve(url)
    })
  })
}

/**
 * @author 刘格优
 * @param {*} fmt 要转换的时间类型
 * @param 转换时间戳
 */
export function datemate (fmt) {
  var o = {
    'M+': new Date().getMonth() + 1, // 月份
    'd+': new Date().getDate(), // 日
    'h+': new Date().getHours(), // 小时
    'm+': new Date().getMinutes(), // 分
    's+': new Date().getSeconds(), // 秒
    'q+': Math.floor((new Date().getMonth() + 3) / 3), // 季度
    S: new Date().getMilliseconds() // 毫秒
  }
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(
      RegExp.$1,
      (new Date().getFullYear() + '').substr(4 - RegExp.$1.length)
    )
  }
  for (var k in o) {
    if (new RegExp('(' + k + ')').test(fmt)) {
      fmt = fmt.replace(
        RegExp.$1,
        RegExp.$1.length == 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length)
      )
    }
  }
  return fmt
}

/**
 *
 */
export function toDate (now) {
  // console.log(now)
  var year = now.getFullYear() // 取得4位数的年份
  var month = now.getMonth() + 1 // 取得日期中的月份，其中0表示1月，11表示12月
  var date = now.getDate() // 返回日期月份中的天数（1到31）
  var hour = now.getHours() // 返回日期中的小时数（0到23）
  var minute = now.getMinutes() // 返回日期中的分钟数（0到59）
  var second = now.getSeconds() // 返回日期中的秒数（0到59）
  month = month < 10 ? '0' + month : month
  date = date < 10 ? '0' + date : date
  hour = hour < 10 ? '0' + hour : hour
  minute = minute < 10 ? '0' + minute : minute
  second = second < 10 ? '0' + second : second
  return (
    year + '-' + month + '-' + date + ' ' + hour + ':' + minute + ':' + second
  )
}
