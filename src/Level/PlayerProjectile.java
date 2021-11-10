package Level;

import GameObject.SpriteSheet;

public class PlayerProjectile extends MapEntity {

    public PlayerProjectile(float x, float y, SpriteSheet spriteSheet, String startingAnimation) {
        super(x, y, spriteSheet, startingAnimation);
    }

    public void initialize() {
        super.initialize();
    }

    public void update(Enemy enemy) {
        super.update();
        if (intersects(enemy)) {
            touchedEnemy(enemy);
        }
    }

    public void touchedEnemy(Enemy enemy) {
        enemy.hurtEnemy(this);
    }
}
