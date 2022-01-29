public class Fire extends Spell {
    public Fire() {
        this.damage = 20;
        this.mana = 15;
    }

    @Override
    public void visit(Entity entity) {
        if (entity.fireProtected)
            return;

        entity.receiveDamage(this.damage);
    }
}
