public class Earth extends Spell {
    public Earth() {
        this.mana = 3;
        this.damage = 5;
    }

    @Override
    public void visit(Entity entity) {
        if (entity.earthProtected)
            return;

        entity.receiveDamage(this.damage);
    }
}
