package com.assignment;

import java.util.ArrayList;
import java.util.List;

import com.assignment.datastructures.Game;
import com.assignment.utils.Utils;

public class Main {

	public static void main(String[] args) {

		List<Integer> sizes = new ArrayList<>();
		sizes.add(3);
		sizes.add(4);
		
		while (true) {
			Game.setup();
			Simulation simulation = new Simulation(Utils.getRandomObject(sizes));
			synchronized (simulation) {
				simulation.start();
				try {
					simulation.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
