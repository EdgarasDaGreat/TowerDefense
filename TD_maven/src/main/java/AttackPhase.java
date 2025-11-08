import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class AttackPhase {
    private final Renderer renderer = new Renderer();
    private final List<Wave> waves;

    public AttackPhase(List<Wave> waves) {
        this.waves = waves;
    }

    public void runWave(Map map, Player player, List<Tower> towers, Wave wave, int waveNumber) {
        List<Enemy> activeEnemies = new ArrayList<>();
        Deque<Enemy> pendingEnemies = new ArrayDeque<>(wave.getQueue());

        int spawnCooldown = 0;
        List<Coordinate> path = map.getPath();

        while (!pendingEnemies.isEmpty() || !activeEnemies.isEmpty()) {
            if (spawnCooldown == 0 && !pendingEnemies.isEmpty()) {
                Enemy e = pendingEnemies.pollLast();
                e.setPathIndex(0);
                activeEnemies.add(e);
                spawnCooldown = 1; //set spawn cooldown
            } else if (spawnCooldown > 0) {
                spawnCooldown--;
            }

            allTowersPerformAttact(towers, player, activeEnemies, path);

            advanceAllEnemies(activeEnemies);

            checkForEscapes(activeEnemies, path, player);

            if(!player.isAlive()){
                renderer.renderGameOverLoss(player, map, activeEnemies, waveNumber);
                return;
            }

            activeEnemies.removeIf(e -> !e.isAlive() || e.getPathIndex() >= path.size());

            renderer.renderAttackPhase(waveNumber, player, map, activeEnemies);

            Renderer.sleep(500);
        }
    }

    private void advanceAllEnemies(List<Enemy> activeEnemies){
        for (Enemy e : activeEnemies) {
            if (!e.isAlive()) continue;
            e.advance();
        }
    }

    private void checkForEscapes(List<Enemy> activeEnemies, List<Coordinate> path, Player player){
        for (Enemy e : activeEnemies) {
            e.hasEscaped(path, player);
        }
    }

    private void allTowersPerformAttact(List<Tower> towers, Player player, List<Enemy> activeEnemies, List<Coordinate> path){
        for (Tower t : towers) {
            t.tickCooldown();
            if(t.canAttack()){
                t.attact(towers, player, activeEnemies, t, path);
            }
        }
    }

    public List<Wave> getWaves() {
        return waves;
    }
}
