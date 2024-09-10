// This class is used for Database Connection

import java.sql.*;

public class ConnectDB {

    Connection cn;
    PreparedStatement pst;

    public ConnectDB() {

        final String url = "jdbc:mysql://localhost:3306/Project";
        final String username = "root";
        final String password = "1234";


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection(url, username, password);

        } catch (Exception shit) {
            System.out.println("Something went wrong in Database Connectivity\n" + shit);
        }
    }
}
