package com.toh.us.CivilizaSim.GameObjects.Civ;

public enum CivActions {
    TRADE("TRADE"),
    TRAIN("TRAIN"),
    PRODUCE("PRODUCE"),
    ATTACK("ATTACK"),
    DEFEND("DEFEND"),
    BUILD("BUILD");

    public final String action;

    CivActions(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
