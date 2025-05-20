package Fungorium_Controller;

import Fugorium_Model.Gomba;
import Fugorium_Model.Rovar;

import java.util.ArrayList;
import java.util.List;

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