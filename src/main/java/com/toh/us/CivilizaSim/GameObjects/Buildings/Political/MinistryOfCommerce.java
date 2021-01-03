package com.toh.us.CivilizaSim.GameObjects.Buildings.Political;

import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingType;

public class MinistryOfCommerce extends Building {
    public MinistryOfCommerce() {
        super(BuildingName.MINISTRY_OF_COMMERCE,
                BuildingType.POLITICAL,
                "Each level in this building increases the amount of resources you get from a trade by 10 for each normal resource and 2 for each luxury resource BEFORE other bonuses are applied.");

        this.getCost().getWheat().setAmount(500);
        this.getCost().getIron().setAmount(250);
        this.getCost().getWood().setAmount(375);
        this.getCost().getClay().setAmount(500);
        this.getCost().getGold().setAmount(20);
    }
}
