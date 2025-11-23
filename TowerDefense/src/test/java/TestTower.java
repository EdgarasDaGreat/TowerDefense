import java.util.List;

class TestTower extends Tower {
    private final int dmg;
    private final int range;
    private final String icon;
    private final int cost;

    public TestTower(int x, int y, int dmg, int range, String icon, int cost) {
        super(x, y);
        this.dmg = dmg;
        this.range = range;
        this.icon = icon;
        this.cost = cost;
    }

    public void setVisibleTiles(List<Coordinate> coords) {
        this.visibleTiles.clear();
        this.visibleTiles.addAll(coords);
    }

    @Override public int getDamage() { return dmg; }
    @Override public int getRange() { return range; }
    @Override public String getIcon() { return icon; }
    @Override public int getCost() { return cost; }
}