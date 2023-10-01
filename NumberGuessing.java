import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class NumberGuessing extends JFrame {

    private int answer;
    private int attemptsNum;
    private final int maxAttempts = 5;

    private JTextField guessField;
    private JLabel resultLabel;
    private JLabel turnLabel;

    public NumberGuessing() {
        super("Number Guessing Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 250);
        setLayout(null);

        guessField = new JTextField();
        guessField.setBounds(160, 20, 50, 20);
        guessField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    checkGuess();
                }
            }
        });
        add(guessField);

        JButton guessButton = new JButton("Guess");
        guessButton.setBounds(100, 50, 80, 30);
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });
        add(guessButton);

        resultLabel = new JLabel("");
        resultLabel.setBounds(20, 90, 250, 20);
        add(resultLabel);

        turnLabel = new JLabel("");
        turnLabel.setBounds(20, 120, 250, 20);
        add(turnLabel);

        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(100, 150, 80, 30);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(quitButton);

        initGame();
    }

    private void initGame() {
        answer = generateRandomNumber();
        attemptsNum = 0;
        updateTurnLabel();
    }

    private int generateRandomNumber() {
        return new Random().nextInt(101) + 1;
    }

    private void checkGuess() {
        String input = guessField.getText();
        int guess;
        try {
            guess = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        attemptsNum++;

        if (guess == answer) {
            resultLabel.setText("Congratulations! You are right!");
            handleGameEnd();
        } else if (guess < answer) {
            resultLabel.setText("Your guess was too LOW.");
        } else if (guess > answer) {
            resultLabel.setText("Your guess was too HIGH.");
        }

        if (attemptsNum == maxAttempts) {
            resultLabel.setText("Sorry, you've reached the maximum number of attempts. The number was " + answer);
            handleGameEnd();
        }

        updateTurnLabel();
    }

    private void updateTurnLabel() {
        turnLabel.setText("Turns: " + attemptsNum + " / " + maxAttempts);
    }

    private void handleGameEnd() {
        int option = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            initGame();
            resultLabel.setText("");
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NumberGuessing().setVisible(true);
            }
        });
    }
}
