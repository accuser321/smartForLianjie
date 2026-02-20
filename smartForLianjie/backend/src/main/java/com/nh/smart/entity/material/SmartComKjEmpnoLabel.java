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
import java.util.Date;

/**
 * smart_com_kj_empno_label#渠道业务员自定义标签表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName("smart_com_kj_empno_label")
@ApiModel(value = "SmartComKjEmpnoLabel对象", description = "smart_com_kj_empno_label#渠道业务员自定义标签表")
public class SmartComKjEmpnoLabel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道编码")
    @TableId(value = "comid", type = IdType.INPUT)
    private String comid;

    @ApiModelProperty(value = "业务员工号")
    private String empno;

    @ApiModelProperty(value = "便签编码")
    private String labcode;

    @ApiModelProperty(value = "便签名称")
    private String labname;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "添加时间")
    private Date intime;


}
