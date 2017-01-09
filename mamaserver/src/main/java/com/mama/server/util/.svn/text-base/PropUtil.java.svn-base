package com.mama.server.util;

import java.io.IOException;
import java.util.Properties;

@Deprecated
public class PropUtil {

  public static Wrapper read(String inclasspath) {
    return read(inclasspath, null);
  }

  public static Wrapper read(String inclasspath, Properties defaultProps) {
    Properties p = new Properties(defaultProps);
    try {

      p.load(Wrapper.class.getResourceAsStream(inclasspath));

      return new Wrapper(p);

    } catch (IOException e) {
      throw new RuntimeException("Load configurations error.", e);
    }
  }

  public static class Wrapper extends Properties {
    private static final long serialVersionUID = -3375395779427904389L;

    private Properties p;

    public Wrapper(Properties p) {
      this.p = p;
    }

    @Override
    public String getProperty(String name) {
      return p.getProperty(name);
    }

    @Override
    public String getProperty(String name, String defaultValue) {
      return p.getProperty(name);
    }

    public String str(String name) {
      return p.getProperty(name);
    }

    public Integer integer(String name) {
      return Integer.valueOf(p.getProperty(name));
    }

    public Boolean bool(String name) {
      String sp = p.getProperty(name);

      if (sp.equals("0") || sp.equals("1")) {
        return sp.equals("0") ? Boolean.FALSE : Boolean.TRUE;
      } else {
        return Boolean.valueOf(sp);
      }

    }

  }
}
