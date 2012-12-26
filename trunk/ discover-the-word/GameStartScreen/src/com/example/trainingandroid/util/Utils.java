package com.example.trainingandroid.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.trainingandroid.GameStartScreen;
import com.example.trainingandroid.constants.Constants;
import com.example.trainingandroid.entity.ScoreEntity;

public class Utils {
	
	public static final String arrayCharToString(char[] array){
		String result = "";
		for(char a : array){
			result += a;
		}
		return result;
	}

	public static final char[] stringToArrayChar(String str){
		char[] result = new char[str.length()];
		for(int i = 0 ; i < str.length(); i++){
			result[i] = str.charAt(i);
		}
		return result;
	}
	
	public static final List<String> readFileAndPutWordsInList (Context context){
		List<String> list = new ArrayList<String>();
		String line = null;
		try {
			AssetManager am = context.getAssets();
			InputStream is = am.open(Constants.WORDS_TXT_PATH);
			InputStreamReader inputStreamReader = new InputStreamReader(is);
			BufferedReader buffReader = new BufferedReader(inputStreamReader);
			while ((line = buffReader.readLine()) != null){
				list.add(line.trim());
			}
		} catch (Exception e) {
			Log.e("DTW Error", e.getMessage());
		}
		return list;
	}
	
	public static final int returnRandomNumber(int listSize){
		Random random = new Random();
		int num = random.nextInt(listSize);
		return num;
	}
	
	public static final String[] mountStringsListScore(List<ScoreEntity> list){
		int size = list.size();
		String[] result = new String[size];
		for (int i = 0; i < size; i++) {
			ScoreEntity score = list.get(i);
			result[i] = score.getPlayerName() + " " + score.getPontuation();
		}
		return result;
	}
	
}
