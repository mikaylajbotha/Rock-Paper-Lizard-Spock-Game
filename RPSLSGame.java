import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class RPSLSGame extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JButton rockButton, paperButton, scissorsButton, lizardButton, spockButton;
    private JLabel userChoiceLabel, computerChoiceLabel, resultLabel, instructionsLabel, rulesLabel;

    private final Font welcomeFont = new Font("Arial", Font.PLAIN, 12);
    private final Color lightPurple = new Color(204, 153, 255);

    public RPSLSGame() {
    	// Set up the main JFrame
        setTitle("Rock Paper Scissors Lizard Spock Game");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2));
        getContentPane().setBackground(Color.PINK);

        // Create and customize labels for instructions and rules
        instructionsLabel = new JLabel("<html>Welcome to the Rock Paper Scissors Lizard Spock Game!<br>"
                + "Choose one of the options below to play:<br>"
                + "Rock, Paper, Scissors, Lizard, or Spock.<br>"
                + "Can you beat the computer?</html>");
        instructionsLabel.setFont(welcomeFont);

        rulesLabel = new JLabel("<html><b>Rules:</b><br>"
                + "Rock crushes Scissors<br>"
                + "Scissors cuts Paper<br>"
                + "Paper covers Rock<br>"
                + "Rock crushes Lizard<br>"
                + "Lizard poisons Spock<br>"
                + "Spock smashes Scissors<br>"
                + "Scissors decapitates Lizard<br>"
                + "Lizard eats Paper<br>"
                + "Paper disproves Spock<br>"
                + "Spock vaporizes Rock<br></html>");
        rulesLabel.setFont(welcomeFont);

        // Create buttons for game choices
        rockButton = createButton("Rock");
        paperButton = createButton("Paper");
        scissorsButton = createButton("Scissors");
        lizardButton = createButton("Lizard");
        spockButton = createButton("Spock");

        // Labels to display user and computer choices, and game result
        userChoiceLabel = new JLabel("Your Choice: ");
        computerChoiceLabel = new JLabel("Computer's Choice: ");
        resultLabel = new JLabel("");
        
        
        // Add action listeners to buttons
        rockButton.addActionListener(this);
        paperButton.addActionListener(this);
        scissorsButton.addActionListener(this);
        lizardButton.addActionListener(this);
        spockButton.addActionListener(this);

        // Add components to the JFrame
        add(instructionsLabel);
        add(new JLabel());
        add(rulesLabel);
        add(new JLabel());
        add(rockButton);
        add(paperButton);
        add(scissorsButton);
        add(lizardButton);
        add(spockButton);
        add(userChoiceLabel);
        add(computerChoiceLabel);
        add(resultLabel);

        // Make the JFrame visible
        setVisible(true);
    }

    private JButton createButton(String text) {
    	// Create and customize a button with light purple background
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setPreferredSize(new Dimension(100, 40));
        button.setBackground(lightPurple);
        return button;
    }

    private int convertChoiceToInt(String choice) {
        switch (choice) {
            case "Rock":
                return 0;
            case "Paper":
                return 1;
            case "Scissors":
                return 2;
            case "Lizard":
                return 3;
            case "Spock":
                return 4;
            default:
                return -1;
        }
    }

    private String convertChoiceToString(int choice) {
        switch (choice) {
            case 0:
                return "Rock";
            case 1:
                return "Paper";
            case 2:
                return "Scissors";
            case 3:
                return "Lizard";
            case 4:
                return "Spock";
            default:
                return "Unknown";
        }
    }

    private int generateRandomChoice() {
        Random random = new Random();
        return random.nextInt(5);
    }

    private String determineWinner(int user, int computer) {
        if (user == computer) {
            return "It's a tie!";
        } else if ((user == 0 && (computer == 2 || computer == 3)) ||
                   (user == 1 && (computer == 0 || computer == 4)) ||
                   (user == 2 && (computer == 1 || computer == 3)) ||
                   (user == 3 && (computer == 1 || computer == 4)) ||
                   (user == 4 && (computer == 0 || computer == 2))) {
            return "You win!";
        } else {
            return "Computer wins!";
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == rockButton || source == paperButton || source == scissorsButton || source == lizardButton || source == spockButton) {
            // User clicked one of the game buttons
            String userChoice = ((JButton) source).getText();
            int userChoiceIndex = convertChoiceToInt(userChoice);

            // Generate a random choice for the computer
            int computerChoiceIndex = generateRandomChoice();
            String computerChoice = convertChoiceToString(computerChoiceIndex);

            // Display the choices
            userChoiceLabel.setText("Your Choice: " + userChoice);
            computerChoiceLabel.setText("Computer's Choice: " + computerChoice);

            // Determine the winner and display the result
            String result = determineWinner(userChoiceIndex, computerChoiceIndex);
            resultLabel.setText(result);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RPSLSGame::new);
    }
}
