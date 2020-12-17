package com.toh.us.CivilizaSim.GameObjects.Civ;

import com.toh.us.CivilizaSim.Display.PrimaryController;
import com.toh.us.CivilizaSim.GameObjects.Buildings.*;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Economic.*;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Military.Barracks;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Military.Walls;
import com.toh.us.CivilizaSim.GameObjects.People.Civilian;
import com.toh.us.CivilizaSim.GameObjects.People.Person;
import com.toh.us.CivilizaSim.GameObjects.People.Soldier;
import com.toh.us.CivilizaSim.GameObjects.Resources.Resource;
import com.toh.us.CivilizaSim.GameObjects.Resources.Warehouse;
import com.toh.us.CivilizaSim.GameObjects.Simulate.MathUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Civilization {

    private String name;

    private List<Person> people = new ArrayList<>();

    private HashMap<BuildingName, Building> buildings = new HashMap<>();

    private Strategy strategy;

    private CivAction action;

    private Warehouse warehouse = new Warehouse();

    private PrimaryController controller;

    public Civilization(String name, PrimaryController controller) {
        this.name   = name;
        this.controller = controller;

        //All civs start with 100 of each resource
        warehouse.getWheat().setAmount(100);
        warehouse.getWood().setAmount(100);
        warehouse.getClay().setAmount(100);
        warehouse.getIron().setAmount(100);
        warehouse.getGold().setAmount(100);

        //All civs start with 2 people
        people.add(new Civilian(name));
        people.add(new Civilian(name));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name   = name;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public HashMap<BuildingName, Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(HashMap<BuildingName, Building> buildings) {
        this.buildings = buildings;
    }

    public CivAction getAction() {
        return action;
    }

    public void setAction(CivAction action) {
        this.action = action;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Warehouse getWarehouse() { return warehouse; }

    public Resource getRandomResource() {
        double check = Math.random();
        Resource toReturn;
        if (check <= 0.2) {
            toReturn =  warehouse.getWheat();
        }
        else if (check > 0.2 && check <= 0.4) {
            toReturn = warehouse.getIron();
        }
        else if (check > 0.4 && check <= 0.6) {
            toReturn = warehouse.getWood();
        }
        else if (check > 0.6 && check <= 0.7) {
            toReturn = warehouse.getClay();
        }
        else {
            toReturn = warehouse.getGold();
        }
        return toReturn;
    }

    public List<Soldier> getSoldiers() {
        List<Soldier> soldiers = new ArrayList<>();
        for (Person person : people) {
            if (person instanceof Soldier) {
                soldiers.add((Soldier) person);
            }
        }
        return soldiers;
    }

    private void swapPeople(Civilization otherCiv) {
        //Get random person from each
        int ind = MathUtils.getRandomNumber(0, people.size() - 1);
        int ind2 = MathUtils.getRandomNumber(0, otherCiv.getPeople().size() - 1);

        if (people.size() > ind && otherCiv.getPeople().size() > ind2) {
            try {
                Person person1 = people.remove(ind);
                Person person2 = otherCiv.getPeople().remove(ind2);

                people.add(person2);
                otherCiv.getPeople().add(person1);
                controller.addLogMessage(person1.getName() + " decided to stay with " + otherCiv.getName());
                controller.addLogMessage(person2.getName() + " decided to stay with " + this.name);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public void produce() {
        growResources();
        growPopulation();
    }

    private void growResources() {
        controller.addLogMessage(this.name + " produced some resources...");

        double aqueductBonus = 1;
        if (getBuildings().containsKey(BuildingName.AQUEDUCT)) {
            aqueductBonus += getBuildings().get(BuildingName.AQUEDUCT).getLevel() * 0.01;
        }
        double flourMillBonus = 0;
        if (getBuildings().containsKey(BuildingName.FLOUR_MILL)) {
            flourMillBonus += getBuildings().get(BuildingName.FLOUR_MILL).getLevel() * 0.05;
        }
        double lumberMillBonus = 0;
        if (getBuildings().containsKey(BuildingName.LUMBER_MILL)) {
            lumberMillBonus += getBuildings().get(BuildingName.LUMBER_MILL).getLevel() * 0.05;
        }
        double kilnBonus = 0;
        if (getBuildings().containsKey(BuildingName.KILN)) {
            kilnBonus += getBuildings().get(BuildingName.KILN).getLevel() * 0.05;
        }
        double mineBonus = 0;
        if (getBuildings().containsKey(BuildingName.MINE)) {
            mineBonus += getBuildings().get(BuildingName.MINE).getLevel() * 0.05;
        }
        double bankBonus = 0;
        if (getBuildings().containsKey(BuildingName.BANK)) {
            bankBonus += getBuildings().get(BuildingName.BANK).getLevel() * 0.05;
        }
        warehouse.getClay().setAmount((int) (warehouse.getClay().getAmount() + (100 * (aqueductBonus + kilnBonus))));
        warehouse.getWheat().setAmount((int) (warehouse.getWheat().getAmount() + (100 * (aqueductBonus + flourMillBonus))));
        warehouse.getIron().setAmount((int) (warehouse.getIron().getAmount() + (100 * (aqueductBonus + mineBonus))));
        warehouse.getWood().setAmount((int) (warehouse.getWood().getAmount() + (100 * (aqueductBonus + lumberMillBonus))));
        warehouse.getGold().setAmount((int) (warehouse.getGold().getAmount() + (10 * (aqueductBonus + bankBonus))));
    }

    private void growPopulation() {
        int growAmt = (int) (Math.ceil(Math.random() * 10));

        int actualGrown = 0;
        for (int i = 0; i < growAmt; i++) {
            if (warehouse.getWheat().getAmount() >=10) {
                Civilian civilian = new Civilian(getName());
                people.add(civilian);
                warehouse.getWheat().removeAmount(10);
                actualGrown++;
            }
        }
        int finalActualGrown = actualGrown;
        controller.addLogMessage(this.name + "'s population grew by " + finalActualGrown + "!");
    }

    public void train() {
        int numberToTrain = 1;
        if (this.getBuildings().containsKey(BuildingName.BARRACKS))
            numberToTrain += this.getBuildings().get(BuildingName.BARRACKS).getLevel();
        int numberTrained = 0;
        List<Soldier> soldiersToAdd = new ArrayList<>();
        List<Person> civiliansToRemove = new ArrayList<>();
        for (Person person : people) {
            if (person instanceof Civilian){
                if (warehouse.getIron().getAmount() >= 25
                        && warehouse.getWood().getAmount() >= 20
                        && warehouse.getWheat().getAmount() >= 20
                        && warehouse.getGold().getAmount() >= 5) {
                    Soldier soldier = new Soldier((Civilian) person);
                    soldiersToAdd.add(soldier);
                    civiliansToRemove.add(person);

                    warehouse.getWheat().removeAmount(20);
                    warehouse.getIron().removeAmount(25);
                    warehouse.getWood().removeAmount(20);
                    warehouse.getGold().removeAmount(5);

                    controller.addLogMessage(this.name + " trained " + soldier.getName() + " into a soldier.");
                }
                else {
                    controller.addLogMessage(this.name + " tried to train a soldier but had insufficient resources.");
                    break;
                }
                numberTrained++;
                if (numberTrained >= numberToTrain)
                    break;
            }
        }
        try {
            people.addAll(soldiersToAdd);
            people.removeAll(civiliansToRemove);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void trade(Civilization otherCiv, CivAction otherCivAction) {
        // Get other warehouse
        Warehouse otherCivWarehouse = otherCiv.getWarehouse();

        // Get both civilizations trade bonus
        double ourBonus = 1;
        double theirBonus = 1;
        if (getBuildings().containsKey(BuildingName.MARKETPLACE)) {
            ourBonus += getBuildings().get(BuildingName.MARKETPLACE).getLevel() * 0.01;
        }
        if (otherCiv.getBuildings().containsKey(BuildingName.MARKETPLACE)) {
            theirBonus += otherCiv.getBuildings().get(BuildingName.MARKETPLACE).getLevel() * 0.01;
        }

        //Both Civs will get a random amount of resources for each resource type
        //If the other Civ did not trade then both Civs will do a random amount exchange of a random set of resources
        //No resources are exchanged if the other civilization defended
        //but the trading civ gets some gold for their troubles
        switch (otherCivAction.getAction()) {
            case TRADE -> {
                otherCivWarehouse.getWheat().addAmount((int) (Math.random() * 100 * theirBonus));
                otherCivWarehouse.getIron().addAmount((int) (Math.random() * 100 * theirBonus));
                otherCivWarehouse.getWood().addAmount((int) (Math.random() * 100 * theirBonus));
                otherCivWarehouse.getClay().addAmount((int) (Math.random() * 100 * theirBonus));
                warehouse.getWheat().addAmount((int) (Math.random() * 100 * ourBonus));
                warehouse.getIron().addAmount((int) (Math.random() * 100 * ourBonus));
                warehouse.getWood().addAmount((int) (Math.random() * 100 * ourBonus));
                warehouse.getClay().addAmount((int) (Math.random() * 100 * ourBonus));
                otherCivWarehouse.getGold().addAmount((int) (Math.random() * 50 * theirBonus));
                warehouse.getGold().addAmount((int) (Math.random() * 50 * ourBonus));
                controller.addLogMessage("Both Civilizations traded peacefully, prospering from the encounter!");

                //Check if Civilians will swap
                if (Math.random() > 0.4) {
                    //Do this for a random number between 0 - 5
                    for (int i = 0; i < Math.random() * 5; i++) {
                        swapPeople(otherCiv);
                    }
                }
            }
            case PRODUCE, TRAIN -> {
                for (int i = 0; i < 5; i++) {
                    if (Math.random() >= 0.5) {
                        int amtToSwap = (int) (Math.random() * 100);
                        Resource resourceToGive = getRandomResource();
                        Resource theirResourceToGive = otherCiv.getRandomResource();

                        resourceToGive.removeAmount(amtToSwap);
                        theirResourceToGive.removeAmount(amtToSwap);

                        Resource resourceToGet = getRandomResource();
                        Resource theirResourceToGet = otherCiv.getRandomResource();

                        resourceToGet.addAmount(amtToSwap);
                        theirResourceToGet.addAmount(amtToSwap);
                    }
                }
                controller.addLogMessage(this.name + " traded peacefully, exchanging wares with " + otherCiv.getName() + ".");

                //Check if Civilians will swap
                if (Math.random() > 0.7) {
                    //Do this for a random number between 0 - 5
                    for (int i = 0; i < Math.random() * 5; i++) {
                        swapPeople(otherCiv);
                    }
                }
            }
            case DEFEND -> {
                warehouse.getGold().addAmount(10);
                controller.addLogMessage(otherCiv.getName() + " isolated themselves and chose not to trade, wary of possible attack. "
                        + this.getName() + " got 10 gold for their troubles.");
            }
        }
    }

    public void attack(Civilization civilization, boolean defended) {
        List<Soldier> theirSoldiers = civilization.getSoldiers();
        List<Soldier> ourSoldiers = this.getSoldiers();
        if (ourSoldiers.size() > 0) {
            if (defended) {
                //Wall bonus
                double bonus = 1;
                if (civilization.getBuildings().containsKey(BuildingName.WALLS))
                    bonus += civilization.getBuildings().get(BuildingName.WALLS).getLevel() * 0.01;

                ourSoldiers = ourSoldiers.subList(0, (int) (ourSoldiers.size() * 0.75 * bonus));
                theirSoldiers = theirSoldiers.subList(0, (int) ((theirSoldiers.size() / 2) * bonus));

                try {
                    civilization.getPeople().removeAll(theirSoldiers);
                    people.removeAll(ourSoldiers);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                controller.addLogMessage(this.name + " attacked " + civilization.getName()
                        + " but " + civilization.getName() + " defended successfully!");
            } else {
                theirSoldiers = theirSoldiers.subList(0, (int) (theirSoldiers.size() * 0.75));

                try {
                    civilization.getPeople().removeAll(theirSoldiers);

                    for (int i = 0; i < (int) (civilization.getPeople().size() * 0.3); i++) {
                        Person exile = civilization.getPeople().remove(i);
                        people.add(exile);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                //Steal their resources (75%)
                Warehouse theirWarehouse = civilization.getWarehouse();
                int gold = (int) (theirWarehouse.getGold().getAmount() * 0.75);
                int wheat = (int) (theirWarehouse.getWheat().getAmount() * 0.75);
                int wood = (int) (theirWarehouse.getWood().getAmount() * 0.75);
                int iron = (int) (theirWarehouse.getIron().getAmount() * 0.75);
                int clay = (int) (theirWarehouse.getClay().getAmount() * 0.75);

                theirWarehouse.getGold().removeAmount(gold);
                theirWarehouse.getWheat().removeAmount(wheat);
                theirWarehouse.getWood().removeAmount(wood);
                theirWarehouse.getIron().removeAmount(iron);
                theirWarehouse.getClay().removeAmount(clay);

                this.warehouse.getGold().addAmount(gold);
                this.warehouse.getWheat().addAmount(wheat);
                this.warehouse.getWood().addAmount(wood);
                this.warehouse.getIron().addAmount(iron);
                this.warehouse.getClay().addAmount(clay);

                controller.addLogMessage(this.name + " attacked and raided " + civilization.getName() + " exiling a portion of their people!");
            }
        } else {
            controller.addLogMessage(this.name + " has no soldiers to attack " + civilization.getName() + " so they just awkwardly looked at each other...");
        }
    }

    public void battle(Civilization civilization) {
        List<Soldier> ourSoldiers = getSoldiers();
        List<Soldier> theirSoldiers = civilization.getSoldiers();
        int numOurSoldiers = ourSoldiers.size();
        int numTheirSoldiers = theirSoldiers.size();

        controller.addLogMessage("A battle occurred!");
        // They win the battle
        if (numOurSoldiers < numTheirSoldiers) {
            try {
                people.removeAll(ourSoldiers);
                for (int i = 0; i < numOurSoldiers; i++) {
                    Soldier remove = theirSoldiers.remove(i);
                    civilization.getPeople().remove(remove);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            controller.addLogMessage(civilization.getName() + " won but at a great cost!");
        } else if (numTheirSoldiers < numOurSoldiers) {
            //We win the battle
            try {
                civilization.getPeople().removeAll(theirSoldiers);
                for (int i = 0; i < numTheirSoldiers; i++) {
                    Soldier remove = ourSoldiers.remove(i);
                    people.remove(remove);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            controller.addLogMessage(this.name + " won but at a great cost!");
        } else {
            //We are equally matched
            try {
                people.removeAll(ourSoldiers);
                civilization.getPeople().removeAll(theirSoldiers);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            controller.addLogMessage(this.getName() + " & " + civilization.getName() + " were equally matched, suffering many casualties!");
        }
    }

    public void build(BuildingName buildingName) {
        //Null Check
        if (buildingName == null) {
            controller.addLogMessage(this.name + " ordered building to be constructed but did not specify what to build! The workers sit around like socialists and nothing gets done!");
            return;
        }

        //First check if Civilization already has the building
        if (buildings.containsKey(buildingName)) {
            Building building = buildings.get(buildingName);

            Cost cost = building.getCost();

            if (checkIfWarehouseHasSufficientResources(cost)) {
                removeCostFromWarehouse(cost);
                building.upgradeLevel(1);
                buildings.put(buildingName, building);
                controller.addLogMessage(this.name + " upgraded " + buildingName.toString()
                        + " to level " + building.getLevel() + ".");
            }
            else {
                controller.addLogMessage(this.name + " attempted to upgrade " + buildingName.toString() + " but had insufficient resources!");
            }
        } else {
            Building building = switch (buildingName) {
                case AQUEDUCT -> new Aqueduct();
                case BANK -> new Bank();
                case BARRACKS -> new Barracks();
                case FLOUR_MILL -> new FlourMill();
                case KILN -> new Kiln();
                case LUMBER_MILL -> new LumberMill();
                case MARKETPLACE -> new Marketplace();
                case MINE -> new Mine();
                case WALLS -> new Walls();
            };

            if (checkIfWarehouseHasSufficientResources(building.getCost())) {
                removeCostFromWarehouse(building.getCost());
                building.upgradeLevel(1);
                buildings.put(buildingName, building);
                controller.addLogMessage(this.name + " upgraded " + buildingName.toString()
                        + " to level " + building.getLevel() + ".");
            }
            else {
                controller.addLogMessage(this.name + " attempted to build " + buildingName.toString() + " but had insufficient resources!");
            }
        }
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
}
