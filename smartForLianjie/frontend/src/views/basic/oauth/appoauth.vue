<!--
 * @Author: 刘格优
 * @Date: 2019-12-12 15:14:10
 * @LastEditors: 李波
 * @LastEditTime: 2020-02-19 21:47:22
 -->

<template>
  <div></div>
</template>

<script>
import { Base64 } from 'js-base64';
import { access, getInit ,getUserInfo} from '@/api/basic';
import { setStorage, getStorage } from '@/lib/util';
import config from '@/config';
import store from '@/store'
export default {
  data () {
    return {}
  },
  created () {
    let result = decodeURI(this.$route.query.channelData)
    store.state.app.isloading = true
    console.log(result)
    // let a = result
    //   .replace(/ /g, '+')
    //   .replace(/%2F/g, '/')
    //   .replace(/%3D/g, '=')
    //   .replace(/%2B/g, '+')
    //   .replace(/%3F/g, '?')
    //   .replace(/%25/g, '%')
    //   .replace(/%23/g, '#')
    //   .replace(/%26/g, '&')
    // console.log(a)
    setStorage('i_f', {})
    access({     //从app跳转获取用户信息
      //   empno: '1111',
      //   function: 'nbsInfo',
      //   sign: '9FC690E164FB0FCE857858C0524AE10C'
      //   empno: result.empno,
      //   function: result.function,
      //   sign: result.sign
      appurl: result
    }).then(res => { 
      setStorage('i_f', res.data.data)
      let jumpurl = res.data.data.function
      getUserInfo().then(res1 => {
        setStorage('u_s', res.data.data.data.user)
        // setStorage('m_l', res1.data.data.menulist)
        // setStorage('ossurl', res1.data.data.ossurl)
        // setStorage('comname', res1.data.data.comname)
        // setStorage('imgs', res1.data.data.abtComModelsMobileImgs)
        // setStorage('banner', res.data.data.banner)
        // setStorage('bar', res.data.data.bar)
        if (jumpurl) {
          location.replace(`${config.redirect_uri}/${jumpurl}?fromwhere=app&bdtgflush=true`)
        }
        store.state.app.isloading = false
        // location.replace(`${config.redirect_uri}/nbsInfo`)
      })
    })
  },
  components: {},

  computed: {},

  methods: {}
}
</script>
<style scoped></style>
