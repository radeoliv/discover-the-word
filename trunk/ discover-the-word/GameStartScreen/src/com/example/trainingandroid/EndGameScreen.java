package com.example.trainingandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.trainingandroid.constants.Constants;

public class EndGameScreen extends Activity{
	
	private int winOrLose;
	private String word;
	private int pontuation;
	
	private TextView pontuationTextView;
	private TextView winOrLoseTextView;
	
	private Button yesButton;
	private Button noButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_over_screen);
		start();
	}

	private void start() {
		catchInformationOfPreviousIntent();
		configMessageWinOrLose();
		setPontuation();
		configButtons();
	}
	
	private void configMessageWinOrLose() {
		winOrLoseTextView = (TextView) findViewById(R.id.youLostTheGameText);
		if(winOrLose == Constants.WIN){
			winOrLoseTextView.setText(getString(R.string.you_won_the_game));
		} else {
			winOrLoseTextView.setText(getString(R.string.you_lost_the_game));
		}
	}

	private void catchInformationOfPreviousIntent() {
		Intent it = getIntent();
		if(it != null){
			Bundle params = it.getExtras();
			if(params != null && params.containsKey(Constants.WORD_KEY)){
				this.winOrLose = params.getInt(Constants.WIN_OR_LOSE);
				this.word = params.getString(Constants.WORD_KEY);
				this.pontuation = params.getInt(Constants.PONTUATION);
			}
			if (word == null || word.equals("")) {
				word = "NULL";
			}
		} else {
			word = "NULL";
		}
	}

	private void setPontuation() {
		pontuationTextView = (TextView) findViewById(R.id.pontuationText);
		String pontStr = pontuation + "";
		pontuationTextView.setText(pontuationTextView.getText() + pontStr);
	}

	private void configButtons() {
		yesButton = (Button)findViewById(R.id.yesButton);
		yesButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EndGameScreen.this.callGameScreen();
			}
		});
		
		noButton = (Button)findViewById(R.id.noButton);
		noButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EndGameScreen.this.callGameScoreScreen();
			}
		});
	}
	
	protected void callGameScoreScreen(){
		Bundle params = new Bundle();
		params.putInt(Constants.PONTUATION, pontuation);
		Intent it = new Intent(this, GameScoreScreen.class);
		it.putExtras(params);
		startActivity(it);
	}
	
	protected void callGameStartScreen() {
		Intent it = new Intent(this, GameStartScreen.class);
		startActivity(it);
	}
	
	private void callGameScreen(){
		if(winOrLose == Constants.WIN){
			intentGameScreen(GameStartScreen.returnRandomWord(), pontuation);
		} else {
			intentGameScreen(word, pontuation);
		}
	}
	
	private void intentGameScreen(String word, int pontuation) {
		Intent it = new Intent(this, GameScreen.class);
		Bundle params = new Bundle();
		params.putString(Constants.WORD_KEY, word);
		params.putInt(Constants.PONTUATION, pontuation);
		it.putExtras(params);
		startActivity(it);
	}
}
