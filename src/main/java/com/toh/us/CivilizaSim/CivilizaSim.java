package com.toh.us.CivilizaSim;

import com.toh.us.CivilizaSim.GameObjects.Civ.CivActions;
import com.toh.us.CivilizaSim.GameObjects.Civ.CivPayouts;
import com.toh.us.CivilizaSim.GameObjects.Civ.Civilization;
import com.toh.us.CivilizaSim.GameObjects.Civ.Strategy;
import com.toh.us.CivilizaSim.GameObjects.Simulate.Simulation;

import java.util.ArrayList;
import java.util.List;

public class CivilizaSim {

    public static void main(String[] args) {
        List<Civilization> score = new ArrayList<Civilization>();


        Civilization rome = new Civilization("Rome");
        Civilization greece = new Civilization("Greece");
        Civilization huns = new Civilization("Huns");
        Civilization egypt = new Civilization("Egypt");
        Civilization gauls = new Civilization("Gauls");

        score.add(rome);
        score.add(greece);
        score.add(huns);
        score.add(egypt);
        score.add(gauls);

        rome.setStrategy(new Strategy() {
            @Override
            public CivActions executeStrategy(CivPayouts lastPayout) {
                CivActions ourAction;
                switch (lastPayout) {
                    case LOW:
                    case VERY_LOW:
                        ourAction = CivActions.TRAIN;
                        break;
                    case MODERATE:
                        ourAction = CivActions.DEFEND;
                        break;
                    case HIGH:
                        ourAction = CivActions.TRADE;
                        break;
                    case VERY_HIGH:
                        ourAction = CivActions.ATTACK;
                        break;
                    default:
                        ourAction = CivActions.PRODUCE;
                }
                return ourAction;
            }
        });

        greece.setStrategy(new Strategy() {
            @Override
            public CivActions executeStrategy(CivPayouts lastPayout) {
                CivActions ourAction;
                switch (lastPayout) {
                    case LOW:
                    case MODERATE:
                        ourAction = CivActions.DEFEND;
                        break;
                    case VERY_LOW:
                        ourAction = CivActions.ATTACK;
                        break;
                    case HIGH:
                        ourAction = CivActions.TRAIN;
                        break;
                    case VERY_HIGH:
                        ourAction = CivActions.TRADE;
                        break;
                    default:
                        ourAction = CivActions.PRODUCE;
                }
                return ourAction;
            }
        });

        huns.setStrategy(new Strategy() {
            @Override
            public CivActions executeStrategy(CivPayouts lastPayout) {
                return CivActions.ATTACK;
            }
        });

        egypt.setStrategy(new Strategy() {
            @Override
            public CivActions executeStrategy(CivPayouts lastPayout) {
                if (lastPayout == CivPayouts.VERY_LOW) {
                    return CivActions.DEFEND;
                }
                return CivActions.PRODUCE;
            }
        });

        gauls.setStrategy(new Strategy() {
            @Override
            public CivActions executeStrategy(CivPayouts lastPayout) {
                double rand = Math.random();

                if (rand < 0.2) {
                    return CivActions.ATTACK;
                }
                else if (rand >= 0.2  && rand < 0.4) {
                    return CivActions.PRODUCE;
                }
                else if (rand >= 0.4 && rand < 0.6) {
                    return CivActions.TRADE;
                }
                else if (rand >= 0.6 && rand < 0.8)  {
                    return CivActions.DEFEND;
                }
                else {
                    return CivActions.TRAIN;
                }
            }
        });

        Simulation simulation = new Simulation();
        simulation.runSim(score);

        simulation.printScore();
    }


}
