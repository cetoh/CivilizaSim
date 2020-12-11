package com.toh.us.CivilizaSim.GameObjects.Buildings;

public class FlourMill extends Building {
    public FlourMill() {
        super(BuildingName.FLOUR_MILL,
                BuildingType.ECONOMIC,
                "Each level in this building increases wheat production by 5%.");

        this.getCost().getWheat().setAmount(175);
        this.getCost().getIron().setAmount(100);
        this.getCost().getWood().setAmount(100);
        this.getCost().getClay().setAmount(125);
        this.getCost().getGold().setAmount(10);
    }
}
