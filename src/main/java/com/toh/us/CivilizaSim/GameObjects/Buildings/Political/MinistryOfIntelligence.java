package com.toh.us.CivilizaSim.GameObjects.Buildings.Political;

import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingType;

public class MinistryOfIntelligence extends Building {
    public MinistryOfIntelligence() {
        super(BuildingName.MINISTRY_OF_INTELLIGENCE,
                BuildingType.POLITICAL,
                "Each level in this building increases the chance you spot an attack coming and successfully defend by 3%.");

        this.getCost().getWheat().setAmount(200);
        this.getCost().getIron().setAmount(375);
        this.getCost().getWood().setAmount(250);
        this.getCost().getClay().setAmount(200);
        this.getCost().getGold().setAmount(75);
    }
}
