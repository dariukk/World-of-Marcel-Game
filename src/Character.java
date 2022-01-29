public abstract class Character extends Entity {
    public String name;
    public Position position;
    public Inventory inventory;
    public Long currentExperience;
    public Long currentLevel;
    public Long strength, charisma, dexterity;

    public Character() {
        this.currentLife = 100;
        this.currentMana = 50;
        this.maximumLife = 100;
        this.maximumMana = 50;

        this.spells.add(new Ice());
        this.spells.add(new Earth());
        this.spells.add(new Fire());
    }

    public boolean buyPotion(Potion potion) {
        if (potion.getPrice() > this.inventory.coins) {
            System.out.println("You don't have enough money to buy a " + potion.getType());
            return false;
        }

        if (potion.getPotionWeight() > this.inventory.getRemainingWeight()) {
            System.out.println("You don't have enough inventory weight to buy a " + potion.getType());
            return false;
        }

        System.out.println("\nYou just bought a new " + potion.getType() + " with " + potion.getPrice() + " coins");

        this.inventory.addPotion(potion);
        System.out.println("Now you have a total of " + inventory.coins + " coins");
        return true;
    }


}
