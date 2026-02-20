package com.nh.smart.constant;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @ClassName: ResponseResult
 * @Description: 返回封装类
 * @Author Demo
 * @DateTime 2017年11月22日 上午10:15:04
 */
public class ResponseResult implements Serializable {

	private static final long serialVersionUID = -4571635145573807674L;

	// 定义jackson对象
	private static final ObjectMapper MAPPER = new ObjectMapper();

	// 响应业务状态
	private Integer status;

	// 响应消息
	private String msg;

	// 响应中的数据
	private Object data;

	// 响应头
	private Map<String, String> header = new HashMap<String, String>();

	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}

	public static ResponseResult build(Integer status, String msg, Object data) {
		return new ResponseResult(status, msg, data);
	}

	/**
	 * 
	 * @Title: ok
	 * @Description: 正常返回时使用
	 * @Author Demo
	 * @DateTime 2017年11月28日 下午3:11:11
	 * @param data
	 * @return
	 */
	public static ResponseResult ok(Object data) {
		return new ResponseResult(data);
	}

	public static ResponseResult ok() {
		return new ResponseResult(null);
	}

	public ResponseResult() {

	}

	/**
	 * 
	 * @Title: build
	 * @Description: 抛出异常时使用
	 * @Author Demo
	 * @DateTime 2017年11月28日 下午3:11:29
	 * @param status 错误码
	 * @param msg    异常信息
	 * @return
	 */
	public static ResponseResult build(Integer status, String msg) {
		return new ResponseResult(status, msg, null);
	}

	public ResponseResult(Integer status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public ResponseResult(Object data) {
		this.status = 200;
		this.msg = "OK";
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 
	 * @Title: formatToPojo
	 * @Description: 将json结果集转化为ResponseResult对象
	 * @Author Demo
	 * @DateTime 2017年11月28日 下午3:11:29
	 * @param jsonData json数据
	 * @param clazz    ResponseResult中的object类型
	 * @return
	 */
	public static ResponseResult formatToPojo(String jsonData, Class<?> clazz) {
		try {
			if (clazz == null) {
				return MAPPER.readValue(jsonData, ResponseResult.class);
			}
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			Object obj = null;
			if (clazz != null) {
				if (data.isObject()) {
					obj = MAPPER.readValue(data.traverse(), clazz);
				} else if (data.isTextual()) {
					obj = MAPPER.readValue(data.asText(), clazz);
				}
			}
			return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @Title: format
	 * @Description: 没有object对象的转化
	 * @Author Demo
	 * @DateTime 2017年11月28日 下午3:11:29
	 * @param json
	 * @return
	 */
	public static ResponseResult format(String json) {
		try {
			return MAPPER.readValue(json, ResponseResult.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Title: formatToList
	 * @Description: Object是集合转化
	 * @Author Demo
	 * @DateTime 2017年11月28日 下午3:11:29
	 * @param jsonData json数据
	 * @param clazz    集合中的类型
	 * @return
	 */
	public static ResponseResult formatToList(String jsonData, Class<?> clazz) {
		try {
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			Object obj = null;
			if (data.isArray() && data.size() > 0) {
				obj = MAPPER.readValue(data.traverse(),
						MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
			}
			return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
		} catch (Exception e) {
			return null;
		}
	}
}
