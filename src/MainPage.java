import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MainPage extends JFrame implements ActionListener {
    private Character selectedCharacter;
    private JPanel guiGameboard;
    private Grid grid;
    private JPanel infoPanel = new JPanel(new GridLayout(7, 1));
    private JPanel optionsPanel = new JPanel(new GridLayout(2, 1));
    private JLabel[][] gameCells;
    private JLabel storyLabel = new JLabel();
    private JButton northButton;
    private JButton southButton;
    private JButton eastButton;
    private JButton westButton;
    private JButton buyManaPotion = new JButton("Buy Mana Potion");
    private JButton buyHealthPotion = new JButton("Buy Health Potion");
    private JButton submit = new JButton("Submit");
    private JPanel movePanel = new JPanel(new BorderLayout());
    private JPanel currentCellOptions = new JPanel(new GridLayout(7, 1));
    private JLabel nameLabel;
    private JLabel lifeLabel;
    private JLabel manaLabel;
    private JLabel experienceLabel;
    private JLabel levelLabel;
    private JLabel inventoryLabel;
    private JLabel maximumWeightLabel;
    private JLabel foundCoinsLabel = new JLabel();

    private JLabel attackState = new JLabel();
    private JButton useManaPotionButton = new JButton("Use Mana Potion");
    private JButton useHealthPotionButton = new JButton("Use Health Potion");
    private JButton useSpellButton = new JButton("Use Spell");
    private JButton normalAttackButton = new JButton("Normal attack");

    private Image questionMarkImage;
    private Image shopImage;
    private Image enemyImage;
    private Image finishImage;
    private Image startImage;

    private JLabel enemyLife = new JLabel();
    private JLabel enemyMana = new JLabel();

    private JLabel manaPotions = new JLabel();
    private JLabel manaPotionDetalis = new JLabel();
    private JLabel healthPotion = new JLabel();
    private JLabel healthPotionDetalis = new JLabel();

    private Enemy enemy;

    public void generateMap() throws IOException {
        BufferedImage questionBufferedImage = ImageIO.read(new File("Images\\question_mark.png"));
        questionMarkImage = questionBufferedImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT);

        BufferedImage shopBufferedImage = ImageIO.read(new File("Images\\shop.png"));
        shopImage = shopBufferedImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT);

        BufferedImage enemyBufferedImage = ImageIO.read(new File("Images\\enemy.png"));
        enemyImage = enemyBufferedImage.getScaledInstance(70, 70, Image.SCALE_DEFAULT);

        BufferedImage finishBufferedImage = ImageIO.read(new File("Images\\finish.png"));
        finishImage = finishBufferedImage.getScaledInstance(70, 70, Image.SCALE_DEFAULT);

        BufferedImage startBufferedImage = ImageIO.read(new File("Images\\start.png"));
        startImage = startBufferedImage.getScaledInstance(70, 70, Image.SCALE_DEFAULT);

        long height, width;
        height = selectedCharacter.currentLevel + 3;
        width = selectedCharacter.currentLevel + 2;

        guiGameboard = new JPanel(new GridLayout((int) height, (int) width));
        guiGameboard.setBorder(new LineBorder(Color.BLACK));

        grid = Grid.generateMap((int) height, (int) width, selectedCharacter,
                Game.getInstance().generateSpecialCells((int) height, (int) width));

        gameCells = new JLabel[(int) height][(int) width];

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                JLabel cellLabel = new JLabel(new ImageIcon(questionMarkImage));
                cellLabel.setBorder(new LineBorder(Color.BLACK));
                gameCells[i][j] = cellLabel;
                guiGameboard.add(gameCells[i][j]);
            }
        }

        gameCells[0][0].setBorder(new LineBorder(Color.red, 5));
        gameCells[0][0].setIcon(new ImageIcon(startImage));
        storyLabel.setText(Game.getInstance().printStory(grid));
    }

    public MainPage(String title, Character selectedCharacter) throws IOException {
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(750, 750));
        getContentPane().setBackground(Color.ORANGE);
        setLayout(new BorderLayout());

        this.selectedCharacter = selectedCharacter;
        generateMap();

        nameLabel = new JLabel("NAME:    " + selectedCharacter.name);
        lifeLabel = new JLabel("LIFE:    " + selectedCharacter.currentLife);
        manaLabel = new JLabel("MANA:    " + selectedCharacter.currentMana);
        experienceLabel = new JLabel("EXP:   " + selectedCharacter.currentExperience);
        levelLabel = new JLabel("LEVEL:    " + selectedCharacter.currentLevel);
        inventoryLabel = new JLabel("COINS:  " + selectedCharacter.inventory.coins);
        maximumWeightLabel = new JLabel("MAXIMUM WEIGHT:     " + selectedCharacter.inventory.maximumWeight);

        infoPanel.add(nameLabel);
        infoPanel.add(lifeLabel);
        infoPanel.add(manaLabel);
        infoPanel.add(experienceLabel);
        infoPanel.add(levelLabel);
        infoPanel.add(inventoryLabel);
        infoPanel.add(maximumWeightLabel);

        northButton = new JButton("North");
        eastButton = new JButton("East");
        southButton = new JButton("South");
        westButton = new JButton("West");

        northButton.addActionListener(this);
        eastButton.addActionListener(this);
        southButton.addActionListener(this);
        westButton.addActionListener(this);

        movePanel.add(northButton, BorderLayout.NORTH);
        movePanel.add(southButton, BorderLayout.SOUTH);
        movePanel.add(eastButton, BorderLayout.EAST);
        movePanel.add(westButton, BorderLayout.WEST);

        optionsPanel.add(currentCellOptions);
        optionsPanel.add(movePanel);

        add(guiGameboard, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.WEST);
        add(optionsPanel, BorderLayout.EAST);
        add(storyLabel, BorderLayout.NORTH);
        add(foundCoinsLabel, BorderLayout.SOUTH);

        show();
        pack();
    }

    private void verifyGoodEnding() {
        if (grid.currentCell.cellType.equals(Cell.CellEnum.FINISH)) {
            GoodEndingPage nextPage = new GoodEndingPage("Good Ending", infoPanel);
        }
    }

    public void updateInfo() {
        nameLabel.setText("NAME:    " + selectedCharacter.name);
        lifeLabel.setText("LIFE:    " + selectedCharacter.currentLife);
        manaLabel.setText("MANA:    " + selectedCharacter.currentMana);
        experienceLabel.setText("EXP:   " + selectedCharacter.currentExperience);
        levelLabel.setText("LEVEL:    " + selectedCharacter.currentLevel);
        inventoryLabel.setText("COINS:  " + selectedCharacter.inventory.coins);
        maximumWeightLabel.setText("MAXIMUM WEIGHT:     " + selectedCharacter.inventory.maximumWeight);
    }

    private void enemyCell() {
        if (!grid.currentCell.cellType.equals(Cell.CellEnum.ENEMY))
            return;

        enemy = ((Enemy) grid.currentCell.cellElement);
        updateEnemy();

        northButton.setEnabled(false);
        southButton.setEnabled(false);
        eastButton.setEnabled(false);
        westButton.setEnabled(false);

        useHealthPotionButton.addActionListener(this);
        useManaPotionButton.addActionListener(this);
        useSpellButton.addActionListener(this);
        normalAttackButton.addActionListener(this);

        attackState.setText("Let the battle begin!");

        useHealthPotionButton.setBackground(Color.white);
        useManaPotionButton.setBackground(Color.white);
        useSpellButton.setBackground(Color.white);

        currentCellOptions.add(attackState);
        currentCellOptions.add(enemyLife);
        currentCellOptions.add(enemyMana);
        currentCellOptions.add(useHealthPotionButton);
        currentCellOptions.add(useManaPotionButton);
        currentCellOptions.add(useSpellButton);
        currentCellOptions.add(normalAttackButton);
    }

    private void shopCell() {
        if (!grid.currentCell.cellType.equals(Cell.CellEnum.SHOP))
            return;

        northButton.setEnabled(false);
        southButton.setEnabled(false);
        eastButton.setEnabled(false);
        westButton.setEnabled(false);

        manaPotions.setText("Mana Potions: "
                + ((Shop) grid.currentCell.cellElement).getNumberOfMana());

        manaPotionDetalis.setText("Cost: " + (new ManaPotion()).getPrice()
                + " Weight: " + (new ManaPotion()).getPotionWeight());

        healthPotion.setText("Health Potions: "
                + ((Shop) grid.currentCell.cellElement).getNumberOfHealth());

        healthPotionDetalis.setText("Cost: " + (new HealthPotion()).getPrice()
                + " Weight: " + (new HealthPotion()).getPotionWeight());

        buyManaPotion.addActionListener(this);
        buyHealthPotion.addActionListener(this);
        buyHealthPotion.setBackground(Color.white);
        buyManaPotion.setBackground(Color.white);
        submit.addActionListener(this);

        currentCellOptions.add(manaPotions);
        currentCellOptions.add(manaPotionDetalis);
        currentCellOptions.add(healthPotion);
        currentCellOptions.add(healthPotionDetalis);
        currentCellOptions.add(buyManaPotion);
        currentCellOptions.add(buyHealthPotion);
        currentCellOptions.add(submit);
    }

    private void emptyCell() {
        if (!grid.currentCell.cellType.equals(Cell.CellEnum.EMPTY)) {
            foundCoinsLabel.setText("");
            return;
        }

        Random rand = new Random();
        int foundCoins = rand.nextInt(5);

        if (foundCoins == 1) {
            foundCoinsLabel.setText("Oh, you are lucky. You found some coins!!");
            selectedCharacter.inventory.coins += 20;
            updateInfo();
        } else
            foundCoinsLabel.setText("There are no coins here =(");
    }

    private void updateEnemy() {
        enemyLife.setText("Enemy Life: " + enemy.currentLife);
        enemyMana.setText("Enemy Mana: " + enemy.currentMana);
    }

    private void isBattleOver() {
        if (enemy.currentLife <= 0) {
            selectedCharacter.currentExperience += 20;
            northButton.setEnabled(true);
            southButton.setEnabled(true);
            eastButton.setEnabled(true);
            westButton.setEnabled(true);
            attackState.setText("You are a truly champion. You've just beat the beast!!");
        } else if (selectedCharacter.currentLife <= 0) {
            BadEndingPage nextPage = new BadEndingPage("Bad Ending", infoPanel);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == useHealthPotionButton) {
            for (int i = 0; i < selectedCharacter.inventory.potions.size(); ++i)
                if (selectedCharacter.inventory.potions.get(i).getClass().equals((new HealthPotion()).getClass())) {
                    Potion usedPotion = selectedCharacter.inventory.potions.get(i);
                    usedPotion.usePotion(selectedCharacter);
                    selectedCharacter.inventory.potions.remove(usedPotion);
                    enemy.attack(selectedCharacter);
                    isBattleOver();
                    updateInfo();
                    return;
                }
            useHealthPotionButton.setBackground(Color.red);
        } else if (e.getSource() == useManaPotionButton) {
            for (int i = 0; i < selectedCharacter.inventory.potions.size(); ++i)
                if (selectedCharacter.inventory.potions.get(i).getClass().equals((new ManaPotion()).getClass())) {
                    Potion usedPotion = selectedCharacter.inventory.potions.get(i);
                    usedPotion.usePotion(selectedCharacter);
                    selectedCharacter.inventory.potions.remove(usedPotion);
                    enemy.attack(selectedCharacter);
                    isBattleOver();
                    updateInfo();
                    return;
                }
            useManaPotionButton.setBackground(Color.red);
        } else if (e.getSource() == useSpellButton) {
            for (int i = 0; i < selectedCharacter.spells.size(); ++i) {
                Spell usedSpell = selectedCharacter.spells.get(i);
                selectedCharacter.useSpell(usedSpell, enemy);
                selectedCharacter.spells.remove(usedSpell);
                isBattleOver();
                updateEnemy();
                if (enemy.currentLife < 0) return;
                enemy.attack(selectedCharacter);
                isBattleOver();
                updateInfo();
                updateEnemy();
                return;
            }
            useSpellButton.setBackground(Color.red);
        } else if (e.getSource() == normalAttackButton) {
            enemy.receiveDamage(selectedCharacter.getDamage());
            isBattleOver();
            updateEnemy();
            if (enemy.currentLife < 0) return;
            enemy.attack(selectedCharacter);
            updateInfo();
            updateEnemy();
            isBattleOver();
        } else if (e.getSource() == buyHealthPotion) {
            int index = ((Shop) grid.currentCell.cellElement).getHealthPotion();

            if (index == -1) {
                buyHealthPotion.setBackground(Color.red);
                return;
            }

            Potion potion = ((Shop) grid.currentCell.cellElement).selectPotion(index);
            if (selectedCharacter.buyPotion(potion)) {
                healthPotion.setText("Health Potions: "
                        + ((Shop) grid.currentCell.cellElement).getNumberOfHealth());
                updateInfo();
            } else {
                buyHealthPotion.setBackground(Color.red);
                return;
            }
        } else if (e.getSource() == buyManaPotion) {
            int index = ((Shop) grid.currentCell.cellElement).getManaPotion();

            if (index == -1) {
                buyManaPotion.setBackground(Color.red);
                return;
            }

            Potion potion = ((Shop) grid.currentCell.cellElement).selectPotion(index);

            if (selectedCharacter.buyPotion(potion)) {
                manaPotions.setText("Mana Potions: "
                        + ((Shop) grid.currentCell.cellElement).getNumberOfMana());
                updateInfo();
            } else {
                buyManaPotion.setBackground(Color.red);
                return;
            }
        } else if (e.getSource() == submit) {
            northButton.setEnabled(true);
            southButton.setEnabled(true);
            eastButton.setEnabled(true);
            westButton.setEnabled(true);
        } else if (e.getSource() instanceof JButton) {
            gameCells[grid.currentCell.position.xCoordinate][grid.currentCell.position.yCoordinate]
                    .setBorder(new LineBorder(Color.black));

            if (e.getSource() == northButton) {
                Grid.goNorth(grid);
            } else if (e.getSource() == southButton) {
                Grid.goSouth(grid);
            } else if (e.getSource() == eastButton) {
                Grid.goEast(grid);
            } else if (e.getSource() == westButton) {
                Grid.goWest(grid);
            }

            gameCells[grid.currentCell.position.xCoordinate][grid.currentCell.position.yCoordinate]
                    .setBorder(new LineBorder(Color.red, 5));

            if (grid.currentCell.cellType.equals(Cell.CellEnum.EMPTY))
                gameCells[grid.currentCell.position.xCoordinate][grid.currentCell.position.yCoordinate]
                        .setIcon(new ImageIcon());
            else if (grid.currentCell.cellType.equals(Cell.CellEnum.ENEMY))
                gameCells[grid.currentCell.position.xCoordinate][grid.currentCell.position.yCoordinate]
                        .setIcon(new ImageIcon(enemyImage));
            else if (grid.currentCell.cellType.equals(Cell.CellEnum.SHOP))
                gameCells[grid.currentCell.position.xCoordinate][grid.currentCell.position.yCoordinate]
                        .setIcon(new ImageIcon(shopImage));
            else
                gameCells[grid.currentCell.position.xCoordinate][grid.currentCell.position.yCoordinate]
                        .setIcon(new ImageIcon(finishImage));

            currentCellOptions.removeAll();
            storyLabel.setText(Game.getInstance().printStory(grid));

            emptyCell();
            shopCell();
            enemyCell();
            verifyGoodEnding();
        }
    }
}
