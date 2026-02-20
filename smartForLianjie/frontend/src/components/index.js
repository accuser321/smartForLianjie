/*
 * @Author: 王鹏
 * @Date: 2019-10-29 17:26:57
 * @LastEditors: 刘格优
 * @LastEditTime: 2019-12-06 11:05:31
 */
import {
  Button,
  Icon,
  Toast,
  Dialog,
  Tag,
  TabBar,
  DatePicker,
  Field,
  FieldItem,
  Popup,
  Landscape,
  PopupTitleBar,
  ResultPage,
  ScrollViewRefresh,
  ScrollView,
  ScrollViewMore,
  InputItem,
  CheckList,
  ImageViewer,
  Tabs,
  TabPane,
  Selector,
  Swiper,
  SwiperItem,
  Radio,
  CellItem,
  RadioBox,
  RadioGroup,
  TextareaItem,
  ActivityIndicator
} from 'mand-mobile';

const components = [
  Button,
  Icon,
  Dialog,
  Tag,
  TabBar,
  DatePicker,
  Field,
  FieldItem,
  Landscape,
  ScrollView,
  ScrollViewMore,
  Popup,
  PopupTitleBar,
  ResultPage,
  ScrollViewRefresh,
  InputItem,
  CheckList,
  ImageViewer,
  Tabs,
  TabPane,
  Selector,
  Swiper,
  SwiperItem,
  Radio,
  CellItem,
  RadioBox,
  RadioGroup,
  TextareaItem,
  ActivityIndicator
]

const install = (Vue, opts = {}) => {
  components.forEach(component => {
    Vue.component(component.name, component)
  })
  Vue.prototype.$toast = Toast
  Vue.prototype.$dialog = Dialog
};

export default {
  install
}
