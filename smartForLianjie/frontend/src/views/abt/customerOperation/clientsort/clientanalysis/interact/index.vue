<!--
 * @Author: 刘格优
 * @Date: 2019-11-14 15:14:30
 * @LastEditors  : 刘格优
 * @LastEditTime : 2020-01-12 03:04:00
 -->

<template>
  <div class="scroll">
    <md-scroll-view
      class="scroll"
      ref="scrollView"
      auto-reflow
      :scrolling-x="false"
      @end-reached="loadMore"
    >
      <div>
        <div class="timeTag" v-for="(item, index) in timeList" :key="index">
          <div class="time">
            <div class="timeBg">
              <img src="@/assets/abt/img/timeicon.png" />
              <span>{{ index }}</span>
            </div>
          </div>
          <div>
            <div
              class="detail"
              v-for="(timeitem, timeindex) in item"
              :key="timeindex"
            >
              <div class="border">
                <img :src="timeitem.headimg" />

                <div class="seeWhat" v-if="timeitem.btagcode === '9'">
                  <span
                    >{{ timeitem.khname
                    }}<span class="color">查看</span
                    >了你的名片，沟通从此刻开始</span
                  >
                </div>
                <div class="seeWhat" v-if="timeitem.btagcode === '4'">
                  <span
                    >{{ timeitem.khname
                    }}<span class="color">查看</span
                    >了你的贺卡，他应该很喜欢你的祝福</span
                  >
                </div>
                <div class="seeWhat" v-if="timeitem.btagcode === '5'">
                  <span
                    >{{ timeitem.khname
                    }}<span class="color">查看</span
                    >了你的邀请函，尽快联系他吧</span
                  >
                </div>
                <div class="seeWhat" v-if="timeitem.btagcode === '10'">
                  <span
                    >{{ timeitem.khname
                    }}<span class="color">查看</span>了你发布的文章<span
                      class="color"
                      >{{ timeitem.stitle }}</span
                    >，看来TA对你感兴趣</span
                  >
                </div>
                <div class="seeWhat" v-if="timeitem.btagcode === '7'">
                  <span
                    >{{ timeitem.khname
                    }}<span class="color">查看</span>了你分享的产品<span
                      class="color"
                      >{{ timeitem.stitle }}</span
                    >，看来TA对该产品感兴趣</span
                  >
                </div>
                 <div class="seeWhat" v-if="timeitem.btagcode === '11'">
                     <span>{{ timeitem.khname}}<span class="color">查看</span>了你发送的<span class="color">{{ timeitem.remark}}</span>解析报告</span>
                 </div>
                 <div class="seeWhat" v-if="timeitem.btagcode === '13'">
                    <span>{{ timeitem.khname}}<span class="color">查看</span>了<span class="color">{{ timeitem.remark}}</span>的家庭解析报告</span>
                  </div>
                  <div class="seeWhat" v-if="timeitem.btagcode === '14'">
                    <span>{{ timeitem.khname}}<span class="color">查看</span>了<span class="color">{{ timeitem.remark}}</span>的客户分析报告</span>
                  </div>
                  <div class="seeWhat" v-if="timeitem.btagcode === '15'">
                    <span>{{ timeitem.khname}}<span class="color">查看</span>了<span class="color">{{ timeitem.remark}}</span>的NBS报告</span>
                  </div>
              </div>
              <div class="bottom">
                <div class="ltime">
                  <span>{{ timeitem.begtime.substr(11, 15) }}</span>
                </div>
                <!-- /60 }}分{{ timeitem.basttimes%60 -->
                <div class="mtime" v-if="timeitem.btagcode === '10'">
                  <span>推荐时长：{{ timeitem.basttime }}</span>
                </div>
                <div class="btime">
                  <span
                    >阅读时长：<md-icon
                      name="time"
                      color="#000"
                      size="md"
                    ></md-icon>
                    {{ timeitem.acttime }}</span
                  >
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <md-scroll-view-more slot="more" :is-finished="loading" />
    </md-scroll-view>
  </div>
</template>

<script>
import { getInteractiveList } from '@/api/abt/customerOperation/clientsort/index'
import loadMore from '@/mixins/loadmore'
export default {
  mixins: [loadMore],
  props: {
    userid: String
  },
  data () {
    return {
      timeList: {},
      pageNo: 1,
      size: 3,
      total: 0,
      totalPage: 0,
      loading: false
    }
  },
  created () {
    this.getData()
  },
  components: {},

  computed: {},

  methods: {
    async getData (isInit = true) {
      let { pageNo, size } = this
      if (!isInit) {
        this.pageNo = ++pageNo
        this.$refs.scrollView.finishLoadMore()
      }
      let data = {
        page: this.pageNo,
        size,
        userid: this.userid
      }
      let res = await getInteractiveList(data)
      // this.timeList = isInit ? res.data.data.rows : this.timeList.concat(res.data.data.rows)
      let Data = res.data.data.rows
      if (isInit) {
        this.timeList = Data
      } else {
        for (var i in this.timeList) {
          for (var j in Data) {
            if (i == j) {
              this.timeList[i] = this.timeList[i].concat(Data[j])
            }
          }
        }
        this.timeList = Object.assign(Data, this.timeList)
      }
      this.total = res.data.data.total
      this.totalPage = res.data.data.totalpage
      return res
    }
  }
}
</script>
<style scoped lang="stylus">
.scroll {
  // overflow-y: auto;
  height: 100%;
}

.timeTag {
  .time {
    .timeBg {
      margin: 50px auto;
      background-color: #fff;
      height: 100px;
      width: 60%;
      border-radius: 50px;
      box-shadow: 4px 6px 5px #dedede;
      position: relative;

      img {
        width: 50px;
        height: 50px;
        pading-right: 50px;
        position: absolute;
        top: 30px;
        left: 150px;
      }

      span {
        font-size: 40px;
        color: color-primary;
        position: absolute;
        top: 30px;
        left: 220px;
      }
    }
  }
}

.detail {
  margin: auto 80px;
  margin-top: 50px;
  margin-bottom: 0.5rem;
  box-shadow: 0.1rem 0.1rem 0.5rem #dedede;
  border-radius: 0.3rem;

  .border {
    padding: 0.3rem 0;
    border-bottom: 1px solid #e5e5e5;
    min-height 220px

    img {
      width: 1.3rem;
      height: 1.3rem;
      border-radius: 50%;
      float: left;
      margin: 0 3% 0 4%;
    }

    .seeWhat {
      width: 75%;
      display: inline-block;
      padding: 0 3% 0 2%;
      // line-height: 0.7rem;

      span {
        font-size: 35px;
        line-height: 60px;

        .color {
          color: color-primary;
        }
      }
    }
  }

  .bottom {
    padding: 0.4rem 0 0.4rem 0.3rem;
    display: flex;
    -webkit-box-pack: justify;
    -ms-flex-pack: justify;
    justify-content: space-between;

    .ltime {
      span {
        font-size 30px
      }
    }

    .mtime {
      span {
        color: red;
        font-size 30px
      }
    }

    .btime {
      margin-right: 30px;
      span {
        font-size 30px
      }
    }
  }
}
</style>
