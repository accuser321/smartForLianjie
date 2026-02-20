<!--
 * @Author: 王鹏
 * @Date: 2019-10-29 16:24:13
 * @LastEditors: 王广婷
 * @LastEditTime: 2020-04-01 17:49:00
 -->
<template>
  <div class="container">
    <!-- 头部 -->
    <div class="head">
      <div class="info">
        <div class="setup">
          <i class="iconfont iconshezhi"
             @click="$router.push({path:'/set'})" />
          <div class="icon_box"
               @click="$router.push({path:'/message'})">
            <i class="iconfont iconxiaoxi" />
            <div class="red"
                 v-if="jrdata.wdMsgCount != '0' && parseInt(jrdata.wdMsgCount) < 99">{{jrdata.wdMsgCount }}</div>
            <div class="red"
                 v-if="parseInt(jrdata.wdMsgCount) > 99">...</div>
          </div>
        </div>
        <div class="vcard">
          <div class="row">
            <div class="col">
              <div class="avatar">
                <img :src="getUser.headimg"
                     alt="avatar" />
              </div>
            </div>
            <div class="col">
              <h4>
                {{ getUser.empname }}<span class="span_text">{{ getUser.empno }}</span>
              </h4>
              <div class="tag">{{ getUser.jgname.split(',')[1] }}</div>
              <p>{{ getUser.jgname.split(',')[getUser.jgname.split(',').length-1] }}</p>
            </div>
          </div>
          <div class="cenbtn"
               @click="$router.push({ path: '/myCenter' })">
            <i class="iconfont iconhuiyuan" />
            <span>个人中心</span>
            <md-icon class="icon" name="arrow-right"
                     size="lg" />
          </div>
          <div class="tag-jf"
               @click="$router.push({ path: '/myScore' })">
            <i class="iconfont iconpoint-active"
               style="color: #bb1a20;font-size 0.28rem" />
            <span style="color:#A4A4A4;font-size 0.28rem"
                  class="jf-mz">我的积分</span>
            <span style="color:#bb1a20;font-size 0.28rem">{{JFNUM}}</span>
            <md-icon name="arrow-right"
                     size="lg"
                     style="color:#BDC0C2;font-size:0.4rem;padding:0 0 0 0.05rem" />
          </div>
        </div>
      </div>
      <div class="datum">
        <div class="datum_left"
             @click="$router.push({path:'/reminders'})">
          <div class="datum_title"></div>
          <div v-if="dataList.data&&dataList.data.alerttype=='1'">
            <span class="span_title">生日提醒</span>
            <span class="span_content">{{dataList.data.pname}}即将生日</span>
          </div>
          <div v-else-if="dataList.data&&dataList.data.alerttype=='2'">
            <span class="span_title">车险提醒</span>
            <span class="span_content">{{dataList.data.pname}}的车险即将到期</span>
          </div>
          <div v-else-if="dataList.data&&dataList.data.alerttype=='3'">
            <span class="span_title">续期提醒</span>
            <span class="span_content">{{dataList.data.pname}}的保险即将到期</span>
          </div>
          <div v-else-if="dataList.data&&dataList.data.alerttype=='4'">
            <span class="span_title">满期提醒</span>
            <span class="span_content">{{dataList.data.pname}}的保险即将到期</span>
          </div>
          <div v-else>
            <span class="span_title">生日提醒</span>
            <span class="span_content">xxx即将生日</span>
          </div>
          <div>
            <span class="datum_look">查看详情</span>
            <span class="span_icon"></span>
          </div>
          <div class="redtwo"
               v-if="dataList.num != '0' && parseInt(dataList.num) < 99">{{dataList.num }}</div>
          <div class="redtwo"
               v-if="parseInt(dataList.num) > 99">...</div>
        </div>
        <div class="datum_right">
          <div class="datum_data">
            <ul>
              <li>
                <h3>{{ jrdata.JRZF }}</h3>
                <p>今日转发</p>
              </li>
              <li>
                <h3>{{ jrdata.JRFC }}</h3>
                <p>今日访次</p>
              </li>
              <li>
                <h3>{{ jrdata.JRHK }}</h3>
                <p>今日获客</p>
              </li>
            </ul>
            <ul>
              <li>
                <h3>{{ jrdata.JYZF }}</h3>
                <p>当月转发</p>
              </li>
              <li>
                <h3>{{ jrdata.JYFC }}</h3>
                <p>当月访次</p>
              </li>
              <li>
                <h3>{{ jrdata.JYHK }}</h3>
                <p>当月获客</p>
              </li>
            </ul>
          </div>
          <div class="datum_to"
               @click="$router.push({path:'/wholookme'})"></div>
        </div>
      </div>
    </div>
    <!-- 内容 -->
    <div class="content">
      <div class="menu_box" v-if="rmhdList.length > 0">
        <div class="hdtitlebox">
          <div class="title">
            <span class="blod"></span>
            <strong>热门活动</strong>
          </div>
          <div class="more" @click="$router.push('/morermhd')"><img src="@/assets/nh/more.png" alt=""></div>
        </div>
        <div class="banner" v-if="rmhdList.length > 0">
          <md-swiper :autoplay="3000"
                    transition="fade"
                    ref="swiper"
                    :is-prevent="false">
            <md-swiper-item :key="index"
                            v-for="(item, index) in rmhdList" @click.native="to()">
                    <div class="img_box">
                      <div><img :src="ossurl + item.img"
                            alt=""></div>
                      <div class="img_text">{{item.title}}</div>
                    </div>
            </md-swiper-item>
          </md-swiper>
        </div>
      </div>
      <div v-for="item in getMenulist"
           class="menu_box"
           v-if="item.id=='PN10001'">
        <div class="title"
             v-if="item.id=='PN10001'">
          <span class="blod"></span>
          <strong>{{ item.mkname }}</strong>
        </div>
        <div class="menu"
             v-if="item.id=='PN10001'">
          <div class="item"
               v-for="it in item.children">
            <router-link tag="div"
                         :to="it.mklj?it.mklj:''">
              <div class="icon"><img :src="ossurl + it.micon"
                     alt="icons"></div>
              <p>{{it.mkname}}</p>
            </router-link>
          </div>
        </div>
      </div>
      <div v-for="(item, index) in getMenulist"
           :key="index"
           class="menu_box"
           v-if="item.id!='PN10001'&&item.id!='20200109152807521236'">
        <div class="title"
             v-if="item.id!='PN10001'&&item.id!='20200109152807521236'">
          <span class="blod"></span>
          <strong>{{ item.mkname }}</strong>
        </div>
        <div class="menu"
             v-if="item.id!='PN10001'">
          <div class="submenu"
               v-for="(it, index) of item.children"
               :key="index">
            <router-link tag="div"
                         :to="it.mklj?it.mklj:''"
                         class="bigtb">
              <h4>{{ it.mkname }}</h4>
              <p>{{ it.mkparms }}</p>
              <div class="rbicon"><img :src="ossurl + it.bicon"
                     alt="icon" /></div>
            </router-link>
          </div>
        </div>
      </div>
    </div>
    <md-landscape v-model="showPic">
      <div class="s-box">
        <div class="s_img">
          <img src="@/assets/image/centerqdjf.png">
        </div>
        <div class="s-bom">
          <div class="s-title">
            <div class="s-t1"><span><i class="iconfont iconzuobianhenggang" /></span></div>
            <div class="s-t2">
              <p> 奖励 </p>
            </div>
            <div class="s-t3"><span><i class="iconfont iconzuobianhenggang" /></span></div>
          </div>
          <div class="s-res">
            <div class="s-r1"><span>积分 </span></div>
            <div class="s-r2"><span>+ {{jf}} </span></div>
          </div>
          <div class="s-btn">
            <button style="font-size: 28px;
                            background-color: #bb1a20;
                            color: #ffffff;
                            font-size: 0.35rem;
                            width: 50%;
                            height: 30px;
                            border-bottom-left-radius: 8px;
                            border-bottom-right-radius: 8px;
                            border-top-left-radius: 8px;
                            border-top-right-radius: 8px;"
                    @click="btn">收下</button>
          </div>
        </div>

      </div>
    </md-landscape>
  </div>
</template>
<script>
import { getNowDayCount, getJf, getJfSum, statistics } from '@/api/basic/index'
import { getStorage } from '@/lib/util'
import { getrmhdlist } from '@/api/basic'
import { mapGetters } from 'vuex'
export default {
  // name: 'sxhome',
  data () {
    return {
      getUser: {},
      getMenulist: [],
      dataList: [],
      jrdata: {},
      jfdata: {},
      ossurl: getStorage('ossurl', ''),
      showPic: false,
      jf: 0,
      JFNUM: 0,
      getjfnum: {},
      rmhdList: [],
      tjcode: ''
    }
  },
  computed () {
    mapGetters(['getossurl'])
  },
  created () {
    // debugger;
    this.getUser = getStorage('u_s', {})
    this.getMenulist = getStorage('m_l', '')
    // console.log("菜单");
    // console.log( this.getMenulist)
    this.tjcode = getStorage('u_s', {}).jglower ? getStorage('u_s', {}).jglower : getStorage('u_s', {}).tjcode
    this.getdata()
    this.getqdjf()
    this.getzntxList()
    this.getRmhdList()
  },
  methods: {
    getdata () {//首页获取数据
      getNowDayCount().then(res => {
        this.jrdata = res.data.data
      })
    },
    getqdjf () {
      getJf().then(res => { //获取登录积分
        this.jfdata = res.data.data
        if (res.data.data === null) {
          this.jf = 0
        } else {
          this.jf = this.jfdata.JF
          // 判断今天第一次登录
          if (this.jfdata.state === 1) {
            this.showPic = true
          }
        }
        this.getJfSum()
      })
    },
    getJfSum () {
      getJfSum().then(res => {//获取我的积分
        this.getjfnum = res.data.data
        if (res.data.data === null) {
          this.JFNUM = 0
        } else {
          this.JFNUM = this.getjfnum
        }
      })
    },
    btn () {
      this.showPic = false
    },
    // 智能提醒显示数据
    getzntxList () {
      statistics().then(res => {
        this.dataList = res.data.data
      })
    },
    getRmhdList () { //热门活动列表
      getrmhdlist({ tjcode: this.tjcode, page: 1, size: 10, flag: 0 }).then(res => {
        this.rmhdList = res.data.data.rows
      })
    },
    to () {
      let rmhd = this.rmhdList[this.$refs.swiper.getIndex()]
      rmhd.urltype == 'inner' ? this.$router.push({ path: '/rmhddetail', query: { activityno: rmhd.activityno } }) : location.replace(`${rmhd.url}`)
    }
  }
}
</script>
<style lang="stylus" scoped>
bg-color = #ffffff;

/* 修改今日转发、仿次、获克的样式 */
.icon_box {
  display: inline-block;
}

.red {
  width: 0.6rem;
  height: 0.6rem;
  line-height: 0.6rem;
  text-align: center;
  color: #fff;
  font-size: 0.35rem;
  border-radius: 50%;
  display: inline-block;
  background: red;
  position: relative;
  bottom: 0.3rem;
  right: 0.4rem;
}

.bigtb {
  height: 100%;
}

.head {
  background-color: bg-color;

  .datum {
    height: 400px;
    padding: 50px;
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    background: bg-color;

    .datum_left {
      position: relative;
      width: 30%;
      height: 100%;
      background: url('~@/assets/image/center_bg.png');
      background-repeat: no-repeat;
      background-size: contain;

      .datum_title {
        width: 100%;
        height: 0.6rem;
        position: relative;
        left: -3px;
        top: 20px;
        background: url('~@/assets/image/center_title.png');
        background-repeat: no-repeat;
        background-size: contain;
      }

      span {
        margin-left: 0.2rem;
        display: block;
      }

      .span_title {
        color: color-primary;
        font-size: 0.35rem;
        font-weight: bold;
        margin-top: 0.3rem;
        margin-bottom: 0.1rem;
      }

      .span_content {
        color: #989898;
        font-size: 0.25rem;
        margin-right: 0.5rem;
      }

      .datum_look {
        display: inline;
        color: color-primary;
        font-size: 0.3rem;
        font-weight: 500;
      }

      .span_icon {
        display: inline-block;
        width: 30px;
        height: 30px;
        margin-left: 0.1rem;
        position: relative;
        top: 0.08rem;
        background: url('~@/assets/image/center_arrow.png');
        background-repeat: no-repeat;
        background-size: contain;
      }
    }

    .datum_right {
      width: 70%;
      height: 96%;
      margin-top: 0.08rem;
      border: 1px solid #F5F5F5;
      box-shadow: 0 0.05rem 0.05rem #F5F5F5;
      display: flex;

      .datum_data {
        width: 87%;
        height: 100%;

        ul {
          display: flex;
          height: 50%;
          padding-top: 0.3rem;

          li {
            width: 33%;
            text-align: center;
            align-items: center;

            p {
              font-size: 0.3rem;
              color: #989898;
              font-weight: 500;
            }
          }
        }
      }

      .datum_to {
        width: 13%;
        height: 100%;
        background: url('~@/assets/image/center_to.png');
        background-repeat: no-repeat;
        background-size: cover;
      }
    }

    h3 {
      margin-bottom: 5px;
      font-size: 36px;
    }

    p {
      color: #999999;
    }
  }
}

.info {
  background: url('~@/assets/image/bg1.png');
  background-repeat: no-repeat;
  background-size: 100% 90%;
  padding: 40px 25px 20px;
  margin-bottom: 10px;

  .vcard {
    background-color: bg-color;
    border-radius: 10px;
    padding: 80px 0;
    box-shadow: 0 5px 5px #E7E7EA;
    position: relative;

    .cenbtn {
      position: absolute;
      right: -5px;
      top: 60%;
      transform: translateY(-110%);
      background-image: linear-gradient(to right, #FCB072, #FB7F25);
      padding: 10px 5px 10px 20px;
      color: bg-color;
      border-top-left-radius: 50px;
      border-bottom-left-radius: 50px;
      line-height 0.4rem
      .iconfont{
        font-size 15px
      }
      span {
        margin: 0 5px;
        font-size 0.35rem
      }
      .icon{
        font-size 0.35rem
      }
    }

    .tag-jf {
      position: absolute;
      right: -5px;
      top: 58%;
      transform: translateY(-90%);
      background-image: linear-gradient(to right, #EAF1F8, #EAF1F8);
      padding: 10px 5px 10px 20px;
      color: bg-color;
      border-top-left-radius: 35px;
      border-bottom-left-radius: 35px;
      margin-top: 75px;
      display: inline-block;
      color: bg-color;
      line-height 0.4rem
      i {
        font-size: 0.28rem;
      }

      span {
        font-size: 0.28rem;
        margin: 0 5px;
      }
    }

    .row {
      display: flex;
      align-items: center;
      padding-left: 20px;

      >.col:last-child {
        margin-left: 20px;

        h4 {
          font-size: 35px;

          .span_text {
            margin-left: 10px;
          }
        }

        p {
          font-size: 28px;
          color: #9B9B9B;
        }

        .tag {
          padding: 10px 28px;
          margin: 30px 0;
          font-size: 28px;
          background-image: linear-gradient(to right, color-primary, color-primary);
          border-radius: 30px;
          display: inline-block;
          color: bg-color;
        }
      }

      .avatar {
        width: 200px;
        height: 200px;
        border-radius: 50%;
        overflow: hidden;
      }
    }
  }

  .info {
    padding: 0 25px;
  }

  .setup {
    text-align: right;
    margin-bottom: 40px;

    .iconfont {
      margin: 0 6px;
      font-size: 19px;
      color: bg-color;

      &:last-child {
        font-size: 17px;
        vertical-align: 1px;
      }
    }
  }
}

.content {
  background-color: #F6F6FA;

  .menu_box {
    padding: 40px 25px;
    margin-bottom: 30px;
    background-color: bg-color;
    &:nth-child(1){
      padding-top 0

    }
    .hdtitlebox{
      display flex
      flex-direction row
      align-items center
      justify-content space-between
      .more{
        width 1.2rem
        height 0.7rem
      }
    }
    .banner {
      width: 95%;
      margin: 0 auto;
      height: 3.5rem;
      border-radius 0.2rem
      overflow hidden
      margin-top 0.3rem
      .img_box{
          position relative
          margin: auto
          .img_text{
            background: #000
            position: absolute
            height: 0.8rem
            width: 100%
            top: 2.6rem
            z-index: 999!important
            opacity: 0.6
            color: #ffffff
            text-align: center
            line-height: 0.8rem
          }
      }
  }
  }

  .menu {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    margin-bottom: 20px;

    .item {
      width: 25%;
      text-align: center;
      margin-top: 40px;

      .icon {
        width: 80px;
        height: 80px;
        margin: 0 auto 20px auto;
      }
    }

    .submenu {
      width: 48%;
      border-radius: 10px;
      background-color: #FFEEF7;
      margin-top: 30px;
      padding: 40px 0 0 28px;
      position: relative;
      height: 200px;

      &:nth-child(2n+1) {
        margin-right: 4%;
      }

      h4 {
        font-size: 31px;
      }

      p {
        font-size: 24px;
        color: #C2BEBD;
      }

      .rbicon {
        width: 140px;
        position: absolute;
        bottom: 10px;
        right: 10px;
      }
    }
  }

  /* 修改标题左边标识的样式 */
  .blod {
    height: 40px;
    width: 12px;
    background: color-primary;
    display: inline-block;
    vertical-align: -6px;
    margin-right: 10px;
  }
}

.s-img {
  display: block;
}

.s-box {
  width: 100%;

  // height 650px;
  .s_img {
    img {
      display: block;
    }
  }

  .s-bom {
    width: 100%;
    background-color: #FFFFFF;
    height: 400px;
    border-bottom-left-radius: 18px;
    border-bottom-right-radius: 18px;
    border-top-left-radius: 18px;
    border-top-right-radius: 18px;
    position: relative;
    top: -20px;
  }

  .s-title {
    display: flex;
    justify-content: space-around;
    line-height: 70px;

    .s-t1 {
      width: 30%;
      text-align: right;
    }

    .s-t2 {
      width: 25%;
      color: #C49961;
      font-size: 0.4rem;
      text-align: center;
    }

    .s-t3 {
      width: 30%;
      text-align: left;
    }

    p {
      font-size: 0.4rem;
    }
  }

  .s-res {
    display: flex;
    justify-content: space-between;
    line-height: 0.7rem;
    margin-top: 60px;

    .s-r1 {
      width: 45%;
      color: #C49961;
      text-align: right;
    }

    .s-r2 {
      width: 45%;
      color: #C49961;
      text-align: left;
    }

    span {
      font-size: 0.5rem;
    }
  }

  .s-btn {
    display: flex;
    justify-content: center;
    text-align: center;
    margin-top: 70px;
  }
}

.redtwo {
  width: 0.6rem;
  height: 0.6rem;
  line-height: 0.6rem;
  text-align: center;
  color: #fff;
  font-size: 0.35rem;
  border-radius: 50%;
  display: inline-block;
  background: #f00;
  position: absolute;
  bottom: 2.4rem;
  left: 1.9rem;
}
/deep/ .md-landscape-content{
  margin auto
}
</style>
