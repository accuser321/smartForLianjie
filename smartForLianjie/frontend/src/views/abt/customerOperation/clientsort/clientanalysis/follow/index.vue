<!--
 * @Author: 刘格优
 * @Date: 2019-11-14 15:14:54
 * @LastEditors: 刘格优
 * @LastEditTime: 2019-11-19 21:12:48
 -->

<template>
  <div id="content">
    <div>
      <div v-for="(item, key, index) in floList" :key="index">
        <div class="time">{{ key }}</div>
        <div class="inbox" v-for="(ite, index) in item" :key="index">
          <p class="conte">{{ ite.content }}</p>
          <md-icon
            name="delete"
            size="lg"
            class="deletebtn"
            @click="del(ite.id)"
          ></md-icon>
        </div>
      </div>
    </div>
    <div
      class="read"
      @click="
        readgo = true;
        textValue = '';
      "
    >
      <img src="@/assets/abt/img/read.png" alt />
    </div>
    <md-popup v-model="readgo" position="bottom" :mask-closable="false">
      <md-popup-title-bar
        title="添加信息"
        ok-text="确定"
        cancel-text="取消"
        large-radius
        @confirm="tochoose()"
        @cancel="hidePopUp()"
      ></md-popup-title-bar>
      <div class="md-example-popup md-example-popup-bottom">
        <div class="list">
          <div class="textarea">
            <textarea v-model="textValue" placeholder="记录美好时刻"></textarea>
          </div>
        </div>
      </div>
    </md-popup>
  </div>
</template>

<script>
import {
  getFollowList,
  insertFollow,
  deleteFollow
} from '@/api/abt/customerOperation/clientsort/index'
import { Toast } from 'mand-mobile'
export default {
  props: {
    userid: String
  },
  data () {
    return {
      readgo: false,
      textValue: '',
      size: 10,
      page: 1,
      floList: []
    }
  },
  mounted () {
    $(document).on('focusout', function () {
      setTimeout(() => {
        // 软键盘收起的事件处理
        window.scroll(0, 0)
      }, 300)
    })
    this.getlist()
  },
  components: {},

  computed: {},

  methods: {
    getlist () { //跟进列表
      getFollowList({
        page: this.page,
        size: this.size,
        userid: this.userid
      }).then(res => {
        this.floList = res.data.data.rows
      })
    },
    del (delid) {
      console.log(delid)
      deleteFollow({ id: delid }).then(res => {
        Toast.info('删除成功')
        this.getlist()
      })
    },
    tochoose () {
      insertFollow({
        userid: this.userid,
        content: this.textValue
      }).then(res => {
        Toast.info('添加成功')
        this.readgo = false
        this.getlist()
      })
    },
    hidePopUp () {
      this.readgo = false
    }
  }
}
</script>
<style scoped lang="stylus">
#content{
  padding-bottom 200px
  /deep/ .md-popup-box{
    height 50%!important;
    background-color:white
  }
}
.textarea {
  position: relative;
  height: 6rem;
  margin: 0.5rem 0 0.1rem;
}
textarea {
  width: 90%;
  border: 1px dashed #ccc;
  height: 100%;
  padding: 0.2rem;
  font-size .4rem;
  margin-left 5%
}
.read{
  width: 100%;
  box-sizing: border-box;
  position: fixed;
  bottom: .4rem;
  text-align: center;
  img{
    width 4.5rem;
    height auto
  }
}
.inbox {
  width: 90%;
  box-shadow: 0.15rem 0.15rem 0.3rem #dedede;
  margin-bottom: 0.6rem;
  border-radius: 0.3rem;
  overflow: hidden;
  padding: 0.3rem;
  padding-bottom: 0.1rem;
  margin-left: 5%;
  /deep/ .md-icon.icon-font.lg{
    float right
    font-size 1rem;
    color:color-primary
  }
}
.conte {
  display: inline-block;
  width: 90%;
  margin-left:15px;
  min-height: 1rem;
  font-size: .38rem;
}
.textimg{
  width: 12vw;
  float: right;
  padding-right: 5vw;
}
.time {
    font-size: 0.45rem;
    font-weight: 600;
    margin: .5rem .4rem;
}
</style>
