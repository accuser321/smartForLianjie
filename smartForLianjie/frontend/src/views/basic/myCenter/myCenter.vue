<template>
  <div class="content">
    <div class="title">
      <div class="income">
        <span class="income_text">年收入(元) <md-icon color="#fff" name="visible" size="lg"></md-icon></span><br/>
        <div class="income_num">
          <div class="num">
            <span>{{form.gz}}</span>
          </div>
          <div class="card" @click="$router.push({ path: '/bankCard' })">
            <div class="c_text">
              <img src="@/assets/image/yhk.png" />
              <span>  银行卡  </span>
              <md-icon color="#fff" name="arrow-right" size="md"></md-icon>
            </div>
          </div>
          <div class="businessTracking" @click="$router.push({ path: '/businesstrack' })">
            <div class="c_text">
              <img src="@/assets/image/yhk.png" />
              <span>  业务追踪  </span>
              <md-icon color="#fff" name="arrow-right" size="md"></md-icon>
            </div>
          </div>
        </div>
      </div>
      <div class="number">
        <div class="jiesuan solid">
          <div class="item solid_right">
            <div class="i_top">
              <span>个人年度新保</span>
            </div>
            <span class="i_bottom">{{ form.xinb }}</span>
          </div>
          <div class="item">
            <div class="i_top">
              <span>个人年度续保</span>
            </div>
            <span class="i_bottom">{{ form.xub }}</span>
          </div>
        </div>
        <div class="jiesuan">
          <div class="item solid_right">
            <div class="i_top">
              <span>团队总人力</span>
            </div>
            <span class="i_bottom">{{ form.tdsum }}</span>
          </div>
          <div class="item">
            <div class="i_top">
              <span>出单人力</span>
            </div>
            <span class="i_bottom">{{ form.tdnum }}</span>
          </div>
        </div>
      </div>
    </div>
    <div class="wages">
      <div class="w_title">
        <div class="wt_title">
          <span>个人指标汇总</span>
        </div>
        <div class="wt_btn">
          <span>当月</span>
        </div>
      </div>
      <div v-if="isShow">
        <div v-show="index<5" v-for="(item,index) in zb" :key="index"  class="w_index">
          <div class="w_inside">
            <div class="w_left">
              <div class="wi_name">
                <span>{{ item.zbname }}</span>
              </div>
              <div class="wi_desc">
                <span>{{ item.zbdesc }}</span>
              </div>
            </div>
            <div class="w_right">
              <div class="wi_num">
                <span>{{ item.soilvalue }}</span>
              </div>
            </div>
          </div>
        </div>
        <div class="ckgd_btn" @click="showMore"><span>查看更多</span></div>
      </div>
      <div v-else class="w_index" v-for="(item,index) in zb" :key="index">
        <div class="w_inside">
          <div class="w_left">
              <div class="wi_name">
                <span>{{ item.zbname }}</span>
              </div>
              <div class="wi_desc">
                <span>{{ item.zbdesc }}</span>
              </div>
            </div>
            <div class="w_right">
              <div class="wi_num">
                <span>{{ item.soilvalue }}</span>
              </div>
            </div>
        </div>
      </div>
    </div>
    <div class="wages">
      <div class="w_title">
        <div class="wt_title">
          <span>工资统计</span>
        </div>
        <div class="wt_btn">
          <span>近6月</span>
        </div>
      </div>
      <div class="line" v-for="(item,index) in gzlist" :key="index" @click="toWages(item)">
        <div class="l_date">
          <span>{{item.showdate}}月工资</span>
        </div>
        <div class="l_value">
          <span>￥{{item.value}}  </span>
        <!-- </div>
        <div class="l_icon"> -->
          <md-icon color="#BDC0C2" name="arrow-right" size="lg"></md-icon>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { selectGRZXByEmpno } from '@/api/basic'
import { getStorage } from '@/lib/util'
export default {
  data () {
    return {
      user: '',
      form: {},
      zb: [],
      gzlist: [],
      isShow: true
    }
  },
  created () {
    this.user = getStorage('u_s', {})
    this.getData()
  },
  methods: {
    getData () {//获取个人中心信息
      selectGRZXByEmpno({ empno: this.user.empno }).then(res => {
        this.form = res.data.data
        this.zb = this.form.zb
        this.gzlist = this.form.gzlist
        this.gzlist.forEach((item, index) => {
          this.$set(item, 'showdate', (item.date.substr(5, 7) - 9 > 0) ? item.date.substr(5, 7) : item.date.substr(6, 7))
        })
      })
    },
    toWages (val) {
      if (val.value - 0 > 0) {
        this.$router.push(`/wages?yearmon=${val.date}`)
      }
    },
    showMore () {
      this.isShow = false
    }
  }
}
</script>
<style lang="stylus" scoped>
.content {
  background-color #F5F5F9
  margin-bottom 100px
  .title {
    position relative
    .income {
      height 410px
      padding-left 79px
      padding-top 80px
      background-image url('../../../assets/image/nhwx_bg.png')
      background-size: 100% auto;
      background-repeat: no-repeat;
      span {
        color #fff
      }
      .income_text {
        width:15.41%;
        height:6.38%;
        font-size:35px;
        font-family:PingFang SC;
        font-weight:500;
        color:rgba(237,243,247,1);
      }
      .income_num {
        padding-top 30px
        display flex
        justify-content space-between
        align-items center
        position relative
        .num {
          span {
            width:34.34%;
            height:16.36%;
            font-size:104px;
            font-family:Bahnschrift;
            font-weight:400;
            color:rgba(255,255,255,1);
          }
        }
        .card {
          background-color #e79577
          border-top-left-radius 50px
          border-bottom-left-radius 50px
          width 35%
          text-align center
          position absolute
          top -20px
          right 0
          .c_text {
            padding 20px 40px 20px 40px
            img {
              width 33px
              height auto
            }
            span {
              width:37.82%;
              height:26.8%;
              font-size:35px;
              font-family:PingFang SC;
              font-weight:500;
              color:rgba(255,255,255,1);
            }
          }
        }
        .businessTracking {
          // background-color #2CB1E5
          background-color #e79577
          border-top-left-radius 50px
          border-bottom-left-radius 50px
          width 35%
          text-align center
          position absolute
          top 130px
          right 0
          .c_text {
            padding 20px 40px 20px 40px
            img {
              width 33px
              height auto
            }
            span {
              width:37.82%;
              height:26.8%;
              font-size:35px;
              font-family:PingFang SC;
              font-weight:500;
              color:rgba(255,255,255,1);
            }
          }

        }
      }
    }
    .number {
      .jiesuan {
        background-color #fff
        display flex
        flex-wrap wrap
        .item {
          width 50%
          padding 50px 0px 50px 80px
          .i_top {
            padding-bottom 23px
            span {
              width 20.65%
              height 0.95%
              font-size:37px;
              font-family:PingFang SC;
              font-weight:bold;
              color:rgba(153,153,153,1);
            }
          }
          .i_bottom {
            width 12.66%;
            height 1.08%
            font-size:55px;
            font-family:Bahnschrift;
            font-weight:400;
            color:rgba(68,68,68,1);
          }
        }
      }
    }
  }
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
        // color color-mycenter
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
    .ckgd_btn {
      text-align center
      span {
        width 13.24%
        height 2.07%
        font-size:37px;
        font-family:PingFang SC;
        font-weight:500;
        // color:color-mycenter
        color color-primary
      }
    }
    .w_index {
      margin-bottom 30px
      height 240px
      // background-color color-mycenter
      background-color color-primary
      .w_inside {
        display flex
        justify-content space-between
        align-items center
        padding-left 50px
        padding-right 50px
        margin-left 10px
        background-color #fff
        box-shadow: -3px 3px 15px #b4c9f7
        height 240px
        .w_left {
          width 75%
          .wi_name {
            padding-bottom 5px
            span {
              width 18.84%
              height 17.68%
              font-size:45px;
              font-family:PingFang SC;
              font-weight:bold;
              color:rgba(7,7,7,1);
            }
          }
          .wi_desc {
            span {
              width 45.45%
              height 12.66%
              font-size:35px;
              font-family:PingFang SC;
              font-weight:500;
              color:rgba(102,102,102,1);
            }
          }
        }
        .w_right {
          .wi_num {
            span {
              width 10.65%
              height 13.91%
              font-size 50px
              // color color-mycenter
              color color-primary
            }
          }
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
        width 50%
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
.solid {
  border-bottom 1px solid #F5F5F9
}
.solid_right {
  border-right 1px solid #F5F5F9
}
</style>
