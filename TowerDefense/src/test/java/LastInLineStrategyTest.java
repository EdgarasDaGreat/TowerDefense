import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class LastInLineStrategyTest {
    @Test
    void selectsEnemyClosestToStartWithinSight() {
        //Given
        TestTower tower = new TestTower(1, 1, 1, 2, "t", 10);
        List<Coordinate> path = List.of(
                new Coordinate(0,0), new Coordinate(0,1), new Coordinate(0,2), new Coordinate(0,3)
        );
        tower.setVisibleTiles(path);

        Enemy e1 = new Enemy(3, 3); e1.setPathIndex(1);
        Enemy e2 = new Enemy(3, 3); e2.setPathIndex(2);
        Enemy e3 = new Enemy(3, 3); e3.setPathIndex(3);
        List<Enemy> enemies = Arrays.asList(e1, e2, e3);

        //When
        TargetingStrategy s = new LastInLineStrategy();
        Enemy chosen = s.selectTarget(tower, enemies, path);

        //Then
        assertSame(e1, chosen, "Should pick enemy with lowest pathIndex in sight");
    }
}