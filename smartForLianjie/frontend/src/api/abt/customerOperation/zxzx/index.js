/*
 * @Author: 刘格优
 * @Date: 2019-11-30 17:44:53
 * @LastEditors: 刘格优
 * @LastEditTime: 2019-12-06 15:29:19
 */
import axios from '@/lib/api.request';

// 咨询数据添加
export const insertKjChat = data =>
  axios.post('/abt/abtComKjActionRecord/insertKjChat', data)
// 获取历史数据
export const getKjChat = params =>
  axios.get('/abt/abtComKjActionRecord/getKjChat', { params })
// 获取已读未读数据
export const getKJChatNum = params =>
  axios.get('/abt/abtComKjActionRecord/getKjChatNum', { params })
// 咨询数据历史查询
export const getLS = data =>
  axios.post('/abt/abtComKjActionRecord/getLS', data)
// 清空未读消息
export const updateMSG = data =>
  axios.put('/abt/abtComKjActionRecord/updateMSG', data)
