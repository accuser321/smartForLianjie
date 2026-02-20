<template>
  <div></div>
</template>

<script>
import { getToken, getInit ,getUserInfo} from '@/api/basic'
import { setStorage, getStorage } from '@/lib/util'
import config from '@/config'
export default {
  name: 'index',
  beforeCreate () {
    let path = getStorage('r_t', '')
    /* 如果获取到reject并且reject为true并且首次进来的页面为yes */
    if (getStorage('reject', false)) {
      this.$router.push(path)
      return ''
    } else {
      /* 根据分享出去的页面需要,增加三个传参 */
      let params = { sno: '', empno: '', type: '' }
      // eslint-disable-next-line no-unused-expressions
      path.split('?')[1]
        ? path
          .split('?')[1]
          .split('&')
          .forEach(item => {
            let keyvalue = item.split('=')
            params[keyvalue[0]] = keyvalue[1]
          })
        : ''
      let { sno, empno, type } = params
      /* 根据code获取token以及用户信息 */
      let { code } = this.$route.query
      getToken({ code, sno, empno, type }).then(res => {
        setStorage('i_f', res.data.data)
        /*
         * 获取用户信息和模块菜单
         * */
        getUserInfo().then(res => {
          console.log("用户信息")
          console.log(res);
          // let { ketel, kfqrcode } = res.data.data.data
          setStorage('u_s', res.data.data.data.user)
          // setStorage('m_l', res.data.data.menulist)
          // setStorage('ossurl', res.data.data.ossurl)
          // setStorage('msossurl', res.data.data.msossurl)
          // setStorage('comname', res.data.data.comname)
          // setStorage('service', res.data.data.flag)
          // setStorage('imgs', res.data.data.abtComModelsMobileImgs)
          // setStorage('banner', res.data.data.banner)
          // setStorage('bar', res.data.data.bar)
          // setStorage('kfInfo', { ketel, kfqrcode })
          // setStorage('website', res.data.data.website)
          // setStorage('flag', res.data.data.user.promotionprice)
          // setStorage('qrcode', res.data.data.qrcode)
          /* 保存该字段是页面返回的问题 */
          setStorage('reject', true)
          /*
           * 回到最初进来的路由
           * */
          if (getStorage('httpflag', '')) {
            let httpurl = getStorage('httpflag').httpurl.split('&cpflag')[0]
            console.log("认证1")
            console.log(httpurl)
            location.replace(httpurl)
          } else if (getStorage('valid') == 'yes') {
            console.log("认证2")
            console.log(`${config.redirect_uri}${path}`)
            window.entryUrl = ''
            if (path == '/customercenter' && res.data.data.data.user.rytype == 'W') {
              location.replace(`${config.redirect_uri}/bdtg`)
            } else {
              location.replace(`${config.redirect_uri}${path}`)
            }
          } else {
            console.log("认证3")
            console.log(path)
            if (res.data.data.data.user.comid == 'NHWX') {
              // 写死的域名
              if (path == '/' && /(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
              // ios
                location.replace(`${config.redirect_uri}${path}`)
              } else {
                this.$router.push({ path: path })
              }
            } else {
              // 动态获取的域名
              this.$router.push({ path: path })
            }
          }
        })
      })
    }
  }
}
</script>

<style scoped></style>
