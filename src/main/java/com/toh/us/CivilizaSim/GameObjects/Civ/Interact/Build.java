package com.toh.us.CivilizaSim.GameObjects.Civ.Interact;

import com.toh.us.CivilizaSim.Display.PrimaryController;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Cost;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Cultural.Amphitheater;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Cultural.Church;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Cultural.Seminary;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Economic.*;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Military.Barracks;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Military.Walls;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Political.Embassy;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Political.MinistryOfCommerce;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Political.MinistryOfForeignAffairs;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Political.MinistryOfIntelligence;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Scientific.Academy;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Scientific.University;
import com.toh.us.CivilizaSim.GameObjects.Civ.Civilization;
import com.toh.us.CivilizaSim.GameObjects.Resources.Warehouse;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class Build {

    private Warehouse warehouse;

    private PrimaryController controller;

    private boolean showLog;

    public Build(PrimaryController controller, boolean showLog) {
        this.controller = controller;
        this.showLog    = showLog;
    }

    public void build(Civilization civilization) {
        //Get Building Name
        BuildingName buildingName = civilization.getAction().getBuildingName();

        //Null Check
        if (buildingName == null) {
            controller.addLogMessage(civilization.getName() + " ordered building to be constructed but did not specify what to build! The workers sit around like socialists and nothing gets done!");
            return;
        }

        //First check if Civilization already has the building
        HashMap<BuildingName, Building> buildings = civilization.getBuildings();
        this.warehouse = civilization.getWarehouse();

        if (buildings.containsKey(buildingName)) {
            Building building = buildings.get(buildingName);

            Cost cost = building.getCost();

            if (checkIfWarehouseHasSufficientResources(cost)) {
                removeCostFromWarehouse(cost);
                building.upgradeLevel(1);
                buildings.put(buildingName, building);

                if (showLog) {
                    controller.addLogMessage(civilization.getName() + " upgraded " + buildingName.toString()
                            + " to level " + building.getLevel() + ".");
                }
            }
            else {
                if (showLog) {
                    controller.addLogMessage(civilization.getName() + " attempted to upgrade " + buildingName.toString()
                            + " but had insufficient resources!");
                }
            }
        } else {
            Building building = switch (buildingName) {
                case ACADEMY -> new Academy();
                case AMPHITHEATER -> new Amphitheater();
                case AQUEDUCT -> new Aqueduct();
                case BANK -> new Bank();
                case BARRACKS -> new Barracks();
                case CHURCH -> new Church();
                case EMBASSY -> new Embassy();
                case FLOUR_MILL -> new FlourMill();
                case KILN -> new Kiln();
                case LUMBER_MILL -> new LumberMill();
                case MARKETPLACE -> new Marketplace();
                case MINISTRY_OF_COMMERCE -> new MinistryOfCommerce();
                case MINISTRY_OF_FOREIGN_AFFAIRS -> new MinistryOfForeignAffairs();
                case MINISTRY_OF_INTELLIGENCE -> new MinistryOfIntelligence();
                case MINE -> new Mine();
                case SEMINARY -> new Seminary();
                case UNIVERSITY -> new University();
                case WALLS -> new Walls();
            };

            if (checkIfWarehouseHasSufficientResources(building.getCost())) {
                removeCostFromWarehouse(building.getCost());
                building.upgradeLevel(1);
                buildings.put(buildingName, building);
                if (showLog) {
                    controller.addLogMessage(civilization.getName() + " upgraded " + buildingName.toString()
                            + " to level " + building.getLevel() + ".");
                }
            }
            else {
                if (showLog) {
                    controller.addLogMessage(civilization.getName() + " attempted to build " + buildingName.toString()
                            + " but had insufficient resources!");
                }
            }
        }

        //If Player update GUI
        if (civilization.getName().equals(controller.getPlayerCivilizationName())) {
            updatePlayerGUI();
        }
    }

    private void updatePlayerGUI() {
       controller.updatePlayerGUI();
    }

    private boolean checkIfWarehouseHasSufficientResources(Cost cost) {
        return cost.getWheat().getAmount() <= warehouse.getWheat().getAmount()
                && cost.getIron().getAmount() <= warehouse.getIron().getAmount()
                && cost.getClay().getAmount() <= warehouse.getClay().getAmount()
                && cost.getWood().getAmount() <= warehouse.getClay().getAmount()
                && cost.getGold().getAmount() <= warehouse.getGold().getAmount();
    }

    private void removeCostFromWarehouse(Cost cost) {
        warehouse.getWheat().removeAmount(cost.getWheat().getAmount());
        warehouse.getIron().removeAmount(cost.getIron().getAmount());
        warehouse.getWood().removeAmount(cost.getWood().getAmount());
        warehouse.getClay().removeAmount(cost.getClay().getAmount());
        warehouse.getGold().removeAmount(cost.getGold().getAmount());
    }

    public static Cost getBuildingCost(BuildingName buildingName) {
        Building building = switch (buildingName) {
            case ACADEMY -> new Academy();
            case AMPHITHEATER -> new Amphitheater();
            case AQUEDUCT -> new Aqueduct();
            case BANK -> new Bank();
            case BARRACKS -> new Barracks();
            case CHURCH -> new Church();
            case EMBASSY -> new Embassy();
            case FLOUR_MILL -> new FlourMill();
            case KILN -> new Kiln();
            case LUMBER_MILL -> new LumberMill();
            case MARKETPLACE -> new Marketplace();
            case MINISTRY_OF_COMMERCE -> new MinistryOfCommerce();
            case MINISTRY_OF_FOREIGN_AFFAIRS -> new MinistryOfForeignAffairs();
            case MINISTRY_OF_INTELLIGENCE -> new MinistryOfIntelligence();
            case MINE -> new Mine();
            case SEMINARY -> new Seminary();
            case UNIVERSITY -> new University();
            case WALLS -> new Walls();
        };

        return building.getCost();
    }
}
