import java.util.List;

public class Enemy {
    private int health;
    private int pathIndex = -1;
    private final int reward;
    private boolean alive = true;

    public Enemy(int health, int reward) {
        this.health = health;
        this.reward = reward;
    }

    public void advance() {
        pathIndex++;
    }

    public void takeDamage(int dmg) {
        if (!alive) return;
        health -= dmg;
        if (health <= 0) {
            health = 0;
            alive = false;
        }
    }

    public String getIcon() {
        return String.valueOf(health);
    }

    public boolean isAlive() {
        return alive;
    }

    public int getPathIndex() {
        return pathIndex;
    }

    public void setPathIndex(int pathIndex) {
        this.pathIndex = pathIndex;
    }

    public int getReward() {
        return reward;
    }

    public static int rewardFor(int hp) {
        return switch (hp) {
            case 1 -> 1;
            case 3 -> 2;
            case 5 -> 5;
            default -> Math.max(1, hp / 2);
        };
    }

    public void hasEscaped(List<Coordinate> path, Player player){
        if (!isAlive()) return;
        if (getPathIndex() >= path.size()) {
            player.takeDamage();
            takeDamage(9999);
        }
    }

}
