import javax.swing.*;
import java.awt.*;

public class BadEndingPage extends JFrame {

    public BadEndingPage(String title, JPanel infoPanel) {
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(300, 300));
        getContentPane().setBackground(Color.red);
        setLayout(new BorderLayout());

        JLabel finalText = new JLabel("You lost! I'm so sorry, but we have better enemies :(");
        finalText.setFont(new Font("Verdana", Font.BOLD, 20));

        add(finalText, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);

        show();
        pack();
    }
}
