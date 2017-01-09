package com.mama.server.util;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Json {
  private static ObjectMapper om = new ObjectMapper();

  static {
    om.setSerializationInclusion(Include.NON_NULL);
  }

  public static String format(Object o) {
    if (o == null) {
      return "";
    }

    try {
      return om.writeValueAsString(o);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T parse(String content, Class<T> cls) {
    if (content == null || content.isEmpty()) {
      throw new RuntimeException("JSON string is " + (content == null ? "null" : "empty"));
    }

    try {
      return om.readValue(content, cls);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
