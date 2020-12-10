package com.toh.us.CivilizaSim.GameObjects.Civ;

public abstract class Strategy {

    public Strategy() {

    }


    public abstract CivAction executeStrategy(CivPayouts lastPayout);
}
