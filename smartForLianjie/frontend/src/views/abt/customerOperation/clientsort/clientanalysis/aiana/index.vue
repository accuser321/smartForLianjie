<!--
 * @Author: 刘格优
 * @Date: 2019-11-14 15:15:34
 * @LastEditors: 刘格优
 * @LastEditTime: 2019-12-04 16:30:08
 -->

<template>
  <div>
    <div class="customerdemand">
      <p>客户15日活跃度</p>
      <ve-line
        :data="chartData"
        :settings="chartSettinghd"
        ref="chart1"
        :legend-visible="false"
      ></ve-line>
    </div>
    <div class="customerdemand">
      <p>客户需求分析</p>
      <ve-pie
        :data="btData"
        :settings="chartSettings"
        :legend-visible="false"
        ref="chart2"
      ></ve-pie>
    </div>
    <div class="customerdemand">
      <p>客户与我的互动</p>
      <ve-bar
        :data="chartDatahd"
        :settings="chartSettinghd"
        :legend-visible="false"
        ref="chart3"
      ></ve-bar>
    </div>
    <div class="customerdemand">
      <p>个人兴趣占比</p>
      <ve-pie
        :data="chartDataxq"
        :settings="chartSettings"
        :legend-visible="false"
        ref="chart4"
      ></ve-pie>
    </div>
    <!-- <div class="customerdemand">
      <p>智能营销漏斗</p>
      <ve-funnel
        :data="chartDatald"
        :settings="chartSettingld"
        :legend-visible="false"
        ref="chart5"
      ></ve-funnel>
    </div> -->
  </div>
</template>

<script>
import { getAIAnalysis } from '@/api/abt/customerOperation/clientsort/index'
export default {
  props: {
    userid: String
  },
  data () {
    this.chartSettings = {
      // dataType: 'percent',
      radius: 110
    }
    // this.chartSettingld = {
    //   sequence: ['第一层', '第二层', '第三层', '第四层']
    // }
    this.chartSettinghd = {
      labelMap: {
        num: '数值'
      }
    }
    return {
      // 客户15日活跃度
      chartData: {
        columns: ['time', 'num'],
        rows: []
      },
      // 客户需求分析
      btData: {
        columns: ['tagname', 'num'],
        rows: []
      },
      // 客户与我的互动
      chartDatahd: {
        columns: ['type', 'num'],
        rows: []
      },
      // 个人兴趣占比
      chartDataxq: {
        columns: ['type', 'num'],
        rows: []
      }
      // // 智能营销漏斗
      // chartDatald: {
      //   columns: ['type', 'num'],
      //   rows: []
      // }
    }
  },
  created () {
    this.$nextTick(() => {
      this.getdata()
    })
  },
  components: {},

  computed: {},
  methods: {
    getdata () {
      getAIAnalysis({ userid: this.userid }).then(res => {
        // 客户15日活跃度
        this.chartData.rows = res.data.data.activeList
        // 客户需求分析
        this.btData.rows = res.data.data.demandList
        // this.btData.rows.forEach((item, index) => {
        //   let data = item.tagname + '/' + item.num
        //   this.$set(this.btData.rows[index], 'tagnameNum', data)
        // })
        // 客户与我的互动
        this.chartDatahd.rows = res.data.data.interactive
        // 个人兴趣占比
        this.chartDataxq.rows = res.data.data.interest
        // // 智能营销漏斗
        // this.chartDatald.rows = res.data.data.smartfunnel
        // console.log(this.chartDatald.rows)
      })
    }
  }
}
</script>
<style scoped>
.customerdemand p {
  text-align: center;
  line-height: 1.5rem;
  font-size: 0.4rem;
}
</style>
