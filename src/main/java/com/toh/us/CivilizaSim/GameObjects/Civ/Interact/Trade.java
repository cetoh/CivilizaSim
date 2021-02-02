package com.toh.us.CivilizaSim.GameObjects.Civ.Interact;

import com.toh.us.CivilizaSim.Display.PrimaryController;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Civ.Civilization;
import com.toh.us.CivilizaSim.GameObjects.People.Person;
import com.toh.us.CivilizaSim.GameObjects.Resources.Resource;
import com.toh.us.CivilizaSim.GameObjects.Resources.Warehouse;
import com.toh.us.CivilizaSim.GameObjects.Simulate.MathUtils;

public class Trade {

    private PrimaryController controller;

    private boolean showLog;

    public Trade(PrimaryController controller, boolean showLog) {
        this.controller = controller;
        this.showLog    = showLog;
    }

    public void trade(Civilization civ1, Civilization civ2) {
        //Get first warehouse
        Warehouse civ1Warehouse = civ1.getWarehouse();

        // Get other warehouse
        Warehouse civ2Warehouse = civ2.getWarehouse();

        // Get both civilizations trade bonus
        double ourBonus = 1;
        double theirBonus = 1;
        if (civ1.getBuildings().containsKey(BuildingName.MARKETPLACE)) {
            ourBonus += civ1.getBuildings().get(BuildingName.MARKETPLACE).getLevel() * 0.05;
        }
        if (civ2.getBuildings().containsKey(BuildingName.MARKETPLACE)) {
            theirBonus += civ2.getBuildings().get(BuildingName.MARKETPLACE).getLevel() * 0.05;
        }

        // Do Amphitheater check
        if (civ1.getBuildings().containsKey(BuildingName.AMPHITHEATER)) {
            int check = civ1.getBuildings().get(BuildingName.AMPHITHEATER).getLevel();
            if (MathUtils.getRandomNumber(0, 100) <= check) {
                Person person = civ2.getPeople().remove(MathUtils.getRandomNumber(0, civ2.getPeople().size() - 1));
                civ1.getPeople().add(person);

                if (showLog) {
                    controller.addLogMessage(person.getName() + ", resident of " + civ2.getName()
                            + ", originally from " + person.getOriginalCivilization()
                            + ", was so enthralled by the culture of " + civ1.getName() + " and decided to stay there.");
                }
            }
        }

        //Both Civs will get a random amount of resources for each resource type
        //If the other Civ did not trade then both Civs will do a random amount exchange of a random set of resources
        //No resources are exchanged if the other civilization defended
        //but the trading civ gets some gold for their troubles
        switch (civ2.getAction().getAction()) {
            case TRADE -> {
                civ2Warehouse.getWheat().addAmount((int) (Math.random() * 100 * theirBonus));
                civ2Warehouse.getIron().addAmount((int) (Math.random() * 100 * theirBonus));
                civ2Warehouse.getWood().addAmount((int) (Math.random() * 100 * theirBonus));
                civ2Warehouse.getClay().addAmount((int) (Math.random() * 100 * theirBonus));
                civ1Warehouse.getWheat().addAmount((int) (Math.random() * 100 * ourBonus));
                civ1Warehouse.getIron().addAmount((int) (Math.random() * 100 * ourBonus));
                civ1Warehouse.getWood().addAmount((int) (Math.random() * 100 * ourBonus));
                civ1Warehouse.getClay().addAmount((int) (Math.random() * 100 * ourBonus));
                civ2Warehouse.getGold().addAmount((int) (Math.random() * 50 * theirBonus));
                civ1Warehouse.getGold().addAmount((int) (Math.random() * 50 * ourBonus));

                if (showLog) {
                    controller.addLogMessage("Both Civilizations traded peacefully, prospering from the encounter!");
                }

                //Check if Civilians will swap
                if (Math.random() > 0.4) {
                    //Do this for a random number between 0 - 5
                    for (int i = 0; i < Math.random() * 5; i++) {
                        swapPeople(civ1, civ2);
                    }
                }
            }
            case PRODUCE, TRAIN, BUILD -> {
                for (int i = 0; i < 5; i++) {
                    if (Math.random() >= 0.5) {
                        int amtToSwap = (int) (Math.random() * 100);
                        Resource resourceToGive = civ1.getRandomResource();
                        Resource theirResourceToGive = civ2.getRandomResource();

                        resourceToGive.removeAmount(amtToSwap);
                        theirResourceToGive.removeAmount(amtToSwap);

                        Resource resourceToGet = civ1.getRandomResource();
                        Resource theirResourceToGet = civ2.getRandomResource();

                        resourceToGet.addAmount((int) (amtToSwap * 1.1));
                        theirResourceToGet.addAmount((int) (amtToSwap * 1.1));
                    }
                }

                if (showLog) {
                    controller.addLogMessage(civ1.getName() + " traded peacefully, exchanging wares with " + civ2.getName() + ".");
                }

                //Check if Civilians will swap
                if (Math.random() > 0.7) {
                    //Do this for a random number between 0 - 5
                    for (int i = 0; i < Math.random() * 5; i++) {
                        swapPeople(civ1, civ2);
                    }
                }
            }
            case DEFEND -> {
                civ1Warehouse.getGold().addAmount(10);

                if (showLog) {
                    controller.addLogMessage(civ2.getName() + " isolated themselves and chose not to trade, wary of possible attack. "
                            + civ1.getName() + " got 10 gold for their troubles.");
                }
            }
        }
    }

    private void swapPeople(Civilization civ1, Civilization civ2) {
        //Get random person from each
        int ind = MathUtils.getRandomNumber(0, civ1.getPeople().size() - 1);
        int ind2 = MathUtils.getRandomNumber(0, civ2.getPeople().size() - 1);

        if (civ1.getPeople().size() > ind && civ2.getPeople().size() > ind2) {
            try {
                Person person1 = civ1.getPeople().remove(ind);
                Person person2 = civ2.getPeople().remove(ind2);

                civ1.getPeople().add(person2);
                civ2.getPeople().add(person1);

                if (showLog) {
                    controller.addLogMessage(person1.getName() + " decided to stay with " + civ2.getName());
                    controller.addLogMessage(person2.getName() + " decided to stay with " + civ1.getName());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
