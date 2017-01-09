package com.mama.server.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapUtil {
  public static <T> List<T> getList(Map<String, List<T>> map, String name) {
    List<T> value = map.get(name);

    if (value == null) {
      value = new LinkedList<T>();
      map.put(name, value);
    }

    return value;
  }

}
