package buyAndSell.database;

import java.sql.*;

import com.mysql.jdbc.Connection;

import javax.swing.*;

public class DatabaseHandler {

    private static DatabaseHandler handler;
    private static final String db_url = "jdbc:mysql://127.0.0.1/test2";
    public static Connection con = null;
    private static Statement statement = null;
    private static final String username = "root";
    private static final String password = "";

    private DatabaseHandler() {
        createConnection();
        setupItemTable();
        setupMemberTable();
        setupNegotiationTable();
    }

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            statement = con.createStatement();
            result = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Exception AT : execQuery(String query) " + e.getLocalizedMessage());
            return null;
        } finally {

        }

        return result;
    }

    public boolean execAction(String query) {
        try {
            statement = con.createStatement();
            statement.execute(query);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception AT : execACTION(String query) " + e.getLocalizedMessage());
            return false;
        } finally {
        }
    }
    
    public void setupNegotiationTable() {
        final String TABLE_NAME = "NEGOTIATION";
        try {
            statement = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists! Ready to go");
            } else {
                statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        + " item_id varchar(200),"
                        + " buyer_id varchar(200)"
                        + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setupItemTable() {
        final String TABLE_NAME = "ITEM";
        try {
            statement = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists! Ready to go");
            } else {
                statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        + " item_id varchar(200) PRIMARY KEY,"
                        + " item_name varchar(200), "
                        + " seller_id varchar(200), "
                        + " price int, "
                        + " is_avail boolean default true, "
//                        + " sold boolean default false, "
                        + " image blob "
                        + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setupMemberTable() {
        try {
            String TABLE_NAME = "MEMBER";
            statement = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists! Ready to go");

            } else {
                statement.execute("CREATE TABLE " + TABLE_NAME + " ("
                        + " id varchar(200) PRIMARY KEY,"
                        + " password varchar(20), "
                        + " name varchar(200), "
                        + " mobile varchar(20), "
                        + " email varchar(200) "
                        + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = (Connection) DriverManager.getConnection(db_url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("dipta");
        DatabaseHandler dbh = new DatabaseHandler();
        dbh.createConnection();

    }
}
