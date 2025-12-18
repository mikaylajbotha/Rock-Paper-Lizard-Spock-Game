// Import necessary Java libraries for GUI, events, and random number generation
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

// Main class extending JFrame to create a GUI window and implementing ActionListener for button clicks
public class RPSLSGame extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L; // Unique identifier for serialization

    // Buttons for player choices
    private JButton rockButton, paperButton, scissorsButton, lizardButton, spockButton;

    // Labels to display choices, results, instructions, and scores
    private JLabel userChoiceLabel, computerChoiceLabel, resultLabel, instructionsLabel;
    private JLabel playerScoreLabel, computerScoreLabel;

    // Variables to track player and computer scores
    private int playerScore = 0, computerScore = 0;

    // Stores the original color of buttons for hover effects
    private Color originalButtonColor;

    // Constructor to set up the GUI
    public RPSLSGame() {
        // Set window title and behavior
        setTitle("Rock Paper Scissors Lizard Spock");
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Exit app when window closes
        setSize(800, 550);                        // Window size
        setLocationRelativeTo(null);              // Center window on screen
        setResizable(false);                      // Disable resizing

        // Main panel with BorderLayout: top = instructions/rules, center = buttons, bottom = results
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(245, 245, 245)); // Light gray background

        // ------------------ Top Panel ------------------
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // Stack vertically
        topPanel.setBackground(mainPanel.getBackground());

        // Instructions label
        instructionsLabel = new JLabel("<html><center>Welcome!<br>Choose Rock, Paper, Scissors, Lizard, or Spock.</center></html>");
        instructionsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        instructionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Rules panel (scrollable for compact display)
        JPanel rulesInnerPanel = new JPanel();
        rulesInnerPanel.setLayout(new BoxLayout(rulesInnerPanel, BoxLayout.Y_AXIS));
        rulesInnerPanel.setBackground(mainPanel.getBackground());
        rulesInnerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Array of game rules
        String[] rules = {
            "Rock crushes Scissors",
            "Scissors cuts Paper",
            "Paper covers Rock",
            "Rock crushes Lizard",
            "Lizard poisons Spock",
            "Spock smashes Scissors",
            "Scissors decapitates Lizard",
            "Lizard eats Paper",
            "Paper disproves Spock",
            "Spock vaporizes Rock"
        };

        // Add each rule as a JLabel with a bullet point
        for (String rule : rules) {
            JLabel ruleLabel = new JLabel("• " + rule);
            ruleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            ruleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            rulesInnerPanel.add(ruleLabel);
        }

        // Wrap rules panel in a scroll pane
        JScrollPane rulesScrollPane = new JScrollPane(rulesInnerPanel);
        rulesScrollPane.setPreferredSize(new Dimension(750, 120));
        rulesScrollPane.setBorder(BorderFactory.createTitledBorder("Rules"));
        rulesScrollPane.setBackground(mainPanel.getBackground());
        rulesScrollPane.getViewport().setBackground(mainPanel.getBackground());

        // Add instructions and rules to top panel
        topPanel.add(instructionsLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        topPanel.add(rulesScrollPane);

        // ------------------ Middle Panel (Buttons) ------------------
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 15, 0)); // Horizontal buttons with spacing
        buttonPanel.setBackground(mainPanel.getBackground());

        // Create buttons for each choice with icons and colors
        rockButton = createButton("Rock", "images/rock.png", new Color(255, 102, 102));
        paperButton = createButton("Paper", "images/paper.png", new Color(102, 255, 178));
        scissorsButton = createButton("Scissors", "images/scissors.png", new Color(102, 178, 255));
        lizardButton = createButton("Lizard", "images/lizard.png", new Color(255, 178, 102));
        spockButton = createButton("Spock", "images/spock.png", new Color(178, 102, 255));

        // Add buttons to the panel
        buttonPanel.add(rockButton);
        buttonPanel.add(paperButton);
        buttonPanel.add(scissorsButton);
        buttonPanel.add(lizardButton);
        buttonPanel.add(spockButton);

        // ------------------ Bottom Panel (Results & Scores) ------------------
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(5, 1, 5, 5)); // Stack labels vertically
        resultPanel.setBackground(mainPanel.getBackground());

        // Labels for showing choices and game results
        userChoiceLabel = new JLabel("Your Choice: ");
        computerChoiceLabel = new JLabel("Computer's Choice: ");
        resultLabel = new JLabel("");

        // Labels for scores
        playerScoreLabel = new JLabel("Player Score: 0");
        computerScoreLabel = new JLabel("Computer Score: 0");

        // Set font and center alignment for all labels
        JLabel[] labels = {userChoiceLabel, computerChoiceLabel, resultLabel, playerScoreLabel, computerScoreLabel};
        for (JLabel lbl : labels) {
            lbl.setFont(new Font("Arial", Font.BOLD, 16));
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            resultPanel.add(lbl);
        }

        // Add top, middle, and bottom panels to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(resultPanel, BorderLayout.SOUTH);

        // Add main panel to the frame
        add(mainPanel);

        // Make GUI visible
        setVisible(true);
    }

    // ------------------ Helper Method to Create Buttons ------------------
    private JButton createButton(String text, String iconPath, Color color) {
        JButton button = new JButton(text);

        // Load and scale icon
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaled = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaled));

        // Button styling
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));

        originalButtonColor = color;

        // Hover effect to brighten button when mouse enters
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { button.setBackground(color.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { button.setBackground(color); }
        });

        // Add action listener for clicks
        button.addActionListener(this);

        return button;
    }

    // Convert string choice to integer for easier comparison
    private int convertChoiceToInt(String choice) {
        switch (choice) {
            case "Rock": return 0;
            case "Paper": return 1;
            case "Scissors": return 2;
            case "Lizard": return 3;
            case "Spock": return 4;
            default: return -1;
        }
    }

    // Convert integer choice back to string
    private String convertChoiceToString(int choice) {
        switch (choice) {
            case 0: return "Rock";
            case 1: return "Paper";
            case 2: return "Scissors";
            case 3: return "Lizard";
            case 4: return "Spock";
            default: return "Unknown";
        }
    }

    // Generate a random choice for the computer
    private int generateRandomChoice() { return new Random().nextInt(5); }

    // Determine the winner based on user and computer choices
    private String determineWinner(int user, int computer) {
        if (user == computer) return "It's a tie!";
        else if ((user == 0 && (computer == 2 || computer == 3)) ||
                 (user == 1 && (computer == 0 || computer == 4)) ||
                 (user == 2 && (computer == 1 || computer == 3)) ||
                 (user == 3 && (computer == 1 || computer == 4)) ||
                 (user == 4 && (computer == 0 || computer == 2))) return "You win!";
        else return "Computer wins!";
    }

    // Handle button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JButton) {
            String userChoice = ((JButton) source).getText();
            int userChoiceIndex = convertChoiceToInt(userChoice);
            int computerChoiceIndex = generateRandomChoice();
            String computerChoice = convertChoiceToString(computerChoiceIndex);

            // Update choice labels
            userChoiceLabel.setText("Your Choice: " + userChoice);
            computerChoiceLabel.setText("Computer's Choice: " + computerChoice);

            // Determine winner and update result label
            String winner = determineWinner(userChoiceIndex, computerChoiceIndex);
            resultLabel.setText(winner);

            // Color-code the result and update scores
            switch (winner) {
                case "You win!": 
                    resultLabel.setForeground(new Color(0, 153, 0)); 
                    playerScore++; 
                    break;
                case "Computer wins!": 
                    resultLabel.setForeground(Color.RED); 
                    computerScore++; 
                    break;
                default: 
                    resultLabel.setForeground(new Color(255, 140, 0)); 
                    break;
            }

            // Update score labels
            playerScoreLabel.setText("Player Score: " + playerScore);
            computerScoreLabel.setText("Computer Score: " + computerScore);
        }
    }

    // Main method to launch the game
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RPSLSGame::new);
    }
}
