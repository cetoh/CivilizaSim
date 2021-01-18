package com.toh.us.CivilizaSim.GameObjects.Civ.Interact;

import com.toh.us.CivilizaSim.Display.PrimaryController;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Civ.Civilization;
import com.toh.us.CivilizaSim.GameObjects.People.*;
import com.toh.us.CivilizaSim.GameObjects.Resources.Warehouse;
import com.toh.us.CivilizaSim.GameObjects.Simulate.MathUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Train {

    private Civilization civilization;

    private PrimaryController controller;

    private boolean showLog;

    public Train(PrimaryController controller, boolean showLog) {
        this.controller = controller;
        this.showLog    = showLog;
    }

    public void train(Civilization civilization) {
        trainSoldiers(civilization);

        trainScholars(civilization);

        trainPriests(civilization);
    }

    private void trainSoldiers(Civilization civilization) {
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

    private void trainScholars(Civilization civilization) {
        int numberToTrain = 1;
        int trainChance = 0;
        if (civilization.getBuildings().containsKey(BuildingName.ACADEMY))
            trainChance = 5 * civilization.getBuildings().get(BuildingName.ACADEMY).getLevel();

        if (MathUtils.getRandomNumber(0, 100) <= trainChance) {
            int numberTrained = 0;
            List<Scholar> priestsToAdd = new ArrayList<>();
            List<Person> civiliansToRemove = new ArrayList<>();

            List<Person> people = civilization.getPeople();
            Warehouse warehouse = civilization.getWarehouse();

            for (Person person : people) {
                if (person instanceof Civilian) {
                    if (warehouse.getIron().getAmount() >= 20
                            && warehouse.getWood().getAmount() >= 20
                            && warehouse.getClay().getAmount() >= 25
                            && warehouse.getGold().getAmount() >= 5) {
                        Scholar scholar = new Scholar((Civilian) person);
                        priestsToAdd.add(scholar);
                        civiliansToRemove.add(person);

                        warehouse.getClay().removeAmount(25);
                        warehouse.getIron().removeAmount(20);
                        warehouse.getWood().removeAmount(20);
                        warehouse.getGold().removeAmount(5);

                        if (showLog) {
                            controller.addLogMessage(civilization.getName() + " trained " + scholar.getName() + " into a scholar.");
                        }
                    } else {
                        if (showLog) {
                            controller.addLogMessage(civilization.getName() + " tried to train a scholar but had insufficient resources.");
                        }
                        break;
                    }
                    numberTrained++;
                    if (numberTrained >= numberToTrain)
                        break;
                }
            }
            try {
                people.addAll(priestsToAdd);
                people.removeAll(civiliansToRemove);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void trainPriests(Civilization civilization) {
        int numberToTrain = 1;
        int trainChance = 0;
        HashMap<BuildingName, Building> buildings = civilization.getBuildings();
        if (buildings.containsKey(BuildingName.CHURCH))
            trainChance = 5 * buildings.get(BuildingName.CHURCH).getLevel();
        if (buildings.containsKey(BuildingName.SEMINARY))
            trainChance += buildings.get(BuildingName.SEMINARY).getLevel();

        if (MathUtils.getRandomNumber(0, 100) <= trainChance) {
            int numberTrained = 0;
            List<Priest> priestsToAdd = new ArrayList<>();
            List<Person> civiliansToRemove = new ArrayList<>();

            List<Person> people = civilization.getPeople();
            Warehouse warehouse = civilization.getWarehouse();

            for (Person person : people) {
                if (person instanceof Civilian) {
                    if (warehouse.getIron().getAmount() >= 15
                            && warehouse.getWood().getAmount() >= 30
                            && warehouse.getClay().getAmount() >= 20
                            && warehouse.getWheat().getAmount() >= 10
                            && warehouse.getGold().getAmount() >= 5) {
                        Priest priest = new Priest((Civilian) person);
                        priestsToAdd.add(priest);
                        civiliansToRemove.add(person);

                        warehouse.getClay().removeAmount(25);
                        warehouse.getIron().removeAmount(20);
                        warehouse.getWood().removeAmount(20);
                        warehouse.getWheat().removeAmount(10);
                        warehouse.getGold().removeAmount(5);

                        if (showLog) {
                            controller.addLogMessage(civilization.getName() + " gained " + priest.getName() + " as a devout priest.");
                        }
                    } else {
                        if (showLog) {
                            controller.addLogMessage(civilization.getName() + " tried to encourage a priest to become devout but had insufficient resources.");
                        }
                        break;
                    }
                    numberTrained++;
                    if (numberTrained >= numberToTrain)
                        break;
                }
            }
            try {
                people.addAll(priestsToAdd);
                people.removeAll(civiliansToRemove);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
