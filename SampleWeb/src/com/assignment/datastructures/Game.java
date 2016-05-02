package com.assignment.datastructures;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.assignment.Model;
import com.assignment.PrintUtil;
import com.assignment.Utils;

public class Game implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static List<Long> ids = new ArrayList<>();

	private static final String DB_Table_Name = "game";

	Long id = new Long(0);
	Integer size = new Integer(0);

	Long startTime = 0l;
	Long endTime = 0l;

	Team opponentTeam_1_Team = null;
	Team opponentTeam_2_Team = null;

	List<Long> opponent_1_PlayerJoinTimes = null;
	List<Long> opponent_2_PlayerJoinTimes = null;

	List<Event> events = null;

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Game() {
		// startTime = new Date();
	}

	public Game(Integer size) {
		super();
		this.size = size;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Team getOpponentTeam_1_Team() {
		return opponentTeam_1_Team;
	}

	public void setOpponentTeam_1_Team(Team opponentTeam_1_Team) {
		this.opponentTeam_1_Team = opponentTeam_1_Team;
	}

	public Team getOpponentTeam_2_Team() {
		return opponentTeam_2_Team;
	}

	public void setOpponentTeam_2_Team(Team opponentTeam_2_Team) {
		this.opponentTeam_2_Team = opponentTeam_2_Team;
	}

	public List<Long> getOpponent_1_PlayerJoinTimes() {
		return opponent_1_PlayerJoinTimes;
	}

	public void setOpponent_1_PlayerJoinTimes(List<Long> opponent_1_PlayerJoinTimes) {
		this.opponent_1_PlayerJoinTimes = opponent_1_PlayerJoinTimes;
	}

	public List<Long> getOpponent_2_PlayerJoinTimes() {
		return opponent_2_PlayerJoinTimes;
	}

	public void setOpponent_2_PlayerJoinTimes(List<Long> opponent_2_PlayerJoinTimes) {
		this.opponent_2_PlayerJoinTimes = opponent_2_PlayerJoinTimes;
	}

	// ##################################################################################################################

	public static Long insert(Game game) {

		Long id = -1l;
		if (Model.statement == null || Model.connect == null) {
			Model.init();
		}

		String query = Model.formInsertQuery(DB_Table_Name);

		try {
			PreparedStatement preparedStatement = Model.connect.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setLong(1, game.getStartTime());
			preparedStatement.setLong(2, game.getEndTime());
			preparedStatement.setObject(3, Utils.convertObjectToByteArray(game.getOpponentTeam_1_Team()));
			preparedStatement.setObject(4, Utils.convertObjectToByteArray(game.getOpponentTeam_2_Team()));
			preparedStatement.setInt(5, game.getSize());
			preparedStatement.setObject(6, Utils.convertObjectToByteArray(game.getEvents()));
			preparedStatement.setObject(7, Utils.convertObjectToByteArray(game.getOpponent_1_PlayerJoinTimes()));
			preparedStatement.setObject(8, Utils.convertObjectToByteArray(game.getOpponent_2_PlayerJoinTimes()));

			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getLong(1);
			}
			rs.close();

			ids.add(id);

		} catch (SQLException | IOException e) {
			PrintUtil.printException(e);
		}
		return id;
	}

	public static void update(Game game) {

		if (Model.statement == null || Model.connect == null) {
			Model.init();
		}

		String query = Model.formUpdateQuery(DB_Table_Name);

		try {

			PreparedStatement preparedStatement = Model.connect.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setLong(1, game.getStartTime());
			preparedStatement.setLong(2, game.getEndTime());
			preparedStatement.setObject(3, Utils.convertObjectToByteArray(game.getOpponentTeam_1_Team()));
			preparedStatement.setObject(4, Utils.convertObjectToByteArray(game.getOpponentTeam_2_Team()));
			preparedStatement.setInt(5, game.getSize());
			preparedStatement.setObject(6, Utils.convertObjectToByteArray(game.getEvents()));
			preparedStatement.setObject(7, Utils.convertObjectToByteArray(game.getOpponent_1_PlayerJoinTimes()));
			preparedStatement.setObject(8, Utils.convertObjectToByteArray(game.getOpponent_2_PlayerJoinTimes()));

			preparedStatement.setLong(9, game.getId()); // where
														// condition

			preparedStatement.executeUpdate();

		} catch (SQLException | IOException e) {
			PrintUtil.printException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Game> select(List<Long> ids) {

		try {
			String query = Model.formSelectQuery(DB_Table_Name, ids);

			PreparedStatement preparedStatement = Model.connect.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			List<Game> games = new ArrayList<Game>();

			while (resultSet.next()) {

				Game game = new Game();
				game.setId(resultSet.getLong(1));

				game.setStartTime(resultSet.getLong(2));
				game.setEndTime(resultSet.getLong(3));

				Blob team_1_Blob = resultSet.getBlob(4);
				Team team_1 = (Team) Utils.GetJavaObject(team_1_Blob);
				game.setOpponentTeam_1_Team(team_1);

				Blob team_2_Blob = resultSet.getBlob(5);
				Team team_2 = (Team) Utils.GetJavaObject(team_2_Blob);
				game.setOpponentTeam_2_Team(team_2);

				game.setSize(resultSet.getInt(6));

				Blob eventBlob = resultSet.getBlob(7);
				List<Event> events = (List<Event>) Utils.GetJavaObject(eventBlob);
				game.setEvents(events);

				Blob team_1_PlayerJoinTimes = resultSet.getBlob(8);
				List<Long> opponent_1_TeamPlayerJoinTimeStamp_ArrayList = (List<Long>) Utils
						.GetJavaObject(team_1_PlayerJoinTimes);
				game.setOpponent_1_PlayerJoinTimes(opponent_1_TeamPlayerJoinTimeStamp_ArrayList);

				Blob team_2_PlayerJoinTimes = resultSet.getBlob(9);
				List<Long> opponent_2_TeamPlayerJoinTimeStamp_ArrayList = (List<Long>) Utils
						.GetJavaObject(team_2_PlayerJoinTimes);
				game.setOpponent_2_PlayerJoinTimes(opponent_2_TeamPlayerJoinTimeStamp_ArrayList);

				games.add(game);
			}

			return games;

		} catch (SQLException | ClassNotFoundException | IOException e) {
			PrintUtil.printException(e);
			return null;
		}

	}

	public static void delete(List<Long> idValues) {

		String query = Model.formDeleteQuery(DB_Table_Name, idValues);

		PreparedStatement preparedStatement;
		try {
			preparedStatement = Model.connect.prepareStatement(query);
			preparedStatement.executeUpdate();

			if (idValues == null)
				ids.clear();
			else
				ids.removeAll(idValues);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getIdsFromDB() {
		String query = "SELECT" + " id ";
		query += "from" + " ";
		query += DB_Table_Name;

		PreparedStatement preparedStatement;
		try {
			preparedStatement = Model.connect.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Long id = resultSet.getLong(1);

				if (ids.contains(id) == false)
					ids.add(id);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", startTime_Date=" + startTime + ", endTime_Date=" + endTime
				+ ", opponentTeam_1_Team=" + opponentTeam_1_Team + ", opponentTeam_2_Team=" + opponentTeam_2_Team
				+ ", size=" + size + ", events=" + events + ", opponent_1_TeamPlayerJoinTimeStamp_ArrayList="
				+ opponent_1_PlayerJoinTimes + ", opponent_2_TeamPlayerJoinTimeStamp_ArrayList="
				+ opponent_2_PlayerJoinTimes + "]";
	}

	// ##########################################################################################
	// GAME PLAY //

	private static List<Team> availableTeams = new ArrayList<>();
	Map<Player, Long> opponent_1_playerMap = null;
	Map<Player, Long> opponent_2_playerMap = null;
	private static List<Long> TIME_STAMPS = new ArrayList<>();
	public static void setup() {
		availableTeams.addAll(new ArrayList<Team>(Team.select(null)));
	}

	public void start() {

		PrintUtil.printInNewLine("Game Began..");
		/// set game timestamps
		Date start = new Date();
		Date end = DateUtils.addMinutes(start, 3);
		this.startTime = start.getTime();
		this.endTime = end.getTime();
		
		for (Long i = this.startTime; i < this.endTime; i++)
			TIME_STAMPS.add(i);

		// set opponents teams
		this.opponentTeam_1_Team = getCompatibleTeam();
		this.opponentTeam_2_Team = getCompatibleTeam();
		// populate
		PrintUtil
				.printInNewLine(this.opponentTeam_1_Team.getName() + " == VS == " + this.opponentTeam_2_Team.getName());
		PrintUtil.printInNewLine(this.opponentTeam_1_Team.getName() + ":" + this.opponentTeam_1_Team.getPlayers());
		PrintUtil.printInNewLine(this.opponentTeam_2_Team.getName() + ":" + this.opponentTeam_2_Team.getPlayers());

		saveEvent(this.opponentTeam_1_Team.getName(), " == VS == ", this.opponentTeam_2_Team.getName());

		// set Player join times and health
		this.opponent_1_PlayerJoinTimes = new ArrayList<>();
		this.opponent_2_PlayerJoinTimes = new ArrayList<>();
		this.opponent_1_playerMap = new HashMap<>();
		this.opponent_2_playerMap = new HashMap<>();

		for (int i = 0; i < this.size; i++) {
			this.opponent_1_PlayerJoinTimes.add(Utils.getRandomObject(TIME_STAMPS));
			this.opponent_2_PlayerJoinTimes.add(Utils.getRandomObject(TIME_STAMPS));
		}

		for (Player player : this.opponentTeam_1_Team.getPlayers())
			this.opponent_1_playerMap.put(player, 1000l);
		for (Player player : this.opponentTeam_2_Team.getPlayers())
			this.opponent_2_playerMap.put(player, 1000l);

		// Events

		List<Statistic> actions = Statistic.select(null);

		while (true) {
			PrintUtil.printInNewLine("######## New Event ########");
			Event event = new Event();
			assignRoles(event);
			event.setAction(Utils.getRandomObject(actions));
			event.setStartTime(Utils.getRandomObject(TIME_STAMPS));
			Event.insert(event);

			for (Statistic attackerStat : event.getAttacker().getStatistics()) {
				if (attackerStat.getName().equals(event.getAction().getName())) {

					attackerStat.setValue(attackerStat.getValue() + 1);
					// populate
					PrintUtil.printInNewLine(event.getAttacker().getName() + " " + event.getAction().getName() + " "
							+ event.getDefender().getName());

					if (this.opponent_1_playerMap.containsKey(event.getDefender())) {
						long defender_health = this.opponent_1_playerMap.get(event.getDefender());
						defender_health -= attackerStat.getWeight() * defender_health / 100l;

						if (defender_health <= 0l) {// remove dead defender
							this.opponent_1_playerMap.remove(event.getDefender());
							// populate
							PrintUtil.printInNewLine(event.getDefender().getName() + " died :(");
							saveEvent(event.getDefender().getName(), "has ", "died :(");

							PrintUtil.printInNewLine(this.opponentTeam_1_Team.getName() + " has "
									+ this.opponent_1_playerMap.size() + " players alive !!");

							saveEvent(this.opponentTeam_1_Team.getName() + " has ",
									String.valueOf(this.opponent_1_playerMap.size()), " players alive !!");
						} else {
							this.opponent_1_playerMap.put(event.getDefender(), defender_health);
							// populate
							PrintUtil.printInNewLine(
									event.getDefender().getName() + " took attack. [Health : " + defender_health + "]");
							saveEvent(event.getDefender().getName(), " took attack. ",
									"[Health : " + defender_health + "]");
						}

					} else {
						long defender_health = this.opponent_2_playerMap.get(event.getDefender());
						defender_health -= attackerStat.getWeight() * defender_health / 100l;

						if (defender_health <= 0l) { // remove dead defender
							this.opponent_2_playerMap.remove(event.getDefender());
							// populate
							PrintUtil.printInNewLine(event.getDefender().getName() + " died :(");
							saveEvent(event.getDefender().getName(), "has ", "died :(");
							PrintUtil.printInNewLine(this.opponentTeam_2_Team.getName() + " has "
									+ this.opponent_2_playerMap.size() + " players alive !!");
							saveEvent(this.opponentTeam_2_Team.getName() + " has ",
									String.valueOf(this.opponent_2_playerMap.size()), " players alive !!");
						} else {
							this.opponent_2_playerMap.put(event.getDefender(), defender_health);
							// populate
							PrintUtil.printInNewLine(
									event.getDefender().getName() + " took attack. [Health : " + defender_health + "]");
							saveEvent(event.getDefender().getName(), " took attack. ",
									"[Health : " + defender_health + "]");
						}
					}
				}
			}

			// event occurred now evaluate event and assign achievements if
			// applicable
			for (Achievement achievement : Achievement.select(null)) {

				int count = 0;

				for (Statistic achievementStatistic : achievement.getCriteria()) {

					for (Statistic playerStatistic : event.getAttacker().getStatistics()) {

						if (achievement.getCriteria().contains(playerStatistic)) {
							if (playerStatistic.getValue() > achievementStatistic.getValue())
								count++;
						}
					}
				}

				if (count == achievement.getCriteria().size()) {
					if (event.getAttacker().getAchievements().contains(achievement) == false) {
						event.getAttacker().getAchievements().add(achievement);
						// populate
						PrintUtil.printInNewLine(
								event.getAttacker().getName() + " got achievement " + achievement.getName());
						saveEvent(event.getAttacker().getName() , " got achievement " , achievement.getName());
						Player.update(event.getAttacker());
					}
				}

			}

			// finish game condition
			if (this.opponent_1_playerMap.isEmpty()) {
				// populate
				PrintUtil.printInNewLine(this.opponentTeam_2_Team.getName() + " wins !!");
				saveEvent(this.opponentTeam_2_Team.getName() , " wins !!" , null);
				break;

			} else if (this.opponent_2_playerMap.isEmpty()) {
				// populate
				PrintUtil.printInNewLine(this.opponentTeam_1_Team.getName() + " wins !!");

				break;
			}
		}

		PrintUtil.printInNewLine("Game ended..");
		availableTeams.add(this.opponentTeam_1_Team);
		availableTeams.add(this.opponentTeam_2_Team);

	}

	private void saveEvent(String attacker, String action, String defender) {
		Event event = new Event();
		event.setStartTime(Utils.getRandomObject(TIME_STAMPS));
		event.setAttacker(new Player(attacker));
		event.setAction(new Statistic(action));
		event.setDefender(new Player(defender));
		Event.insert(event);
		
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void assignRoles(Event event) {
		List<Integer> turnToAttack = new ArrayList<>();
		turnToAttack.add(1);
		turnToAttack.add(2);

		if (Utils.getRandomObject(turnToAttack) == 1l) {
			event.setAttacker(Utils.getRandomObject(this.opponent_1_playerMap.keySet()));
			event.setDefender(Utils.getRandomObject(this.opponent_2_playerMap.keySet()));

		} else {
			event.setAttacker(Utils.getRandomObject(this.opponent_2_playerMap.keySet()));
			event.setDefender(Utils.getRandomObject(this.opponent_1_playerMap.keySet()));
		}

	}

	private Team getCompatibleTeam() {

		while (true) {
			Iterator<Team> itr = availableTeams.listIterator();
			while (itr.hasNext()) {
				Team t = itr.next();
				if (t.getSize() == this.size) {
					itr.remove();
					return t;
				}
			}
		}
	}
}
