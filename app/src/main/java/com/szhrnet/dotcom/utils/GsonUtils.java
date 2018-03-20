package com.szhrnet.dotcom.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.szhrnet.dotcom.bean.BaseResponseBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lk on 2017/4/5 0005.
 */

public class GsonUtils {

  private GsonUtils() {
  }

  /**
   * 转成json
   */
  public static String GsonString(Object object) {
    String gsonString = null;
    gsonString = new Gson().toJson(object);
    return gsonString;
  }

  /**
   * 添加注解后生成 Json 字符串
   */
  public static String jsonExposeString(Object object) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.excludeFieldsWithoutExposeAnnotation();
    Gson gson = gsonBuilder.create();
    return gson.toJson(object);
  }

  /**
   * 转成bean
   */
  public static <T> T GsonToBean(String gsonString, Class<T> cls) {
    T t = null;
    t = new Gson().fromJson(gsonString, cls);
    return t;
  }

  /**
   * 转成list
   * 泛型在编译期类型被擦除导致报错
   */
  public static <T> ArrayList<T> GsonToList(String gsonString, Class<T> cls) {
    ArrayList<T> list = null;
    list = new Gson().fromJson(gsonString, new TypeToken<ArrayList<T>>() {
    }.getType());
    return list;
  }

  /**
   * 转成list
   * 解决泛型问题
   */
  public static <T> List<T> jsonToList(String json, Class<T> cls) {
    Gson gson = new Gson();
    List<T> list = new ArrayList<T>();
    JsonArray array = new JsonParser().parse(json).getAsJsonArray();
    for (final JsonElement elem : array) {
      list.add(gson.fromJson(elem, cls));
    }
    return list;
  }

  /** 自定义ParameterizedTypeImpl 解析到 */
  public static <T> BaseResponseBean<T> GsonToNetObject(String jsonString, Class<T> clazz) {
    ParameterizedTypeImpl parameterizedType =
        new ParameterizedTypeImpl(BaseResponseBean.class, new Class[] { clazz });
    Gson gson = new Gson();
    return gson.fromJson(jsonString, parameterizedType);
  }

  /** 自定义ParameterizedTypeImpl 解析到 */
  public static <T> BaseResponseBean<List<T>> GsonToNetList(String jsonString, Class<T> clazz) {
    // 生成List<T> 中的 List<T>
    ParameterizedTypeImpl parameterizedType =
        new ParameterizedTypeImpl(List.class, new Class[] { clazz });
    // 根据List<T>生成完整的Result<List<T>>
    ParameterizedTypeImpl parameterizedType1 =
        new ParameterizedTypeImpl(BaseResponseBean.class, new Type[] { parameterizedType });

    Gson gson = new Gson();
    return gson.fromJson(jsonString, parameterizedType1);
  }

  /** 参照：搞定Gson泛型封装 http://www.jianshu.com/p/d62c2be60617 */
  public static class ParameterizedTypeImpl implements ParameterizedType {
    private final Class raw;
    private final Type[] args;

    public ParameterizedTypeImpl(Class raw, Type[] args) {
      this.raw = raw;
      this.args = args != null ? args : new Type[0];
    }

    @Override public Type[] getActualTypeArguments() {
      return args;
    }

    @Override public Type getRawType() {
      return raw;
    }

    @Override public Type getOwnerType() {
      return null;
    }
  }
}