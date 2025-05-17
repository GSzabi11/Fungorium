package Fungorium_Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.*;

public class Menu {

    static class Player {
        String name;
        String role;
        int score;

        Player(String name, String role) {
            this.name = name;
            this.role = role;
            this.score = 0;
        }

        void AddScore(int a) {
            this.score += a;
        }

        @Override
        public String toString() {
            return name + " - " + role + " - " + score;
        }
    }

    public static ArrayList<Player> playersarraylist = new ArrayList<Player>();
    public static void menu() {
        int boardWidth = 1024;
        int boardHeight = 768;

        JFrame frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(boardWidth, boardHeight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JLabel background = new JLabel(new ImageIcon(new ImageIcon("Images/windows.jpg").getImage()));
        background.setLayout(null);
        frame.setContentPane(background);

        JButton startButton = new JButton("START");
        startButton.setFont(new Font("Arial", Font.BOLD, 48));
        startButton.setBounds(100, 100, 400, 100);
        background.add(startButton);

        JLabel playersLabel = new JLabel("Number of players");
        playersLabel.setBounds(100, 220, 200, 25);
        playersLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        background.add(playersLabel);

        String[] players = {"2", "3", "4"};
        JComboBox<String> playerDropdown = new JComboBox<>(players);
        playerDropdown.setBounds(100, 250, 200, 30);
        background.add(playerDropdown);

        JButton exitButton = new JButton("EXIT");
        exitButton.setFont(new Font("Arial", Font.BOLD, 48));
        exitButton.setBounds(100, 300, 400, 100);
        background.add(exitButton);

        exitButton.addActionListener(e -> System.exit(0));

        startButton.addActionListener(e -> {
            int playerCount = Integer.parseInt((String) playerDropdown.getSelectedItem());
            ArrayList<Player> playerList = new ArrayList<>();

            for (int i = 1; i <= playerCount; i++) {
                String name;
                while (true) {
                    name = JOptionPane.showInputDialog(frame, "Enter name for Player " + i + ":");
                    if (name == null) {
                        JOptionPane.showMessageDialog(frame, "Player setup cancelled.");
                        return;
                    }

                    name = name.trim();
                    if (name.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Name cannot be empty. Please try again.");
                        continue;
                    }

                    final String finalName = name;
                    boolean nameExists = playerList.stream().anyMatch(p -> p.name.equalsIgnoreCase(finalName));
                    if (nameExists) {
                        JOptionPane.showMessageDialog(frame, "This name already exists. Choose a different one.");
                        continue;
                    }

                    break;
                }

                String[] options = {"rovarász", "gombász"};
                String role = (String) JOptionPane.showInputDialog(
                        frame,
                        "Choose role for " + name + ":",
                        "Role Selection",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (role == null) {
                    JOptionPane.showMessageDialog(frame, "Player setup cancelled.");
                    return;
                }

                playerList.add(new Player(name, role));
                playersarraylist.add(new Player(name, role));

            }

            boolean hasRovarasz = playerList.stream().anyMatch(p -> p.role.equals("rovarász"));
            boolean hasGombasz = playerList.stream().anyMatch(p -> p.role.equals("gombász"));

            if (!hasRovarasz || !hasGombasz) {
                JOptionPane.showMessageDialog(frame, "There must be at least one 'rovarász' and one 'gombász'. Please restart setup.");
                return;
            }

            frame.dispose();
            MainFrame mainFrame = new MainFrame();
            mainFrame.jatekMenu(playerList);
        });

        frame.setVisible(true);
    }

    public static void victory(ArrayList<Player> players) {
        JFrame victoryFrame = new JFrame("Results");
        victoryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        victoryFrame.setSize(800, 600);
        victoryFrame.setLocationRelativeTo(null);
        victoryFrame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Final Results", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        victoryFrame.add(titleLabel, BorderLayout.NORTH);

        JPanel leaderboardPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        leaderboardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextArea rovaraszArea = new JTextArea();
        JTextArea gombaszArea = new JTextArea();

        rovaraszArea.setEditable(false);
        gombaszArea.setEditable(false);

        rovaraszArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        gombaszArea.setFont(new Font("Monospaced", Font.PLAIN, 16));

        ArrayList<Player> rovaraszList = new ArrayList<>();
        ArrayList<Player> gombaszList = new ArrayList<>();

        for (Player p : players) {
            if (p.role.equals("rovarász")) {
                rovaraszList.add(p);
            } else if (p.role.equals("gombász")) {
                gombaszList.add(p);
            }
        }

        Comparator<Player> byScoreDesc = Comparator.comparingInt(p -> -p.score);
        rovaraszList.sort(byScoreDesc);
        gombaszList.sort(byScoreDesc);

        StringBuilder rovText = new StringBuilder("Rovarász Leaderboard:\n\n");
        for (Player p : rovaraszList) {
            rovText.append(p.name).append(" - ").append(p.score).append("\n");
        }

        StringBuilder gomText = new StringBuilder("Gombász Leaderboard:\n\n");
        for (Player p : gombaszList) {
            gomText.append(p.name).append(" - ").append(p.score).append("\n");
        }

        rovaraszArea.setText(rovText.toString());
        gombaszArea.setText(gomText.toString());

        leaderboardPanel.add(new JScrollPane(rovaraszArea));
        leaderboardPanel.add(new JScrollPane(gombaszArea));

        victoryFrame.add(leaderboardPanel, BorderLayout.CENTER);

        JButton backToMenu = new JButton("Back to Main Menu");
        backToMenu.setFont(new Font("Arial", Font.BOLD, 24));
        backToMenu.addActionListener((ActionEvent e) -> {
            victoryFrame.dispose();
            menu();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backToMenu);
        victoryFrame.add(buttonPanel, BorderLayout.SOUTH);

        victoryFrame.setVisible(true);
    }
}
