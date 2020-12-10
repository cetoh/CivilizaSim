package com.toh.us.CivilizaSim.GameObjects.Buildings;

public enum BuildingName {
    AQUEDUCT("AQUEDUCT"),
    BARRACKS("BARRACKS"),
    MARKETPLACE("MARKETPLACE"),
    WALLS("WALLS");

    public final String name;

    BuildingName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
