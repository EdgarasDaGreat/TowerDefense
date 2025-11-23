public class Player {
    private int health;   // starting lives
    private int money;  // starting money

    public int getHealth() {
        return health;
    }
    public int getMoney() {
        return money;
    }

    public Player(int health, int money) {
        this.health = health;
        this.money = money;
    }

    public boolean canAfford(int cost) {
        return money >= cost;
    }
    public void spend(int cost) {
        if (cost <= money) {
            money -= cost;
        }
    }

    public void gain(int reward) {
        money += reward;
    }

    public void takeDamage() {
        health--;
    }

    public boolean isAlive(){
        return health > 0;
    }

}
