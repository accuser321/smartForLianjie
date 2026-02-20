/*
 * @Author: 黄孝娟
 * @Date: 2019-11-08 14:48:19
 * @LastEditors: 黄孝娟
 * @LastEditTime: 2019-11-08 11:48:06
 */
import axios from '@/lib/api.request'

// 谁在看我 时间访客列表
export const selectTimeKHByEmpno = data =>
  axios.post('abt/abtComKjActionRecord/selectTimeKHByEmpno', data)
// 谁在看我 行为列表
export const selectKHByOtype = data =>
  axios.post('/abt/abtComKjActionRecord/selectKHByOtype', data)

// 谁在看我 分析列表 客户活跃度，客户与我的互动
export const selectanalysis = params => axios.get('/abt/abtComKjActionRecord/getRecordAnalyze', {
  params
})

// 谁在看我  分析列表新增客户数
export const getkhlist = params => axios.get('/abt/abtComKjActionRecord/getRecordKH', {
  params
})

// 谁在看我  分析列表阅读数
export const getreadlist = params => axios.get('/abt/abtComKjActionRecord/getRecordRD', {
  params
})
// 智能营销漏斗
export const getYSLD = params => axios.get('/abt/abtComKjActionRecord/getYSLD', {
  params
})
