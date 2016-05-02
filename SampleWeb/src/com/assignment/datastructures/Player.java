package com.assignment.datastructures;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.assignment.model.Model;
import com.assignment.utils.PrintUtil;
import com.assignment.utils.Utils;

public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static List<Long> ids = new ArrayList<>();

	private static String DB_Table_Name = "player";
	private Long id = new Long(0);
	private String name = "";
	private List<Achievement> achievements = new ArrayList<>();
	private List<Statistic> statistics = new ArrayList<>();

	public Player() {
		// TODO Auto-generated constructor stub
	}

	public Player(String name, List<Statistic> statistics) {
		super();
		this.name = name;
		this.statistics = statistics;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + "]";
	}

	public Player(String name) {
		super();
		this.name = name;
	}

	public List<Statistic> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<Statistic> statistics) {
		this.statistics = statistics;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Achievement> getAchievements() {
		return achievements;
	}

	public void setAchievements(List<Achievement> achievements) {
		this.achievements = achievements;
	}

	public static Long insert(Player player) {

		Long id = -1l;
		if (Model.statement == null || Model.connect == null) {
			Model.init();
		}

		String query = Model.formInsertQuery(DB_Table_Name);

		try {
			PreparedStatement preparedStatement = Model.connect.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setObject(1, player.getName());
			preparedStatement.setObject(2, Utils.convertObjectToByteArray(player.getStatistics()));
			preparedStatement.setObject(3, Utils.convertObjectToByteArray(player.getAchievements()));

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

	public static void update(Player player) {

		if (Model.statement == null || Model.connect == null) {
			Model.init();
		}

		String query = Model.formUpdateQuery(DB_Table_Name);

		try {

			PreparedStatement preparedStatement = Model.connect.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setObject(1, player.getName());
			preparedStatement.setObject(2, Utils.convertObjectToByteArray(player.getStatistics()));
			preparedStatement.setObject(3, Utils.convertObjectToByteArray(player.getAchievements()));

			preparedStatement.setObject(4, player.getId()); // where
															// condition

			preparedStatement.executeUpdate();

		} catch (SQLException | IOException e) {
			PrintUtil.printException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Player> select(List<Long> ids) {

		try {
			String query = Model.formSelectQuery(DB_Table_Name, ids);

			PreparedStatement preparedStatement = Model.connect.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			List<Player> players = new ArrayList<Player>();

			while (resultSet.next()) {

				Player player = new Player();
				player.setId(resultSet.getLong(1));
				player.setName(resultSet.getString(2));

				Blob statisticBlob = resultSet.getBlob(3);
				ArrayList<Statistic> playerstatistics = (ArrayList<Statistic>) Utils.GetJavaObject(statisticBlob);
				player.setStatistics(playerstatistics);

				Blob achievementBlob = resultSet.getBlob(4);
				ArrayList<Achievement> playerAchievements = (ArrayList<Achievement>) Utils
						.GetJavaObject(achievementBlob);
				player.setAchievements(playerAchievements);

				players.add(player);
			}

			return players;

		} catch (SQLException | IOException | ClassNotFoundException e) {
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

}
