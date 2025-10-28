import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame implements ActionListener {
    private JButton[] buttons = new JButton[9];
    private char currentPlayer = 'X';
    private JLabel statusLabel;

    public Main() {
        setTitle("Tic Tac Toe");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Game board
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        Font font = new Font("Arial", Font.BOLD, 60);

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(font);
            buttons[i].addActionListener(this);
            boardPanel.add(buttons[i]);
        }

        // Status and reset
        JPanel bottomPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Player X's turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        JButton resetButton = new JButton("Restart");
        resetButton.addActionListener(e -> resetGame());

        bottomPanel.add(statusLabel, BorderLayout.CENTER);
        bottomPanel.add(resetButton, BorderLayout.EAST);

        add(boardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (!clicked.getText().equals("")) return;

        clicked.setText(String.valueOf(currentPlayer));
        if (checkWinner()) {
            statusLabel.setText("Player " + currentPlayer + " wins!");
            disableButtons();
        } else if (isDraw()) {
            statusLabel.setText("It's a draw!");
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            statusLabel.setText("Player " + currentPlayer + "'s turn");
        }
    }

    private boolean checkWinner() {
        int[][] combos = {
                {0,1,2}, {3,4,5}, {6,7,8},
                {0,3,6}, {1,4,7}, {2,5,8},
                {0,4,8}, {2,4,6}
        };
        for (int[] combo : combos) {
            String a = buttons[combo[0]].getText();
            String b = buttons[combo[1]].getText();
            String c = buttons[combo[2]].getText();
            if (!a.equals("") && a.equals(b) && b.equals(c)) return true;
        }
        return false;
    }

    private boolean isDraw() {
        for (JButton b : buttons) {
            if (b.getText().equals("")) return false;
        }
        return true;
    }

    private void disableButtons() {
        for (JButton b : buttons) {
            b.setEnabled(false);
        }
    }

    private void resetGame() {
        for (JButton b : buttons) {
            b.setText("");
            b.setEnabled(true);
        }
        currentPlayer = 'X';
        statusLabel.setText("Player X's turn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
