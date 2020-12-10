package com.toh.us.CivilizaSim.GameObjects.Buildings;

public class Aqueduct extends Building {

    public Aqueduct() {
        super(BuildingName.AQUEDUCT,
                BuildingType.ECONOMIC,
                "Each level in this building increases your production gain by 1.5%.");

        this.getCost().getWheat().setAmount(75);
        this.getCost().getIron().setAmount(125);
        this.getCost().getWood().setAmount(135);
        this.getCost().getClay().setAmount(160);
        this.getCost().getGold().setAmount(15);
}
}
