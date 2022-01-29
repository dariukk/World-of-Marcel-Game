public class HealthPotion implements Potion {
    private double price;
    private double weight;
    private double regenerationValue;

    public HealthPotion() {
        this.price = 10;
        this.weight = 8;
        this.regenerationValue = 10;
    }

    @Override
    public void usePotion(Character character) {
        character.currentLife += this.regenerationValue;
        if(character.currentLife > character.maximumLife)
            character.currentLife = character.maximumLife;
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
        return "Health Potion";
    }
}
