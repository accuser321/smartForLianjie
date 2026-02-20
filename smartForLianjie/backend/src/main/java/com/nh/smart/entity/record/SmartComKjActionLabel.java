package com.nh.smart.entity.record;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nh.smart.entity.vo.TreeVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * smart_com_kj_action_label#渠道客户内容标签行为关系
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName("smart_com_kj_action_label")
@ApiModel(value = "SmartComKjActionLabel对象", description = "smart_com_kj_action_label#渠道客户内容标签行为关系")
public class SmartComKjActionLabel extends TreeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道编码")
    @TableId(value = "comid", type = IdType.INPUT)
    private String comid;

    @ApiModelProperty(value = "用户编号")
    private Integer userid;

    @ApiModelProperty(value = "上级传播人用户编号")
    private Integer suserid;

    @ApiModelProperty(value = "原始传播业务员工号")
    private Integer empno;

    @ApiModelProperty(value = "标签编号")
    private String labid;

    @ApiModelProperty(value = "最新更新时间")
    private Date lastuptime;

    @ApiModelProperty(value = "标签次数")
    private Integer num;

    @ApiModelProperty(value = "标签创建来源")
    private String fromsctype;

    @ApiModelProperty(value = "标签来源素材编号")
    private String fromsno;

    @ApiModelProperty(value = "客户名称")
    @TableField(exist = false)
    private String khname;

    @ApiModelProperty(value = "客户头像")
    @TableField(exist = false)
    private String headimg;

}
