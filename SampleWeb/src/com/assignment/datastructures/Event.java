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

public class Event implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String DB_Table_Name = "event";
	public static List<Long> ids = new ArrayList<>();

	private Long id = new Long(0);
	private Long startTime = new Long(0l);
	private Player attacker = new Player();
	private Player defender = new Player();
	private Statistic action = new Statistic();

	public Event() {
		// TODO Auto-generated constructor stub
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

	public Player getAttacker() {
		return attacker;
	}

	public void setAttacker(Player attacker) {
		this.attacker = attacker;
	}

	public Player getDefender() {
		return defender;
	}

	public void setDefender(Player defender) {
		this.defender = defender;
	}

	public Statistic getAction() {
		return action;
	}

	public void setAction(Statistic action) {
		this.action = action;
	}

	public static Long insert(Event event) {

		Long id = -1l;
		if (Model.statement == null || Model.connect == null) {
			Model.init();
		}

		String query = Model.formInsertQuery(DB_Table_Name);

		try {
			PreparedStatement preparedStatement = Model.connect.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setLong(1,  event.getStartTime());
			preparedStatement.setObject(2, Utils.convertObjectToByteArray(event.getAttacker()));
			preparedStatement.setObject(3, Utils.convertObjectToByteArray(event.getDefender()));
			preparedStatement.setObject(4, Utils.convertObjectToByteArray(event.getAction()));

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

	public static void update(Event event) {

		if (Model.statement == null || Model.connect == null) {
			Model.init();
		}

		String query = Model.formUpdateQuery(DB_Table_Name);

		try {

			PreparedStatement preparedStatement = Model.connect.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setLong(1, event.getStartTime());
			preparedStatement.setObject(2, Utils.convertObjectToByteArray(event.getAttacker()));
			preparedStatement.setObject(3, Utils.convertObjectToByteArray(event.getDefender()));
			preparedStatement.setObject(4, Utils.convertObjectToByteArray(event.getAction()));

			preparedStatement.setObject(5, event.getId()); // where
															// condition

			preparedStatement.executeUpdate();

		} catch (SQLException | IOException e) {
			PrintUtil.printException(e);
		}
	}

	public static List<Event> select(List<Long> idValues) {

		try {
			if (Model.statement == null || Model.connect == null) {
				Model.init();
			}
			String query = Model.formSelectQuery(DB_Table_Name, idValues);

			PreparedStatement preparedStatement = Model.connect.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			List<Event> events = new ArrayList<Event>();

			while (resultSet.next()) {

				Event event = new Event();
				event.setId(resultSet.getLong(1));

				event.setStartTime(resultSet.getLong(2));

				Blob attackerBlob = resultSet.getBlob(3);
				Player attacker = (Player) Utils.GetJavaObject(attackerBlob);
				event.setAttacker(attacker);

				Blob defenderBlob = resultSet.getBlob(4);
				Player defender = (Player) Utils.GetJavaObject(defenderBlob);
				event.setDefender(defender);

				Blob actionBlob = resultSet.getBlob(5);
				Statistic action = (Statistic) Utils.GetJavaObject(actionBlob);
				event.setAction(action);

				events.add(event);
			}

			return events;

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
			PrintUtil.printException(e);
		}

	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", startTime=" + startTime + ", attacker=" + attacker
				+ ", defender=" + defender + ", action=" + action + "]";
	}
}
