package com.assignment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class Utils {

	int getRandom(int seed) {

		// Random r = new Random();
		// r.
		//

		return 01;

	}

	public static byte[] convertObjectToByteArray(Object obj) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

		objectOutputStream.writeObject(obj);
		return byteArrayOutputStream.toByteArray();
	}

	public static long saveObject(Connection con, Object javaObject) {

		byte[] byteArray = null;
		PreparedStatement preparedStatement = null;
		String SQLQUERY_TO_SAVE_JAVAOBJECT = "INSERT INTO persist_java_objects(object_name, java_object) VALUES (?, ?)";
		int persistObjectID = -1;
		try {

			byteArray = convertObjectToByteArray(javaObject);
			preparedStatement = con.prepareStatement(SQLQUERY_TO_SAVE_JAVAOBJECT,
					PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, javaObject.getClass().getName());
			preparedStatement.setBytes(2, byteArray);
			preparedStatement.executeUpdate();

			System.out.println("Query - " + SQLQUERY_TO_SAVE_JAVAOBJECT
					+ " is successfully executed for Java object serialization ");

			// Trying to get the Generated Key
			ResultSet rs = preparedStatement.getGeneratedKeys();

			if (rs.next()) {
				persistObjectID = rs.getInt(1);
				System.out.println("Object ID while saving the binary object is->" + persistObjectID);
			}

			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return persistObjectID;
	}

	public static Object GetJavaObject(Blob team) throws SQLException, IOException, ClassNotFoundException {
		byte[] bytes = team.getBytes(1, (int) (team.length()));

		ObjectInputStream objectInputStream = null;
		if (bytes != null)
			objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));

		Object retrievingObject = objectInputStream.readObject();
		return retrievingObject;
	}

	public static <T> T getRandomObject(Collection<T> coll) {
		
		int num = (int) (Math.random() * coll.size());
		for (T t : coll)
			if (--num < 0)
				return t;
		throw new AssertionError();
	}

}
