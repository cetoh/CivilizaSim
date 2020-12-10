package com.toh.us.CivilizaSim.GameObjects.Buildings;

public enum BuildingType {
    ECONOMIC("ECONOMIC"),
    MILITARY("MILITARY"),
    POLITICAL("POLITICAL"),
    CULTURAL("CULTURAL"),
    ACADEMIC("ACADEMIC"),
    SPECIAL("SPECIAL");

    public final String type;

    BuildingType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
