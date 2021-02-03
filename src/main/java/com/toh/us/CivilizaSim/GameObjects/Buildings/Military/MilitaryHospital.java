package com.toh.us.CivilizaSim.GameObjects.Buildings.Military;

import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingType;

public class MilitaryHospital extends Building {
    public MilitaryHospital() {
        super(BuildingName.MILITARY_HOSPITAL,
                BuildingType.MILITARY,
                "Each level in this building will increase the number of soldiers who regain +5 health by 1 a Produce action.");

        this.getCost().getWheat().setAmount(75);
        this.getCost().getWood().setAmount(75);
        this.getCost().getClay().setAmount(75);
        this.getCost().getIron().setAmount(75);
    }
}
