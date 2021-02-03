package com.toh.us.CivilizaSim.GameObjects.Buildings.Military;

import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingType;

public class Blacksmith extends Building {
    public Blacksmith() {
        super(BuildingName.BLACKSMITH,
                BuildingType.MILITARY,
                "Each level in this building will increase the number of soldiers who gain +1 attack stat by 1 during a Produce action.");

        this.getCost().getWheat().setAmount(50);
        this.getCost().getWood().setAmount(75);
        this.getCost().getClay().setAmount(100);
        this.getCost().getIron().setAmount(100);
    }
}
