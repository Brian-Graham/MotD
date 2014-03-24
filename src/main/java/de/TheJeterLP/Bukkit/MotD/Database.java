package de.TheJeterLP.Bukkit.MotD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author TheJeterLP
 */
public class Database {

        private static Connection conn;
        private static final String table = "stars";

        public static Connection getConnection() throws SQLException {
                if (conn == null || conn.isClosed()) {
                        reactivateConnection();
                }
                return conn;
        }

        public static Statement getStatement() {
                try {
                        return getConnection().createStatement();
                } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                }
        }

        public static PreparedStatement getPreparedStatement(String query) {
                try {
                        return getConnection().prepareStatement(query);
                } catch (SQLException ex) {
                        ex.printStackTrace();
                        return null;
                }
        }

        public static void loadDriver() {
                try {
                        Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                        Main.getInstance().getLogger().severe("MySQL driver was not found!");
                        e.printStackTrace();
                }
        }

        public static void setup() throws SQLException {
                loadDriver();
                conn = getConnection();
        }

        public static void reactivateConnection() throws SQLException {
                String password = Config.MYSQL_PASSWORD.getString();
                String user = Config.MYSQL_USER.getString();
                String Database = Config.MYSQL_DATABASE.getString();
                String port = Config.MYSQL_PORT.getString();
                String host = Config.MYSQL_HOST.getString();
                String dsn = "jdbc:mysql://" + host + ":" + port + "/" + Database;
                conn = DriverManager.getConnection(dsn, user, password);
        }

        public static int getStars(String player) {
                int ret = -1;
                PreparedStatement stmnt = null;
                ResultSet rs = null;
                try {
                        stmnt = getPreparedStatement("SELECT * FROM " + table + " WHERE `player` = ?");
                        stmnt.setString(1, player);
                        rs = stmnt.executeQuery();
                        while (rs.next()) {
                                ret = rs.getInt("stars");
                        }
                } catch (SQLException ex) {
                        ex.printStackTrace();
                } finally {
                        closeResultSet(rs);
                        closeStatement(stmnt);
                }
                return ret;
        }

        public static Map<String, Integer> getStarsMap() {
                HashMap<String, Integer> ret = new HashMap<String, Integer>();
                Statement stmnt = null;
                ResultSet rs = null;
                try {
                        stmnt = getStatement();
                        rs = stmnt.executeQuery("SELECT * FROM " + table);
                        while (rs.next()) {
                                String player = rs.getString("player");
                                int stars = rs.getInt("stars");
                                ret.put(player, stars);
                        }
                } catch (SQLException ex) {
                        ex.printStackTrace();
                } finally {
                        closeResultSet(rs);
                        closeStatement(stmnt);
                }
                return ret;
        }

        public static void closeConnection() {
                try {
                        if (conn != null && !conn.isClosed()) {
                                conn.close();
                        }
                } catch (SQLException ex) {
                        ex.printStackTrace();
                }
        }

        public static void closeStatement(Statement st) {
                try {
                        if (st != null) {
                                st.close();
                        }
                } catch (SQLException ex) {
                        ex.printStackTrace();
                }
        }

        public static void closeResultSet(ResultSet rs) {
                try {
                        if (rs != null) {
                                rs.close();
                        }
                } catch (SQLException ex) {
                        ex.printStackTrace();
                }
        }
}
