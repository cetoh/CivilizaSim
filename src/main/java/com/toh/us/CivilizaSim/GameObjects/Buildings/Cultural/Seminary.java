package com.toh.us.CivilizaSim.GameObjects.Buildings.Cultural;

import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingType;

public class Seminary extends Building {
    public Seminary() {
        super(BuildingName.SEMINARY,
                BuildingType.CULTURAL,
                "Each level in this building increases the chance faith is generated during production by 5%. It will also increase chance of a new priest by 1%.");

        this.getCost().getWheat().setAmount(150);
        this.getCost().getIron().setAmount(77);
        this.getCost().getWood().setAmount(130);
        this.getCost().getClay().setAmount(100);
        this.getCost().getGold().setAmount(7);
    }
}
