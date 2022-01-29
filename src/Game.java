import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class Game {
    private static Game instance = null;

    private Game() {

    }

    public static Game getInstance() {
        if (instance == null)
            instance = new Game();

        return instance;
    }

    public List<Account> gameAccounts; //json accounts
    public Map<Cell.CellEnum, List<String>> stories; // json stories

    public void run(String gameOption) throws IOException {
        parseJSON();

        switch (gameOption) {
            case "terminal":
                runTerminal();
                break;

            case "graphic interface":
                runGraphicInterface();
                break;
        }
    }

    public static void verifyMove(String key) throws Test.InvalidCommandException {
        if (!key.equals("P"))
            throw new Test.InvalidCommandException();
    }

    private void nextMove() {
        Scanner nextMove = new Scanner(System.in);
        String key = nextMove.nextLine();

        while (!key.equals("P")) {
            try {
                verifyMove(key);
            } catch (Test.InvalidCommandException e) {
                e.printStackTrace();
                key = nextMove.next();
            }
        }

    }

    public String printStory(Grid gameBoard) {
        List<String> storiesList = stories.get(gameBoard.currentCell.cellType);
        Random rand = new Random();
        int indexOfStory = rand.nextInt(storiesList.size());

        String story = storiesList.get(indexOfStory);
        return story;
    }

    public boolean isAlive(Character character) {
        if (character.currentLife > 0)
            return true;
        return false;
    }

    public void simulateAttackTerminal(Character character, Enemy enemy) {
        boolean isYourMove = true;
        boolean usedHealthPotion = false;
        boolean usedManaPotion = false;

        System.out.println();

        while (character.currentLife > 0 && enemy.currentLife > 0) {
            System.out.println(character.name + " life: " + character.currentLife
                    + " coins: " + character.inventory.coins
                    + " mana: " + character.currentMana);

            System.out.println("Enemy " + " life: " + enemy.currentLife
                    + " mana: " + enemy.currentMana + '\n');

            if (isYourMove) {
                System.out.println("It's your move");
                boolean madeMove = false;
                isYourMove = false;

                if (character.currentLife < character.maximumLife && !usedHealthPotion && !madeMove) {
                    for (int i = 0; i < character.inventory.potions.size(); ++i)
                        if (character.inventory.potions.get(i).getClass().equals((new HealthPotion()).getClass())) {
                            Potion usedPotion = character.inventory.potions.get(i);

                            System.out.println("You've used a Health Potion");
                            usedPotion.usePotion(character);
                            character.inventory.potions.remove(usedPotion);
                            usedHealthPotion = true;
                            madeMove = true;
                            break;
                        }
                }

                if (character.currentMana < character.maximumMana && !usedManaPotion && !madeMove) {
                    for (int i = 0; i < character.inventory.potions.size(); ++i)
                        if (character.inventory.potions.get(i).getClass().equals((new ManaPotion()).getClass())) {
                            Potion usedPotion = character.inventory.potions.get(i);

                            System.out.println("You've used a Mana Potion");
                            usedPotion.usePotion(character);
                            character.inventory.potions.remove(usedPotion);
                            usedManaPotion = true;
                            madeMove = true;
                            break;
                        }
                }

                if (!madeMove) {
                    boolean usedSpellOnEnemy = false;

                    for (int i = 0; i < character.spells.size(); ++i) {
                        Spell usedSpell = character.spells.get(i);

                        System.out.println("You've used a Spell which class type is " + usedSpell.getClass());
                        character.useSpell(usedSpell, enemy);
                        character.spells.remove(usedSpell);
                        usedSpellOnEnemy = true;
                        break;
                    }

                    if (!usedSpellOnEnemy) {
                        System.out.println("You've used a classic attack!");
                        enemy.receiveDamage(character.getDamage());
                    }
                }
            } else {
                isYourMove = true;
                enemy.attack(character);
            }

            nextMove();
            System.out.println();
        }

        if (character.currentLife < 0)
            System.out.println("Oh, no! You are dead");
        else {
            System.out.println("You are a truly champion. You've just beat the beast!!");

            Random rand = new Random();
            int foundCoins = rand.nextInt(5);

            if (foundCoins != 0) {
                System.out.println("You found some coins in the beast's pocket. You are a very lucky dude!");
                character.inventory.coins += 50;
            }
        }
    }

    public void cellOptions(Cell currentCell, Character character) {
        switch (currentCell.cellType) {
            case EMPTY:
                Random rand = new Random();
                int foundCoins = rand.nextInt(5);

                if (foundCoins == 1) {
                    System.out.println("Oh, you are lucky. You found some coins!!");
                    character.inventory.coins += 20;
                }

                break;

            case SHOP:
                System.out.println("\nThis shop has the following potions:");
                System.out.println(((Shop) currentCell.cellElement).printShopPotions());
                System.out.println("You have a total of " + character.inventory.coins + " coins");
                break;

            case ENEMY:
                simulateAttackTerminal(character, ((Enemy) currentCell.cellElement));
                break;

            case FINISH:
                break;
        }
    }

    public Account verifyEmailPassword(String email, String password) {
        for (Account account : gameAccounts)
            if (account.information.getCredentials().getEmail().equals(email)
                    && account.information.getCredentials().getPassword().equals(password))
                return account;

        return null;
    }

    public HashMap<Position, Cell.CellEnum> generateSpecialCells(int height, int width) {
        int numberOfShops = height;
        int numberOfEnemies = 2 * width;

        HashMap<Position, Cell.CellEnum> specialCells = new HashMap<Position, Cell.CellEnum>();
        specialCells.put(new Position(height - 1, width - 1), Cell.CellEnum.FINISH);
        Random rand = new Random();
        Position position;

        for (int i = 0; i < height; ++i) {
            position = new Position(rand.nextInt(height), rand.nextInt(width));

            while (specialCells.get(position) != null || (position.xCoordinate == 0 && position.yCoordinate == 0)) {
                position = new Position(rand.nextInt(height), rand.nextInt(width));
            }
            specialCells.put(position, Cell.CellEnum.SHOP);
        }

        for (int i = 0; i < width; ++i) {
            position = new Position(rand.nextInt(height), rand.nextInt(width));

            while (specialCells.get(position) != null || (position.xCoordinate == 0 && position.yCoordinate == 0)) {
                position = new Position(rand.nextInt(height), rand.nextInt(width));
            }
            specialCells.put(position, Cell.CellEnum.ENEMY);
        }

        return specialCells;
    }

    public void runTerminal() {
        Scanner loginData = new Scanner(System.in);
        System.out.println("-----------------------------");
        System.out.println("Choose account :)");
        boolean found = false;
        Account currentAccount = null;

        while (currentAccount == null) {
            System.out.print("Email: ");
            String email = loginData.nextLine();

            System.out.print("Password: ");
            String password = loginData.nextLine();

            currentAccount = verifyEmailPassword(email, password);
            if (currentAccount == null) {
                System.out.println("Incorrect email or password");
                System.out.println("Please try again");
            }
        }

        System.out.println("Welcome back to the World of Marcel, " + currentAccount.information.getName() + "!");
        System.out.println("-----------------------------");
        System.out.println("Please choose a character!");
        System.out.println("Note: Select the character's index");

        for (int i = 0; i < currentAccount.accountCharacters.size(); ++i)
            System.out.println((i + 1) + ".   " + currentAccount.accountCharacters.get(i).name);

        Integer numberOfCharacter = loginData.nextInt();
        Character selectedCharacter = currentAccount.accountCharacters.get(numberOfCharacter - 1);

        System.out.println("-----------------------------");
        HashMap<Position, Cell.CellEnum> specialCells = new HashMap<Position, Cell.CellEnum>();
        specialCells.put(new Position(0, 3), Cell.CellEnum.SHOP);
        specialCells.put(new Position(1, 3), Cell.CellEnum.SHOP);
        specialCells.put(new Position(2, 0), Cell.CellEnum.SHOP);
        specialCells.put(new Position(3, 4), Cell.CellEnum.ENEMY);
        specialCells.put(new Position(4, 4), Cell.CellEnum.FINISH);

        Grid gameBoard = Grid.generateMap(5, 5, selectedCharacter, specialCells);
        Grid.printMap(gameBoard);
        printStory(gameBoard);
        nextMove();

        System.out.println("-----------------------------");
        Grid.goEast(gameBoard);
        Grid.printMap(gameBoard);
        System.out.println(printStory(gameBoard));
        cellOptions(gameBoard.currentCell, selectedCharacter);
        nextMove();

        System.out.println("-----------------------------");
        Grid.goEast(gameBoard);
        Grid.printMap(gameBoard);
        System.out.println(printStory(gameBoard));
        cellOptions(gameBoard.currentCell, selectedCharacter);
        nextMove();

        System.out.println("-----------------------------");
        Grid.goEast(gameBoard);
        Grid.printMap(gameBoard);
        System.out.println(printStory(gameBoard));
        cellOptions(gameBoard.currentCell, selectedCharacter);
        nextMove();

        selectedCharacter.buyPotion(((Shop) gameBoard.currentCell.cellElement).selectPotion(0));
        nextMove();

        selectedCharacter.buyPotion(((Shop) gameBoard.currentCell.cellElement).selectPotion(0));
        nextMove();

        System.out.println("-----------------------------");
        Grid.goEast(gameBoard);
        Grid.printMap(gameBoard);
        System.out.println(printStory(gameBoard));
        cellOptions(gameBoard.currentCell, selectedCharacter);
        nextMove();

        System.out.println("-----------------------------");
        Grid.goSouth(gameBoard);
        Grid.printMap(gameBoard);
        System.out.println(printStory(gameBoard));
        cellOptions(gameBoard.currentCell, selectedCharacter);
        nextMove();

        System.out.println("-----------------------------");
        Grid.goSouth(gameBoard);
        Grid.printMap(gameBoard);
        System.out.println(printStory(gameBoard));
        cellOptions(gameBoard.currentCell, selectedCharacter);
        nextMove();

        System.out.println("-----------------------------");
        Grid.goSouth(gameBoard);
        Grid.printMap(gameBoard);
        System.out.println(printStory(gameBoard));
        cellOptions(gameBoard.currentCell, selectedCharacter);

        if (!this.isAlive(selectedCharacter))
            return;

        nextMove();

        System.out.println("-----------------------------");
        Grid.goSouth(gameBoard);
        Grid.printMap(gameBoard);
        System.out.println(printStory(gameBoard));
        cellOptions(gameBoard.currentCell, selectedCharacter);
        nextMove();
    }

    public void runGraphicInterface() throws IOException {
        LoginPage loginPage = new LoginPage("Login");
    }

    private void parseJSON() {
        JSONParser parser = new JSONParser();
        gameAccounts = new ArrayList<Account>();
        try (Reader reader = new FileReader("src\\Testing\\accounts.json")) {
            JSONObject accountsJSONObject = (JSONObject) parser.parse(reader);

            JSONArray accountsArray = (JSONArray) accountsJSONObject.get("accounts");
            for (int i = 0; i < accountsArray.size(); ++i) {
                JSONObject arrayEntry = (JSONObject) accountsArray.get(i);

                JSONObject credentialObject = (JSONObject) arrayEntry.get("credentials");
                String email = (String) credentialObject.get("email");
                String password = (String) credentialObject.get("password");
                Credentials credentials = new Credentials(email, password);

                String name = (String) arrayEntry.get("name");
                String country = (String) arrayEntry.get("country");

                TreeSet favouriteGames = new TreeSet();
                JSONArray gamesArray = (JSONArray) arrayEntry.get("favorite_games");

                for (int j = 0; j < gamesArray.size(); ++j) {
                    String game = (String) gamesArray.get(j);
                    favouriteGames.add(game);
                }
                Account.Information information = new Account.Information.InformationBuilder(credentials, name)
                        .favouriteGames(favouriteGames)
                        .country(country)
                        .build();

                String mapsString = (String) arrayEntry.get("maps_completed");
                Long numberOfGames = new Long(0);

                try {
                    numberOfGames = Long.parseLong(mapsString);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }

                JSONArray charactersArray = (JSONArray) arrayEntry.get("characters");
                ArrayList<Character> accountCharacters = new ArrayList<Character>();

                for (int j = 0; j < charactersArray.size(); ++j) {
                    JSONObject characterArrayEntry = (JSONObject) charactersArray.get(j);
                    String characterName = (String) characterArrayEntry.get("name");
                    String characterProfession = (String) characterArrayEntry.get("profession");
                    String levelString = (String) characterArrayEntry.get("level");
                    Long characterLevel = new Long(0);

                    try {
                        characterLevel = Long.parseLong(levelString);
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }

                    Long characterExperience = (Long) characterArrayEntry.get("experience");

                    CharacterFactory characterFactory = new CharacterFactory();
                    Character newCharacter = characterFactory.getCharacter(characterProfession, characterName,
                            characterExperience, characterLevel);
                    accountCharacters.add(newCharacter);
                }
                Account account = new Account(information, accountCharacters, numberOfGames);
                gameAccounts.add(account);
            }
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Reader reader = new FileReader("src\\Testing\\stories.json")) {
            JSONObject storiesJSONObject = (JSONObject) parser.parse(reader);
            JSONArray storiesArray = (JSONArray) storiesJSONObject.get("stories");
            stories = new HashMap<Cell.CellEnum, List<String>>();
            ArrayList<String> emptyStories = new ArrayList<String>();
            ArrayList<String> enemyStories = new ArrayList<String>();
            ArrayList<String> shopStories = new ArrayList<String>();
            ArrayList<String> finishStories = new ArrayList<String>();

            for (int i = 0; i < storiesArray.size(); ++i) {
                JSONObject storiesArrayEntry = (JSONObject) storiesArray.get(i);
                String storyType = (String) storiesArrayEntry.get("type");
                String storyValue = (String) storiesArrayEntry.get("value");

                switch (storyType) {
                    case "EMPTY":
                        emptyStories.add(storyValue);
                        break;

                    case "SHOP":
                        shopStories.add(storyValue);
                        break;

                    case "ENEMY":
                        enemyStories.add(storyValue);
                        break;

                    case "FINISH":
                        finishStories.add(storyValue);
                        break;
                }
            }

            stories.put(Cell.CellEnum.EMPTY, emptyStories);
            stories.put(Cell.CellEnum.ENEMY, enemyStories);
            stories.put(Cell.CellEnum.SHOP, shopStories);
            stories.put(Cell.CellEnum.FINISH, finishStories);
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
