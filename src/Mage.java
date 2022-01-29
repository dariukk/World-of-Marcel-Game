import java.util.Random;

public class Mage extends Character {
    public Mage(String name, Long currentExperience, Long currentLevel) {
        super();
        this.name = name;
        this.currentExperience = currentExperience;
        this.inventory = new Inventory(13);
        this.currentLevel = currentLevel;
        this.earthProtected = false;
        this.iceProtected = true;
        this.fireProtected = false;
        this.charisma = this.currentLevel * 2 + 15;
        this.dexterity = this.currentLevel * 2 + 5;
        this.strength = this.currentLevel * 2;
        this.spells.add(new Ice());
    }

    public Mage() {

    }

    @Override
    public void receiveDamage(int damage) {
        Random rand = new Random();
        boolean receivedDamage = rand.nextBoolean();

        if (receivedDamage)
            this.currentLife -= Math.max((damage - (int) (this.dexterity + this.strength) / 5) / 2, 0);
        else
            this.currentLife -= Math.max(damage - (int) (this.dexterity + this.strength) / 5, 0);
    }

    @Override
    public int getDamage() {
        return (int) (this.charisma + 3);
    }
}
