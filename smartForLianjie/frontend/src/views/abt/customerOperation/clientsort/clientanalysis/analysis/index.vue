<!--
 * @Author: 刘格优
 * @Date: 2019-11-14 15:15:20
 * @LastEditors: 刘格优
 * @LastEditTime: 2019-11-19 21:12:39
 -->

<template>
  <div class="khbox">
    <div class="findkh">
      <p class="words">寻找客户</p>
      <div class="search_echart"
           ref="chartfound"></div>
    </div>
  </div>
</template>

<script>
import {
  getKHRelation,
  getKHContacts
} from '@/api/abt/customerOperation/clientsort/index'
import { selectKJFL } from '@/api/abt/customerOperation/common/index'
// 引用echarts组件
let echarts = require('echarts/lib/echarts')
require('echarts/lib/chart/graph')
require('echarts/lib/component/legend')
export default {
  props: {
    userid: String,
    sno:String
  },
  data () {
    return {
      labellist: [],
      read: { data: [], links: [] },
      client: { nodes: [], edges: [] }
    }
  },
  created () {
    this.findkh()
    // this.findrm('')
    // this.getbq()
  },
  components: {},

  computed: {},

  methods: {
    findkh () {
      getKHRelation({ userid: this.userid,sno:this.sno}).then(res => {
        let info = res.data.data
        this.read.data = []
        this.read.links = []
        if (info.message) {
          let len = info.message.length
          for (let i = 0; i < len; i++) {
            this.read.data[i] = {}
            this.read.data[i].id = info.message[i].userid + ''
            this.read.data[i].name = info.message[i].name
            if (info.message[i].headimg) {
              this.read.data[i].symbol = 'image://' + info.message[i].headimg
            } else {
              this.read.data[i].symbol = 'image://' + require('../../../../../../assets/abt/img/wechat.png')
            }
            this.read.data[i].symbolSize = '30'
          }
        }
        if (info.relationship) {
          let edgLen = info.relationship.length
          for (let i = 0; i < edgLen; i++) {
            this.read.links[i] = {}
            this.read.links[i].source = info.relationship[i].source + ''
            this.read.links[i].target = info.relationship[i].target + ''
            this.read.links[i].weight = '1'
            this.read.links[i].itemStyle = {
              normal: { color: 'RGB(127,127,127)' }
            }
            this.read.links[i].symbol = ['none', 'arrow']
            this.read.links[i].lineStyle = {
              normal: {
                color: '#9b9b9b',
                width: '0.5'
              }
            }
          }
        }
        this.drawchartFound()
      })
    },
    // 寻找客户关系图
    drawchartFound () {
      let chartFound = echarts.init(this.$refs.chartfound, { renderer: 'svg' })
      chartFound.setOption(
        {
          tooltip: {
            show: false
          },
          animationDurationUpdate: 1500,
          animationEasingUpdate: 'quinticInOut',
          series: [
            {
              type: 'graph',
              // clickable: true,
              layout: 'force',
              roam: true,
              name: 'Force tree',
              edgeSymbol: ['circle', 'arrow'],
              force: {
                // initLayout: 'circular', // 初始布局
                repulsion: 80, // 斥力大小
                gravity: 0.04,
                edgeLength: 80,
                layoutAnimation: true
              },
              itemStyle: {
                normal: {
                  label: {
                    show: true,
                    position: 'bottom',
                    textStyle: {
                      color: 'black',
                      fontSize: 12
                    }
                  },
                  nodeStyle: {
                    brushType: 'both',
                    borderColor: 'rgba(0,0,0,0.4)',
                    borderWidth: 1,
                    borderRadius: 1,
                    itemStyle: {
                      normal: {
                        label: {
                          position: 'right',
                          textStyle: {
                            color: '#333',
                            fontSize: 12
                          }
                        },
                        nodeStyle: {
                          brushType: 'both',
                          borderColor: 'rgba(255,215,0,0.4)',
                          borderWidth: 1
                        }
                      }
                    }
                  }
                }
              },
              lineStyle: {
                normal: {
                  show: true,
                  color: '#4c74ee',
                  opacity: 1,
                  width: 1,
                  curveness: 0
                }
              },
              categories: [{ name: 0 }],
              minRadius: 15,
              maxRadius: 25,
              linkSymbol: 'arrow',
              data: this.read.data,
              links: this.read.links
            }]
        }, true)
    },
    findrm (labid) { //寻找人脉
      getKHContacts({ userid: this.userid, labid: labid }).then(res => {
        let info = res.data.data
        this.client.nodes = []
        this.client.edges = []
        if (info.relationship) {
          let edgLen = info.relationship.length
          for (let i = 0; i < edgLen; i++) {
            this.client.edges[i] = {}
            this.client.edges[i].source = info.relationship[i].source + ''
            this.client.edges[i].target = info.relationship[i].target + ''
            this.client.edges[i].symbol = ['none', 'arrow']
            this.client.edges[i].lineStyle = {
              normal: {
                color: '#9b9b9b',
                width: '1'
              }
            }
          }
        }
        if (info.message) {
          let len = info.message.length
          for (let i = 0; i < len; i++) {
            this.client.nodes[i] = {}
            this.client.nodes[i].id = info.message[i].userid + ''
            this.client.nodes[i].name = info.message[i].khname
            this.client.nodes[i].symbolSize = '30'
            if (info.message[i].headimg) {
              this.client.nodes[i].symbol =
                'image://' + info.message[i].headimg
            }
            this.client.nodes[i].flag = '1'
          }
        }
        let chartFriendship = echarts.init(this.$refs.chartFriendship, {
          renderer: 'svg'
        })
        chartFriendship.clear()
        this.drawFriendship()
      })
    },
    drawFriendship () { //h画人脉图
      let chartFriendship = echarts.init(this.$refs.chartFriendship, {
        renderer: 'svg'
      })
      chartFriendship.clear()
      chartFriendship.setOption(
        {
          tooltip: {
            show: false
          },
          animationDurationUpdate: 1500,
          animationEasingUpdate: 'quinticInOut',
          series: [
            {
              type: 'graph',
              clickable: true,
              layout: 'force',
              roam: true,
              name: 'Force tree',
              edgeSymbol: ['circle', 'arrow'],
              force: {
                repulsion: 80, // 斥力大小
                gravity: 0.04,
                edgeLength: 80,
                layoutAnimation: true
              },
              itemStyle: {
                normal: {
                  label: {
                    show: true,
                    position: 'bottom',
                    textStyle: {
                      color: 'black',
                      fontSize: 12
                    }
                  },
                  nodeStyle: {
                    brushType: 'both',
                    borderColor: 'rgba(0,0,0,0.4)',
                    borderWidth: 1,
                    borderRadius: 1,
                    itemStyle: {
                      normal: {
                        label: {
                          position: 'right',
                          textStyle: {
                            color: '#333',
                            fontSize: 12
                          }
                        },
                        nodeStyle: {
                          brushType: 'both',
                          borderColor: 'rgba(255,215,0,0.4)',
                          borderWidth: 1
                        }
                      }
                    }
                  }
                }
              },
              lineStyle: {
                normal: {
                  show: true,
                  color: 'gray',
                  opacity: 1,
                  width: 1,
                  curveness: 0
                }
              },
              categories: [{ name: 0 }],
              minRadius: 15,
              maxRadius: 25,
              linkSymbol: 'arrow',
              nodes: this.client.nodes,
              links: this.client.edges
            }
          ]
        },
        true
      )
    },
    getbq () { //获取下拉框值
      selectKJFL({ tagcode: 'L002' }).then(res => {
        this.labellist = res.data.data
      })
    },
    selItem (event, labid) { //切换标签获取人脉
      console.log(labid)
      if (event !== '') {
        let allSel = document.getElementsByClassName('items')[0]
        let sel = allSel.getElementsByClassName('active')[0]
        sel.classList.remove('active')
        let el = event.currentTarget
        el.classList.add('active')
        this.findrm(labid)
      }
    }
  }
}
</script>
<style scoped lang="stylus">
@import './index.styl';
</style>
