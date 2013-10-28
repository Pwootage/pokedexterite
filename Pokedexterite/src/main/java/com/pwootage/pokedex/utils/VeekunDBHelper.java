package com.pwootage.pokedex.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.pwootage.pokedex.R;

import java.io.File;

/**
 * Created by Christopher on 10/26/13.
 */
public class VeekunDBHelper {
    private static final String t = VeekunDBHelper.class.getName();
    public static String DB_NAME = "veekun.sqlite";
    public static final int DB_VERSION = 1;
    private static SQLiteDatabase db = null;

    public static SQLiteDatabase getDB(Context context) {
        if (db == null) {
            db = loadDB(context);
        }
        return db;
    }

    private static SQLiteDatabase loadDB(Context context) {
        SQLiteDatabase db = openDB(context);
        return db;
    }

    public static void extractDB(final Context context) {
        startExtract(context);
//        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
//        alertBuilder.setMessage(context.getString(R.string.download_prompt));
//        alertBuilder.setPositiveButton(context.getString(R.string.download), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                startExtract(context);
//            }
//        });
//        alertBuilder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //
//            }
//        });
//        AlertDialog request = alertBuilder.create();
//        try {
//        request.show();
//        } catch (Exception e) {
//            Log.d(t, "Can't show alert", e);
//        }
    }

    private static void startExtract(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(context.getString(R.string.extracting));
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(true);
        DBDLTask task = new DBDLTask(context, dialog);
        task.execute();
    }

    private static SQLiteDatabase openDB(Context context) {
        String path = context.getDatabasePath(DB_NAME).getPath();
        SQLiteDatabase db;
        try {
            db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
            return db;
        } catch (SQLiteException e) {
            Log.d(t, "Unable to get database", e);
            return null;
        }
    }
}
