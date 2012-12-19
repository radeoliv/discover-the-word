package com.example.trainingandroid.entity;

public class ScoreEntity {
	
	private long id;
	private String playerName;
	private int pontuation;
	
	public ScoreEntity(long id, String playerName, int pontuation){
		this.id = id;
		this.playerName = playerName;
		this.pontuation = pontuation;
	}

	public ScoreEntity(String playerName, int pontuation){
		this.playerName = playerName;
		this.pontuation = pontuation;
	}

	public ScoreEntity() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPontuation() {
		return pontuation;
	}

	public void setPontuation(int pontuation) {
		this.pontuation = pontuation;
	}
}
