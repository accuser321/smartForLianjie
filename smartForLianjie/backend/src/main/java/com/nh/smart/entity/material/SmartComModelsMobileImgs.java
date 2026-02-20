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
 * smart_com_models_mobile_imgs#渠道图片设置表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName("smart_com_models_mobile_imgs")
@ApiModel(value = "SmarttComModelsMobileImgs对象", description = "smart_com_models_mobile_imgs#渠道图片设置表")
public class SmartComModelsMobileImgs implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道编码")
    @TableId(value = "comid", type = IdType.INPUT)
    private String comid;

    @ApiModelProperty(value = "图片编码")
    private String imgcode;

    @ApiModelProperty(value = "使用名称")
    private String imgname;

    @ApiModelProperty(value = "使用描述")
    private String imgdesc;

    @ApiModelProperty(value = "尺寸要求")
    private String imgsize;

    @ApiModelProperty(value = "图片地址")
    private String imgurl;

    @ApiModelProperty(value = "操作人")
    private String operno;

    @ApiModelProperty(value = "操作时间")
    private Date optime;


}
