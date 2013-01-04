package com.example.trainingandroid;

import java.util.List;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.trainingandroid.constants.Constants;
import com.example.trainingandroid.database.DTWScoreHelpenDatabase;
import com.example.trainingandroid.entity.ScoreEntity;
import com.example.trainingandroid.util.Utils;

public class GameScoreScreen extends ListActivity {
	
	private int pontuation;
	private Dialog dialog;
	private EditText editText;
	private Button okButton;
	
	private DTWScoreHelpenDatabase database;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_score_screen);
		
		verifyIntent();
	}
	
	private void verifyIntent() {
		
		Intent it = getIntent();
		Bundle params = it.getExtras();
		
		if (params == null){
			showScore();
		} else {
			verifyScore(params);
		}
		
	}

	private void verifyScore(Bundle params) {
		pontuation = params.getInt(Constants.PONTUATION);
		database = new DTWScoreHelpenDatabase(this);
		List<ScoreEntity> list = database.getAllScores();
		ScoreEntity lastScore = list.get(list.size() - 1);
		int lastScorePontuation = lastScore.getPontuation();
		if(pontuation > lastScorePontuation){
			callDialog(lastScore);
		} else {
			showScore();
		}
	}

	private void callDialog(final ScoreEntity lastScore) {
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.score_dialog_name);
		
		editText = (EditText)dialog.findViewById(R.id.inputText);
		
		okButton = (Button)getDialog().findViewById(R.id.bt_ok);
		okButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String text = editText.getText().toString();
				if (text != null && !text.equals("")){
					GameScoreScreen.this.updateScore(text, lastScore);
				}
			}
		});
		
		dialog.show();
		
	}

	protected void updateScore(String playerName, ScoreEntity lastScore) {
		database.deleteScore(lastScore, database.getWritableDatabase());
		ScoreEntity newScore = new ScoreEntity(playerName, pontuation);
		database.insertScore(newScore, database.getWritableDatabase());
		showScore();
	}

	private void showScore(){
		DTWScoreHelpenDatabase db = new DTWScoreHelpenDatabase(this);
		List<ScoreEntity> list = db.getAllScores();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, Utils.mountStringsListScore(list));
		
		setListAdapter(adapter);
		
		if (dialog != null){
			dialog.dismiss();
		}
	}
	
	public int getPontuation() {
		return pontuation;
	}

	public void setPontuation(int pontuation) {
		this.pontuation = pontuation;
	}

	public Dialog getDialog() {
		return dialog;
	}

	public void setDialog(Dialog dialog) {
		this.dialog = dialog;
	}

}
