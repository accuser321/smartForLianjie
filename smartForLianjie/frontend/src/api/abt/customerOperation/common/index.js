/*
 * @Author: 黄孝娟
 * @Date: 2019-11-06 09:24:19
 * @LastEditors: 侯依辰
 * @LastEditTime: 2019-12-02 20:34:00
 */

import axios from '@/lib/api.request'

// 枚举值下拉框
export const selectMJ = data => axios.post('/saas/select/selectMJ', data)
// 列表 POST
export const selectPage = data =>
  axios.post('/smartComKjLibw/selectPage', data)
// 下拉框 GET /{tagcode}
export const selectKJFL = params =>
  axios.get('/smartComKjLibw/selectKJFL', {
    params
  })
// 上传Base64格式图片
export const uploadBase64Img = data =>
  axios.post('/saas/upload/uploadBase64Img', data)
// 上传图片
export const Uploadpic = data => axios.post('/saas/upload/uploadImg', data)
// 上传我的照片
export const uploadByMediaId = data => axios.post('/abt/abtComMuserCard/uploadByMediaId', data)
// 素材详情
export const selectOneBySno = params =>
  axios.get('/smartComKjLibw/selectOneBySno', {
    params
  })
// 素材删除
export const deletesc = params =>
  axios.delete('/abt/abtComKjLibw/delete', {
    params
  })
// 获取微信config
export const GetwxConfig = data =>
  axios.post('/auth/getJsapiTicket', data)
// 行为记录
export const DoRecord = data =>
  axios.post('/smartComKjActionRecord/insertActionCord', data)
// 生成海报 POST
export const useHB = data =>
  axios.post('/abt/abtComKjLibw/useHB', data)
// 文章建议投诉 POST
export const getreport = data =>
  axios.post('/abt/abtComKjComplain/insertComplain', data)
