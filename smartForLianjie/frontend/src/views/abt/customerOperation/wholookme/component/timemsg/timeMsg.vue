<template>
  <div>
    <div class="timeTag"
         v-for="(item, index) in timeList"
         :key="index">
      <div class="time">
        <div class="timeBg">
          <img src="@/assets/abt/img/timeicon.png" />
          <span>{{ index }}</span>
        </div>
      </div>
      <div>
        <div class="detail"
             v-for="(timeitem, timeindex) in item"
             :key="timeindex"
             @click="tokhwj(timeitem.userid)">
          <div class="border">
            <div>
              <img :src="timeitem.headimg" />
            </div>
            <div class="seeWhat"
                 v-if="timeitem.btagcode === '9'">
              <span>{{ timeitem.khname
                }}<span class="color">查看</span>了你的名片，沟通从此刻开始</span>
            </div>
            <div class="seeWhat"
                 v-if="timeitem.btagcode === '10'">
              <span>{{ timeitem.khname
                }}<span class="color">查看</span>了你发布的文章<span class="color">{{ timeitem.stitle }}</span>，看来TA对你感兴趣</span>
            </div>
            <div class="seeWhat"
                 v-if="timeitem.btagcode === '5'">
              <span>{{ timeitem.khname
                }}<span class="color">查看</span>了你的邀请函<span class="color">{{ timeitem.stitle }}</span>，尽快联系他吧</span>
            </div>
            <div class="seeWhat"
                 v-if="timeitem.btagcode === '4'">
              <span>{{ timeitem.khname
                }}<span class="color">查看</span>了你的贺卡<span class="color">{{ timeitem.stitle }}</span>，他应该很喜欢你的祝福</span>
            </div>
            <div class="seeWhat"
                 v-if="timeitem.btagcode === '12'">
              <span>{{ timeitem.khname
                }}<span class="color">查看</span>了你公司的<span class="color">官网</span>，看来TA对你的公司感兴趣</span>
            </div>
            <div class="seeWhat"
                 v-if="timeitem.btagcode === '11'">
              <span>{{ timeitem.khname}}<span class="color">查看</span>了你发送的<span class="color">{{ timeitem.remark}}</span>解析报告</span>
            </div>
            <div class="seeWhat"
                 v-if="timeitem.btagcode === '13'">
              <span>{{ timeitem.khname}}<span class="color">查看</span>了<span class="color">{{ timeitem.remark}}</span>的家庭解析报告</span>
            </div>
            <div class="seeWhat"
                 v-if="timeitem.btagcode === '14'">
              <span>{{ timeitem.khname}}<span class="color">查看</span>了<span class="color">{{ timeitem.remark}}</span>的客户分析报告</span>
            </div>
            <div class="seeWhat"
                 v-if="timeitem.btagcode === '15'">
              <span>{{ timeitem.khname}}<span class="color">查看</span>了<span class="color">{{ timeitem.remark}}</span>的NBS报告</span>
            </div>
            <div class="seeWhat"
                 v-if="timeitem.btagcode === '7'">
              <span>{{ timeitem.khname
                }}<span class="color">查看</span>了你分享的产品<span class="color">{{ timeitem.stitle }}</span>，看来TA对该产品感兴趣</span>
            </div>
            <div class="seeWhat"
                 v-if="timeitem.btagcode === '17'">
              <span>{{ timeitem.khname
                }}<span class="color">查看</span>了你分享的微店<span class="color">{{ timeitem.stitle }}</span>，看来TA对该微店感兴趣</span>
            </div>
          </div>
          <div class="bottom">
            <div class="ltime">
              <span>{{ timeitem.begtime.substr(11, 15) }}</span>
            </div>
            <!-- /60 }}分{{ timeitem.basttimes%60 -->
            <div class="mtime"
                 v-if="timeitem.btagcode === '10'">
              <!-- {{
                timeitem.basttime.match(/分(\S*)秒/)[1] == "0"
                  ? timeitem.basttime.match(/(\S*)分/)[1] + "分"
                  : timeitem.basttime
              }} -->
              <span>推荐时长：{{
                  timeitem.basttime.match(/分(\S*)秒/)[1] == "0"
                    ? timeitem.basttime.match(/(\S*)分/)[1] + "分"
                    : timeitem.basttime
                }}</span>
            </div>
            <div class="btime">
              <span v-show="timeitem.acttime != '00:00'">阅读时长：<md-icon name="time"
                         color="#000"
                         size="md"></md-icon>
                {{ timeitem.acttime }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    timeList: {},
    isSeeCard: String
  },
  data () {
    return {
      height: ''
    }
  },
  created () {
    this.height = document.body.clientHeight
  },
  methods: {
    tokhwj (userid) {  //用户id
      this.$router.push({
        name: 'clientAnalysis',
        query: { customer: userid.toString() }
      })
    }
  }
}
</script>
<style lang="stylus" scoped>
@import './time.styl';
</style>
