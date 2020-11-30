package com.henley.android.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import androidx.annotation.RequiresApi;

/**
 * Scoped Storage Manager for Android Q
 *
 * @author Henley
 * @since 2020/6/25 11:43
 */
@RequiresApi(api = Build.VERSION_CODES.Q)
public class ScopedStorageManager {

    private static final Uri URI_DOCUMENTS = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL);
    private static final Uri URI_DOWNLOADS = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
    private static final Uri URI_IMAGES = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    private static final Uri URI_AUDIOS = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private static final Uri URI_VIDEOS = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

    /**
     * Read content from Documents
     */
    public static String readFileFromDocuments(Context context, String fileName) {
        return readFileFromMediaStore(context, URI_DOCUMENTS, fileName, null);
    }

    /**
     * Read content from Documents
     */
    public static String readFileFromDocuments(Context context, String fileName, String relativePath) {
        return readFileFromMediaStore(context, URI_DOCUMENTS, fileName, relativePath);
    }

    /**
     * Read content from Downloads
     */
    public static String readFileFromDownloads(Context context, String fileName) {
        return readFileFromMediaStore(context, URI_DOWNLOADS, fileName, null);
    }

    /**
     * Read content from Downloads
     */
    public static String readFileFromDownloads(Context context, String fileName, String relativePath) {
        return readFileFromMediaStore(context, URI_DOWNLOADS, fileName, relativePath);
    }

    /**
     * Read content from Images
     */
    public static String readFileFromImages(Context context, String fileName) {
        return readFileFromMediaStore(context, URI_IMAGES, fileName, null);
    }

    /**
     * Read content from Images
     */
    public static String readFileFromImages(Context context, String fileName, String relativePath) {
        return readFileFromMediaStore(context, URI_IMAGES, fileName, relativePath);
    }

    /**
     * Read content from Audios
     */
    public static String readFileFromAudios(Context context, String fileName) {
        return readFileFromMediaStore(context, URI_AUDIOS, fileName, null);
    }

    /**
     * Read content from Images
     */
    public static String readFileFromAudios(Context context, String fileName, String relativePath) {
        return readFileFromMediaStore(context, URI_AUDIOS, fileName, relativePath);
    }

    /**
     * Read content from Videos
     */
    public static String readFileFromVideos(Context context, String fileName) {
        return readFileFromMediaStore(context, URI_VIDEOS, fileName, null);
    }

    /**
     * Read content from Videos
     */
    public static String readFileFromVideos(Context context, String fileName, String relativePath) {
        return readFileFromMediaStore(context, URI_VIDEOS, fileName, relativePath);
    }

    /**
     * Read content from MediaStore
     */
    public static String readFileFromMediaStore(Context context, Uri uri, String fileName, String relativePath) {
        String content = null;
        String[] projection = {
                MediaStore.MediaColumns._ID,
                MediaStore.MediaColumns.TITLE,
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.RELATIVE_PATH
        };
        String selection;
        String[] selectionArgs;
        if (relativePath == null || relativePath.isEmpty()) {
            selection = MediaStore.MediaColumns.TITLE + " = ? AND " + MediaStore.MediaColumns.DISPLAY_NAME + " like ?";
            selectionArgs = new String[]{fileName, fileName + "%"};
        } else {
            selection = MediaStore.MediaColumns.TITLE + " = ? AND " + MediaStore.MediaColumns.DISPLAY_NAME + " like ?  AND " + MediaStore.MediaColumns.RELATIVE_PATH + " like ?";
            selectionArgs = new String[]{fileName, fileName + "%", relativePath + "%"};
        }
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, null);
        if (cursor == null) {
            return null;
        }
        if (cursor.getCount() == 0) {
            cursor.close();
            return null;
        }
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.TITLE));
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
            int indexOf = name.indexOf(".");
            if (indexOf <= 0) {
                indexOf = name.length();
            }
            if (!fileName.equals(title) && !fileName.equals(name.substring(0, indexOf))) {
                continue;
            }
            int fileId = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            String filePath = uri.buildUpon()
                    .appendPath(String.valueOf(fileId))
                    .build()
                    .toString();
            Uri fileUri = Uri.parse(filePath);
            if (fileUri == null) {
                continue;
            }
            try {
                content = readInputStream(resolver.openInputStream(fileUri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (content == null || content.isEmpty()) {
                break;
            }
        }
        cursor.close();
        return content;
    }

    /**
     * Write content into Documents
     */
    public static Uri insertFileIntoDocuments(Context context, String fileName, String mimeType, String content) {
        return insertFileIntoMediaStore(context, URI_DOCUMENTS, fileName, mimeType, null, content);
    }

    /**
     * Write content into Documents
     */
    public static Uri insertFileIntoDocuments(Context context, String fileName, String mimeType, String relativePath, String content) {
        return insertFileIntoMediaStore(context, URI_DOCUMENTS, fileName, mimeType, relativePath, content);
    }

    /**
     * Write content into Downloads
     */
    public static Uri insertFileIntoDownloads(Context context, String fileName, String mimeType, String content) {
        return insertFileIntoMediaStore(context, URI_DOWNLOADS, fileName, mimeType, null, content);
    }

    /**
     * Write content into Downloads
     */
    public static Uri insertFileIntoDownloads(Context context, String fileName, String mimeType, String relativePath, String content) {
        return insertFileIntoMediaStore(context, URI_DOWNLOADS, fileName, mimeType, relativePath, content);
    }

    /**
     * Write content into Images
     */
    public static Uri insertFileIntoImages(Context context, String fileName, String mimeType, String content) {
        return insertFileIntoMediaStore(context, URI_IMAGES, fileName, mimeType, null, content);
    }

    /**
     * Write content into Images
     */
    public static Uri insertFileIntoImages(Context context, String fileName, String mimeType, String relativePath, String content) {
        return insertFileIntoMediaStore(context, URI_IMAGES, fileName, mimeType, relativePath, content);
    }

    /**
     * Write content into Audios
     */
    public static Uri insertFileIntoAudios(Context context, String fileName, String mimeType, String content) {
        return insertFileIntoMediaStore(context, URI_AUDIOS, fileName, mimeType, null, content);
    }

    /**
     * Write content into Audios
     */
    public static Uri insertFileIntoAudios(Context context, String fileName, String mimeType, String relativePath, String content) {
        return insertFileIntoMediaStore(context, URI_AUDIOS, fileName, mimeType, relativePath, content);
    }

    /**
     * Write content into Videos
     */
    public static Uri insertFileIntoVideos(Context context, String fileName, String mimeType, String content) {
        return insertFileIntoMediaStore(context, URI_VIDEOS, fileName, mimeType, null, content);
    }

    /**
     * Write content into Videos
     */
    public static Uri insertFileIntoVideos(Context context, String fileName, String mimeType, String relativePath, String content) {
        return insertFileIntoMediaStore(context, URI_VIDEOS, fileName, mimeType, relativePath, content);
    }

    /**
     * Write content into MediaStore
     */
    public static Uri insertFileIntoMediaStore(Context context, Uri uri, String fileName, String mimeType, String relativePath, String content) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.TITLE, fileName);
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
        if (relativePath != null && !relativePath.isEmpty()) {
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath);
        }
        values.put(MediaStore.MediaColumns.IS_PENDING, 1);
        ContentResolver resolver = context.getContentResolver();
        Uri fileUri = null;
        try {
            fileUri = resolver.insert(uri, values);
            if (fileUri == null) {
                return null;
            }
            writeOutputStream(resolver.openOutputStream(fileUri), content);
        } catch (IOException e) {
            // Don't leave an orphan entry in the MediaStore
            if (fileUri != null) {
                resolver.delete(fileUri, null, null);
            }
            e.printStackTrace();
            return null;
        }
        values.clear();
        values.put(MediaStore.MediaColumns.IS_PENDING, 0);
        resolver.update(fileUri, values, null, null);
        return fileUri;
    }

    /**
     * Write content to OutputStream
     */
    private static void writeOutputStream(OutputStream stream, String content) throws IOException {
        try {
            if (stream != null) {
                stream.write(content.getBytes());
                stream.flush();
            }
        } finally {
            CloseUtils.closeIOQuietly(stream);
        }
    }

    /**
     * Read content from InputStream
     */
    private static String readInputStream(InputStream stream) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                if (builder.length() > 0) {
                    builder.append("\r\n");
                }
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIOQuietly(reader);
        }
        return builder.toString();
    }

}