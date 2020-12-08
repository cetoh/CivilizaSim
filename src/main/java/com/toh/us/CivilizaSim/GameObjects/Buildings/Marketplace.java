package com.toh.us.CivilizaSim.GameObjects.Buildings;

public class Marketplace extends Building {
    public Marketplace() {
        this.getCost().getWheat().setAmount(250);
        this.getCost().getWood().setAmount(200);
        this.getCost().getClay().setAmount(300);
        this.getCost().getIron().setAmount(150);
        this.getCost().getGold().setAmount(50);

        this.setBuildingType(BuildingType.ECONOMIC);
        this.setDescription("Each level in this building increases your trade yield with other civilizations by 1%.");
    }
}
