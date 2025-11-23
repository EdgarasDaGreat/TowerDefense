import java.util.List;

public class Game {
    Map map = new Map("""
            SPPPP_______________
            ____P___PPPPPP______
            ____PPPPP____P______
            _____________PPPPP__
            _________________PPE
            """);
    Player player = new Player(5, 20);
    AttackPhase attackPhase = new AttackPhase(
    List.of(
        new Wave(List.of(1,1,1,1,1)),
        new Wave(List.of(1,1,3,1,3)),
        new Wave(List.of(3,1,3,1,3,1)),
        new Wave(List.of(5,1,3,5,1,3)),
        new Wave(List.of(5,3,5,3,5,3,1,1))
    ));
    BuildPhase buildPhase = new BuildPhase();

    public void startGame() {
        Renderer.renderIntro();
        map.initializeMap();
        List<Wave> waves = attackPhase.getWaves();
        for (int i = 0; i < waves.size(); i++) {
            Wave wave = waves.get(i);
            int waveNumber = i + 1;
            buildPhase.beginBuildPhase(map, player, waveNumber);
            attackPhase.runWave(map, player, buildPhase.getTowers(), wave, waveNumber);
            if (!player.isAlive()) {
                return;
            }
            player.gain(attackPhase.getWaveCompletionReward());
        }
        Renderer.renderGameOverVictory();
    }
}
