/*
 * @Author: 王鹏
 * @Date: 2019-10-31 14:08:19
 * @LastEditors: 王广婷
 * @LastEditTime: 2019-11-09 10:14:22
 */
import { getStorage } from '@/lib/util'
export default {
  state: {
    info: getStorage('i_f', ''),
    user: getStorage('u_s', ''),
    menulist: getStorage('m_l', ''),
    ossurl: getStorage('ossurl', ''),
    comname: getStorage('comname', '')
  },
  getters: {
    getInfo: state => state.info,
    getUser: state => state.user,
    getMenulist: state => state.menulist,
    getossurl: state => state.ossurl,
    getcomname: state => state.comname
  },
  mutations: {
    setUser (state, user) {
      state.user = user
    },
    setMenulist (state, menulist) {
      state.menulist = menulist
    },
    setOssurl (state, ossurl) {
      state.ossurl = ossurl
    },
    setComname (state, comname) {
      state.comname = comname
    }
  }
}
