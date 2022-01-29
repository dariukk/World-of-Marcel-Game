public class Rogue extends Character {
    public Rogue(String name, Long currentExperience, Long currentLevel) {
        super();
        this.name = name;
        this.currentExperience = currentExperience;
        this.inventory = new Inventory(20);
        this.currentLevel = currentLevel;
        this.earthProtected = true;
        this.iceProtected = false;
        this.fireProtected = false;

        this.charisma = this.currentLevel * 2;
        this.dexterity = this.currentLevel * 3 + 10;
        this.strength = this.currentLevel * 2 + 5;

        this.spells.add(new Earth());
    }

    @Override
    public void receiveDamage(int damage) {
        this.currentLife -= Math.max((damage - (int) (this.dexterity + this.charisma) / 5) / 2, 0);
    }

    @Override
    public int getDamage() {
        return (int) (this.dexterity + 3);
    }
}
