package com.toh.us.CivilizaSim.GameObjects.Buildings;

public class Bank extends Building {
    public Bank() {
        super(BuildingName.BANK,
                BuildingType.ECONOMIC,
                "Each level in this building increase Gold production by 5%.");

        this.getCost().getWheat().setAmount(110);
        this.getCost().getIron().setAmount(120);
        this.getCost().getWood().setAmount(110);
        this.getCost().getClay().setAmount(120);
        this.getCost().getGold().setAmount(50);
    }
}
