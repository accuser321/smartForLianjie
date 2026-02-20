<template>
  <div class="content">
    <!-- <div class="wages">
      <div class="w_title">
        <div class="wt_title">
          <span>考核预警</span>
        </div>
              <div class="wt_btn">
                <span>近6批次</span>
              </div>
      </div>
      <div class="oneTitle"><span>晋升</span></div>
      <div v-for="(item,index) in jsList" :key="index">
        <div class="line"  @click="isShowZb(item,index)">
          <div class="l_date">
            <span>{{item.salename}}</span>
          </div>
          <div class="l_value">
            <span>{{item.value == '0' ? '达标': '未达标'}}  </span>
            <md-icon color="#BDC0C2" name="arrow-down" size="lg" v-if="jsList[index].isShowZb"></md-icon>
            <md-icon color="#BDC0C2" name="arrow-right" size="lg" v-else></md-icon>
          </div>
        </div>
        <div class="line" v-for="(item1,index1) in item.zb" :key="index1" v-show="jsList[index].isShowZb">
          <div class="l_date">
            <span>{{item1.name}}</span>
          </div>
          <div class="l_value">
            <span>{{item1.value}}</span>
          </div>
        </div>
      </div>
      <div class="oneTitle"><span>维持</span></div>
      <div v-for="(item,index) in wcList" :key="index">
        <div class="line"  @click="isShowWczb(item,index)">
          <div class="l_date">
            <span>{{item.salename}}</span>
          </div>
          <div class="l_value">
            <span>{{item.value == '0' ? '达标': '未达标'}}  </span>
            <md-icon color="#BDC0C2" name="arrow-down" size="lg" v-if="wcList[index].isShowZb"></md-icon>
            <md-icon color="#BDC0C2" name="arrow-right" size="lg" v-else></md-icon>
          </div>
        </div>
        <div class="line" v-for="(item1,index1) in item.zb" :key="index1" v-show="wcList[index].isShowZb">
          <div class="l_date">
            <span>{{item1.name}}</span>
          </div>
          <div class="l_value">
            <span>{{item1.value}}</span>
          </div>
        </div>
      </div>

    </div> -->
    <div class="wages">
      <div class="w_title">
        <div class="wt_title">
          <span>工资明细</span>
        </div>
        <div class="wt_btn">
          <span>近6批次</span>
        </div>
      </div>
      <div class="line" v-for="(item,index) in gzlist" :key="index" @click="toWages(item)">
        <div class="l_date">
          <span>{{item.yearmon}}</span>
        </div>
        <div class="l_value">
          <span>￥{{item.hjsale}}  </span>
          <md-icon color="#BDC0C2" name="arrow-right" size="lg"></md-icon>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { selectGRZXByEmpno, selectKHbyEmpno } from '@/api/basic'
import { getStorage } from '@/lib/util'
export default {
  data () {
    return {
      user: '',
      form: {},
      zb: [],
      gzlist: [],
      isShow: true,
      jsList: [],
      wcList: []
    }
  },
  created () {
    this.user = getStorage('u_s', {})
    this.getData()
    this.getList()
  },
  methods: {
    getData () {
      selectGRZXByEmpno({ empno: this.user.empno }).then(res => {
        this.form = res.data.data
        this.zb = this.form.zb
        this.gzlist = this.form.gzlist
      })
    },
    // 获取预警列表
    getList () {
      selectKHbyEmpno().then(res => {
        this.jsList = res.data.data.js
        this.jsList.forEach((item, index) => {
          item.isShowZb = true
        })
        this.wcList = res.data.data.wc
        this.wcList.forEach((item, index) => {
          item.isShowZb = true
        })
      })
    },
    isShowZb (item, index) {
      this.jsList[index].isShowZb = !this.jsList[index].isShowZb
      console.log(this.jsList[index].isShowZb)
      this.$forceUpdate()
    },
    isShowWczb (item, index) {
      this.wcList[index].isShowZb = !this.wcList[index].isShowZb
      console.log(this.wcList[index].isShowZb)
      this.$forceUpdate()
    },
    toWages (val) {
      if (val.hjsale - 0 > 0) {
        this.$router.push(`/wages?yearmon=${val.yearmon}`)
      }
    }
    // showMore () {
    //   this.isShow = false
    // }
  }
}
</script>
<style lang="stylus" scoped>
.content {
  background-color #F5F5F9
  margin-bottom 100px
  .wages {
    margin-top 30px
    background-color #fff
    padding 50px 50px
    .w_title {
      display flex
      justify-content space-between
      padding-bottom 50px
      .wt_title {
        span {
          width 26.09%
          height 1.63%
          font-size:47px;
          font-family:PingFang SC;
          font-weight:bold;
          color:rgba(61,61,61,1);
        }
      }
      .wt_btn {
        border 1px solid color-primary
        padding 5px 50px
        color color-primary
        span {
          width 5.56%
          height 1.87
          font-size 35px;
          font-family PingFang SC;
          font-weight 500;
        }
      }
    }
    .line {
      display: flex
      align-items center
      border-bottom: 1px solid #EAEAEA
      height: 144px
      display flex
      justify-content space-between
      // padding-left 50px
      .l_date {
        width 60%
        span {
          width 14.77%
          height 1.35%
          font-size:40px;
          font-family:PingFang SC;
          font-weight:500;
          color:rgba(44,44,60,1);
        }
      }
      .l_value {
        span {
          width 13.62%
          height 1.45%
          font-size:45px;
          font-family:PingFang SC;
          font-weight:500;
          color:rgba(44,44,60,1);
        }
        // width 40%
        // text-align center
      }
      .l_icon {
        width 10%
        text-align right
      }
    }
  }
}
.oneTitle {
  text-align center
  padding-top 20px
  span {
    font-size:43px;
    font-family:PingFang SC;
    font-weight:bold;
    color:rgba(61,61,61,1);
  }
}
</style>
