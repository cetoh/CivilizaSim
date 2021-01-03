package com.toh.us.CivilizaSim.GameObjects.Simulate;

import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Civ.*;
import com.toh.us.CivilizaSim.GameObjects.Resources.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class GenerateCivilizations {

    private List<Civilization> civilizations = new ArrayList<>();

    public GenerateCivilizations() {

        Civilization rome = new Civilization("Rome");
        Civilization greece = new Civilization("Greece");
        Civilization huns = new Civilization("Huns");
        Civilization egypt = new Civilization("Egypt");
        Civilization gauls = new Civilization("Gauls");
        Civilization babylon = new Civilization("Babylon");
        Civilization china = new Civilization("China");

        civilizations.add(rome);
        civilizations.add(greece);
        civilizations.add(huns);
        civilizations.add(egypt);
        civilizations.add(gauls);
        civilizations.add(babylon);
        civilizations.add(china);

        rome.setStrategy(new Strategy() {
            @Override
            public CivAction executeStrategy(CivPayouts lastPayout) {
                CivAction ourAction = new CivAction();
                switch (lastPayout) {
                    case LOW:
                    case MODERATE:
                        if (rome.getWarehouse().getIron().getAmount() >= 250
                                && rome.getWarehouse().getWood().getAmount() >= 250
                                && rome.getWarehouse().getWheat().getAmount() >= 250
                                && rome.getWarehouse().getGold().getAmount() >= 50) {
                            ourAction.setAction(CivActions.BUILD);

                            switch (MathUtils.getRandomNumber(0, 8)) {
                                case 0 -> ourAction.setBuildingName(BuildingName.AQUEDUCT);
                                case 1 -> ourAction.setBuildingName(BuildingName.WALLS);
                                case 2 -> ourAction.setBuildingName(BuildingName.BARRACKS);
                                case 3 -> ourAction.setBuildingName(BuildingName.MARKETPLACE);
                                case 4 -> ourAction.setBuildingName(BuildingName.FLOUR_MILL);
                                case 5 -> ourAction.setBuildingName(BuildingName.LUMBER_MILL);
                                case 6 -> ourAction.setBuildingName(BuildingName.MINE);
                                case 7 -> ourAction.setBuildingName(BuildingName.KILN);
                                case 8 -> ourAction.setBuildingName(BuildingName.BANK);
                            }
                        } else {
                            ourAction.setAction(CivActions.PRODUCE);
                        }
                        break;
                    case VERY_LOW:
                        if (rome.getSoldiers().size() < rome.getPeople().size()
                                && rome.getWarehouse().getIron().getAmount() >= 25
                                && rome.getWarehouse().getWood().getAmount() >= 20
                                && rome.getWarehouse().getWheat().getAmount() >= 20
                                && rome.getWarehouse().getGold().getAmount() >= 5) {
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
                        ourAction.setAction(CivActions.DEFEND);
                        break;
                    case MODERATE:
                        ourAction.setAction(CivActions.BUILD);
                        switch (MathUtils.getRandomNumber(0, 8)) {
                            case 0 -> ourAction.setBuildingName(BuildingName.AQUEDUCT);
                            case 1 -> ourAction.setBuildingName(BuildingName.WALLS);
                            case 2 -> ourAction.setBuildingName(BuildingName.BARRACKS);
                            case 3 -> ourAction.setBuildingName(BuildingName.MARKETPLACE);
                            case 4 -> ourAction.setBuildingName(BuildingName.FLOUR_MILL);
                            case 5 -> ourAction.setBuildingName(BuildingName.LUMBER_MILL);
                            case 6 -> ourAction.setBuildingName(BuildingName.MINE);
                            case 7 -> ourAction.setBuildingName(BuildingName.KILN);
                            case 8 -> ourAction.setBuildingName(BuildingName.BANK);
                        }
                        break;
                    case VERY_LOW:
                        ourAction.setAction(CivActions.ATTACK);
                        break;
                    case HIGH:
                        if (greece.getSoldiers().size() < greece.getPeople().size()
                                && greece.getWarehouse().getIron().getAmount() >= 25
                                && greece.getWarehouse().getWood().getAmount() >= 20
                                && greece.getWarehouse().getWheat().getAmount() >= 20
                                && greece.getWarehouse().getGold().getAmount() >= 5) {
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
                        && warehouse.getClay().getAmount() > 100
                        && warehouse.getWood().getAmount() > 100
                        && warehouse.getIron().getAmount() > 100
                        && warehouse.getWheat().getAmount() > 100) {
                    switch (MathUtils.getRandomNumber(0, 8)) {
                        case 0 -> ourAction.setBuildingName(BuildingName.AQUEDUCT);
                        case 1 -> ourAction.setBuildingName(BuildingName.WALLS);
                        case 2 -> ourAction.setBuildingName(BuildingName.BARRACKS);
                        case 3 -> ourAction.setBuildingName(BuildingName.MARKETPLACE);
                        case 4 -> ourAction.setBuildingName(BuildingName.FLOUR_MILL);
                        case 5 -> ourAction.setBuildingName(BuildingName.LUMBER_MILL);
                        case 6 -> ourAction.setBuildingName(BuildingName.MINE);
                        case 7 -> ourAction.setBuildingName(BuildingName.KILN);
                        case 8 -> ourAction.setBuildingName(BuildingName.BANK);
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
                    switch (MathUtils.getRandomNumber(0, 8)) {
                        case 0 -> ourAction.setBuildingName(BuildingName.AQUEDUCT);
                        case 1 -> ourAction.setBuildingName(BuildingName.WALLS);
                        case 2 -> ourAction.setBuildingName(BuildingName.BARRACKS);
                        case 3 -> ourAction.setBuildingName(BuildingName.MARKETPLACE);
                        case 4 -> ourAction.setBuildingName(BuildingName.FLOUR_MILL);
                        case 5 -> ourAction.setBuildingName(BuildingName.LUMBER_MILL);
                        case 6 -> ourAction.setBuildingName(BuildingName.MINE);
                        case 7 -> ourAction.setBuildingName(BuildingName.KILN);
                        case 8 -> ourAction.setBuildingName(BuildingName.BANK);
                    }
                }

                return ourAction;
            }
        });

        babylon.setStrategy(new Strategy() {
            @Override
            public CivAction executeStrategy(CivPayouts lastPayout) {
                CivAction ourAction = new CivAction(CivActions.PRODUCE);
                if (lastPayout.equals(CivPayouts.NONE)) {
                    return ourAction;
                }
                Warehouse warehouse = babylon.getWarehouse();
                if (warehouse.getWheat().getAmount() >= 75
                        && warehouse.getIron().getAmount() >= 125
                        && warehouse.getWood().getAmount() >= 135
                        && warehouse.getClay().getAmount() >= 160
                        && warehouse.getGold().getAmount() >= 15) {
                    ourAction.setAction(CivActions.BUILD);
                    ourAction.setBuildingName(BuildingName.AQUEDUCT);
                    return ourAction;
                } else if (warehouse.getWheat().getAmount() >= 125
                        && warehouse.getIron().getAmount() >= 75
                        && warehouse.getWood().getAmount() >= 100
                        && warehouse.getClay().getAmount() >= 150
                        && warehouse.getGold().getAmount() >= 25) {
                    ourAction.setAction(CivActions.BUILD);
                    ourAction.setBuildingName(BuildingName.MARKETPLACE);
                    return ourAction;
                } else if (warehouse.getWheat().getAmount() >= 50
                        && warehouse.getIron().getAmount() >= 150
                        && warehouse.getWood().getAmount() >= 100
                        && warehouse.getClay().getAmount() >= 125) {
                    ourAction.setAction(CivActions.BUILD);
                    ourAction.setBuildingName(BuildingName.BARRACKS);
                    return ourAction;
                } else if (warehouse.getWheat().getAmount() >= 100
                        && warehouse.getIron().getAmount() >= 175
                        && warehouse.getWood().getAmount() >= 175
                        && warehouse.getClay().getAmount() >= 200
                        && warehouse.getGold().getAmount() >= 10) {
                    ourAction.setAction(CivActions.BUILD);
                    ourAction.setBuildingName(BuildingName.WALLS);
                    return ourAction;
                } else if (lastPayout.equals(CivPayouts.VERY_LOW)) {
                    ourAction.setAction(CivActions.DEFEND);
                } else if (lastPayout.equals(CivPayouts.LOW)) {
                    ourAction.setAction(CivActions.TRAIN);
                } else if (babylon.getSoldiers().size() > 1) {
                    ourAction.setAction(CivActions.ATTACK);
                } else if (lastPayout.equals(CivPayouts.MODERATE)) {
                    ourAction.setAction(CivActions.TRADE);
                }

                return ourAction;
            }
        });

        china.setStrategy(new Strategy() {
            private int num = 0;

            @Override
            public CivAction executeStrategy(CivPayouts lastPayout) {
                CivAction ourAction = new CivAction(CivActions.PRODUCE);

                switch (num) {
                    case 0, 1, 5, 6, 8, 9, 11 -> num++;
                    case 2 -> {
                        ourAction.setAction(CivActions.TRADE);
                        num++;
                    }
                    case 3 -> {
                        ourAction.setAction(CivActions.BUILD);
                        ourAction.setBuildingName(BuildingName.AQUEDUCT);
                        num++;
                    }
                    case 4, 12, 13 -> {
                        ourAction.setAction(CivActions.TRAIN);
                        num++;
                    }
                    case 7 -> {
                        ourAction.setAction(CivActions.BUILD);
                        ourAction.setBuildingName(BuildingName.BARRACKS);
                        num++;
                    }
                    case 10 -> {
                        ourAction.setAction(CivActions.BUILD);
                        ourAction.setBuildingName(BuildingName.WALLS);
                        num++;
                    }
                    case 14 -> {
                        ourAction.setAction(CivActions.ATTACK);
                        num++;
                    }
                    case 15 -> {
                        ourAction.setAction(CivActions.DEFEND);
                        num++;
                    }
                    default -> {
                        Warehouse warehouse = china.getWarehouse();
                        if (warehouse.getWheat().getAmount() >= 75
                                && warehouse.getIron().getAmount() >= 125
                                && warehouse.getWood().getAmount() >= 135
                                && warehouse.getClay().getAmount() >= 160
                                && warehouse.getGold().getAmount() >= 15) {
                            ourAction.setAction(CivActions.BUILD);
                            ourAction.setBuildingName(BuildingName.AQUEDUCT);
                            return ourAction;
                        } else if (warehouse.getWheat().getAmount() >= 125
                                && warehouse.getIron().getAmount() >= 75
                                && warehouse.getWood().getAmount() >= 100
                                && warehouse.getClay().getAmount() >= 150
                                && warehouse.getGold().getAmount() >= 25) {
                            ourAction.setAction(CivActions.BUILD);
                            ourAction.setBuildingName(BuildingName.MARKETPLACE);
                            return ourAction;
                        } else if (warehouse.getWheat().getAmount() >= 50
                                && warehouse.getIron().getAmount() >= 150
                                && warehouse.getWood().getAmount() >= 100
                                && warehouse.getClay().getAmount() >= 125) {
                            ourAction.setAction(CivActions.BUILD);
                            ourAction.setBuildingName(BuildingName.BARRACKS);
                            return ourAction;
                        } else if (warehouse.getWheat().getAmount() >= 100
                                && warehouse.getIron().getAmount() >= 175
                                && warehouse.getWood().getAmount() >= 175
                                && warehouse.getClay().getAmount() >= 200
                                && warehouse.getGold().getAmount() >= 10) {
                            ourAction.setAction(CivActions.BUILD);
                            ourAction.setBuildingName(BuildingName.WALLS);
                            return ourAction;
                        } else if (lastPayout.equals(CivPayouts.VERY_LOW)) {
                            ourAction.setAction(CivActions.DEFEND);
                        } else if (lastPayout.equals(CivPayouts.LOW)) {
                            ourAction.setAction(CivActions.TRAIN);
                        } else if (china.getSoldiers().size() > 1) {
                            ourAction.setAction(CivActions.ATTACK);
                        } else if (lastPayout.equals(CivPayouts.MODERATE)) {
                            ourAction.setAction(CivActions.TRADE);
                        }
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
