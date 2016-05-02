package com.assignment;

import java.util.ArrayList;
import java.util.List;

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

		int i = 0;
		List<Integer> sizes = new ArrayList<>();
		sizes.add(3);
		sizes.add(4);
		
		while (i++ < 20) {
			Simulation simulation = new Simulation(Utils.getRandomObject(sizes));
			synchronized (simulation) {
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
