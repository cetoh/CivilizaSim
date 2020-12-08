package com.toh.us.CivilizaSim.GameObjects.Buildings;

public class Walls extends Building {
    public Walls() {
        this.getCost().getWheat().setAmount(200);
        this.getCost().getWood().setAmount(350);
        this.getCost().getIron().setAmount(350);
        this.getCost().getClay().setAmount(400);
        this.getCost().getGold().setAmount(30);

        this.setBuildingType(BuildingType.MILITARY);
        this.setDescription("Each level in this building increases your defensive capabilities by 1%.");
    }
}
