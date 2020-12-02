# CivilizaSim
 A Java Based Civilization Simulation

The game is as follows. Make civilization. Implement a strategy. Strategies are given the last Payout your Civilization received and must return one of the 5 CivActions:

* ATTACK
* DEFEND
* PRODUCE
* TRADE
* TRAIN

Civiliazations are instantiated and must have a strategy to compete. All Civilizations will complete ~~twice~~ once (this was changed in v1.0.1) against the other civilization in a head-to-head. During the head to head, each Civilization can take 10 actions. Based on these actions, and the action of the opposing civilization, a reward or Payout will be given and added to the Civ's point total.

* VERY_HIGH = 5 pts
* HIGH = 4 pts
* MODERATE = 3 pts
* LOW = 2 pts
* VERY_LOW = 1 pts
* NONE = 0 pts

The first Payout passed into strategy will always be CivPayouts.NONE as in the first round both Civilizations will not have made an action yet.

The table of what happens between the Civ Actions when matched up and what each Civilization gets as the resulting Payout is as follows:

| Civ 1 Action | Civ 2 Action | Attack            | Defend                    | Produce                           | Trade                             | Train                           |
|    :---:     |     :---:    |  :---:            |  :---:                    |  :---:                            | :---:                             | :---:                           |
| Attack       |      ***     |Both Civs: Very Low|Civ 1: Low, Civ 2: Moderate| Civ 1: Very High, Civ 2: Very Low | Civ 1: Very High, Civ 2: Very Low |  Civ 1: Very High, Civ 2: Very Low |
| Defend       |      ***     |Civ 1: Moderate, Civ 2: Low|Both Civs: Moderate|Civ 1: Very Low, Civ 2: High|Civ 1: Very Low, Civ 2: Low|Civ 1: Low, Civ 2: Moderate|
| Produce      |      ***     |Civ 1: Very Low, Civ 2: Very High|Civ 1: High, Civ 2: Very Low|Civ 1: Very High, Civ 2: Very High|Civ 1: Moderate, Civ 2: Moderate|Civ 1: High, Civ 2: Low|
| Trade        |      ***     |Civ 1: Very Low, Civ 2: Very High|Civ 1: Low, Civ 2: Very Low|Civ 1: Moderate, Civ 2: Moderate|Civ 1: Very High, Civ 2: Very High|Civ 1: Low, Civ 2: Moderate|
| Train        |      ***     |Civ 1: Very Low, Civ 2: Very High|Civ 1: Moderate, Civ 2: Low|Civ 1: Low, Civ 2: High|Civ 1: Moderate, Civ 2: Low|Both Civs: Low|

At the end of running the simulation, the Civilizations will be listed with their final scores.

I have no idea what the best strategy is and as more civilizations are added you could potentially seeing other some strategies gaining precedence while others falter.

## Future Plans
I intend to eventually implement more complicated game mechanics such as scoring based off population, possibly buildings and resources as well. For example during produce you could potentially see a Civ also growing in population but during an a successful attack some Civilians are carried off into exile into the new Civilization that wins.

## [v1.0.0](https://github.com/cetoh/CivilizaSim/tree/v1.0.0)
* This version has a basic game with only the 5 actions in the table and the basic scoring mentioned.

## [v1.0.1](https://github.com/cetoh/CivilizaSim/tree/v1.0.1)
* Implemented Naming of Persons
* Implemented Growing of civilization population
* Changed game rules to have civilizations only face each other once instead of twice
* Implemented Attacking, Defending, and Battling, including exiling of persons into another Civilization
* Added more messages to give an idea of what's going on.
