package com.toh.us.CivilizaSim.GameObjects.Civ;

import com.toh.us.CivilizaSim.Display.PrimaryController;
import com.toh.us.CivilizaSim.GameObjects.People.Civilian;
import com.toh.us.CivilizaSim.GameObjects.People.Person;
import com.toh.us.CivilizaSim.GameObjects.People.Soldier;
import com.toh.us.CivilizaSim.GameObjects.Resources.Resource;
import com.toh.us.CivilizaSim.GameObjects.Resources.Warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Civilization {

    private String name;

    private List<Person> people = new ArrayList<>();

    private Strategy strategy;

    private CivActions action;

    private Warehouse warehouse = new Warehouse();

    private PrimaryController controller;

    public Civilization(String name, PrimaryController controller) {
        this.name   = name;
        this.controller = controller;

        //All civs start with 100 of each resource
        warehouse.getWheat().setAmount(100);
        warehouse.getWheat().setAmount(100);
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

    public CivActions getAction() {
        return action;
    }

    public void setAction(CivActions action) {
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
        Random rand = new Random();
        int ind = rand.ints(0, people.size() - 1).findFirst().getAsInt();
        Person person1 = people.remove(ind);

        int ind2 = rand.ints(0, otherCiv.getPeople().size() - 1).findFirst().getAsInt();
        Person person2 = otherCiv.getPeople().remove(ind2);

        people.add(person2);
        otherCiv.getPeople().add(person1);
        controller.addLogMessage(person1.getName() + " decided to stay with " + otherCiv.getName());
        controller.addLogMessage(person2.getName() + " decided to stay with " + this.name);
    }


    public void produce() {
        growResources();
        growPopulation();
    }

    private void growResources() {
        controller.addLogMessage(this.name + " produced some resources...");
        warehouse.getClay().setAmount(warehouse.getClay().getAmount() + 100);
        warehouse.getWheat().setAmount(warehouse.getWheat().getAmount() + 100);
        warehouse.getIron().setAmount(warehouse.getIron().getAmount() + 100);
        warehouse.getWood().setAmount(warehouse.getWood().getAmount() + 100);
        warehouse.getGold().setAmount(warehouse.getGold().getAmount() + 10);
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
        for (Person person : people) {
            if (person instanceof Civilian){
                if (warehouse.getIron().getAmount() >= 25
                        && warehouse.getWood().getAmount() >= 20
                        && warehouse.getWheat().getAmount() >= 20
                        && warehouse.getGold().getAmount() >= 5) {
                    Soldier soldier = new Soldier((Civilian) person);
                    people.add(soldier);
                    people.remove(person);

                    warehouse.getWheat().removeAmount(20);
                    warehouse.getIron().removeAmount(25);
                    warehouse.getWood().removeAmount(20);
                    warehouse.getGold().removeAmount(5);

                    controller.addLogMessage(this.name + " trained " + soldier.getName() + " into a soldier.");
                }
                else {
                    controller.addLogMessage(this.name + " tried to train a soldier but had insufficient resources.");
                }
                break;
            }
        }
    }

    public void trade(Civilization otherCiv, CivActions otherCivAction) {
        // Get other warehouse
        Warehouse otherCivWarehouse = otherCiv.getWarehouse();

        //Both Civs will get a random amount of resources for each resource type
        //If the other Civ did not trade then both Civs will do a random amount exchange of a random set of resources
        //No resources are exchanged if the other civilization defended
        //but the trading civ gets some gold for their troubles
        switch (otherCivAction) {
            case TRADE -> {
                otherCivWarehouse.getWheat().addAmount((int) (Math.random() * 100));
                otherCivWarehouse.getIron().addAmount((int) (Math.random() * 100));
                otherCivWarehouse.getWood().addAmount((int) (Math.random() * 100));
                otherCivWarehouse.getClay().addAmount((int) (Math.random() * 100));
                warehouse.getWheat().addAmount((int) (Math.random() * 100));
                warehouse.getIron().addAmount((int) (Math.random() * 100));
                warehouse.getWood().addAmount((int) (Math.random() * 100));
                warehouse.getClay().addAmount((int) (Math.random() * 100));
                otherCivWarehouse.getGold().addAmount((int) (Math.random() * 50));
                warehouse.getGold().addAmount((int) (Math.random() * 50));
                controller.addLogMessage("Both Civilizations traded peacefully, prospering from the encounter!");

                //Check if Civilians will swap
                if (Math.random() > 0.4) {
                    //Do this for a random number between 0 - 5
                    for (int i = - 1; i < Math.random() * 5; i++) {
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
                    for (int i = - 1; i < Math.random() * 5; i++) {
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
        List<Soldier> ourSoldiers = getSoldiers();
        if (ourSoldiers.size() > 0) {
            if (defended) {
                theirSoldiers = theirSoldiers.subList(0, (int) (theirSoldiers.size() * 0.75));
                ourSoldiers = ourSoldiers.subList(0, (ourSoldiers.size() / 2));

                civilization.getPeople().removeAll(theirSoldiers);
                people.removeAll(ourSoldiers);
                controller.addLogMessage(this.name + " attacked " + civilization.getName()
                        + " but " + civilization.getName() + " defended successfully!");
            } else {
                theirSoldiers = theirSoldiers.subList(0, (int) (theirSoldiers.size() * 0.75));

                civilization.getPeople().removeAll(theirSoldiers);

                List<Person> theirRemainingPeople = civilization.getPeople();
                List<Person> peopleToExile = theirRemainingPeople.subList(0, (int) (theirRemainingPeople.size() * 0.3));

                people.addAll(peopleToExile);
                civilization.getPeople().removeAll(peopleToExile);

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
            people.removeAll(ourSoldiers);
            for (int i = 0; i < numTheirSoldiers - numOurSoldiers - 1; i++) {
                Soldier remove = theirSoldiers.remove(i);
            }
            civilization.getPeople().removeAll(theirSoldiers);

            controller.addLogMessage(civilization.getName() + " won but at a great cost!");
        } else if (numTheirSoldiers < numOurSoldiers) {
            //We win the battle
            civilization.getPeople().removeAll(theirSoldiers);
            for (int i = 0; i < numOurSoldiers - numTheirSoldiers - 1; i++) {
                Soldier remove = ourSoldiers.remove(i);
            }
            people.removeAll(ourSoldiers);

            controller.addLogMessage(this.name + " won but at a great cost!");
        } else {
            //We are equally matched
            people.removeAll(ourSoldiers);
            civilization.getPeople().removeAll(theirSoldiers);

            controller.addLogMessage(this.getName() + " & " + civilization.getName() + " were equally matched, suffering many casualties!");
        }
    }
}
