/*
 * @Author: 王鹏
 * @Date: 2019-09-10 16:36:46
 * @LastEditors: 刘格优
 * @LastEditTime: 2020-04-01 17:17:04
 */

export default {
  /**
   * @description 配置显示在浏览器标签的title
   */
  title: "渠道管理平台",

  /**
   * @description api请求基础路径
   */
  baseUrl: {
      // dev:'http://192.168.0.14:8082',
      //  dev:'http://192.168.0.24:1235/smart',//测试环境
  //  dev:'https://wx.ebaolian.com/nhcs/',//网销
    //  dev:'https://wx.ebaolian.com/nhsx',//寿险
      //  dev:'http://192.168.0.120:8082/',
      // dev:'http://192.168.0.57:8089', //测试环境
     
    // dev:'http://nhwx.devnhqs.hcpaiahk.com/',
      // dev: "http://192.168.0.67:8082",
    // Local default for V2 development with springboot-smart.
    dev: 'http://127.0.0.1:8080',
    /* 南华 */
    pro:`https://wx.ebaolian.com/smart`, //213环境
    // pro: `http://${window.location.hostname.split(".")[0]}.devnhcs.hcpaiahk.com`
    // pro: `http://${window.location.hostname.split(".")[0]}.nhcs.abtpt.com`
    // pro: `http://${window.location.hostname.split(".")[0]}.uatcs.hcpaia.com`
  },
  // appId: 'wxb78763020baee641',
  // 寿险管理端ossurl
  // pcossurl: "https://hxyw-test.oss-cn-beijing.aliyuncs.com/",
  pcossurl: "https://hxywtest.oss-cn-shenzhen.aliyuncs.com/",
  // pcossurl:'https://wx.ebaolian.com/nhcs/',
  // // // 南华测试
  //cvu: "https://saasms-test.oss-cn-beijing.aliyuncs.com/",
  cvu:'https://hxywtest.oss-cn-shenzhen.aliyuncs.com/',
  // cvu:'https://wx.ebaolian.com/nhcs/',
  // /* 动态域名 */
  redirect_uri: `https://${window.location.hostname}/article`,
  /**
   * @description 默认打开的首页的路由name值，默认为home
   */
  homeName: "home",
  /**
   *  @description 打包不同的子模块, 默认为 'all' 全部打包
   *               参数支持字符串和数组,如果需要全部打包请将参数直接填写为 'all'
   *               如果要分模块打包请将打包的子模块文件名放入数组当中既可 ['xxx','xxx']
   */
  modules: "all"
};
