# Vending Machine
Vending Machine TDD Coding Kata

Example TDD project to demonstrate the functionality of a typical Vending Machine driven by JUnit Tests.

##Overview

The goal of this program is to model a vending machine and the state it must maintain during its operation. How exactly the actions on the machine are driven is left intentionally vague and is up to the implementer. The machine works like all vending machines: it takes money then gives you items.

The vending machine accepts money in the form of Ten Pence, Twenty Pence, Fifty Pence and Pound coins.

You must have 3 items that cost £0.60, £1.00, and £1.70.

The user may hit a “coin return” button to get back the money they’ve entered so far.

If you put more money in than the item’s price, you get change back.

The machine can also be turned on and off.

##Specification

The valid set of actions on the vending machine is:
* INSERT MONEY – Ten Pence, Twenty Pence, Fifty Pence, Pound
* COIN RETURN – returns all inserted money
* GET-A, GET-B, GET-C – selects item A (£0.60), B (£1), or C (£1.70)
* TURN-ON, TURN-OFF – turns the machine on and off

The valid set of responses from the vending machine is:
* Ten Pence, Twenty Pence, Fifty Pence, Pound – return coin
* A, B, C – vend item A, B, or C
* On / Off – turn on / off

The vending machine must track the following state:
* Available items – each item has a count, a price, and a selector (A, B or C)
* Available change – # of Ten Pence, Twenty Pence, Fifty Pence, and Pounds available
* Currently inserted money
* Whether the machine is on or off

##Build and Run Instructions

Project is a Java 8 based Maven project using JUnit as a Dependency.

Make sure you have Java 8 and Maven Installed, and that both JAVA_HOME and MAVEN_HOME are on the path.

Project can be built from its root folder by executing the command `mvn clean test`

This will compile the code and run the unit tests, compiled code and unit test results will are available in the /target folder

##Design
The Vending machine provides a main interface class - `VendingMachine`.  This provides the main interface to the vending machine via the following operations

- **`setOn()`** - switch the vending machine on
- **`setOff()`** - switch the vending machine off
- **`isOn()`** - check whether the machine is switched on
- **`getBalance()`** - returns the sum of all coins currently inserted into the vending machine
- **`returnCoins()`** - returns the coins currently inserted, providing the following state conditions are met
  * Vending Machine is ON - throws `VendingMachineOffException` if machine is off
  * Vending Machine has inserted coins - throws `NoCoinsInsertedException` if no coins have been inserted
- **`insertCoin(Coin)`** - insert a coin into the vending machine, providing the following state conditions are met
  * Vending Machine is ON - throws `VendingMachineOffException` if machine is off
  * Coin is a "Valid Coin" - throws `UnknownCoinException` if coin is not recognised
- **`getNumberAvailable(Item item)`** - returns the number available for a given `Item`, providing the following sate conditions are met
  * Vending Machine is ON - throws `VendingMachineOffException` if machine is off
- **`vendItem(Item item)`** attempts to vend the given item, providing the following state conditions are met
  * Vending Machine is ON - throws `VendingMachineOffException` if machine is off
  * There is sufficent stock of the selected item - throws `ItemUnavailableException` if the item is not available
  * At least enough coins have been inserted into the machine - throws `InsertMoreCoinsException` is more coins needed
  
    Once the above conditions have been met, the vending machine will do the following
    * Add the inserted coins into the machines coin store
    * Check for available change (see below for detail)
    * Decrement item availability
    * Return selected item

  * **Check for available change**
  
    The internal **`checkForChange(Item)` method is called which first of all determines whether change is applicable based on the value of the coins inserted and the price of the selected item.
    
    If change is indeed required, then the `ChangeHandler.getChange()` method is called which will attempt to return the demoniations of coins needed to fulfill the giving of change.
    
    Any change (list of coins) returned by the ChangeHandler is set as the available balance in the machine.  From here the coins acn be re-used to purchase another item or returned
  
  The `ChangeHandler` determines whether it is possible based on the coins contained within the machine and the amount of change required
  whether it is actually possible to provide change back to the user.  If this is not possible then a `ChangeUnavailableException` is thrown

The Vending Machine, when initialised has the following default state

- Off
- No inserted Coins
- No Coins already inserted from previous vending activities
- A default number of 10 for each of the 3 items the machine is able to vend

##Notes / Considerations
(1) A command line interface may have also proved useful for end-to-end testing, but this was not asked for and therefore
deemed unnecessary to implement at this stage.

(2) A design decision was made to return the vended item and leave the change as available balance in the machine.  It was considered that
an object containing the Item and the available change could be returned but this would need further requirements clarification.

(3) Although the tests have been developed using JUnit, it was considered to write them as Cucumber based, BDD tests in order to describe
the different scenarios in a more english based aspect. However, given the time, it was decided that Junit Tests would suffice.
  
  e.g. 
  
    Given the vending machine is switched on 
    and I insert a valid coin
    the available coins in the machine are updated




