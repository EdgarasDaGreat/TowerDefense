import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class EscapeHealthLossTest {
    @Test
    void playerLosesHealthWhenEnemyEscapes() {
        //Given
        List<Coordinate> path = Arrays.asList(new Coordinate(0,0), new Coordinate(0,1));
        Enemy e = new Enemy(5, 1);
        e.setPathIndex(path.size());
        Player player = new Player(5, 20);

        //When
        e.hasEscaped(path, player);

        //Then
        assertEquals(4, player.getHealth(), "Health should decrease by 1 when enemy escapes");
        assertFalse(e.isAlive(), "Escaped enemy should be marked dead");
    }
}