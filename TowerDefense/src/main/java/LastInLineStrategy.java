import java.util.List;

public class LastInLineStrategy implements TargetingStrategy {
    @Override
    public Enemy selectTarget(Tower tower, List<Enemy> activeEnemies, List<Coordinate> path) {
        List<Coordinate> visibleTiles = tower.getVisibleTiles();
        if (visibleTiles.isEmpty()) return null;

        Enemy best = null;
        int bestIndex = Integer.MAX_VALUE;

        for (Enemy e : activeEnemies) {
            if (!e.isAlive()) continue;
            int enemyPathIndex = e.getPathIndex();
            Coordinate enemyPos = path.get(enemyPathIndex);
            if (!tower.isEnemyInSight(visibleTiles, enemyPos)) continue;
            if (enemyPathIndex < bestIndex) {
                bestIndex = enemyPathIndex;
                best = e;
            }
        }
        return best;
    }
}