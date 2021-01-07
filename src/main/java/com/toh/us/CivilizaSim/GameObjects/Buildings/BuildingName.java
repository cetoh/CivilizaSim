package com.toh.us.CivilizaSim.GameObjects.Buildings;

import java.util.ArrayList;
import java.util.List;

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
    UNIVERSITY("University"),
    WALLS("Walls");

    public final String name;

    BuildingName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static List<BuildingName> getEconomicBuildings() {
        List<BuildingName> buildingNames = new ArrayList<>();
        buildingNames.add(AQUEDUCT);
        buildingNames.add(BANK);
        buildingNames.add(FLOUR_MILL);
        buildingNames.add(KILN);
        buildingNames.add(LUMBER_MILL);
        buildingNames.add(MARKETPLACE);
        buildingNames.add(MINE);

        return  buildingNames;
    }

    public static List<BuildingName> getMilitaryBuildings() {
        List<BuildingName> buildingNames = new ArrayList<>();
        buildingNames.add(BARRACKS);
        buildingNames.add(WALLS);

        return  buildingNames;
    }

    public static List<BuildingName> getCulturalBuildings() {
        List<BuildingName> buildingNames = new ArrayList<>();
        buildingNames.add(AMPHITHEATER);

        return  buildingNames;
    }

    public static List<BuildingName> getPoliticalBuildings() {
        List<BuildingName> buildingNames = new ArrayList<>();
        buildingNames.add(EMBASSY);
        buildingNames.add(MINISTRY_OF_COMMERCE);
        buildingNames.add(MINISTRY_OF_FOREIGN_AFFAIRS);
        buildingNames.add(MINISTRY_OF_INTELLIGENCE);

        return  buildingNames;
    }

    public static List<BuildingName> getScientificBuildings() {
        List<BuildingName> buildingNames = new ArrayList<>();
        buildingNames.add(ACADEMY);
        buildingNames.add(UNIVERSITY);

        return  buildingNames;
    }

    public static List<BuildingName> getSpecialBuildings() {
        List<BuildingName> buildingNames = new ArrayList<>();

        return  buildingNames;
    }
}
