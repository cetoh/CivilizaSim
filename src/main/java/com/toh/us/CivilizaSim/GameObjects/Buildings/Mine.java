package com.toh.us.CivilizaSim.GameObjects.Buildings;

public class Mine extends Building {
    public Mine() {
        super(BuildingName.MINE,
                BuildingType.ECONOMIC,
                "Each level in this building increases Iron production by 5%.");
        this.getCost().getWheat().setAmount(125);
        this.getCost().getIron().setAmount(175);
        this.getCost().getWood().setAmount(100);
        this.getCost().getClay().setAmount(100);
        this.getCost().getGold().setAmount(10);
    }
}
