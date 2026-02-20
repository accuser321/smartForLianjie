/*
 * @Author: 王鹏
 * @Date: 2019-10-29 14:56:00
 * @LastEditors: 王广婷
 * @LastEditTime: 2020-04-01 15:35:24
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
import routes from './routers'
import store from '@/store'
import { getWxURLInfo } from '@/api/basic'
import { setStorage, getStorage } from '@/lib/util'
import config from '@/config'
import { Dialog } from 'mand-mobile'

Vue.use(VueRouter)

const router = new VueRouter({
  // base:'/abt/',
  routes,
  mode: 'history',
  base:'/article/'
})

/** 获取当前的hostname
 * 根据域名获取hostname
 * 目前一共有三种:
 * 1.  nhwx(南华网销)————{对应的的登录页路由是'/login',对应的的首页路由是'/'}
 * 2.  nhsx(南华寿险)————{对应的的登录页路由是'/sxlogin',对应的的首页路由是'/sxhome'}
 * 3.  hcdl(汇才代理)————{对应的的登录页路由是'/sxlogin',对应的的首页路由是'/sxhome'}
*/
// let HOSTNAME = 'nhwx'
// let HN_ROOT = ['nhsx', 'hcdl']

/*
 * no表示该页面游客不能直接访问
 * yes表示该页面游客可以直接访问
 * 但是这两种情况都需要和微信交互获取token
 * 而pass表示直接可以访问并且不需要再和微信交互获取token
 * */

let valid = {
  '/': 'no',
  '/confirm': 'yes',
  '/addmember': 'yes', //入司申请
  '/report': 'yes',  //家庭报告
  '/policydetail': 'yes', //解析报告
  '/detail': 'yes',  //个人报告
  '/visitingcard': 'yes', //个人名片
  '/Empshare': 'yes',
  '/Emparticle': 'yes',
  '/greetingcard/cardshare': 'yes',   //分享贺卡
  '/invitationletter/invite': 'yes',  //报名邀约
  '/sharereport': 'yes', //分享报告
  '/website': 'yes',    //微官网
  '/nbsReport': 'yes',  //NBS报告
  '/customercenter': 'yes', // 客户中心
  '/bdtg': 'yes', // 家庭列表
  '/responsibility': 'yes', // 计划书
  '/generate': 'yes', // 产品对比报告
  '/looked': 'no', // 消息中心
  '/acquisitionposter': 'no', // 展业海报
  '/Libwarticle': 'yes', // 文章
  '/Consult': 'yes', // 咨询
  '/recommendpage': 'yes', // 保单托管客户扫码
  '/productShow': 'yes', // 产品展示分享
  '/Invitenew': 'yes', // 邀新页面
  '/microshop': 'yes', // 门店
  '/productShowNhwx': 'yes' // 产品分享页面
}

// 验证会员身份的客户
let ISSHARE = false
let HY_ROOT = [
  '/toInvitenew',  //邀新
  '/tuoke',   //拓客
  '/microshop', //个人微店
  '/morermhd',  //热门活动
  '/orderform', //订单详情
  '/renewal',   //续期保单
  '/policycheck', //保单检视
  '/study',  //学习
  '/productShowNhwx',//产品分享页面
  '/clientsort' //客户挖掘
]

function Oauth (replace_url, router, valid) {
  if (process.env.NODE_ENV === 'production') {
    getWxURLInfo().then(res => {   //获取APPID
      /*
       * 保存要访问的路由对应值和路由
       * */
      setStorage('valid', valid)
      setStorage('r_t', router)
      setStorage('ossurl', res.data.ossurl)
      console.log("数据")
      console.log(res.data)
      let {
        appid,
        scope,
        userange,
        sharelogo,
        applogo,
        appsysname,
        stylenum,
        comlogo,
        leadimg,
        mplogo
      } = res.data
      setStorage('a_d', appid)
      setStorage('s_n', stylenum)
      setStorage('sys_info', {
        userange,
        sharelogo,
        applogo,
        appsysname,
        stylenum,
        comlogo,
        leadimg,
        mplogo
      })
      console.log("appid不能为空！");
      console.log(appid)
      console.log(`https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appid}&redirect_uri=${replace_url}&response_type=code&scope=${scope}&state=STATE#wechat_redirect`)
      window.location.replace(
        `https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appid}&redirect_uri=${replace_url}&response_type=code&scope=${scope}&state=STATE#wechat_redirect`
      )
    })
  } else {
    localStorage.setItem('u_s', JSON.stringify(getStorage('u_s', {})))
    localStorage.setItem('i_f', JSON.stringify(getStorage('i_f', {})))
    localStorage.setItem('m_l', JSON.stringify(getStorage('m_l', {})))
    localStorage.setItem('r_t', JSON.stringify(getStorage('r_t', '')))
    localStorage.setItem('ossurl', JSON.stringify(getStorage('ossurl', '')))
    localStorage.setItem('valid', JSON.stringify(getStorage('valid', '')))
    // 本地开发默认南华网销的身份
    localStorage.setItem('hostname', 'nhwx')
    localStorage.setItem(
      'sys_info',
      JSON.stringify(getStorage('sys_info', ''))
    )
  }
}

let isEntry = () => {
  if (
    getStorage('u_s', '').rytype == 'W' ||
    getStorage('u_s', '').rytype == 'N' ||
    getStorage('u_s', '').rytype == 'M'
  ) {
    return true
  } else {
    return false
  }
}

router.beforeEach((to, from, next) => {
  /**
   * 判断即将进入的页面的地址是否有 isshare 参数
   * 当前用户是会员身份时会用到isshare参数
   */
  ISSHARE = to.query.isshare ? to.query.isshare : false
  /**
   * 判断跳转是否为登录页面，如果不是登录页面
   * 则路由跳转展示加载中页面
   */
  if (to.path != '/login' && to.path != '/' && to.path != '/oauth') {
    store.state.app.isloading = true
    if (
      to.path != '/policydetail' &&    //解析报告
      to.path != '/report' &&          //家庭报告
      to.path != '/detail'             //个人报告
    ) {
      setTimeout(() => {
        store.state.app.isloading = false
      }, 1000)
    }
  }
  // debugger;
  /*
   *  判断是不是管理端访问页面
   *  */
  /*
   * 如果访问的路由对应的值为pass,
   * 直接通过
   * */
  console.log("路径参数")
  console.log(to.query.type)
  if (
    (to.query.type && to.query.type == 'pc') ||
    to.path == '/oauth' ||
    to.path == '/appoauth'
  ) {
    next()
  } else {
    if (valid[to.path] == 'yes') {
      if (to.params.yes) {
      } else {
        to.params.yes = true
      }
    }
      console.log("参数12");
    //  console.log(config.redirect_uri);
    //  console.log(!getStorage('u_s', ''));
    //  console.log(getStorage('u_s', ''));
    //  console.log('访问路由');
    if (!getStorage('u_s', '') && !getStorage('reject', false)) {
      /*
       * 首次访问路由(即没有数据缓存)
       * 先和微信交互获取token
       *
       * */
      console.log('微信token')
      setStorage('hostname', window.location.hostname.split('.')[0])
               console.log("路由");
              // console.log(config);
              //config.redirect_uri:获取页面IP地址或页面域名
      let replace_url = config.redirect_uri + '/oauth'
      // console.log('认证');
      // console.log(replace_url);
      Oauth(replace_url, to.fullPath, valid[to.path])
    } else {
      // debugger;
      /*
       * 已有缓存
       * */
      if (!isEntry()) {
        /*
         * 状态是游客
         * */
        if (getStorage('valid') == 'yes') {
          /*
           * 如果valid等于yes
           * */

          next()
        } else {
          /*
           * 如果valid等于no
           * */

          /*
           * 如果valid不等于yes也不等于no
           * */
          // next()
         if (to.path == '/login' || to.path == '/sxlogin') {
            next()
          } else {
            // getStorage('hostname') == 'nhwx' ? next('/sxlogin') : next('/login')
            next('/login')
          }
        }
      } else {
        console.log("hostname值")
        console.log(getStorage('hostname'));
        /*
         * 状态是内勤或外勤
         * */
        if (to.path == '/login') {
          next('/')
        } else if (to.path == '/sxlogin' || ((getStorage('hostname') == 'nhsx' || getStorage('hostname') == 'hcdl') && to.path == '/')) {
          next('/sxhome')
        } else if (HY_ROOT.includes(to.path) && !ISSHARE && getStorage('u_s', '').rytype == 'M') {
          next('/diaoauth') //职业认证
          return false
        } else if (getStorage('hostname') == 'qd' && to.path == '/') {
          next('/main')  //首页
        } else {
          Dialog.closeAll()
          if (process.env.NODE_ENV === 'production') {
            if (valid[to.path] == 'yes' && !to.query.flush) {
              if (to.fullPath.includes('?')) {
                location.href = `${config.redirect_uri}${to.fullPath}&flush=true`
              } else {
                location.href = `${config.redirect_uri}${to.fullPath}?flush=true`
              }
            } else {
              next()
            }
          } else {
            next()
          }
        }
      }
    }
  }
})

router.afterEach(to => { //路由跳转后的成功操作
  window.document.title = to.meta.title
    ? to.meta.title
    : getStorage('sys_info', {}).appsysname
  if (!window.entryUrl && /(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
    var url = window.location.href
    window.entryUrl = url // 将后面的参数去除
  }
})

export default router
