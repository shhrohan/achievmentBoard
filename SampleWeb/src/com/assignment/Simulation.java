package com.assignment;

import com.assignment.datastructures.Game;

public class Simulation {

	public static void main(String[] args) {

		Game.setup();

		Game game = new Game(3);

		game.start();
		game = new Game(4);

		game.start();

	}

}
