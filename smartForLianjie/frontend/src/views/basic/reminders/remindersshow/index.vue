<!--
/*
 * @Author: 李宗哲
 * @Date: 2020-01-14 16:17:12
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2020-01-08 18:38:35
 */
 -->
 <template>
  <div class="content">
    <div class="topbox">
      <div class="ttoopp">
        <div class="toptop"
             v-if="alerttype=='1'">
          <img src="@/assets/abt/img/zntx05.png"
               alt=""
               class="mainpic">
        </div>
        <!-- <div class="toptop"
             v-else-if="alerttype=='2'">

          <img src="@/assets/abt/img/zntx06.png"
               alt=""
               class="mainpic">
        </div> -->
        <div class="toptop"
             v-else-if="alerttype=='3'">
          <img src="@/assets/abt/img/zntx07.png"
               alt=""
               class="mainpic">
        </div>
        <div class="toptop"
             v-else-if="alerttype=='4'">
          <img src="@/assets/abt/img/zntx08.png"
               alt=""
               class="mainpic">
        </div>
        <div class="bottom">
          <!-- <span class="biaoti"
                v-if="alerttype=='2'">车险到期提醒</span> -->
          <span class="biaoti"
                v-if="alerttype=='1'">生日提醒</span>
          <span class="biaoti"
                v-if="alerttype=='3'">续费提醒</span>
          <span class="biaoti"
                v-if="alerttype=='4'">满期提醒</span>
          <br />
          <span class="biaotiriqi">{{pdate?pdate:'暂无'}}</span>
        </div>
      </div>
      <div class="tianshu"
           v-if="alerttype=='1'&&List.dayNumber>='0'">
        <div class="ssqqjjjj">
          <span class="aztdx">{{List.dayNumber}}</span>
          <span class="bztdx">天</span><br />
        </div>
        <span class="cztdx">后过生日</span>
      </div>
      <div class="tianshu"
           v-if="alerttype!='1'&&List.dayNumber>='0'">
        <div class="ssqqjjjj">
          <span class="aztdx">{{List.dayNumber}}</span>
          <span class="bztdx">天</span><br />
        </div>
        <span class="cztdx">后保险到期</span>
      </div>
    </div>
    <!-- 车险 -->
    <!-- <div class="contentbox"
         v-if="alerttype=='2'">
      <div class="xiangqing">
        <span class="zitidx">基本信息</span>
      </div>
      <div class="jibenxinxi">
        <div class="xinxi">
          <span class="zitiyshi">姓名：</span>
          <span class="zitiyshiaa">{{List.pname?List.pname:'暂无'}}</span><br />
          <span class="zitiyshi">车牌号：</span>
          <span class="zitiyshiaa">{{List.carno?List.carno:'暂无'}}</span><br />
          <span class="zitiyshi">品牌型号：</span>
          <span class="zitiyshiaa">{{List.ppno?List.ppno:'暂无'}}</span> <br />
          <span class="zitiyshi">联系电话：</span>
          <span class="zitiyshiaa">{{List.tel?List.tel:'暂无'}}</span>
        </div>
      </div>
      <div class="xiangqing">
        <span class="zitidx">车险</span>
      </div>
      <div class="jibenxinxi">
        <div class="xinxi">
          <span class="zitiyshi">保险公司：</span>
          <span class="zitiyshiaa">{{List.shortname?List.shortname:'暂无'}}</span><br />
          <span class="zitiyshi">保单号：</span>
          <span class="zitiyshiaa">{{List.policyno?List.policyno:'暂无'}}</span><br />
          <span class="zitiyshi">产品名称：</span>
          <span class="zitiyshiaa">{{List.classname?List.classname:'暂无'}}</span><br />
          <span class="zitiyshi">保费：</span>
          <span class="zitiyshiaa">{{List.mount?List.mount:'暂无'}}</span><br />
          <span class="zitiyshi">生效日期：</span>
          <span class="zitiyshiaa">{{List.sxdate?List.sxdate:'暂无'}}</span><br />
          <span class="zitiyshi">责任期间：</span>
          <span class="zitiyshiaa"
                v-if="List.begdate">{{List.begdate}}-{{List.enddate}}</span>
          <span class="zitiyshiaa"
                v-else>{{List.enddate}}到期</span>
        </div>
      </div>
    </div> -->
    <!-- 生日 -->
    <div class="contentbox"
         v-if="alerttype=='1'">
      <div class="xiangqing">
        <span class="zitidx">生日信息</span>
      </div>
      <div class="jibenxinxi">
        <div class="xinxi">
          <span class="zitiyshi">姓名：</span>
          <span class="zitiyshiaa">{{pname?pname:'暂无'}}</span><br />
          <span class="zitiyshi">出生日期：</span>
          <span class="zitiyshiaa">{{(birthdate &&birthdate != 'undefined')?birthdate:'暂无'}}</span><br />
          <span class="zitiyshi">年龄：</span>
          <span class="zitiyshiaa">{{List.age?List.age:'暂无'}}</span><br />
          <span class="zitiyshi">联系电话：</span>
          <span class="zitiyshiaa">{{(tel &&tel != 'undefined')?tel:'暂无'}}</span><br />
        </div>
      </div>
    </div>
    <!-- 保单 -->
    <div class="contentbox"
         v-if="alerttype=='3'||alerttype=='4'">
      <div class="xiangqing">
        <span class="zitidx">基本信息</span>
      </div>
      <div class="jibenxinxi">
        <div class="xinxi">
          <span class="zitiyshi">姓名：</span>
          <span class="zitiyshiaa">{{pname?pname:'暂无'}}</span><br />
          <span class="zitiyshi">联系电话：</span>
          <span class="zitiyshiaa">{{(tel &&tel != 'undefined')?tel:'暂无'}}</span>
        </div>
      </div>
      <div class="xiangqing">
        <span class="zitidx">保单信息</span>
      </div>
      <div class="jibenxinxi">
        <div class="xinxi">
          <span class="zitiyshi">保险公司：</span>
          <span class="zitiyshiaa">{{Data.basename?Data.basename:'暂无'}}</span><br />
          <span class="zitiyshi">保单号：</span>
          <span class="zitiyshiaa">{{Data.policyno?Data.policyno:'暂无'}}</span><br />
          <div v-for="(item,index) in Listdata "
               :key="index">
            <span class="zitiyshi">险种名称：</span>
            <span class="zitiyshiaa">{{(Listdata[index].classname == ''||Listdata[index].classname == 'undefined')?'暂无':Listdata[index].classname}}</span><br />
            <span class="zitiyshi">缴费年限：</span>
            <span class="zitiyshiaa">{{(Listdata[index].yearnum == ''||Listdata[index].yearnum == 'undefined')?'暂无':Listdata[index].yearnum}}</span><br />
            <span class="zitiyshi">保费：</span>
            <span class="zitiyshiaa">{{(Listdata[index].mount == ''||Listdata[index].mount == 'undefined')?'暂无':Listdata[index].mount}}</span><br />
          </div>
          <span class="zitiyshi">投保日期：</span>
          <span class="zitiyshiaa">{{Data.appdate ?Data.appdate:'暂无'}}</span><br />
          <span class="zitiyshi">生效日期：</span>
          <span class="zitiyshiaa">{{Data.sxdate?Data.sxdate:'暂无'}}</span><br />
          <span class="zitiyshi">承保日期：</span>
          <span class="zitiyshiaa">{{Data.cbdate ?Data.cbdate:'暂无'}}</span><br />
          <span class="zitiyshi">保单状态：</span>
          <span class="zitiyshiaa">{{Data.polistname?Data.polistname:'暂无'}}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {  selectzntxXQ
} from '@/api/abt/customerOperation/zhinengtx/index'

export default {
  data () {
    return {
      diffDate: '',
      Listdata: [],
      Data: [],
      List: [],
      alerttype: '',
      pdate: '',
      pname: '',
      policyno: '',
      tel: '',
      birthdate: ''
    }
  },
  components: {

  },
  created () {
    this.alerttype = this.$route.query.alerttype,
      this.pdate = this.$route.query.pdate,
      this.pname = this.$route.query.pname,
      this.policyno = this.$route.query.policyno,
      this.tel = this.$route.query.tel,
      this.birthdate = this.$route.query.birthdate,
      this.getList()
  },
  mounted () {

  },
  methods: {
    getList () { //获取智能提醒列表详情页
      let data = {
        type: this.alerttype, //类型
        pdate: (this.pdate == 'undefined') ? '' : this.pdate, //投保日期
        pname: (this.pname == 'undefined') ? '' : this.pname, //姓名
        policyno: (this.policyno == 'undefined') ? '' : this.policyno, //保单号
        birthdate: (this.birthdate == 'undefined') ? '' : this.birthdate  //生日
      }
      // console.log("智能提醒");
      // console.log(data);
      selectzntxXQ(data).then(res => { 
        this.List = res.data.data
        this.Data = res.data.data.riskconList
        for (var a in res.data.data.classlist) {
          this.Listdata.push(res.data.data.classlist[a])
        }
      })
    },
  }
}
</script>

<style scoped>
.content {
  width: 100%;
  height: 100%;
  position: relative;
}
.topbox {
  border: 2px solid #a1a1a1;
  padding: 10px 40px;
  background-image: linear-gradient(to right, #fc737b, #cd2b2f);
  height: 3.3rem;
  width: 94%;
  border-radius: 0.2rem;
  display: flex;
  justify-content: space-between;
  margin: 0 auto;
  top: 0.5rem;
  margin-bottom: 1rem;
  position: relative;
}
.toptop {
  display: flex;
  align-items: center;
  margin: 0 0.4rem 0 0;
  width: 35%;
}
.ttoopp {
  display: flex;
  align-items: center;
  width: 65%;
}
.mainpic {
  height: 60%;
  width: 100%;
}
.biaoti {
  color: rgb(255, 253, 253);
  line-height: 1rem;
}
.biaotiriqi {
  color: rgb(255, 253, 253);
  font-size: 0.3rem;
  line-height: 0.8rem;
}
.bottom {
  align-items: center;
}
.zitidx {
  font-size: 0.45rem;
  font-weight: 600;
  margin: 0 0 0 0.5rem;
}
.jibenxinxi {
  display: flex;
  align-items: center;
}
.xinxi {
  margin: 0.3rem 0.5rem 0.3rem 0.8rem;
  line-height: 1rem;
}
.zitiyshi {
  font-size: 0.35rem;
  color: #949494;
  display: inline-block;
  width: 2rem;
  text-align: left;
}
.zitiyshiaa {
  width: 2rem;
  font-size: 0.35rem;
  color: #949494;
  text-align: left;
}
.tianshu {
  text-align: center;
  display: flex;
  width: 30%;
  color: white;
  flex-direction: column;
  justify-content: center;
}
.aztdx {
  font-size: 0.9rem;
}
.bztdx {
  font-size: 0.3rem;
}
.cztdx {
  font-size: 0.3rem;
}
.ssqqjjjj {
}
</style>
