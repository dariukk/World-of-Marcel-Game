import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Element {
    public List<Spell> spells = new ArrayList<Spell>();
    public int currentLife, maximumLife;
    public int currentMana, maximumMana;
    public boolean iceProtected, fireProtected, earthProtected;

    public void regenerateLife(int newLifeSpan) {
        if (newLifeSpan > maximumLife)
            currentLife = maximumLife;
        else
            currentLife = newLifeSpan;
    }

    public void regenerateMana(int newManaValue) {
        if (newManaValue > maximumMana)
            currentMana = maximumMana;
        else
            currentMana = newManaValue;
    }

    public void useSpell(Spell spell, Enemy enemy) {
        if (this.currentMana < spell.mana)
            return;

        this.currentMana -= spell.mana;
        enemy.accept(spell);
    }


    public abstract void receiveDamage(int damage);

    public abstract int getDamage();

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
