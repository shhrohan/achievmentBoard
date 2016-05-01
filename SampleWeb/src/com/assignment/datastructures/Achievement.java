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

import com.assignment.Model;
import com.assignment.PrintUtil;
import com.assignment.Utils;

public class Achievement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String DB_Table_Name = "achievement";
	public static List<Long> ids = new ArrayList<>();

	private Long id = new Long(0);
	private String name = "";
	private List<Statistic> criteria = new ArrayList<>();

	public Achievement() {
		// TODO Auto-generated constructor stub
	}

	public Achievement(String name, List<Statistic> requirements) {
		super();
		this.name = name;
		this.criteria = requirements;
	}

	@Override
	public String toString() {
		return "Achievement [id=" + id + ", name=" + name + ", criteria=" + criteria + "]";
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

	public List<Statistic> getCriteria() {
		return criteria;
	}

	public void setCriteria(List<Statistic> criteria) {
		this.criteria = criteria;
	}

	public static Long insert(Achievement achievement) {

		Long id = -1l;
		if (Model.statement == null || Model.connect == null) {
			Model.init();
		}

		String query = Model.formInsertQuery(DB_Table_Name);

		try {
			PreparedStatement preparedStatement = Model.connect.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, achievement.getName());
			preparedStatement.setObject(2, Utils.convertObjectToByteArray(achievement.getCriteria()));

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

	public static void update(Achievement achievement) {

		if (Model.statement == null || Model.connect == null) {
			Model.init();
		}

		String query = Model.formUpdateQuery(DB_Table_Name);

		try {

			PreparedStatement preparedStatement = Model.connect.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, achievement.getName());
			preparedStatement.setObject(2, Utils.convertObjectToByteArray(achievement.getCriteria()));

			preparedStatement.setObject(3, achievement.getId()); // where
																	// condition

			preparedStatement.executeUpdate();

		} catch (SQLException | IOException e) {
			PrintUtil.printException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Achievement> select(List<Long> ids) {

		try {
			String query = Model.formSelectQuery(DB_Table_Name, ids);

			PreparedStatement preparedStatement = Model.connect.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			List<Achievement> achievements = new ArrayList<Achievement>();

			while (resultSet.next()) {

				Achievement achievement = new Achievement();

				achievement.setId(resultSet.getLong(1));
				achievement.setName(resultSet.getString(2));

				Blob criteriaBlob = resultSet.getBlob(3);
				List<Statistic> criteria = (ArrayList<Statistic>) Utils.GetJavaObject(criteriaBlob);
				achievement.setCriteria(criteria);

				achievements.add(achievement);

			}

			return achievements;

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
			
			while(resultSet.next()){
				
				Long id = resultSet.getLong(1);
				
				if(ids.contains(id)==false)
					ids.add(id);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

}
