package com.assignment.datastructures;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.assignment.model.Model;
import com.assignment.utils.PrintUtil;

public class Statistic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static List<Long> ids = new ArrayList<>();
	private Long id;
	private String name;
	private Double weight;
	private Long value;

	private static String DB_Table_Name = "statistic";

	public Statistic() {
		// TODO Auto-generated constructor stub
	}

	public Statistic(String name, Double weight, Long value) {

		this.name = name;
		this.weight = weight;
		this.value = value;

	}

	public Statistic(String name) {
		super();
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Statistic other = (Statistic) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Statistic [id=" + id + ", name=" + name + ", weight=" + weight + ", value=" + value + "]";
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

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public static Long insert(Statistic statistic) {

		Long id = -1l;
		if (Model.statement == null || Model.connect == null) {
			Model.init();
		}

		String query = Model.formInsertQuery(DB_Table_Name);

		try {
			PreparedStatement preparedStatement = Model.connect.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setObject(1, statistic.getName());
			preparedStatement.setObject(2, statistic.getWeight());
			preparedStatement.setObject(3, statistic.getValue());

			preparedStatement.executeUpdate();

			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getLong(1);
			}
			rs.close();
			ids.add(id);

		} catch (SQLException e) {
			PrintUtil.printException(e);
		}
		return id;
	}

	public static void update(Statistic statistic) {

		if (Model.statement == null || Model.connect == null) {
			Model.init();
		}

		String query = Model.formUpdateQuery(DB_Table_Name);

		try {

			PreparedStatement preparedStatement = Model.connect.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setObject(1, statistic.getName());
			preparedStatement.setObject(2, statistic.getWeight());
			preparedStatement.setObject(3, statistic.getValue());

			preparedStatement.setObject(4, statistic.getId()); // where
																// condition

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			PrintUtil.printException(e);
		}
	}

	public static List<Statistic> select(List<Long> ids) {

		try {
			String query = Model.formSelectQuery(DB_Table_Name, ids);

			PreparedStatement preparedStatement = Model.connect.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			List<Statistic> statistics = new ArrayList<Statistic>();

			while (resultSet.next()) {

				Statistic statistic = new Statistic();
				statistic.setId(resultSet.getLong(1));
				statistic.setName(resultSet.getString(2));
				statistic.setWeight(resultSet.getDouble(3));
				statistic.setValue(resultSet.getLong(4));

				statistics.add(statistic);
			}

			return statistics;

		} catch (SQLException e) {
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
