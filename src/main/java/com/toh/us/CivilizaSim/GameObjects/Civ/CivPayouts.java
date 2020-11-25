package com.toh.us.CivilizaSim.GameObjects.Civ;

public enum CivPayouts {
    VERY_HIGH("VERY_HIGH"),
    HIGH("HIGH"),
    MODERATE("MODERATE"),
    LOW("LOW"),
    VERY_LOW("VERY_LOW"),
    NONE("NONE");

    public final String action;

    private CivPayouts(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
