<template>
    <div class="set_content">
        <div class="wd_change">
            <div class="change_item">
                <div class="ci_left">
                <span>公司名称</span>
                </div>
                <div class="ci_right">
                    <span>{{form.comname}}</span>
                </div>
            </div>
            <div class="change_item">
                <div class="ci_left">
                <span>姓名</span>
                </div>
                <div class="ci_right">
                    <input v-model="form.empname"
                           type="text"
                           placeholder="请输入姓名" />
                </div>
            </div>
            <div class="change_item">
                <div class="ci_left">
                    <span>性别</span>
                </div>
                <div class="ci_right">
                    <input v-model="showSex"
                        readonly
                        @click="changeSex"
                        type="text"
                        placeholder="请选择性别" />
                    <md-selector v-model="isSelectorShow"
                                :data="sexlist"
                                :default-value="form.sex"
                                max-height="320px"
                                @choose="onSelectorChoose"></md-selector>
                    <!-- <input v-model="form.sex"
                           type="text"
                           placeholder="请输入性别" /> -->
                </div>
            </div>
            <div class="change_item solidTop">
                <div class="ci_left">
                <span>手机号</span>
                </div>
                <div class="ci_right">
                    <input v-model="form.phone"
                           type="text"
                           placeholder="请输入手机号" />
                </div>
            </div>
            <div class="change_item">
                <div class="ci_left">
                <span>邮箱</span>
                </div>
                <div class="ci_right">
                    <input v-model="form.email"
                           type="text"
                           placeholder="请输入邮箱" />
                </div>
            </div>
            <div class="change_item">
                <div class="ci_left">
                <span>地址</span>
                </div>
                <div class="ci_right">
                    <input v-model="form.zaddr"
                           type="text"
                           placeholder="请输入地址" />
                </div>
            </div>
            <div class="change_item">
                <div class="ci_left">
                <span>职位名称</span>
                </div>
                <div class="ci_right">
                    <input v-model="form.degreename"
                           type="text"
                           placeholder="请输入职位名称" />
                </div>
            </div>
            <div class="change_item">
                <div class="ci_left">
                <span>微信号码</span>
                </div>
                <div class="ci_right">
                    <input v-model="form.wxnumber"
                           type="text"
                           placeholder="请输入微信号码" />
                </div>
            </div>
            <div class="change_myMsg">
                <div class="ci_left">
                <span>自我介绍</span>
                </div>
                <div class="ci_right">
                    <textarea v-model="form.pdesc"></textarea>
                </div>
            </div>
        </div>
        <div class="btn" @click="submit">
            <div class="quitBtn">确定</div>
        </div>
    </div>
</template>
<script>
import { getPersonalData,updatePersonalData } from '@/api/basic/index'
import { Toast } from 'mand-mobile'
export default {
    data () {
        return {
            sexlist: [
                {
                value: '2',
                text: '男'
                },
                {
                value: '1',
                text: '女'
                },
                {
                value: '0',
                text: '未知性别'
                },
                {
                value: '9',
                text: '未说明性别'
                }
            ],
            isSelectorShow: false,
            showSex: '',
            form: {}
        }
    },
    created () {
        this.getData()
    },
    methods: {
        getData () {
            getPersonalData().then(res => {
                this.form = res.data.data
                this.sexlist.forEach((item, index) => {
                if (this.form.sex == item.value) {
                    this.showSex = item.text
                }
                })
            })
        },
        changeSex () {
            this.isSelectorShow = true
        },
        onSelectorChoose ({ value, text }) {
            this.form.sex = value
            this.showSex = text
            this.$forceUpdate()
        },
        submit () {
            updatePersonalData(this.form).then(res => {
                Toast.succeed('保存成功')
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
        .change_myMsg {
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
                // height 500px
                padding-top 50px
                textarea {
                    height: 400px;
                    width: 100%;
                    padding: 30px 40px;
                    outline: none;
                    font-size: 40px;
                    border: 1px solid #efefef;
                }
                // input {
                //     height 300px
                // }
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
</style>