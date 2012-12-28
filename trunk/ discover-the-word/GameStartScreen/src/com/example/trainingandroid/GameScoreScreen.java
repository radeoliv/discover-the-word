package com.example.trainingandroid;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.trainingandroid.constants.Constants;
import com.example.trainingandroid.database.DTWScoreHelpenDatabase;
import com.example.trainingandroid.entity.ScoreEntity;
import com.example.trainingandroid.util.Utils;

public class GameScoreScreen extends ListActivity {
	
	private int pontuation;
	
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
			pontuation = params.getInt(Constants.PONTUATION);
			callDialog();
		}
		
	}

	// TODO Método nao implementado!!
	private void callDialog() {
		
	}

	private void showScore(){
		DTWScoreHelpenDatabase db = new DTWScoreHelpenDatabase(this);
		List<ScoreEntity> list = db.getAllScores();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, Utils.mountStringsListScore(list));

		setListAdapter(adapter);
	}
	
	public void onClickBackToTitle(View v){
		this.finish();
	}

	public int getPontuation() {
		return pontuation;
	}

	public void setPontuation(int pontuation) {
		this.pontuation = pontuation;
	}

}
