package com.nh.smart.entity.material;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * smart_com_empno#渠道业务员表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName("smart_com_empno")
@ApiModel(value = "SmartComEmpno对象", description = "smart_com_empno#渠道业务员表")
public class SmartComEmpno implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道编码")
    @TableId(value = "comid", type = IdType.INPUT)
    private String comid;

    @ApiModelProperty(value = "机构代码")
    private String tjcode;

    @ApiModelProperty(value = "业务员工号")
    private String empno;

    @ApiModelProperty(value = "业务员姓名")
    private String empname;

    @ApiModelProperty(value = "状态:0在职，1离职，2解约，6待激活，8禁止新保")
    private String status;

    @ApiModelProperty(value = "证件类型")
    private String cardtype;

    @ApiModelProperty(value = "证件号码")
    private String cardnum;

    @ApiModelProperty(value = "登录密码")
    private String passwd;

    @ApiModelProperty(value = "微信openid")
    private String empopenid;

    @ApiModelProperty(value = "微信昵称")
    private String empnickname;

    @ApiModelProperty(value = "微信头像")
    private String empheadimg;

    @ApiModelProperty(value = "最后一次登录时间")
    private LocalDateTime lastlogintime;

    @ApiModelProperty(value = "入职时间")
    private Date indate;

    @ApiModelProperty(value = "离职时间")
    private Date outdate;

    @ApiModelProperty(value = "最新职级")
    private String degreeno;

    @ApiModelProperty(value = "推荐人")
    private String tjempno;

    @ApiModelProperty(value = "上级主管")
    private String upempno;

    @ApiModelProperty(value = "操作人")
    private String operno;

    @ApiModelProperty(value = "操作时间")
    private Date optime;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "国籍")
    private String nality;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "学历")
    private String educational;

    @ApiModelProperty(value = "出生日期")
    private Date birth;

    @ApiModelProperty(value = "开户银行")
    private String bankcode;

    @ApiModelProperty(value = "开户支行")
    private String bankzh;

    @ApiModelProperty(value = "银行号码")
    private String bankno;

    @ApiModelProperty(value = "居住地址")
    private String zaddr;

    @ApiModelProperty(value = "业务员电话")
    private String phone;

    @ApiModelProperty(value = "职级名称")
    private String degreename;

    @ApiModelProperty(value = "系统来源")
    private String fromtype;
}
