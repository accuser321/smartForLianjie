<template>
    <div class="set_content">
        <div class="wd_change">
            <div class="change_item">
                <div class="ci_left">
                <span>执业证编号</span>
                </div>
                <div class="ci_right">
                    <span>{{form.certno}}</span>
                </div>
            </div>
            <div class="change_item">
                <div class="ci_left">
                <span>有效起期</span>
                </div>
                <div class="ci_right">
                    <span>{{form.begdate}}</span>
                </div>
            </div>
            <div class="change_item">
                <div class="ci_left">
                <span>有效终期</span>
                </div>
                <div class="ci_right">
                    <span>{{form.enddate}}</span>
                </div>
            </div>
            <!-- <div class="change_pic">
                <div class="ci_left">
                    <span>银行卡影像</span>
                </div>
                <simple-cropper :initParam="uploadParam"
                                :successCallback="uploadHandle"
                                :successBank="successSeeBank"
                                ref="cropper"
                                class="box">
                    <div class="p_pic" @click="upload('cropper')">
                        <img v-if="form.souhttp != ''" :src="ossurl+form.souhttp" alt="" class="mainpic" />
                        <img v-else src="@/assets/image/w_yhk.png" class="mainpic"/>
                    </div>
                </simple-cropper>
            </div> -->
        </div>
        <!-- <div class="btn" @click="submit">
            <div class="quitBtn">确定</div>
        </div> -->
    </div>
</template>
<script>
import { 
    getZYZMessage } from '@/api/basic/index'
// import simpleCropper from './component/simpleCropper'
// import { getStorage } from '@/lib/util'
// import { Toast } from 'mand-mobile'
export default {
    // components: {
    //     simpleCropper
    // },
    data () {
        return {
            empno: '',
            form: {}
            // ossurl: ''
        }
    },
    created (){
        // this.ossurl = getStorage('ossurl', '')
        this.empno = this.$route.query.empno
        this.getData()
    },
    methods: {
        getData () {
            getZYZMessage({empno: this.empno}).then(res => {
                this.form = res.data.data
            })
        }
    }
}
</script>
<style lang="stylus" scoped>
.set_content {
    background-color #F6F6F6
    .wd_change {
        background-color #FFFEFF
        .change_item {
            display flex
            justify-content space-between
            border-bottom 1px solid #F6F6F6
            padding 50px 60px
            .ci_left {
                span {
                    font-size:45px;
                    font-family:PingFang SC;
                    font-weight:500;
                    color:rgba(56,56,56,1);
                }
            }
            .ci_right {
                input {
                    overflow: hidden;
                    font-size: 40px;
                    border: none;
                    text-align: right;
                    outline: none;
                }
            }
        }
        .change_pic {
            padding 50px 60px
            .ci_left {
                padding-bottom 50px
                span {
                    font-size:45px;
                    font-family:PingFang SC;
                    font-weight:500;
                    color:rgba(56,56,56,1);
                }
            }

        }

    }
    .btn {
        text-align center
        padding-top 200px
        .quitBtn {
            padding: 30px 30px;
            margin: 80px auto;
            width: 90%;
            background-color: #BA1C21;
            font-size:40px;
            font-family:PingFang SC;
            font-weight:500;
            color:rgba(255,255,255,1);
            border-radius 10px
        }
    }
}
.solidTop {
  border-top 50px solid #F6F6F6
}
.mainpic {
  width 100%
  height auto
}
</style>