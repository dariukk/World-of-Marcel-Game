import javax.swing.*;
import java.awt.*;

public class GoodEndingPage extends JFrame {

    public GoodEndingPage(String title, JPanel infoPanel) {
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(300, 300));
        getContentPane().setBackground(Color.GREEN);
        setLayout(new BorderLayout());

        JLabel finalText = new JLabel("You finish the level! Great success :)");
        finalText.setFont(new Font("Verdana", Font.BOLD, 20));

        add(finalText, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);

        show();
        pack();
    }
}
