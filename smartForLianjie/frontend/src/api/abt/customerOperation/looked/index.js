/*
 * @Author: 黄孝娟
 * @Date: 2019-11-08 14:48:19
 * @LastEditors: 刘格优
 * @LastEditTime: 2019-11-29 15:12:06
 */

import axios from '@/lib/api.request';

// 谁看了我详情
export const getlook = data =>
  axios.post('/smartComKjActionRecord/getWZRdZf', data)

// 咨询列表
export const selectMessage = data =>
  axios.post('/abt/abtComKjActionRecord/selectMessage', data)
