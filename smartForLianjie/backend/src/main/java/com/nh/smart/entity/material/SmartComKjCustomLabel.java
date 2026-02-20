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
 * smart_com_kj_custom_label#渠道客户标签表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName("smart_com_kj_custom_label")
@ApiModel(value = "SmartComKjCustomLabel对象", description = "smart_com_kj_custom_label#渠道客户标签表")
public class SmartComKjCustomLabel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道编码")
    @TableId(value = "comid", type = IdType.INPUT)
    private String comid;

    @ApiModelProperty(value = "业务员工号")
    private String empno;

    @ApiModelProperty(value = "客户用户编号")
    private Integer userid;

    @ApiModelProperty(value = "标签编号")
    private String labid;

    @ApiModelProperty(value = "标签创建时间")
    private Date intime;

    @ApiModelProperty(value = "标签分类")
    private String labtype;

    @ApiModelProperty(value = "标签名称")
    @TableField(exist = false)
    private String labname;

}
