package com.toh.us.CivilizaSim.GameObjects.Civ.Interact;

import com.toh.us.CivilizaSim.Display.PrimaryController;
import com.toh.us.CivilizaSim.GameObjects.Civ.Civilization;
import com.toh.us.CivilizaSim.GameObjects.People.Soldier;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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

        int powerDifference = calculatePowerDifference(civilization1, civilization2);
        // They win the battle
        if (powerDifference < 0) {
            /*try {
                civilization1.getPeople().removeAll(ourSoldiers);
                for (int i = 0; i < numOurSoldiers; i++) {
                    Soldier remove = theirSoldiers.remove(i);
                    civilization2.getPeople().remove(remove);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }*/

            if (showLog) {
                controller.addLogMessage(civilization2.getName() + " won but at a great cost!");
            }
        } else if (powerDifference > 0) {
            //We win the battle
            /*try {
                civilization2.getPeople().removeAll(theirSoldiers);
                for (int i = 0; i < numTheirSoldiers; i++) {
                    Soldier remove = ourSoldiers.remove(i);
                    civilization1.getPeople().remove(remove);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }*/

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

    private int calculatePowerDifference(Civilization civilization1, Civilization civilization2) {
        List<Soldier> ourSoldiers = civilization1.getSoldiers();
        List<Soldier> theirSoldiers = civilization2.getSoldiers();
        int numOurSoldiers;
        int numTheirSoldiers;

        List<Soldier> ourDeadSoldiers = new ArrayList<>();
        List<Soldier> theirDeadSoldiers = new ArrayList<>();

        while (ourSoldiers.size() > 0 && theirSoldiers.size() > 0) {

            for (Soldier ourSoldier : ourSoldiers) {

                if (ourSoldier.getHealth() > 0) {
                    for (Soldier theirSoldier : theirSoldiers) {

                        if (theirSoldier.getHealth() > 0) {
                            if (showLog) {
                                controller.addLogMessage(theirSoldier.getName() + " dealt " + theirSoldier.getAttackStat() + " damage to " + ourSoldier.getName());
                                controller.addLogMessage(ourSoldier.getName() + " dealt " + ourSoldier.getAttackStat() + " damage to " + theirSoldier.getName());
                            }
                            ourSoldier.decreaseHealth((int) theirSoldier.getAttackStat());
                            theirSoldier.decreaseHealth((int) ourSoldier.getAttackStat());

                            if (ourSoldier.getHealth() <= 0) {
                                if (showLog) {
                                    controller.addLogMessage(ourSoldier.getName() + " perished in battle!");
                                }
                                ourDeadSoldiers.add(ourSoldier);
                                break;
                            }

                            if (theirSoldier.getHealth() <= 0) {
                                if (showLog) {
                                    controller.addLogMessage(theirSoldier.getName() + " perished in battle!");
                                }
                                theirDeadSoldiers.add(theirSoldier);
                            }
                        }

                    }
                }
            }

            civilization1.getPeople().removeAll(ourDeadSoldiers);
            civilization2.getPeople().removeAll(theirDeadSoldiers);

            ourDeadSoldiers.clear();
            theirDeadSoldiers.clear();

            ourSoldiers = civilization1.getSoldiers();
            theirSoldiers = civilization2.getSoldiers();
        }

        numOurSoldiers = civilization1.getSoldiers().size();
        numTheirSoldiers = civilization2.getSoldiers().size();

        return numOurSoldiers - numTheirSoldiers;
    }
}
