package com.henley.android.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.ArrayRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.annotation.StringRes;
import androidx.annotation.XmlRes;
import androidx.core.content.ContextCompat;

/**资源操作工具类
 * @author liyunlong
 * @since 2020/5/26 15:42
 */
public final class ResourceHelper {

    /**
     * 获取Resources对象
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * 打开assets目录中的资源
     */
    public static InputStream openAssets(Context context, String fileName) {
        if (context == null || TextUtils.isEmpty(fileName)) {
            return null;
        }
        try {
            return context.getResources().getAssets().open(fileName);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 打开assets目录中的资源并转换为String
     */
    public static String geFileFromAssets(Context context, String fileName) {
        InputStream is = openAssets(context, fileName);
        if (is == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        InputStreamReader in = null;
        BufferedReader br = null;
        try {
            in = new InputStreamReader(is);
            br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (IOException e) {
            return null;
        } finally {
            CloseHelper.closeIOQuietly(br, in, is);
        }
    }

    /**
     * 打开assets目录中的资源并转换为List<String>
     */
    public static List<String> geFileToListFromAssets(@NonNull Context context, String fileName) {
        InputStream is = openAssets(context, fileName);
        if (is == null) {
            return null;
        }
        List<String> fileContent = new ArrayList<String>();
        InputStreamReader in = null;
        BufferedReader br = null;
        try {
            in = new InputStreamReader(is);
            br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
            return fileContent;
        } catch (IOException e) {
            return null;
        } finally {
            CloseHelper.closeIOQuietly(br, in, is);
        }
    }

    /**
     * 打开raw目录中的资源
     */
    public static InputStream openRaw(@NonNull Context context, @RawRes int id) {
        return context.getResources().openRawResource(id);
    }

    /**
     * 打开raw目录中的资源并转换为String
     */
    public static String geFileFromRaw(@NonNull Context context, int resId) {
        InputStream is = context.getResources().openRawResource(resId);
        StringBuilder builder = new StringBuilder();
        InputStreamReader in = null;
        BufferedReader br = null;
        try {
            in = new InputStreamReader(is);
            br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (IOException e) {
            return null;
        } finally {
            CloseHelper.closeIOQuietly(br, in, is);
        }
    }

    /**
     * 打开raw目录中的资源并转换为List<String>
     */
    public static List<String> geFileToListFromRaw(@NonNull Context context, int resId) {
        InputStream is = context.getResources().openRawResource(resId);
        List<String> fileContent = new ArrayList<>();
        BufferedReader br = null;
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(is);
            br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
            return fileContent;
        } catch (IOException e) {
            return null;
        } finally {
            CloseHelper.closeIOQuietly(br, in, is);
        }
    }

    /**
     * 打开raw目录中的资源(只适用于存储在资源包未压缩的数据)
     */
    public static AssetFileDescriptor getRawFd(@NonNull Context context, int id) {
        return context.getResources().openRawResourceFd(id);
    }

    /**
     * 打开raw目录中的资源，并转换为String
     */
    public String getRawText(@NonNull Context context, int id) {
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(openRaw(context, id));
            br = new BufferedReader(isr);
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseHelper.closeIOQuietly(br, isr);
        }
        return null;
    }

    /**
     * 获取资源ID
     *
     * @param context 上下文
     * @param name    资源的名称
     * @param defType 资源的类型(drawable,string等)
     */
    public static int getResourceByName(@NonNull Context context, String name, String defType) {
        return context.getResources().getIdentifier(name, defType, context.getPackageName());
    }

    /**
     * 打开xml目录中的资源
     *
     * @param context
     * @param id
     * @return
     */
    public static XmlResourceParser getXml(@NonNull Context context, @XmlRes int id) {
        return context.getResources().getXml(id);
    }

    /**
     * 打开drawable目录中的资源
     */
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int id) {
        return ContextCompat.getDrawable(context, id);
    }

    /**
     * 打开values文件中的资源获取字符串
     *
     * @param context
     * @param id
     * @return
     */
    public static String getString(@NonNull Context context, @StringRes int id) {
        return context.getResources().getString(id);
    }

    /**
     * 打开values文件中的资源获取字符串数组
     */
    public static String[] getStringArray(@NonNull Context context, @ArrayRes int id) {
        return context.getResources().getStringArray(id);
    }

    /**
     * 打开values文件中的资源获取颜色
     */
    public static int getColor(@NonNull Context context, @ColorRes int id) {
        return ContextCompat.getColor(context, id);
    }

    /**
     * 打开values文件中的资源获取颜色
     */
    public static ColorStateList getColorStateList(@NonNull Context context, @ColorRes int id) {
        return ContextCompat.getColorStateList(context, id);
    }

    /**
     * 打开values文件中的资源获取尺寸(如果是dp或sp的单位,将其乘以density,如果是px,则不乘)
     */
    public static float getDimension(@NonNull Context context, @DimenRes int id) {
        return context.getResources().getDimension(id);
    }

    /**
     * 打开values文件中的资源获取尺寸(如果是dp或sp的单位,将其乘以density,如果是px,则不乘)
     */
    public static int getDimensionPixelOffset(@NonNull Context context, @DimenRes int id) {
        return context.getResources().getDimensionPixelOffset(id);
    }

    /**
     * 打开values文件中的资源获取尺寸(不管单位是dp、sp或px,都会乘以denstiy)
     */
    public static int getDimensionPixelSize(@NonNull Context context, @DimenRes int id) {
        return context.getResources().getDimensionPixelSize(id);
    }

    /**
     * 打开assets目录中的资源
     */
    public static InputStream getDimensionPixelSize(@NonNull Context context, @NonNull String fileName) {
        try {
            return context.getResources().getAssets().open(fileName);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 打开assets目录中的资源
     * <br/>{@link AssetManager#ACCESS_UNKNOWN}
     * <br/>{@link AssetManager#ACCESS_STREAMING}
     * <br/>{@link AssetManager#ACCESS_RANDOM}
     * <br/>{@link AssetManager#ACCESS_BUFFER}
     */
    public static InputStream getDimensionPixelSize(@NonNull Context context, @NonNull String fileName, int accessMode) {
        try {
            return context.getResources().getAssets().open(fileName, accessMode);
        } catch (IOException e) {
            return null;
        }
    }

}
