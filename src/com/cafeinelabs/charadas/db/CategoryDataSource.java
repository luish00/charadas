package com.cafeinelabs.charadas.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CategoryDataSource {

	private SQLiteOpenHelper dbHelper;
	private SQLiteDatabase database;

	private static final String[] allColumns = {
		DbOpenHelper._ID,
		DbOpenHelper._NAME
	};

	public CategoryDataSource(Context context){
		dbHelper = new DbOpenHelper(context);
	}

	public void open(){
		database = dbHelper.getWritableDatabase();
	}

	public void close(){
		database.close();
	}
	
	public List<Category> findAll(){

		Cursor cursor = database.query("'" + DbOpenHelper.TABLE_CATEGORIES + "'", allColumns, 
				null, null, null, null, null);
		List<Category> cards = cursorToList(cursor);
		return cards;
	}
	
	private List<Category> cursorToList(Cursor cursor) {
		List<Category> categories = new ArrayList<Category>();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Category category = new Category();
				category.setId(cursor.getLong(cursor.getColumnIndex(DbOpenHelper._ID)));
				category.setName(cursor.getString(cursor.getColumnIndex(DbOpenHelper._NAME)));
				categories.add(category);
			}
		}
		return categories;
	}
}
