package com.mama.server.util;

import java.util.Properties;

@Deprecated
public class ServerConf {

  private static final String KEY_NAME    = "name";
  private static final String KEY_VERSION = "version";

  private static final Properties DEFAULT_CONF = new Properties() {
    private static final long serialVersionUID = 1L;
    {
      setProperty(KEY_VERSION, "0.0.0.00000000.000");
    }
  };
  private static final String DEFAULT_PATH = "/sconf.properties";

  private static Properties conf;


  private ServerConf() {}
  static {
    init();
  }


  private static void init() {
    conf = PropUtil.read(DEFAULT_PATH, DEFAULT_CONF);
  }


  //
  // Specific Properties Getters
  //

  public static String name() {
    return conf.getProperty(KEY_NAME);
  }

  public static String version() {
    return conf.getProperty(KEY_VERSION);
  }

  public static boolean debug() {
    return getBoolean("debug");
  }

  public static String secretKey() {
    return getStr("secret.key");
  }

  //
  // General Getters
  //

  public static String getStr(String name) {
    return conf.getProperty(name, "");
  }

  public static int getInt(String name) {
    return Integer.valueOf(getStr(name));
  }

  public static boolean getBoolean(String name) {
    return Boolean.valueOf(getStr(name));
  }
}
