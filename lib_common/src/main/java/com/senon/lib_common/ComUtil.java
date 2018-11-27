package com.senon.lib_common;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.widget.EditText;
import com.senon.lib_common.utils.MD5Utils;
import com.senon.lib_common.utils.ToastUtil;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ComUtil
 */
public class ComUtil {

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^1[3|4|5|7|8|9][0-9]{9}$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isChinese(String str) {
        Pattern pattern = Pattern.compile("^[\\u4E00-\\u9FA5]+$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 充值金额时输入是否合理
     * @param str
     * @return
     */
    public static boolean payRecharge(String str) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    //long型时间转换为字符串时间类型
    public static String longToString(Object longTime, String timeFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(timeFormat == null ? "yyyy-MM-dd" : timeFormat);
        long time = 0;
        if (longTime instanceof Integer || longTime instanceof Long) {
            return formatter.format(longTime);
        } else if (longTime instanceof String) {
            return formatter.format(Long.valueOf((String) longTime));
        }
        return "时间获取错误";
    }

    // 屏幕宽度（像素）
    public static int getScreenWidth(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    public static String getMd5Str(HashMap<String, String> map) {
        StringBuffer sb = new StringBuffer();
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            sb.append(entry.getValue().toString());
        }
        return MD5Utils.getMd5(sb.toString() + AppConfig.SECRET);
    }

    public static HashMap<String, String> getMd5Str(String[] keyArray, String[] valueArray) {
        HashMap<String, String> map = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        if (keyArray.length != valueArray.length) {
            ToastUtil.initToast("key value长度不对应");
        } else {
            for (int i = 0; i < keyArray.length; i++) {
                map.put(keyArray[i], valueArray[i]);
            }
            for (int i = 0; i < valueArray.length; i++) {
                sb.append(valueArray[i]);
            }
            map.put("secret", MD5Utils.getMd5(sb.toString() + AppConfig.SECRET));
            return map;
        }
        return null;
    }

    /**
     * 初始化数据格式
     */
    public static void judgePwdFormat(final EditText editText, final int maxCount, final String msg){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(editText.getText().toString().length() > maxCount){
                    ToastUtil.initToast(msg);
                    int selectionEnd = editText.getSelectionEnd();
                    s.delete(selectionEnd - 1, selectionEnd);
                    editText.setText(s);
                    editText.setSelection(editText.getText().length());
                }
            }
        });
    }


}
