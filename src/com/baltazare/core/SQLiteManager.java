package com.baltazare.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLiteManager extends SQLiteOpenHelper {
	
	private static final String TABLE_QUESTIONS = "table_questions";
	
	private static final String COLONNE_ID = "id";
	private static final String COLONNE_TEXT = "text";
	private static final String COLONNE_GOOD_ANSWERS = "good_answers";
	private static final String COLONNE_WRONG_ANSWERS = "wrong_answers";
	
	private static final String REQUETE_CREATION_BD = "create table"
			+ TABLE_QUESTIONS + " (" + COLONNE_ID + " integer primary key autoincrement, " + COLONNE_TEXT
			+ " text not null, " + COLONNE_GOOD_ANSWERS + " text not null, " + COLONNE_WRONG_ANSWERS + " text not null);";
	
	public SQLiteManager(Context context, String nom, CursorFactory cursorFactory, int version) {
		// TODO Auto-generated constructor stub
		super(context, nom, cursorFactory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(REQUETE_CREATION_BD);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
