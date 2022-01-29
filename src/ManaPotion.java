public class ManaPotion implements Potion {
    private double price;
    private double weight;
    private double regenerationValue;

    public ManaPotion() {
        this.price = 15;
        this.weight = 5;
        this.regenerationValue = 10;
    }

    @Override
    public void usePotion(Character character) {
        character.currentMana += this.regenerationValue;
        if (character.currentMana > character.maximumMana)
            character.currentMana = character.maximumMana;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getRegenerationValue() {
        return regenerationValue;
    }

    @Override
    public double getPotionWeight() {
        return weight;
    }

    @Override
    public String getType() {
        return "Mana Potion";
    }
}
