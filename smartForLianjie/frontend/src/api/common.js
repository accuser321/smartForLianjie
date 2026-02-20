/*
 * @Author: 王广婷
 * @Date: 2019-10-31 09:24:19
 * @LastEditors: 王广婷
 * @LastEditTime: 2019-11-01 11:04:06
 */

import axios from '@/lib/api.request'

// 获取证件类型和家庭关系
export const selectMJ = data =>
  axios.post('/saas/select/selectMJ', data)
