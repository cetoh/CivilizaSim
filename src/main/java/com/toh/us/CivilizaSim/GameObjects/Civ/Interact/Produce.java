package com.toh.us.CivilizaSim.GameObjects.Civ.Interact;

import com.toh.us.CivilizaSim.Display.PrimaryController;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Civ.Civilization;
import com.toh.us.CivilizaSim.GameObjects.People.Civilian;
import com.toh.us.CivilizaSim.GameObjects.People.Person;
import com.toh.us.CivilizaSim.GameObjects.People.Priest;
import com.toh.us.CivilizaSim.GameObjects.People.Scholar;
import com.toh.us.CivilizaSim.GameObjects.Resources.Warehouse;
import com.toh.us.CivilizaSim.GameObjects.Simulate.MathUtils;

import java.util.HashMap;
import java.util.List;

public class Produce {

    private Civilization civilization;

    private PrimaryController controller;

    private boolean showLog;

    public Produce(PrimaryController controller, boolean showLog) {
        this.controller = controller;
        this.showLog    = showLog;
    }

    public void produce(Civilization civilization) {
        this.civilization = civilization;
        growResources();
        growPopulation();
        generateKnowledge();
        generateFaith();
    }

    private void growResources() {
        if (showLog) {
            controller.addLogMessage(civilization.getName() + " produced some resources...");
        }

        double aqueductBonus = 1;
        HashMap<BuildingName, Building> buildings = civilization.getBuildings();
        if (buildings.containsKey(BuildingName.AQUEDUCT)) {
            aqueductBonus += (buildings.get(BuildingName.AQUEDUCT).getLevel() * 0.01);
        }
        double flourMillBonus = 0;
        if (buildings.containsKey(BuildingName.FLOUR_MILL)) {
            flourMillBonus += (buildings.get(BuildingName.FLOUR_MILL).getLevel() * 0.05);
        }
        double lumberMillBonus = 0;
        if (buildings.containsKey(BuildingName.LUMBER_MILL)) {
            lumberMillBonus += (buildings.get(BuildingName.LUMBER_MILL).getLevel() * 0.05);
        }
        double kilnBonus = 0;
        if (buildings.containsKey(BuildingName.KILN)) {
            kilnBonus += (buildings.get(BuildingName.KILN).getLevel() * 0.05);
        }
        double mineBonus = 0;
        if (buildings.containsKey(BuildingName.MINE)) {
            mineBonus += (buildings.get(BuildingName.MINE).getLevel() * 0.05);
        }
        double bankBonus = 0;
        if (buildings.containsKey(BuildingName.BANK)) {
            bankBonus += (buildings.get(BuildingName.BANK).getLevel() * 0.1);
        }

        int commerce = 0;
        if (buildings.containsKey(BuildingName.MINISTRY_OF_COMMERCE)){
            commerce = buildings.get(BuildingName.MINISTRY_OF_COMMERCE).getLevel();
        }
        Warehouse warehouse = civilization.getWarehouse();
        warehouse.getClay().setAmount((int) (warehouse.getClay().getAmount() + ((50 + (10 * commerce))* (aqueductBonus + kilnBonus))));
        warehouse.getWheat().setAmount((int) (warehouse.getWheat().getAmount() + ((50 + (10 * commerce)) * (aqueductBonus + flourMillBonus))));
        warehouse.getIron().setAmount((int) (warehouse.getIron().getAmount() + ((50 + (10 * commerce)) * (aqueductBonus + mineBonus))));
        warehouse.getWood().setAmount((int) (warehouse.getWood().getAmount() + ((50 + (10 * commerce)) * (aqueductBonus + lumberMillBonus))));
        warehouse.getGold().setAmount((int) (warehouse.getGold().getAmount() + ((10 + (2 * commerce)) * (aqueductBonus + bankBonus))));
    }

    private void growPopulation() {
        int growAmt = (int) (Math.ceil(Math.random() * 5));

        int actualGrown = 0;
        Warehouse warehouse = civilization.getWarehouse();
        List<Person> people = civilization.getPeople();
        for (int i = 0; i < growAmt; i++) {
            if (warehouse.getWheat().getAmount() >= 5) {
                Civilian civilian = new Civilian(civilization.getName());
                people.add(civilian);
                warehouse.getWheat().removeAmount(5);
                actualGrown++;
            }
        }
        int finalActualGrown = actualGrown;
        if (showLog) {
            controller.addLogMessage(civilization.getName() + "'s population grew by " + finalActualGrown + "!");
        }
    }

    private void generateKnowledge() {
        if (civilization.getBuildings().containsKey(BuildingName.UNIVERSITY)) {
            int chance = 5 * civilization.getBuildings().get(BuildingName.UNIVERSITY).getLevel();
            List<Scholar> scholars = civilization.getScholars();
            if (MathUtils.getRandomNumber(0, 100) <= chance && scholars.size() > 0) {
                civilization.getKnowledge().addAmount(scholars.size());
                controller.addLogMessage("The scholars of " + civilization.getName() + " generated " + scholars.size() + " knowledge!");
            }
        }
    }

    private void generateFaith() {
        if (civilization.getBuildings().containsKey(BuildingName.SEMINARY)) {
            int chance = 5 * civilization.getBuildings().get(BuildingName.SEMINARY).getLevel();
            List<Priest> priests = civilization.getPriests();
            if (MathUtils.getRandomNumber(0, 100) <= chance && priests.size() > 0) {
                int amount = priests.size() * 10;
                civilization.getFaith().addAmount(amount);
                controller.addLogMessage("The faithful of " + civilization.getName() + " produced " + amount + " faith!");
            }
        }
    }
}
