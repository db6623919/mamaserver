package com.console.util;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

/**
 * 工具类
 * 
 * @author style
 * 
 */
public class FormworkUtil {

        private static Map<String, String> typeMap = new HashMap<String, String>();
        static {
                typeMap.put("int", "int");
                typeMap.put("long", "long");
                typeMap.put("boolean", "boolean");
                typeMap.put("float", "float");
                typeMap.put("double", "double");
                typeMap.put("byte", "byte");
                typeMap.put("char", "char");
                typeMap.put("short", "short");
                typeMap.put("java.math.BigDecimal", "java.math.BigDecimal");
                typeMap.put("java.math.BigInteger", "java.math.BigInteger");
                typeMap.put("java.lang.String", "string");
                typeMap.put("java.util.Date", "date");
                typeMap.put("java.lang.Integer", "int");
                typeMap.put("java.lang.Long", "long");
                typeMap.put("java.lang.Boolean", "boolean");
                typeMap.put("java.lang.Float", "float");
                typeMap.put("java.lang.Double", "double");
                typeMap.put("java.lang.Byte", "byte");
                typeMap.put("java.lang.Character", "char");
                typeMap.put("java.lang.Short", "short");
        }

        public static String getMethodByFieldName(String fieldName) {
                return "get" + fieldName.substring(0, 1).toUpperCase()
                                + fieldName.substring(1);

        }

        public static String toSetMethodName(String fieldName) {
                return "set" + fieldName.substring(0, 1).toUpperCase()
                                + fieldName.substring(1);
        }

        public static void main(String[] args) {

        }

        public static boolean isDateType(String type) {
                return typeMap.containsKey(type);
        }

        /**
         * 是否支持的类型
         * 
         * @param type
         *            数据类型
         * @return
         */
        public static boolean isSupportType(String type) {

                return typeMap.containsKey(type);
        }

        /**
         * 转换类型
         * 
         * @param type
         * @param value
         * @return
         */
        public static Object convert(String type, String value) throws Exception {
                String obj = (String) typeMap.get(type);
                if (obj.equals("String")) {
                        return value;
                }
                Object objVal = null;
                if (value.length() > 0) {
                        if (obj.equals("int")) {
                                objVal = Integer.parseInt(value);
                        } else if (obj.equals("long")) {
                                objVal = Long.parseLong(value);
                        } else if (obj.equals("short")) {
                                objVal = Short.parseShort(value);
                        } else if (obj.equals("double")) {
                                objVal = Double.parseDouble(value);
                        } else if (obj.equals("string")) {
                                objVal = value;
                        } else if (obj.equals("boolean")) {
                                objVal = Boolean.parseBoolean(value);
                        } else if (obj.equals("float")) {
                                objVal = Float.parseFloat(value);
                        } else if (obj.equals("byte")) {
                                objVal = Byte.parseByte(value);
                        } else if (obj.equals("char")) {
                                objVal = Character.valueOf(value.toCharArray()[0]);
                        } else if (type.equals("date")) {
                                int startIndex = value.indexOf(58);
                                int secondIndex = -1;
                                if (startIndex > -1) {
                                        secondIndex = value.indexOf(58, startIndex + 1);
                                }
                                String format = "yyyy-MM-dd";
                                if ((startIndex > 0) && (secondIndex == -1)) {
                                        format = format + " HH:mm";
                                } else if ((startIndex > 0) && (secondIndex > startIndex)) {
                                        format = format + " HH:mm:ss";
                                }
                                objVal = DateUtils.parseDate(value, new String[] { format });
                        }
                }
                return objVal;

        }

}