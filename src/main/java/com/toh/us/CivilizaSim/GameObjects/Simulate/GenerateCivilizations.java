package com.toh.us.CivilizaSim.GameObjects.Simulate;

import com.toh.us.CivilizaSim.Display.PrimaryController;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Civ.*;
import com.toh.us.CivilizaSim.GameObjects.Resources.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class GenerateCivilizations {

    private List<Civilization> civilizations = new ArrayList<>();

    public GenerateCivilizations(PrimaryController controller) {

        Civilization rome = new Civilization("Rome", controller);
        Civilization greece = new Civilization("Greece", controller);
        Civilization huns = new Civilization("Huns", controller);
        Civilization egypt = new Civilization("Egypt", controller);
        Civilization gauls = new Civilization("Gauls", controller);

        civilizations.add(rome);
        civilizations.add(greece);
        civilizations.add(huns);
        civilizations.add(egypt);
        civilizations.add(gauls);

        rome.setStrategy(new Strategy() {
            @Override
            public CivAction executeStrategy(CivPayouts lastPayout) {
                CivAction ourAction = new CivAction();
                switch (lastPayout) {
                    case LOW:
                    case MODERATE:
                        if (huns.getWarehouse().getIron().getAmount() >= 250
                                && huns.getWarehouse().getWood().getAmount() >= 250
                                && huns.getWarehouse().getWheat().getAmount() >= 250
                                && huns.getWarehouse().getGold().getAmount() >= 50) {
                            ourAction.setAction(CivActions.BUILD);

                            switch (MathUtils.getRandomNumber(0, 3)) {
                                case 0 -> ourAction.setBuildingName(BuildingName.AQUEDUCT);
                                case 1 -> ourAction.setBuildingName(BuildingName.WALLS);
                                case 2 -> ourAction.setBuildingName(BuildingName.BARRACKS);
                                case 3 -> ourAction.setBuildingName(BuildingName.MARKETPLACE);
                            }
                        } else {
                            ourAction.setAction(CivActions.PRODUCE);
                        }
                        break;
                    case VERY_LOW:
                        if (huns.getSoldiers().size() < huns.getPeople().size()
                                && huns.getWarehouse().getIron().getAmount() >= 25
                                && huns.getWarehouse().getWood().getAmount() >= 20
                                && huns.getWarehouse().getWheat().getAmount() >= 20
                                && huns.getWarehouse().getGold().getAmount() >= 5) {
                            ourAction.setAction(CivActions.TRAIN);
                        }
                        else {
                            ourAction.setAction(CivActions.PRODUCE);
                        }
                        break;
                    case HIGH:
                        ourAction.setAction(CivActions.TRADE);
                        break;
                    case VERY_HIGH:
                        ourAction.setAction(CivActions.ATTACK);
                        break;
                    default:
                        ourAction.setAction(CivActions.PRODUCE);
                }
                return ourAction;
            }
        });

        greece.setStrategy(new Strategy() {
            @Override
            public CivAction executeStrategy(CivPayouts lastPayout) {
                CivAction ourAction = new CivAction();
                switch (lastPayout) {
                    case LOW:
                    case MODERATE:
                        ourAction.setAction(CivActions.DEFEND);
                        break;
                    case VERY_LOW:
                        ourAction.setAction(CivActions.ATTACK);
                        break;
                    case HIGH:
                        if (huns.getSoldiers().size() < huns.getPeople().size()
                                && huns.getWarehouse().getIron().getAmount() >= 25
                                && huns.getWarehouse().getWood().getAmount() >= 20
                                && huns.getWarehouse().getWheat().getAmount() >= 20
                                && huns.getWarehouse().getGold().getAmount() >= 5) {
                            ourAction.setAction(CivActions.TRAIN);
                        }
                        else {
                            ourAction.setAction(CivActions.PRODUCE);
                        }
                        break;
                    case VERY_HIGH:
                        ourAction.setAction(CivActions.TRADE);
                        break;
                    default:
                        ourAction.setAction(CivActions.PRODUCE);
                }
                return ourAction;
            }
        });

        huns.setStrategy(new Strategy() {
            @Override
            public CivAction executeStrategy(CivPayouts lastPayout) {
                CivAction ourAction = new CivAction();
                if (lastPayout.equals(CivPayouts.NONE)) {
                    ourAction.setAction(CivActions.PRODUCE);
                }

                if (huns.getWarehouse().getIron().getAmount() >= 250
                        && huns.getWarehouse().getWood().getAmount() >= 250
                        && huns.getWarehouse().getWheat().getAmount() >= 250
                        && huns.getWarehouse().getGold().getAmount() >= 50){
                    ourAction.setAction(CivActions.BUILD);
                    ourAction.setBuildingName(BuildingName.BARRACKS);
                }
                else if (huns.getSoldiers().size() < huns.getPeople().size()
                        && huns.getWarehouse().getIron().getAmount() >= 25
                        && huns.getWarehouse().getWood().getAmount() >= 20
                        && huns.getWarehouse().getWheat().getAmount() >= 20
                        && huns.getWarehouse().getGold().getAmount() >= 5) {
                    ourAction.setAction(CivActions.TRAIN);
                } else {
                    ourAction.setAction(CivActions.ATTACK);
                }

                return ourAction;
            }
        });

        egypt.setStrategy(new Strategy() {
            @Override
            public CivAction executeStrategy(CivPayouts lastPayout) {
                CivAction ourAction = new CivAction();
                Warehouse warehouse = egypt.getWarehouse();
                if (lastPayout.equals(CivPayouts.VERY_LOW)) {
                    ourAction.setAction(CivActions.DEFEND);
                }
                else if (lastPayout.equals(CivPayouts.NONE)) {
                    ourAction.setAction(CivActions.PRODUCE);
                }
                else if (lastPayout.equals(CivPayouts.HIGH)){
                    ourAction.setAction(CivActions.TRADE);
                }
                else if (lastPayout.equals(CivPayouts.MODERATE) && warehouse.getGold().getAmount() > 20
                        && warehouse.getClay().getAmount() > 200
                        && warehouse.getWood().getAmount() > 200
                        && warehouse.getIron().getAmount() > 200
                        && warehouse.getWheat().getAmount() > 200) {
                    switch (MathUtils.getRandomNumber(0, 3)) {
                        case 0 -> ourAction.setBuildingName(BuildingName.AQUEDUCT);
                        case 1 -> ourAction.setBuildingName(BuildingName.WALLS);
                        case 2 -> ourAction.setBuildingName(BuildingName.BARRACKS);
                        case 3 -> ourAction.setBuildingName(BuildingName.MARKETPLACE);
                    }
                } else {
                    ourAction.setAction(CivActions.PRODUCE);
                }

                return ourAction;
            }
        });

        gauls.setStrategy(new Strategy() {
            @Override
            public CivAction executeStrategy(CivPayouts lastPayout) {
                double rand = Math.random();
                CivAction ourAction = new CivAction();
                if (rand < 0.166) {
                    ourAction.setAction(CivActions.ATTACK);
                }
                else if (rand >= 0.166  && rand < 0.333) {
                    ourAction.setAction(CivActions.PRODUCE);
                }
                else if (rand >= 0.333 && rand < 0.499) {
                    ourAction.setAction(CivActions.TRADE);
                }
                else if (rand >= 0.499 && rand < 0.666)  {
                    ourAction.setAction(CivActions.DEFEND);
                }
                else if (rand >= 0.666 && rand < 0.833){
                    ourAction.setAction(CivActions.TRAIN);
                }
                else {
                    ourAction.setAction(CivActions.BUILD);
                    switch (MathUtils.getRandomNumber(0, 3)) {
                        case 0 -> ourAction.setBuildingName(BuildingName.AQUEDUCT);
                        case 1 -> ourAction.setBuildingName(BuildingName.WALLS);
                        case 2 -> ourAction.setBuildingName(BuildingName.BARRACKS);
                        case 3 -> ourAction.setBuildingName(BuildingName.MARKETPLACE);
                    }
                }

                return ourAction;
            }
        });
    }

    public List<Civilization> getCivilizations() {
        return civilizations;
    }
}
