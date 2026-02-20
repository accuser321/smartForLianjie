<template>
  <div class="cash">
    <div class="cashTitle">
      <div class="top">
      <div class="toCard">
        <div>
          <span>到账银行卡</span><span>   {{bankName}}</span>
        </div>
        <div>
          <md-icon name="arrow-right" color="rgba(152,152,152,1)" size="lg"></md-icon>
        </div>
      </div>
        <!-- <div class="arrivalAccount"><span>2小时内到账</span></div> -->
    </div>
    <div class="bottom">
      <div class="cashMoney"><span>提现金额</span></div>
        <div class="cashNum">
            ￥<input v-model="mount"
                      type="text" />
        </div>
        <div class="showTxt">
          <span>可提现余额￥{{allMount}}，<span class="blue" @click="mount = allMount">全部提现</span></span>
        </div>
        <div class="btn" v-if="isCashing">
          <div class="cantCash">提现中</div>
        </div>
        <div class="btn" @click="cashMoney" v-else>
          <div :class="(allMount - 0 > 0) ?'quitBtn' : 'cantCash'">提现</div>
        </div>
      </div>
    </div>
    <div class="showCashList" v-show="noCashList.length-0>0">
      <div class="listTitle">
        <div class="item" v-for="(item,index) in noCashList" :key="index">
          <div>
            <div class="itemNum">{{ item.mount  }}</div>
            <div class="itemStatus">{{ item.txno }}</div>
          </div>
          <div class="itemMoney">{{ item.qrstatus == '0' ? '未审核': (item.qrstatus == '2'?'已拒绝': '已审核') }}</div>
        </div>
      </div>
    </div>
    <div class="showMore">
      <div>
        <span @click="showMore">查看更多</span>
      </div>
    </div>
    <md-landscape v-model="showFullScreen"
                  full-screen>
      <div class="header">
        <span>提现记录</span>
      </div>
      <md-scroll-view ref="scrollView"
                      v-show="!isNull"
                      auto-reflow
                      :scrolling-x="false"
                      @end-reached="loadMore">
        <div class="imgList">
          <div class="cashitem" v-for="(item,index) in cashLists" :key="index" @click="showRefuseMsg(item)">
            <div>
              <div class="itemNum">{{ item.mount  }}</div>
              <div class="itemStatus">{{ item.txno }}</div>
            </div>
            <div class="itemMoney">{{ item.qrstatus == '0' ? '未审核': (item.qrstatus == '2'?'已拒绝': '已审核') }}</div>
          </div>
        </div>
        <md-scroll-view-more slot="more"
                             :is-finished="loading" />
      </md-scroll-view>
      <div class="nodata"
           v-show="isNull">
        <img class="kongimg"
             src="@/assets/image/null.png"
             alt="" />
      </div>
    </md-landscape>
    <div v-show="isShowRefuseMsg" class="showZyImg">
      <div class="zyImg">
        <div>
          <img src="@/assets/nh/zyzz.png" class="manageImg" alt="">
        </div>
        <div class="zzMsg">
          <div class="itemTitle">
            <span>拒绝详情</span>
            <div class="code">
              <span>{{refuseMsg}}</span>
              <!-- <span v-show="showZzform.certno">NO{{showZzform.certno}}</span> -->
            </div>
          </div>
          <!-- <div class="zzMsgItem">
            <div class="showText">执业证编号</div>
            <div class="showNum">{{showZzform.certno}}</div>
          </div>
          <div class="zzMsgItem">
            <div class="showText">有效起期</div>
            <div class="showNum">{{showZzform.begdate}}</div>
          </div>
          <div class="zzMsgItem">
            <div class="showText">有效终期</div>
            <div class="showNum">{{showZzform.enddate}}</div>
          </div> -->
        </div>
        <div class="closeImg">
          <img src="@/assets/nh/sctp.png" @click="isShowRefuseMsg = false" alt="">
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { upMount, getTXRecord, getTXRecords } from '@/api/basic/index'
import { Toast } from 'mand-mobile'
import loadMore from '@/mixins/loadmore'
export default {
  mixins: [loadMore],
  data () {
    return {
      mount: '',
      allMount: '',
      showFullScreen: false,
      loading: false,
      isNull: false,
      noCashList: [],
      cashLists: [],
      pageNo: 1,
      size: 10,
      total: 0,
      totalPage: 2,
      isShowRefuseMsg: false,
      refuseMsg: '',
      isCashing: false,
      bankName: ''
    }
  },
  created () {
    // this.allMount = this.$route.query.allMount || '0.00'
  },
  mounted () {
    this.getbfData()
  },
  methods: {
    cashMoney () {
      if (this.allMount - 0 <= 0) {
        return
      }
      if (this.allMount - 0 - this.mount < 0) {
        Toast.info(`您的可提现金额只有${this.allMount}元！`)
        return
      }
      if ((this.mount - 0) >= 100 && (this.mount - 0) <= 4999) {
        this.isCashing = true
        upMount({ mount: this.mount }).then(res => {
          Toast.succeed('提现申请已提交，请等待审核')
        }).finally(() => {
          this.$router.push(`/nhcenter`)
          this.isCashing = false
        })
      } else {
        Toast.info('提现金额最低100元，最高4999元！')
      }
    },
    showMore () {
      this.showFullScreen = true
      this.getData()
    },
    // 部分
    getbfData () {
      let data = {
        page: 1,
        size: 10
      }
      getTXRecord(data).then(res => {
        this.noCashList = res.data.data.rows
        this.allMount = res.data.data.blankmessage.balance
        this.bankName = res.data.data.blankmessage.bankname
        console.log(res)
      })
    },
    async getData (isInit = true) {
      Toast.loading('加载中...')
      let { pageNo, size } = this
      if (!isInit) {
        this.pageNo = ++pageNo
        this.$refs.scrollView.finishLoadMore()
      }
      let data = {
        page: this.pageNo,
        size
      }
      let res = await getTXRecords(data).finally(() => {
        Toast.hide()
      })
      this.cashLists = isInit ? res.data.data.rows : this.cashLists.concat(res.data.data.rows)
      if (this.cashLists.length == 0) {
        this.isNull = true
      } else {
        this.isNull = false
      }
      this.total = res.data.data.total
      this.totalPage = res.data.data.totalpage
      return res
    },
    // 显示拒绝理由
    showRefuseMsg (val) {
      console.log(val)
      if (val.qrstatus == '2') {
        this.refuseMsg = val.remark
        this.isShowRefuseMsg = true
      }
    }
  }
}
</script>
<style lang="stylus" scoped>
.cash {
    background-color #EDEDED
    height 100%
    .cashTitle {
        margin 0px 40px
        // padding 0px 80px
        .top {
            padding 40px 80px
            background-color #F7F7F7
            .toCard {
                display flex
                justify-content space-between
                span {
                    font-size:35px;
                    font-family:PingFang SC;
                    font-weight:500;
                    color:rgba(56,56,56,1);
                }
            }
            .arrivalAccount {
                text-align center
                padding-top 20px
                span {
                    font-size:35px;
                    font-family:PingFang SC;
                    font-weight:500;
                    color:rgba(152,152,152,1);
                }
            }

        }
        .bottom {
            padding 40px 80px
            background-color #FFFFFF
            .cashMoney {
                font-size:35px;
                font-family:PingFang SC;
                font-weight:bold;
                color:rgba(56,56,56,1);

            }
            .cashNum {
                padding: 40px 0px;
                font-size: 70px;
                font-family:PingFang SC;
                font-weight:bold;
                color:rgba(56,56,56,1);
                border-bottom 1px solid #F6F6F6
                input {
                    overflow: hidden;
                    font-size: 90px;
                    border: none;
                    text-align: left;
                    outline: none;
                    width: 80%
                }
            }
            .showTxt {
                padding 40px 0px
                span {
                    font-size:35px;
                    font-family:PingFang SC;
                    font-weight:500;
                    color:rgba(152,152,152,1);
                }
                .blue {
                    color #666688 !important
                    font-size:35px;
                }
            }
            .btn {
                text-align center
                padding-bottom 40px
                // padding-top 200px
                .quitBtn {
                    padding: 30px 30px;
                    // margin: 0px auto;
                    width: 100%;
                    background-color: color-primary;
                    font-size:40px;
                    font-family:PingFang SC;
                    font-weight:500;
                    color:rgba(255,255,255,1);
                    border-radius 10px
                }
                .cantCash {
                    padding: 30px 30px;
                    background-color: #F2F2F2
                    color: #BFBFBF !important
                    width: 100%;
                    font-size:40px;
                    font-family:PingFang SC;
                    font-weight:500;
                    border-radius 10px
                }
            }
        }
    }
    .showCashList {
      margin 40px 40px
      background-color #FFFFFF
      .listTitle {
        border-bottom 1px solid #F6F6F6
        height 800px
        overflow-y scroll
        .item {
          padding 30px 50px
          display flex
          justify-content space-between
          align-items center
          .itemNum {
            font-size 45px
            color:rgba(56,56,56,1);
            font-family:PingFang SC;
            font-weight:500;

          }
          .itemStatus {
            padding-top 10px
            font-size 35px
            color:rgba(152,152,152,1);
          }
          .itemMoney {
            font-size 50px
            color color-primary
          }
        }
      }
    }
    .showMore {
      margin 0px 40px
      text-align center
      color color-primary
      padding-top 50px
    }
}
.header {
  width: 100%;
  height: 130px;
  border-bottom: 1px solid #dbdddc;
  text-align: center;
  // line-height: 50px;
  background: #ffffff;
  position: fixed;
  // top: 100px;
  // left: 0;
  z-index: 9;

  span {
    margin-top: 130px;
    line-height: 130px;
    font-size: 40px;
    font-weight: 600;
  }
}
.imgList {
  padding-top: 150px;
  // display flex
  // flex-wrap wrap
  // padding 30px 50px
  border-bottom 1px solid #F6F6F6
  .cashitem {
    padding 30px 50px
    // width:33%;
    // text-align:center;
    display flex
    justify-content space-between
    align-items center
    .itemNum {
      font-size 45px
      color:rgba(56,56,56,1);
      font-family:PingFang SC;
      font-weight:500;

    }
    .itemStatus {
      padding-top 10px
      font-size 35px
      color:rgba(152,152,152,1);
    }
    .itemMoney {
      font-size 50px
      color #BA1C21
    }
  }
}
.nodata {
  padding-top: 30%;
  text-align: center;

  .kongimg {
    width: 300px;
    height: auto;
  }
}

.showZyImg {
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  z-index: 9999;
  padding: 250px 150px;
  background:rgba(0,0,0,0.7)
  .zyImg {
    position: relative;
    .zzMsg {
      position: absolute;
      top: 30%;
      width: 100%;
      .itemTitle {
        text-align: center;
        padding-bottom: 50px;
        span {
          font-size:50px;
          font-family:PingFang SC;
          font-weight:bold;
          color:rgba(56,56,56,1);
        }
        .code {
          // margin-top: 20px;
          // margin-top: 120px;
          text-align left
          margin 120px 50px 0px
          span {
            font-weight:500;
            font-size:35px !important;
          }
        }
      }
      // .zzMsgItem {
      //   display: flex;
      //   justify-content: space-between;
      //   margin: 25px 50px;
      //   padding-bottom: 25px;
      //   border-bottom 1px solid #F6F6F6
      //   .showText {
      //     font-size:40px;
      //     font-family:PingFang SC;
      //     font-weight:bold;
      //     color:rgba(56,56,56,1);
      //   }
      //   .showNum {
      //     font-size:40px;
      //     font-family:PingFang SC;
      //     font-weight:500;
      //     color:rgba(152,152,152,1);
      //   }
      // }
    }
    .closeImg {
      text-align: center;
      padding-top: 30px;
      img {
        width: 100px;
        height: auto;
      }
    }
  }
}
</style>
