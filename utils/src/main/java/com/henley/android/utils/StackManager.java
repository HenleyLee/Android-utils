package com.henley.android.utils;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import androidx.core.app.ActivityCompat;

/**
 * 管理Activity的堆栈管理器
 * <br>Stack是一种后进先出的数据结构(LIFO),存放了一系列对象的栈。用户可以对栈执行pop和push操作，哪怕是null对象也行，而且Stack没有大小的显示
 *
 * @author Henley
 * @since 2020/5/26 15:55
 */
public final class StackManager {

    private static final String TAG = "StackManager";

    /**
     * Stack中对应的Activity列表
     */
    private Stack<Activity> mActivityStack;
    /**
     * 栈管理器实例对象
     */
    private static volatile StackManager mInstance;

    /**
     * 私有的构造方法
     */
    private StackManager() {
        mActivityStack = new Stack<>();
    }

    /**
     * 获取栈管理工具
     */
    public static StackManager getInstance() {
        if (mInstance == null) {
            synchronized (StackManager.class) {
                if (mInstance == null) {
                    mInstance = new StackManager();
                    Log.i(TAG, "堆栈管理器实例被创建");
                }
            }
        }
        return mInstance;
    }

    public synchronized int size() {
        return mActivityStack == null ? 0 : mActivityStack.size();
    }

    /**
     * 将指定Activity推入栈中
     */
    public synchronized void pushActivity(Activity activity) {
        boolean result = mActivityStack.add(activity);
        if (result) {
            Log.i(TAG, activity.getClass().getName() + "被压入栈");
        }
    }

    /**
     * 将指定Activity从栈中弹出
     */
    public synchronized void popActivity(Activity activity) {
        boolean result = mActivityStack.remove(activity);
        if (result) {
            ActivityCompat.finishAfterTransition(activity);
            Log.i(TAG, activity.getClass().getName() + "被弹出栈");
        }
    }

    /**
     * 判断栈中是否存在指定的Activity
     */
    public boolean checkActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断栈中是否存在指定的Activity
     */
    public boolean checkActivity(String className) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().getName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 结束指定类名的Activity
     */
    public synchronized void finishActivity(Class<?> cls) {
        if (cls != null) {
            for (Activity activity : mActivityStack) {
                if (activity.getClass().equals(cls)) {
                    finishActivity(activity);
                }
            }
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            popActivity(activity);
        }
    }

    /**
     * 结束指定的Activity之上所有的Activity
     */
    public boolean finishTopActivitys(Activity activity) {
        return finishTopActivitys(activity.getClass(), false);
    }

    /**
     * 结束指定的Activity之上所有的Activity
     */
    public boolean finishTopActivitys(Class<? extends Activity> cls) {
        return finishTopActivitys(cls, false);
    }

    /**
     * 结束指定的Activity之上所有的Activity
     */
    public boolean finishTopActivitys(Class<? extends Activity> cls, boolean includeSelf) {
        Activity topActivity;
        List<Activity> buffer = new ArrayList<>();
        int size = mActivityStack.size();
        for (int i = size - 1; i >= 0; i--) {
            topActivity = mActivityStack.get(i);
            if (topActivity.getClass().isAssignableFrom(cls)) {
                for (Activity activity : buffer) {
                    finishActivity(activity);
                }
                return true;
            } else if (i == size - 1 && includeSelf) {
                buffer.add(topActivity);
            } else if (i != size - 1) {
                buffer.add(topActivity);
            }
        }
        return false;
    }

    /**
     * 弹出栈中所有Activity
     */
    public synchronized void popAllActivitys() {
        while (!mActivityStack.empty()) {
            Activity activity = currentActivity();
            if (activity != null) {
                popActivity(activity);
            }
        }
    }

    /**
     * 获得当前栈顶Activity
     */
    public synchronized Activity currentActivity() {
        if (mActivityStack == null || mActivityStack.size() == 0) {
            return null;
        }
        // lastElement() 获取最后个子元素，这里是栈顶的Activity
        return mActivityStack.lastElement();
    }

}
