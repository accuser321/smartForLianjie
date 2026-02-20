/*
 * @Author: 黄孝娟
 * @Date: 2019-11-08 14:48:19
 * @LastEditors: 刘格优
 * @LastEditTime: 2019-11-18 20:36:24
 */
import axios from '@/lib/api.request'

/**
 * @param 客户挖掘
 */
// 标签列表
export const selectKHBQ = params =>
  axios.get('/saas/select/selectKHBQ', { params })
// 获取用户信息
export const getCustomMessage = params =>
  axios.get('/auth/getinfobyid', { params })
// 添加更新客户信息
export const insertCustomBasemsg = data =>
  axios.post('/abt/abtComKjCustomBasemsg/insertCustomBasemsg', data)
// 添加自定义客户标签
export const insertCustomLabel = data =>
  axios.post('/abt/abtComKjCustomBasemsg/insertCustomLabel', data)
// 修改客户标签
export const insertKHLabel = data =>
  axios.post('/abt/abtComKjCustomBasemsg/insertKHLabel', data)
// AI分析
export const getAIAnalysis = params =>
  axios.get('/abt/abtComKjActionRecord/getAIAnalysis', { params })
// 朋友列表（产品语义调整为“朋友”，接口名沿用历史命名 getColleague）
export const getColleague = data =>
  axios.post('/abt/abtComKjActionRecord/getColleague', data)
// 通讯录列表
export const getCommunicationKH = data =>
  axios.post('/abt/abtComKjActionRecord/getCommunicationKH', data)
// 最近访客
export const getRecentlyKH = data =>
  axios.post('/abt/abtComKjActionRecord/getRecentlyKH', data)
// 准客户
export const getStandardKH = data =>
  axios.post('/abt/abtComKjActionRecord/getStandardKH', data)
// // 寻找客户
// export const getKHRelation = params =>
//   axios.get('/abt/abtComKjActionRecord/getKHRelation', { params })
//寻找客户
export const getKHRelation = params =>
axios.get('/smartComKjActionRecord/getKHRelation', { params })
// 寻找人脉
export const getKHContacts = data =>
  axios.post('/abt/abtComKjActionRecord/getKHContacts', data)
// 跟进列表
export const getFollowList = data =>
  axios.post('/abt/abtComKjActionRecord/getFollowList', data)
// 添加跟进客户信息
export const insertFollow = data =>
  axios.post('/abt/abtComKjActionRecord/insertFollow', data)
// 删除跟进信息
export const deleteFollow = params =>
  axios.delete('/abt/abtComKjActionRecord/deleteFollow', { params })
// 获取互动列表
export const getInteractiveList = data =>
  axios.post('/abt/abtComKjActionRecord/getInteractiveList', data)
