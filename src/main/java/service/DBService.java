package service;

import model.Log;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;
import java.util.Properties;

@Service
public class DBService implements IDBService {

    private final String connectionUrl = "jdbc:h2:tcp://192.168.1.64:9092/~/test";
    private final String USER = "scott";
    private final String PASSWORD = "tiger";
    static final String JDBC_DRIVER = "org.h2.Driver";
    Connection connection;
    Statement statement;
    private final String INSERT_LOG = "insert into message_log " +
            "(created_at, first_name, message, second_name, user_id)" +
            " value (?,?,?,?,?)";

    private final String SELECT_LOG = "select created_at, first_name, message, second_name, user_id" +
            " from message_log" +
            " where created_at =?";

    public DBService() {
    }

    @Override
    public void setLog(List<Log> logs) throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(connectionUrl, USER, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        for (Log log : logs) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LOG);
            preparedStatement.setDate(1, log.getCreated_at());
            preparedStatement.setString(2, log.getFirst_name());
            preparedStatement.setString(3, log.getMessage());
            preparedStatement.setString(4, log.getSecond_name());
            preparedStatement.setInt(5, log.getUser_id());
            preparedStatement.execute();
        }
    }

    @Override
    public ResultSet getLog(Date date) throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(connectionUrl, USER, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LOG);
        preparedStatement.setDate(1, date);
        return preparedStatement.getResultSet();
    }

    @Override
    protected void finalize() throws Throwable {
        connection.close();
        super.finalize();
    }
}
