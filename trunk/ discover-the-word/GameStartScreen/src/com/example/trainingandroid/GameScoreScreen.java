package com.example.trainingandroid;

import java.util.List;

import com.example.trainingandroid.database.DTWScoreHelpenDatabase;
import com.example.trainingandroid.entity.ScoreEntity;
import com.example.trainingandroid.util.Utils;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GameScoreScreen extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_score_screen);
		
//		ListView listView = (ListView)findViewById(android.R.id.list);
		
		DTWScoreHelpenDatabase db = new DTWScoreHelpenDatabase(this);
		List<ScoreEntity> list = db.getAllScores();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, Utils.mountStringsListScore(list));

		setListAdapter(adapter);
	}
	
	public void onClickBackToTitle(View v){
		this.finish();
	}

}
