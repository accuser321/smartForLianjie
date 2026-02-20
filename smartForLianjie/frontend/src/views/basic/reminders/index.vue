<!--
/*
 * @Author: 李宗哲
 * @Date: 2020-01-08 11:35:12
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2020-01-08 18:38:35
 */
 -->
<template>
  <div class="content">
    <div class="topbox"
         ref="zntopbox">
      <div class="topimgbg">
        <div class="topimg">
          <img src="@/assets/nh/nhsxzntx.png"
               alt=""
               class="mainpic">
        </div>
      </div>
      <div class="searchbox clearfix">
        <div class="searchline clearfix">
          <button class="btnicon">
            <md-icon name="search"
                     size="lg"></md-icon>
          </button>
          <input type="text"
                 placeholder="搜索客户姓名"
                 v-model="searchValue"
                 class="searchinput"
                 v-on:blur="yourmethod"
                 v-on:focus="getFocus">
          <button class="btnclear"
                  @click="clearinput"
                  v-show="isclear">
            <md-icon name="fail"
                     size="lg"
                     color="#666"></md-icon>
          </button>
        </div>
        <div class="select">
          <input v-model="shortname"
                 readonly
                 @click="changeCompany"
                 type="text"
                 placeholder="筛选"
                 style="color: #383838;" />
          <md-icon name="filter"
                   size="md"
                   class="icon"></md-icon>
          <md-selector v-model="isSelectorShow"
                       :data="insuranceCompany"
                       :default-value="type"
                       max-height="320px"
                       title="请选择类型"
                       @choose="onSelectorChoose"></md-selector>
        </div>
      </div>
    </div>
    <div class="contentbox">
      <md-scroll-view ref="scrollView"
                      v-show="!searchisNull"
                      auto-reflow
                      :scrolling-x="false"
                      @end-reached="loadMore">
        <div v-for="(item, index) in remindList"
             :key="index"
             class="contentlist clearfix">
          <div class="listbox"
               @click="jump(item)">
            <div class="left">
              <img v-if="item.alerttype=='1'"
                   :src="remindicon[0].img"
                   alt="">
              <!-- <img v-if="item.alerttype=='2'"
                   :src="remindicon[1].img"
                   alt=""> -->
              <img v-if="item.alerttype=='3'"
                   :src="remindicon[2].img"
                   alt="">
              <img v-if="item.alerttype=='4'"
                   :src="remindicon[3].img"
                   alt="">
              <p class="toptips"
                 v-if="item.alerttype=='1'">生日</p>
              <!-- <p class="toptips"
                 v-if="item.alerttype=='2'">车险</p> -->
              <p class="toptips"
                 v-if="item.alerttype=='3'">续期</p>
              <p class="toptips"
                 v-if="item.alerttype=='4'">满期</p>
            </div>
            <div class="right">
              <div class="rrightt">
                <h4 class="title">{{item.enumname}}</h4>
                <div class="chosetime">
                  <span>客户：{{item.pname}}</span><br />
                  <span v-if="item.alerttype=='1'">生日：{{item.birthdate}}</span><br v-if="item.alerttype=='1'" />
                  <!-- <span v-if="item.alerttype=='2'">车牌号：{{item.carno}}</span><br v-if="item.alerttype=='2'" /> -->
                  <!-- <span v-if="item.alerttype=='2'">车险到期日：{{item.pdate}}</span><br v-if="item.alerttype=='2'" /> -->
                  <span v-if="item.alerttype=='3'||item.alerttype=='4'">保单号：{{item.policyno}}</span><br v-if="item.alerttype=='3'||item.alerttype=='4'" />
                  <span v-if="item.alerttype=='3'">保险应缴日：{{item.pdate}}</span><br v-if="item.alerttype=='3'" />
                  <span v-if="item.alerttype=='4'">保险到期日：{{item.pdate}}</span><br v-if="item.alerttype=='4'" />
                </div>
              </div>
              <div class="twotwo"
                   v-if="item.status=='0'">
                <div class="tishi"></div>
              </div>
            </div>
          </div>
        </div>
        <div class="showUrl"></div>
        <md-scroll-view-more slot="more"
                             :is-finished="loading">
        </md-scroll-view-more>
      </md-scroll-view>
      <div class="nodata"
           v-show="searchisNull">
        <img class="kongimg"
             src="@/assets/image/null.png"
             alt="" />
      </div>
    </div>
  </div>
</template>

<script>

import {  selectzntx
} from '@/api/abt/customerOperation/zhinengtx/index'

import { getStorage } from '@/lib/util'
import { Toast, Field, FieldItem, Dialog } from 'mand-mobile'

export default {
  data () {
    return {
      searchisNull: true,
      shortname: '',
      searchValue: '',
      selectval: '',
      remindList: [],
      remindicon: [
        {
          img: require('@/assets/abt/img/zntx01.png')
        },
        // {
        //   img: require('@/assets/abt/img/zntx02.png')
        // },
        {
          img: require('@/assets/abt/img/zntx03.png')
        },
        {
          img: require('@/assets/abt/img/zntx04.png')
        }
      ],
      type: '',
      height: '',
      topheight: '',
      loading: false,
      currentPage: 1,
      pageSize: 5,
      total: 0,
      totalPage: 0,
      insuranceCompany: [
        {
          value: '',
          text: '全部'
        },
        {
          value: '1',
          text: '生日提醒'
        },
        // {
        //   value: '2',
        //   text: '车险续期提醒'
        // },
        {
          value: '3',
          text: '保单续期提醒'
        },
        {
          value: '4',
          text: '保单期满提醒'
        }
      ],
      isSelectorShow: false,
      isclear: false,
      user: {}
    }
  },
  created () {
    this.user = getStorage('u_s', {})
    this.getData()
  },
  mounted () {
    this.$nextTick(() => {
      setTimeout(() => {
        this.topheight = this.$refs.zntopbox.clientHeight
        this.height = document.body.clientHeight - this.topheight
        document.querySelector('.contentbox').style.height = `${this.height}px`
      }, 1000)
    })
  },
  methods: {
    jump (val) {//智能提醒详情页
      this.$router.push(
        `/remindersshow?alerttype=${val.alerttype}&pdate=${val.pdate}&pname=${val.pname}&policyno=${val.policyno}&tel=${val.tel}&birthdate=${val.birthdate}`
      )
    },
    onSelectorChoose ({ value, text }) { //下拉框选择
      this.type = value
      this.currentPage = 1
      this.getData()
    },
    changeCompany () { //打开下拉框
      this.isSelectorShow = true
    },
    yourmethod () {//搜索
      this.currentPage = 1
      this.getData()
    },
    getFocus () {//获取搜索焦点展示删除
      this.isclear = true
    },
    clearinput () {//清除搜索值
      this.searchValue = ''
      this.isclear = false
      this.currentPage = 1
      this.getData()
    },
    async getData (isInit = true) {  //获取智能提醒列表
      let { currentPage, pageSize } = this
      if (!isInit) {
        this.currentPage = ++currentPage
        this.$refs.scrollView.finishLoadMore()
      }
      let data = {
        currentPage: this.currentPage,
        pageSize: this.pageSize,
        type: this.type,
        name: this.searchValue,
      }
      let res = await selectzntx(data)
      // console.log('智能提醒')
      // console.log(res);
      this.remindList = isInit ? res.data.data.rows : this.remindList.concat(res.data.data.rows)

      if (this.remindList.length == 0) {
        this.searchisNull = true
      } else {
        this.searchisNull = false
      }
      this.total = res.data.data.total
      this.totalPage = res.data.data.totalpage
      return res
    },
    loadMore () {
      let { loading, currentPage, totalPage } = this
      if (loading) {
        return false
      } else {
        if (totalPage <= currentPage) {
          this.loading = true
        } else {
          this.getData(false)
        }
      }
    },
  }
}
</script>
<style scoped lang="stylus">
.clearfix:after { /* 伪元素是行内元素 正常浏览器清除浮动方法 */
  content: '';
  display: block;
  height: 0;
  clear: both;
  visibility: hidden;
}

// }
.topbox {
  height: 5rem;
  // -webkit-box-flex: 1;
  // -webkit-flex: 1;
  // -ms-flex: 1;
  // flex: 1;
  width: 100%;

  .topimgbg {
    height: 3.6rem;
    width: 100%;
    // background: linear-gradient(90deg, #0af, #0085ff);
  }

  .topimg {
    height: 100%;
  }

  .searchbox {
    // height: 1rem;
    // margin-top: 0.3rem;
    padding: 0 0.6rem;

    .searchline {
      margin-top: 0.3rem;
      border-radius: 0.5rem;
      background: #eee;
      height: 1rem;
      width: 80%;
      float: left;

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
      }

      .btnclear {
        background: none;
      }
    }

    .select {
      margin-top: 0.3rem;
      width: 20%;
      float: right;
      text-align: right;

      input {
        width: 66%;
        height: 1rem;
        background: none;
        color: #383838;
        outline: none;
        text-align: right;
      }

      button {
        outline: none;
        background: none;
        height: 1rem;
        margin-right: 8px;
      }
    }
  }
}

.contentlist {
  width: 100%;
  padding: 0.2rem 0.6rem;

  .listbox {
    box-shadow: 10px 10px 20px #EBEBEB;
    margin-bottom: 0.2rem;
    width: 100%;
    border-radius: 0.3rem;
    background: #fff;
    display: flex;
    justify-content: space-between;
    padding: 0 0 0.3rem 0.3rem;

    .left {
      width: 25%;
      position: relative;

      img {
        width: 35%;
        height: auto;
        margin-left: 30%;
        margin-top: 58%;
      }

      .toptips {
        color: #F5F5F5;
        font-size: 0.3rem;
        width: 84.5%;
        height: 0.8rem;
        background-image: url('~@/assets/nh/nhwxzntxbq.png');
        background-size: 1.8rem;
        background-repeat: no-repeat;
        position: absolute;
        top: 0;
        right: 0.6rem;
        text-align: center;
        line-height: 0.8rem;
      }
    }

    .right {
      margin-right: 0.3rem;
      float: left;
      width: 70%;
      display: flex;
      justify-content: space-between;

      .rrightt {
        width: 80%;
        flex-direction: column;
        justify-content: center;
        display: flex;
      }

      .twotwo {
        display: flex;
        align-items: center;
        width: 10%;

        .tishi {
          background: red;
          // border: 2px solid red;
          border-radius: 50%;
          width: 0.25rem;
          height: 0.25rem;
          margin-left: auto;
        }
      }

      .title {
        margin-bottom: 0.3rem;
        margin-top: 0.3rem;
      }

      .chosetime {
        color: #B4B4B4;

        span {
          font-size: 0.3rem;
          padding: 0.1rem 0.2rem;
          word-wrap: break-word;
        }
      }
    }
  }
}

/deep/ .md-field-item-content {
  padding: 0;
  min-height: 0.5rem;
}

.nodata {
  padding-top: 30%;
  text-align: center;
}

.kongimg {
  width: 300px;
  height: auto;
}
</style>
