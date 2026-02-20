<template>
    <div class="searchPolicy">
        <div class="container">
            <div class="put">
                <span class="iconfont iconsousuo" v-show="isShow"></span>
                <input type="text"
                    @input="change"
                    v-model="text"
                    :placeholder="reminder" />
            </div>
            <div class="btn" @click="Search">搜索</div>
        </div>
    </div>
</template>
<script>
export default {
  name: 'search',
  props: {
    reminder: {
      type: String,
      default: '按客户姓名筛选'
    }
  },
  data () {
    return {
      isShow: true,
      text: ''
    }
  },
  methods: {
    Search () {
      this.$emit('handleSearch')
      this.$parent.search = this.text
    },
    change () {
      if (this.text) {
        this.isShow = false
      } else {
        this.isShow = true
      }
      this.$parent.search = this.text
    }
  }
}
</script>
<style scoped>
.searchPolicy{
    padding:26px 18px;
    border-bottom:1px solid #EEEEEE;
    background:#FFF;
}
.container{
    display:flex;
    align-items:center;
    justify-content:space-around;
}
.put{
    width:80%;
    height:70px;
    position:relative;
    background:#F2F2F2;
    padding:10px;
    border-radius:20px;
}
.put input{
    width:100%;
    height:100%;
    background:transparent;
    text-align:center;
    font-size:24px;
    border: none;
}
.btn{
    width:10%;
    text-align:center;
}
.iconsousuo {
  position: absolute;
  top: 50%;
  left: 160px;
  color: #666;
  font-size: 28px;
  transform: translateY(-41%);
}
</style>
