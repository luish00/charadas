package com.cafeinelabs.charadas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cafeinelabs.charadas.R;

public class DbOpenHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "cards.db";
	private static final int DATABASE_VERSION = 1;

	//TABLES
	public static final String TABLE_MOVIES = "movies";
	public static final String TABLE_ARTISTS = "artists";
	public static final String TABLE_VIDEO_GAMES = "video_games";
	public static final String TABLE_CHARACTERS = "characters";
	public static final String TABLE_COUNTRIES = "countries";
	public static final String TABLE_ANIMALS = "animals";
	public static final String TABLE_SUPER_HEROES = "super_heroes";
	public static final String TABLE_TV_SHOWS = "tv_shows";
	public static final String TABLE_TV_SHOWS_FOR_KIDS = "tv_shows_for_kids";
	public static final String TABLE_QUICK_PLAY = "quick_play";
	public static final String TABLE_ACT_IT_OUT = "act_it_out";

	//Categories
	public static final String _ID = "id";
	public static final String _NAME = "name";
	public static final String TABLE_CATEGORIES = "categories";
	public static String[] arrayCategories = {
		"Películas","Artistas","Video juegos","Personajes",
		"Continentes","Animales","Súper heroes","Seríes de TV",
		"Seríes de TV para niños","¡Actualo!","xxx","xxx2"};

	//COLUMS
	public static final String COLUMN_ID = "cardId";
	public static String COLUMN_TITLE = "title";
	
	private String[] arrayMovies;
	private String[] arrayArtists;
	private String[] arrayVideoGames;
	private String[] arrayCharacters;
	private String[] arrayCountries;
	private String[] arrayAnimals;
	private String[] arraySuperHeroes;
	private String[] arrayTvShows;
	private String[] arrayTvShowsForKids;
	private String[] arrayQuickPlay;
	private String[] arrayActItOut;
	
	private static final String CREATE_TABLE_MOVIES =
			"CREATE TABLE " + TABLE_MOVIES + " (" +
					COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_TITLE + " TEXT " +
					")";
	private static final String CREATE_TABLE_ARTISTS =
			"CREATE TABLE " + TABLE_ARTISTS + " (" +
					COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_TITLE + " TEXT " +
					")";
	private static final String CREATE_TABLE_VIDEO_GAMES =
			"CREATE TABLE " + TABLE_VIDEO_GAMES + " (" +
					COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_TITLE + " TEXT " +
					")";
	private static final String CREATE_TABLE_CHARACTERS =
			"CREATE TABLE " + TABLE_CHARACTERS + " (" +
					COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_TITLE + " TEXT " +
					")";
	private static final String CREATE_TABLE_COUNTRIES =
			"CREATE TABLE " + TABLE_COUNTRIES + " (" +
					COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_TITLE + " TEXT " +
					")";
	private static final String CREATE_TABLE_ANIMALS =
			"CREATE TABLE " + TABLE_ANIMALS + " (" +
					COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_TITLE + " TEXT " +
					")";
	private static final String CREATE_TABLE_SUPER_HEROES =
			"CREATE TABLE " + TABLE_SUPER_HEROES + " (" +
					COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_TITLE + " TEXT " +
					")";
	private static final String CREATE_TABLE_TV_SHOWS =
			"CREATE TABLE " + TABLE_TV_SHOWS + " (" +
					COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_TITLE + " TEXT " +
					")";
	private static final String CREATE_TABLE_TV_SHOWS_FOR_KIDS =
			"CREATE TABLE " + TABLE_TV_SHOWS_FOR_KIDS + " (" +
					COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_TITLE + " TEXT " +
					")";
	private static final String CREATE_TABLE_QUICK_PLAY =
			"CREATE TABLE " + TABLE_QUICK_PLAY + " (" +
					COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_TITLE + " TEXT " +
					")";
	private static final String CREATE_TABLE_ACT_IT_OUT =
			"CREATE TABLE " + TABLE_ACT_IT_OUT + " (" +
					COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_TITLE + " TEXT " +
					")";
	private static final String CREATE_TABLE_CATEGOIES =
			"CREATE TABLE " + TABLE_CATEGORIES + " (" +
					_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					_NAME + " TEXT " +
					")";
	
	public DbOpenHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		arrayMovies = context.getResources().getStringArray(R.array.arrayMovies);
		arrayArtists = context.getResources().getStringArray(R.array.arrayArtists);
		arrayVideoGames = context.getResources().getStringArray(R.array.arrayVideoGames);
		arrayCharacters = context.getResources().getStringArray(R.array.arrayCharacters);
		arrayCountries = context.getResources().getStringArray(R.array.arrayCountries);
		arrayAnimals = context.getResources().getStringArray(R.array.arrayAnimals);
		arraySuperHeroes = context.getResources().getStringArray(R.array.arraySuperHeroes);
		arrayTvShows = context.getResources().getStringArray(R.array.arrayTvShows);
		arrayTvShowsForKids = context.getResources().getStringArray(R.array.arrayTvShowsForKids);
		arrayQuickPlay = context.getResources().getStringArray(R.array.arrayQuickPlay);
		arrayActItOut = context.getResources().getStringArray(R.array.arrayActItOut);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		long id = 0;
		
		db.execSQL(CREATE_TABLE_QUICK_PLAY);
		for (int i = 0; i < arrayQuickPlay.length; i++) {
			id++;
			String cardName = arrayQuickPlay[i];
			ContentValues values = insertBitValues(cardName, id);
			db.insert(TABLE_QUICK_PLAY, null, values);
			Log.i("insert", cardName + " se inserto en la base de datos");
		}
		
		db.execSQL(CREATE_TABLE_MOVIES);
		for (int i = 0; i < arrayMovies.length; i++) {
			id++;
			String cardName = arrayMovies[i];
			ContentValues values = insertBitValues(cardName, id);
			db.insert(TABLE_MOVIES, null, values);
			Log.i("insert", cardName + " se inserto en la base de datos");
		}
		
		db.execSQL(CREATE_TABLE_CHARACTERS);
		for (int i = 0; i < arrayCharacters.length; i++) {
			id++;
			String cardName = arrayCharacters[i];
			ContentValues values = insertBitValues(cardName, id);
			db.insert(TABLE_CHARACTERS, null, values);
			Log.i("insert", cardName + " se inserto en la base de datos");
		}
		
		db.execSQL(CREATE_TABLE_VIDEO_GAMES);
		for (int i = 0; i < arrayVideoGames.length; i++) {
			id++;
			String cardName = arrayVideoGames[i];
			ContentValues values = insertBitValues(cardName, id);
			db.insert(TABLE_VIDEO_GAMES, null, values);
			Log.i("insert", cardName + " se inserto en la base de datos");
		}
		
		db.execSQL(CREATE_TABLE_COUNTRIES);
		for (int i = 0; i < arrayCountries.length; i++) {
			id++;
			String cardName = arrayCountries[i];
			ContentValues values = insertBitValues(cardName, id);
			db.insert(TABLE_COUNTRIES, null, values);
			Log.i("insert", cardName + " se inserto en la base de datos");
		}
		
		db.execSQL(CREATE_TABLE_ANIMALS);
		for (int i = 0; i < arrayAnimals.length; i++) {
			id++;
			String cardName = arrayAnimals[i];
			ContentValues values = insertBitValues(cardName, id);
			db.insert(TABLE_ANIMALS, null, values);
			Log.i("insert", cardName + " se inserto en la base de datos");
		}
		
		db.execSQL(CREATE_TABLE_ARTISTS);
		for (int i = 0; i < arrayArtists.length; i++) {
			id++;
			String cardName = arrayArtists[i];
			ContentValues values = insertBitValues(cardName, id);
			db.insert(TABLE_ARTISTS, null, values);
			Log.i("insert", cardName + " se inserto en la base de datos");
		}
		
		db.execSQL(CREATE_TABLE_ACT_IT_OUT);
		for (int i = 0; i < arrayActItOut.length; i++) {
			id++;
			String cardName = arrayActItOut[i];
			ContentValues values = insertBitValues(cardName, id);
			db.insert(TABLE_ACT_IT_OUT, null, values);
			Log.i("insert", cardName + " se inserto en la base de datos");
		}
		
		db.execSQL(CREATE_TABLE_SUPER_HEROES);
		for (int i = 0; i < arraySuperHeroes.length; i++) {
			id++;
			String cardName = arraySuperHeroes[i];
			ContentValues values = insertBitValues(cardName, id);
			db.insert(TABLE_SUPER_HEROES, null, values);
			Log.i("insert", cardName + " se inserto en la base de datos");
		}
		
		db.execSQL(CREATE_TABLE_TV_SHOWS);
		for (int i = 0; i < arrayTvShows.length; i++) {
			id++;
			String cardName = arrayTvShows[i];
			ContentValues values = insertBitValues(cardName, id);
			db.insert(TABLE_TV_SHOWS, null, values);
			Log.i("insert", cardName + " se inserto en la base de datos");
		}
		
		db.execSQL(CREATE_TABLE_TV_SHOWS_FOR_KIDS);
		for (int i = 0; i < arrayTvShowsForKids.length; i++) {
			id++;
			String cardName = arrayTvShowsForKids[i];
			ContentValues values = insertBitValues(cardName, id);
			db.insert(TABLE_TV_SHOWS_FOR_KIDS, null, values);
			Log.i("insert", cardName + " se inserto en la base de datos");
		}
		
		db.execSQL(CREATE_TABLE_CATEGOIES);
		for (int i = 0; i < arrayCategories.length; i++) {
//			String cardName = arrayTvShowsForKids[i];
			ContentValues values = new ContentValues();
//			values.put(_ID, id);
			values.put(_NAME, arrayCategories[i]);
//			ContentValues values = insertBitValues(cardName, id);
			db.insert(TABLE_CATEGORIES, null, values);
			Log.i("insert", arrayCategories[i] + " se inserto en la base de datos");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTISTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEO_GAMES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHARACTERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRIES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANIMALS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPER_HEROES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TV_SHOWS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TV_SHOWS_FOR_KIDS);
		onCreate(db);
	}
	
	private ContentValues insertBitValues(String name, long id){
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, id);
		values.put(COLUMN_TITLE, name);
		return values;
	}
}
