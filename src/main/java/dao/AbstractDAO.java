package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.String;

import connection.ConnectionWarehouse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;
	Connection connection = null;
	PreparedStatement statement = null;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	}

	private String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE " + field + " =?");
		return sb.toString();
	}

	/***
	 * afiseaza datele dintr-un tabel
	 * @return null
	 */
	public List<T> findAll() {
		String s = "SELECT * FROM " + type.getSimpleName();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = ConnectionWarehouse.getConnection();
			statement = connection.prepareStatement(s);
			resultSet = statement.executeQuery();
			return createObjects(resultSet);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DA:findAll " + e.getMessage());
		} finally {
			ConnectionWarehouse.close(resultSet);
			ConnectionWarehouse.close(statement);
			ConnectionWarehouse.close(connection);
		}
		return null;
	}

	/**
	 * Extracts the object by an id from a table in the database
	 * @param id - a unique ID of an Object
	 * @return Object
	 */
	public T findById(int id) {
		ResultSet resultSet = null;
		String query = createSelectQuery("id");
		try {
			connection = ConnectionWarehouse.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			return createObjects(resultSet).get(0);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionWarehouse.close(resultSet);
			ConnectionWarehouse.close(statement);
			ConnectionWarehouse.close(connection);
		}
		return null;
	}

	/**
	 * Converts the data from the table into a list of Objects
	 * @param resultSet
	 * @return List of Objects (Client,Product,Order)
	 */
	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		Constructor[] ctors = type.getDeclaredConstructors();
		Constructor ctor = null;
		for (int i = 0; i < ctors.length; i++) {
			ctor = ctors[i];
			if (ctor.getGenericParameterTypes().length == 0)
				break;
		}
		try {
			while (resultSet.next()) {
				ctor.setAccessible(true);
				T instance = (T)ctor.newInstance();
				for (Field field : type.getDeclaredFields()) {
					//System.out.println(type.getDeclaredFields());
					String fieldName = field.getName();
					//System.out.println(fieldName);
					Object value = resultSet.getObject(fieldName);
					//System.out.println(value);
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Converts the data from the table into a list of Strings
	 * @param object
	 * @return
	 */
	public String[] retrieveProperties(Object object) {
		String[] list = new String[100];
		int j = 0;
		for (Field field : object.getClass().getDeclaredFields()) {

			field.setAccessible(true);
			Object value;
			try {
				value = field.get(object);
				list[j] = value.toString();
				j++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * Extracts all the data from a table in the database
	 * @return List of Objects
	 */
	@SuppressWarnings("null")
	public Object[][] populateTable() {

		String query = "SELECT * from " + type.getSimpleName();

		connection= ConnectionWarehouse.getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		String[][] lista = new String[100][100];
		int i = 0;
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			List<T> list = new ArrayList<T>();
			list = createObjects(rs);
			for (Object o : list) {
				lista[i] =  retrieveProperties(o);
				i++;
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "AbstractDAO:populateTable" + e.getMessage());

		}
		finally {
			ConnectionWarehouse.close(rs);
			ConnectionWarehouse.close(ps);
		}
		return lista;
	}

	/**
	 * create a list with the name of the tables's column
	 * @return list
	 */
	public Object[] columnNames() {

		int n = 0;
		int i = 0;
		String[] list;

		for (Field field : type.getDeclaredFields()) {
			n++;
		}
		list = new String[n];
		for (Field field : type.getDeclaredFields()) {
			list[i] = field.getName();
			i++;
		}
		return list;
	}

	/**
	 * Build the String used for insert the data into tables by an ID
	 * @param x - the column which you select by
	 * @return String - query
	 */
	public String insertStatement(T x)
	{
		StringBuilder query=new StringBuilder();
		int nrFields=0;
		query.append("INSERT INTO ");
		query.append(type.getSimpleName());
		query.append("( ");
		for(Field field: type.getDeclaredFields())
		{
			if(field.getName()!="id")
		{
			query.append(field.getName()+" ,");
			nrFields++;
		}
		}

		query.deleteCharAt(query.length()-1); //sterg ultima virgula
		query.append(" ) ");

		query.append("VALUES (");
		while(nrFields>0)
		{
			if(nrFields!=1)
				query.append("?,");
			else
				query.append("?)");

			nrFields--;
		}



		//System.out.println("Insert Statement: "+query.toString());
		return query.toString();

	}

	/**
	 * Inserts an Object into a specific table in the database
	 * @param x - the object corresponding to the table
	 */
	public  int insert(T x)
	{
		String query=insertStatement(x);
		int insertId=-1;
		connection= ConnectionWarehouse.getConnection();
		ResultSet rs=null;
		PreparedStatement ps=null;
		Object value=null;
		int insertIndex=1;
		try {
			ps=connection.prepareStatement(query, statement.RETURN_GENERATED_KEYS);
			for(Field f: type.getDeclaredFields())
			{
				f.setAccessible(true);
				if(f.getName().equals("id")==false)
				{
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(f.getName(), type);
					Method method = propertyDescriptor.getReadMethod();
					value=method.invoke(x);
					ps.setObject(insertIndex, value);
					insertIndex++;
				}
			}
			System.out.println("**********"+ps);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				insertId = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return insertId;
	}

	/**
	 * Build the String used to delete an Object from a specific table in the database
	 * @return String - query
	 */
	public String deleteStatement()
	{
		StringBuilder query=new StringBuilder();
		query.append("DELETE FROM " );
		query.append(type.getSimpleName());
		query.append(" where id= ?");
		return query.toString();
	}

	/**
	 * Deletes an Object from a specific table in the database
	 * @param id - the object's id corresponding to the table
	 */
	public void delete( int id)
	{
		Connection connection=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String query=deleteStatement();

		try {
			connection=ConnectionWarehouse.getConnection();
			ps=connection.prepareStatement(query);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
		} finally {
			ConnectionWarehouse.close(rs);
			ConnectionWarehouse.close(ps);
			ConnectionWarehouse.close(connection);
		}

	}

	/**
	 * Build the String used to delete an Object from a specific table in the database
	 * @return String - query
	 */
	public String updateStatement()
	{
		StringBuilder query =new StringBuilder();
		query.append(" UPDATE ");
		query.append(type.getSimpleName());
		query.append(" SET " );

		for(Field f: type.getDeclaredFields())
		{
			if(f.getName().equals("id")==false)
				query.append(f.getName()+" = ? ," );
		}
		query.deleteCharAt(query.length()-1);
		query.append(" where id  = ?");

		//System.out.println(query.toString());
		return query.toString();

	}
	/**
	 * Updates an Object into a specific table in the database
	 * @param x - the object corresponding to the table
	 */
	public void update(T x)
	{
		Connection connection=null;
		PreparedStatement ps=null;
		String query=updateStatement();
		int insertIndex=1;
		Object idObject=null;
		Object value=null;

		try {
			connection=ConnectionWarehouse.getConnection();
			ps=connection.prepareStatement(query);
			for(Field f: type.getDeclaredFields())
			{
				f.setAccessible(true);
				value=f.get(x);
				if(f.getName().equals("id")==false)
				{ps.setObject(insertIndex, value);
					insertIndex++;}
				else
				{
					idObject=value;
				}
			}
			ps.setObject(insertIndex, idObject);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}