package com.toh.us.CivilizaSim.GameObjects.Civ.Interact;

import com.toh.us.CivilizaSim.Display.PrimaryController;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Civ.Civilization;
import com.toh.us.CivilizaSim.GameObjects.People.Civilian;
import com.toh.us.CivilizaSim.GameObjects.People.Person;
import com.toh.us.CivilizaSim.GameObjects.People.Soldier;
import com.toh.us.CivilizaSim.GameObjects.Resources.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class Train {

    private Civilization civilization;

    private PrimaryController controller;

    private boolean showLog = false;

    public Train(PrimaryController controller, boolean showLog) {
        this.controller = controller;
        this.showLog    = showLog;
    }

    public void train(Civilization civilization) {
        int numberToTrain = 1;
        if (civilization.getBuildings().containsKey(BuildingName.BARRACKS))
            numberToTrain += civilization.getBuildings().get(BuildingName.BARRACKS).getLevel();
        int numberTrained = 0;
        List<Soldier> soldiersToAdd = new ArrayList<>();
        List<Person> civiliansToRemove = new ArrayList<>();

        List<Person> people = civilization.getPeople();
        Warehouse warehouse = civilization.getWarehouse();

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

                    if (showLog) {
                        controller.addLogMessage(civilization.getName() + " trained " + soldier.getName() + " into a soldier.");
                    }
                }
                else {
                    if (showLog) {
                        controller.addLogMessage(civilization.getName() + " tried to train a soldier but had insufficient resources.");
                    }
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
}
