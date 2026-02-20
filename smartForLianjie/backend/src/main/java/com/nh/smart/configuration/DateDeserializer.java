package com.nh.smart.configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * 
 * @ClassName: DateDeserializer
 * @Description: 用于处理前端使用JSON传递给后台时，传递参数为json格式的时间
 *  {
	"name":"x",
	"age":"22",
	"createTime":"2019-12-25 16:15:35"			==>传递json格式的时间，要经过此拦截器进行转换
	}
 * @Author Demo
 * @DateTime 2020年1月9日 下午5:23:26
 */
public class DateDeserializer extends JsonDeserializer<LocalDateTime> {
	@Override
	public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		if (StringUtils.isBlank(jsonParser.getText()))
			return null;
		LocalDateTime localDate = LocalDateTime.parse(jsonParser.getText(),
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        LocalDateTime dateTime = LocalDateTime.of(formatter.ge, Month.APRIL, 8, 12, 30);
		return localDate;
	}
}
