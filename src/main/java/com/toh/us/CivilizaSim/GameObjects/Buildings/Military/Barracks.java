package com.toh.us.CivilizaSim.GameObjects.Buildings.Military;

import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingType;

public class Barracks extends Building {

    public Barracks() {
        super(BuildingName.BARRACKS,
                BuildingType.MILITARY,
                "Each level in this building will increase the number of soldiers you can train for each Train Action by 1.");

        this.getCost().getWheat().setAmount(50);
        this.getCost().getWood().setAmount(100);
        this.getCost().getClay().setAmount(125);
        this.getCost().getIron().setAmount(150);
    }

}
