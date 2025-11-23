public class Tower_weak extends Tower {
    private static final String ICON = "w";
    private static final int DAMAGE = 1;
    private static final int RANGE = 1;
    private static final int COST  = 10;

    public Tower_weak(int x, int y) {
        super(x, y);
    }

    @Override public int getDamage() { return DAMAGE; }
    @Override public int getRange()  { return RANGE; }
    @Override public String getIcon(){ return ICON; }
    @Override public int getCost()   { return COST; }
}
