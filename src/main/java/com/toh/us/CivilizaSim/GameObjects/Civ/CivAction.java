package com.toh.us.CivilizaSim.GameObjects.Civ;

import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;

public class CivAction {

    private CivActions action = CivActions.PRODUCE;

    private BuildingName buildingName = null;

    public CivAction() {

    }

    public CivAction(CivActions action) {
        this.action = action;
    }


    public CivActions getAction() {
        return action;
    }

    public void setAction(CivActions action) {
        this.action = action;
    }

    public BuildingName getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(BuildingName buildingName) {
        this.buildingName = buildingName;
    }
}
