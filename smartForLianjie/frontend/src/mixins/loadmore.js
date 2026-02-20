/*
 * @Author: 王鹏
 * @Date: 2019-08-16 11:53:48
 * @Last Modified by: 王鹏
 * @Last Modified time: 2019-08-16 12:02:10
 */

/**
 *
 * 定义全局的 loadMore 使用Vue的mixin
 * 全局混入分发到使用loadMore功能的组件
 *
 */

export default {
  created () {
    // this.$refs.toast.hide()
    // this.isShow = false
  },
  methods: {
    loadMore () {
      let { loading, pageNo, totalPage } = this
      if (loading) {
        return false
      } else {
        if (totalPage <= pageNo) {
          this.loading = true
        } else {
          this.getData(false)
        }
      }
    }
  }
}
