package com.example.trainingandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.trainingandroid.constants.Constants;
import com.example.trainingandroid.util.Utils;

@SuppressLint("DefaultLocale") 
public class GameScreen extends Activity {
	
	private String word;
	private int pontuation;
	
	private TextView pontuationTextView;
	private TextView numberOfErrors;
	private TextView wrongLetters;
	
	private TextView underlinesTextView;
	private EditText triesEditText;
	
	private static final String PREFIX_TRIES =  "Number of Errors: ";
	private static final int MAX_TRIES = 5;
	private int numberOfTries = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_screen);
		pontuationTextView = (TextView)findViewById(R.id.pontuationText);
		start();
	}

	private void start() {
		catchWord();
		setPontuationInScreen();
		wrongLettersView();
		underlinesView();
		triesView();
	}

	private void catchWord() {
		Intent it = getIntent();
		if(it != null){
			Bundle params = it.getExtras();
			if(params != null && params.containsKey(Constants.WORD_KEY)){
				this.word = params.getString(Constants.WORD_KEY);
				this.pontuation = params.getInt(Constants.PONTUATION);
				Log.v("DTW-GameScreen.java", this.word);
			}
			if (word == null || word.equals("")) {
				word = "ERROR";
			}
		} else {
			word = "ERROR";
		}
	}

	private void setPontuationInScreen(){
		pontuationTextView.setText(getString(R.string.pontuation_text) + pontuation + "");
	}
	
	private void wrongLettersView() {
		numberOfErrors = (TextView)findViewById(R.id.numberOfErrors);
		numberOfErrors.setTextSize(34);
		wrongLetters = (TextView)findViewById(R.id.wrongLetters);
		wrongLetters.setTextSize(34);
		mountNumberOfErrorsString();
	}
	
	private void mountNumberOfErrorsString(){
		numberOfErrors.setText(PREFIX_TRIES + numberOfTries + "/" + MAX_TRIES);
	}
	
	private void mountWrongLettersString(char letter) {
		String str = wrongLetters.getText() + " " + letter;
		wrongLetters.setText(str.toUpperCase());
	}
	
	private void underlinesView() {
		underlinesTextView = (TextView) findViewById(R.id.underlines);

		String strUnderlines = "";
		for (int i = 0; i < word.length(); i++) {
			strUnderlines += (i != word.length() - 1) ? "_ " : "_";
		}

		underlinesTextView.setText(strUnderlines);
		underlinesTextView.setTextSize(34);		
	}
	
	private void triesView() {
		triesEditText = (EditText) findViewById(R.id.tries);
		triesEditText.setTextSize(34);
		triesEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				String strAux = triesEditText.getText().toString();
				if(strAux.length() > 1){
					triesEditText.setText("");
				} else if (strAux.length() == 1){
					verifyMainEditText();
				}
			}
		});
	}
	
	private void verifyMainEditText() {
		int countLetter = 0;
		char letter = triesEditText.getText().toString().charAt(0);
		char[] oldUnderlines = Utils.stringToArrayChar(underlinesTextView.getText().toString());
		boolean alreadyVerified = false;
		for (int i = 0; i < word.length(); i++) {
			String aux = word.charAt(i) + "";
			if(aux.equalsIgnoreCase(letter + "")){
				countLetter++;
				if (oldUnderlines[2*i] == '_'){
					oldUnderlines[2*i] = letter;
				} else {
					alreadyVerified = true;
				}
			}
		}
		if(countLetter == 0){
			if (pontuation > 0){
				pontuation--;
				setPontuationInScreen();
			}
			numberOfTries++;
			mountNumberOfErrorsString();
			mountWrongLettersString(letter);
			verifyDefeat();
		} else {
			if (!alreadyVerified){
				pontuation += 2 * countLetter;
				setPontuationInScreen();
				underlinesTextView.setText(Utils.arrayCharToString(oldUnderlines).toUpperCase());
				verifyWin();
			}
		}
		triesEditText.setText("");
	}

	private void verifyWin() {
		String underlines = underlinesTextView.getText().toString();
		if(!underlines.contains("_")){
			mountBundleParams(Constants.WIN);
		}
	}

	private void verifyDefeat() {
		if(numberOfTries == MAX_TRIES){
			mountBundleParams(Constants.LOSE);
		}
	}
	
	private void mountBundleParams(int winOrLose){
		Bundle params = new Bundle();
		params.putInt(Constants.WIN_OR_LOSE, winOrLose);
		params.putString(Constants.WORD_KEY, this.word);
		params.putInt(Constants.PONTUATION, this.pontuation);
		callEndGameScreen(params);
	}
	
	private void callEndGameScreen(Bundle params){
		Intent endGameIntent = new Intent(this, EndGameScreen.class);
		endGameIntent.putExtras(params);
		startActivity(endGameIntent);
	}
	
	@Override
	public void onBackPressed(){
		
	}
	
}
