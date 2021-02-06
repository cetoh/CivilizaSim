package com.toh.us.CivilizaSim.GameObjects.Civ.Technology;

import java.util.ArrayList;
import java.util.List;

public enum TechName {
    CALENDAR("Calendar"),
    FERTILIZER("Fertilizer"),
    FURNACES("Furnaces"),
    SERRATED_EDGES("Serrated Edges"),
    SOIL_ANALYSIS("Soil Analysis"),
    WRITING("Writing");

    public final String name;

    TechName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }


}
