package com.toh.us.CivilizaSim.GameObjects.Civ;

import com.toh.us.CivilizaSim.GameObjects.People.Civilian;
import com.toh.us.CivilizaSim.GameObjects.People.Person;
import com.toh.us.CivilizaSim.GameObjects.Resources.Warehouse;

import java.util.HashSet;

public class Civilization {

    private String name;

    private HashSet<Person> people = new HashSet<Person>();

    private Strategy strategy;

    private CivActions action;

    private Warehouse warehouse = new Warehouse();

    public Civilization() {
        //All civs start with 100 of each resource
        warehouse.getWheat().setAmount(100);
        warehouse.getWheat().setAmount(100);
        warehouse.getClay().setAmount(100);
        warehouse.getIron().setAmount(100);
        warehouse.getGold().setAmount(100);

        //All civs start with 2 people
        people.add(new Civilian());
        people.add(new Civilian());
    }

    public Civilization(String name) {
        this.name   = name;

        //All civs start with 100 of each resource
        warehouse.getWheat().setAmount(100);
        warehouse.getWheat().setAmount(100);
        warehouse.getClay().setAmount(100);
        warehouse.getIron().setAmount(100);
        warehouse.getGold().setAmount(100);

        //All civs start with 2 people
        people.add(new Civilian());
        people.add(new Civilian());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name   = name;
    }

    public HashSet<Person> getPeople() {
        return people;
    }

    public void setPeople(HashSet<Person> people) {
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
}
