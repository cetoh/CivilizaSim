package com.toh.us.CivilizaSim.GameObjects.People;

import com.toh.us.CivilizaSim.GameObjects.People.NameGen.Gender;
import com.toh.us.CivilizaSim.GameObjects.People.NameGen.Name;
import com.toh.us.CivilizaSim.GameObjects.People.NameGen.NameMachine;

public abstract class Person {

    private int health;

    private Name name;

    protected Person() {
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }
}
