package com.toh.us.CivilizaSim.GameObjects.Civ.Interact;

import com.toh.us.CivilizaSim.Display.PrimaryController;

public class CivInteraction {

    private Attack attack;
    private Battle battle;
    private Build build;
    private Trade trade;
    private Train train;
    private Produce produce;

    public CivInteraction(PrimaryController controller, boolean showLog) {
        attack  = new Attack(controller, showLog);
        battle  = new Battle(controller, showLog);
        build   = new Build(controller, showLog);
        trade   = new Trade(controller, showLog);
        train   = new Train(controller, showLog);
        produce = new Produce(controller, showLog);
    }

    public Attack attack() {
        return this.attack;
    }

    public Battle battle() {
        return this.battle;
    }

    public Build build() {
        return this.build;
    }

    public Trade trade() {
        return this.trade;
    }

    public Train train() {
        return this.train;
    }

    public Produce produce() {
        return this.produce;
    }
}
