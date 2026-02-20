/*
 * @Author: 黄孝娟
 * @Date: 2019-11-08 14:48:19
 * @LastEditors  : 刘格优
 * @LastEditTime : 2019-12-24 13:24:26
 */

import axios from '@/lib/api.request';

// 文章导入
export const importWZ = data => axios.post('/smartComKjLibw/importWZ', data)
// 文章转发变成我的
export const ForwardWZ = params =>
  axios.get('/abt/abtComKjLibw/ForwardWZ', { params })
// 文章制作
export const insertWZ = data => axios.post('/abt/abtComKjLibw/insertWZ', data)
// 文章点击变成我的
export const BecomeWZ = params =>
  axios.get('/abt/abtComKjLibw/BecomeWZ', { params })
// 文章修改
export const updateWZ = data => axios.put('/abt/abtComKjLibw/updateWZ', data)
// 给素材添加标签
export const updateBq = data => axios.put('/abt/abtComKjLibw/updateBq', data)
// // 名片 GET
// export const selectUserCard = params => axios.get('/abt/abtComMuserCard/selectUserCard', {
//   params
// })
