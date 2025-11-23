import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class TowerRangeTest {
    @Test
    void enemyNotInVisibleTilesIsNotDamagedAndNoCooldown() {
        //Given
        TestTower tower = new TestTower(1,1,2,1,"t",10);
        tower.setTargetingStrategy(new FirstInLineStrategy());
        List<Coordinate> path = List.of(new Coordinate(0,0),new Coordinate(0,1));
        tower.setVisibleTiles(List.of(new Coordinate(0,1)));

        Enemy e = new Enemy(1, 1);
        e.setPathIndex(0);
        Player player = new Player(5, 20);

        //When
        tower.attack(player, List.of(e), path);

        //Then
        assertTrue(e.isAlive(), "Enemy should not be damaged when out of range");
        assertTrue(tower.canAttack(), "Cooldown unchanged (no shot fired)");
    }
}