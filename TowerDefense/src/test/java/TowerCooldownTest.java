import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class TowerCooldownTest {
    @Test
    void enemyDontTakeDamageWhileCooldownIsActive(){
        //Given
        TestTower tower = new TestTower(1,1,1,1,"t",10);
        tower.setTargetingStrategy(new FirstInLineStrategy());
        List<Coordinate> path = List.of(new Coordinate(0,0));
        tower.setVisibleTiles(path);
        Enemy e = new Enemy(1, 1);
        e.setPathIndex(0);
        Player player = new Player(5, 20);

        //When
        tower.setAttackCooldown();
        if(tower.canAttack()){
            tower.attack(player, List.of(e), path);
        }

        //Then
        assertTrue(e.isAlive(), "Enemy should not be damaged while cooldown is active");
    }

    @Test
    void enemyTakeDamageAfterCooldown(){
        //Given
        TestTower tower = new TestTower(1,1,1,1,"t",10);
        tower.setTargetingStrategy(new FirstInLineStrategy());
        List<Coordinate> path = List.of(new Coordinate(0,0));
        tower.setVisibleTiles(path);
        Enemy e = new Enemy(2, 1);
        e.setPathIndex(0);
        Player player = new Player(5, 20);

        //When
        tower.attack(player, List.of(e), path);
        assertTrue(e.isAlive());

        tower.tickCooldown();
        tower.tickCooldown();
        if(tower.canAttack()){
            tower.attack(player, List.of(e), path);
        }

        //Then
        assertFalse(e.isAlive(), "Enemy should not be damaged while cooldown is active");
    }
}