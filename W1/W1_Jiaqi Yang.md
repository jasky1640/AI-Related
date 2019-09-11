### Written Homework 1 
#### Jiaqi Yang 
#### Due September 17th, Tuesday

---
##### Problem 1

---
##### Problem 2
 | Agent Type | Performance Measure | Environment | Actuators | Sensors |
 | :-: | :-: | :-: | :-: | :-: |
 | Shopping for used AI books on the Internet | Price, Book Quality (Customer Rate, Book Condition) | Websites for Book Rental | Programs that place the book rental order in the websites | Programs that collect the price, customer rates, and other relevant information from the book rental websites | 
 | Bidding on an item at an auction | Purchased Price and Revenue (Compared to market value) if win a bid, Potential Gain/Loss (Compared to market value) if lose a bid, Hit/Miss Rate (if a bid is profittable in the final bidding price, do we get the item?) | Websites for Auction, Other Online Bidders if the auction is online; Auctioneers, Other Bidders, Auction Hall if the auction happens in the real life | Speaker/Display/Jointed Arm and Hand to announce the bidding price, Programs that enter bidding price in the website | Perception of Voice or Display of bidding price by other bidders (Voice/Image Sensor or Programs that monitor bids) | 

For the scenerio of shopping for used AI books on the Internet, the environment (Websites for Book Rental) is represented as a HashMap, whose keys are names of the websites in the form of String and corresponding values are HashMaps (Whose keys are names of the books in the form of String, and corresponding values are Books. Book is a class that contains all the information about the book, including price, customer rate, and book condition.) The performance could be evaluated, based on all the stored information, by modeling all the factors of rental book quality into a score. The score will provide insight for the performance evaluation of program output.

For the scenerio of bidding on an item at an aution, the environment is represented as a HashMap, whose keys are the names/ids of the bidders and the corresponding values are Bid. Bid is a class that contains the current highest bidding price, a list of bidding history, and the bids' time. The performance could be evaluated, based on the final bidding price and market price/profittable price of the item, that 1) if a bid is won, the AI's price is higher or lower compared to the market price/profittable price, 2) if a bid is not won, if the final price is higher or lower compared to the market price/profittable price, 3) if the AI participates in multiple auditions, what is the rate of hit (bid won when the final price is profittable) or the rate of miss (bid not won when the final price is profittable).

---
##### Problem 3
 
 
---
##### Problem 4
 
 
---
##### Problem 5
 
 
---
##### Problem 6
 
 
---
##### Problem 7
 
 
---
##### Problem 8
 
 
---
##### Problem 9
 
 
---
##### Problem 10
 
 
