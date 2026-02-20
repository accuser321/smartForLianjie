<!--
 * @Author: 李波
 * @Date: 2020-02-11 17:57:52
 * @LastEditors: 刘格优
 * @LastEditTime: 2020-04-01 17:30:39
 -->
<template>
  <div class="container">
    <div class="top">
      <div class="md-example-item-s">
        <md-icon name="service"
                 size="lg"
                 @click="$router.push({path:'/customerserver'})"></md-icon>
      </div>
      <div class="md-box"
           @click="$router.push({path:'/drnews'})">
        <i class="iconfont iconxiaoxi" />
        <div class="red"
             v-if="wdmsgdata.num > 0">{{wdmsgdata.num}} </div>
      </div>
      <!--  搜索框  -->
      <div class="searchbox clearfix">
        <div class="searchline clearfix">
          <button class="btnicon">
            <md-icon name="search"
                     size="lg"></md-icon>
          </button>
          <input type="text"
                 placeholder="搜索产品名称"
                 v-model="searchValue"
                 v-on:focus="getFocus"
                 class="searchinput">
          <button class="btnclear"
                  @click="clearinput"
                  v-show="isclear">
            <md-icon name="fail"
                     size="lg"
                     color="#666"></md-icon>
          </button>
        </div>
        <div class="searchTxt"
             @click="yourmethod">搜索</div>
      </div>
    </div>
    <!-- banner区域 -->
    <div class="banner">
      <md-swiper :autoplay="3000"
                 transition="fade"
                 ref="swiper"
                 :is-prevent="false"
                 v-if="getbanner.length > 0">
        <md-swiper-item :key="index"
                        v-for="(item, index) in getbanner">
          <img :src="ossurl + item.pichttp"
               @click="banclick()">
        </md-swiper-item>
      </md-swiper>
    </div>
    <!-- 菜单栏 -->
    <div class="content">
      <div class="menu_box">
        <div class="menu">
          <div class="item"
               v-for="(it, index) in getbar"
               :key="index">
            <router-link tag="div"
                         :to="it.mklj?it.mklj:''">
              <div class="icon"><img :src="ossurl + it.pichttp"
                     alt="icons"></div>
              <p class="p_text">{{it.mkname}}</p>
            </router-link>
          </div>
        </div>
      </div>
      <!-- 推荐内容 -->
      <div class="menu_box">
        <div class="title">
          <span class="blod"></span>
          <span class="fenlei">推荐内容</span>
          <div class="title_more"
               @click="clickmoretj ()">更多</div>
        </div>
        <div class="con_box">
          <div id="tj_menu"
               class="con_menu"
               v-for="(aitem,index) in tjlist"
               :key="index">
            <div class="con_menu-item tjcontent"
                 @click="cpjumptj (aitem)">
              <span class="con_menu-title">{{aitem.prodname}}</span>
              <div class="bujucontent">
                <div class="left">
                  <p class="con_menu-oneaa">{{aitem.pdesc}}</p>
                </div>
                <div class="right">
                  <img :src="ossurl + aitem.iconhttp"
                       alt="">
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 医疗险等 -->
      <div class="menu_box"
           v-for="item in menulist"
           :key="item.code">
        <div class="title">
          <span class="blod"></span>
          <span class="fenlei">{{item.name}}</span>
          <div class="title_more"
               @click="clickmore(item)">更多</div>
        </div>
        <div class="con_box">
          <div class="con_menu"
               v-for="(itema,index) in item.list"
               :key="index">
            <div class="con_menu-item samestyle"
                 @click="cpjump(itema)">
              <span class="con_menu-title">{{itema.prodname}}</span>
              <div class="bujucontent">
                <div class="left">
                  <p class="con_menu-one">{{itema.pdesc}}</p>
                  <div style="height: 0.6rem;">
                    <div class="input-red"
                         v-if="itema.promotionprice&&flag!='1'">推广费最高{{itema.promotionprice}}</div>
                  </div>
                </div>
                <div class="right">
                  <img :src="ossurl + itema.iconhttp"
                       alt="">
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="bg_bottom">
        <div class="bot-con">
          <div class="bot-title">
            <span></span>
            <span>汇立天下</span>
            <span></span>
          </div>
          <div class="bot-content">
            <ul>
              <li>
                <div><img src="@/assets/nh/shouye-07.png"
                       alt=""></div>
                <p>专业服务</p>
              </li>
              <li>
                <div><img src="@/assets/nh/shouye-08.png"
                       alt=""></div>
                <p>合规稳健</p>
              </li>
              <li>
                <div><img src="@/assets/nh/shouye-09.png"
                       alt=""></div>
                <p>品质保障</p>
              </li>
            </ul>
          </div>
          <p class="p_con">南华保险经纪有限公司</p>
        </div>
      </div>
    </div>
    <div>
      <nhwx></nhwx>
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
            <button class="btn"
                    @click="btn">收下</button>
          </div>
        </div>
      </div>
    </md-landscape>
    <md-landscape v-model="yhqshowPic"
                  class="yhq">
      <div class="y-box">
        <div class="imgbg">
          <div class="s-title">
            <div class="s-t1"><span><i class="iconfont iconzuobianhenggang" /></span></div>
            <div class="s-t2">
              <p> 恭喜！获得优惠券礼包 </p>
            </div>
            <div class="s-t3"><span><i class="iconfont iconzuobianhenggang" /></span></div>
          </div>
          <div class="middle">
            <ul>
              <li v-for="(item,index) in yhqlist"
                  :key="index">
                <div class="yhqlist">
                  <img src="@/assets/nh/yhqmoney.png"
                       alt=""
                       v-if="item.type == '1'">
                  <img src="@/assets/nh/fwltk.png"
                       alt=""
                       v-if="item.type == '2'">
                  <p class="one"
                     v-if="item.type == '1'">￥<span>{{item.amount}}</span></p>
                  <p class="two"
                     v-if="item.type == '1'">现金优惠券</p>
                  <p class="three">{{item.couponname}}</p>
                </div>
              </li>
            </ul>
          </div>
          <div class="bottom">
            <img src="@/assets/nh/fg.png"
                 alt="">
          </div>
          <div class="bottomtop"
               @click="$router.push({path:'/yhqshow'})">
            <img src="@/assets/nh/yhqgo.png"
                 alt="">
          </div>
          <img src="@/assets/nh/yhqclose.png"
               alt=""
               class="closeimg"
               @click="yhqshowPic = false">
        </div>

      </div>
    </md-landscape>
  </div>
</template>

<script>
import { getNHlb, getJf, getJfSum, wdmsg, grantCoupon } from '@/api/basic/index'
import { getStorage } from '@/lib/util'
import { mapGetters } from 'vuex'
import { DoRecord } from '@/api/abt/customerOperation/common/index'
import initWebSocket from '@/mixins/websock'
import nhwx from '@/views/basic/home/component/nhwx'
import { wechatshare } from '@/lib/wechat_share'
export default {
  mixins: [initWebSocket],
  components: { nhwx },
  data () {
    return {
      chanpin: [],
      searchValue: '',
      isclear: false,
      ossurl: '',
      getMenulist: [],
      getbanner: [],
      getbar: [],
      menulist: [],
      tjlist: [],
      jfdata: {},
      showPic: false,
      yhqshowPic: false,
      jf: 0,
      JFNUM: 0,
      getjfnum: {},
      wdmsgdata: { num: 0 },
      user: '',
      websock: null,
      empno: '',
      rytype: '',
      suserid: '',
      flag: '',
      yhqlist: []
    }
  },
  computed () {
    mapGetters(['getossurl'])
  },
  created () {
    debugger;
    wechatshare('', '', '', '', '', '', true)
    this.ossurl = getStorage('ossurl', '')
    this.flag = getStorage('flag', '')
    this.getMenulist = getStorage('m_l', '')
    this.getbanner = getStorage('banner', '')
    this.getbar = getStorage('bar', [])
    this.user = getStorage('u_s', {})
    this.empno = this.$route.query.empno ? this.$route.query.empno : this.user.empno
    this.rytype = this.$route.query.rytype ? this.$route.query.rytype : this.user.rytype
    this.suserid = this.$route.query.suserid ? this.$route.query.suserid : this.user.userid
    console.log("勾选数据");
    console.log(this.getbar);
    // this.getbar.map(item => {
    //   if (item.bancode == 'CS001') {
    //     item.mklj = '/toInvitenew'
    //   } else if (item.bancode == 'CS002') {
    //     item.mklj = '/dzincome'
    //   } else if (item.bancode == 'CS003') {
    //     item.mklj = '/tuoke'
    //   } else if (item.bancode == 'CS004') {
    //     item.mklj = '/microshop'
    //   } else if (item.bancode == 'CS005') {
    //     item.mklj = '/orderform'
    //   } else if (item.bancode == 'CS006') {
    //     item.mklj = '/clientsort'
    //   } else if (item.bancode == 'CS009') {
    //     item.mklj = '/morermhd'
    //   } else if (item.bancode == 'CS007') {
    //     item.mklj = '/policycheck'
    //   }
    // })
     this.getList()
    this.getqdjf()
    this.wdmsg()
    // this.getyhq()
  },
  methods: {
    wdmsg () {
      wdmsg().then(res => {
        console.log("数据1")
        console.log(res);
        this.wdmsgdata = res.data.data
      })
    },
    clearinput () {
      this.searchValue = ''
      this.isclear = false
    },
    getFocus () {
      this.isclear = true
      // this.$router.push({ path: '/product', query: { searchflag: true } })
    },
    yourmethod () {
      this.$router.push({ path: '/product', query: { searchflag: true, searchdata: this.searchValue } })
      //   this.$router.push(
      //     `/productDisplayNhwx?searchdata=${this.searchValue}`)
    },
    clickmore (val) {
      this.$router.push(
        `/productDisplayNhwx?sclasscode=${val.code}&typename=${val.name}`
      )
    },
    clickmoretj () {
      this.$router.push(
        `/productDisplayNhwx?`
      )
    },
    cpjumptj (aitem) {
      if (getStorage('u_s', '').rytype == 'M') {
        this.$router.push('/diaoauth')
      } else {
        if (aitem.tbflag == '1') {
          this.$router.push(
            `/productShowNhwx?jumppath=${aitem.showhttp}&iconhttp=${aitem.iconhttp}&prodname=${aitem.prodname}&pdesc=${aitem.pdesc}&prodcode=${aitem.prodcode}&rytype=${this.rytype}&suserid=${this.suserid}`
          )
        } else {
          if (this.user.rytype == 'M') {
            window.location.href = aitem.comtburl
          } else {
            this.behaviorRecord(aitem)
          }
        }
      }
    },
    cpjump (itema) {
      if (getStorage('u_s', '').rytype == 'M') {
        this.$router.push('/diaoauth')
      } else {
        if (itema.tbflag == '1') {
          this.$router.push(
            `/productShowNhwx?jumppath=${itema.showhttp}&iconhttp=${itema.iconhttp}&prodname=${itema.prodname}&pdesc=${itema.pdesc}&prodcode=${itema.prodcode}&rytype=${this.rytype}&suserid=${this.suserid}`
          )
        } else {
          if (this.user.rytype == 'M') {
            window.location.href = itema.comtburl
          } else {
            this.behaviorRecord(itema)
          }
        }
      }
    },
    // 行为记录
    behaviorRecord (val) {
      let data = {
        suserid: this.suserid,
        empno: this.empno,
        sno: val.prodcode,
        otype: '1',
        btagcode: '7',
        rytype: this.rytype
      }
      DoRecord(data).then(res => {
        let id = res.data.data.id
        let comid = this.user.comid
        this.initWebSocket(id, comid) // 开启websocket连接
        window.location.href = val.comtburl
        // alert(id)
      })
    },
    getList () {
      // console.log(12)
      getNHlb().then(res => {
        console.log("参数");
        console.log(res);
        res.data.data.forEach((item, index) => {
          if (item.code == 'TJ') {
            res.data.data.splice(index, 1)
            this.tjlist = item.list
          }
        })
        this.menulist = res.data.data
      })
    },
    // 轮播图点击事件
    banclick () {
      let ele = this.getbanner[this.$refs.swiper.getIndex()]
      if (ele.urltype === 'O' || ele.urltype === 'P') {
        window.location.href = ele.urlhttp
      } else {
        this.$router.push({ path: '/rmhddetail', query: { type: 'banner', bannerhttp: ele.urlhttp } })
      }
    },
    async getqdjf () {
      if (this.user.rytype == 'W') {
        await grantCoupon().then((res) => {
          if (res.data.data.length > 0) {
            this.yhqlist = res.data.data
            this.yhqshowPic = true
          }
        })
      }
      getJf().then(res => {
        this.jfdata = res.data.data
        if (res.data.data == null) {
          this.jf = 0
        } else {
          this.jf = this.jfdata.JF
          // 判断今天第一次登录
          if (this.jfdata.state == 1 && getStorage('u_s', '').rytype != 'M' && getStorage('u_s', '').rytype != 'Y' && !this.yhqshowPic) {
            this.showPic = true
          }
        }
        this.getJfSum()
      })
    },
    getJfSum () {
      getJfSum().then(res => {
        this.getjfnum = res.data.data
        if (res.data.data == null) {
          this.JFNUM = 0
        } else {
          this.JFNUM = this.getjfnum
        }
      })
    },
    btn () {
      this.showPic = false
    }
  }
}
</script>

<style lang="stylus" scoped>
.container {
  background: #fffeff;
}

.searchbox {
  display: flex;
  height: 1rem;
  padding: 0 0.7rem;
  margin-left: 0.5rem;
  width: 90%;
  align-items: center;

  .searchline {
    border-radius: 0.5rem;
    background: #eee;
    height: 1rem;
    width: 85%;
    margin-left: auto;
    margin-top: 0.6rem;
  }

  .btnicon {
    width: 16%;
    height: 1rem;
    border-radius: 0.3rem 0 0 0.3rem;
    background: none;
  }

  input {
    width: 70%;
    height: 1rem;
    border-radius: 0 0.5rem 0.5rem 0;
    border: none;
    line-height: 1;
    background: none;
    font-size: 0.38rem;
  }

  .btnclear {
    background: none;
  }
}

.banner {
  width: 95%;
  margin: 0 auto;
  margin-top: 0.6rem;
  height: 4rem;
}

.content {
  background: #fffeff;

  .menu_box {
    padding: 40px 0;
    // margin-bottom: 30px;
    background-color: bg-color;
  }

  .menu {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    margin-bottom: 20px;

    .item {
      width: 20%;
      text-align: center;
      margin-top: 40px;

      .icon {
        width: 150px;
        height: 150px;
        margin: 0 auto 5px auto;
      }

      .p_text {
        font-size: 0.37rem;
        font-family: PingFang SC;
        font-weight: 500;
        color: rgba(56, 56, 56, 1);
      }
    }
  }

  /* 修改标题左边标识的样式 */
  .blod {
    height: 40px;
    width: 12px;
    background: rgb(187, 26, 32);
    display: inline-block;
    vertical-align: -6px;
    margin-right: 25px;
  }

  .title_more {
    float: right;
    width: 1.2rem;
    font-size: 0.3rem;
    display: inline;
    text-align: center;
    border: 1px solid #eee;
    border-radius: 30px;
    color: rgb(126, 126, 126);
    margin-right: 25px;
    line-height: 0.5rem;
  }

  .bg_bottom {
    width: 100%;
    height: 5rem;
    background: #fafafa;
    margin-bottom: 1.5rem;

    .bot-con {
      margin-top: 10px;
      background: #FCF9FC;

      .bot-title {
        color: #B7B4B8;
        letter-spacing: 0.05rem;
        font-family: PingFang SC;
        padding: 0.5rem 0;
        text-align: center;

        span {
          display: inline-block;

          &:nth-child(2n+1) {
            width: 23%;
            border-top: 0.02rem solid #e7e4e7;
            position: relative;
            top: -0.07rem;
          }

          &:nth-child(2n) {
            margin: 0 0.3rem;
          }
        }
      }

      .bot-content ul {
        display: flex;
        flex-direction: row;
        flex-wrap: nowrap;
        justify-content: space-around;
        margin-bottom: 0.7rem;

        li {
          div {
            width: 1.3rem;
            height: 1.3rem;
          }

          p {
            font-size: 0.35rem;
            font-family: PingFang SC;
            font-weight: 500;
            color: rgba(152, 152, 152, 1);
            margin-top: 5px;
          }
        }
      }

      .p_con {
        text-align: center;
        color: #C6C4C7;
        font-size: 0.36rem;
      }
    }
  }

  .con_box {
    display: flex;
    flex-wrap: wrap;
    margin: 0 0.25rem;

    #tj_menu {
      width: 49%;

      &:nth-child(2n+1) {
        margin-right: 0.15rem;
      }
    }

    .con_menu {
      width: 50%;
      margin-top: 30px;

      .tjcontent {
        background: #fafafa;
      }

      .samestyle {
        border: 1px solid rgba(200, 200, 200, 0.15);
      }

      .con_menu-item {
        width: 100%;
        height: 3rem;
        position: relative;
        border-radius: 5px;

        .con_menu-title {
          display: inline-block;
          width: 460px;
          height: 56px;
          font-size: 0.37rem;
          font-family: PingFang SC;
          font-weight: bold;
          color: rgba(56, 56, 56, 1);
          margin-top: 30px;
          margin-left: 5%;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }

        p {
          font-size: 0.3rem;
          color: #989898;
          margin-left: 5%;
        }

        .con_menu-one {
          margin-bottom: 20px;
          text-overflow: -o-ellipsis-lastline;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
          font-family: PingFang SC;
          font-weight: 500;
          color: rgba(152, 152, 152, 1);
          height: 0.8rem;
        }

        .input-red {
          width: 90%;
          font-size: 0.3rem;
          height: 0.6rem;
          line-height: 0.6rem;
          border: 1px solid rgb(247, 188, 189);
          color: rgb(210, 71, 71);
          border-radius: 30px;
          text-align: center;
          margin-top: 0.2rem;
          margin-left: 5%;
        }
      }

      .con-item {
        width: 49%;
        height: 2.2rem;
        margin-top: 15px;
        margin-right: 1%;
        margin-left: 1%;
        position: relative;
        background-color: #eee;
        border-radius: 5px;

        .con_menu-title {
          margin-top: 30px;
          margin-left: 5%;
        }

        p {
          font-size: 0.3rem;
          color: rgb(169, 169, 169);
          margin-left: 5%;
        }

        .con_menu-one {
          margin-top: 20px;
        }
      }
    }
  }
}

.fenlei {
  font-size: 0.4rem;
  font-family: PingFang SC;
  font-weight: bold;
  color: rgba(56, 56, 56, 1);
}

.bujucontent {
  width: 100%;
  display: flex;

  .left {
    width: 65%;
  }

  .right {
    width: 30%;

    /deep/img {
      margin-left: 0.1rem;
    }
  }
}

.md-example-item-s {
  position: absolute;
  top: 0.55rem;
  left: 0.5rem;

  /deep/.md-icon {
    font-size: 0.55rem;
  }
}

.md-box {
  position: absolute;
  top: 0.48rem;
  right: 0.2rem;

  .iconfont {
    font-size: 0.55rem;
  }

  .red {
    width: 0.35rem;
    height: 0.35rem;
    line-height: 0.35rem;
    text-align: center;
    color: #fff;
    font-size: 0.3rem;
    border-radius: 50%;
    display: inline-block;
    background: color-primary;
    position: relative;
    bottom: 0.45rem;
    right: 0.2rem;
  }
}

.con_menu-oneaa {
  margin-bottom: 0.2rem;
  text-overflow: -o-ellipsis-lastline;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}

.left {
  width: 65%;
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

    .btn {
      font-size: 28px;
      background-color: color-primary;
      color: #ffffff;
      font-size: 0.35rem;
      width: 50%;
      height: 60px;
      border-bottom-left-radius: 8px;
      border-bottom-right-radius: 8px;
      border-top-left-radius: 8px;
      border-top-right-radius: 8px;
    }
  }
}

.searchTxt {
  color: rgb(117, 117, 117);
  margin-top: 0.6rem;
  margin-right: 0.1rem;
  margin-left: 0.1rem;
}

.yhq {
  /deep/ .md-landscape-content {
    width: 80vw;
    height: 60vh;
  }

  /deep/ .md-landscape-close {
    display: none;
  }

  .y-box {
    height: 100%;

    .s-title {
      display: flex;
      justify-content: space-around;
      line-height: 70px;
      position: absolute;
      width: 100%;
      top: 10vh;

      .s-t1 {
        width: 20%;
        text-align: right;
        color: #FEFBF3;
      }

      .s-t2 {
        width: 60%;
        color: #FEFBF3;
        font-size: 0.4rem;
        text-align: center;
      }

      .s-t3 {
        width: 20%;
        text-align: left;
        color: #FEFBF3;
      }

      p {
        font-size: 0.4rem;
      }
    }

    .bottom {
      position: absolute;
      bottom: 10vw;
    }

    .bottomtop {
      position: absolute;
      bottom: 14vw;
      text-align: center;

      img {
        width: 73%;
      }
    }

    .imgbg {
      height: 100%;
      position: relative;
      background-image: url('~@/assets/nh/quan.png');
      background-size: 100% auto;
      background-repeat: no-repeat;
    }

    .closeimg {
      width: 8vw;
      height: 8vw;
      position: absolute;
      bottom: 0px;
      left: 37vw;
    }

    .middle {
      height: 30vh;
      overflow-y: auto;
      position: absolute;
      width: 100%;
      top: 18vh;
      padding-bottom: 7vh;

      .yhqlist {
        overflow: hidden;
        margin: 0 3%;
        position: relative;

        img {
          height: 14vh;
        }

        .one {
          position: absolute;
          top: 18%;
          color: #EC2D38;
          left: 6vw;
          font-size: 0.45rem;

          span {
            font-size: 0.7rem;
          }
        }

        .two {
          position: absolute;
          top: 55%;
          font-size: 0.3rem;
          color: #B08039;
          left: 5vw;
          border: 1px solid #B08039;
          border-radius: 0.2rem;
          padding: 0 0.1rem;
        }

        .three {
          position: absolute;
          top: 18%;
          left: 33vw;
          color: #A36229;
          font-weight: 500;
        }

        .right {
          float: right;
          width: 65%;
        }
      }
    }
  }
}
</style>
