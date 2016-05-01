package com.assignment;

import java.util.ArrayList;
import java.util.List;

import com.assignment.datastructures.Achievement;
import com.assignment.datastructures.Event;
import com.assignment.datastructures.Game;
import com.assignment.datastructures.Player;
import com.assignment.datastructures.Statistic;
import com.assignment.datastructures.Team;

public class SetUpInitialData {

	public static void main(String[] args) {

		// Clear all pre existed Data from all the Object Tables;

		initializeDBHandlers();
		purgeAllTables();
		initializeTablesFromConfig();

	}

	public static void initializeTablesFromConfig() {

		// Statistic Object
		initiatizeStatisticsFromConfig();
		PrintUtil.printInNewLine("Statistics reset done.");

		// achievement Object
		initiatizeAchievementsFromConfig();
		PrintUtil.printInNewLine("Achievements reset done.");

		// player Object
		initiatizePlayersFromConfig();
		PrintUtil.printInNewLine("Players reset done.");

		// team object
		initializeTeamsFromConfig();
		PrintUtil.printInNewLine("Teams reset done.");
	}

	private static void initializeTeamsFromConfig() {

		String teams = Model.config.getProperty("teams");
		List<Player> players = Player.select(null);
		List<Player> teamPlayers;
		String teamName;

		teamName = teams.split(",")[0];
		teamPlayers = new ArrayList<>(players.subList(0, 3));
		Team team = new Team(teamName, teamPlayers, teamPlayers.size());
		Team.insert(team);

		teamName = teams.split(",")[1];
		teamPlayers = new ArrayList<>(players.subList(3, 6));
		team = new Team(teamName, teamPlayers, teamPlayers.size());
		Team.insert(team);

		teamName = teams.split(",")[2];
		teamPlayers = new ArrayList<>(players.subList(6, 10));
		team = new Team(teamName, teamPlayers, teamPlayers.size());
		Team.insert(team);

		teamName = teams.split(",")[3];
		teamPlayers = new ArrayList<>(players.subList(10, 14));
		team = new Team(teamName, teamPlayers, teamPlayers.size());
		Team.insert(team);

	}

	private static void initiatizePlayersFromConfig() {
		String players = Model.config.getProperty("players");

		List<Statistic> statistics = Statistic.select(null);

		for (String player : players.split(",")) {
			Player.insert(new Player(player, statistics));
		}

	}

	private static void initiatizeAchievementsFromConfig() {
		String achievements = Model.config.getProperty("achievements");
		String achievement;
		ArrayList<Long> ids;
		List<Statistic> stats;

		achievement = achievements.split(",")[0];

		ids = new ArrayList<>();
		ids.add(Statistic.ids.get(0));
		ids.add(Statistic.ids.get(2));
		stats = Statistic.select(ids);

		stats.get(0).setValue(100l);
		stats.get(1).setValue(75l);

		Achievement.insert(new Achievement(achievement, stats));

		achievement = achievements.split(",")[1];
		ids = new ArrayList<>();
		ids.add(Statistic.ids.get(1));
		stats = Statistic.select(ids);
		stats.get(0).setValue(500l);
		Achievement.insert(new Achievement(achievement, stats));
	}

	private static void initiatizeStatisticsFromConfig() {
		String statistics = Model.config.getProperty("gamestatistics");

		Statistic.insert(new Statistic(statistics.split(",")[0], 25d, 0l));
		Statistic.insert(new Statistic(statistics.split(",")[1], 100d, 0l));
		Statistic.insert(new Statistic(statistics.split(",")[2], 33d, 0l));
	}

	public static void initializeDBHandlers() {

		Model.init();
	}

	public static void purgeAllTables() {

		PrintUtil.printInNewLine("purging all object tables...");
		Statistic.delete(null);
		Achievement.delete(null);
		Player.delete(null);
		Team.delete(null);
		Game.delete(null);
		Event.delete(null);

		PrintUtil.printInNewLine("All Object tables purged.");
	}

}
