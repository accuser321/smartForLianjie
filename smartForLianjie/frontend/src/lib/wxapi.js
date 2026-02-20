import {
  GetwxConfig,
  uploadByMediaId
} from '@/api/abt/customerOperation/common';
// import { Uploadpic } from '@/api/abt/customerOperation/common'
import { getStorage } from '@/lib/util';
import wx from 'weixin-js-sdk';
// import config from '@/config';

// 获取微信配置
export function getConfig (path, type) {
  let newpath = path
  if (window.__wxjs_is_wkwebview === true) {
    newpath = window.location.href.split('#')[0] || window.location.href
    if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
      newpath = window.entryUrl
    }
  } else {
    newpath = window.location.href
  }
  GetwxConfig({ url: newpath }).then(res => {
    var wxconfig = res.data.data
    wx.config({
      debug: false,
      appId: getStorage('a_d', ''),
      timestamp: wxconfig.timestamp,
      nonceStr: wxconfig.nonceStr,
      signature: wxconfig.signature,
      jsApiList: [
        'onMenuShareTimeline',
        'onMenuShareAppMessage',
        'startRecord',
        'stopRecord',
        'playVoice',
        'pauseVoice',
        'onVoiceRecordEnd',
        'onVoicePlayEnd',
        'stopVoice',
        'uploadVoice',
        'downloadVoice',
        'chooseImage',
        'previewImage',
        'uploadImage',
        'wxpreviewImage',
        'openLocation',
        'getLocation',
        'closeWindow'
      ]
    })
  })
}

let voice = {
  localId: '',
  serverId: ''
}
let START = 0
let END = 0
let recordTimer = null

// 录音开始
export function voiceStart () {
  // let e = window.event
  return new Promise((resolve, reject) => {
    wx.ready(function () {
      // e.preventDefault()
      START = new Date().getTime()
      recordTimer = setTimeout(function () {
        wx.startRecord({
          success: function () {
            localStorage.rainAllowRecord = 'true';
          },
          cancel: function () {
            // alert('用户拒绝授权录音')
            resolve('fail')
          }
        })
      }, 300)
      // 监听录音自动停止
      wx.onVoiceRecordEnd({
        complete: function (res) {
          voice.localId = res.localId
          // alert('录音时间已经超过一分钟')
          resolve('fail')
        }
      })
    })
  })
}

// 获取地址
export function getlocation () {
  // let e = window.event
  return new Promise((resolve, reject) => {
    setTimeout(function () {
      wx.ready(function () {
        wx.getLocation({
          success: function (res) {
            // let latitude = res.latitude + 0.004880706848;
            // let longitude = res.longitude + 0.012723672143;
            // return res
            resolve(res)
          },
          cancel: function (res) {
            // alert('用户拒绝授权获取地理位置');
          }
        })
      })
    }, 0)
  })
}

// 录音结束，上传到三方服务器
// avg为用户的userid
export function voiceEnd () {
  // let e = window.event
  return new Promise((resolve, reject) => {
    wx.ready(function () {
      // e.preventDefault()
      END = new Date().getTime()
      if (END - START < 300) {
        END = 0
        START = 0
        // 小于300ms，不录音
        clearTimeout(recordTimer)
      } else {
        wx.stopRecord({
          success: function (res) {
            voice.localId = res.localId
            // 调用微信的上传录音接口把本地录音先上传到微信的服务器
            // 不过，微信只保留3天，而我们需要长期保存，我们需要把资源从微信服务器下载到自己的服务器

            wx.uploadVoice({
              // 需要上传的音频的本地ID，由stopRecord接口获得
              localId: voice.localId,
              // 默认为1，显示进度提示
              isShowProgressTips: 1,
              success: function (resserver) {
                let data = {
                  show: voice.localId,
                  save: resserver.serverId
                }
                resolve(data)
              }
            })
          },
          fail: function (res) {
            reject(res)
          }
        })
      }
    })
  })
}
// 播放录音
export function voiceplay () {
  return new Promise((resolve, reject) => {
    wx.ready(function () {
      wx.playVoice({
        localId: voice.localId // 需要播放的音频的本地ID，由stopRecord接口获得
      })
      wx.onVoicePlayEnd({
        success: function (res) {
          resolve(res.localId) // 返回音频的本地ID
        }
      })
    })
  })
}
// 暂停播放录音
export function voiceplayend () {
  return new Promise((resolve, reject) => {
    wx.ready(function () {
      wx.pauseVoice({
        localId: voice.localId // 需要播放的音频的本地ID，由stopRecord接口获得
      })
    })
  })
}
// export function voiceplayisend () {
//   wx.onVoicePlayEnd({
//     success: function (res) {
//       // var localId = res.localId; // 返回音频的本地ID
//     }
//   })
// }

// 图片预览
export function wxpreviewImage (current, urls) {
  wx.ready(function () {
    wx.previewImage({
      current: current,
      urls: urls
    })
  })
}
export function wxpicUpload (num) {
  return new Promise((resolve, reject) => {
    let localIds = []
    // alert(3)
    // let e = window.event
    wx.ready(function () {
      wx.chooseImage({
        count: num || 9,
        needResult: 1,
        sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
        success: function (data) {
          localIds = data.localIds // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
          upload()
        },
        fail: function (res) {
          // alterShowMessage("操作提示", JSON.stringify(res), "1", "确定", "", "", "");
          reject(res)
        }
      })
    })

    var i = 0
    var serverId = []
    function upload () {
      wx.uploadImage({
        localId: localIds[i].toString(),
        success: function (res) {
          i++
          serverId.push(res.serverId)
          if (i < localIds.length) {
            upload()
          } else {
            let data = {
              // show: localIds,
              wx: serverId
            }
            uploadByMediaId(data).then(reson => {  //上传我的照片
              resolve(reson.data.data)
            })
          }
        },
        fail: function (res) {
          reject(JSON.stringify(res))
        }
      })
    }
  })
}

// 微信支付
// export function callWxPay (params) {
//   return new Promise((resolve, reject) => {
//     if (typeof WeixinJSBridge === 'undefined') {
//       if (document.addEventListener) {
//         document.addEventListener('WeixinJSBridgeReady', jsApiCall(params), false)
//       } else if (document.attachEvent) {
//         document.attachEvent('WeixinJSBridgeReady', jsApiCall(params))
//         document.attachEvent('onWeixinJSBridgeReady', jsApiCall(params))
//       }
//     } else {
//       jsApiCall(params)
//     }

//     function jsApiCall (params) {
//       let that = this
//       WeixinJSBridge.invoke(
//         'getBrandWCPayRequest', {
//           'appId': params.appId,
//           'timeStamp': params.timeStamp,
//           'nonceStr': params.nonceStr,
//           'package': params.package,
//           'signType': params.signType,
//           'paySign': params.paySign
//         },
//         function (res) {
//           resolve(res)
//         })
//     }
//   })
// }
