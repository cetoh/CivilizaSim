package com.toh.us.CivilizaSim.GameObjects.Buildings.Cultural;

import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingType;

public class Amphitheater extends Building {
    public Amphitheater() {
        super(BuildingName.AMPHITHEATER,
                BuildingType.CULTURAL,
                "Each level in this building increases your increases the chance a person from another civilization joins yours during a trade initiated by you by 1%.");

        this.getCost().getWheat().setAmount(100);
        this.getCost().getIron().setAmount(50);
        this.getCost().getWood().setAmount(150);
        this.getCost().getClay().setAmount(75);
        this.getCost().getGold().setAmount(15);
    }

}
