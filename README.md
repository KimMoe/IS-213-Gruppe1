![Java](https://img.shields.io/badge/Java-8.0-orange.svg)
![Neuroph](https://img.shields.io/badge/Neuroph-2.94-brightgreen.svg)
![License](https://img.shields.io/badge/Lisence-MIT-blue.svg)
![NN](https://img.shields.io/badge/Neural%20Network-Operational-green.svg)
![Pong](https://img.shields.io/badge/Pong-Operational-green.svg)

# Pong with Neural Network

## Content:

1. [The Software](#the-software)
0. [Getting Started](#getting-started)
0. [Tests](#tests)
0. [Deployment](#deployment)
0. [Built with](#built-with)
0. [Contributing](#contributing)
0. [Developers](#developers)
0. [License](#license)
0. [Acknowledgements](#acknowledgements)

This is a university project were we try to get a better understanding of machine learning and neural networks.
We have made a basic pong game in Java. We will try and use a neural network to learn and play the game.

<br>

## The software

Short description of the software.

More info [here](https://github.com/KimMoe/IS-213-Gruppe1/wiki)

###### Pong

The first part of the software, is a pong game. The pong game is just a basic implementation of the classic pong game. Where you bounce a ball with a paddle from left to right, right to left.

![Pong with Neural Network](https://media.discordapp.net/attachments/319884106798202890/441899751336837130/unknown.png?width=523&height=527)

Pong

###### Neural Network

The second part is a implementation of Neuroph's multilayer perceptron neural network. This neural network, learns and plays the game at a almost too high rate. 

###### Live View

The third and final part of the software handels the second frame. The live view frame. It's implementet so that the user can see what inputs the neural network get, and what outputs it generates. Albeit it's might be a bit hard to read, because it updates every 15 millisecond...

![Live view](https://media.discordapp.net/attachments/319884106798202890/443681998653882378/unknown.png?width=840&height=585)

Live view

#### Class description

Description of the different classes used by the program.

| Class | Package | Description |
| ----- | ------- | ----------- |
| Pong  | Pongnnreborn | Main class, handels the pong aspect of the program. |
| Paddle | Pongnnreborn | Creates "Paddle" objects used in the pong game. |
| Ball | Pongnnreborn | Creates a "Ball" object used in the game. In addition to gathering data for NN. |
| Rendering | Pongnnreborn | Renders the Pong frame. |
| Neural_Network | Neural_Network | Neural Network class. Creates, learns, and uses the neural network to play pong. |
| Neural_View | Neural_Network | Handles the Neural network live view frame. |
| Neural_View_Rendering | Neural_Network | Renders the live view frame. |

#### Architecture 

For more information about the architecture of the software, check out the [wiki](https://github.com/KimMoe/IS-213-Gruppe1/wiki/Architecture). 

### Versioning

Current version: 2.1.2

Checkout the wiki for changelogs: [Wiki](https://github.com/KimMoe/IS-213-Gruppe1/wiki/Versions)

We use [SemVer](http://semver.org/) for versioning. 

### The Neural Network

#### Data

Inputs:

1. Paddle1.y: Y cordinates of the paddle.
0. Ball.y: Y cordinates of the ball.
0. Desired Output: 0, 0.5, or 1. Created by checking if the ball is above, below, or at the paddle position. 

Outputs:

1. Output 1: Number beetwen 0-1. Uses to see if the neural network needs to go up or down.

<br>


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things do you need to hava ready before using the software, and how to install them.

### Installation

A step by step series of examples that tell you have to get a development enviroment running

1. Download the repository.

2. Download the neuroph 2.94 library from [here](http://neuroph.sourceforge.net/download.html).

3. Open the PongNnReborn project in your IDE of choice (we used Netbeans). 

4. Apply the needed libraries from the Neuroph download.

   Neuroph (Needed):

   * Neuroph-adapters
   * Neuroph-contrib
   * Neuroph-core
   * Neuroph-ocr

   Neuroph (Optional):

   * Neuroph-samples
   * Neuroph-samples-Javadoc
   * Neuroph-adapters-Javadoc
   * Neuroph-contrib-Javadoc
   * Neuroph-core-Javadoc
   * Neuroph-ocr-Javadoc

   Neuroph dependencies (Needed):

   * Ajt
   * Java-ML
   * Logback-core
   * Sfl4-API
   * Sfl4-NOP
   * Weka

5. Test the software by compiling it with the IDE.

6. Use 1-4 and space to change the setting in the pong menu.

7. Start the game, and enjoy a neural network playing agains a BOT, or a human.

8. You can now edit/change the program has much as you would like.

9. Check out the contribution page for more information on cotribution. 

<br>


## Tests

We haven't had time to implement the tests we wanted to. 

Tests we wanted and should have had:

* Unit testing
* Syntax testing/Coding style

``` ### Running tests

### Break down into end to end tests

Explain what these tests test and why

``java
Give an example
``

### And coding style tests

Explain what these tests test and why

``java
Give an example
``
```

<br>


## Deployment

Add additional notes about how to deploy this on a live system

<br>

## Built With

NetBeans - [Webpage](https://netbeans.org/)

Neuroph 2.94 - [Webpage](http://neuroph.sourceforge.net/)

Java 8 - [Webpage](https://www.oracle.com/index.html)

<br>

## Contributing

[Contributing](https://github.com/KimMoe/IS-213-Gruppe1/blob/master/CONTRIBUTING.md)

<br>


## Developers

Moe, Kim Arild

Sakseid, Vegar

Røren, Tønnes Tobias Pedersen

_Your name here_ 

<br>


## License

Collection of licenses applicable to the project.

### Our

[Licensed under MIT license.](https://github.com/KimMoe/IS-213-Gruppe1/blob/master/LICENSE)

### 3rd Party:

Neuroph - [Apache 2.0 License](https://www.apache.org/licenses/LICENSE-2.0.html)

Neuroph uses: 

* Abeel Java Toolkit (AJT) [GNU Library or Lesser General Public License version 3.0 (LGPLv3)](https://opensource.org/licenses/lgpl-3.0.html)

* Java Machine Learning Library (Java-ML) [GNU Library or Lesser General Public License version 3.0 (LGPLv3)](https://opensource.org/licenses/lgpl-3.0.html)

* Weka 3: Data Mining Software [GNU Library or Lesser General Public License version 3.0 (LGPLv3)](https://opensource.org/licenses/lgpl-3.0.html)

* Logback - [Eclipse Public License v1.0](https://www.eclipse.org/legal/epl-v10.html)

<br>


## Acknowledgements

* Hat tip to anyone who's code was used. 🎩
* Neuroph for having an open source neural network. 🤖
* Lisence creators, for letting everyone use thier liceses free of charge. 📃
