package com.toh.us.CivilizaSim.GameObjects.Civ.Interact;

import com.toh.us.CivilizaSim.Display.PrimaryController;
import com.toh.us.CivilizaSim.GameObjects.Civ.Civilization;
import com.toh.us.CivilizaSim.GameObjects.People.Soldier;

import java.util.List;

public class Battle {

    private PrimaryController controller;

    private boolean showLog;

    public Battle(PrimaryController controller, boolean showLog) {
        this.controller = controller;
        this.showLog    = showLog;
    }

    public void battle(Civilization civilization1, Civilization civilization2) {
        List<Soldier> ourSoldiers = civilization1.getSoldiers();
        List<Soldier> theirSoldiers = civilization2.getSoldiers();
        int numOurSoldiers = ourSoldiers.size();
        int numTheirSoldiers = theirSoldiers.size();

        if (showLog) {
            controller.addLogMessage("A battle occurred!");
        }
        // They win the battle
        if (numOurSoldiers < numTheirSoldiers) {
            try {
                civilization1.getPeople().removeAll(ourSoldiers);
                for (int i = 0; i < numOurSoldiers; i++) {
                    Soldier remove = theirSoldiers.remove(i);
                    civilization2.getPeople().remove(remove);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if (showLog) {
                controller.addLogMessage(civilization2.getName() + " won but at a great cost!");
            }
        } else if (numTheirSoldiers < numOurSoldiers) {
            //We win the battle
            try {
                civilization2.getPeople().removeAll(theirSoldiers);
                for (int i = 0; i < numTheirSoldiers; i++) {
                    Soldier remove = ourSoldiers.remove(i);
                    civilization1.getPeople().remove(remove);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if (showLog) {
                controller.addLogMessage(civilization1.getName() + " won but at a great cost!");
            }
        } else {
            //We are equally matched
            try {
                civilization1.getPeople().removeAll(ourSoldiers);
                civilization2.getPeople().removeAll(theirSoldiers);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (showLog) {
                controller.addLogMessage(civilization1.getName() + " & " + civilization2.getName()
                        + " were equally matched, suffering many casualties!");
            }
        }
    }
}
