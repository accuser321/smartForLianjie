/*
 * @Author: 刘格优
 * @Date: 2019-12-23 15:29:54
 * @LastEditors: 刘格优
 * @LastEditTime: 2020-03-30 19:57:58
 */
export default [
  // {
  //   path: "/product",
  //   name: "product",
  //   meta: {
  //     title: "产品"
  //   },
  //   component: () => import("@/views/agent/productDisplayNhwx/index")
  // },
  {
    path: "/study",
    name: "study",
    meta: {
      title: "学习"
    },
    component: () => import("@/views/basic/home/component/component/study")
  },
  {
    path: "/nhcenter",
    name: "nhcenter",
    meta: {
      title: "个人中心"
    },
    component: () => import("@/views/basic/home/component/component/nhcenter")
  },
  {
    path: "/bszt",
    name: "bszt",
    meta: {
      title: "保司投保链接"
    },
    component: () => import("@/views/basic/bszt/index")
  },
  {
    path: "/customerserver",
    name: "customerserver",
    meta: {
      title: "客服中心"
    },
    component: () => import("@/views/basic/customer/index")
  },
  {
    path: "/policyDetails",
    name: "policyDetails",
    meta: {
      title: "保单明细"
    },
    component: () =>
      import("@/views/basic/home/component/component/setUp/policyDetails.vue")
  },
  {
    path: "/cashWithdrawal",
    name: "cashWithdrawal",
    meta: {
      title: "提现"
    },
    component: () =>
      import("@/views/basic/home/component/component/setUp/cashWithdrawal.vue")
  },
  {
    path: "/setup",
    name: "setup",
    meta: {
      title: "设置"
    },
    component: () =>
      import("@/views/basic/home/component/component/setUp/setup.vue")
  },
  {
    path: "/changePassword",
    name: "changePassword",
    meta: {
      title: "更换密码"
    },
    component: () =>
      import("@/views/basic/home/component/component/setUp/changePassword.vue")
  },
  {
    path: "/changePhoneNumber",
    name: "changePhoneNumber",
    meta: {
      title: "更换手机号"
    },
    component: () =>
      import(
        "@/views/basic/home/component/component/setUp/changePhoneNumber.vue"
      )
  },
  {
    path: "/myMsg",
    name: "myMsg",
    meta: {
      title: "个人资料"
    },
    component: () =>
      import("@/views/basic/home/component/component/setUp/myMsg.vue")
  },
  {
    path: "/realName",
    name: "realName",
    meta: {
      title: "实名认证"
    },
    component: () =>
      import("@/views/basic/home/component/component/setUp/realName.vue")
  },
  {
    path: "/practisingQualification",
    name: "practisingQualification",
    meta: {
      title: "执业资质"
    },
    component: () =>
      import(
        "@/views/basic/home/component/component/setUp/practisingQualification.vue"
      )
  },
  {
    path: "/changeBank",
    name: "changeBank",
    meta: {
      title: "银行卡信息"
    },
    component: () =>
      import("@/views/basic/home/component/component/setUp/changeBank.vue")
  },
  {
    path: "/aboutUs",
    name: "aboutUs",
    meta: {
      title: "关于我们"
    },
    component: () =>
      import("@/views/basic/home/component/component/setUp/aboutUs.vue")
  },

  {
    path: "/artificialUnderwriting",
    name: "artificialUnderwriting",
    meta: {
      title: "人工核保"
    },
    component: () =>
      import(
        "@/views/basic/home/component/component/setUp/artificialUnderwriting.vue"
      )
  },
  {
    path: "/messagepush",
    name: "messagepush",
    meta: {
      title: ""
    },
    component: () => import("@/views/basic/messagepush/index")
  },
  {
    path: "/diaoauth",
    name: "diaoauth",
    meta: {
      title: ""
    },
    component: () => import("@/views/basic/oauth/income")  //职业认证
  }
];
