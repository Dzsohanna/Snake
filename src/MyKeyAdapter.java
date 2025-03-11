import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyAdapter extends KeyAdapter {
    private final GamePanel gamePanel;

    public MyKeyAdapter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (gamePanel.getDirection() != 'R')
                    gamePanel.setDirection('L');
                break;
            case KeyEvent.VK_RIGHT:
                if (gamePanel.getDirection() != 'L')
                    gamePanel.setDirection('R');
                break;

            case KeyEvent.VK_UP:
                if (gamePanel.getDirection() != 'D')
                    gamePanel.setDirection('U');
                break;
            case KeyEvent.VK_DOWN:
                if (gamePanel.getDirection() != 'U')
                    gamePanel.setDirection('D');
        }
    }
}
