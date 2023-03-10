package com.eoemusic.eoebackend.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 20/01/23 3:02 AM
 */
@Slf4j
public final class JSON {
  public static ObjectMapper defaultObjectMapper;

  static {
    defaultObjectMapper = new ObjectMapper();
    defaultObjectMapper.findAndRegisterModules();
    defaultObjectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    defaultObjectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    defaultObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    defaultObjectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
  }

  private JSON(){
    throw new IllegalStateException("Utils class");
  }

  public static class SimpleDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
      jsonGenerator.writeString(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
  }

  public static class SimpleDateTimeDesrializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
      return Timestamp.valueOf(p.getText()).toLocalDateTime();
    }
  }

  public static String writeValueAsString(Object object) {
    try {
      return defaultObjectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static String toJson(Object src) {
    return writeValueAsString(src);
  }
 

  public static <T> List<T> readList(String jsonString, Class<T> valueType) {
    if (StringUtils.isEmpty(jsonString)) {
      return Collections.emptyList();
    }
    JavaType javaType = defaultObjectMapper.getTypeFactory().constructParametricType(ArrayList.class, valueType);
    try {
      return defaultObjectMapper.readValue(jsonString, javaType);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T readValue(String content, Class<T> valueType) {
    try {
      return defaultObjectMapper.readValue(content, valueType);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T fromJson(String json, Class<T> classOfT) {
    return readValue(json, classOfT);
  }
}
