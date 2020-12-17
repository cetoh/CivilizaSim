package com.toh.us.CivilizaSim.GameObjects.Buildings.Economic;

import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingType;

public class LumberMill extends Building {
    public LumberMill() {
        super(BuildingName.LUMBER_MILL,
                BuildingType.ECONOMIC,
                "Each level in this building increases Wood production by 5%.");
        this.getCost().getWheat().setAmount(100);
        this.getCost().getIron().setAmount(125);
        this.getCost().getWood().setAmount(175);
        this.getCost().getClay().setAmount(100);
        this.getCost().getGold().setAmount(10);
    }
}
