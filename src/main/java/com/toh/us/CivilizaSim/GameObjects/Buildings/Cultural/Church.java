package com.toh.us.CivilizaSim.GameObjects.Buildings.Cultural;

import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingType;

public class Church extends Building {
    public Church() {
        super(BuildingName.CHURCH,
                BuildingType.CULTURAL,
                "Each level in this building increases the chance a Civilian becomes a Priest by 5%.");

        this.getCost().getWheat().setAmount(100);
        this.getCost().getIron().setAmount(50);
        this.getCost().getWood().setAmount(80);
        this.getCost().getClay().setAmount(75);
        this.getCost().getGold().setAmount(1);
    }
}
