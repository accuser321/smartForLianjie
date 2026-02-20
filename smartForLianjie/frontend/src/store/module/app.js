/*
 * @Author: 刘格优
 * @Date: 2019-11-04 11:36:58
 * @LastEditors: 王广婷
 * @LastEditTime: 2019-11-12 17:59:12
 */
export default {
  state: {
    isloading: false
  },
  getters: {
    getIsloading: state => state.isloading
  },
  mutations: {
    setIsloading (state, isloading) {
      state.isloading = isloading
    }
  }
}
