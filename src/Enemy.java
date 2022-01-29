import java.util.Random;

public class Enemy extends Entity implements CellElement {
    private final int normalDamage = 10;

    public Enemy() {
        Random rand = new Random();
        int low = 30;
        int high = 70;

        maximumLife = rand.nextInt(high - low) + low;
        currentLife = maximumLife;

        maximumMana = rand.nextInt(high - low) + low;
        currentMana = maximumMana;

        iceProtected = rand.nextBoolean();
        fireProtected = rand.nextBoolean();
        earthProtected = rand.nextBoolean();

        final int maximumNumberOfSpells = 4, minimumNumberOfSpells = 2;
        int numberOfSpells = rand.nextInt(maximumNumberOfSpells - minimumNumberOfSpells + 1) + 2;

        for (int i = 0; i < numberOfSpells; ++i) {
            final int typeOfSpell = rand.nextInt(3);

            switch (typeOfSpell) {
                case 0:
                    this.spells.add(new Earth());
                    break;
                case 1:
                    this.spells.add(new Ice());
                    break;
                case 2:
                    this.spells.add(new Fire());
                    break;
            }
        }
    }

    @Override
    public String toCharacter() {
        return "E";
    }

    @Override
    public void receiveDamage(int damage) {
        Random rand = new Random();
        boolean receivedDamage = rand.nextBoolean();

        if (receivedDamage == true)
            this.currentLife -= damage;
    }

    @Override
    public int getDamage() {
        Random rand = new Random();
        boolean doubleDamage = rand.nextBoolean();

        if (doubleDamage)
            return 2 * normalDamage;
        return normalDamage;
    }

    public void attack(Character character) {
        Random random = new Random();
        int isNormalAttack = random.nextInt(4);

        if (isNormalAttack == 0 && spells.size() > 0) {
            for (int i = 0; i < spells.size(); ++i)
                if (this.currentMana >= spells.get(i).mana) {
                    System.out.println("The enemy is using a spell! Wow, wow, be careful!");
                    Spell usedSpell = spells.get(i);

                    character.receiveDamage(usedSpell.damage);
                    this.currentMana -= usedSpell.mana;

                    spells.remove(usedSpell);
                    return;
                }
        }

        System.out.println("The enemy is using a simple attack");
        character.receiveDamage(getDamage());
    }

}
