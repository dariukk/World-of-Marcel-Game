import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class ChooseCharacterPage extends JFrame implements ActionListener {
    private JLabel title;
    private Vector<JLabel> characterName;
    private Vector<JLabel> characterType;
    private Vector<JButton> chooseCharacterButton;
    public Account currentAccount;

    public JPanel getCharactersLabelsAndButtons(Account currentAccount) throws IOException {
        JPanel charactersPanel = new JPanel(new GridLayout());
        int i = 0;
        this.currentAccount = currentAccount;

        for (Character character : currentAccount.accountCharacters) {
            BufferedImage characterImage = null;

            if (character.getClass().equals((new Warrior()).getClass()))
                characterImage = ImageIO.read(new File("Images\\warrior.png"));
            else if (character.getClass().equals((new Mage()).getClass()))
                characterImage = ImageIO.read(new File("Images\\mage.png"));
            else
                characterImage = ImageIO.read(new File("Images\\rogue.png"));

            Image newImage = characterImage.getScaledInstance(300, 500, Image.SCALE_DEFAULT);
            JLabel picLabel = new JLabel(new ImageIcon(newImage));
            JPanel characterPanel = new JPanel(new BorderLayout());

            characterName.add(new JLabel(character.name));
            characterType.add(new JLabel("Warrior"));

            JButton newButton = new JButton("Select");
            newButton.addActionListener(this);
            chooseCharacterButton.add(newButton);

            characterPanel.add(characterName.get(i), BorderLayout.NORTH);
            characterPanel.add(picLabel, BorderLayout.CENTER);
            characterPanel.add(chooseCharacterButton.get(i), BorderLayout.SOUTH);

            charactersPanel.add(characterPanel);

            ++i;
        }
        return charactersPanel;
    }

    public ChooseCharacterPage(String title, Account currentAccount) throws IOException {
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(250, 450));
        getContentPane().setBackground(new Color(255, 229, 175));
        setLayout(new BorderLayout());

        characterName = new Vector<JLabel>();
        characterType = new Vector<JLabel>();
        chooseCharacterButton = new Vector<JButton>();

        this.title = new JLabel("Now it's time to choose your character");

        add(this.title, BorderLayout.NORTH);
        add(getCharactersLabelsAndButtons(currentAccount), BorderLayout.CENTER);


        show();
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            for (int i = 0; i < this.chooseCharacterButton.size(); ++i)
                if (this.chooseCharacterButton.get(i) == e.getSource()) {
                    try {
                        MainPage nextPage = new MainPage("World of Marcel", this.currentAccount.accountCharacters.get(i));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
        }
    }
}
