public class Warrior extends Character {
    public Warrior(String name, Long currentExperience, Long currentLevel) {
        super();
        this.name = name;
        this.currentExperience = currentExperience;
        this.inventory = new Inventory(30);
        this.currentLevel = currentLevel;
        this.fireProtected = true;
        this.iceProtected = false;
        this.earthProtected = false;
        this.charisma = this.currentLevel * 2 + 10;
        this.dexterity = this.currentLevel * 2;
        this.strength = this.currentLevel * 2 + 5;
        this.spells.add(new Fire());
    }

    public Warrior() {

    }

    @Override
    public void receiveDamage(int damage) {
        this.currentLife -= Math.max(damage - (int) (this.charisma + this.dexterity) / 5, 0);
    }

    @Override
    public int getDamage() {
        return (int) (this.strength + 3) * 2;
    }
}
