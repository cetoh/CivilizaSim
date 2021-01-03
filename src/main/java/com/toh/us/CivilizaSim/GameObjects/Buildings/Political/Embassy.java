package com.toh.us.CivilizaSim.GameObjects.Buildings.Political;

import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingType;

public class Embassy extends Building {
    public Embassy(){
        super(BuildingName.EMBASSY,
                BuildingType.POLITICAL,
                "Each level in this building increases the chance a civilization decides to be your ally by 1%.");

        this.getCost().getWheat().setAmount(90);
        this.getCost().getIron().setAmount(100);
        this.getCost().getWood().setAmount(150);
        this.getCost().getClay().setAmount(125);
        this.getCost().getGold().setAmount(35);
    }
}
