package com.example.trainingandroid;

import java.util.List;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.trainingandroid.constants.Constants;
import com.example.trainingandroid.database.DTWScoreHelpenDatabase;
import com.example.trainingandroid.entity.ScoreEntity;
import com.example.trainingandroid.util.Utils;

public class GameStartScreen extends Activity{
	
	private Button startGame;
	
	public static List<String> wordsList;
	public static int listSize;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_start_screen);
		
//		if(wordsList == null){
//			Toast.makeText(this, "LOADING...", Toast.LENGTH_LONG).show();
//			wordsList = Utils.readFileAndPutWordsInList(this);
//			listSize = wordsList.size();
//		}
//		start();
		
//		// IMPORTANTE NÃO DELETE!!!
//		DTWScoreHelpenDatabase db = new DTWScoreHelpenDatabase(this);
//		List<ScoreEntity> list = db.getAllScores();
//		printListEntities(list);
//		
//		ScoreEntity sampleToDelet = list.get(1);
//		db.deleteScore(sampleToDelet, db.getWritableDatabase());
//		list = db.getAllScores();
//		printListEntities(list);
		
	}
	
	private void printListEntities(List<ScoreEntity> list){
		Log.v("ListSize", list.size() + "");
		for(ScoreEntity entity : list){
			Log.v("Player Name", entity.getPlayerName() + "");
			Log.v("Pontuation", entity.getPontuation() + "");
		}
	}
	
	private void start() {
		configStartGameButton();
	}

	private void configStartGameButton() {
		startGame = (Button)findViewById(R.id.startGame);
		startGame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GameStartScreen.this.startGame();
			}
		});
	}

	private void startGame() {
		Intent it = new Intent(this, GameScreen.class);
		Bundle params = new Bundle();
		params.putString(Constants.WORD_KEY, returnRandomWord());
		params.putInt(Constants.PONTUATION, 0);
		it.putExtras(params);
		startActivity(it);
	}
	
	public static String returnRandomWord(){
		int randomNumber = Utils.returnRandomNumber();
		return wordsList.get(randomNumber);
	}
	
}
