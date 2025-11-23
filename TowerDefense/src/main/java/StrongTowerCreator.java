public class StrongTowerCreator implements TowerCreator {
    @Override
    public Tower create(int x, int y) {
        return new Tower_strong(x, y);
    }
}