package com.toh.us.CivilizaSim.GameObjects.Buildings;

public class Kiln extends Building {
    public Kiln() {
        super(BuildingName.KILN,
                BuildingType.ECONOMIC,
                "Each level in this building increases Clay production by 5%.");

        this.getCost().getWheat().setAmount(100);
        this.getCost().getIron().setAmount(100);
        this.getCost().getWood().setAmount(125);
        this.getCost().getClay().setAmount(175);
        this.getCost().getGold().setAmount(10);
    }
}
