package connection;

import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionWarehouse {

    private static final Logger LOGGER = Logger.getLogger(ConnectionWarehouse.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/schooldb";
    private static final String USER = "root";
    private static final String PASS = "Informatic@1";


    private static ConnectionWarehouse singleInstance=new ConnectionWarehouse();

    /**
     * Contructor for the ConnectionWarehouse class, which is the class that helps the app to connect to the database
     *
     */
    private  ConnectionWarehouse()
    {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Returns the connection to the database which will be used in the DAO classes
     * @return connection - the connection to the database
     */
    private Connection createConnection()
    {
        Connection connection=null;
        try {
            connection=DriverManager.getConnection(DBURL,USER,PASS);
            System.out.println(" Connection succesful");

        }catch (SQLException e)
        {
            LOGGER.log(Level.WARNING,"An error occured while trying to connect to database");
            e.printStackTrace();
        }

        return connection;
    }

    /***
     * Returns the connection to the database
     * @return connection - the connection to the database
     */
    public static Connection getConnection()
    {
        return singleInstance.createConnection();
    }

    /**
     * Close the connection to the database, used in the DAO classes
     * @param connection - Connection to the data base
     */
    public static void close(Connection connection)
    {
        if(connection!=null)
        {
            try {
                connection.close();
            }catch(SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the database");
            }
        }
    }

    /**
     * Close the statement, used in the DAO classes
     * @param statement - the statement of the connection
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
            }
        }
    }

    /**
     * Close the resultSet after extracting the data, used in the DAO classes
     * @param resultSet - the ResultSet of the connection and statement
     */
    public static void close(ResultSet resultSet)
    {
        if(resultSet!=null)
        {
            try {
                resultSet.close();
            }catch(SQLException e)
            {
                LOGGER.log(Level.WARNING,"An error occured while trying to close the ResultSet");

            }
        }
    }


}
