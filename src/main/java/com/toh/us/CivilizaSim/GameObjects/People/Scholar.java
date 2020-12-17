package com.toh.us.CivilizaSim.GameObjects.People;

public class Scholar extends Person {
    public Scholar(Civilian civilian) {
        super(civilian.getOriginalCivilization(), civilian.getName());
    }
}
