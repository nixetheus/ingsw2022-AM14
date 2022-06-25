# ingsw2022-AM14
Project for Software Engineering Course - Polimi

- Dario Amadori ([@darioamadori](https://github.com/darioamadori))<br>dario.amadori@mail.polimi.it

- Alessandro Arbasino ([@AlessandroArbasino](https://github.com/AlessandroArbasino))<br>alessandro.arbasino@mail.polimi.it

- Luca Cassenti ([@nixetheus](https://github.com/nixetheus))<br>luca.cassenti@mail.polimi.it

| Functionality        |  State  |
|:---------------------|:-------:|
| Basic rules          | 🟢 |
| Complete rules       | 🟢 |
| Socket               | 🟢 |
| GUI                  | 🟢 |
| CLI                  | 🟢 |
| FA: 4 Players Mode   | 🟢 |
| FA: 12 Characters    | 🟢 |

🔴 Not started
🟡 In progress
🟢 Complete

### TEST COVERAGE

| Functionality        |  Class%  |  Method%  |   Line%   |
|:---------------------|:--------:|:---------:|:---------:|
| Controller           |100%      |84%        |77%        |
| Model                |100%      |90%        |91%        |

![Test Coverage - Image](https://github.com/nixetheus/ingsw2022-AM14/blob/main/deliverables/coverage.png)

### HOW TO RUN THE GAME

<p align="justify">To run the game simply download the jar file in the deliverables folder and run it from a command prompt or terminal.
Then select a mode between 1 (Server), 2 (CLI) or 3 (GUI) and, for views, decide whether or not to change the default configurations before starting. Please note that only one server can run at a time and that the login phases <b>MUST</b> proceed in order: so you need to first start the server, than connect the first user, insert the game paramters from the first user's prompt and then connect in order the other players.
You can't have more clients login at the same time.</p>

### USEFUL INFORMATION

- <p align="justify">To play a character card from a GUI client select the desired card by clicking on its image, you can then press all the objects on the game screen to connect them to the character card you want to play. When all is set simply click on the PLAY button to play the card. While a character card is selected you cannot do any other move. To unselect a character card, simply click on it again and it should unselect.</p>

- <p align="justify">Two players cannot have the same username. The GUI promprt for a username will not move forward until a different username is given.</p>
