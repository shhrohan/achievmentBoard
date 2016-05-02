package com.assignment;

import com.assignment.datastructures.Game;

public class Simulation extends Thread {

	int gameSize = -1;

	public Simulation() {
	}

	public Simulation(int size) {
		this.gameSize = size;
	}

	@Override
	public void run() {
		Game game = new Game(this.gameSize);
		game.start();
	}

}
