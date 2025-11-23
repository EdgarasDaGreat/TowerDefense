import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class TowerAttackRewardTest {
    @Test
    void attackKillsEnemyAndGrantsReward() {
        //Given
        TestTower tower = new TestTower(1,1,1,1,"t",10);
        tower.setTargetingStrategy(new FirstInLineStrategy());
        List<Coordinate> path = List.of(new Coordinate(0,0));
        tower.setVisibleTiles(path);

        Enemy e = new Enemy(1, 5);
        e.setPathIndex(0);
        Player player = new Player(5, 20);

        //When
        tower.attack(player, List.of(e), path);

        //Then
        assertFalse(e.isAlive(), "Enemy should be dead after exact damage");
        assertEquals(25, player.getMoney(), "Player should gain enemy reward on kill");
    }
}