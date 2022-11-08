package com.toh.us.CivilizaSim.GameObjects.Civ.Interact;

import com.toh.us.CivilizaSim.Display.PrimaryController;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Civ.Civilization;
import com.toh.us.CivilizaSim.GameObjects.People.Person;
import com.toh.us.CivilizaSim.GameObjects.People.Soldier;
import com.toh.us.CivilizaSim.GameObjects.Resources.Warehouse;
import com.toh.us.CivilizaSim.GameObjects.Simulate.MathUtils;

import java.util.ArrayList;
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

            int difference = calculatePowerDifference(attacker, defender, defended);
            if (defended) {
                if (showLog) {
                    if (difference < 0) {
                        controller.addLogMessage(attacker.getName() + " attacked " + defender.getName()
                                + " but " + defender.getName() + " defended successfully!");
                    } else if (difference > 0) {
                        controller.addLogMessage(attacker.getName() + " attacked " + defender.getName()
                                + ". " + defender.getName() + " mounted a defense but were overwhelmed! All their defenders perished but their resources were saved!");
                    } else {
                        controller.addLogMessage(attacker.getName() + " attacked " + defender.getName()
                                + ". " + defender.getName() + "! The battle was fierce and neither could gain an advantage!");
                    }
                }
            } else {
                if (difference > 0) {
                    // Exile 30% of their people
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

                    //Steal their resources based on the capacity of remaining soldiers
                    int capacity = 0;
                    attackingSoldiers = attacker.getSoldiers();
                    for (Soldier soldier: attackingSoldiers) {
                        capacity += soldier.getPackAmount();
                    }
                    capacity /= 5;
                    Warehouse defenderWarehouse = defender.getWarehouse();
                    int gold = capacity;
                    int wheat = capacity;
                    int wood = capacity;
                    int iron = capacity;
                    int clay = capacity;

                    int goldTaken = defenderWarehouse.getGold().removeAmount(gold);
                    int wheatTaken = defenderWarehouse.getWheat().removeAmount(wheat);
                    int woodTaken = defenderWarehouse.getWood().removeAmount(wood);
                    int ironTaken = defenderWarehouse.getIron().removeAmount(iron);
                    int clayTaken = defenderWarehouse.getClay().removeAmount(clay);

                    Warehouse attackerWarehouse = attacker.getWarehouse();
                    attackerWarehouse.getGold().addAmount(goldTaken);
                    attackerWarehouse.getWheat().addAmount(wheatTaken);
                    attackerWarehouse.getWood().addAmount(woodTaken);
                    attackerWarehouse.getIron().addAmount(ironTaken);
                    attackerWarehouse.getClay().addAmount(clayTaken);

                    if (showLog) {
                        controller.addLogMessage(attacker.getName() + " attacked and raided "
                                + defender.getName() + " exiling a portion of their people!");
                    }
                } else if (difference < 0){
                    if (showLog) {
                        controller.addLogMessage(attacker.getName() + " attacked and raided "
                                + defender.getName() + ", but the soldiers of " + defender.getName() + " rallied and repelled them!");
                    }
                } else {
                    if (showLog) {
                        controller.addLogMessage(attacker.getName() + " attacked " + defender.getName()
                                + ". " + defender.getName() + "! The battle was fierce and neither could gain an advantage!");
                    }
                }
            }
        } else {
            if (showLog) {
                controller.addLogMessage(attacker.getName() + " has no soldiers to attack "
                        + defender.getName() + " so they just awkwardly looked at each other...");
            }
        }
    }

    private int calculatePowerDifference(Civilization attacker, Civilization defender, boolean defended) {
        List<Soldier> attackingSoldiers = attacker.getSoldiers();
        List<Soldier> defendingSoldiers = defender.getSoldiers();

        List<Soldier> attackerDeadSoldiers = new ArrayList<>();
        List<Soldier> defenderDeadSoldiers = new ArrayList<>();

        while (attackingSoldiers.size() > 0 && defendingSoldiers.size() > 0) {

            for (Soldier attackSoldier : attackingSoldiers) {

                if (attackSoldier.getHealth() > 0) {
                    for (Soldier defendSoldier : defendingSoldiers) {

                        if (defendSoldier.getHealth() > 0) {

                            double defenseBonus = getDefenseBonus(defender);
                            double attackBonus = getAttackBonus(attacker);

                            int damageToAttacker = (int) (calculateDamageDealtToAttacker(attackSoldier, defendSoldier, defended) * defenseBonus);
                            int damageToDefender = (int) (calculateDamageDealtToDefender(attackSoldier, defendSoldier, defended) * attackBonus);

                            if (showLog) {
                                controller.addLogMessage(defendSoldier.getName() + " dealt " + damageToAttacker + " damage to " + attackSoldier.getName());
                                controller.addLogMessage(attackSoldier.getName() + " dealt " + damageToDefender + " damage to " + defendSoldier.getName());
                            }

                            attackSoldier.decreaseHealth(damageToAttacker);
                            defendSoldier.decreaseHealth(damageToDefender);

                            if (attackSoldier.getHealth() <= 0) {
                                if (showLog) {
                                    controller.addLogMessage(attackSoldier.getName() + " perished in battle!");
                                }
                                attackerDeadSoldiers.add(attackSoldier);
                                break;
                            }

                            if (defendSoldier.getHealth() <= 0) {
                                if (showLog) {
                                    controller.addLogMessage(defendSoldier.getName() + " perished in battle!");
                                }
                                defenderDeadSoldiers.add(defendSoldier);
                            }
                        }

                    }
                }
            }

            attacker.getPeople().removeAll(attackerDeadSoldiers);
            defender.getPeople().removeAll(defenderDeadSoldiers);

            attackerDeadSoldiers.clear();
            defenderDeadSoldiers.clear();

            attackingSoldiers = attacker.getSoldiers();
            defendingSoldiers = defender.getSoldiers();
        }

        int numAttackingSoldiers = attacker.getSoldiers().size();
        int numDefendingSoldiers = defender.getSoldiers().size();

        return numAttackingSoldiers - numDefendingSoldiers;
    }

    private int calculateDamageDealtToAttacker(Soldier attackSoldier, Soldier defendSoldier, boolean defended) {
        double attackModifier = 1;

        if (defended) {
            attackModifier = attackModifier / 10;
        }

        int damageToAttacker = (int) ((attackSoldier.getAttackStat() * attackModifier) - defendSoldier.getDefenseStat());

        if (damageToAttacker < 0) {
            damageToAttacker = 1;
        }

        return damageToAttacker;
    }

    private int calculateDamageDealtToDefender(Soldier attackSoldier, Soldier defendSoldier, boolean defended) {
        double defenseModifier = 1;

        if (!defended) {
            defenseModifier = defenseModifier / 10;
        }

        int damageToDefender = (int) (attackSoldier.getAttackStat() - (defendSoldier.getDefenseStat() * defenseModifier));

        if (damageToDefender < 0) {
            damageToDefender = 1;
        }

        return damageToDefender;
    }

    private double getDefenseBonus(Civilization defender) {
        //Wall bonus
        double bonus = 1;
        if (defender.getBuildings().containsKey(BuildingName.WALLS))
            bonus += defender.getBuildings().get(BuildingName.WALLS).getLevel() * 0.01;

        return bonus;
    }

    private double getAttackBonus(Civilization attacker) {
        //Wall bonus
        double bonus = 1;

        return bonus;
    }
}
