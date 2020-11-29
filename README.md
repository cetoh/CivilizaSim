# CivilizaSim
 A Java Based Civilation Simulation

The game is as follows. Make civilization. Implement a strategy. Strategies are given the last Payout your Civilization received and must return one of the 5 CivActions:

ATTACK
DEFEND
PRODUCE
TRADE
TRAIN

Civiliazations are instantiated and must have a strategy to compete. All Civilizations will complete twice against the other civilization in a head-to-head. During the head to head, each Civilization can take 10 actions. Based on these actions, and the action of the opposing civilization, a reward or Payout will be given and added to the Civ's point total.

VERY_HIGH = 5 pts
HIGH = 4 pts
MODERATE = 3 pts
LOW = 2 pts
VERY_LOW = 1 pts
NONE = 0 pts

The first Payout passed into strategy will always be CivPayouts.NONE as in the first round both Civilizations will not have made an action yet.

The table of what happens between the Civ Actions is as follows:

| Civ 1 Action | Civ 2 Action | Attack            | Defend                    | Produce                           | Trade                             | Train                           |
|    :---:     |     :---:    |  :---:            |  :---:                    |  :---:                            | :---:                             | :---:                           |
| Attack       |      ***     |Both Civs: Very Low|Civ 1: Low, Civ 2: Moderate| Civ 1: Very High, Civ 2: Very Low | Civ 1: Very High, Civ 2: Very Low |  Civ 1: Very High, Civ 2: Very Low |
| Defend       |      ***     |Civ 1: Moderate, Civ 2: Low|Both Civs: Moderate|Civ 1: Very Low, Civ 2: High|Civ 1: Very Low, Civ 2: Low|Civ 1: Low, Civ 2: Moderate|
| Produce      |      ***     |Civ 1: Very Low, Civ 2: Very High|Civ 1: High, Civ 2: Very Low|Civ 1: Very High, Civ 2: Very High|Civ 1: Moderate, Civ 2: Moderate|Civ 1: High, Civ 2: Low|
| Trade        |      ***     |Civ 1: Very Low, Civ 2: Very High|Civ 1: Low, Civ 2: Very Low|Civ 1: Moderate, Civ 2: Moderate|Civ 1: Very High, Civ 2: Very High|Civ 1: Low, Civ 2: Moderate|
| Train        |      ***     |Civ 1: Very Low, Civ 2: Very High|Civ 1: Moderate, Civ 2: Low|Civ 1: Low, Civ 2: High|Civ 1: Moderate, Civ 2: Low|Both Civs: Low|
