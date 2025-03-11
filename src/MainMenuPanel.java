import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {

    private final Button play;
    private final Button help;
    private final Image background;

    public MainMenuPanel() {
        background = new ImageIcon("img/images.png").getImage();
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        play = new Button("Play Game");
        play.setPreferredSize(new Dimension(140, 60));
        buttonPanel.add(play);

        help = new Button("Help");
        help.setPreferredSize(new Dimension(140, 60));
        buttonPanel.add(help);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public JButton getPlayButton() {
        return play;

    }

    public JButton getHelpButton() {
        return help;
    }
}
