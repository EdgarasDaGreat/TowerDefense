import java.util.ArrayList;
import java.util.List;

public abstract class Tower {
    protected int x;
    protected int y;
    private final int presetAttackCooldown = 2;
    private int attackCooldown = 0;

    protected List<Coordinate> visibleTiles = new ArrayList<>();

    private TargetingStrategy targetingStrategy;

    public void setTargetingStrategy(TargetingStrategy targetingStrategy) {
        this.targetingStrategy = targetingStrategy;
    }

    protected Tower(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract int getDamage();
    public abstract int getRange();
    public abstract String getIcon();
    public abstract int getCost();


    public List<Coordinate> getVisibleTiles() { return visibleTiles; }

    // Compute and cache visible tiles using Chebyshev distance (includes diagonals) and keep only path tiles.
    public void computeVisibleTiles(int range, int rows, int cols, String[][] visualMap) {
        visibleTiles.clear();
        for (int dy = -range; dy <= range; dy++) {
            for (int dx = -range; dx <= range; dx++) {
                int tx = x + dx;
                int ty = y + dy;
                if (tx < 0 || ty < 0 || ty >= rows || tx >= cols) continue;
                if (Math.max(Math.abs(dx), Math.abs(dy)) > range) continue;
                String cell = visualMap[ty][tx];
                if (cell.equals("*") || cell.equals("S") || cell.equals("E")) {
                    visibleTiles.add(new Coordinate(ty, tx));
                }
            }
        }
    }

    public void setAttackCooldown() {
        attackCooldown = presetAttackCooldown;
    }

    public void tickCooldown() {
        if (attackCooldown > 0) attackCooldown--;
    }

    public boolean canAttack() {
        return attackCooldown == 0;
    }

    public void attack(Player player, List<Enemy> activeEnemies, List<Coordinate> path){
        Enemy target = targetingStrategy.selectTarget(this, activeEnemies, path);
        if (target != null) {
            target.takeDamage(this.getDamage());
            this.setAttackCooldown();
            if (!target.isAlive()) {
                player.gain(target.getReward());
            }
        }
    }

    public boolean isEnemyInSight(List<Coordinate> visible, Coordinate enemy) {
        for (Coordinate c : visible) {
            if (c.getX() == enemy.getX() && c.getY() == enemy.getY()) return true;
        }
        return false;
    }

    public void createTower(Map map){
        computeVisibleTiles(getRange(), map.getRows(), map.getColumns(), map.getVisualMap());
        placeTowerOnMap(map, x, y, getIcon());
    }

    private void placeTowerOnMap(Map map, int x, int y, String icon) {
        String[][] visualMap = map.getVisualMap();
        visualMap[y][x] = icon;
    }
}
