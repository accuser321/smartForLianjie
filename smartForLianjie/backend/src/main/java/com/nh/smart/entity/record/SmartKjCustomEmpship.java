package com.nh.smart.entity.record;

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
 * smart_com_kj_custom_empship#客户与业务员关系表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName("smart_com_kj_custom_empship")
@ApiModel(value = "SmartComKjCustomEmpship对象", description = "smart_com_kj_custom_empship#客户与业务员关系表")
public class SmartKjCustomEmpship implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道编码")
    @TableId(value = "comid", type = IdType.INPUT)
    private String comid;

    @ApiModelProperty(value = "业务员工号")
    private String empno;

    @ApiModelProperty(value = "客户用户编号")
    private Integer userid;

    @ApiModelProperty(value = "是否同时关系")
    private String tsflag;

    @ApiModelProperty(value = "最近关系更新时间")
    private Date flushtime;

    @ApiModelProperty(value = "是否是爱保单客户")
    private String abdflag;

    @ApiModelProperty(value = "是否是NBS客户")
    private String nbsflag;

    @ApiModelProperty(value = "关系建立时间")
    private Date intime;

    @ApiModelProperty(value = "关系创建来源")
    private String fromsctype;

    @ApiModelProperty(value = "关系创建来源素材")
    private String fromsno;

    @ApiModelProperty(value = "是否准客户")
    private String zkhflag;

    @ApiModelProperty(value = "是否给业务员点赞：0没有点赞，1点赞")
    private String praiseflag;


}
