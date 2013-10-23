package ru.mail.projects.game.mechanics.impl;

public enum GameState {
	started("Game started!"),
	endedFirstWon("Game ended, first player has won"),
	endedSecondWon("Game ended, second player has won"),
	endedFinished ("Game is Finished");
	
	String gameState;
	private GameState(String gameState){
		this.gameState = gameState;
	}
	@Override
	public String toString() {
		return gameState;
	}
}
