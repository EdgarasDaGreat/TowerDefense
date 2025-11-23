public class WeakTowerCreator implements TowerCreator {
    @Override
    public Tower create(int x, int y) {
        return new Tower_weak(x, y);
    }
}