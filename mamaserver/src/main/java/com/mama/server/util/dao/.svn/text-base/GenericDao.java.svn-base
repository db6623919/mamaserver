package com.mama.server.util.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface GenericDao<T> {

  int count();

  List<T> getAll();

  T get(Object id);

  int save(T po);

  int update(T po);

  int delete(@Param("ids") List<Object> ids);

  int delete(@Param("id") Object id);
}
