<!--
 * @Author: 刘格优
 * @Date: 2020-02-19 16:35:14
 * @LastEditors: 刘格优
 * @LastEditTime: 2020-03-20 09:45:08
 -->
<template>
  <div>
    <div class="top">
      <p class="topword">联系方式</p>
      <p class="descword">您有任何使用或者保险咨询的问题</p>
      <p class="descword">都可以随时与我们联系。</p>
    </div>
    <div class="bottom">
      <div class="bottomtop">
        <img :src="ossurl+kfimg"
             alt="">
        <p class="kfword">
          客服微信二维码
        </p>
      </div>
      <div class="bottomword">
        <img src="@/assets/nh/tel.png"
             alt="">
        <a :href="'tel:' + kfphone">
          <span>{{kfphone}}</span>
        </a>
      </div>
    </div>
  </div>
</template>

<script>
import { getStorage } from '@/lib/util'
export default {
  data () {
    return {
      kfimg: '',
      kfphone: '',
      ossurl: ''
    }
  },
  created () {
    this.ossurl = getStorage('ossurl', '')
    let kfinfo = getStorage('kfInfo', '')
    for (let val in kfinfo) {
      if (val == 'ketel') {
        this.kfphone = kfinfo['ketel']
      } else {
        this.kfimg = kfinfo['kfqrcode']
      }
    }
  },
  components: {},

  computed: {},

  methods: {}
}

</script>
<style scoped lang="stylus">
.topword {
  font-weight: bold;
  color: #11111D;
  font-size: 72px;
  padding: 1rem 0 0.6rem 0.8rem;
}

.descword {
  color: #8E8E8E;
  font-size: 0.4rem;
  padding-left: 0.8rem;
  line-height: 0.6rem;
}

.bottom {
  width: 80%;
  margin: 1rem auto 0;
  padding-top: 0.5rem;
  box-shadow: 0.01rem 0.01rem 0.5rem 0.1rem rgba(0, 0, 0, 0.05);

  .bottomtop {
    // position: relative;
    // top: 30%;
    width: 5.5rem;
    margin: 1rem auto;
    height: 5.5rem;
    background-image: url('~@/assets/nh/kfbg.png');
    background-size: 100% 100%;
    background-repeat: no-repeat;
    text-align: center;

    img {
      width: 4rem;
      height: 4rem;
      margin-top: 0.3rem;
    }

    p {
      text-align: center;
    }
  }

  .bottomword {
    text-align: center;
    border-top: 1px dashed #EFEFEF;
    padding: 1.2rem;

    img {
      width: 0.6rem;
      height: auto;
    }

    span {
      color: #6b4652;
      font-size: 0.4rem;
      bottom: 0.15rem;
      position: relative;
      margin-left: 0.2rem;
    }
  }
}

.kfword {
  color: #8e8e8e;
}
</style>
