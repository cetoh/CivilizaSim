# CivilizaSim
 A Java Based Civilization Simulation

The game is as follows. Make civilization. Implement a strategy. Strategies are given the last Payout your Civilization received and must return one of the 6 CivActions:

* ATTACK
* DEFEND
* PRODUCE
* TRADE
* TRAIN
* BUILD

Civilizations are instantiated and must have a strategy to compete. All Civilizations will complete ~~twice~~ once (this was changed in v1.0.1) against the other civilization in a head-to-head. During the head to head, each Civilization can take a specified number of actions. Based on these actions, and the action of the opposing civilization, a reward or Payout will be given and added to the Civilization's point total. Civilizations will face new civilizations in a round-robin format.

* VERY_HIGH = 5 pts
* HIGH = 4 pts
* MODERATE = 3 pts
* LOW = 2 pts
* VERY_LOW = 1 pts
* NONE = 0 pts

The first Payout passed into strategy will always be CivPayouts.NONE as in the first round both Civilizations will not have made an action yet.

The table of what happens between the Civ Actions when matched up and what each Civilization gets as the resulting Payout is as follows:

| Civ 1 Action | Civ 2 Action | Attack            | Defend                    | Produce                           | Trade                             | Train                           | Build                           |
|    :---:     |     :---:    |  :---:            |  :---:                    |  :---:                            | :---:                             | :---:                           | :---:                           |
| Attack       |      ***     |Both Civs: Very Low|Civ 1: Low, Civ 2: Moderate| Civ 1: Very High, Civ 2: Very Low | Civ 1: Very High, Civ 2: Very Low |  Civ 1: Very High, Civ 2: Very Low |Civ 1: Very High, Civ 2: Very Low|
| Defend       |      ***     |Civ 1: Moderate, Civ 2: Low|Both Civs: Moderate|Civ 1: Very Low, Civ 2: High|Civ 1: Very Low, Civ 2: Low|Civ 1: Low, Civ 2: Moderate|Civ 1: Low, Civ 2: High|
| Produce      |      ***     |Civ 1: Very Low, Civ 2: Very High|Civ 1: High, Civ 2: Very Low|Civ 1: Very High, Civ 2: Very High|Civ 1: Moderate, Civ 2: Moderate|Civ 1: High, Civ 2: Low|Both Civs High|
| Trade        |      ***     |Civ 1: Very Low, Civ 2: Very High|Civ 1: Low, Civ 2: Very Low|Civ 1: Moderate, Civ 2: Moderate|Civ 1: Very High, Civ 2: Very High|Civ 1: Low, Civ 2: Moderate|Both Civs Moderate|
| Train        |      ***     |Civ 1: Very Low, Civ 2: Very High|Civ 1: Moderate, Civ 2: Low|Civ 1: Low, Civ 2: High|Civ 1: Moderate, Civ 2: Low|Both Civs: Low|Both Civs Moderate|
| Build        |      ***     |Civ 1: Very Low, Civ 2: Very High|Civ 1: High, Civ 2: Low | Both Civs High|Both Civs Moderate|Both Civs Moderate|Both Civs Moderate|

At the end of running the simulation, the Civilizations will be listed with their final scores.

I have no idea what the best strategy is and as more civilizations are added you could potentially see other some strategies gaining precedence while others falter.

# How To Play The Game

*As of v1.0.4, this is now unnecessary and a GUI has been created to give the player an easier method without coding to play*

You can instantiate your own civilization in the `GenerateCivilizations.java` class.

Within this class you can create a `new Civilization()` as follows:

```java
Civilization myCivilization = new Civilization("Name My Civ");

//Add Civilization to the list of civs
civilization.add(myCivilization);

//Set up a strategy for your civ
myCivilization.setStrategy(new Strategy() {
            @Override
            public CivAction executeStrategy(CivPayouts lastPayout) {
                CivAction ourAction = new CivAction();
                // You are given your last reward payout for your last action and you must return a valid CivAction for your next action. 
                // How you decide to do this is up to you! You can create aditional methods if you wish!
                // The default CivAction on construction is CivActions.PRODUCE
                ourAction = otherMethodToHelpDecideStrategy();
                
                return ourAction;
            }
            
            private CivAction otherMethodToHelpDecideStrategy() {
                // You can declare other methods to make your strategy more robust or complicated!
                // Up to you!
        
                // Let's say you want to check your civilization's resources. You can do so here as well.
                Warehouse myWarehouse = myCivilization.warehouse;
                return new CivAction(CivActions.NONE);
            }
        });
```

### Training
When attempting to train, the Civilization must have a Civilian who is not already Soldier available. The Train Action will only ever produce one soldier unless the civilization has a Barracks built. Training will only be successful if the Civilization has available 25 Iron, 20 Wood, 20 Wheat, and 5 Gold.

### Producing
Producing will give the Civilization 100 of each basic resource (Iron, Wood, Wheat, & Clay) and 10 of each luxury resource (GOLD). The Civilization will also give birth to a random number between 1-10 Civilians.

### Trading
If both Civilizations trade both civilizations will gain a random number of resources between 1-100 for each basic resource and a random number between 1-50 for each luxury resource.

If one Civ chooses to trade, and the other only produces or trains, for a random set of resources each Civ will randomly lose between 1-100 of that resource and randomly gain between 1-100 of another resource.

If one Civ chooses to trade, and the other Defends nothing happens, but the Civ that traded gets an extra 10 gold for their time.

In the case of attack see Attacking.

### Attacking
When Attacking, if the other Civilization also Attacked then both Civilizations will put their Soldiers head-to-head. The Civilization's will kill each other sodliers on a 1 to 1 basis and any Civilization that has Soldiers remaing is declared the "winner", although you don't really win much except losing all your people.

When Attacking, if the other Civilization chose to Produce, Train, or Defend, then the Attacking Civilization will steal 75% of each resource from the other Civilization. The Attacking Civilization also Exile 30% of the other Civilization's population, assimilating them into their own Civilization.

When Attacking, if the other Civilization chose, to Defend see Defending below.

### Defending
When Defending, nothing happens unless the Civilization successfully Defends against another Attack. The Attacking Civilization will lose 75% of their Soldiers while the Defending Civilization will lose 50% of their Soldiers.

### Building
Whe Building, you must also specify a Building Name in your ```CivAction()``` for example ```civAction.setBuildingName(BuildingName.BARRACKS)``` when returning it from your strategy. If the civilization has enough resources the building will be upgraded. Each subsequent level of the building will cost 20 more of each resource.

#### Buildings Available Currently
* _**AMPHITHEATER**_                - Each level in this building increases your increases the chance a person from another civilization joins yours during a trade initiated by you by 1%.
* _**AQUEDUCT**_                    - Each level in this building increases your production gain by 1.5%.
* _**BANK**_                        - Each level in this building increase Gold production by 10%.
* _**BARRACKS**_                    - Each level in this building will increase the number of soldiers you can train for each Train Action by 1.
* _**FLOUR MILL**_                  - Each level in this building increases wheat production by 5%.
* _**KILN**_                        - Each level in this building increases Clay production by 5%.  
* _**LUMBER MILL**_                 - Each level in this building increases Wood production by 5%.
* _**MARKETPLACE**_                 - Each level in this building increases your trade yield with other civilizations by 1%.
* _**MINE**_                        - Each level in this building increases Iron production by 5%.
* _**MINISTRY OF COMMERCE**_        - Each level in this building increases the amount of resources you get from a trade by 10 for each normal resource and 2 for each luxury resource BEFORE other bonuses are applied.
* _**MINISTRY OF INTELLIGENCE**_    - Each level in this building increases the chance you spot an attack coming and successfully defend by 3%.  
* _**WALLS**_                       - Each level in this building increases your defensive capabilities by 1%.

## Future Plans
(DONE)I intend to eventually implement more complicated game mechanics such as scoring based off population, possibly buildings and resources as well. For example during produce you could potentially see a Civ also growing in population but during an a successful attack some Civilians are carried off into exile into the new Civilization that wins.

* Plan to add more buildings in all categories
* Plan to improve AI's
* Plan to add graphics (pending Andrew Toh's availability)

## [v1.0.0](https://github.com/cetoh/CivilizaSim/tree/v1.0.0)
* This version has a basic game with only the 5 actions in the table and the basic scoring mentioned.

## [v1.0.1](https://github.com/cetoh/CivilizaSim/tree/v1.0.1)
* Implemented Naming of Persons
* Implemented Growing of civilization population
* Changed game rules to have civilizations only face each other once instead of twice
* Implemented Attacking, Defending, and Battling, including exiling of persons into another Civilization
* Added more messages to give an idea of what's going on.

## [v1.0.2](https://github.com/cetoh/CivilizaSim/tree/v1.0.2) 
* Resources are now involved in Attacking and Training
* Updated Scoring to include calculation of Population and Resources remaining at the end
* Implemented an actual round-robin tournament play to prevent any one Civ from getting an advantage from going first
* GUI Framework to display results in a window instead of just console
* Updated several calculations of Soldiers after battle
* Will now add a NPC Civilization called Nomads which will only produce in the case that their are an odd number of Civilizations

## [v1.0.3](https://github.com/cetoh/CivilizaSim/tree/v1.0.3) 
* Implemented Trading Mechanism
* Buildings Implemented (Focusing on Military and Commerce). This includes adding a new CivActions.BUILD.

## [v1.0.4](https://github.com/cetoh/CivilizaSim/tree/v1.0.4) (Latest Release)
* More Buildings Coming!
* GUI Updated to allow for more player control
* Changes to the gameplay to allow for turn by turn control
* Backend framework updates for better support

## [v1.0.5](https://github.com/cetoh/CivilizaSim/tree/v1.0.5) (Bleeding Edge - Experimental Release)
* Working on GUI Graphics
* Even More Buildings!
