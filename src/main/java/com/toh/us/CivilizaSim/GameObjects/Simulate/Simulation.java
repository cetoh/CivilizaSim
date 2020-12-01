package com.toh.us.CivilizaSim.GameObjects.Simulate;

import com.toh.us.CivilizaSim.GameObjects.Civ.CivActions;
import com.toh.us.CivilizaSim.GameObjects.Civ.CivPayouts;
import com.toh.us.CivilizaSim.GameObjects.Civ.Civilization;
import com.toh.us.CivilizaSim.GameObjects.People.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Simulation {

    HashMap<Civilization, Integer> score = new HashMap<Civilization, Integer>();

    //Default 10 rounds
    private int numRounds = 10;

    public Simulation() {

    }

    public Simulation(int numRounds) {
        this.numRounds  = numRounds;
    }

    public void printScore() {
        for (Civilization civ : score.keySet()) {
            calculateAdditionalPoints(civ);
            System.out.println(civ.getName() + ": " + score.get(civ));
            System.out.println("\tCitizens:");
            List<Person> people = civ.getPeople();
            for (Person person : people) {
                System.out.println("\t\t" + person.getName().toString() + " - " + person.getOriginalCivilization());
            }
        }
    }

    public void calculateAdditionalPoints(Civilization civ) {
        //Award points for population (2 per person)
        score.put(civ, score.get(civ) + (2 * civ.getPeople().size()));
    }

    public void runSim (List<Civilization> civilizationList) {
        List<Civilization> opponents = new ArrayList<>(civilizationList);
        for (Civilization civ1 : civilizationList) {
            opponents.remove(civ1);
            for (Civilization civ2 : opponents) {
                System.out.println(civ1.getName() + " vs. " + civ2.getName());
                headToHead(civ1, civ2);
            }

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

                civ1.battle(civ2);
            }
            else if (civActions1.equals(CivActions.ATTACK) && civActions2.equals(CivActions.DEFEND)) {
                lastPayout1 = CivPayouts.LOW;
                lastPayout2 = CivPayouts.MODERATE;

                civ1.attack(civ2, true);
            }
            else if (civActions1.equals(CivActions.ATTACK) &&
                    (civActions2.equals(CivActions.PRODUCE) || civActions2.equals(CivActions.TRADE) || civActions2.equals(CivActions.TRAIN))) {
                lastPayout1 = CivPayouts.VERY_HIGH;
                lastPayout2 = CivPayouts.VERY_LOW;

                civ1.attack(civ2, false);
            }
            else if (civActions1.equals(CivActions.TRADE) && civActions2.equals(CivActions.TRADE)) {
                lastPayout1 = CivPayouts.VERY_HIGH;
                lastPayout2 = CivPayouts.VERY_HIGH;
            }
            else if (civActions1.equals(CivActions.TRADE) && civActions2.equals(CivActions.PRODUCE)) {
                lastPayout1 = CivPayouts.MODERATE;
                lastPayout2 = CivPayouts.MODERATE;

                civ2.produce();
            }
            else if (civActions1.equals(CivActions.TRADE) && civActions2.equals(CivActions.ATTACK)) {
                lastPayout1 = CivPayouts.VERY_LOW;
                lastPayout2 = CivPayouts.VERY_HIGH;

                civ2.attack(civ1, false);
            }
            else if (civActions1.equals(CivActions.TRADE) && civActions2.equals(CivActions.DEFEND)) {
                lastPayout1 = CivPayouts.LOW;
                lastPayout2 = CivPayouts.VERY_LOW;
            }
            else if (civActions1.equals(CivActions.TRADE) && civActions2.equals(CivActions.TRAIN)) {
                lastPayout1 = CivPayouts.LOW;
                lastPayout2 = CivPayouts.MODERATE;

                civ2.train();
            }
            else if (civActions1.equals(CivActions.PRODUCE) && civActions2.equals(CivActions.PRODUCE)) {
                lastPayout1 = CivPayouts.HIGH;
                lastPayout2 = CivPayouts.HIGH;

                //Both civs produced
                civ1.produce();
                civ2.produce();
            }
            else if (civActions1.equals(CivActions.PRODUCE) && civActions2.equals(CivActions.ATTACK)) {
                lastPayout1 = CivPayouts.VERY_LOW;
                lastPayout2 = CivPayouts.VERY_HIGH;

                civ2.attack(civ1, false);
            }
            else if (civActions1.equals(CivActions.PRODUCE) && civActions2.equals(CivActions.TRADE)) {
                lastPayout1 = CivPayouts.MODERATE;
                lastPayout2 = CivPayouts.MODERATE;
            }
            else if (civActions1.equals(CivActions.PRODUCE) && civActions2.equals(CivActions.TRAIN)) {
                lastPayout1 = CivPayouts.HIGH;
                lastPayout2 = CivPayouts.LOW;

                civ1.produce();
                civ2.train();
            }
            else if (civActions1.equals(CivActions.PRODUCE) && civActions2.equals(CivActions.DEFEND)) {
                lastPayout1 = CivPayouts.HIGH;
                lastPayout2 = CivPayouts.VERY_LOW;

                civ1.produce();
            }
            else if (civActions1.equals(CivActions.TRAIN) && civActions2.equals(CivActions.TRAIN)) {
                lastPayout1 = CivPayouts.LOW;
                lastPayout2 = CivPayouts.LOW;

                //Both trained soldiers
                civ1.train();
                civ2.train();
            }
            else if (civActions1.equals(CivActions.TRAIN) && civActions2.equals(CivActions.TRADE)) {
                lastPayout1 = CivPayouts.MODERATE;
                lastPayout2 = CivPayouts.LOW;

                civ1.train();
            }
            else if (civActions1.equals(CivActions.TRAIN) && civActions2.equals(CivActions.PRODUCE)) {
                lastPayout1 = CivPayouts.LOW;
                lastPayout2 = CivPayouts.HIGH;

                civ1.train();
                civ2.produce();
            }
            else if (civActions1.equals(CivActions.TRAIN) && civActions2.equals(CivActions.ATTACK)) {
                lastPayout1 = CivPayouts.VERY_LOW;
                lastPayout2 = CivPayouts.HIGH;

                civ2.attack(civ1, false);
            }
            else if (civActions1.equals(CivActions.TRAIN) && civActions2.equals(CivActions.DEFEND)) {
                lastPayout1 = CivPayouts.MODERATE;
                lastPayout2 = CivPayouts.LOW;

                civ1.train();
            }
            else if (civActions1.equals(CivActions.DEFEND) && civActions2.equals(CivActions.ATTACK)) {
                lastPayout1 = CivPayouts.MODERATE;
                lastPayout2 = CivPayouts.LOW;

                civ2.attack(civ1, true);
            }
            else if (civActions1.equals(CivActions.DEFEND) && civActions2.equals(CivActions.DEFEND)) {
                lastPayout1 = CivPayouts.LOW;
                lastPayout2 = CivPayouts.LOW;
            }
            else if (civActions1.equals(CivActions.DEFEND) && civActions2.equals(CivActions.PRODUCE)) {
                lastPayout1 = CivPayouts.VERY_LOW;
                lastPayout2 = CivPayouts.HIGH;

                civ2.produce();
            }
            else if (civActions1.equals(CivActions.DEFEND) && civActions2.equals(CivActions.TRADE)) {
                lastPayout1 = CivPayouts.VERY_LOW;
                lastPayout2 = CivPayouts.LOW;
            }
            else if (civActions1.equals(CivActions.DEFEND) && civActions2.equals(CivActions.TRAIN)) {
                lastPayout1 = CivPayouts.LOW;
                lastPayout2 = CivPayouts.MODERATE;

                civ2.train();
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
                score.put(civ, score.getOrDefault(civ, 0));
                break;
        }
    }
}
