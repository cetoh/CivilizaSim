package com.toh.us.CivilizaSim.GameObjects.Buildings.Scientific;

import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingType;

public class Academy extends Building {
    public Academy() {
        super(BuildingName.ACADEMY,
                BuildingType.SCIENTIFIC,
                "Each level in this building increases the chance a Civilian becomes a Scholar by 5%.");

        this.getCost().getWheat().setAmount(175);
        this.getCost().getIron().setAmount(100);
        this.getCost().getWood().setAmount(60);
        this.getCost().getClay().setAmount(80);
        this.getCost().getGold().setAmount(5);
    }
}
