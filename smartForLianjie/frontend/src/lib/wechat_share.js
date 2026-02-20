import {
  GetwxConfig,
  DoRecord
} from '@/api/abt/customerOperation/common/index';
import {
  ForwardWZ
} from '@/api/abt/customerOperation/hkbw/index';
import {
  getStorage
} from '@/lib/util';
import wx from 'weixin-js-sdk';

export function wechatshare(
  title,
  desc,
  shareimage,
  shareurl,
  path,
  fxstr,
  type,
  issource,
  isfirst
) {
  let newpath = path
  if (window.__wxjs_is_wkwebview === true) {
    newpath = window.location.href.split('#')[0] || window.location.href
    if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
      newpath = window.entryUrl
    }
  } else {
    newpath = window.location.href
  }
  // console.log(newpath)
  GetwxConfig({
    url: newpath
  }).then(res => {
    var wxconfig = res.data.data
    wx.config({
      debug: false,
      appId: getStorage('a_d', ''),
      timestamp: wxconfig.timestamp,
      nonceStr: wxconfig.nonceStr,
      signature: wxconfig.signature,
      jsApiList: [
        'onMenuShareAppMessage',
        'onMenuShareTimeline',
        'onMenuShareQQ',
        'onMenuShareQZone',
        'uploadImage',
        'hideOptionMenu',
        'showOptionMenu'
      ]
    })
    wx.ready(function () {
      if (type) {
        if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
          wx.hideOptionMenu()
        } else if (/(Android)/i.test(navigator.userAgent)) {
          if (typeof WeixinJSBridge === 'undefined') {
            // 微信浏览器内置对象。参考微信官方文档
            if (document.addEventListener) {
              document.addEventListener(
                'WeixinJSBridgeReady',
                onBridgeReady(),
                false
              )
            } else if (document.attachEvent) {
              document.attachEvent('WeixinJSBridgeReady', onBridgeReady())
              document.attachEvent('onWeixinJSBridgeReady', onBridgeReady())
            }
            // alert('undefined')
          } else {
            onBridgeReady()
          }
        }
      } else {
        if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
          wx.showOptionMenu()
        } else if (/(Android)/i.test(navigator.userAgent)) {
          if (typeof WeixinJSBridge === 'undefined') {
            // 微信浏览器内置对象。参考微信官方文档
            if (document.addEventListener) {
              document.addEventListener(
                'WeixinJSBridgeReady',
                onBridgeshowReady(),
                false
              )
            } else if (document.attachEvent) {
              document.attachEvent('WeixinJSBridgeReady', onBridgeshowReady())
              document.attachEvent(
                'onWeixinJSBridgeReady',
                onBridgeshowReady()
              )
            }
          } else {
            onBridgeshowReady()
          }
        }
      }
      if (!isfirst) {
        setTimeout(function () {
          ready(title, desc, shareimage, shareurl, path, fxstr)
        }, 500)
      }
    })
  })
}
export function ready(title, desc, shareimage, shareurl, path, fxstr) {
  // alert('我调用分享微信的方法了')
  // alert(title)
  // alert(desc)
  // alert(shareimage)
  // alert(shareurl)
  // 分享到朋友圈
  wx.onMenuShareTimeline({
    title: title,
    link: shareurl, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
    imgUrl: shareimage, // 分享图标
    success: function (res) {
      // eslint-disable-next-line eqeqeq
      if (fxstr != 'no') {
        // eslint-disable-next-line eqeqeq
        if (fxstr.btagcode == '1') {
          ForwardWZ({
            sno: fxstr.sno, //文章编号
            osno: fxstr.osno //原始文章编号
          }).then(res => {})
        }
        DoRecord(fxstr).then(res => {})
      }
    },
    cancel: function (res) {}
  })

  // 分享给朋友
  wx.onMenuShareAppMessage({
    title: title, // 分享标题
    desc: desc, // 分享描述
    link: shareurl, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
    imgUrl: shareimage, // 分享图标
    type: '', // 分享类型,music、video或link，不填默认为link
    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
    success: function (res) {
      // eslint-disable-next-line eqeqeq
      if (fxstr != 'no') {
        // eslint-disable-next-line eqeqeq
        if (fxstr.btagcode == '1') {
          ForwardWZ({
            sno: fxstr.sno,
            osno: fxstr.osno
          }).then(res => {})
        }
        DoRecord(fxstr).then(res => {})
      }
    },
    cancel: function (res) {}
  })
}
export function onBridgeReady() {
  WeixinJSBridge.call('hideOptionMenu')
}

export function onBridgeshowReady() {
  WeixinJSBridge.call('showOptionMenu')
}

export function onBridgecolse() {
  document.addEventListener(
    'WeixinJSBridgeReady',
    function () {
      WeixinJSBridge.call('closeWindow')
    },
    false
  )
  WeixinJSBridge.call('closeWindow')
}
