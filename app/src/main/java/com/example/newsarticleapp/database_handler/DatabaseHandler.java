package com.example.newsarticleapp.database_handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.newsarticleapp.models.NewsArticleModel;

import java.util.ArrayList;

import static com.example.newsarticleapp.database_handler.DBConfig.NEWS_ARTICLE_TABLE;
import static com.example.newsarticleapp.database_handler.DBConfig.author;
import static com.example.newsarticleapp.database_handler.DBConfig.content;
import static com.example.newsarticleapp.database_handler.DBConfig.publishedAt;
import static com.example.newsarticleapp.database_handler.DBConfig.source;
import static com.example.newsarticleapp.database_handler.DBConfig.url;
import static com.example.newsarticleapp.database_handler.DBConfig.urlToImage;
import static com.example.newsarticleapp.database_handler.DBConfig.title;
import static com.example.newsarticleapp.database_handler.DBConfig.description;



public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHandler.class.getSimpleName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "NewsArticle Database";

    DatabaseHandler databaseHandler;
    private Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    public void deleteLocalDatabase() {
        context.deleteDatabase(DATABASE_NAME);
    }


    @Override

    public void onCreate(SQLiteDatabase db) {

        String CREATE_NEWS_ARTICLE_TABLE = "CREATE TABLE " + NEWS_ARTICLE_TABLE + "("
                + source
                + " TEXT not null, "
                + author + " TEXT not null, " + title
                + " TEXT not null, " + description + " TEXT not null, " + url + " TEXT not null, "
                + urlToImage + " TEXT not null, " + publishedAt + " TEXT not null, "
                + content + " TEXT not null " + ")";


        SQLiteStatement stmt = db.compileStatement(CREATE_NEWS_ARTICLE_TABLE);
        stmt.execute();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        SQLiteStatement stmt = db.compileStatement("DROP TABLE IF EXISTS " + NEWS_ARTICLE_TABLE);
        stmt.execute();


        onCreate(db);
    }


    // Delete Single Table Row
    public int deleteSingleTableRow(String tableName, String fieldName, String fieldValue) {

        Log.e(TAG, "Delete Row: " + tableName + " -Column Name: " + fieldName + " - Column Value : " + fieldValue);

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(tableName, fieldName + " = ?", new String[]{fieldValue});

    }

    // Delete Single Table Row
    public int deleteSingleTableRow(String tableName, String fieldName, String fieldValue, String fieldName1, String fieldValue1) {

        Log.e(TAG, "Delete Row: " + tableName + " -Column Name: " + fieldName + " - Column Value : " + fieldValue);

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(tableName, fieldName + " = ? AND " + fieldName1 + " = ?", new String[]{fieldValue, fieldValue1});


    }

    /****  ------------------------------------  ****/


    public boolean isTableExists(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        try {
            cursor = db.query(tableName, null,
                    null, null, null, null, null);
            cursor.close();
            Log.e(TAG, tableName + " exist :(((");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, tableName + " doesn't exist :(((");
            return false;

        }

    }

    // Delete Table
    public int deleteTable(String tableName) {

        Log.e(TAG, "Delete Table: " + tableName);

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(tableName, null, null);

    }


    public void addNewsArticleModel(NewsArticleModel newsArticleModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(source, newsArticleModel.getSource());
        values.put(author, newsArticleModel.getAuthor());
        values.put(title, newsArticleModel.getTitle());
        values.put(description, newsArticleModel.getDescription());
        values.put(url, newsArticleModel.getUrl());
        values.put(urlToImage, newsArticleModel.getUrlToImage());
        values.put(publishedAt, newsArticleModel.getPublishedAt());
        values.put(content, newsArticleModel.getContent());

        //DMT fields
        // Inserting Row
        db.insert(NEWS_ARTICLE_TABLE, null, values);
        // db.close();

    }


    public ArrayList<NewsArticleModel> fetchNewsArticle() {
        ArrayList<NewsArticleModel> newsArticleModelArrayList = new ArrayList<>();
        // Select All Query
        SQLiteDatabase db = this.getReadableDatabase();


        String table = NEWS_ARTICLE_TABLE;
        String[] columnsToReturn = {source, author, title, description, url, urlToImage, publishedAt, content};
        String selection = null;

        String[] selectionArgs = {}; // matched to "?" in selection
        Cursor cursor = db.query(table, columnsToReturn, selection, selectionArgs, null, null, null);

        // looping through all rows and adding to list
        try {
            if (cursor.moveToFirst()) {
                do {
                    NewsArticleModel upiBankAccountModel = new NewsArticleModel(
                            cursor.getString(cursor.getColumnIndex(source)),
                            cursor.getString(cursor.getColumnIndex(author)),
                            cursor.getString(cursor.getColumnIndex(title)),
                            cursor.getString(cursor.getColumnIndex(description)),
                            cursor.getString(cursor.getColumnIndex(url)),
                            cursor.getString(cursor.getColumnIndex(urlToImage)),
                            cursor.getString(cursor.getColumnIndex(publishedAt)),
                            cursor.getString(cursor.getColumnIndex(content)));


                    // Adding All Data to List
                    newsArticleModelArrayList.add(upiBankAccountModel);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            // db.close();
        }

        // return status list
        return newsArticleModelArrayList;
    }

}

