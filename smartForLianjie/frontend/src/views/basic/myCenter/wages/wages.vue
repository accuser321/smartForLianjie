<template>
  <div class="content">
    <div class="top">
    </div>
    <div class="bottom">
      <div class="item" v-for="(item,index) in list" :key="index">
        <div>{{item.label}}</div>
        <div>{{item.value}}</div>
      </div>
    </div>
  </div>
</template>
<script>
import { selectGZByEmpno } from '@/api/basic'
import { getStorage } from '@/lib/util'
export default {
  data () {
    return {
      user: '',
      yearmon: '',
      list: []
    }
  },
  created () {
    this.user = getStorage('u_s', {})
    this.yearmon = this.$route.query.yearmon
    this.getData()
  },
  methods: {
    getData () {
      selectGZByEmpno({ empno: this.user.empno, yearmon: this.yearmon }).then(res => {
        this.list = res.data.data.result
      })
    }
  }
}
</script>
<style lang="stylus" scoped>
.content {
  .bottom {
    padding 50px 50px
    .item {
      display: flex
      align-items center
      border-bottom: 1px solid #D8D8D8
      height: 144px
      display flex
      justify-content space-between
      padding-left 50px
      padding-right 50px
    }
  }
}
</style>
