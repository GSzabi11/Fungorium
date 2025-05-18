package Fungorium_Controller;

public class Player {
    String name;
    String role;
    int score;

    Player(String name, String role) {
        this.name = name;
        this.role = role;
        this.score = 0;
    }

    void addScore(int a) {
        this.score += a;
    }

    public String getName() { return name; }
    public String getRole()   { return role; }
    public int getScore()   { return score; }

    @Override
    public String toString() {
        return name + " - " + role + " - " + score;
    }
}