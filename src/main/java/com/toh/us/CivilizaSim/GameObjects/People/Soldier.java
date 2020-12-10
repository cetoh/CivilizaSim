package com.toh.us.CivilizaSim.GameObjects.People;

public class Soldier extends Person {
    public Soldier(Civilian civilian) {
        super(civilian.getOriginalCivilization(), civilian.getName());
    }
}
