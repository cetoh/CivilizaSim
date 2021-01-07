package com.toh.us.CivilizaSim.GameObjects.Buildings.Scientific;

import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingType;

public class University extends Building {
    public University() {
        super(BuildingName.UNIVERSITY,
                BuildingType.SCIENTIFIC,
                "Each level in this building increases the chance knowledge is generated during production by 5%.");

        this.getCost().getWheat().setAmount(100);
        this.getCost().getIron().setAmount(100);
        this.getCost().getWood().setAmount(60);
        this.getCost().getClay().setAmount(125);
        this.getCost().getGold().setAmount(5);
    }
}
