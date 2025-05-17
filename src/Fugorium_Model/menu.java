import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class menu extends JFrame {
    public static void menu() {
        int boardWidth = 1024;
        int boardHeight = 768;

        // Create the main frame
        JFrame frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(boardWidth, boardHeight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // Set background image
        JLabel background = new JLabel(new ImageIcon(Main.class.getResource("/background.jpg")));

        background.setLayout(null);  // We'll use absolute positioning
        frame.setContentPane(background);

        // "Start" button
        JButton startButton = new JButton("START");
        startButton.setFont(new Font("Arial", Font.BOLD, 48));
        startButton.setBounds(100, 100, 400, 100);  // x, y, width, height
        background.add(startButton);

        // Player selection combo box
        JLabel playersLabel = new JLabel("Number of players");
        playersLabel.setBounds(100, 220, 200, 25);
        playersLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        background.add(playersLabel);

        String[] players = {"2", "3", "4"};
        JComboBox<String> playerDropdown = new JComboBox<>(players);
        playerDropdown.setBounds(100, 250, 200, 30);
        background.add(playerDropdown);

        // "Exit" button
        JButton exitButton = new JButton("EXIT");
        exitButton.setFont(new Font("Arial", Font.BOLD, 48));
        exitButton.setBounds(100, 300, 400, 100);
        background.add(exitButton);

        // Actions
        startButton.addActionListener(e -> {
            String selected = (String) playerDropdown.getSelectedItem();
            JOptionPane.showMessageDialog(frame, "Starting game with " + selected + " players!");
        });

        exitButton.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }
}
