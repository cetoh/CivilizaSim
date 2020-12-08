package com.toh.us.CivilizaSim.GameObjects.Buildings;

public class Barracks extends Building {

    public Barracks() {
        this.getCost().getWheat().setAmount(100);
        this.getCost().getWood().setAmount(200);
        this.getCost().getClay().setAmount(250);
        this.getCost().getIron().setAmount(300);

        this.setBuildingType(BuildingType.MILITARY);
        this.setDescription("Each level in this building will increase the number of soldiers you can train for each Train Action by 1.");
    }

}
