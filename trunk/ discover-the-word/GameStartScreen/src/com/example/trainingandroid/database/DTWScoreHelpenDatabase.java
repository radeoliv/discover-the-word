package com.example.trainingandroid.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.trainingandroid.entity.ScoreEntity;

public class DTWScoreHelpenDatabase extends SQLiteOpenHelper {

	private static final int versionScoreDatabase = 32;

	private static String dbName = "ScoreDB";
	
	private static String scoreTableName = "ScoreTable";
	private static String colID = "ScoreID";
	private static String colPlayerName = "PlayerName";
	private static String colPontuation = "Pontuation";
	
	private Context context;
	
	private static final String CREATE_SCORE_TABLE =
			"CREATE TABLE " +  scoreTableName + " (" +
			colID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			colPlayerName + " TEXT," + 
			colPontuation + " INTEGER)";
	
	private static final String DROP_SCORE_TABLE = 
			"DROP TABLE IF EXISTS " + scoreTableName + ";";
	
	public DTWScoreHelpenDatabase(Context context) {
		super(context, dbName, null, versionScoreDatabase);
		this.setContext(context);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_SCORE_TABLE);
		insertDefaultScores(db);
	}

	private void insertDefaultScores(SQLiteDatabase db) {
		ScoreEntity entity2 = new ScoreEntity("Robert", 5);
		insertScore(entity2, db);
		ScoreEntity entity1 = new ScoreEntity("John", 2);
		insertScore(entity1, db);
		ScoreEntity entity3 = new ScoreEntity("Alice", 10);
		insertScore(entity3, db);
		ScoreEntity entity4 = new ScoreEntity("Jordan", 17);
		insertScore(entity4, db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_SCORE_TABLE);
		onCreate(db);
	}
	
	public void insertScore(ScoreEntity entity, SQLiteDatabase database){
		
		ContentValues cv = new ContentValues();
		cv.put(colPlayerName,entity.getPlayerName());
		cv.put(colPontuation, entity.getPontuation());
		
		database.insert(scoreTableName, null, cv);
	}
	
	public boolean deleteScore(ScoreEntity entity, SQLiteDatabase db){
		long id = entity.getId();
		return db.delete(scoreTableName, colID + "=" + id, null) > 0;
	}
	
	public List<ScoreEntity> getAllScores() {
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		List<ScoreEntity> list = new ArrayList<ScoreEntity>();
		
		Cursor cursor = db.query(scoreTableName, 
				new String[]{colID, colPlayerName, colPontuation}, 
				null, null, null, 
				null, colPontuation + " DESC");
		
		if(cursor != null && cursor.moveToFirst()){
			int idIndex = cursor.getColumnIndexOrThrow(colID);
			int playerNameIndex = cursor.getColumnIndexOrThrow(colPlayerName);
			int pontuationIndex = cursor.getColumnIndexOrThrow(colPontuation);
			do {
				long idScore = cursor.getLong(idIndex);
				String playerName = cursor.getString(playerNameIndex);
				int pontuation = cursor.getInt(pontuationIndex);
				ScoreEntity score = new ScoreEntity(idScore, playerName, pontuation);
				list.add(score);
			} while(cursor.moveToNext());

			cursor.close();
		}
		
		return list;
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
