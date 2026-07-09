package ChickenInvaders.model;

public class User {
    private String username;
    private String password;
    private int highScore;
    private int lastLevel;
    private boolean bgMusic;
    private boolean shotMusic;
    private boolean crashMusic;
    private boolean gameOverSound;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.highScore = 0;
        this.lastLevel = 1;
        this.bgMusic = true;
        this.shotMusic = true;
        this.crashMusic = true;
        this.gameOverSound = true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getLastLevel() {
        return lastLevel;
    }

    public void setLastLevel(int lastLevel) {
        this.lastLevel = lastLevel;
    }

    public boolean isBgMusic() {
        return bgMusic;
    }

    public void setBgMusic(boolean bgMusic) {
        this.bgMusic = bgMusic;
    }

    public boolean isShotMusic() {
        return shotMusic;
    }

    public void setShotMusic(boolean shotMusic) {
        this.shotMusic = shotMusic;
    }

    public boolean isCrashMusic() {
        return crashMusic;
    }

    public void setCrashMusic(boolean crashMusic) {
        this.crashMusic = crashMusic;
    }

    public boolean isGameOverSound() {
        return gameOverSound;
    }

    public void setGameOverSound(boolean gameOverSound) {
        this.gameOverSound = gameOverSound;
    }
}
