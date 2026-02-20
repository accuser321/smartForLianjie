<template>
  <div class="images">
    <div class="title">
      <div v-if="type == 'lp'" class="bgImg">
        <img src="@/assets/nh/lplc.jpg" class="topImg" />
        <!-- <div class="buttom">
          <div class="btn">
            <div class="btnImg">
              <img src="@/assets/nh/lpzx.png" @click="toCustomerserver" />
            </div>
            <div class="btnImg right">
              <img src="@/assets/nh/lpzy.png" />
            </div>
          </div>
        </div> -->
      </div>
      <div v-else>
        <img src="@/assets/nh/rghb.jpg" />
      </div>
    </div>

  </div>
</template>
<script>
export default {
  data () {
    return {
      type: ''
    }
  },
  created () {
    this.type = this.$route.query.type
    if (this.type == 'lp') {
      document.title = '理赔'
    }
  },
  methods: {
    toCustomerserver () {
      this.$router.push({ path: '/customerserver' })
    }
  }
}
</script>
<style lang="stylus" scoped>
.images {
  background-color: #F6F6F6
}
.bgImg {
  position: relative;
  .topImg {
    position: relative;
  }
  .buttom {
    position: absolute;
    bottom 80px
    padding 0px 60px
    width 100%
    .btn {
      .btnImg {
        float left
        width 50%
        text-align center
        img {
          height: 80px;
          width: auto;
        }
      }
    }
  }
}
</style>
