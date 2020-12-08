package com.toh.us.CivilizaSim.GameObjects.Buildings;

import com.toh.us.CivilizaSim.GameObjects.Resources.*;

public class Cost {
    private Wheat wheat = new Wheat();
    private Iron iron = new Iron();
    private Wood wood = new Wood();
    private Clay clay = new Clay();

    private Gold gold = new Gold();

    public Cost() {}

    public void increaseCost(int amount) {
        wheat.addAmount(amount);
        iron.addAmount(amount);
        wood.addAmount(amount);
        clay.addAmount(amount);
        gold.addAmount(amount);
    }

    public void decreaseCost(int amount) {
        wheat.removeAmount(amount);
        iron.removeAmount(amount);
        wood.removeAmount(amount);
        clay.removeAmount(amount);
        gold.removeAmount(amount);
    }

    public Wheat getWheat() {
        return wheat;
    }

    public void setWheat(Wheat wheat) {
        this.wheat = wheat;
    }

    public Iron getIron() {
        return iron;
    }

    public void setIron(Iron iron) {
        this.iron = iron;
    }

    public Wood getWood() {
        return wood;
    }

    public void setWood(Wood wood) {
        this.wood = wood;
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
