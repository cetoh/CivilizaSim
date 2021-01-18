package com.toh.us.CivilizaSim.GameObjects.Civ.Technology;

import java.util.HashMap;

public class Technologies {

    private HashMap<String, Technology> researched = new HashMap<>();
    private HashMap<String, Technology> unresearched = new HashMap<>();

    public Technologies() {

    }


    public HashMap<String, Technology> getResearchedTechnologies() {
        return researched;
    }

    public HashMap<String, Technology> getUnresearchedTechnologies() {
        return unresearched;
    }
}
