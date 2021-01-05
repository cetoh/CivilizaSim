package com.toh.us.CivilizaSim.GameObjects.Buildings.Economic;

import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingType;

public class Bank extends Building {
    public Bank() {
        super(BuildingName.BANK,
                BuildingType.ECONOMIC,
                "Each level in this building increase Gold production by 10%.");

        this.getCost().getWheat().setAmount(90);
        this.getCost().getIron().setAmount(120);
        this.getCost().getWood().setAmount(110);
        this.getCost().getClay().setAmount(120);
        this.getCost().getGold().setAmount(50);
    }
}
