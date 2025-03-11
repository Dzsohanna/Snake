import javax.swing.*;
import java.awt.*;

class HelpPanel extends JPanel {

    private final Button back;

    public HelpPanel() {
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JLabel title = new JLabel("How to Play", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        //a cim folott es alatt letrehoz egy 20 pixelnyi ures reszt
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JTextArea instructions = new JTextArea();
        instructions.setText("""
                1. Click 'Start' to start the game!
                
                2. Use the arrow keys to control the snake:
                        UP: Move up
                        DOWN: Move down
                        LEFT: Move left
                        RIGHT: Move right
                
                3. Click 'Pause' to pause the game.
                
                4. Click 'New game' to start a new game.
                
                5. Eat the fruits to gain points and grow your snake!
                
                6. Avoid colliding with the walls or yourself.
                
                Good luck and have fun!
                """);
        instructions.setFont(new Font("Arial", Font.BOLD, 20));
        //nem szerkesztheto csak olvashato
        instructions.setEditable(false);
        instructions.setBackground(new Color(175, 224, 174));
        //ures helyeket tesz a szoveg kore hogy rendezettebb legyen a kepernyon
        instructions.setBorder(BorderFactory.createEmptyBorder(20, 200, 10, 10));

        back = new Button("Back");
        back.setPreferredSize(new Dimension(140, 60));
        back.setBackground(new Color(255, 255, 255));
        buttonPanel.add(back);
        add(buttonPanel, BorderLayout.SOUTH);
        add(instructions, BorderLayout.CENTER);
        add(title, BorderLayout.NORTH);
    }

    public JButton getBackButton() {
        return back;
    }
}
