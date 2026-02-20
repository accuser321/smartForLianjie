<template>
  <div class="container">
    <!-- 头部 -->
    <div class="head">
      <div class="head-top">
        <div class="row">
          <div class="col">
            <span style="font-size:30px;width:50%;">{{jfnum}}</span>
          </div>
          <!-- <div class="col">
            <div class="tag-jf">
              <i class="iconfont icontishi" style="color:#88888A;font-size:0.28rem;"/>
              <span style="color:#88888A;font-size:0.28rem;">  积分规则  </span>
            </div>
          </div> -->
        </div>
        <!-- <div>
           <span style="font-size:0.28rem;font-weight: normal;">0分永久有效</span>
        </div> -->
        <div class="tag-jf">
          <i class="iconfont iconshiliangzhinengduixiang" style="color:#88888A;font-size:0.35rem;"/>
          <span style="color:#88888A;font-size:0.35rem;">  我的积分  </span>
        </div>
      </div>
      <div class="head-bom">
        <div class="left">
            <!-- <md-field style="font-size: 0.4rem;">
              <md-field-item
                name="name"
                title="日期"
                arrow="arrow-right"
                align="center"
                :content="datePickerValue"
                @click.native="isDatePickerShow = true">
              </md-field-item>
            </md-field>
            <md-date-picker
              ref="datePicker"
              v-model="isDatePickerShow"
              type="custom"
              title="选择年月"
              large-radius
              :min-date="minDate"
              :custom-types="['yyyy', 'MM']"
              :default-date="currentDate"
              @confirm="onDatePickerConfirm"
            ></md-date-picker> -->
          <!-- <i class="iconfont iconjiantou-up-down" style="color:#999999;"/> -->
        </div>
        <!-- <div class="right">
          <span>筛选</span>
          <i class="iconfont iconjiantou-up-down" style="color:#999999;"/>
        </div> -->
      </div>
    </div>
    <!-- 内容 -->
    <div class="content" v-if="jflist.length">
       <md-scroll-view ref="scrollView"
                        :auto-reflow="true"
                        :scrolling-x="false"
                        @end-reached="loadMore"
                        :loading-text="totalpage < pageNo || totalpage == pageNo?'没有要加载的数据啦...':'正在加载中'">
      <div class="line" v-for="(item,index) in jflist" :key="index">
        <div class="l_date">
          <div class="l_label">{{ item.jfInfo.label }}</div>
          <p>{{item.jftime}}</p>
        </div>
        <div class="l_value">
          <span >+{{item.jf}}</span>
        </div>
      </div>
      <md-scroll-view-more slot="more" :is-finished="loading" />
      </md-scroll-view>
    </div>
    <!-- <md-result-page :img-url="require('@/assets/abd/image/null.png')"
                      v-else
                      subtext="要不然刷新试试？"/> -->
  </div>
</template>

<script>
import { jfInfo } from '@/api/basic/index'
import loadMore from '@/mixins/loadmore'
export default {
  mixins: [loadMore],
  data(){
    return {
      user: '',
      form: {},
      zb: [],
      jflist: [{jfInfo:{}}],
      minDate: new Date('1990/1/1'),
      currentDate: new Date(),
      isDatePickerShow: false,
      datePickerValue: '',
      defaultdate:'2020-01',
      yjjf:'',
      year:'',
      month:'',
      catchdate:'',
      pageNo: 1,
      pageSize: 6,
      totalpage: 0,
      total:'',
      loading: false,
      jfnum:'0'
    }
},
created() {
  this.changeDate();
  this.getData()
},
methods: {
  //获取积分
  async getData(isInit = true){
      this.jf = this.$route.query.jf
      if (!isInit) {
        if (this.totalpage < this.pageNo || this.totalpage == this.pageNo) {
          this.pageNo = this.totalpage
        } else {
          this.pageNo = ++this.pageNo
        }
        this.$refs.scrollView.finishLoadMore()
      }
      
      let a = this.pageNo*this.pageSize
      if(this.jflist.length !== this.total){
        let res = await jfInfo({
        page: this.pageNo, size: this.pageSize
      })
      if( res.data.data.rows.length>0 ){
        if (this.pageNo > 1) {
          for (let item in res.data.data.rows) {
            this.jflist.push(res.data.data.rows[item])
            this.total = res.data.data.total
            this.totalpage = res.data.data.totalpage
            this.jfnum = res.data.data.jfnum
          }
        }else{
          this.jflist = res.data.data.rows
          this.total = res.data.data.total
          this.totalpage = res.data.data.totalpage
          this.jfnum = res.data.data.jfnum
        }
      } else {
        this.jflist = []
      }
      this.$forceUpdate()
      }
  },
  onDatePickerConfirm(columnsValue) { //选择年月  被注销了
    let data = this.$refs.datePicker.getFormatDate('yyyy-MM').split('-')
    this.datePickerValue = data[0] + '-' + data[1]
    this.catchdate = data[0] + data[1]
    this.getData(this)
    this.$forceUpdate()
  },
  changeDate(){ //选择时间
    let nowDate = new Date();
    let date = {
      year :nowDate.getFullYear()+ '',
      month : nowDate.getMonth() + 1+ ''
    }
    if ( date.month < 10 ) {
      this.datePickerValue = date.year + '-' + '0' + date.month;
    } else {
      this.datePickerValue = date.year + '-' + date.month;
    }
  }
},

}
</script>

<style lang="stylus" scoped>
bg-color = #F5F5F9;
.container {
  background-color  #FFFFFF
  width 100vw
  height 100vh
  overflow hidden
  .head{
     position relative
     border-bottom: 15px solid #F5F5F9
     .head-top{
        height 370px
        padding-left 100px
        padding-top 120px
        background: url('../../../assets/image/centerjfmx.png');
        background-size: 100% auto;
        background-repeat: no-repeat;
        .row {
          display: flex;
          justify-content flex-start
          text-align:center 
          >.col:first-child {
              width 90%
          }
          // padding-left: 10px;
          // >.col:last-child {
            
          // }
        }
        .tag-jf{
            display: flex;
            text-align:center ;
            width 90%
            padding: 10px 28px;
            margin: 1px 0;
            background-color: #FFFFFF;
            border-color: #C6D3E9
            border-radius: 50px;
            display: inline-block;
            color: bg-color;
        }
        .head-bom{
          padding-top:-100px;
          background-color #ffffff
          width 100vw
          height 150px
          margin 0 auto 0.3rem
          display flex
          .left{
            display flex
            .md-field .md-field-content /deep/.md-field-item-title{
                  font-size:0.4rem
              }
              .md-field .md-field-content /deep/.md-field-item-control{
                  font-size:0.4rem
                  color:#228ACE
              }
          }
        }
     }
  }
  .content{
    height: 70vh
    width: 100%
  }
  .line {
      background-color #FFFFFF
      align-items center
      height: 200px
      font-family:PingFang SC;
      width 93.35%
      border-bottom: 1px solid #EAEAEA
      margin 0px auto 
      display flex
      justify-content space-between
      text-align left
      .l_date {
        width 50%
        .l_label{
          padding 5px 0 5px 0.6rem
          font-size:0.40rem;
        }
        p {
          padding 5px 0 5px 0.6rem
          width 100%
          height 5%
          font-size:0.28rem;
          
          color:#A3A3A3;
        }
      }
      .l_value {
        width 15.35%
        span {
          width 30%
          height 5%
          font-size:0.45rem;
          font-family:PingFang SC;
          color:rgba(44,44,60,1);
        }
      }
    }
}
</style>