import java.util.ArrayList;
import java.util.List;

public class Inventory {
    public List<Potion> potions;
    public int maximumWeight;
    public int coins;

    public Inventory(int maximumWeight) {
        this.potions = new ArrayList<Potion>();
        this.coins = 25;
        this.maximumWeight = maximumWeight;
    }

    public void addPotion(Potion potion) {
        potions.add(potion);
        coins -= potion.getPrice();
    }

    public void deletePotion(Potion potion) {
        potions.remove(potion);
    }

    public double getRemainingWeight() {
        double currentWeight = 0;

        for (Potion potion : potions)
            currentWeight += potion.getPotionWeight();

        return maximumWeight - currentWeight;
    }
}
