/*
 * @Author: 王鹏
 * @Date: 2019-09-10 16:36:46
 * @LastEditors: 刘格优
 * @LastEditTime: 2020-03-03 15:04:22
 */

import axios from "axios";
import router from "@/router";
import { Toast } from "mand-mobile";
import store from "@/store";
import { getStorage, setStorage, clearStorage } from "@/lib/util";
import { refresh } from "@/api/basic";
class HttpRequest {
  constructor(baseUrl) {
    this.baseUrl = baseUrl;
    this.queue = {};
    this.isRefresh = false;
  }
  getInsideConfig() {
    const config = {
      baseURL: this.baseUrl,
      headers: {}
    };
    return config;
  }
  destroy(url) {
    delete this.queue[url];
    if (!Object.keys(this.queue).length) {
      // Toast.hide()
    }
  }
  logOut() {
    let fromwhere = getStorage("fromwhere", "");
    if (fromwhere === "app") {
      router.push({ path: "/bdtg", query: { fromwhere: "app" } });
    }
    clearStorage();
    router.push({ path: "/" });
  }
  interceptors(instance, url) {
    // 请求拦截
    instance.interceptors.request.use(
      config => {
        // store.state.app.isloading = true
        const { expireTime, refreshTime, token } = getStorage("i_f", {});
        const now = Date.now();
        // 添加全局的loading...
        if (!Object.keys(this.queue).length) {
          // Toast.loading('加载中...', 0, true)
        }
        this.queue[url] = true;

        if (refreshTime < now && expireTime > now && !this.isRefresh) {
          this.isRefresh = true;
          refresh().then(res => {
            setStorage("i_f", res.data.data);
          });
        } else if (now > expireTime) {
          // this.logOut()
        }

        if (process.env.NODE_ENV === "production") {
          config.headers.Authorization = token;
        } else {
          config.headers.Authorization =
            //"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvZ1pnbndfSV84RjZRSEZVVHAzTEhldldqTlpjJk5IV1gmUyIsImV4cCI6MTU4MzIyMTMyOCwiY3JlYXRlZCI6MTU4MzIxODMyODI5NiwiaXAiOiJlNWRhODFhMDQxMzM5ODYyYzY4N2ViMzRmYWI1MTc4MCJ9.LUvhJzsQ274JeQEaeXHHUWq7tF-IE8dx9RoQyUtRtmo";
           //"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJveERBdTFicVBTWkU2cEZibVRhZGdXcTZSRHBzJk5IU1gmUyIsImV4cCI6MTU4NjUwMjMxOSwiY3JlYXRlZCI6MTU4NjQ5OTMxOTcyMywiaXAiOiJjZTI2NWU3NzIwYTFiMjRiMzExNDVmYzA4MjQ4ZGE2NiJ9.S2oJjAYeIn3AKAa3cf_lckjS5r4Oa2TOn6_rOSKSjhw";
            // "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvTWNfc3czQUNHdzhNRkRla0tJTGVDUVJ0OW4wJk5IV1gmUyIsImV4cCI6MTU4NjUxOTg4NSwiY3JlYXRlZCI6MTU4NjUxNjg4NTExNiwiaXAiOiJjZTI2NWU3NzIwYTFiMjRiMzExNDVmYzA4MjQ4ZGE2NiJ9.XcU7QPnfBxYsV9SX6_nN2bfBOQXSPsDLp0fuMSI14UU";
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvQzE2MjBfVjRjNEo1NEZKN05ZdjFqVl9EblBrJk5IU1gmUyIsImV4cCI6MTU4ODk5MzU5NywiY3JlYXRlZCI6MTU4ODk5MDU5NzI0NCwiaXAiOiJkYjU1NjQ5ZWZmOTM2ZGUwZDBiMWY0NDNlOWFiY2QyNyJ9.W4eL86UB6NM3AbD2lJT6Tj6N-cM0qHbgsCpKpoQvZq0";
          }
        return this.transfromData(config);
      },
      error => {
        return Promise.reject(error);
      }
    );
    // 响应拦截
    instance.interceptors.response.use(
      res => {
        store.state.app.isloading = false;
        this.destroy(url);
        const { data, status, headers, config } = res;
        const responseTypes = ["arraybuffer", "blob", "stream"];
        if (data.code && data.code !== 200) {
          /*
           * 如果是刷新token接口报401
           * */
          data.code == 401 ? this.logOut() : Toast.failed(data.msg);
          // eslint-disable-next-line prefer-promise-reject-errors
          return Promise.reject("logical error");
        } else if (responseTypes.includes(config.responseType)) {
          return { data, status, headers };
        } else {
          return { data, status };
        }
      },
      error => {
        // Toast.failed('网络异常,请检查......')

        this.destroy(url);

        let errorInfo = error.response;

        if (!errorInfo) {
          const {
            request: { statusText, status },
            config
          } = JSON.parse(JSON.stringify(error));
          errorInfo = {
            statusText,
            status,
            request: { responseURL: config.url }
          };
        }

        return Promise.reject(error);
      }
    );
  }
  transfromData(config) {
    let types = ["get", "delete"];
    if (types.includes(config.method) && config.params) {
      config.url = Object.keys(config.params).reduce(
        (acc, currt) => (acc += `/${config.params[currt]}`),
        config.url
      );
      config.params = null;
    }
    return config;
  }
  // async doRequest (error, token) {
  //   let result = await this.request().post('/weixin/isOverdueToken', { token })

  //   const { ...info } = getStorage('i_f', {})
  //   info.token = result.data.data.token
  //   setStorage('i_f', info)

  //   let config = error.response.config

  //   config.headers.Authorization = result.data.token

  //   return await this.request().request(config)
  // }
  request() {
    const instance = axios.create(this.getInsideConfig());
    this.interceptors(instance);
    return instance;
  }
}
export default HttpRequest;
