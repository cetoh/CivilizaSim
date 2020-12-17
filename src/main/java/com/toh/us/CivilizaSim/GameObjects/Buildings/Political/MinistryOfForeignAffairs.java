package com.toh.us.CivilizaSim.GameObjects.Buildings.Political;

import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingType;

public class MinistryOfForeignAffairs extends Building {
    public MinistryOfForeignAffairs() {
        super(BuildingName.MINISTRY_OF_FOREIGN_AFFAIRS,
                BuildingType.POLITICAL,
                "Each level in this building increases the number of allies you can have by 1.");

        this.getCost().getWheat().setAmount(300);
        this.getCost().getIron().setAmount(450);
        this.getCost().getWood().setAmount(375);
        this.getCost().getClay().setAmount(400);
        this.getCost().getGold().setAmount(50);
    }
}
