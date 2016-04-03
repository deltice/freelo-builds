# freelo-builds

Executable file available at freelo.tech

Java desktop program that helps users in League of Legends determine what item to build next using a database of winrates by situtation (determined by current user item build and enemy item builds).

When program is running: press "[" if user is on blue side, "]" if user is on red side. Hold tab and press "\" to have program tell you what to build next using text-to-speech.

A database was constructed by analyzing data from many past games using the Developer API from Riot Games. The database contains situations and build paths sorted by winrate for each situation, where a situation is determined from a mathematical function incorporating the user's current item builds and the enemy's item builds to account for dynamic item build paths. When the user inputs a request for an item by holding tab and pressing "\", the program takes a screenshot of the user's scoreboard. Using image recognition software, the program determines the item builds of everyone in the game to calculate the situation that the user is in. It then searches the database for the closest situation and the best build path for the user given the situation. The program then uses text-to-speech to say the name of the best item to build next so the user never has to divert attention away from the game to determine what to build next.

Currently only works for 1920x1080 resolution.

Dependencies: json-simple-1.1.1, FreeTTS 1.2, jnativehoook, Java Advanced Imaging 1.1.2
