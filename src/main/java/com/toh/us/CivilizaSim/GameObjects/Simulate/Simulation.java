package com.toh.us.CivilizaSim.GameObjects.Simulate;

import com.toh.us.CivilizaSim.Display.PrimaryController;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Civ.*;
import com.toh.us.CivilizaSim.GameObjects.Civ.Interact.CivInteraction;
import com.toh.us.CivilizaSim.GameObjects.People.Civilian;
import com.toh.us.CivilizaSim.GameObjects.People.Person;
import com.toh.us.CivilizaSim.GameObjects.People.Scholar;
import com.toh.us.CivilizaSim.GameObjects.People.Soldier;
import com.toh.us.CivilizaSim.GameObjects.Resources.Warehouse;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Simulation extends Service<Void> {

    HashMap<Civilization, Integer> score = new HashMap<>();

    //Default 3 rounds
    private int numRounds = 3;

    private List<Civilization> civilizationList;
    private PrimaryController controller;

    private CivAction playerAction;

    private Civilization player;

    private Civilization nextOpposingCiv;

    public Simulation(PrimaryController controller, List<Civilization> civilizationList, Civilization player) {
        this.controller = controller;
        this.civilizationList = civilizationList;
        this.player = player;

        this.setOnSucceeded(workerStateEvent -> {
            this.reset();
        });

        this.setOnFailed(workerStateEvent -> {
            this.reset();
        });

        if (this.civilizationList.size() % 2 != 0)
        {
            //If odd number create Dummy civ that only produces
            Civilization dummy = new Civilization("Nomads");
            dummy.setStrategy(new Strategy() {
                @Override
                public CivAction executeStrategy(CivPayouts lastPayout) {
                    return new CivAction(CivActions.PRODUCE);
                }
            });
            dummy.getPeople().clear();
            this.civilizationList.add(dummy); // If odd number of teams add a dummy
        }
    }

    public Simulation(int numRounds) {
        this.numRounds  = numRounds;
    }

    public void setNumRounds(int numRounds){
        this.numRounds = numRounds;
    }

    public void setPlayerAction(CivAction civAction) {
        this.playerAction = civAction;
        this.player.setStrategy(new Strategy() {
            @Override
            public CivAction executeStrategy(CivPayouts lastPayout) {
                return playerAction;
            }
        });
    }

    public void printScore() {
        for (Civilization civ : score.keySet()) {
            calculateAdditionalPoints(civ);
            controller.addLogMessage(civ.getName() + ": " + score.get(civ));
            controller.addLogMessage("\tCitizens:");
            List<Person> people = civ.getPeople();
            for (Person person : people) {
                String type = "";
                if (person instanceof Civilian) {
                    type = "Civilian";
                }
                else if (person instanceof Soldier) {
                    type = "Soldier";
                }
                controller.addLogMessage("\t\t" + person.getName().toString() + "\t(" + type + ")\t" + "-" + person.getOriginalCivilization());
            }

            printResources(civ);
            printBuildingLevels(civ);
        }
    }

    private void printResources(Civilization civ) {
        controller.addLogMessage("\tResources:");
        Warehouse warehouse = civ.getWarehouse();
        controller.addLogMessage("\t\tWheat: " + warehouse.getWheat().getAmount());
        controller.addLogMessage("\t\tIron: " + warehouse.getIron().getAmount());
        controller.addLogMessage("\t\tWood: " + warehouse.getWood().getAmount());
        controller.addLogMessage("\t\tClay: " + warehouse.getClay().getAmount());
        controller.addLogMessage("\t\tGold: " + warehouse.getIron().getAmount());
    }

    private void printBuildingLevels(Civilization civ) {
        controller.addLogMessage("\tBuildings:");
        HashMap<BuildingName, Building> buildings = civ.getBuildings();
        for (BuildingName buildingName : buildings.keySet()) {
            Building building = buildings.get(buildingName);
            controller.addLogMessage("\t\t" + buildingName.toString() + " - Level " + building.getLevel());
        }
    }

    public void calculateAdditionalPoints(Civilization civ) {
        int additionalPoints = 0;

        //Award points for population (2 per person)
        additionalPoints += calculatePopulationPoints(civ);

        //Award points for resources
        additionalPoints += calculateResourcePoints(civ);

        //Award points for buildings
        additionalPoints += calculateBuildingPoints(civ);

        //Add to score
        score.put(civ, score.get(civ) + additionalPoints);
    }

    private int calculatePopulationPoints(Civilization civ) {
        int points = 2 * civ.getPeople().size();
        controller.addLogMessage(points + " points awarded to " + civ.getName() + " for population.");
        return points;
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

        controller.addLogMessage(points + " points awarded to " + civ.getName() + " for resources.");
        return points;
    }

    private int calculateBuildingPoints(Civilization civ) {
        int points = 0;
        HashMap<BuildingName, Building> buildings = civ.getBuildings();

        for (BuildingName buildingName : buildings.keySet()) {
            Building building = buildings.get(buildingName);

            points += building.getLevel() * 5;
        }

        controller.addLogMessage(points + " points awarded to " + civ.getName() + " for buildings.");
        return points;
    }

    public void runSim () {
        runRoundRobin();
    }

    private void runRoundRobin() {
        if (civilizationList.size() % 2 != 0)
        {
            //If odd number create Dummy civ that only produces
            Civilization dummy = new Civilization("Nomads");
            dummy.setStrategy(new Strategy() {
                @Override
                public CivAction executeStrategy(CivPayouts lastPayout) {
                    return new CivAction(CivActions.PRODUCE);
                }
            });
            dummy.getPeople().clear();
            civilizationList.add(dummy); // If odd number of teams add a dummy
        }

        //Temporarily remove player from list
        civilizationList.removeIf(civ -> civ.getName().equals(player.getName()));

        int halfSize = civilizationList.size() / 2;

        List<Civilization> teams = new ArrayList<>(civilizationList); // Add teams to List

        int teamsSize = teams.size();

        if (nextOpposingCiv == null) {
            int ind = MathUtils.getRandomNumber(0, teamsSize - 1);
            nextOpposingCiv = teams.get(ind);
            teams.remove(ind);
        }
        else {
            teams.remove(this.nextOpposingCiv);
        }

        for (int i = 0; i < numRounds; i++)
        {
            controller.addLogMessage("\n== Round " + (i + 1) + " ==\n");
            headToHead(player, nextOpposingCiv, true);
        }

        controller.addLogMessage("Processing other Civilizations' moves...");
        // Have other civs randomly play each other in the background
        for (int i = 0; i < halfSize; i++) {
            Civilization civ1 = teams.get(i);
            Civilization civ2 = teams.get(i + halfSize);
            for (int j = 0; j < numRounds; j++) {
                headToHead(civ1, civ2, false);
            }
        }

        //Remove dummy civilization
        civilizationList.removeIf(civ -> civ.getName().equals("Nomads"));

        //Add player back
        civilizationList.add(player);

        //Get next opponent
        nextOpposingCiv = teams.get(MathUtils.getRandomNumber(0, teams.size() - 1));
        controller.addLogMessage("\n" + player.getName() + " encountered " + nextOpposingCiv.getName() + "!");

        //Update scores
        HashMap<Civilization, Integer> updatedScores = new HashMap<>();
        for (Civilization civ : score.keySet()) {
            if (!civ.getName().equals("Nomads")) {
                updatedScores.put(civ, score.get(civ));
            }
        }
        score = updatedScores;
    }

    public void headToHead(Civilization civ1, Civilization civ2, boolean showLog) {
        CivPayouts lastPayout1 = CivPayouts.NONE;
        CivPayouts lastPayout2 = CivPayouts.NONE;

        //Instantiate Interaction Helper
        CivInteraction civInteraction = new CivInteraction(this.controller, showLog);

        for (int i = 0; i < numRounds; i++) {
            CivAction civActions1 = civ1.getStrategy().executeStrategy(lastPayout1);
            civ1.setAction(civActions1);
            CivAction civActions2 = civ2.getStrategy().executeStrategy(lastPayout2);
            civ2.setAction(civActions2);

            //Civ 1 Attacks
            switch (civActions1.getAction()) {

                case ATTACK -> {
                    switch (civActions2.getAction()) {
                        case ATTACK -> {
                            lastPayout1 = CivPayouts.VERY_LOW;
                            lastPayout2 = CivPayouts.VERY_LOW;
                            civInteraction.battle().battle(civ1, civ2);
                        }
                        case DEFEND -> {
                            lastPayout1 = CivPayouts.LOW;
                            lastPayout2 = CivPayouts.MODERATE;
                            civInteraction.attack().attack(civ1, civ2, true);
                        }
                        case PRODUCE, TRADE, TRAIN, BUILD -> {
                            lastPayout1 = CivPayouts.VERY_HIGH;
                            lastPayout2 = CivPayouts.VERY_LOW;
                            civInteraction.attack().attack(civ1, civ2, false);
                        }
                    }
                }

                case TRADE -> {
                    switch (civActions2.getAction()) {
                        case TRADE -> {
                            lastPayout1 = CivPayouts.VERY_HIGH;
                            lastPayout2 = CivPayouts.VERY_HIGH;
                            civInteraction.trade().trade(civ1, civ2);
                        }
                        case ATTACK -> {
                            lastPayout1 = CivPayouts.VERY_LOW;
                            lastPayout2 = CivPayouts.VERY_HIGH;
                            civInteraction.attack().attack(civ2, civ1, false);
                        }
                        case DEFEND -> {
                            lastPayout1 = CivPayouts.LOW;
                            lastPayout2 = CivPayouts.VERY_LOW;

                            civInteraction.trade().trade(civ1, civ2);
                        }
                        case TRAIN -> {
                            lastPayout1 = CivPayouts.LOW;
                            lastPayout2 = CivPayouts.MODERATE;

                            civInteraction.trade().trade(civ1, civ2);
                            civInteraction.train().train(civ2);
                        }
                        case BUILD -> {
                            lastPayout1 = CivPayouts.MODERATE;
                            lastPayout2 = CivPayouts.MODERATE;

                            civInteraction.trade().trade(civ1, civ2);
                            civInteraction.build().build(civ2);
                        }
                    }
                }

                case PRODUCE -> {
                    switch (civActions2.getAction()) {
                        case PRODUCE -> {
                            lastPayout1 = CivPayouts.HIGH;
                            lastPayout2 = CivPayouts.HIGH;

                            //Both civs produced
                            civInteraction.produce().produce(civ1);
                            civInteraction.produce().produce(civ2);
                        }
                        case ATTACK -> {
                            lastPayout1 = CivPayouts.VERY_LOW;
                            lastPayout2 = CivPayouts.VERY_HIGH;

                            civInteraction.attack().attack(civ2, civ1, false);
                        }
                        case TRADE -> {
                            lastPayout1 = CivPayouts.MODERATE;
                            lastPayout2 = CivPayouts.MODERATE;
                            civInteraction.produce().produce(civ1);
                            civInteraction.trade().trade(civ2, civ1);
                        }
                        case TRAIN -> {
                            lastPayout1 = CivPayouts.HIGH;
                            lastPayout2 = CivPayouts.LOW;

                            civInteraction.produce().produce(civ1);
                            civInteraction.train().train(civ2);
                        }
                        case DEFEND -> {
                            lastPayout1 = CivPayouts.HIGH;
                            lastPayout2 = CivPayouts.VERY_LOW;

                            civInteraction.produce().produce(civ1);
                        }
                        case BUILD -> {
                            lastPayout1 = CivPayouts.MODERATE;
                            lastPayout2 = CivPayouts.MODERATE;

                            civInteraction.produce().produce(civ1);
                            civInteraction.build().build(civ2);
                        }
                    }
                }

                case TRAIN -> {
                    switch (civActions2.getAction()) {
                        case TRAIN -> {
                            lastPayout1 = CivPayouts.LOW;
                            lastPayout2 = CivPayouts.LOW;

                            //Both trained soldiers
                            civInteraction.train().train(civ1);
                            civInteraction.train().train(civ2);
                        }
                        case TRADE -> {
                            lastPayout1 = CivPayouts.MODERATE;
                            lastPayout2 = CivPayouts.LOW;

                            civInteraction.train().train(civ1);
                            civInteraction.trade().trade(civ2, civ1);
                        }
                        case PRODUCE -> {
                            lastPayout1 = CivPayouts.LOW;
                            lastPayout2 = CivPayouts.HIGH;

                            civInteraction.train().train(civ1);
                            civInteraction.produce().produce(civ2);
                        }
                        case ATTACK -> {
                            lastPayout1 = CivPayouts.VERY_LOW;
                            lastPayout2 = CivPayouts.HIGH;

                            civInteraction.attack().attack(civ2, civ1, false);
                        }
                        case DEFEND -> {
                            lastPayout1 = CivPayouts.MODERATE;
                            lastPayout2 = CivPayouts.LOW;

                            civInteraction.train().train(civ1);
                        }
                        case BUILD -> {
                            lastPayout1 = CivPayouts.MODERATE;
                            lastPayout2 = CivPayouts.MODERATE;

                            civInteraction.train().train(civ1);
                            civInteraction.build().build(civ2);
                        }
                    }
                }

                case DEFEND -> {
                    switch (civActions2.getAction()) {
                        case ATTACK -> {
                            lastPayout1 = CivPayouts.MODERATE;
                            lastPayout2 = CivPayouts.LOW;

                            civInteraction.attack().attack(civ2, civ1, true);
                        }
                        case DEFEND -> {
                            lastPayout1 = CivPayouts.LOW;
                            lastPayout2 = CivPayouts.LOW;
                        }
                        case PRODUCE -> {
                            lastPayout1 = CivPayouts.VERY_LOW;
                            lastPayout2 = CivPayouts.HIGH;

                            civInteraction.produce().produce(civ2);
                        }
                        case TRADE -> {
                            lastPayout1 = CivPayouts.VERY_LOW;
                            lastPayout2 = CivPayouts.LOW;

                            civInteraction.trade().trade(civ2, civ1);
                        }
                        case TRAIN -> {
                            lastPayout1 = CivPayouts.LOW;
                            lastPayout2 = CivPayouts.MODERATE;

                            civInteraction.train().train(civ2);
                        }
                        case BUILD -> {
                            lastPayout1 = CivPayouts.LOW;
                            lastPayout2 = CivPayouts.HIGH;

                            civInteraction.build().build(civ2);
                        }
                    }
                }

                case BUILD -> {

                    switch (civActions2.getAction()) {
                        case BUILD -> {
                            lastPayout1 = CivPayouts.MODERATE;
                            lastPayout2 = CivPayouts.MODERATE;
                            civInteraction.build().build(civ1);
                            civInteraction.build().build(civ2);
                        }
                        case ATTACK -> {
                            lastPayout1 = CivPayouts.VERY_LOW;
                            lastPayout2 = CivPayouts.VERY_HIGH;

                            civInteraction.attack().attack(civ2, civ1, false);
                        }
                        case DEFEND -> {
                            lastPayout1 = CivPayouts.HIGH;
                            lastPayout2 = CivPayouts.LOW;

                            civInteraction.build().build(civ1);
                        }
                        case TRAIN -> {
                            lastPayout1 = CivPayouts.MODERATE;
                            lastPayout2 = CivPayouts.MODERATE;

                            civInteraction.build().build(civ1);
                            civInteraction.train().train(civ2);
                        }
                        case TRADE -> {
                            lastPayout1 = CivPayouts.MODERATE;
                            lastPayout2 = CivPayouts.MODERATE;

                            civInteraction.build().build(civ1);
                            civInteraction.trade().trade(civ1, civ2);
                        }
                        case PRODUCE -> {
                            lastPayout1 = CivPayouts.MODERATE;
                            lastPayout2 = CivPayouts.MODERATE;

                            civInteraction.build().build(civ1);
                            civInteraction.produce().produce(civ2);
                        }
                    }
                }
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

    public void updateResourceGUI() {
        Warehouse warehouse = player.getWarehouse();

        Platform.runLater(() -> controller.getLabelKnowledgeAmount().setText("Knowledge: " + player.getKnowledge().getAmount()));

        Platform.runLater(() -> controller.getButtonWheat().setText("Wheat: " + warehouse.getWheat().getAmount()));
        Platform.runLater(() -> controller.getButtonClay().setText("Clay: " + warehouse.getClay().getAmount()));
        Platform.runLater(() -> controller.getButtonWood().setText("Wood: " + warehouse.getWood().getAmount()));
        Platform.runLater(() -> controller.getButtonIron().setText("Iron: " + warehouse.getIron().getAmount()));

        Platform.runLater(() -> controller.getButtonGold().setText("Gold: " + warehouse.getGold().getAmount()));
    }

    public void updatePeopleGUI() {
        List<Person> people  = player.getPeople();
        List<Soldier> soldiers = player.getSoldiers();
        List<Scholar> scholars = player.getScholars();

        Platform.runLater(() -> controller.getLabelCivilianCount().setText("Civilians: " + (people.size() - soldiers.size() - scholars.size())));
        Platform.runLater(() -> controller.getLabelSoldierCount().setText("Soldiers: " + soldiers.size()));
        Platform.runLater(() -> controller.getLabelScholarCount().setText("Scholars: " + scholars.size()));
    }

    @Override
    protected Task<Void> createTask() {

        return new Task<>() {
            @Override
            protected Void call() {
                runSim();
                updateResourceGUI();
                updatePeopleGUI();
                return null;
            }
        };
    }
}
