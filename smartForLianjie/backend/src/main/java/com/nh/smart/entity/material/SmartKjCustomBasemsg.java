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
import java.util.List;

/**
 * smart_com_kj_custom_basemsg#渠道业务员客户信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName("smart_com_kj_custom_basemsg")
@ApiModel(value = "SmartComKjCustomBasemsg对象", description = "smart_com_kj_custom_basemsg#渠道业务员客户信息")
public class SmartKjCustomBasemsg implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道编号")
    @TableId(value = "comid", type = IdType.INPUT)
    private String comid;

    @ApiModelProperty(value = "业务员工号")
    private String empno;

    @ApiModelProperty(value = "客户编号")
    private Integer userid;

    @ApiModelProperty(value = "客户姓名")
    private String name;

    @ApiModelProperty(value = "客户性别")
    private String sex;

    @ApiModelProperty(value = "客户手机号")
    private String mobile;

    @ApiModelProperty(value = "客户证件类型")
    private String cardtype;

    @ApiModelProperty(value = "客户证件号码")
    private String cardno;

    @ApiModelProperty(value = "客户生日")
    private Date birthdate;

    @ApiModelProperty(value = "客户婚姻状况")
    private String ismarried;

    @ApiModelProperty(value = "客户孩子数量")
    private Integer childnum;

    @ApiModelProperty(value = "客户工作单位")
    private String company;

    @ApiModelProperty(value = "家庭地址")
    private String address;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "微信头像")
    @TableField(exist = false)
    private String headimg;

    @ApiModelProperty(value = "建立客户关系时间")
    @TableField(exist = false)
    private Date intime;

    @ApiModelProperty(value = "建立客户关系天数")
    @TableField(exist = false)
    private Long day;

    @ApiModelProperty(value = "客户标签")
    @TableField(exist = false)
    private List<SmartComKjEmpnoLabel> labelList;

}
