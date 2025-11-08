import java.util.ArrayList;
import java.util.List;

public abstract class Tower {
    protected int x;
    protected int y;
    private final int presetAttackCooldown = 2;
    private int attackCooldown = 0;

    protected List<Coordinate> visibleTiles = new ArrayList<>();

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

    public void attact(List<Tower> towers, Player player, List<Enemy> activeEnemies, Tower t, List<Coordinate> path){
        Enemy target = selectTargetFirstInLine(t, activeEnemies, path);
        if (target != null) {
            target.takeDamage(t.getDamage());
            t.setAttackCooldown();
            if (!target.isAlive()) {
                player.gain(target.getReward());
            }
        }
    }

    private Enemy selectTargetFirstInLine(Tower t, List<Enemy> activeEnemies, List<Coordinate> path) {
        Enemy bestToAttack = null;
        int bestIndex = -1;
        List<Coordinate> visibleTiles = t.getVisibleTiles();
        if (visibleTiles.isEmpty()) return null;
        for (Enemy e : activeEnemies) {
            if (!e.isAlive()) continue;

            int enemyPathIndex = e.getPathIndex();
            Coordinate enemyCoordinate = path.get(enemyPathIndex);
            if(!isEnemyInSight(enemyCoordinate)) continue;

            if (enemyPathIndex > bestIndex) {
                bestIndex = enemyPathIndex;
                bestToAttack = e;
            }
        }
        return bestToAttack;
    }

    private boolean isEnemyInSight(Coordinate enemyCoordinate){
        for (Coordinate c : visibleTiles) {
            if(c.getX() == enemyCoordinate.getX() && c.getY() == enemyCoordinate.getY()){
                return true;
            }
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
