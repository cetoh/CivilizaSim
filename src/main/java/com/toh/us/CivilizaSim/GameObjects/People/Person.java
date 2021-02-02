package com.toh.us.CivilizaSim.GameObjects.People;

import com.toh.us.CivilizaSim.GameObjects.People.NameGen.Gender;
import com.toh.us.CivilizaSim.GameObjects.People.NameGen.Name;
import com.toh.us.CivilizaSim.GameObjects.People.NameGen.NameMachine;

public abstract class Person {

    private int health = 100;

    private int maxHealth = 100;

    private double attackStat;

    private double defenseStat;

    private Name name;

    private String originalCivilization = "";

    protected Person(String originalCivilization) {
        this.originalCivilization = originalCivilization;
        double chanceCheck = Math.random();
        Gender gender;
        if (chanceCheck <= 0.5) {
            gender = Gender.MALE;
        }
        else {
            gender = Gender.FEMALE;
        }
        NameMachine nameMachine = new NameMachine(1, gender.toString());
        name = nameMachine.getNames().get(0);

    }

    protected Person(String originalCivilization, Name name) {
        this.originalCivilization = originalCivilization;
        this.name                 = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void increaseHealth(int amount) {
        this.health += amount;
    }

    public void decreaseHealth(int amount) {
        this.health -= amount;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getOriginalCivilization() {
        return originalCivilization;
    }

    public void setOriginalCivilization(String originalCivilization) {
        this.originalCivilization = originalCivilization;
    }

    public double getAttackStat() {
        return attackStat;
    }

    public void setAttackStat(double attackStat) {
        this.attackStat = attackStat;
    }

    public void increaseAttackStat(int amount) {
        this.attackStat += amount;
    }

    public void decreaseAttackStat(int amount) {
        this.attackStat -= amount;
    }

    public double getDefenseStat() {
        return defenseStat;
    }

    public void setDefenseStat(double defenseStat) {
        this.defenseStat = defenseStat;
    }

    public void increaseDefenseStat(int amount) {
        this.defenseStat += amount;
    }

    public void decreaseDefenseStat(int amount) {
        this.defenseStat -= amount;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void increaseMaxHealth(int amount) {
        this.maxHealth += amount;
    }

    public void decreaseMaxHealth(int amount) {
        this.maxHealth -= amount;
    }
}
