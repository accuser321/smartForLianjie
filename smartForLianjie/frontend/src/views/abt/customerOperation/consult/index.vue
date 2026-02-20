<template>
  <div class="content">
    <div class="center md-example-child md-example-child-scroll-view md-example-child-scroll-view-2">
      <md-scroll-view ref="scrollView"
                      :scrolling-x="false"
                      :auto-reflow="true"
                      @refreshing="$_onRefresh">
        <md-scroll-view-refresh slot="refresh"
                                slot-scope="{ scrollTop, isRefreshActive, isRefreshing }"
                                :scroll-top="scrollTop"
                                :is-refreshing="isRefreshing"
                                :is-refresh-active="isRefreshActive"></md-scroll-view-refresh>
        <div id="top"></div>
        <ul style="padding:0.5rem;margin-bottom: 40px;"
            id="aul">
          <li class="listy"
              v-if="isself&&allLoaded ">
            <div class="txright">
              <img class="touxiang"
                   :src="empuserinfo.headimg"
                   alt=""
                   v-if="empuserinfo.headimg" />
            </div>

            <div class="andiv"
                 style="width: 70%;background:white;color:#3d4145;">
              <p class="bigwords">
                你好，我是{{
                empuserinfo.cardempname
              }}，欢迎进入我的名片，有什么可以帮到你的吗？你可以在这里跟我实时沟通。
              </p>
              <p style="font-size: .35rem; margin: 13px 0;">
                通过我的名片你还可以：
              </p>
              <div class="listdiv"
                   @click="entermp">
                <img class="imgsty"
                     src="./images/card.png"
                     alt="" /><span class="sty">进入我的名片</span>
              </div>
              <!-- <div class="listdiv" @click="entercp">
              <img class="imgsty" src="./images/div.png" alt="" /><span
                class="sty"
                >查看我们公司商品</span
              >
            </div> -->
              <a :href="'tel:' + empuserinfo.cardmobile">
                <div class="listdiv"
                     @click="call">
                  <span class="smallwords">拨打我的电话</span><img class="imgsty imgpos"
                       src="./images/phone.png"
                       alt="" />
                </div>
              </a>
              <div class="listdiv wxtab"
                   @click="onCopywx(empuserinfo.wxnumber)">
                <span class="smallwords">添加我的微信</span><img class="imgsty imgpos"
                     src="./images/wechat.png"
                     alt="" />
              </div>

              <span class="squre"
                    style="background:white;"></span>
            </div>
          </li>
          <li class="listy"
              v-if="!isself&&allLoaded">
            <div class="txleft">
              <img class="touxiang"
                   :src="empuserinfo.headimg"
                   alt=""
                   v-if="empuserinfo.headimg" />
            </div>

            <div class="ctdiv"
                 style="width: 70%;">
              <p class="bigwords">
                你好，我是{{
                empuserinfo.cardempname
              }}，欢迎进入我的名片123，有什么可以帮到你的吗？你可以在这里跟我实时沟通。
              </p>
              <p style="font-size: .35rem; margin: 13px 0;">
                通过我的名片你还可以：
              </p>
              <div class="listdiv"
                   @click="entermp">
                <img class="imgsty"
                     src="./images/card.png"
                     alt="" /><span class="sty">进入我的名片</span>
              </div>
              <!-- <div class="listdiv" @click="entercp">
              <img class="imgsty" src="./images/div.png" alt="" /><span
                class="sty"
                >查看我们公司商品</span
              >
            </div> -->
              <a :href="'tel:' + empuserinfo.cardmobile">
                <div class="listdiv"
                     @click="call">
                  <span class="smallwords">拨打我的电话</span><img class="imgsty imgpos"
                       src="./images/phone.png"
                       alt="" />
                </div>
              </a>
              <div class="listdiv wxtab"
                   @click="onCopywx(empuserinfo.wxnumber)">
                <span class="smallwords">添加我的微信</span><img class="imgsty imgpos"
                     src="./images/wechat.png"
                     alt="" />
              </div>

              <span class="whitesqure"></span>
            </div>
          </li>
          <li class="listy"
              v-for="(item, index) in msgdata"
              :key="index">
            <div class="txleft"
                 v-if="item.status == 'receive'">
              <img class="touxiang"
                   :src="askuserinfo.headimg"
                   alt="" />
            </div>
            <div class="ctdiv"
                 v-if="item.status == 'receive'">
              <p v-if="item.info == 0">{{ item.data }}</p>
              <div class="tstitle"
                   v-if="item.info == 1">
                {{ item.data.type == "9" ? "名片" : "文章" }}
              </div>
              <div class="tscontent"
                   v-if="item.info == 1">
                <div class="tsimg"><img :src="ossurl + item.data.libpic" /></div>
                <div class="tsjj">{{ item.data.libtitle }}</div>
              </div>
              <span class="whitesqure"></span>
            </div>
            <div class="txright"
                 v-if="item.status == 'send'">
              <img class="touxiang"
                   :src="ansuserinfo.headimg"
                   alt="" />
            </div>
            <div class="xunwen"
                 v-if="item.status == 'send'">
              <div class="andiv">
                <p v-if="item.info == 0">{{ item.data }}</p>
                <div class="tstitle"
                     v-if="item.info == 1">
                  {{ item.data.type == "9" ? "名片" : "文章" }}
                </div>
                <div class="tscontent"
                     v-if="item.info == 1">
                  <div class="tsimg">
                    <img :src="ossurl + item.data.libpic" />
                  </div>
                  <div class="tsjj">{{ item.data.libtitle }}</div>
                </div>
                <span class="squre"></span>
              </div>
            </div>
          </li>
          <li id="test"></li>
        </ul>

        <!-- </mt-loadmore> -->
      </md-scroll-view>
      <!-- <div id="test"></div> -->
    </div>
    <div class="footdiv">
      <div class="foottop">
        <span>我想</span>
        <span class="sdiv wxtab"
              @click="onCopywx(empuserinfo.wxnumber)">加微信</span>
        <a :href="'tel:' + empuserinfo.cardmobile">
          <span class="sdiv"
                @click="call">拨打电话</span>
        </a>
      </div>
      <div class="footbot">
        <input type="text"
               class="inputsty"
               v-model="sendcontent" />
        <div class="butsty"
             @click="sendmsg">发送</div>
      </div>
    </div>
  </div>
</template>

<script>
import MD5 from 'js-md5'
import { getStorage } from '@/lib/util'
import { datemate, toDate } from '@/lib/function'
import { getykinfo } from '@/api/basic/index'
import { selectOneBySno } from '@/api/abt/customerOperation/common/index'
import {
  insertKjChat,
  getLS,
  updateMSG
} from '@/api/abt/customerOperation/zxzx/index'
// import { selectUserCard } from '@/api/abt/customerOperation/visitingcard/index'
import Clipboard from 'clipboard'
import { Toast } from 'mand-mobile'
import { wechatshare } from '@/lib/wechat_share'
import { DoRecord } from '@/api/abt/customerOperation/common'
export default {
  components: {},
  data () {
    return {
      appid: '8a216da8679d0e9d0167bf78448b1262',
      apptoken: 'eb558d0fe0f8fe1427b5af344c226d04',
      userid: '',
      empno: '',
      emprytype: '',
      askrytype: '',
      askuserid: '',
      sendcontent: '',
      msgdata: [],
      userinfo: {},
      user: {},
      ansuserinfo: {},
      askuserinfo: {},
      empuserinfo: {},
      limit: 10,
      path: '',
      isshare: '',
      zfflag: '',
      lxmes: [],
      topStatus: '',
      topText: '',
      pageNo: 1,
      totalPageCount: '',
      allLoaded: false,
      time: '',
      lxmsg: [],
      msgCon: [],
      pagetype: '',
      libno: '',
      detail: {},
      pagetypeflag: false,
      sendcontentpage: '',
      vip: '',
      news: '',
      ossurl: '',
      height: '',
      suserid: ''
    }
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      wechatshare('', '', '', '', '', '', true)
      vm.getuserinfo() //获取业务员或游客信息
      vm.clearUnreadmes() //清空未读消息
      if (vm.$route.query.libno) {
        vm.libno = vm.$route.query.libno
      }
      if (vm.$route.query.news) {
        vm.news = vm.$route.query.news
      }
      setTimeout(() => {
        if (vm.$route.query.pagetype) { //页面类型
          Toast.loading('加载中...')
          vm.pagetype = vm.$route.query.pagetype
          vm.record() //行为记录
          if (vm.$route.query.pagetype == 10) {
            vm.pagetypeflag = true
            vm.iminit('send')
          } else if (vm.$route.query.pagetype == 9) {
            vm.iminit('')
            vm.gethistory()
          } else {
            vm.gethistory()//实时查询历史记录
          }
        } else {
          Toast.loading('加载中...')
          vm.gethistory()
          vm.iminit('')  //初始化数据
        }
      }, 500)
    })
  },
  mounted () {
    $(document).on('focusout', function () {
      setTimeout(() => {
        // 软键盘收起的事件处理
        window.scroll(0, 0)
      }, 300)
    })
    document.querySelector('.center').style.height = `${this.height - 100}px`
    setTimeout(() => {
      window.ScrollViewTrigger1 = () => {
        this.$refs.scrollView.triggerRefresh()
      }
    }, 500)
  },
  created () {
    this.height = document.body.clientHeight
    this.ossurl = getStorage('ossurl', '');
    // console.log('数据');
    // console.log(this.ossurl);
    this.user = getStorage('u_s', {})
    if (this.$route.query.askuserid) {
      this.askuserid = this.$route.query.askuserid
    }
    if (this.$route.query.empno) {
      this.empno = this.$route.query.empno
    }
    if (this.$route.query.suserid) {
      this.suserid = this.$route.query.suserid
    }
    if (this.$route.query.emprytype) {
      this.emprytype = this.$route.query.emprytype
    }
    if (this.$route.query.orytype) {
      this.emprytype = this.$route.query.orytype
    }
    if (this.empno == this.user.empno) {
      this.isself = true
    } else {
      this.isself = false
    }
  },
  methods: {
    clearUnreadmes () { //一进页面清空未读消息
      let data = { userid: this.askuserid, empno: this.empno, type: this.isself ? '2' : '1' }
      updateMSG(data).then((res) => { })
    },
    record () { //用户行为记录
      let data = {
        suserid: this.suserid, //上级来源人用户编号
        btagcode: this.pagetype,//素材类型编码
        userid: this.user.userid, //查看人用户编号
        otype: '8', //来源分类 1 阅读 2 转发分享 3 制作 4 点赞 6 保存  8咨询
        rytype: this.emprytype, //人员类型标志:N内勤，W外勤，Y游客(必须)
        empno: this.empno  //最初来源业务员工号
      }
      DoRecord(data).then(res => { })
    },
    handleTopChange (status) {
      // this.topStatus = status
    },
    $_onRefresh () { //下拉刷新
      if (!this.allLoaded) {
        console.log('没加载完')
        this.pageNo += 1
        setTimeout(() => {
          this.gethistory()
        }, 1200)
      } else {
        console.log('加载完')
        this.$refs.scrollView.finishRefresh()
      }
    },
    onc () {
      // document.getElementById('test').scrollIntoView(false)
      this.$refs.scrollView.scrollTo(0, parseInt(`${this.msgdata.length * 70}`), true)
    },
    toTop () {
      document.getElementById('top').scrollIntoView(false)
    },
    entermp () { //进入名片
      // this.$router.push(
      //   `/visitingcard?empno=${this.empno}&rytype=${this.emprytype}&suserid=${this.empuserinfo.userid}&type=9`
      // )
    },
    entercp () {
      alert('暂无产品')
      // this.$router.push('/productCard?ouserid=' + this.empuserid + '&suserid=' + this.empuserid + '&isshare=1' + '&zfflag=0')
    },
    call () { //拨打我的电话需要名片中有编辑过自己的电话，或者拨打电话失败
      let data = {
        suserid: this.suserid, //上级来源人用户编号
        empno: this.empno,    //最初来源业务员工号(必须)
        otype: '7',  
        btagcode: '9',  //素材类型--名片
        rytype: this.emprytype //人员类型标志:N内勤，W外勤，Y游客(必须)
      }
      DoRecord(data).then(res => { })
    },
    //   // 复制成功
    onCopywx (mes) {  //微信复制添加
      if (!mes) {
        Toast.info('暂无数据')
      } else {
        var clipboard = new Clipboard('.wxtab', {
          text: function () {
            return mes
          }
        })
        clipboard.on('success', e => {
          Toast.succeed('复制成功')
          let data = {
            suserid: this.suserid,
            empno: this.empno,
            otype: '6',
            btagcode: '9',
            rytype: this.emprytype
          }
          DoRecord(data).then(res => { })
        })
      }
    },
    // 获取咨询者信息
    getuserinfo () {
      if (this.isself) { //根据类型判断
        selectUserCard({ //获取业务员名片
          empno: this.empno, //用户工号
          flag: '0',   //是否需要查询风采照片  0 不查询 1查询
          type: this.emprytype  //用户身份标识 w外勤 N内勤
        }).then(res => {
          this.empuserinfo = res.data.data
          this.ansuserinfo = res.data.data
        })
        getykinfo({ userid: this.askuserid }).then(res => { //获取游客用户信息
          this.askuserinfo = res.data.data.user
        })
      } else {
        selectUserCard({
          empno: this.empno,
          flag: '0',
          type: this.emprytype
        }).then(res => {
          this.empuserinfo = res.data.data
          this.askuserinfo = res.data.data
        })
        this.ansuserinfo.headimg = this.user.headimg
      }
    },
    // 初始化
    iminit (value) {
      //  console.log(value)
      //  console.log('初始化')
      //  console.log( RL_YTX);
      var resp = RL_YTX.init(this.appid)  //初始化用户的appid
      var _this = this
      if (resp.code == 170002) {
        // 缺少必要参数，详情见msg参数
        console.log('缺少必要参数，详情见msg参数')
      } else if (resp.code == 174001) {
        // 不支持HTML5，关闭页面//用户逻辑处理}
        console.log('不支持HTML5，关闭页面//用户逻辑处理')
      } else if (resp.code == 200) {
        // 初始化成功
        console.log('初始化成功')
        var unsupport = resp.unsupport
        // 账号登录参数设置
        var loginBuilder = new RL_YTX.LoginBuilder()
        // 登录类型 1账号登录，3voip账号密码登录
        loginBuilder.setType(1)
        // 设置用户名
        if (this.news == 1) {
          loginBuilder.setUserName(this.user.userid + '')
        } else {
          loginBuilder.setUserName(this.user.empno)
        }

        // type值为1时，密码可以不赋值
        loginBuilder.setPwd()
        // 设置sig
        let Timestamp = datemate('yyyyMMddhhmmss')
        let sig = ''

        // sig = MD5(
        //   this.appid + this.user.userid + '' + Timestamp + this.apptoken
        // )
        if (this.news == 1) {
          sig = MD5(
            this.appid + this.user.userid + '' + Timestamp + this.apptoken
          )
        } else {
          sig = MD5(
            this.appid + this.user.empno + Timestamp + this.apptoken
          )
        }
        loginBuilder.setSig(sig)
        // 设置时间戳
        loginBuilder.setTimestamp(Timestamp)
        // 执行用户登录
        RL_YTX.login(
          loginBuilder,
          function (obj) {
            console.log('登录成功')
            if (value == 'send') {
              _this.sendmsg()   //发送消息
            }

            // 登录成功回调
            RL_YTX.onMsgReceiveListener(function (obj) {
              // 收到push消息或者离线消息或判断输入状态//如果obj.msgType==12  判断obj.msgDomainn的值//obj.msgDomain 0 无输入状态  1 正在输入  2 正在录音
              console.log('登陆成功的回调')
              _this.msgReceive(obj)  //监听收到的信息
            })
          },
          function (obj) {
            // 登录失败方法回调
            console.log('登录失败方法回调')
          }
        )
      }
    },
    getState () {  //获取用户在线状态
      var _this = this
      var getUserStateBuilder = new RL_YTX.GetUserStateBuilder()
      getUserStateBuilder.setNewUserstate(true)
      // 使用新SDK的用户状态
      getUserStateBuilder.setUseracc(this.askuserid)
      RL_YTX.getUserState(
        getUserStateBuilder,
        function (obj) {
          // 获取成功
          // obj[i].useracc 对方账号
          // obj[i].state 对方在线状态1:在线2:离线当用户为离线状态时，obj.state,obj.network和obj.device为undefined
          // obj[i].network对方网络状态 1:WIFI 2:4G 3:3G 4:2G(EDGE) 5: INTERNET  6: other
          // obj[i].device对方登录终端1:Android 2:iPhone10:iPad11:Android Pad20:PC 21:H5
          alert(
            '错误码：' + obj[0].state + '; 错误描述：获得用户状态结果不合法'
          )
        },
        function (obj) {
          if (obj.code != 174006) {
            alert('错误码：' + obj.code + '; 错误描述：' + obj.msg)
          }
        }
      )
    },
    // 发送消息
    sendmsg () {
      if (this.pagetypeflag && this.pageNo == 1) {
        this.sendcontentpage =
          '&#&' + this.pagetype + '&#&' + this.libno + '&#&'
      }
      var _this = this
      // 新建消息体对象
      var obj = new RL_YTX.MsgBuilder()
      // 设置自定义消息id
      console.log('发送消息');
      console.log('saas');
      let Timestamp = datemate('yyyyMMddhhmmss')
      obj.setId(Timestamp + 'saas')
      // 设置发送的文本内容
      let sendcontent = _this.pagetypeflag
        ? _this.sendcontentpage
        : _this.sendcontent
      obj.setText(sendcontent)
      // 设置发送的消息类型1:文本消息 4:图片消息 6:压缩文件 7:非压缩文件
      // 发送非文本消息时，text字段将被忽略，发送文本消息时 file字段将被忽略
      obj.setType(1)
      // 设置接收者
      // obj.setReceiver(this.askuserid)
      if (this.news == 1) {
        obj.setReceiver(this.empno)
      } else {
        obj.setReceiver(this.askuserid)
      }
      // console.log(this.emprytype)
      // console.log(toDate(new Date()))
      // 判断用户是否在线(新建用户状态对象)
      var getUserStateBuilder = new RL_YTX.GetUserStateBuilder()
      getUserStateBuilder.setNewUserstate(true)
      // 使用新SDK的用户状态
      getUserStateBuilder.setUseracc(_this.askuserid)
      RL_YTX.getUserState(getUserStateBuilder, function (ret) {
        // 获取成功
        // obj[i].useracc 对方账号
        // obj[i].state 对方在线状态1:在线2:离线当用户为离线状态时，obj.state,obj.network和obj.device为undefined
        // obj[i].network对方网络状态 1:WIFI 2:4G 3:3G 4:2G(EDGE) 5: INTERNET  6: other
        // obj[i].device对方登录终端1:Android 2:iPhone10:iPad11:Android Pad20:PC 21:H5
        if (ret[0].state == 1) {
          // 在线 消息发送
          RL_YTX.sendMsg(
            obj,
            function (obj1) {
              console.log(obj1)
              // 06BA7F52E8284E3E41885EE341BB6C7A|3
              let data1 = {}
              if (_this.isself) { //类型
                data1 = {
                  empno: _this.empno,        //业务员工号
                  khuserid: _this.askuserid,  //客户ID
                  content: _this.pagetypeflag  //消息内容
                    ? _this.sendcontentpage
                    : _this.sendcontent,
                  type: '1',             //类型(1业务员 2客户)
                  rytype: _this.emprytype,  //业务员类型
                  // info: _this.pagetypeflag ? '1' : '0',
                  // btagcode: _this.pagetype ? _this.pagetype : '',
                  read: '1',            //状态  已读0 未读1
                  date: toDate(new Date()) //时间
                }
              } else {
                data1 = {
                  empno: _this.empno,
                  khuserid: _this.user.userid + '',
                  content: _this.pagetypeflag
                    ? _this.sendcontentpage
                    : _this.sendcontent,
                  type: '2',
                  rytype: _this.emprytype,
                  // info: _this.pagetypeflag ? '1' : '0',
                  // btagcode: _this.pagetype ? _this.pagetype : '',
                  read: '1',
                  date: toDate(new Date())
                }
              }
              // console.log(data1)
              insertKjChat(data1).then(res => {  //添加咨询数据 
                let data4 = {}
                if (_this.pagetypeflag) {
                  _this.pagetypeflag = false
                  _this.gethistory()
                } else {
                  data4.info = '0'
                  data4.data = _this.sendcontent
                  data4.status = 'send'
                  _this.msgdata.push(data4)
                  _this.sendcontent = ''
                  let timec1 = setTimeout(function () {
                    _this.onc()
                  }, 200)
                  // _this.$refs.scrollView.scrollTo(0, `${_this.height - 100}`, true)
                }
              })
            },
            function (obj) {
              // 失败
              // 发送消息失败
              console.log('发送消息失败')
            }
          )
        } else {
          // 离线
          RL_YTX.sendMsg(
            obj,
            function (obj1) {
              // console.log(obj1)
              // console.log('发送成功2')
              let data2 = {}
              if (_this.isself) {
                data2 = {
                  empno: _this.empno,
                  khuserid: _this.askuserid,
                  content: _this.pagetypeflag
                    ? _this.sendcontentpage
                    : _this.sendcontent,
                  type: '1',
                  rytype: _this.emprytype,
                  // info: _this.pagetypeflag ? '1' : '0',
                  // btagcode: _this.pagetype ? _this.pagetype : '',
                  read: '0',
                  date: toDate(new Date())
                }
              } else {
                data2 = {
                  empno: _this.empno,
                  khuserid: _this.user.userid + '',
                  content: _this.pagetypeflag
                    ? _this.sendcontentpage
                    : _this.sendcontent,
                  type: '2',
                  rytype: _this.emprytype,
                  // info: _this.pagetypeflag ? '1' : '0',
                  // btagcode: _this.pagetype ? _this.pagetype : '',
                  read: '0',
                  date: toDate(new Date())
                }
              }
              // console.log(data2)
              insertKjChat(data2).then(res => {
                let data = {}
                if (_this.pagetypeflag) {
                  _this.pagetypeflag = false
                  _this.gethistory()
                } else {
                  data.info = '0'
                  data.data = _this.sendcontent
                  data.status = 'send'
                  _this.msgdata.push(data)
                  _this.sendcontent = ''
                  let timec4 = setTimeout(function () {
                    _this.onc()
                  }, 200)
                  // console.log(_this.msgdata.length)
                  // console.log(parseInt(`${_this.msgdata.length * 60}`))
                  // _this.$refs.scrollView.scrollTo(0, 100, true)
                  // console.log(_this.$refs.scrollView.scrollTo())
                }
              })
            },
            function (obj) {
              if (obj.code != 174006) {
                _this.iminit('')
                // _this.sendmsg();
                // console.log('错误码3：' + obj.code + '; 错误描述：' + obj.msg)
              }
            }
          )
        }
      })
    },
    // getdata ({ scrollLeft, scrollTop }) {
    //   console.log(scrollTop)
    // },
    // 监听收到的消息
    msgReceive (obj) {
      console.log("监听")
      console.log(obj);
      var _this = this
      // 获取发送者为
      var sender = obj.msgSender
      // 获取发送者昵称，如果不存在，使用账号代替
      var you_senderNickName = obj.senderNickName
      var name = obj.msgSender
      if (you_senderNickName) {
        name = you_senderNickName
      }
      var content_type = null
      // 获取消息版本号（是服务器的版本号）
      var version = obj.version
      // 获取消息发送时间
      var time = obj.msgDateCreated
      // 获取消息类型
      // 1:文本消息 2:语音消息4:图片消息6:文件
      var msgType = obj.msgType
      if (msgType == 1 || msgType == 0) {
        // if (obj.msgSender == this.askuserid) {
        if ((this.news == 1 && obj.msgSender == this.empno) || (!this.news && obj.msgSender == this.askuserid)) {
          // 文本消息，获取消息内容
          let data = {}
          let arr = obj.msgContent.split('&#&')
          if (arr[0] == '') {
            let arr = obj.msgContent.split('&#&')
            _this.pagetype = arr['1']
            _this.libno = arr['2']
            // this.detail.libtitle = content['result'][a].wztitle
            // this.detail.libpic = content['result'][a].wzpichttp
            // this.detail.type = arr[1]
            // this.detail.libno = arr[2]
            // data.data = this.detail
            // data.info = '1'
            // data.status = 'receive'
            // data.msgDate = obj.msgDateCreated
            // let tmp = true
            // let msgDate = ''
            // for (var j = 0; j < _this.msgdata.length; j++) {
            //   if (obj.msgDateCreated == _this.msgdata[j]['msgDate']) {
            //     tmp = false
            //   }
            // }
            // if (tmp) {
            //   _this.msgdata.push(data)
            //   _this.lxmsg.push(obj)
            // }

            // let timec3 = setTimeout(function () {
            //   _this.onc()
            // }, 200)
            _this.getDetail().then(res => { //获取素材详情
              data.data = _this.detail
              data.info = '1'
              data.status = 'receive'
              data.msgDate = obj.msgDateCreated
              let tmp = true
              let msgDate = ''
              for (var j = 0; j < _this.msgdata.length; j++) {
                if (obj.msgDateCreated == _this.msgdata[j]['msgDate']) {
                  tmp = false
                }
              }
              if (tmp) {
                _this.msgdata.push(data)
                _this.lxmsg.push(obj)
              }

              let timec3 = setTimeout(function () {
                _this.onc()
              }, 200)
            })
          } else {
            data.data = obj.msgContent
            data.info = '0'
            data.status = 'receive'
            data.msgDate = obj.msgDateCreated
            let tmp = true
            let msgDate = ''
            for (var j = 0; j < _this.msgdata.length; j++) {
              if (obj.msgDateCreated == _this.msgdata[j]['msgDate']) {
                tmp = false
              }
            }
            if (tmp) {
              _this.msgdata.push(data)
              _this.lxmsg.push(obj)
            }
            let timec3 = setTimeout(function () {
              _this.onc()
            }, 200)
          }
        }
      } else if (msgType == 2) {
        // 语音消息，获取语音文件url
        var url = obj.msgFileUrl
      } else if (msgType == 3) {
        // 3：视频消息，获取视频url
        // 语音消息，获取语音文件url
        var url = obj.msgFileUrl
      } else if (msgType == 4) {
        // 图片消息 获取图片url
        var url = obj.msgFileUrl
      } else {
        // 后续还会支持(地理位置，视频，以及自定义消息等)
      }
    },
    gethistory () {  //实时获取咨询历史数据查询
      // Toast.loading('加载中...')
      let data = {}
      data = {
        sender: this.news ? this.user.userid : this.user.empno,  //发送者
        receiver: this.news ? this.appid + '#' + this.empno : this.appid + '#' + this.askuserid, //接收者
        time: this.time,       //时间
        msgDecompression: 0    //信息描述
      }
      let content = []
      getLS(data).then(res => {
        content = res.data.data
        if (content) {
          this.time = res.data.data.time
          for (var i = 0; i < content['result'].length; i++) {
            let time = (content['result'][i]['msgDateCreated'].substring(0, 19)).replace(/-/g, '/')
            let timeflag = content['result'][i]['msgDateCreated'].substring(20, 23)
            content['result'][i]['msgDate'] = Math.round(new Date(time).getTime() / 1000).toString() + timeflag
            for (var j = 0; j < this.lxmsg.length; j++) {
              if (content['result'][i]) {
                if (this.lxmsg[j]['msgDateCreated'] == content['result'][i]['msgDate']) {
                  content['result'][i] = ''
                }
              }
            }
          }

          for (var a = 0; a < content['result'].length; a++) {
            let msgdata = {}
            if (content['result'][a] != '') {
              msgdata['info'] = '0'
              msgdata['msgDate'] = content['result'][a]['msgDate']
              let arr = content['result'][a]['msgContent'].split('&#&')
              if (arr[0] == '') {
                msgdata['info'] = '1'
                msgdata['data'] = {
                  libtitle: content['result'][a]['wztitle'],
                  libpic: content['result'][a].wzpichttp,
                  type: arr[1],
                  libno: arr[2]
                }
              } else {
                msgdata['data'] = content['result'][a]['msgContent']
              }
              // msgdata['info'] = content['result'][a]['info']
              msgdata['msgDateCreated'] = content['result'][a]['msgDateCreated']

              if (this.news == 1) {
                if (content['result'][a]['msgSender'] == this.user.userid) {
                  msgdata['status'] = 'send'
                } else {
                  msgdata['status'] = 'receive'
                }
              } else {
                if (content['result'][a]['msgSender'] == this.user.empno) {
                  msgdata['status'] = 'send'
                } else {
                  msgdata['status'] = 'receive'
                }
              }
              // console.log(msgdata)
              this.msgdata.unshift(msgdata)
              // console.log(this.msgdata)
              // if (arr[0] == '') {
              //   this.pagetype = arr[1]
              //   this.libno = arr[2]
              //   // this.getDetail().then(res => {
              //   //   msgdata['data'] = this.detail
              //   //   msgdata['info'] = '1'
              //   // })
              //   this.detail.libtitle = content['result'][a].wztitle
              //   this.detail.libpic = content['result'][a].wzpichttp
              //   this.detail.type = arr[1]
              //   this.detail.libno = arr[2]
              //   msgdata['data'] = this.detail
              //   msgdata['info'] = '1'
              //   console.log(msgdata)
              //   this.msgdata.unshift(msgdata)
              //   console.log(this.msgdata)
              // }
            }
          }
          this.msgdata.sort(function (a, b) { return a.msgDate - b.msgDate })
          if (content['result'].length < this.limit) {
            this.allLoaded = true
          }
          this.$refs.scrollView.finishRefresh()
          Toast.hide()
        } else {
          console.log(11)
          Toast.hide()
          this.allLoaded = true
          this.$refs.scrollView.finishRefresh()
        }
      })
    },
    getDetail () { //获取素材详情
      let _this = this
      let p = new Promise(function (resolve, reject) {
        if (_this.pagetype == '10') {
          let data = { btagcode: '1', sno: _this.libno, flag: '1' }
          selectOneBySno(data).then(res => {  //素材详情
            _this.detail.libtitle = res.data.data.stitle
            _this.detail.libpic = res.data.data.pichttp
            _this.detail.libno = res.data.data.sno
            _this.detail.type = '10'
            resolve(res)
          })
        }
      })
      return p
    }
  }
}
</script>
<style scoped lang="styl">
.center {
  flex: 1;
  overflow: auto;
}
.content {
  background: #f5f7fa;
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
}
.content .txleft {
  width: 1.3rem;
  height: 1.3rem;
  border: 1px solid #fafafa;
  border-radius: 0.3rem;
  overflow: hidden;
  float: left;
}
.content .txright {
  width: 1.3rem;
  height: 1.3rem;
  border: 1px solid #fafafa;
  border-radius: 0.3rem;
  overflow: hidden;
  float: right;
}
.content .ctdiv {
  border-radius: 4px;
  border: 1px solid #ccc;
  background: #fff;
  max-width: 70%;
  padding: 30px 30px;
  position: relative;
  float: left;
  margin-left: 0.7rem;
}
.content .andiv {
  border-radius: 4px;
  border: 1px solid #ccc;
  background: white;
  max-width: 70%;
  padding: 30px 30px;
  position: relative;
  float: right;
  margin-right: 0.7rem;
}
.xunwen {
  position: relative;
}
.content .listy {
  overflow: hidden;
  margin-bottom: 25px;
}
.listdiv {
  padding: 0.2rem 0.2rem;
  border-radius: 0.06rem;
  border: 1px solid #ccc;
  margin-top: 0.27rem;
}
.listdiv {
  overflow: hidden;
}
.listdiv .imgpos {
  float: right;
}
.sty {
  margin-left: 0.2rem;
  font-size: 0.35rem;
  display: inline-block;
  position: relative;
  bottom: 0.13rem;
}
.imgsty {
  width: 0.7rem;
}
.content .touxiang {
  width: 100%;
}
.footdiv {
  background-color: #fcfcfc;
  color: #aaa;
  font-size: 14px;
  font-size: 0.14rem;
  height: 2.5rem;
}
.footdiv .foottop {
  padding: 0.3rem 0.3rem 0.2rem;
}
.footdiv .foottop .sdiv {
  border-radius: 0.2rem;
  background-color: #fff;
  border: 1px solid #ccc;
  padding: 0.03rem 0.3rem;
  margin-left: 0.2rem;
  color: #aaa;
}
.footdiv .footbot {
  padding: 0.1rem 0.08rem;
  width: 100%;
}
.footdiv .footbot .inputsty {
  padding: 0.2rem;
  background-color: #fff;
  border: 1px solid #ccc;
  height: 1rem;
  font-size: 0.3rem;
  border-radius: 0.05rem;
  /* -webkit-box-flex: 1; */
  /* -ms-flex: 1; */
  /* flex: 1; */
  width: 78%;
}
.footdiv .footbot .butsty {
  width: 1.5rem;
  height: 1rem;
  border-radius: 0.2rem;
  background: color-primary;
  border: 1px solid color-primary;
  color: #fff;
  text-align: center;
  line-height: 1rem;
  /* margin-top: 0.025rem; */
  margin-left: 0.1rem;
  float: right;
  font-size: 0.35rem;
}
.squre {
  display: inline-block;
  background-color: white;
  width: 0.4rem;
  height: 0.4rem;
  -webkit-transform: rotate(45deg);
  transform: rotate(45deg);
  position: absolute;
  right: -0.2rem;
  top: 0.4rem;
  border-right: 1px solid #ccc;
  border-top: 1px solid #ccc;
}
.whitesqure {
  display: inline-block;
  background-color: white;
  width: 0.4rem;
  height: 0.4rem;
  -webkit-transform: rotate(45deg);
  transform: rotate(45deg);
  position: absolute;
  left: -0.2rem;
  top: 0.4rem;
  border-left: 1px solid #ccc;
  border-bottom: 1px solid #ccc;
}
.foot {
  z-index: 199;
  width: 100%;
  height: 55px;
  position: absolute;
  bottom: 0;
  left: 0;
}
.tstitle {
  height: 0.7rem;
  border-bottom: 1px solid #ccc;
}
.tscontent {
  width: 5rem;
  height: 1.5rem;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  margin-top: 0.15rem;
}
.tsimg {
  width: 1rem;
  height: 1rem;
}
.tsimg img {
  width: 100%;
  height: 100%;
}
.tsjj {
  flex: 1;
  padding: 0 0.2rem;
  text-overflow: -o-ellipsis-lastline;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}
.bigwords {
  font-size: 0.4rem;
}
.smallwords {
  font-size: 0.35rem;
}
</style>
