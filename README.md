![Java](https://img.shields.io/badge/Java-8.0-orange.svg)
![Neuroph](https://img.shields.io/badge/Neuroph-2.94-brightgreen.svg)
![license](https://img.shields.io/github/license/mashape/apistatus.svg)(https://github.com/KimMoe/IS-213-Gruppe1)
![NN](https://img.shields.io/badge/Neural%20Network-YES-red.svg)
![Pong](https://img.shields.io/badge/Pong-YES-red.svg)

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

The main components are the pong game, and the neural network. Where the pong game, is just a simple game with few rules. And the neural network is made with the Neuroph framework.

#### Live View of Neural Network

Pong with neural network

![Pong with Neural Network](https://media.discordapp.net/attachments/319884106798202890/441899751336837130/unknown.png?width=523&height=527)

Live view of neural network.

![Live view](https://media.discordapp.net/attachments/319884106798202890/443681998653882378/unknown.png?width=840&height=585)

### PongNnReborn - (the third version).

#### Pongnnreborn package:

This package hosts the pong game, plus some neural network components.

##### Pong (Main class)

Starts the process of the game. Updates most of the program every 2 milliseconds, with the help of a swing timer.

##### Paddle

Handles the paddle movement and rendering.

##### Ball

The ball class contains the ball movement, collision, spawning, rendering, in addition to the testData output method.

##### Rendering

Class to help render different objects.


#### NeuralNetwork package:

This package contains the Neural Network part of the code. For the most part.

##### Neural_Network

This class contains the neural network.

##### Neural_View

Contains the extra frame, with information about the neural network.

##### Neural_View_Rendering

Renders the content of the second frame from Neural_View.


### Versioning

Current version: 2.0.1

Checkout the wiki for changelogs: [Wiki](https://github.com/KimMoe/IS-213-Gruppe1/wiki/Versions)

We use [SemVer](http://semver.org/) for versioning. 

<br>


### The Neural Network

#### DataSet

The dataset is comprised of tree different values. These values are: 
	
1. Paddle1.y - (Paddle 1's y coordinates).
0. Ball.y - (The ball's y coordinates).
0. Desired Output (if the ball is above or below the paddle).

<br>

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```java
Give examples
```

### Installing

A step by step series of examples that tell you have to get a development env running

Say what the step will be

```java
Give the example
```

And repeat

```java
until finished
```

End with an example of getting some data out of the system or using it for a little demo

<br>

## Tests

### Running tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```java
Give an example
```

### And coding style tests

Explain what these tests test and why

```java
Give an example
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

If you want to contribute to a project and make it better, your help is very welcome. 
We appreciate help on coding, interface as well as documentation. 

### How to make a pull request and push

- Create a personal fork of the project on Github.
- Clone the fork on your local machine. Your remote repo on Github is called `origin`.
- Add the original repository as a remote called `upstream`.
- If you created your fork a while ago be sure to pull upstream changes into your local repository.
- Create a new branch to work on! Branch from `develop`.
- Implement/fix your feature, comment your code.
- Write or adapt tests as needed.
- Add or change the documentation as needed.
- Push your branch to your fork on Github, the remote `origin`.
- From your fork open a pull request in the correct branch. Target the project's `develop`.
- Once the pull request is approved and merged you can pull the changes from `upstream` to your local repo and delete
your extra branch(es).
<br>

## Developers

Moe, Kim Arild

Sakseid, Vegard

Røren, Tønnes Tobias Pedersen

<br>

## License

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

* Hat tip to anyone who's code was used.
* Neuroph for having an open source neural network.
* etc
