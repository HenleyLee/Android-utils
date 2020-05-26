package com.henley.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Map;
import java.util.Set;

/**
 * 共享参数工具类
 *
 * @author liyunlong
 * @since 2020/5/25 13:54
 */
public final class PreferencesHelper {

    private static final String DEFAULT_NAME = "config_prefs";

    public static Context context = Tool.getContext();
    private volatile static PreferencesHelper INSTANCE;
    private final SharedPreferences preferences;

    private PreferencesHelper(String name, int mode) {
        if (TextUtils.isEmpty(name)) {
            name = DEFAULT_NAME;
        }
        preferences = context.getSharedPreferences(name, mode);
    }

    /**
     * 返回单例实例
     *
     * @see #getInstance(String)
     */
    public static PreferencesHelper getInstance() {
        return getInstance(DEFAULT_NAME);
    }

    /**
     * 返回单例实例
     *
     * @param name 文件名(不用带后缀)
     * @see #getInstance(String, int)
     */
    public static PreferencesHelper getInstance(String name) {
        return getInstance(name, Context.MODE_PRIVATE);
    }

    /**
     * 返回单例实例
     *
     * @param name 文件名(不用带后缀)
     * @param mode 操作模式
     *             <br/>操作模式共有四种：
     *             <br/>{@link Context#MODE_PRIVATE} 默认操作模式，代表该文件是私有数据，只能被应用本身访问，在该模式下，写入的内容会覆盖原文件的内容
     *             <br/>{@link Context#MODE_APPEND} 该模式会检查文件是否存在，存在就往文件追加内容，否则就创建新文件
     *             <br/>{@link Context#MODE_WORLD_READABLE} 表示当前文件可以被其他应用读取
     *             <br/>{@link Context#MODE_WORLD_WRITEABLE} 表示当前文件可以被其他应用写入
     */
    public static PreferencesHelper getInstance(String name, int mode) {
        if (INSTANCE == null) {
            synchronized (PreferencesHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PreferencesHelper(name, mode);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 获取SharedPreferences对象
     */
    public static SharedPreferences getSharedPreferences(String name) {
        return getSharedPreferences(context, name);
    }

    /**
     * 获取SharedPreferences对象
     */
    public static SharedPreferences getSharedPreferences(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * 获取SharedPreferences对象
     */
    public SharedPreferences getSharedPreferences() {
        return preferences;
    }

    /**
     * 读取int类型参数
     */
    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    /**
     * 读取int类型参数
     */
    public int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    /**
     * 写入int类型参数
     */
    public void putInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    /**
     * 写入int类型参数
     */
    public boolean putIntWithResult(String key, int value) {
        return preferences.edit().putInt(key, value).commit();
    }

    /**
     * 读取float类型参数
     */
    public float getFloat(String key) {
        return preferences.getFloat(key, 0.0f);
    }

    /**
     * 读取float类型参数
     */
    public float getFloat(String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }

    /**
     * 写入float类型参数
     */
    public void putFloat(String key, float value) {
        preferences.edit().putFloat(key, value).apply();
    }

    /**
     * 写入float类型参数
     */
    public boolean putFloatWithResult(String key, float value) {
        return preferences.edit().putFloat(key, value).commit();
    }

    /**
     * 读取long类型参数
     */
    public long getLong(String key) {
        return preferences.getLong(key, 0L);
    }

    /**
     * 读取long类型参数
     */
    public long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    /**
     * 写入long类型参数
     */
    public void putLong(String key, long value) {
        preferences.edit().putLong(key, value).apply();
    }

    /**
     * 写入long类型参数
     */
    public boolean putLongWithResult(String key, long value) {
        return preferences.edit().putLong(key, value).commit();
    }

    /**
     * 读取String类型参数
     */
    public String getString(String key) {
        return preferences.getString(key, null);
    }

    /**
     * 读取String类型参数
     */
    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    /**
     * 写入String类型参数
     */
    public void putString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    /**
     * 写入String类型参数
     */
    public boolean putStringWithResult(String key, String value) {
        return preferences.edit().putString(key, value).commit();
    }

    /**
     * 读取boolean类型参数
     */
    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    /**
     * 读取boolean类型参数
     */
    public boolean getBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    /**
     * 写入boolean类型参数
     */
    public void putBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    /**
     * 写入boolean类型参数
     */
    public boolean putBooleanWithResult(String key, boolean value) {
        return preferences.edit().putBoolean(key, value).commit();
    }

    /**
     * 保存数据的方法，拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    public void put(String key, Object object) {
        SharedPreferences.Editor editor = preferences.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }

    /**
     * 保存数据的方法，拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    public boolean putWithResult(String key, Object object) {
        SharedPreferences.Editor editor = preferences.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        return editor.commit();
    }

    /**
     * 保存数据的方法，根据默认值得到保存的数据的具体类型，然后调用相对应的方法获取值
     */
    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return preferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return preferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return preferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return preferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return preferences.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * 将Map中的数据分别保存
     */
    public void putMap(Map<String, ?> map) {
        if (map == null || map.size() == 0) {
            return;
        }
        SharedPreferences.Editor editor = preferences.edit();
        Set<? extends Map.Entry<String, ?>> entrieSet = map.entrySet();
        for (Map.Entry<String, ?> entry : entrieSet) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else {
                editor.putString(key, value.toString());
            }
        }
        editor.apply();
    }

    /**
     * 将Map中的数据分别保存
     */
    public boolean putMapWithResult(Map<String, ?> map) {
        if (map == null || map.size() == 0) {
            return false;
        }
        SharedPreferences.Editor editor = preferences.edit();
        Set<? extends Map.Entry<String, ?>> entrieSet = map.entrySet();
        for (Map.Entry<String, ?> entry : entrieSet) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else {
                editor.putString(key, value.toString());
            }
        }
        return editor.commit();
    }

    /**
     * 注册SharedPreference变化监听
     */
    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        preferences.registerOnSharedPreferenceChangeListener(listener);
    }

    /**
     * 反注册SharedPreference变化监听
     */
    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        preferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    /**
     * 获取所有的键值对
     */
    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    /**
     * 查询某个key是否已经存在
     */
    public boolean contains(String key) {
        return preferences.contains(key);
    }

    /**
     * 移除某个key对应的值
     */
    public void remove(String key) {
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains(key)) {
            editor.remove(key);
        }
        editor.apply();
    }

    /**
     * 移除某个key对应的值
     */
    public boolean removeWithResult(String key) {
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains(key)) {
            editor.remove(key);
        }
        return editor.commit();
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        preferences.edit()
                .clear()
                .apply();
    }

    /**
     * 清除所有数据
     */
    public boolean clearWithResult() {
        return preferences.edit()
                .clear()
                .commit();
    }

}
