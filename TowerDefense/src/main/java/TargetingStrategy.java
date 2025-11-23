import java.util.List;

public interface TargetingStrategy {
    Enemy selectTarget(Tower tower, List<Enemy> activeEnemies, List<Coordinate> path);
}