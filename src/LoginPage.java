import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginPage extends JFrame implements ActionListener {
    private JLabel title;
    private JTextField email;
    private JPasswordField password;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private ChooseCharacterPage nextPage;
    private Account account;

    public LoginPage(String title) throws IOException {
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(200, 200));
        getContentPane().setBackground(new Color(251, 255, 175));
        setLayout(new BorderLayout());

        this.title = new JLabel("Welcome back to the World of Marcel");
        email = new JTextField();
        password = new JPasswordField();
        emailLabel = new JLabel("Email ");
        passwordLabel = new JLabel("Password ");
        loginButton = new JButton("Login");

        this.title.setFont(new Font("Verdana", Font.BOLD, 15));
        email.setColumns(30);
        password.setColumns(30);

        JPanel emailPanel = new JPanel();
        emailPanel.add(emailLabel);
        emailPanel.add(email);

        JPanel passwordPanel = new JPanel();
        passwordPanel.add(passwordLabel);
        passwordPanel.add(password);

        JPanel loginPanel = new JPanel(new GridLayout(2, 1));
        loginPanel.add(emailPanel);
        loginPanel.add(passwordPanel);

        loginButton.addActionListener(this);

        add(this.title, BorderLayout.NORTH);
        add(loginPanel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);

        this.email.addActionListener(this);
        this.password.addActionListener(this);

        show();
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            account = Game.getInstance().verifyEmailPassword(this.email.getText(), this.password.getText());

            if (account != null) {
                try {
                    nextPage = new ChooseCharacterPage("Choose a character", account);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } else
                this.title.setText("Wrong email or password");
        }
    }
}