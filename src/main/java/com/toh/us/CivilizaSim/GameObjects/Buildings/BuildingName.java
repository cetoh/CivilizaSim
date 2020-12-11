package com.toh.us.CivilizaSim.GameObjects.Buildings;

public enum BuildingName {
    AQUEDUCT("Aqueduct"),
    BANK("Bank"),
    BARRACKS("Barracks"),
    FLOUR_MILL("Flour Mill"),
    KILN("Kiln"),
    LUMBER_MILL("Lumber Mill"),
    MARKETPLACE("Marketplace"),
    MINE("Mine"),
    WALLS("Walls");

    public final String name;

    BuildingName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
