/*
 * Created by Elliott Rheault on 2022.3.24
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.capstoneproject.helpers.Recipe;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "tendorDB";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // RECIPE Table - Column names
    private static final String KEY_RECIPE_NAME = "recipe";

    // Table Names
    private static final String TABLE_RECIPE = "recipe";

    /*
    ---------------------------------------
    |       Table Create Statements       |
    ---------------------------------------
     */
    private static final String CREATE_TABLE_RECIPE = "CREATE TABLE " + TABLE_RECIPE + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_RECIPE_NAME + " TEXT"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create the required tables
        sqLiteDatabase.execSQL(CREATE_TABLE_RECIPE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // On upgrade, drop older tables
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);

        // Create new tables
        onCreate(sqLiteDatabase);
    }

    /*
    ----------------------------------
    |      RECIPE Table Methods      |
    ----------------------------------
    */

    /*
     * Create a recipe
     */
    public long createRecipe(Recipe recipe) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_RECIPE_NAME, recipe.getRecipeName());

        // insert row
        long recipe_id = sqLiteDatabase.insert(TABLE_RECIPE, null, values);

        return recipe_id;
    }

    /*
     * get single recipe
     */
    public Recipe getRecipe(long recipe_id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_RECIPE + " WHERE "
                + KEY_ID + " = " + recipe_id;

        Log.e(LOG, selectQuery);

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        Recipe recipe = new Recipe();

        recipe.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
        recipe.setRecipeName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_RECIPE_NAME)));

        cursor.close();

        return recipe;
    }
}
