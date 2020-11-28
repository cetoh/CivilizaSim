package com.toh.us.CivilizaSim.GameObjects.Simulate;

import com.toh.us.CivilizaSim.GameObjects.Civ.CivActions;
import com.toh.us.CivilizaSim.GameObjects.Civ.CivPayouts;
import com.toh.us.CivilizaSim.GameObjects.Civ.Civilization;

import java.util.HashMap;
import java.util.List;

public class Simulation {

    HashMap<Civilization, Integer> score = new HashMap<Civilization, Integer>();

    //Default 100 rounds
    private int numRounds = 100;

    public Simulation() {

    }

    public Simulation(int numRounds) {
        this.numRounds  = numRounds;
    }

    public void printScore() {
        for (Civilization civ : score.keySet()) {
            System.out.println(civ.getName() + ": " + score.get(civ));
        }

    }

    public void runSim (List<Civilization> civilizationList) {
        for (int i = 0; i < civilizationList.size(); i++) {
            Civilization civ1 = civilizationList.get(i);
            Civilization civ2;
            if (i < civilizationList.size() - 1) {
                civ2 = civilizationList.get(i + 1);
            }
            else {
                civ2 = civilizationList.get(0);
            }
            headToHead(civ1, civ2);
        }
    }

    public void headToHead(Civilization civ1, Civilization civ2) {
        CivPayouts lastPayout1 = CivPayouts.NONE;
        CivPayouts lastPayout2 = CivPayouts.NONE;
        for (int i = 0; i < numRounds; i++) {
            CivActions civActions1 = civ1.getStrategy().executeStrategy(lastPayout1);
            CivActions civActions2 = civ2.getStrategy().executeStrategy(lastPayout2);

            if (civActions1.equals(CivActions.ATTACK) && civActions2.equals(CivActions.ATTACK)) {
                lastPayout1 = CivPayouts.VERY_LOW;
                lastPayout2 = CivPayouts.VERY_LOW;
            }
            else if (civActions1.equals(CivActions.ATTACK) && civActions2.equals(CivActions.DEFEND)) {
                lastPayout1 = CivPayouts.LOW;
                lastPayout2 = CivPayouts.MODERATE;
            }
            else if (civActions1.equals(CivActions.ATTACK) &&
                    (civActions2.equals(CivActions.PRODUCE) || civActions2.equals(CivActions.TRADE) || civActions2.equals(CivActions.TRAIN))) {
                lastPayout1 = CivPayouts.VERY_HIGH;
                lastPayout2 = CivPayouts.VERY_LOW;
            }
            else if (civActions1.equals(CivActions.TRADE) && civActions2.equals(CivActions.TRADE)) {
                lastPayout1 = CivPayouts.VERY_HIGH;
                lastPayout2 = CivPayouts.VERY_HIGH;
            }
            else if (civActions1.equals(CivActions.TRADE) && civActions2.equals(CivActions.PRODUCE)) {
                lastPayout1 = CivPayouts.MODERATE;
                lastPayout2 = CivPayouts.MODERATE;
            }
            else if (civActions1.equals(CivActions.TRADE) && civActions2.equals(CivActions.ATTACK)) {
                lastPayout1 = CivPayouts.VERY_LOW;
                lastPayout2 = CivPayouts.VERY_HIGH;
            }
            else if (civActions1.equals(CivActions.TRADE) && civActions2.equals(CivActions.DEFEND)) {
                lastPayout1 = CivPayouts.LOW;
                lastPayout2 = CivPayouts.VERY_LOW;
            }
            else if (civActions1.equals(CivActions.TRADE) && civActions2.equals(CivActions.TRAIN)) {
                lastPayout1 = CivPayouts.LOW;
                lastPayout2 = CivPayouts.MODERATE;
            }
            else if (civActions1.equals(CivActions.PRODUCE) && civActions2.equals(CivActions.PRODUCE)) {
                lastPayout1 = CivPayouts.HIGH;
                lastPayout2 = CivPayouts.HIGH;
            }
            else if (civActions1.equals(CivActions.PRODUCE) && civActions2.equals(CivActions.ATTACK)) {
                lastPayout1 = CivPayouts.VERY_LOW;
                lastPayout2 = CivPayouts.VERY_HIGH;
            }
            else if (civActions1.equals(CivActions.PRODUCE) && civActions2.equals(CivActions.TRADE)) {
                lastPayout1 = CivPayouts.MODERATE;
                lastPayout2 = CivPayouts.MODERATE;
            }
            else if (civActions1.equals(CivActions.PRODUCE) && civActions2.equals(CivActions.TRAIN)) {
                lastPayout1 = CivPayouts.HIGH;
                lastPayout2 = CivPayouts.LOW;
            }
            else if (civActions1.equals(CivActions.PRODUCE) && civActions2.equals(CivActions.DEFEND)) {
                lastPayout1 = CivPayouts.HIGH;
                lastPayout2 = CivPayouts.VERY_LOW;
            }
            else if (civActions1.equals(CivActions.TRAIN) && civActions2.equals(CivActions.TRAIN)) {
                lastPayout1 = CivPayouts.LOW;
                lastPayout2 = CivPayouts.LOW;
            }
            else if (civActions1.equals(CivActions.TRAIN) && civActions2.equals(CivActions.TRADE)) {
                lastPayout1 = CivPayouts.MODERATE;
                lastPayout2 = CivPayouts.LOW;
            }
            else if (civActions1.equals(CivActions.TRAIN) && civActions2.equals(CivActions.PRODUCE)) {
                lastPayout1 = CivPayouts.LOW;
                lastPayout2 = CivPayouts.HIGH;
            }
            else if (civActions1.equals(CivActions.TRAIN) && civActions2.equals(CivActions.ATTACK)) {
                lastPayout1 = CivPayouts.VERY_LOW;
                lastPayout2 = CivPayouts.HIGH;
            }
            else if (civActions1.equals(CivActions.TRAIN) && civActions2.equals(CivActions.DEFEND)) {
                lastPayout1 = CivPayouts.MODERATE;
                lastPayout2 = CivPayouts.LOW;
            }
            else if (civActions1.equals(CivActions.DEFEND) && civActions2.equals(CivActions.ATTACK)) {
                lastPayout1 = CivPayouts.MODERATE;
                lastPayout2 = CivPayouts.LOW;
            }
            else if (civActions1.equals(CivActions.DEFEND) && civActions2.equals(CivActions.DEFEND)) {
                lastPayout1 = CivPayouts.LOW;
                lastPayout2 = CivPayouts.LOW;
            }
            else if (civActions1.equals(CivActions.DEFEND) && civActions2.equals(CivActions.PRODUCE)) {
                lastPayout1 = CivPayouts.VERY_LOW;
                lastPayout2 = CivPayouts.HIGH;
            }
            else if (civActions1.equals(CivActions.DEFEND) && civActions2.equals(CivActions.TRADE)) {
                lastPayout1 = CivPayouts.VERY_LOW;
                lastPayout2 = CivPayouts.LOW;
            }
            else if (civActions1.equals(CivActions.DEFEND) && civActions2.equals(CivActions.TRAIN)) {
                lastPayout1 = CivPayouts.LOW;
                lastPayout2 = CivPayouts.MODERATE;
            }


            scorePayouts(civ1, lastPayout1);
            scorePayouts(civ2, lastPayout2);
        }
    }

    private void scorePayouts(Civilization civ, CivPayouts payouts) {
        switch (payouts) {
            case VERY_HIGH:
                if (score.containsKey(civ)) {
                    score.put(civ, score.get(civ) + 5);
                } else {
                    score.put(civ, 5);
                }
                break;
            case HIGH:
                if (score.containsKey(civ)) {
                    score.put(civ, score.get(civ) + 4);
                } else {
                    score.put(civ, 4);
                }
                break;
            case MODERATE:
                if (score.containsKey(civ)) {
                    score.put(civ, score.get(civ) + 3);
                } else {
                    score.put(civ, 3);
                }
                break;
            case LOW:
                if (score.containsKey(civ)) {
                    score.put(civ, score.get(civ) + 2);
                } else {
                    score.put(civ, 2);
                }
                break;
            case VERY_LOW:
                if (score.containsKey(civ)) {
                    score.put(civ, score.get(civ) + 1);
                } else {
                    score.put(civ, 1);
                }
                break;
            case NONE:
                if (score.containsKey(civ)) {
                    score.put(civ, score.get(civ));
                } else {
                    score.put(civ, 0);
                }
                break;
        }
    }
}
