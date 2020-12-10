package com.toh.us.CivilizaSim.GameObjects.Buildings;

public class Marketplace extends Building {
    public Marketplace() {
        super(BuildingName.MARKETPLACE,
                BuildingType.ECONOMIC,
                "Each level in this building increases your trade yield with other civilizations by 1%.");

        this.getCost().getWheat().setAmount(125);
        this.getCost().getWood().setAmount(100);
        this.getCost().getClay().setAmount(150);
        this.getCost().getIron().setAmount(75);
        this.getCost().getGold().setAmount(25);
    }
}
