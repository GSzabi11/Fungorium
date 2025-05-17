package Fungorium_Controller;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Menu {
    static class Player {
        String name;
        String role;

        Player(String name, String role) {
            this.name = name;
            this.role = role;
        }

        @Override
        public String toString() {
            return name + " - " + role;
        }
    }

    public static void menu() {
        int boardWidth = 1024;
        int boardHeight = 768;

        JFrame frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(boardWidth, boardHeight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // Background image (update path as needed)
        JLabel background = new JLabel(new ImageIcon(new ImageIcon("Images/windows.jpg").getImage()));
        background.setLayout(null);
        frame.setContentPane(background);

        // START button
        JButton startButton = new JButton("START");
        startButton.setFont(new Font("Arial", Font.BOLD, 48));
        startButton.setBounds(100, 100, 400, 100);
        background.add(startButton);

        // Player count selection
        JLabel playersLabel = new JLabel("Number of players");
        playersLabel.setBounds(100, 220, 200, 25);
        playersLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        background.add(playersLabel);

        String[] players = {"2", "3", "4"};
        JComboBox<String> playerDropdown = new JComboBox<>(players);
        playerDropdown.setBounds(100, 250, 200, 30);
        background.add(playerDropdown);

        // EXIT button
        JButton exitButton = new JButton("EXIT");
        exitButton.setFont(new Font("Arial", Font.BOLD, 48));
        exitButton.setBounds(100, 300, 400, 100);
        background.add(exitButton);

        // Exit behavior
        exitButton.addActionListener(e -> System.exit(0));

        // Start button behavior
        startButton.addActionListener(e -> {
            int playerCount = Integer.parseInt((String) playerDropdown.getSelectedItem());
            ArrayList<Player> playerList = new ArrayList<>();

            for (int i = 1; i <= playerCount; i++) {
                String name = JOptionPane.showInputDialog(frame, "Enter name for Player " + i + ":");
                if (name == null) {
                    JOptionPane.showMessageDialog(frame, "Player setup cancelled.");
                    return;
                }
                name = name.trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Name cannot be empty. Please try again.");
                    i--;
                    continue;
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
            }

            // Just show the players for now
            StringBuilder summary = new StringBuilder("Players:\n");
            for (Player p : playerList) {
                summary.append(p).append("\n");
            }
            JOptionPane.showMessageDialog(frame, summary.toString());

            // Proceed to game
            frame.dispose();
            MainFrame mainFrame = new MainFrame();
            mainFrame.jatekMenu(playerList);
        });

        frame.setVisible(true);
    }
}
