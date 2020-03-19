package util;

import java.sql.*;

public class Database {
    Connection connection = null;
    Statement stmt = null;
    public Database() {
        try
        {
            connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
        }
        catch (SQLException e){
            System.err.println(e);
        }
    }

    public ResultSet executeQuery(String sqlQuery) {
        try {
            stmt = connection.createStatement();
            stmt.setQueryTimeout(30);
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs;
        }
        catch (SQLException e) {
            System.err.println(e);
        }
        return null;
    }

    public boolean insertQuery(String query) {
        try {
            stmt = connection.createStatement();
            stmt.setQueryTimeout(30);
            if (stmt.executeUpdate(query) == 1) {
                return true;
            };
        }
        catch (SQLException e) {
            System.err.println(e);
        }
        return false;
    }
}
