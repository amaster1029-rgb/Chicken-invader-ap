package ChickenInvaders.database;

import ChickenInvaders.model.User;

import java.sql.*;

public class DatabaseManager {
    static String url = "jdbc:sqlite:game.db";

    public static void initDatabase(){
        try{
            Connection conn = DriverManager.getConnection(url);
            Statement stm = conn.createStatement();

            stm.execute("CREATE TABLE IF NOT EXISTS Users (" +
                    "username TEXT PRIMARY KEY, " +
                    "password TEXT NOT NULL, " +
                    "highScore INT DEFAULT 0, " +
                    "lastLevel INT DEFAULT 1, " +
                    "bgMusic INT DEFAULT 1, " +
                    "shotMusic INT DEFAULT 1, " +
                    "crashMusic INT DEFAULT 1, " +
                    "gameOverSound INT DEFAULT 1)");

    stm.execute("CREATE TABLE IF NOT EXISTS GameRecords (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username TEXT NOT NULL, " +
            "finalScore INT NOT NULL, " +
            "lastLevel INT NOT NULL, " +
            "timeSpend TEXT NOT NULL, " +
            "bgMusic INT, " +
            "shotMusic INT, " +
            "crashMusic INT, " +
            "gameOverSoudn INT, " +
            "FOREIGN KEY (username) REFERENCES Users(username))");

    stm.close();
    conn.close();
    System.out.println("Database initialized successfully");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static User login(String username, String password){
        try(Connection conn = DriverManager.getConnection(url)){
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM Users WHERE username = ? AND password = ?"
            );
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            User user = null;

            if(rs.next()){
                user = new User(rs.getString("username"), rs.getString("password"));
                user.setHighScore(rs.getInt("highScore"));
                user.setLastLevel(rs.getInt("lastLevel"));
                user.setBgMusic(rs.getInt("bgMusic") == 1);
                user.setShotMusic(rs.getInt("shotMusic") == 1);
                user.setCrashMusic(rs.getInt("crashMusic") == 1);
                user.setGameOverSound(rs.getInt("gameOverSound") == 1);
            }

            rs.close();
            ps.close();
            return user;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean registerUser(String username, String password){
        try (Connection conn = DriverManager.getConnection(url)){
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Users (username, password) VALUES (?, ?)"
            );

            ps.setString(1, username);
            ps.setString(2, password);

            ps.executeUpdate();
            ps.close();
            return true;
        }catch (SQLException e){
            System.out.println("Registration failed: " + e.getMessage());
            return false;
        }
    }

    public static void updateUserProgress(String username, int score, int currenLevel){
        try (Connection conn = DriverManager.getConnection(url)){
            PreparedStatement psScore = conn.prepareStatement(
                    "UPDATE Users SET highScore = ? WHERE username = ? AND highScore < ?"
            );

            psScore.setInt(1, score);
            psScore.setString(2, username);
            psScore.setInt(3, score);

            psScore.executeUpdate();
            psScore.close();

            PreparedStatement psLevel = conn.prepareStatement(
                    "UPDATE Users SET lastLevel = ? WHERE username = ?"
            );

            psLevel.setInt(1, currenLevel);
            psLevel.setString(2, username);

            psLevel.executeUpdate();
            psLevel.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void updateSoundSetting(String username, boolean bg, boolean shot, boolean crash , boolean over){
        try (Connection conn = DriverManager.getConnection(url)){
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE Users SET bgMusic = ?, shotMusic = ?, crashMusic = ?, gameOverSound = ? " +
                            "WHERE username = ?"
            );

            ps.setInt(1, bg ? 1 : 0);
            ps.setInt(2, shot ? 1 : 0);
            ps.setInt(3, crash ? 1 : 0);
            ps.setInt(4, over ? 1 : 0);
            ps.setString(5, username);

            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void saveGameRecord(String username, int finalScore, int lastLevel,
                                      boolean bg, boolean shot, boolean crash, boolean over){
        try (Connection conn = DriverManager.getConnection(url)){

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO GameRecords (username, finalScore, lastLevel, timeSpend, " +
                            "bgMusic, shotMusic, crashMusic, gameOverSound) " +
                            "VALUES (?, ?, ?, datetime('now', 'localtime'), ?, ?, ?, ?)"
            );

            ps.setString(1, username);
            ps.setInt(2, finalScore);
            ps.setInt(3, lastLevel);
            ps.setInt(4, bg ? 1 : 0);
            ps.setInt(5, shot ? 1 : 0);
            ps.setInt(6, crash ? 1 : 0);
            ps.setInt(7, over ? 1 : 0);

            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void printHighScore(){
        try (Connection conn = DriverManager.getConnection(url)){
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery(
                    "SELECT username, MAX(finalScore) as topScore, lastLevel, timeSpend " +
                            "FROM GameRecords GROUP BY username ORDER BY topScore DESC "
            );

            boolean found = false;
            System.out.println("____High Score Table____");
            while (rs.next()){
                found = true;
                System.out.println("User: " + rs.getString("username") +
                        " | Score: " + rs.getInt("topScore") +
                        " | Level: " + rs.getInt("lastLevel") +
                        " | Date: " + rs.getString("timeSpend"));
            }
            if(!found)
                System.out.println("No game record found!");

            rs.close();
            stm.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
