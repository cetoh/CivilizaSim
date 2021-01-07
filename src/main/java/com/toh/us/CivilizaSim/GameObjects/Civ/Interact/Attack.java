package com.toh.us.CivilizaSim.GameObjects.Civ.Interact;

import com.toh.us.CivilizaSim.Display.PrimaryController;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Civ.Civilization;
import com.toh.us.CivilizaSim.GameObjects.People.Person;
import com.toh.us.CivilizaSim.GameObjects.People.Soldier;
import com.toh.us.CivilizaSim.GameObjects.Resources.Warehouse;
import com.toh.us.CivilizaSim.GameObjects.Simulate.MathUtils;

import java.util.List;

public class Attack {
    private PrimaryController controller;

    private boolean showLog;

    public Attack(PrimaryController controller, boolean showLog) {
        this.controller = controller;
        this.showLog    = showLog;
    }

    public void attack(Civilization attacker, Civilization defender, boolean defended) {
        List<Soldier> defendingSoldiers = defender.getSoldiers();
        List<Soldier> attackingSoldiers = attacker.getSoldiers();
        if (attackingSoldiers.size() > 0) {
            //Do Ministry of Intelligence Check
            if (defender.getBuildings().containsKey(BuildingName.MINISTRY_OF_INTELLIGENCE)){
                int check = 3 * defender.getBuildings().get(BuildingName.MINISTRY_OF_INTELLIGENCE).getLevel();

                if (MathUtils.getRandomNumber(0, 100) <= check) {
                    defended = true;
                    controller.addLogMessage(defender.getName() + "'s spies caught wind of an attack from "
                            + attacker.getName() + ". Thus they were able to set up a successful defense!");
                }
            }
            if (defended) {
                //Wall bonus
                double bonus = 1;
                if (defender.getBuildings().containsKey(BuildingName.WALLS))
                    bonus += defender.getBuildings().get(BuildingName.WALLS).getLevel() * 0.01;

                attackingSoldiers = attackingSoldiers.subList(0, (int) (attackingSoldiers.size() * 0.75 * bonus));
                defendingSoldiers = defendingSoldiers.subList(0, (int) ((defendingSoldiers.size() / 2) * bonus));

                try {
                    defender.getPeople().removeAll(defendingSoldiers);
                    attacker.getPeople().removeAll(attackingSoldiers);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                if (showLog) {
                    controller.addLogMessage(attacker.getName() + " attacked " + defender.getName()
                            + " but " + defender.getName() + " defended successfully!");
                }
            } else {
                defendingSoldiers = defendingSoldiers.subList(0, (int) (defendingSoldiers.size() * 0.75));

                try {
                    defender.getPeople().removeAll(defendingSoldiers);
                    List<Person> attackingPeople = attacker.getPeople();
                    for (int i = 0; i < (int) (defender.getPeople().size() * 0.3); i++) {
                        Person exile = defender.getPeople().remove(i);
                        attackingPeople.add(exile);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                //Steal their resources (75%)
                Warehouse defenderWarehouse = defender.getWarehouse();
                int gold = (int) (defenderWarehouse.getGold().getAmount() * 0.75);
                int wheat = (int) (defenderWarehouse.getWheat().getAmount() * 0.75);
                int wood = (int) (defenderWarehouse.getWood().getAmount() * 0.75);
                int iron = (int) (defenderWarehouse.getIron().getAmount() * 0.75);
                int clay = (int) (defenderWarehouse.getClay().getAmount() * 0.75);

                defenderWarehouse.getGold().removeAmount(gold);
                defenderWarehouse.getWheat().removeAmount(wheat);
                defenderWarehouse.getWood().removeAmount(wood);
                defenderWarehouse.getIron().removeAmount(iron);
                defenderWarehouse.getClay().removeAmount(clay);

                Warehouse attackerWarehouse = attacker.getWarehouse();
                attackerWarehouse.getGold().addAmount(gold);
                attackerWarehouse.getWheat().addAmount(wheat);
                attackerWarehouse.getWood().addAmount(wood);
                attackerWarehouse.getIron().addAmount(iron);
                attackerWarehouse.getClay().addAmount(clay);

                if (showLog) {
                    controller.addLogMessage(attacker.getName() + " attacked and raided "
                            + defender.getName() + " exiling a portion of their people!");
                }
            }
        } else {
            if (showLog) {
                controller.addLogMessage(attacker.getName() + " has no soldiers to attack "
                        + defender.getName() + " so they just awkwardly looked at each other...");
            }
        }
    }
}
