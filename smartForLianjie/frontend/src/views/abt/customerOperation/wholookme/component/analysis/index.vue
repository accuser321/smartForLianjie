<template>
  <div class="container">
    <div class="scroll"
         :style="{ height: height + 'px' }">
      <div class="title2">
        <div class="item">
          <div class="total">
            <span>客户总数</span>
          </div>
          <div class="number">
            <span>{{ mestop.kuNUM }}</span>
          </div>
          <div class="red">
            <span>(累计)</span>
          </div>
        </div>
        <div class="item">
          <div class="total">
            <span>名片点赞</span>
          </div>
          <div class="number">
            <span>{{ mestop.mpDZ }}</span>
          </div>
          <div class="red">
            <span>(累计)</span>
          </div>
        </div>
        <div class="item">
          <div class="total">
            <span>名片转发</span>
          </div>
          <div class="number">
            <span>{{ mestop.mpZF }}</span>
          </div>
          <div class="red">
            <span>(累计)</span>
          </div>
        </div>
        <div class="item">
          <div class="total">
            <span>文章浏览</span>
          </div>
          <div class="number">
            <span>{{ mestop.wzRD }}</span>
          </div>
          <div class="red">
            <span>(累计)</span>
          </div>
        </div>
        <div class="item">
          <div class="total">
            <span>文章转发</span>
          </div>
          <div class="number">
            <span>{{ mestop.wzZF }}</span>
          </div>
          <div class="red">
            <span>(累计)</span>
          </div>
        </div>
        <div class="item">
          <div class="total">
            <span>咨询总数</span>
          </div>
          <div class="number">
            <span>{{ mestop.zxNUM }}</span>
          </div>
          <div class="red">
            <span>(累计)</span>
          </div>
        </div>
      </div>
      <div class="chart">
        <div class="chartTitle">
          <span>新增客户数</span>
        </div>
        <div class="chartBtn">
          <div class="btn blueBorder newkh"
               @click="getchart('7', 'newkh', '0')">
            近7日
          </div>
          <div class="btn newkh"
               @click="getchart('15', 'newkh', '1')">
            近15日
          </div>
          <div class="btn newkh"
               @click="getchart('30', 'newkh', '2')">
            近30日
          </div>
        </div>
        <ve-line :data="chartDatakh"
                 :settings="chartSettings"
                 :legend-visible="false"></ve-line>
      </div>
      <div class="chart">
        <div class="chartTitle">
          <span>阅读数</span>
        </div>
        <div class="chartBtn">
          <div class="btn blueBorder read"
               @click="getchart('7', 'read', '0')">
            近7日
          </div>
          <div class="btn read"
               @click="getchart('15', 'read', '1')">
            近15日
          </div>
          <div class="btn read"
               @click="getchart('30', 'read', '2')">
            近30日
          </div>
        </div>
        <ve-line :data="chartDatayd"
                 :settings="chartSettings"
                 :legend-visible="false"></ve-line>
      </div>
      <div class="chart">
        <div class="chartTitle">
          <span>近15日客户活跃度</span>
          <ve-line :data="chartData"
                   :settings="chartSettings"
                   :legend-visible="false"></ve-line>
        </div>
      </div>
      <div class="chart">
        <div class="chartTitle">
          <span>客户与我互动</span>
          <ve-bar :data="chartDatahd"
                  :settings="chartSettings"
                  :legend-visible="false"></ve-bar>
        </div>
      </div>
      <div class="chart">
        <div class="chartTitle">
          <span>智能营销漏斗</span>
          <span class="right"
                @click="isPopupShow = true"></span>
          <ve-funnel :data="chartDatald"
                     :tooltip-visible="false"
                     :legend-visible="false"></ve-funnel>
        </div>
      </div>
    </div>
    <md-popup v-model="isPopupShow"
              position="bottom"
              :mask-closable="false">
      <md-popup-title-bar only-close
                          large-radius
                          @confirm="isPopupShow = false"
                          @cancel="isPopupShow = false"></md-popup-title-bar>
      <div class="md-example-popup">
        <p>智能营销漏斗分层以客户累计活跃次数为标准</p>
        <p>第一层：累计活跃次数≥0</p>
        <p>第二层：累计活跃次数≥50</p>
        <p>第三层：累计活跃次数≥100</p>
        <p>第四层：累计活跃次数≥150</p>
      </div>
    </md-popup>
  </div>
</template>

<script>
export default {
  props: {
    blueBorder: String,
    mestop: {},
    chartData: {},
    chartDatahd: {},
    chartDatakh: {},
    chartDatayd: {},
    chartDatald: {}
  },
  data () {
    this.chartSettings = {
      labelMap: {
        num: '数值'
      },
      yAxisType: 'normal',
      min: ['0', 'dataMax']
    }
    return {
      height: '',
      isPopupShow: false
    }
  },
  created () {
    this.height = document.body.clientHeight - 78
  },
  methods: {
    getchart (time, type, index) {
      let len = document.getElementsByClassName(type).length
      for (let i = 0; i < len; i++) {
        document.getElementsByClassName(type)[i].classList.remove('blueBorder')
      }
      document.getElementsByClassName(type)[index].classList.add('blueBorder')

      if (type == 'read') {
        this.$emit('getReadnum', time, type)
      } else if (type == 'newkh') {
        this.$emit('getNewkhnum', time, type)
      }
    }
  }
}
</script>

<style lang="stylus" scoped>
@import './index.styl';
</style>
