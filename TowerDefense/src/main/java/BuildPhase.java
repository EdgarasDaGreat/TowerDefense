import java.util.ArrayList;
import java.util.List;

public class BuildPhase {

    PlayerInput playerInput = new PlayerInput();
    Renderer renderer = new Renderer();
    private List<Tower> towers = new ArrayList<>();

    public void beginBuildPhase(Map map, Player player, int waveNumber){
        Cursor cursor = new Cursor(0,0);
        boolean buildPhase = true;
        playerInput.start();
        while(buildPhase){
            String[][] view = map.getSnapshot();

            cursor.clampCursor(cursor, map.getColumns(), map.getRows());
            view[cursor.getCursorY()][cursor.getCursorX()] = "_";

            renderer.renderBuildPhase(waveNumber, player, map, view);

            String inputStatement = playerInput.readPlayerInput(cursor);
            if(inputStatement.equals("stop")) buildPhase = false;
            else if(inputStatement.equals("build")) selectTower(map, cursor, player);
        }
        playerInput.stop();
    }

    public void selectTower(Map map, Cursor cursor, Player player){
        if (!isPlacementValid(map, cursor)) {
            Renderer.renderErrorMessage("Invalid placement location. Press any key...");
            playerInput.readKey();
            return;
        }

        renderer.renderTowerSelection();

        Character towerChoice = playerInput.readTowerSelection();
        if (towerChoice == null) return;

        Tower t = createTowerFromChoice(towerChoice, cursor);
        if(t == null) return;//useless, bet be jo warningai

        renderer.renderAttackStrategySelection();
        Character attackStrategyChoice = playerInput.readTowerAttackStrategySelection();

        switch (Character.toLowerCase(attackStrategyChoice)) {
            case 'l' -> t.setTargetingStrategy(new LastInLineStrategy());
            case 'f' -> t.setTargetingStrategy(new FirstInLineStrategy());
        }

        if (player.canAfford(t.getCost())) {
            manageTowerCreation(t, player, map);
        } else {
            Renderer.renderErrorMessage("Not enough money (" + t.getCost() + "). Press any key...");
            playerInput.readKey();
        }
    }

    private boolean isPlacementValid(Map map, Cursor cursor) {
        String cell = map.getVisualMap()[cursor.getCursorY()][cursor.getCursorX()];
        return !(cell.equals("*") || cell.equals("S") || cell.equals("E") ||
                 cell.equals("w") || cell.equals("m") || cell.equals("s"));
    }

    private Tower createTowerFromChoice(char choice, Cursor cursor) {
        int x = cursor.getCursorX();
        int y = cursor.getCursorY();
        TowerCreator creator = switch (choice) {
            case '1' -> new WeakTowerCreator();
            case '2' -> new MediumTowerCreator();
            case '3' -> new StrongTowerCreator();
            default -> null;
        };
        if (creator == null) { //useless, bet be jo warningai
            return null;
        }
        return creator.create(x,y);
    }

    private void manageTowerCreation(Tower t, Player player, Map map){
        t.createTower(map);
        player.spend(t.getCost());
        towers.add(t);
    }
    public List<Tower> getTowers() {
        return towers;
    }

}
