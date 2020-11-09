package cz.vsb.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static String username = "sol0095";
    private static String password = "";
    private static String url = "jdbc:sqlserver://dbsys.cs.vsb.cz\\STUDENT;";
    private static String databaseName = "sol0095";
    private static String connectionString;

    public static void setConnectionString(){
        connectionString =
                url + "database=" + databaseName + ";"
                        + "user=" + username + ";"
                        + "password=" + password + ";"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;"
                        + "loginTimeout=3600;"
                        + "rewriteBatchedStatements=true;";
    }

    public static Connection getConnection(){
        Connection con = null;
        try {
            con = DriverManager.getConnection(connectionString);
        } catch (SQLException throwables) {
            System.out.println(throwables);
        }

        return con;
    }
}
