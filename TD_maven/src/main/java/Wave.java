import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Wave {
    private Deque<Enemy> queue = new ArrayDeque<>();

    public Wave(List<Integer> hpValues) {
        addEnemies(hpValues);
    }

    public void addEnemy(int hp) {
        queue.addLast(new Enemy(hp, Enemy.rewardFor(hp)));
    }

    public void addEnemies(List<Integer> hpValues) {
        for (int hp : hpValues) addEnemy(hp);
    }

    public Deque<Enemy> getQueue() {
        return queue;
    }
}
