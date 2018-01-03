package com.arvin.bspatch;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by xiaoyi on 2018-1-3.
 */

public class BSPatch {

    private native static int mergeFile(String oldFile, String newFile, String patch);


    public static String TAG = "BSPatch";

    static {
        System.loadLibrary("bspatch");
    }

    private static String extractApk(Context context) {
        context = context.getApplicationContext();
        ApplicationInfo info = context.getApplicationInfo();
        String apkdir = info.sourceDir;
        Log.d(TAG, apkdir);
        return apkdir;
    }

    public static void bspatch(Context context, String newPath, String patchPath,Callback callback) {
        String apkPath = extractApk(context);
        if (!checkFile(patchPath)) {
            throw new IllegalArgumentException(String.format("%s not exits,please check your input path!", patchPath));
        }
        listener = callback;
        BSPatchAsyncTask task = new BSPatchAsyncTask(apkPath, newPath, patchPath);
        task.execute();
    }

    private static boolean checkFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    private static String getDefaultPatchPath(Context context) {
        context = context.getApplicationContext();
        String path = context.getCacheDir().getAbsolutePath()
                + File.separator + "patch" + File.separator
                + "apk.patch";
        return path;
    }

    private static String getDefaultNewPath(Context context) {
        context = context.getApplicationContext();
        String path = context.getCacheDir().getAbsolutePath()
                + File.separator + "patch" + File.separator
                + "new.apk";
        return path;
    }

    private static class BSPatchAsyncTask extends AsyncTask<Void, Void, Integer> {
        private String oldPath;
        private String newPath;
        private String patch;

        public BSPatchAsyncTask(String oldPath, String newPath, String patch) {
            this.oldPath = oldPath;
            this.newPath = newPath;
            this.patch = patch;

        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return mergeFile(oldPath, newPath, patch);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (listener != null) {
                listener.mergeResult(result);
            }
        }
    }

    public interface Callback {
        void mergeResult(int result);
    }

    private static Callback listener;

}
