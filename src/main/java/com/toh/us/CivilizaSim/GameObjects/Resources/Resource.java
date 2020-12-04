package com.toh.us.CivilizaSim.GameObjects.Resources;

public abstract class Resource {
    int amount = 0;

    public Resource() {

    }

    public Resource(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void addAmount(int amount) { this.amount += amount; }

    public void removeAmount(int amount) { this.amount -= amount; }
}
