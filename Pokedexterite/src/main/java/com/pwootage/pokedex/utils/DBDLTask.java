package com.pwootage.pokedex.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.pwootage.pokedex.R;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

/**
 * Created by Christopher on 10/26/13.
 */
public class DBDLTask extends AsyncTask<String, Integer, String> {
    private static final String t = DBDLTask.class.getName();
    private Context ctx;
    private ProgressDialog progress;

    public DBDLTask(Context context, ProgressDialog progress) {
        this.ctx = context;
        this.progress = progress;
    }

    @Override
    protected String doInBackground(String... params) {
        PowerManager pm = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock lock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, DBDLTask.class.getName());
        lock.acquire();
        try {
            File db = ctx.getDatabasePath(VeekunDBHelper.DB_NAME);
            Log.d(t, "Path: " + db);
            InputStream in = null;
            OutputStream out = null;
            byte[] buff = new byte[1024];
            int read = 0;
            try {
                in = new GZIPInputStream(ctx.getResources().openRawResource(R.raw.pokedex));
                db.getParentFile().mkdirs();
                db.createNewFile();
                out = new FileOutputStream(db);
                long total = 0;
                long last = 0;
                while ((read = in.read(buff)) >= 0) {
                    out.write(buff, 0, read);
                    total += read;
                    if (last + 1024 * 512 < total) {
                        last = total;
                        Log.d(t, "Downloading progress: downloaded " + total / 1024 + " kb");
                        progress.setProgress((int)total/(1024*1024*34));
                    }
                }
            } catch (Exception e) {
                Log.e(t, "Error downloading file", e);
            } finally {
                closeIfNotNullIgnoreException(in, out);
            }
        } finally {
            lock.release();
        }
        return null;
    }

    public static void printRecursively(File folder, String prefix) {
        Log.d(t, "Path2: " + prefix + folder.getPath());
        if (!folder.isDirectory()) return;
        File[] sub = folder.listFiles();
        if (sub == null) return;
        for (File f : sub) {
            printRecursively(f, prefix + "  ");
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
    }

    @Override
    protected void onPreExecute() {
        progress.show();
    }

    @Override
    protected void onPostExecute(String s) {
        progress.dismiss();
        if (s != null) {
            Toast.makeText(ctx, "Download error: " + s, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ctx, "File downloaded", Toast.LENGTH_SHORT).show();
        }
    }

    private void closeIfNotNullIgnoreException(Closeable... closeables) {
        for (Closeable cls : closeables) {
            if (cls != null) {
                try {
                    cls.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
