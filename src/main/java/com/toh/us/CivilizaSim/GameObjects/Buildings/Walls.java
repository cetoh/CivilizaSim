package com.toh.us.CivilizaSim.GameObjects.Buildings;

public class Walls extends Building {
    public Walls() {
        super(BuildingName.WALLS,
                BuildingType.MILITARY,
                "Each level in this building increases your defensive capabilities by 1%.");

        this.getCost().getWheat().setAmount(100);
        this.getCost().getWood().setAmount(175);
        this.getCost().getIron().setAmount(175);
        this.getCost().getClay().setAmount(200);
        this.getCost().getGold().setAmount(10);
    }
}
