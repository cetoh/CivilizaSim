package com.toh.us.CivilizaSim;

import com.toh.us.CivilizaSim.GameObjects.Civ.Civilization;
import com.toh.us.CivilizaSim.GameObjects.Simulate.GenerateCivilizations;
import com.toh.us.CivilizaSim.GameObjects.Simulate.Simulation;

import java.util.List;

public class CivilizaSim {

    public static void main(String[] args) {
        GenerateCivilizations generateCivilizations = new GenerateCivilizations();

        List<Civilization> score = generateCivilizations.getCivilizations();

        Simulation simulation = new Simulation();
        simulation.runSim(score);

        simulation.printScore();
    }


}
