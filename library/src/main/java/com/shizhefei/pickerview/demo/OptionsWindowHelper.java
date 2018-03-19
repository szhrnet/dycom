package com.shizhefei.pickerview.demo;

import com.shizhefei.pickerview.CharacterPickerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 地址选择器
 *
 * @version 0.1 king 2015-10
 */
public class OptionsWindowHelper {

  private static List<String> options1Items = null;
  private static List<List<String>> options2Items = null;
  private static List<List<List<String>>> options3Items = null;

  public interface OnOptionsSelectListener {
    void onOptionsSelect(String province, String city, String area);
  }

  private OptionsWindowHelper() {
  }

  /**
   * 初始化选项数据
   */
  public static void setPickerData(CharacterPickerView view) {
    if (options1Items == null) {
      options1Items = new ArrayList<>();
      options2Items = new ArrayList<>();
      options3Items = new ArrayList<>();

      final Map<String, Map<String, List<String>>> allCitys = ArrayDataDemo.getAll();
      for (Map.Entry<String, Map<String, List<String>>> entry1 : allCitys.entrySet()) {
        String key1 = entry1.getKey();
        Map<String, List<String>> value1 = entry1.getValue();

        options1Items.add(key1);

        List<String> options2Items01 = new ArrayList<>();
        List<List<String>> options3Items01 = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry2 : value1.entrySet()) {
          String key2 = entry2.getKey();
          List<String> value2 = entry2.getValue();

          options2Items01.add(key2);
          Collections.sort(value2);
          options3Items01.add(new ArrayList<>(value2));
        }
        Collections.sort(options2Items01);
        options2Items.add(options2Items01);
        options3Items.add(options3Items01);
      }
      Collections.sort(options1Items);
    }

    //三级联动效果
    view.setPicker(options1Items, options2Items, options3Items);
  }
}
