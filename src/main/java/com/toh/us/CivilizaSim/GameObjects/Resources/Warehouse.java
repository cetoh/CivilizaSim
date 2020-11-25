package com.toh.us.CivilizaSim.GameObjects.Resources;

public class Warehouse {

    private Wheat wheat = new Wheat();
    private Wood wood = new Wood();
    private Iron iron = new Iron();
    private Clay clay = new Clay();
    private Gold gold = new Gold();

    public Warehouse() {

    }

    public Wheat getWheat() {
        return wheat;
    }

    public void setWheat(Wheat wheat) {
        this.wheat = wheat;
    }

    public Wood getWood() {
        return wood;
    }

    public void setWood(Wood wood) {
        this.wood = wood;
    }

    public Iron getIron() {
        return iron;
    }

    public void setIron(Iron iron) {
        this.iron = iron;
    }

    public Clay getClay() {
        return clay;
    }

    public void setClay(Clay clay) {
        this.clay = clay;
    }

    public Gold getGold() {
        return gold;
    }

    public void setGold(Gold gold) {
        this.gold = gold;
    }
}
