# Vending Machine
Vending Machine TDD Coding Kata

#OVERVIEW

The goal of this program is to model a vending machine and the state it must maintain during its operation. How exactly the actions on the machine are driven is left intentionally vague and is up to the implementer. The machine works like all vending machines: it takes money then gives you items.

The vending machine accepts money in the form of Ten Pence, Twenty Pence, Fifty Pence and Pound coins.

You must have 3 items that cost £0.60, £1.00, and £1.70.

The user may hit a “coin return” button to get back the money they’ve entered so far.

If you put more money in than the item’s price, you get change back.

The machine can also be turned on and off.

#SPECIFICATION

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
