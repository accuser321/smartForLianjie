/*
 * @Author: 黄孝娟
 * @Date: 2019-11-04 15:26:22
 * @LastEditors: 刘格优
 * @LastEditTime: 2020-03-17 10:43:24
 */

export default [
  {
    path: "/Hkbw",
    name: "Hkbw",
    meta: {
      title: "获客爆文"
    },
    component: () => import("@/views/abt/customerOperation/hkbw/bwlist/index")
  },
  {
    path: "/Emplist",
    name: "Emplist",
    meta: {
      title: "我的文章"
    },
    component: () =>
      import("@/views/abt/customerOperation/hkbw/emplist/emplist")
  },
  {
    path: "/Libwarticle",
    name: "Libwarticle",
    meta: {
      title: ""
    },
    component: () => import("@/views/abt/customerOperation/hkbw/libw/article")
  },
  {
    path: "/Empshare",
    name: "Empshare",
    meta: {
      title: ""
    },
    component: () =>
      import("@/views/abt/customerOperation/hkbw/emplibw/empshare")
  },
  {
    path: "/Emparticle",
    name: "Emparticle",
    meta: {
      title: ""
    },
    component: () =>
      import("@/views/abt/customerOperation/hkbw/emplibw/emparticle")
  },
  {
    path: "/makebw",
    name: "makebw",
    meta: {
      title: "制作文章"
    },
    component: () => import("@/views/abt/customerOperation/hkbw/makebw/editor")
  },
  {
    path: "/editarticle",
    name: "editarticle",
    meta: {
      title: "制作文章"
    },
    component: () =>
      import("@/views/abt/customerOperation/hkbw/makebw/editarticle")
  },
  {
    path: "/looked",
    name: "looked",
    meta: {
      title: "消息中心"
    },
    component: () => import("@/views/abt/customerOperation/looked/looked")
  },
  {
    path: "/wholookme",
    name: "wholookme",
    meta: {
      title: "谁在看我"
    },
    component: () => import("@/views/abt/customerOperation/wholookme/wholookme")
  },
  {
    path: "/clientsort",
    name: "clientsort",
    meta: {
      title: "客户挖掘"
    },
    component: () => import("@/views/abt/customerOperation/clientsort/index")
  },
  {
    path: "/clientAnalysis",
    name: "clientAnalysis",
    meta: {
      title: "客户挖掘"
    },
    component: () =>
      import("@/views/abt/customerOperation/clientsort/clientanalysis/index")
  },
  {
    path: "/zxlist",
    name: "zxlist",
    meta: {
      title: "咨询列表"
    },
    component: () => import("@/views/abt/customerOperation/looked/zxlist")
  },
  {
    path: "/Consult",
    name: "Consult",
    meta: {
      title: "消息咨询"
    },
    component: () => import("@/views/abt/customerOperation/consult/index")
  },
  {
    path: "/materiallibrary",
    name: "materiallibrary",
    meta: {
      title: "素材库"
    },
    component: () =>
      import("@/views/abt/customerOperation/materiallibrary/materiallibrary")
  },
  {
    path: "/abtreport",
    name: "abtreport",
    meta: {
      title: "投诉"
    },
    component: () =>
      import("@/views/abt/customerOperation/common/report/report")
  },
  {
    path: "/complain",
    name: "complain",
    meta: {
      title: "投诉"
    },
    component: () =>
      import("@/views/abt/customerOperation/common/complain/complain")
  },
];
