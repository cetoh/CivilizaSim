package com.toh.us.CivilizaSim.GameObjects.Buildings;

public abstract class Building {

    private int level = 0;

    private String description = "";

    private Cost cost = new Cost();

    private BuildingType buildingType;

    public Building() {

    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void upgradeLevel(int amount) {
        this.level += amount;
        cost.increaseCost(amount * 20);
    }

    public void downgradeLevel(int amount) {
        this.level -= amount;
        cost.decreaseCost(amount * 20);
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }
}
