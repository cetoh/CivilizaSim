package com.toh.us.CivilizaSim.GameObjects.Buildings.Economic;

import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingType;

public class WaterMill extends Building {
    public WaterMill() {
        super(BuildingName.WATER_MILL,
                BuildingType.ECONOMIC,
                "Each level in this building increases all other resource economic buildings' bonus by 1%.");

        this.getCost().getWheat().setAmount(50);
        this.getCost().getWood().setAmount(100);
        this.getCost().getClay().setAmount(100);
        this.getCost().getIron().setAmount(100);
        this.getCost().getGold().setAmount(1);
    }
}
