/*
 * @Author: 黄孝娟
 * @Date: 2019-11-08 14:48:19
 * @LastEditors: 刘格优
 * @LastEditTime: 2019-11-29 15:12:06
 */

import axios from '@/lib/api.request';

// 智能提醒列表
export const selectzntx = data =>
  axios.post('/core/abtComAlertmsg/selectByPage', data)
// 智能提醒列表详情
export const selectzntxXQ = data =>
  axios.post('/core/abtComAlertmsg/checkDetails', data)
