public class MediumTowerCreator implements TowerCreator {
    @Override
    public Tower create(int x, int y) {
        return new Tower_medium(x, y);
    }
}