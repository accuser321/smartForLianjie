<!--
 * @Author: 刘格优
 * @Date: 2019-11-29 15:13:48
 * @LastEditors: 刘格优
 * @LastEditTime: 2020-03-17 17:29:30
 -->

<template>
  <div class="content">
    <div class="nodata"
         v-show="isnull">
      <img class="kongimg"
           src="@/assets/image/null.png"
           alt="" />
    </div>
    <div v-show="!isnull"
         class="scroll">
      <md-scroll-view ref="scrollView"
                      auto-reflow
                      :scrolling-x="false"
                      @end-reached="loadMore">
        <ul>
          <li v-for="(item, index) in lists"
              :key="index"
              @click="tozx(item.userid)">
            <div class="wordbox">
              <div class="left">
                <div class="imgbox">
                  <img :src="item.headimg"
                       alt="" />
                </div>
                <div class="word">
                  <p>{{ item.khname }}</p>
                  <p>{{item.content.length>10? item.content.substr(0,10)+'...':item.content}}</p>
                </div>
              </div>
              <div class="right">
                <span>{{ item.intime}}</span><br />
                <md-tag size="large"
                        shape="bubble"
                        class="tagRed"
                        fill-color="linear-gradient(90deg, #FF5B60 0%, #F98472 100%)"
                        type="fill"
                        v-show="item.unread - 1 >= 0">{{ item.unread }}</md-tag>
                <img :src="getStorage('ossurl','')+getStorage('imgs',{}).xxicon"
                     alt=""
                     class="tozx" />
              </div>
            </div>
          </li>
        </ul>
        <md-scroll-view-more slot="more"
                             :is-finished="loading" />
      </md-scroll-view>
    </div>
  </div>
</template>

<script>
import { selectMessage } from '@/api/abt/customerOperation/looked/index'
import loadMore from '@/mixins/loadmore'
import { getStorage } from '@/lib/util'
export default {
  mixins: [loadMore],
  data () {
    return {
      pageNo: 1,
      size: 12,
      total: 0,
      totalPage: 0,
      loading: false,
      lists: [],
      isnull: false,
      user: {}
    }
  },
  created () {
    this.user = getStorage('u_s', {})
    this.getData()
  },
  components: {},

  computed: {},

  methods: {
    getData (isInit = true) {  //查询出咨询列表
      let { pageNo, size } = this
      if (!isInit) {
        this.pageNo = ++pageNo
        this.$refs.scrollView.finishLoadMore()
      }
      let data = {
        page: this.pageNo,
        size
      }
      selectMessage(data).then(res => {
        this.lists =
          this.lists == []
            ? res.data.data.rows
            : this.lists.concat(res.data.data.rows)
        if (this.lists.length == 0) {
          this.isnull = true
        } else {
          this.lists.forEach((item, index) => {
            this.$set(item, 'intime', item.content.substring(5, 10))
            if (item.content.substr(19).split('&#&')[0] == '') {
              item.content = '文章'
            } else {
              item.content = item.content.substr(19)
            }
            // console.log(item)
          })
        }
        this.total = res.data.data.total
        this.totalPage = res.data.data.totalpage
      })
    },
    tozx (userid) {//跳转到咨询页面
      this.$router.push(
        `Consult?askuserid=${userid}&empno=${this.user.empno}&emprytype=${this.user.rytype}`
      )
    }
  }
}
</script>
<style scoped lang="stylus">
.content {
  overflow-y: hidden;
  height: 100%;
}

.scroll {
  overflow-y: auto;
  height: 100%;

  li {
    height: 2.5rem;
    border-bottom: 1px solid #E3E3E3;
  }

  .wordbox {
    height: 100%;
    padding: 0.3rem 0;
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

.left {
  float: left;
  width: 75vw;

  .imgbox {
    display: inline-block;
    padding: 0 0.3rem;

    img {
      width: 1.5rem;
      height: 1.5rem;
      border-radius: 50%;
    }
  }

  .word {
    display: inline-block;
    line-height: 1rem;

    p:nth-child(1) {
      font-size: 0.4rem;
      font-weight: 500;
    }

    p:nth-child(2) {
      font-size: 0.35rem;
    }
  }
}

.right {
  float: right;
  color: #A9A9A9;
  font-size: 15px;
  position: relative;
  width: 25vw;
  text-align: right;
  padding-right: 0.5rem;
  padding-top: 0.2rem;

  .tagRed {
    position: absolute;
    right: 0.3rem;
  }
}

.tozx {
  width: 1.1rem;
  height: auto;
  padding-top: 20px;
}
</style>
