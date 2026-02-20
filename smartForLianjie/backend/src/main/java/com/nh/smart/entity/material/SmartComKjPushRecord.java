package com.nh.smart.entity.material;

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
import java.util.Date;

/**
 * smart_com_kj_push_record#推送记录表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName("smart_com_kj_push_record")
@ApiModel(value = "SmartComKjPushRecord对象", description = "smart_com_kj_push_record#推送记录表")
public class SmartComKjPushRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道编码")
    @TableId(value = "comid", type = IdType.INPUT)
    private String comid;

    @ApiModelProperty(value = "推送编号")
    private String pushid;

    @ApiModelProperty(value = "推送接收人用户编号")
    private Integer userid;

    @ApiModelProperty(value = "推送接收人openid")
    private String openid;

    @ApiModelProperty(value = "推送接收人工号")
    private String empno;

    @ApiModelProperty(value = "推送接收人类型")
    private String emptype;

    @ApiModelProperty(value = "推送状态：20发送成功，21发送失败")
    private String pushstatus;

    @ApiModelProperty(value = "推送时间")
    private Date pushtime;

    @ApiModelProperty(value = "阅读状态")
    private String readstatus;

    @ApiModelProperty(value = "推送数量")
    private String tsnum;

    @ApiModelProperty(value = "操作人部分")
    @TableField(exist = false)
    private String deptname;

    @ApiModelProperty(value = "阅读时间")
    private Date readtime;
    @ApiModelProperty(value = "ptitle")
    @TableField(exist = false)
    private String ptitle;

    @ApiModelProperty(value = "pdesc")
    @TableField(exist = false)
    private String pdesc;

    @ApiModelProperty(value = "pcontent")
    @TableField(exist = false)
    private String pcontent;

    @ApiModelProperty(value = "pimgurl")
    @TableField(exist = false)
    private String pimgurl;

    @ApiModelProperty(value = "phttpurl")
    @TableField(exist = false)
    private String phttpurl;

    @ApiModelProperty(value = "stagcode")
    @TableField(exist = false)
    private String stagcode;

}
