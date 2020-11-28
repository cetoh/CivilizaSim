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

        score.add(rome);
        score.add(greece);

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

        Simulation simulation = new Simulation();
        simulation.runSim(score);

        simulation.printScore();
    }


}
