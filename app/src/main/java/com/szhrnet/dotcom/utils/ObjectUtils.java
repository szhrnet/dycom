package com.szhrnet.dotcom.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Object Utils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2011-10-24
 */
public class ObjectUtils {

  private ObjectUtils() {
    throw new AssertionError();
  }

  /**
   * compare two object
   *
   * @return <ul>
   * <li>if both are null, return true</li>
   * <li>return actual.{@link Object#equals(Object)}</li>
   * </ul>
   */
  public static boolean isEquals(Object actual, Object expected) {
    return actual == expected || (actual == null ? expected == null : actual.equals(expected));
  }

  /**
   * null Object to empty string
   *
   * <pre>
   * nullStrToEmpty(null) = &quot;&quot;;
   * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
   * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
   * </pre>
   */
  public static String nullStrToEmpty(Object str) {
    return (str == null ? "" : (str instanceof String ? (String) str : str.toString()));
  }

  /**
   * convert long array to Long array
   */
  public static Long[] transformLongArray(long[] source) {
    Long[] destin = new Long[source.length];
    for (int i = 0; i < source.length; i++) {
      destin[i] = source[i];
    }
    return destin;
  }

  /**
   * convert Long array to long array
   */
  public static long[] transformLongArray(Long[] source) {
    long[] destin = new long[source.length];
    for (int i = 0; i < source.length; i++) {
      destin[i] = source[i];
    }
    return destin;
  }

  /**
   * convert int array to Integer array
   */
  public static Integer[] transformIntArray(int[] source) {
    Integer[] destin = new Integer[source.length];
    for (int i = 0; i < source.length; i++) {
      destin[i] = source[i];
    }
    return destin;
  }

  /**
   * convert Integer array to int array
   */
  public static int[] transformIntArray(Integer[] source) {
    int[] destin = new int[source.length];
    for (int i = 0; i < source.length; i++) {
      destin[i] = source[i];
    }
    return destin;
  }

  /**
   * compare two object
   * <ul>
   * <strong>About result</strong>
   * <li>if v1 > v2, return 1</li>
   * <li>if v1 = v2, return 0</li>
   * <li>if v1 < v2, return -1</li>
   * </ul>
   * <ul>
   * <strong>About rule</strong>
   * <li>if v1 is null, v2 is null, then return 0</li>
   * <li>if v1 is null, v2 is not null, then return -1</li>
   * <li>if v1 is not null, v2 is null, then return 1</li>
   * <li>return v1.{@link Comparable#compareTo(Object)}</li>
   * </ul>
   */
  @SuppressWarnings({ "unchecked", "rawtypes" }) public static <V> int compare(V v1, V v2) {
    return v1 == null ? (v2 == null ? 0 : -1) : (v2 == null ? 1 : ((Comparable) v1).compareTo(v2));
  }

  /**
   * 在Java语言里深复制一个对象，常常可以先使对象实现Serializable接口，然后把对象（实际上只是对象的一个拷贝）
   * 写到一个流里（腌成咸菜），再从流里读出来（把咸菜回鲜），便可以重建对象。
   */
  public static Object deepClone(Object object) {
    try {
      //将对象写到流里
      ByteArrayOutputStream bo = new ByteArrayOutputStream();
      ObjectOutputStream oo = new ObjectOutputStream(bo);
      oo.writeObject(object);
      //从流里读出来
      ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
      ObjectInputStream oi = new ObjectInputStream(bi);
      return (oi.readObject());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
