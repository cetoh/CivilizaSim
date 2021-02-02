package com.toh.us.CivilizaSim.GameObjects.People;

public class Priest extends Person {
    public Priest(Civilian civilian) {
        super(civilian.getOriginalCivilization(), civilian.getName());
        setAttackStat(0);
        setDefenseStat(0);
    }
}
