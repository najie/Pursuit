package com.baltazare.core;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class PursuitDBAdaptateur {
	
	private static final int BASE_VERSION = 1;
	private static final String BASE_NOM = "pursuit.db";
	
	private static final String TABLE_QUESTIONS = "table_questions";
	
	private static final String COLONNE_ID = "id";
	private static final int COLONNE_ID_ID = 0;
	private static final String COLONNE_TEXT = "text";
	private static final int COLONNE_TEXT_ID = 1;
	private static final String COLONNE_GOOD_ANSWERS = "good_answers";
	private static final int COLONNE_GOOD_ANSWERS_ID = 2;
	private static final String COLONNE_WRONG_ANSWERS = "wrong_answers";
	private static final int COLONNE_WRONG_ANSWERS_ID = 3;
	
	private static final String REQUETE_CREATION_BD = "create table"
			+ TABLE_QUESTIONS + " (" + COLONNE_ID + " integer primary key autoincrement, " + COLONNE_TEXT
			+ " text not null, " + COLONNE_GOOD_ANSWERS + " text not null, " + COLONNE_WRONG_ANSWERS + " text not null);";
	
	private SQLiteDatabase PursuitBD;
	private SQLiteManager sqliteManager;
	
	public PursuitDBAdaptateur(Context ctx) {
		sqliteManager = new SQLiteManager(ctx, BASE_NOM, null, BASE_VERSION);
	}
	
	public SQLiteDatabase open() {
		PursuitBD = sqliteManager.getWritableDatabase();
		return PursuitBD;
	}
	
	public void close() {
		PursuitBD.close();
	}
	
	public SQLiteDatabase getBDD() {
		return PursuitBD;
	}
	
	public Question getQuestion(int id) {
		return null;
	}
	
	public ArrayList<Question> getAllQuestions() {
		return null;
	}
	
	public long insertQuestion(Question question) {
		return 0;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
