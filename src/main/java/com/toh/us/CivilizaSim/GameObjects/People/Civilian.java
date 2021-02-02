package com.toh.us.CivilizaSim.GameObjects.People;

public class Civilian extends Person {

    public Civilian(String originalCivilization) {
        super(originalCivilization);
        setAttackStat(5);
        setDefenseStat(10);
    }
}
