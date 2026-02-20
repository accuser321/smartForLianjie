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
 * smart_com_kj_action_empcount#渠道业务员每日行为统计
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName("smart_com_kj_action_empcount")
@ApiModel(value = "SmartComKjActionEmpcount对象", description = "smart_com_kj_action_empcount#渠道业务员每日行为统计")
public class SmartComKjActionEmpcount implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道编码")
    @TableId(value = "comid", type = IdType.INPUT)
    private String comid;

    @ApiModelProperty(value = "业务员工号")
    private String empno;

    @ApiModelProperty(value = "统计日期")
    private Date tjdate;

    @ApiModelProperty(value = "新增获客数")
    private Integer xzkhnum;

    @ApiModelProperty(value = "文章转发数")
    private Integer wzzfnum;

    @ApiModelProperty(value = "文章浏览数")
    private Integer wzllnum;

    @ApiModelProperty(value = "名片转发")
    private Integer mpzfnum;

    @ApiModelProperty(value = "名片浏览")
    private Integer mpllnum;

    @ApiModelProperty(value = "名片被点赞")
    private Integer mpdznum;

    @ApiModelProperty(value = "被咨询数量")
    private Integer zxnum;


}
