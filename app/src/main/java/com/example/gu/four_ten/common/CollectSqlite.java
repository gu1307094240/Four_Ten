package com.example.gu.four_ten.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by GU on 2016/10/7.
 */
public class CollectSqlite {
    private DBOpenHandler dbOpenHandler;

    public CollectSqlite(Context context) {
        this.dbOpenHandler = new DBOpenHandler(context, "collect.db", null, 1);
    }

    public void save(CollectInfo info) {// 插入记录
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();// 取得数据库操作
        db.execSQL("insert into table_collect (tag,title,url) values(?,?,?)", new String[] { info.getTag(), info.getTitle(), info.getUrl() });
        db.close();// 记得关闭数据库操作
    }

    public void delete(String title) {// 删除纪录
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        db.execSQL("delete from table_collect where title=?", new String[] { title.toString() });
        db.close();
    }
    public CollectInfo find(String title) {// 根据title查找纪录
        CollectInfo info = null;
        SQLiteDatabase db = dbOpenHandler.getReadableDatabase();
        // 用游标Cursor接收从数据库检索到的数据
        Cursor cursor = db.rawQuery("select * from table_collect where title=?", new String[] { title.toString() });
        if (cursor.moveToFirst()) {// 依次取出数据
            info = new CollectInfo();
            info.setId(cursor.getInt(cursor.getColumnIndex("id")));
            info.setTag(cursor.getString(cursor.getColumnIndex("tag")));
            info.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            info.setUrl(cursor.getString(cursor.getColumnIndex("url")));

        }
        db.close();
        return info;
    }

    public List<CollectInfo> findAll() {// 查询所有记录
        List<CollectInfo> lists = new ArrayList<CollectInfo>();
        CollectInfo info = null;
        SQLiteDatabase db = dbOpenHandler.getReadableDatabase();
        // Cursor cursor=db.rawQuery("select * from t_users limit ?,?", new
        // String[]{offset.toString(),maxLength.toString()});
        // //这里支持类型MYSQL的limit分页操作

        Cursor cursor = db.rawQuery("select * from table_collect ", null);
        while (cursor.moveToNext()) {
            info = new CollectInfo();
            info.setId(cursor.getInt(cursor.getColumnIndex("id")));
            info.setTag(cursor.getString(cursor.getColumnIndex("tag")));
            info.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            info.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            lists.add(info);
        }
        Collections.reverse(lists);    //倒序
        db.close();
        return lists;
    }
//    public void save(CollectInfo info){
//        ContentValues values = new ContentValues();
//        values.put("tag",info.getTag());
//        values.put("title",info.getTitle());
//        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
//        db.insert("collection", null, values);
//        db.close();
//    }
//    public void delete(String title){
//        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
//        db.delete("collection", "title=?", new String[]{title});
//        db.close();
//    }
//    public List<CollectInfo> findAll(){
//        List<CollectInfo> lists = new ArrayList<CollectInfo>();
//        CollectInfo info;
//        SQLiteDatabase db = dbOpenHandler.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("select * from collection ", null);
//        while (cursor.moveToNext()) {
//            info = new CollectInfo();
//            info.setId(cursor.getInt(cursor.getColumnIndex("id")));
//            info.setTag(cursor.getString(cursor.getColumnIndex("tag")));
//            info.setTitle(cursor.getString(cursor.getColumnIndex("title")));
//            lists.add(info);
//        }
//        db.close();
//        return lists;
//    }
}
