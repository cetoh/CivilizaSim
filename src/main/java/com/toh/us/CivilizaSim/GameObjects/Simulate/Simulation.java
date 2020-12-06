package com.toh.us.CivilizaSim.GameObjects.Simulate;

import com.toh.us.CivilizaSim.Display.PrimaryController;
import com.toh.us.CivilizaSim.GameObjects.Civ.CivActions;
import com.toh.us.CivilizaSim.GameObjects.Civ.CivPayouts;
import com.toh.us.CivilizaSim.GameObjects.Civ.Civilization;
import com.toh.us.CivilizaSim.GameObjects.Civ.Strategy;
import com.toh.us.CivilizaSim.GameObjects.People.Person;
import com.toh.us.CivilizaSim.GameObjects.Resources.Warehouse;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Simulation extends Service<Void> {

    HashMap<Civilization, Integer> score = new HashMap<>();

    //Default 10 rounds
    private int numRounds = 10;

    private List<Civilization> civilizationList;
    private PrimaryController controller;

    public Simulation(PrimaryController controller, List<Civilization> civilizationList) {
        this.controller = controller;
        this.civilizationList = civilizationList;
    }

    public Simulation(int numRounds) {
        this.numRounds  = numRounds;
    }

    public void printScore() {
        for (Civilization civ : score.keySet()) {
            calculateAdditionalPoints(civ);
            controller.addLogMessage(civ.getName() + ": " + score.get(civ));
            controller.addLogMessage("\tCitizens:");
            List<Person> people = civ.getPeople();
            for (Person person : people) {
                controller.addLogMessage("\t\t" + person.getName().toString() + " - " + person.getOriginalCivilization());
            }

            controller.addLogMessage("\tResources:");
            Warehouse warehouse = civ.getWarehouse();
            controller.addLogMessage("Wheat: " + warehouse.getWheat().getAmount());
            controller.addLogMessage("Iron: " + warehouse.getIron().getAmount());
            controller.addLogMessage("Wood: " + warehouse.getWood().getAmount());
            controller.addLogMessage("Clay: " + warehouse.getClay().getAmount());
            controller.addLogMessage("Gold: " + warehouse.getIron().getAmount());
        }
    }

    public void calculateAdditionalPoints(Civilization civ) {
        int additionalPoints = 0;
        //Award points for population (2 per person)
        additionalPoints += calculatePopulationPoints(civ);
        score.put(civ, score.get(civ) + calculatePopulationPoints(civ));

        //Award points for resources
        additionalPoints += calculateResourcePoints(civ);

        //Add to score
        score.put(civ, score.get(civ) + additionalPoints);
    }

    private int calculatePopulationPoints(Civilization civ) {
        return 2 * civ.getPeople().size();
    }

    private int calculateResourcePoints(Civilization civ) {
        int points = 0;
        Warehouse warehouse = civ.getWarehouse();

        // Normal Resources
        points += warehouse.getWheat().getAmount() / 100;
        points += warehouse.getClay().getAmount() / 100;
        points += warehouse.getIron().getAmount() / 100;
        points += warehouse.getWood().getAmount() / 100;

        // Luxury Resources
        points += warehouse.getGold().getAmount() / 10;

        return points;
    }

    public void runSim () {
        runRoundRobin();
        /*
        List<Civilization> opponents = new ArrayList<>(civilizationList);
        for (Civilization civ1 : civilizationList) {
            opponents.remove(civ1);
            for (Civilization civ2 : opponents) {
                controller.addLogMessage(civ1.getName() + " vs. " + civ2.getName() + "\n");
                headToHead(civ1, civ2);
            }

        }
        */
    }

    private void runRoundRobin()
    {
        if (civilizationList.size() % 2 != 0)
        {
            //If odd number create Dummy civ that only produces
            Civilization dummy = new Civilization("Nomads", controller);
            dummy.setStrategy(new Strategy() {
                @Override
                public CivActions executeStrategy(CivPayouts lastPayout) {
                    return CivActions.PRODUCE;
                }
            });
            dummy.getPeople().clear();
            civilizationList.add(dummy); // If odd number of teams add a dummy
        }

        int numRounds = (civilizationList.size() - 1); // Rounds needed to complete tournament
        int halfSize = civilizationList.size() / 2;

        List<Civilization> teams = new ArrayList<>(civilizationList); // Add teams to List and remove the first team
        teams.remove(0);

        int teamsSize = teams.size();

        for (int i = 0; i < numRounds; i++)
        {
            controller.addLogMessage("\n== Round " + (i + 1) + " ==\n");

            int teamIdx = i % teamsSize;

            if (teams.get(teamIdx).getName().equals("Nomads")){
                controller.addLogMessage("\nSome Nomads vs. " + civilizationList.get(0).getName() + "\n");
                teams.get(teamIdx).getPeople().clear();
            } else if (civilizationList.get(0).getName().equals("Nomads")) {
                controller.addLogMessage("\n" + teams.get(teamIdx).getName() + " vs. Some Nomads\n");
                civilizationList.get(0).getPeople().clear();
            } else {
                controller.addLogMessage("\n" + teams.get(teamIdx).getName() + " vs. " + civilizationList.get(0).getName() + "\n");
            }

            headToHead(teams.get(teamIdx), civilizationList.get(0));

            for (int idx = 1; idx < halfSize; idx++)
            {
                int firstTeam = (i + idx) % teamsSize;
                int secondTeam = (i  + teamsSize - idx) % teamsSize;

                if (teams.get(firstTeam).getName().equals("Nomads")){
                    controller.addLogMessage("\nSome Nomads vs. " + teams.get(secondTeam).getName() + "\n");
                    teams.get(firstTeam).getPeople().clear();
                } else if (teams.get(secondTeam).getName().equals("Nomads")) {
                    controller.addLogMessage("\n" + teams.get(firstTeam).getName() + " vs. Some Nomads\n");
                    teams.get(secondTeam).getPeople().clear();
                } else {
                    controller.addLogMessage("\n" + teams.get(firstTeam).getName() + " vs. " + teams.get(secondTeam).getName() + "\n");
                }

                headToHead(teams.get(firstTeam), teams.get(secondTeam));
            }
        }

        //Remove dummy civilization
        civilizationList.removeIf(civ -> civ.getName().equals("Nomads"));
        HashMap<Civilization, Integer> updatedScores = new HashMap<>();
        for (Civilization civ : score.keySet()) {
            if (!civ.getName().equals("Nomads")) {
                updatedScores.put(civ, score.get(civ));
            }
        }
        score = updatedScores;
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

                civ1.trade(civ2, civActions2);
            }
            else if (civActions1.equals(CivActions.TRADE) && civActions2.equals(CivActions.PRODUCE)) {
                lastPayout1 = CivPayouts.MODERATE;
                lastPayout2 = CivPayouts.MODERATE;

                civ2.produce();
                civ1.trade(civ2, civActions2);
            }
            else if (civActions1.equals(CivActions.TRADE) && civActions2.equals(CivActions.ATTACK)) {
                lastPayout1 = CivPayouts.VERY_LOW;
                lastPayout2 = CivPayouts.VERY_HIGH;

                civ2.attack(civ1, false);
            }
            else if (civActions1.equals(CivActions.TRADE) && civActions2.equals(CivActions.DEFEND)) {
                lastPayout1 = CivPayouts.LOW;
                lastPayout2 = CivPayouts.VERY_LOW;

                civ1.trade(civ2, civActions2);
            }
            else if (civActions1.equals(CivActions.TRADE) && civActions2.equals(CivActions.TRAIN)) {
                lastPayout1 = CivPayouts.LOW;
                lastPayout2 = CivPayouts.MODERATE;

                civ2.train();
                civ1.trade(civ2, civActions2);
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

                civ2.trade(civ1, civActions1);
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
                civ2.trade(civ1, civActions1);
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
                civ2.trade(civ1, civActions1);
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

    @Override
    protected Task<Void> createTask() {

        return new Task<>() {
            @Override
            protected Void call() {
                runSim();
                printScore();
                return null;
            }
        };
    }
}
