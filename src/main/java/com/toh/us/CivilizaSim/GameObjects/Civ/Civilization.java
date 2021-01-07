package com.toh.us.CivilizaSim.GameObjects.Civ;

import com.toh.us.CivilizaSim.GameObjects.Buildings.*;
import com.toh.us.CivilizaSim.GameObjects.People.Civilian;
import com.toh.us.CivilizaSim.GameObjects.People.Person;
import com.toh.us.CivilizaSim.GameObjects.People.Scholar;
import com.toh.us.CivilizaSim.GameObjects.People.Soldier;
import com.toh.us.CivilizaSim.GameObjects.Resources.Knowledge;
import com.toh.us.CivilizaSim.GameObjects.Resources.Resource;
import com.toh.us.CivilizaSim.GameObjects.Resources.Warehouse;

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

    private Knowledge knowledge = new Knowledge();

    public Civilization(String name) {
        this.name   = name;

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

    public List<Scholar> getScholars() {
        List<Scholar> scholars = new ArrayList<>();
        for (Person person : people) {
            if (person instanceof Scholar) {
                scholars.add((Scholar) person);
            }
        }
        return scholars;
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }
}
