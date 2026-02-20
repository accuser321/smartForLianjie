package com.nh.smart.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @ClassName: 枚举类VO视图对象
 * @Description: 下拉框共用组建
 * @Author Demo
 * @DateTime 2020年4月7日 下午2:31:54
 */
@Data					//给实体类的属性加上get(),set()方法
@ToString
@AllArgsConstructor		//使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
@NoArgsConstructor		//使用后创建一个无参构造函数
@Builder
@EqualsAndHashCode(callSuper=false)
public class EnumVo  implements Serializable{

	private static final long serialVersionUID = 1L;

	private String label;

    private List<Map<String,String>> value;
}
