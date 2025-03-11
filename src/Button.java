import javax.swing.*;
import java.awt.*;

class Button extends JButton {
    public Button(String text) {
        setText(text);
        setFont(new Font("Arial", Font.BOLD, 14));
        setBackground(new Color(175, 224, 174));
    }
}
