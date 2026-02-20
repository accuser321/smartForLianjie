package com.nh.smart.entity.record;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
    @TableName("smart_com_muser")
@ApiModel(value = "SmartComMuser对象", description = "smart_com_muser#渠道手机用户表")
public class SmartComMuser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道编码")

    private String comid;

    @ApiModelProperty(value = "用户编号，10000001开始计数")
    @TableId(value = "userid", type = IdType.INPUT)
    private Integer userid;

    @ApiModelProperty(value = "微信openid")
    private String openid;

    @ApiModelProperty(value = "微信昵称")
    private String nickname;

    @ApiModelProperty(value = "微信头像")
    private String headimg;

    @ApiModelProperty(value = "工号")
    private String empno;

    @ApiModelProperty(value = "人员类型标志:N内勤，Y游客")
    private String rytype;

    @ApiModelProperty(value = "首次注册时间")
    private Date regtime;

    @ApiModelProperty(value = "绑定时间")
    private Date bdtime;

    @ApiModelProperty(value = "最后一次登录时间")
    private Date lastlogintime;

    @ApiModelProperty(value = "状态：0可用，1禁用")
    private String status;

    @ApiModelProperty(value = "用户活跃度")
    private Integer acvitivy;

    @ApiModelProperty(value = "客户来源：客户首次进入系统的来源")
    private String fromempno;

    @ApiModelProperty(value = "客户来源素材类型")
    private String fromsctype;

    @ApiModelProperty(value = "客户来源素材编号")
    private String fromsno;

    @TableField(exist = false)
    private String num;

    @ApiModelProperty(value = "客户所在省")
    private String  custprovince;

    @ApiModelProperty(value = "客户所在市")
    private String custcity;

    @ApiModelProperty(value = "客户性别")
    private String custsex;

    @ApiModelProperty(value = "业务员姓名")
    @TableField(exist = false)
    private String empname;

    @ApiModelProperty(value = "业务员手机号")
    @TableField(exist = false)
    private String empphone;

    @ApiModelProperty(value = "验证码生成时间")
    private Date yzmtime;

    @ApiModelProperty(value = "验证码")
    private String yzm;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "是否展示推广费，0展示，1不展示")
    private String promotionprice;

    @ApiModelProperty(value = "规模保费")
    @TableField(exist = false)
    private BigDecimal mount;

    @ApiModelProperty(value = "奖励")
    @TableField(exist = false)
    private BigDecimal jl;

}
