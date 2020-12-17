package com.toh.us.CivilizaSim.GameObjects.Buildings;

public enum BuildingName {
    ACADEMY("Academy"),
    AMPHITHEATER("Amphitheater"),
    AQUEDUCT("Aqueduct"),
    BANK("Bank"),
    BARRACKS("Barracks"),
    EMBASSY("Embassy"),
    FLOUR_MILL("Flour Mill"),
    KILN("Kiln"),
    LUMBER_MILL("Lumber Mill"),
    MARKETPLACE("Marketplace"),
    MINISTRY_OF_COMMERCE("Ministry of Commerce"),
    MINISTRY_OF_FOREIGN_AFFAIRS("Ministry of Foreign Affairs"),
    MINISTRY_OF_INTELLIGENCE("Ministry of Intelligence"),
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
