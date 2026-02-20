/*
 * @Author: 王鹏
 * @Date: 2019-10-29 16:57:11
 * @LastEditors: 王广婷
 * @LastEditTime: 2020-03-26 18:10:01
 */
import module from './modeule'
export default [
  {
    path: '/login',
    alias: '/login',
    name: 'login',
    meta: {
      title: '登录'
    },
    component: () => import('@/views/basic/login/nhlogin')
  },
  {
    path: '/sxlogin',
    alias: '/sxlogin',
    name: 'sxlogin',
    meta: {
      title: '登录'
    },
    component: () => import('@/views/basic/login/login')
  },
  // {
  //   path: '/',
  //   name: 'home',
  //   meta: {
  //     title: '',
  //     keepAlive: true
  //   },
  //   component: () => import('@/views/basic/home/component/component/nhhome')
  // },
  {
    path: '/',
    name: "Hkbw",
    meta: {
      title: "获客爆文"
    },
    component: () => import("@/views/abt/customerOperation/hkbw/bwlist/index")
  },
  {
    path: '/sxhome',
    name: 'sxhome',
    meta: {
      title: '',
      keepAlive: true
    },
    component: () => import('@/views/basic/home/component/schjone')
  },
  {
    path: '/tuoke', //拓客
    name: 'tuoke',
    meta: {
      title: '',
      keepAlive: true
    },
    component: () => import('@/views/basic/home/component/drbx')
  },
  {
    path: '/oauth',
    name: 'oauth',
    meta: {
      title: ''
    },
    component: () => import('@/views/basic/oauth/index')
  },
  {
    path: '/set',
    name: 'set',
    meta: {
      title: '设置'
    },
    component: () => import('@/views/basic/set/index')
  },
  {
    path: '/appoauth',
    name: 'appoauth',
    meta: {
      title: '首页'
    },
    component: () => import('@/views/basic/oauth/appoauth')
  },
  {
    path: '/advice',
    name: 'advice',
    meta: {
      title: '建议反馈'
    },
    component: () => import('@/views/basic/advice/index')
  },
  {
    path: '/myScore',
    name: 'myScore',
    meta: {
      title: '我的积分'
    },
    component: () => import('@/views/basic/myScore/index')
  },

  ...module.abt,
  ...module.basic,
]
