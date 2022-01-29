public class Ice extends Spell {
    public Ice() {
        this.damage = 10;
        this.mana = 5;
    }

    @Override
    public void visit(Entity entity) {
        if (entity.iceProtected)
            return;

        entity.receiveDamage(this.damage);
    }
}
