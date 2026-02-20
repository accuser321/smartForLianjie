/*
 * @Author: 刘格优
 * @Date: 2019-12-24 09:55:01
 * @LastEditors  : 刘格优
 * @LastEditTime : 2019-12-24 11:20:22
 */
import axios from '@/lib/api.request';

// 消息中心列表
export const QueryMessage = data =>
  axios.post('/abt/abtComKjActionRecord/QueryMessage', data)
// 改变消息状态
export const updateStatus = data =>
  axios.post('/abt/abtComKjActionRecord/updateStatus', data)
