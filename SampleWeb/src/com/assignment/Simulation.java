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

	public static void main(String[] args) {

		Game.setup();

		int i = 1;
		while (i < 4) {
			Simulation simulation = new Simulation(4);
			synchronized (simulation) {

				System.out.println("##################### Run " + i++ + " ######################");
				simulation.start();
				try {
					simulation.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
