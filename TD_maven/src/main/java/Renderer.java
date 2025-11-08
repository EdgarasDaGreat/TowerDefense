import java.util.List;
import java.util.Scanner;

public class Renderer {

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls")
                        .inheritIO()
                        .start()
                        .waitFor();
            } else {
                System.out.print("\u001b[2J\u001b[H");
                System.out.flush();
            }
        } catch (Exception ignored) {
            System.out.println("\n".repeat(100));
        }
    }

    public void renderMoneyAndHealth(Player player){
        System.out.println("=======================");
        System.out.println("Money: " + player.getMoney() + " | Health: " + player.getHealth());
        System.out.println("=======================");
        System.out.println();
    }

    public void renderMovementInstructions(){
        System.out.println();
        System.out.println("Build: WASD to move, 'b' to build, SPACE to start wave");
    }

    public void renderCursor(Map map, String[][] view){
        for (int i = 0; i < map.getRows(); i++) {
            for (int j = 0; j < map.getColumns(); j++) {
                System.out.print(view[i][j]);
            }
            System.out.println();
        }
    }

    public void renderTowerSelection(){
        String selectTower = """
                Select a tower to build (press 1-3 for tower type. press q to exit):
                1 - Weak.              2 - Medium.     _____    3 - Strong.     _____
                    Damage: 1   ___        Damage: 1   _____        Damage: 2   _____
                    Range: 1    _w_        Range: 2    __m__        Range: 2    __s__
                    Cost: 10    ___        Cost: 20    _____        Cost: 30    _____
                                                       _____                    _____
                """;
        System.out.println();
        System.out.println(selectTower);
    }

    public static void renderErrorMessage(String message){
        System.out.println();
        System.out.println(message);
    }

    public void renderAttackFrame(Map map, List<Enemy> activeEnemies) {

        String[][] view = map.getSnapshot();
        List<Coordinate> path = map.getPath();

        for (Enemy e : activeEnemies) {
            int enemyPathIndex = e.getPathIndex();
            if (enemyPathIndex >= 0 && enemyPathIndex < path.size()) {
                Coordinate c = path.get(enemyPathIndex);
                view[c.getX()][c.getY()] = String.valueOf(e.getIcon());
            }
        }
        renderCursor(map, view);//reuse
    }

    public void renderGameOverLoss(Player player, Map map, List<Enemy> activeEnemies, int waveNumber){
        for (int i=0; i<5; i++){
            clearConsole();
            renderMoneyAndHealth(player);
            renderAttackFrame(map, activeEnemies);
            sleep(300);
            clearConsole();
            renderMoneyAndEmptyHealth(player);
            renderAttackFrame(map, activeEnemies);
            sleep(300);
        }
        clearConsole();
        renderMoneyAndHealth(player);
        renderAttackFrame(map, activeEnemies);
        System.out.println();
        System.out.println("java.Game Over! You lost all lives! You made it to wave " + waveNumber);
    }

    public static void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    private void renderMoneyAndEmptyHealth(Player player){
        System.out.println("=======================");
        System.out.println("Money: " + player.getMoney() + " | Health: ");
        System.out.println("=======================");
        System.out.println();
    }

    public void renderWaveNumber(int waveNumber, String phase){
        System.out.println("java.Wave " + waveNumber + " | Phase: " + phase);
    }

    public void renderBuildPhase(int waveNumber, Player player, Map map, String[][] view){
        clearConsole();
        renderWaveNumber(waveNumber, "Building");
        renderMoneyAndHealth(player);
        renderCursor(map, view);
        renderMovementInstructions();
    }

    public void renderAttackPhase(int waveNumber, Player player, Map map, List<Enemy> activeEnemies){
        clearConsole();
        renderWaveNumber(waveNumber, "Defending");
        renderMoneyAndHealth(player);
        renderAttackFrame(map, activeEnemies);
    }

    public static void renderGameOverVictory(){
        System.out.println();
        System.out.println("Congratulations! You won the game!");
    }

    public static void renderIntro(){
        String introText = """
                ===============================
                =========TOWER=DEFENSE=========
                ===============================
                
                You have to defend your garden from enemies.
                
                java.Game is played in turns. While a wave is active, you can't place a tower, you can only spectate.
                Before each wave you are allowed to place a tower. Using arrow keys navigate the map, selected
                tile will be marked as "_". When you decide to place a tower, press T. You will then be asked
                to select what tower you want to purchase. Keep in mind that you have a limited budget to
                purchase towers with. After you have placed your towers, press S to start the wave. During the
                wave enemies will follow the path marked by "*". Enemies enter the map on "S" tile and exit on "E".
                If the enemy gets to the end "E", you lose lives. If you survive all waves you win. Killed enemies give 
                money, to purchase new towers. There are 3 types of towers:
                
                1 - Weak.       ___
                    Damage: 1   _1_
                    Range: 1    ___
                    Cost: 10
                                     _____   
                2 - Medium.          _____                    
                    Damage: 1        __2__
                    Range: 2         _____
                    Cost: 20         _____
                                            _____
                3 - Strong.                 _____
                    Damage: 2               __3__                    
                    Range: 2                _____
                    Cost: 30                _____
                                            
                You start the game with 5 lives and 20 money, 1 enemy = 1 life.
                
                Good luck!
                
                Press enter to continue.
                """;
        System.out.println(introText);

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

}
