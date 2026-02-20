/*
 * @Author: 王鹏
 * @Date: 2019-10-29 16:29:09
 * @LastEditors: 王鹏
 * @LastEditTime: 2019-11-01 10:23:01
 */
import HttpRequest from '@/lib/axios'
import config from '@/config'
const baseUrl = process.env.NODE_ENV === 'development' ? config.baseUrl.dev : config.baseUrl.pro

const axios = new HttpRequest(baseUrl)
export default axios.request()
