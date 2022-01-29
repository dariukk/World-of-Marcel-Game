import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shop implements CellElement {
    public List<Potion> potions;
    public int maxPotions;
    public int minPotions;

    public Shop() {
        maxPotions = 5;
        minPotions = 2;

        potions = new ArrayList<Potion>();
        Random random = new Random();
        int numberOfPotions = random.nextInt(maxPotions - minPotions) + minPotions;

        potions.add(new ManaPotion());
        potions.add(new HealthPotion());

        for (int i = 0; i < numberOfPotions - 2; ++i) {
            int potionType = random.nextInt(2);

            switch (potionType) {
                case 0:
                    potions.add(new ManaPotion());
                    break;

                case 1:
                    potions.add(new HealthPotion());
                    break;
            }
        }
    }

    public String printShopPotions() {
        String potionString = "";

        for (int i = 0; i < potions.size(); ++i) {
            Potion potion = potions.get(i);
            potionString += ((i + 1) + ". " + potion.getType() + " - Cost:"
                    + " " + potion.getPrice() + '\n');
        }
        return potionString;
    }

    public int getNumberOfMana() {
        int numberOfManaPotions = 0;

        for (Potion potion : potions)
            if (potion instanceof ManaPotion)
                numberOfManaPotions++;

        return numberOfManaPotions;
    }

    public int getNumberOfHealth() {
        int numberOfHealthPotions = 0;

        for (Potion potion : potions)
            if (potion instanceof HealthPotion)
                numberOfHealthPotions++;

        return numberOfHealthPotions;
    }

    public int getManaPotion() {
        for (int i = 0; i < potions.size(); ++i)
            if (potions.get(i) instanceof ManaPotion)
                return i;

        return -1;
    }

    public int getHealthPotion() {
        for (int i = 0; i < potions.size(); ++i)
            if (potions.get(i) instanceof HealthPotion)
                return i;

        return -1;
    }

    @Override
    public String toCharacter() {
        return "S";
    }

    public Potion selectPotion(int index) {
        Potion selectedPotion = potions.get(index);
        potions.remove(index);

        return selectedPotion;
    }
}
