<!--
 * @Author: 王广婷
 * @Date: 2019-12-21 10:14:13
 * @LastEditors: 刘格优
 * @LastEditTime: 2020-04-01 16:43:04
 -->
<template>
  <div class="container">
    <!-- 内容 -->
    <div class="content">
      <div v-for="item in ZNYXlist"
           class="menu_box">
        <div class="title">
          <h3>{{ item.mkname }}</h3>
        </div>
        <div class="menu">
          <div class="submenu"
               v-for="it in item.children">
            <router-link tag="div"
                         :to="it.mklj?it.mklj:''">
              <div class="icon"><img :src="ossurl + it.micon"
                     alt="icons"></div>
              <p class="menu_title">{{it.mkname}}</p>
            </router-link>
          </div>
        </div>
      </div>
      <div v-for="item in KHFWlist"
           class="menu_box">
        <div class="title">
          <h3>{{ item.mkname }}</h3>
        </div>
        <div class="menu">
          <div class="item"
               v-for="it in item.children">
            <router-link tag="div"
                         :to="it.mklj?it.mklj:''">
              <div class="icon">
                <img :src="ossurl + it.bicon"
                     alt="icons">
                <!--v-if="jrdata.wdMsgCount != '0' && parseInt(jrdata.wdMsgCount) < 99"-->
                <!--v-if="parseInt(jrdata.wdMsgCount) > 99"-->
                <!--<div class="red">{{jrdata.wdMsgCount }}</div>-->
                <!--<div class="red">...</div>-->
              </div>
              <p class="menu_title">{{it.mkname}}</p>
              <p class="p_text">{{it.mkinfo}}</p>
            </router-link>
            <!-- <div v-if="it.id=='PN10004'"
                 class="new_icon"></div> -->
            <div v-show="it.mkname == '消息中心'"
                 class="showNoRead">
              <div class="red"
                   v-if="jrdata.wdMsgCount != '0' && parseInt(jrdata.wdMsgCount) < 99">{{jrdata.wdMsgCount }}</div>
              <div class="red"
                   v-if="parseInt(jrdata.wdMsgCount) > 99">...</div>
            </div>
          </div>
        </div>
      </div>
      <div v-for="item in TDGLlist"
           class="menu_box">
        <div class="title">
          <h3>{{ item.mkname }}</h3>
        </div>
        <div class="menu-tdgl">
          <div class="submenu"
               v-for="(it, index) of item.children"
               :key="index">
            <router-link tag="div"
                         :to="it.mklj?it.mklj:''"
                         class="bigtb">
              <h4 class="menu_title">{{ it.mkname }}</h4>
              <p>{{ it.mkparms }}</p>
              <div class="tolook">
                <div class="look_text">马上查看</div>
                <div class="look_icon">
                  <div class="icon"></div>
                </div>
              </div>
              <div class="rbicon"><img :src="ossurl + it.bicon"
                     alt="icon" /></div>
            </router-link>
          </div>
        </div>
      </div>
    </div>
    <!-- 尾部 -->
    <div class="foot"></div>
  </div>
</template>
<script>
import { getNowDayCount } from '@/api/basic/index'
import { getStorage, setStorage } from '@/lib/util'
import { getInit } from '@/api/basic'
import { mapGetters } from 'vuex'
export default {
  data () {
    return {
      getUser: {},
      getMenulist: [],
      jrdata: {},
      ossurl: getStorage('ossurl', ''),
      ZNYXlist: [{ mkname: '智能营销', children: [] }],
      KHFWlist: [{ mkname: '客户服务', children: [] }],
      TDGLlist: [{ mkname: '团队管理', children: [] }],
      color: '#666666',
      bgshow: false
    }
  },
  computed () {
    mapGetters(['getossurl'])
  },
  // beforeRouteEnter (to, from, next) {
  //   if (from.path != '/login') {
  //     /*
  //      * 修改用户信息
  //      * */
  //     getInit().then(res => {
  //       setStorage('u_s', res.data.data.user)
  //       setStorage('m_l', res.data.data.menulist)
  //       setStorage('ossurl', res.data.data.ossurl)
  //       setStorage('comname', res.data.data.comname)
  //       setStorage('imgs', res.data.data.abtComModelsMobileImgs)
  //     })
  //   }
  //   next()
  // },
  created () {
    this.getUser = getStorage('u_s', {})
    this.getMenulist = getStorage('m_l', '')
    this.getMenulist.map(item => {
      item.children.map(it => {
        if (it.id == 'PN10002' || it.id == 'PN10003' || it.id == 'PN10008' || it.id == 'PN10009') {
          this.ZNYXlist[0].children.push(it)
        }
        if (it.id == 'PN10004' || it.id == 'PN10005' || it.id == 'PN10006' || it.id == 'PN10007' || it.id == 'PN10011' || it.id == '20191224094158060968' || it.id == 'PN100034') {
          it.id == 'PN10004' ? it.mkinfo = '专属精美海报' : ''
          it.id == 'PN10005' ? it.mkinfo = '个性化实时天气' : ''
          it.id == 'PN10006' ? it.mkinfo = '在线也能发贺卡' : ''
          it.id == 'PN10007' ? it.mkinfo = '微信高效邀约' : ''
          it.id == 'PN10011' ? it.mkinfo = '双向沟通不受限' : ''
          it.id == '20191224094158060968' ? it.mkinfo = '反馈提醒早知道' : ''
          it.id == 'PN100034' ? it.mkinfo = '消息提醒早知道' : ''
          this.KHFWlist[0].children.push(it)
        }
        if (it.id == 'PN10012' || it.id == 'PN10013') {
          it.id == 'PN10012' ? it.mkparms = '团队实时播报' : ''
          it.id == 'PN10013' ? it.mkparms = '团队工作分析' : ''
          this.TDGLlist[0].children.push(it)
        }
      })
    })
    this.getdata()
  },
  methods: {
    getdata () {
      getNowDayCount().then(res => {
        this.jrdata = res.data.data
      })
    },
    tomorning () {
      this.$router.push('/morning')
    },
    tozt () {
      this.$router.push('/bszt')
    },
    tojys () {
      window.location.href = 'http://dc.ishdr.com/dred/index.html'
    },
    closemc () {
      this.bgshow = false
    }
  }
}
</script>
<style lang="stylus" scoped>
bg-color = #ffffff;
bj-color = #efefef;
font-color = #989898;
changeCol(#008070);

.container {
  height: 100%;
  background-color: bg-color;
  overflow-y: auto;

  .head {
    border-bottom: 0.3rem solid bj-color;

    .info {
      background: url('~@/assets/dr/bg.png');
      background-repeat: no-repeat;
      background-size: 100% 106%;
      padding: 20px 2.5vw 20px;

      .setup {
        text-align: right;
        margin-bottom: 40px;
        margin-top: 40px;

        img {
          width: 0.5rem;

          &:nth-child(1) {
            margin-right: 0.3rem;
          }
        }
      }

      .row {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding-left: 35px;

        .col:last-child {
          margin-right: 30px;
        }

        h1 {
          font-size: 0.6rem !important;

          .span_text {
            font-size: 0.5rem;
            margin-left: 0.3rem;
          }
        }

        .jg_info {
          color: font-color !important;
          display: inline-block;
          margin-top: 0.2rem;
        }

        .degree {
          margin-top: 0.2rem;
          color: font-color !important;
        }

        .avatar {
          width: 200px;
          height: 200px;
          border-radius: 50% !important;
          overflow: hidden;
          border: 0.1rem solid bg-color;
        }
      }

      .head_info {
        height: 1.5rem;
        line-height: 1.5rem;

        ul {
          display: flex;
          flex-direction: row;
          justify-content: space-around;

          li {
            color: font-color;

            .span_style {
              color: color-primary;
              margin-left: 0.2rem;
              font-weight: 500;
              font-size: 0.5rem;
            }
          }
        }
      }
    }
  }

  .banner {
    margin-top: 0.5rem;

    img {
      width: 94%;
      margin-left: 3%;
    }
  }

  .content {
    .menu_box {
      padding: 0 0.25rem 0.4rem;
      border-bottom: 0.3rem solid bj-color;

      &:last-child {
        border-bottom: none;
      }

      .title {
        line-height: 1.4rem;

        h3 {
          color: #3d3d3d;
        }
      }

      .menu {
        display: flex;
        align-items: center;
        flex-wrap: wrap;
        margin-bottom: 20px;
        padding: 0 0.5rem;

        .submenu {
          width: 25%;
          text-align: center;
          margin-top: 40px;

          .icon {
            width: 1rem;
            height: 0.9rem;
            margin: 0 auto 20px auto;

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
          }

          .menu_title {
            color: #4f4f4f;
          }
        }

        .item {
          width: 32%;
          height: 3rem;
          text-align: center;
          margin-bottom: 40px;
          margin-right: 2%;
          box-shadow: #f0f0f0 0 0 20px;
          position: relative;

          .showNoRead {
            position: absolute;
            top: 40px;
            right: 0;
          }

          &:nth-child(3n+3) {
            margin-right: 0;
          }

          .icon {
            width: 0.9rem;
            height: 0.9rem;
            margin: 0.45rem auto 0.2rem auto;
          }

          .menu_title {
            color: #4f4f4f;
          }

          .p_text {
            color: #A2A2A2;
            line-height: 0.8rem;
            font-size: 0.3rem;
            letter-spacing: 2px;
          }

          .new_icon {
            width: 1rem;
            height: 0.5rem;
            background: url('~@/assets/dr/new.png');
            background-repeat: no-repeat;
            background-size: contain;
            position: absolute;
            top: 0;
            right: 0;
          }
        }
      }
    }
  }

  .foot {
    width: 60%;
    margin: auto;
    height: 1rem;
    background: url('~@/assets/dr/footer.png');
    background-repeat: no-repeat;
    background-size: contain;
  }

  .phoneshadow {
    display: inline-block;
    height: 1.2rem;
    line-height: 1.2rem;
    width: 43vw;
    border-radius: 10px;
    margin: 0 2vw;
    box-shadow: 0.2rem 0.1rem 0.5rem 0rem rgba(33, 130, 111, 0.25);
    position: relative;
    border-radius: 60px;

    .word {
      font-size: 0.36rem;
    }

    .img {
      width: 0.6rem;
      height: auto;
      margin: 0 0.3rem;
      position: relative;
      top: 0.2rem;
    }
  }

  .iconfont {
    margin: 0 5px;
    font-size: 0.5rem;
    position: relative;
    top: 0.05rem;
  }

  .contbox {
    margin-bottom: 0.3rem;
  }

  .first {
    margin: 0 0.3rem 0 0.6rem;
    color: #039E89;
  }
}

.menu-tdgl {
  display: flex;
  margin: 0 0.4rem;

  .submenu {
    width: 48%;
    border-radius: 10px;
    background-color: #F5F8FD;
    margin-top: 30px;
    padding: 25px 0 0 28px;
    position: relative;
    height: 200px;
    background: #f8f8fa;

    &:nth-child(2n+1) {
      margin-right: 4%;
    }

    h4 {
      font-size: 36px;
      color: #4f4f4f;
    }

    p {
      font-size: 24px;
      color: #C2BEBD;
    }

    .tolook {
      display: flex;
      margin-top: 20px;

      .look_text {
        font-size: 27px;
        color: gray;
      }

      .look_icon {
        width: 30px;
        height: 30px;
        background: #ffffff;
        border-radius: 50%;
        margin-left: 10px;
        margin-top: 5px;

        /* box-shadow: 0.2rem 0.1rem 0.5rem 0rem rgba(33, 130, 111, 0.25); */
        /* border: 1px solid #000; */
        .icon {
          margin: 6px auto;
          width: 0;
          height: 0;
          border: 10px solid transparent;
          border-left-color: color-primary;
          position: relative;
          left: 6px;
        }
      }
    }

    .rbicon {
      width: 1rem;
      position: absolute;
      bottom: 0.35rem;
      right: 0.1rem;
    }
  }
}

.red {
  width: 0.5rem;
  height: 0.5rem;
  line-height: 0.5rem;
  text-align: center;
  color: #fff;
  font-size: 0.3rem;
  border-radius: 50%;
  display: inline-block;
  background: red;
  position: relative;
  bottom: 0.3rem;
  right: 0.4rem;
}
</style>
