/*
 * @Author: 王鹏
 * @Date: 2019-10-29 14:56:00
 * @LastEditors: 王鹏
 * @LastEditTime: 2020-03-21 11:23:35
 */
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import components from './components'
import importDirective from '@/directive'
import transform from '@/lib/transform.js'
import { getStorage } from '@/lib/util'
import i18n from './locale'
import VCharts from 'v-charts'
import VueAwesomeSwiper from 'vue-awesome-swiper'
import VueSignaturePad from 'vue-signature-pad'
import 'swiper/dist/css/swiper.css'
import './lib/fast-click'
import '@/assets/global.styl'
import '@/assets/util.styl'
import 'amfe-flexible'
import '@/validate'
import Vconsole from 'vconsole'
import echarts from 'echarts'
import config from '@/config'
import { Loadmore, InfiniteScroll } from 'mint-ui'
import 'mint-ui/lib/style.css'
Vue.component(Loadmore.name, Loadmore)
Vue.use(InfiniteScroll)
const { redirect_uri } = config
let vConsole = new Vconsole()
Vue.use(vConsole)
Vue.use(VCharts)
Vue.use(components)
Vue.use(i18n)
Vue.use(VueAwesomeSwiper)
Vue.use(VueSignaturePad)
Vue.prototype.$transform = transform
Vue.prototype.$echarts = echarts
Vue.prototype.$store = store
Vue.prototype.getStorage = getStorage
Vue.prototype.redirect_uri = redirect_uri
/**
 * 注册指令
 */
importDirective(Vue)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
