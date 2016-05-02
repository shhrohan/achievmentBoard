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

public class Team implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static List<Long> ids = new ArrayList<>();

	private static String DB_Table_Name = "team";

	private Long id = new Long(0);
	private String name = "";
	private List<Player> players = new ArrayList<Player>();
	private Integer size = 0;

	public Team() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", players=" + players + ", size=" + size + "]";
	}

	public Team(String name, List<Player> players, Integer size) {
		super();
		this.name = name;
		this.players = players;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public static Long insert(Team team) {

		Long id = -1l;
		if (Model.statement == null || Model.connect == null) {
			Model.init();
		}

		String query = Model.formInsertQuery(DB_Table_Name);

		try {
			PreparedStatement preparedStatement = Model.connect.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setObject(1, team.getName());
			preparedStatement.setObject(2, Utils.convertObjectToByteArray(team.getPlayers()));
			preparedStatement.setInt(3, team.getSize());

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

	public static void update(Team team) {

		if (Model.statement == null || Model.connect == null) {
			Model.init();
		}

		String query = Model.formUpdateQuery(DB_Table_Name);

		try {

			PreparedStatement preparedStatement = Model.connect.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, team.getName());
			preparedStatement.setObject(2, Utils.convertObjectToByteArray(team.getPlayers()));
			preparedStatement.setInt(3, team.getSize());

			preparedStatement.setLong(4, team.getId()); // where
														// condition

			preparedStatement.executeUpdate();

		} catch (SQLException | IOException e) {
			PrintUtil.printException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Team> select(List<Long> ids) {

		if (Model.statement == null || Model.connect == null) {
			Model.init();
		}
		try {
			String query = Model.formSelectQuery(DB_Table_Name, ids);

			PreparedStatement preparedStatement = Model.connect.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			List<Team> teams = new ArrayList<Team>();

			while (resultSet.next()) {

				Team team = new Team();
				team.setId(resultSet.getLong(1));
				team.setName(resultSet.getString(2));

				Blob playersBlob = resultSet.getBlob(3);
				List<Player> players = (ArrayList<Player>) Utils.GetJavaObject(playersBlob);
				team.setPlayers(players);

				team.setSize(resultSet.getInt(4));

				teams.add(team);
			}

			return teams;

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
}
