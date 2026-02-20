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
  methods: {
    loadMorechange () {
      if (this.loadflag) {
        if (this.listFinished) {
          return false
        } else {
          if (this.listtotalPage < this.listpageNo) {
            this.listFinished = true
          } else {
            this.loadflag = false
            this.$refs.scrollView.finishLoadMore()
            this.listpageNo = ++this.listpageNo
            console.log('--------------上拉加载请求更多中--------------')
            console.log('加载时总页数' + this.listtotalPage)
            console.log('加载时当前页' + this.listpageNo)
            console.log('加载时加载状态' + this.loadflag)
            console.log('加载时是否停止加载' + this.listFinished)
            this.getData()
          }
        }
      }
    }
  }
}
