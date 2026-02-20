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
import java.util.List;
import java.util.Map;

/**
 * smart_com_kj_action_record#渠道业务员行为记录表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName("smart_com_kj_action_record")
@ApiModel(value = "SmartComKjActionRecord对象", description = "smart_com_kj_action_record#渠道业务员行为记录表")
public class SmartComKjActionRecord extends TreeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "行为记录id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "渠道编码")
    private String comid;

    @ApiModelProperty(value = "查看人用户编号")
    private Integer userid;

    @ApiModelProperty(value = "上级来源人用户编号")
    private Integer suserid;

    @ApiModelProperty(value = "最初来源业务员工号")
    private String empno;

    @ApiModelProperty(value = "来源分类：转发、浏览")
    private String otype;

    @ApiModelProperty(value = "素材类型编码")
    private String btagcode;

    @ApiModelProperty(value = "素材分类")
    private String stagcode;

    @ApiModelProperty(value = "素材内容编号")
    private String sno;

    @ApiModelProperty(value = "原始素材内容编号")
    private String osno;

    @ApiModelProperty(value = "浏览时长")
    private Integer acttimes;

    @ApiModelProperty(value = "开始浏览时间")
    private Date begtime;

    @ApiModelProperty(value = "hash编号")
    private String hashnum;

    @ApiModelProperty(value = "标签数组")
    @TableField(exist = false)
    private List<String> labidlist;

    @ApiModelProperty(value = "素材标题")
    @TableField(exist = false)
    private String stitle;

    @ApiModelProperty(value = "最佳阅读时间")
    @TableField(exist = false)
    private Long basttimes;

    @ApiModelProperty(value = "微信头像")
    @TableField(exist = false)
    private String headimg;

    @ApiModelProperty(value = "客户名称")
    @TableField(exist = false)
    private String khname;

    @ApiModelProperty(value = "最佳阅读时间")
    @TableField(exist = false)
    private String basttime;

    @ApiModelProperty(value = "浏览时长")
    @TableField(exist = false)
    private String acttime;

    @ApiModelProperty(value = "客户类型")
    @TableField(exist = false)
    private String rytype;

    @ApiModelProperty(value = "展示素材")
    @TableField(exist = false)
    private Map<String,Object> scmap;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "产品名称")
    @TableField(exist = false)
    private String cpname;

}
