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
 * smart_com_kj_action_count#渠道业务员行为次数表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName("smart_com_kj_action_count")
@ApiModel(value = "SmartComKjActionCount对象", description = "smart_com_kj_action_count#渠道业务员行为次数表")
public class SmartComKjActionCount implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道编码")
    @TableId(value = "comid", type = IdType.INPUT)
    private String comid;

    @ApiModelProperty(value = "业务员工号")
    private String empno;

    @ApiModelProperty(value = "客户编号")
    private Integer userid;

    @ApiModelProperty(value = "计数类型")
    private String otype;

    @ApiModelProperty(value = "素材类型编码")
    private String btagcode;

    @ApiModelProperty(value = "次数")
    private Integer num;

    @ApiModelProperty(value = "最新数据时间")
    private Date lasttime;


}
