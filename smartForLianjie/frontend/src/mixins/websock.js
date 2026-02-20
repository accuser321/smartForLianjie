/*
 * @Author: 黄孝娟
 * @Date: 2019-11-07 16:05:48
 * @Last Modified by: 黄孝娟
 * @Last Modified time: 2019-11-07 17:25:10
 */
export default {
  methods: {
    // 初始化websocket
    initWebSocket(val, val1) {
      if (typeof WebSocket === "undefined") {
        alert("您的浏览器不支持socket");
      } else {
        // console.log("浏览器数据")
        // console.log(val);
        if (val - 0 > 0) {
          // console.log(config.Websocket_uri)
          // 南华正式
          // const wsuri = `ws://47.101.162.119:14365/ws?id=${val}&comid=${val1}`
          // 南华测试
          // const wsuri = `ws://120.79.213.190:14365/ws?id=${val}&comid=${val1}`;
          // const wsuri = `ws://47.93.240.132:14365/ws?id=${val}&comid=${val1}`;
          // 南华uat
          //  const wsuri = `ws://120.79.220.78:14365/ws?id=${val}&comid=${val1}`;
          // const wsuri=`ws://wx.ebaolian.com/nhsx/ws?id=${val}&comid=${val1}`;
          const wsuri =`ws://54.223.26.213:14365/ws?id=${val}&comid=${val1}`;
          console.log(wsuri);
          this.websock = new WebSocket(wsuri);  //客户端就会与服务器进行连接
          this.websock.onopen = this.websocketonopen; //用于指定连接成功后的回调函数
          this.websock.onerror = this.websocketonerror;//用于指定报错时的回调函数
          this.websock.onmessage = this.websocketonmessage;//用于指定收到服务器数据后的回调函数
          // this.websock.onclose = this.websocketclose
        }
      }
    },
    test() {
      console.log("每隔9秒钟执行一次");
      this.websock.send("心跳测试");
    },
    websocketonopen() {
      console.log("Websocket连接成功");
      this.dsq = setInterval(this.test, 9000);
      // console.log(this.dsq);
    },
    websocketonerror() {
      console.log("Websocket连接发生错误");
    },
    websocketonmessage(e) {
      // console.log("数据接收")
      // console.log(redata);
      // 数据接收
      const redata = JSON.parse(e.data);
      
      console.log(redata);
    },
    websocketclose() {
      // 关闭连接
      clearInterval(this.dsq);
      this.websock.close();
      console.log("断开连接");
    }
  }
};
