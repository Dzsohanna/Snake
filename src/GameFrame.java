import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame() {

        //tobb ablak hasznalata miatt card layout
        CardLayout layout = new CardLayout();
        //tartalmazni fogja az osszes alpanelt
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(layout);
        add(cardPanel);

        //harom kulonbozo panel egyedi nevvel ellatva hogy konnyen valtogassuk oket
        MainMenuPanel mainMenuPanel = new MainMenuPanel();
        cardPanel.add(mainMenuPanel, "MainMenu");

        GamePanel gamePanel = new GamePanel();
        cardPanel.add(gamePanel, "Game");

        HelpPanel helpPanel = new HelpPanel();
        cardPanel.add(helpPanel, "Help");

        //a gombokhoz hozzaadott action listener hogy valtogassa a panelt
        mainMenuPanel.getPlayButton().addActionListener(e -> {
            audio();
            gamePanel.resetGame();
            // a game nevhez tartozo panelt fogja megjeleniteni
            layout.show(cardPanel, "Game");
        });
        gamePanel.getBackButton().addActionListener(e -> {
            audio();
            Timer t = gamePanel.getTimer();
            if (t != null) {
                t.stop();
            }
            layout.show(cardPanel, "MainMenu");
        });
        mainMenuPanel.getHelpButton().addActionListener(e -> {
            audio();
            layout.show(cardPanel, "Help");
        });
        helpPanel.getBackButton().addActionListener(e -> {
            audio();
            layout.show(cardPanel, "MainMenu");
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //keret meret beallitasa
        setBounds(300, 100, 850, 665);
        setVisible(true);
    }

    void audio() {
        Audio clicked = new Audio("sounds/click.wav");
        clicked.audio.start();
    }
}
