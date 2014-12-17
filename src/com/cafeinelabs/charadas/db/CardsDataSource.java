package com.cafeinelabs.charadas.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CardsDataSource {

	private SQLiteOpenHelper dbHelper;
	private SQLiteDatabase database;

	private static final String[] allColumns = {
		DbOpenHelper.COLUMN_ID,
		DbOpenHelper.COLUMN_TITLE
	};

	public CardsDataSource(Context context){
		dbHelper = new DbOpenHelper(context);
	}

	public void open(){
		database = dbHelper.getWritableDatabase();
	}

	public void close(){
		database.close();
	}

	public void saveCard(String table, String cardName){
		
		Card card = new Card();
		card.setTitle(cardName);
		
		ContentValues values = new ContentValues();

		values.put(DbOpenHelper.COLUMN_TITLE, card.getTitle());

		long insertId = database.insert("'"+table+"'", null, values);
		card.setId(insertId);
		
		Log.i("create", card.getTitle() + " inserted");
	}

	public void removeCard(String currentTable, Card card){
		String cardName = card.getTitle();
		String where = DbOpenHelper.COLUMN_TITLE + "=" + "'"+cardName+"'" 
				+ "AND " + DbOpenHelper.COLUMN_ID + "=" + card.getId();
		database.delete("'"+currentTable+"'", where, null);
		Log.i("delete", cardName + " deleted");
	}

	public List<Card> findAll(String currentTable){

		Cursor cursor = database.query("'"+currentTable+"'", allColumns, 
				null, null, null, null, null);
		List<Card> cards = cursorToList(cursor);
		return cards;
	}

	private List<Card> cursorToList(Cursor cursor) {
		List<Card> cards = new ArrayList<Card>();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Card card = new Card();
				card.setId(cursor.getLong(cursor.getColumnIndex(DbOpenHelper.COLUMN_ID)));
				card.setTitle(cursor.getString(cursor.getColumnIndex(DbOpenHelper.COLUMN_TITLE)));
				cards.add(card);
			}
		}
		return cards;
	}
}

