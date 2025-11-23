public class Tower_strong extends Tower {
    private static final String ICON = "s";
    private static final int DAMAGE = 2;
    private static final int RANGE = 2;
    private static final int COST  = 30;

    public Tower_strong(int x, int y) {
        super(x, y);
    }

    @Override public int getDamage() { return DAMAGE; }
    @Override public int getRange()  { return RANGE; }
    @Override public String getIcon(){ return ICON; }
    @Override public int getCost()   { return COST; }
}
