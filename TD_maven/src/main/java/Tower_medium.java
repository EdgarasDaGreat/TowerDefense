public class Tower_medium extends Tower {
    private static final String ICON = "m";
    private static final int DAMAGE = 1;
    private static final int RANGE = 2;
    private static final int COST  = 20;

    public Tower_medium(int x, int y) {
        super(x, y);
    }

    @Override public int getDamage() { return DAMAGE; }
    @Override public int getRange()  { return RANGE; }
    @Override public String getIcon(){ return ICON; }
    @Override public int getCost()   { return COST; }
}
