![Java](https://img.shields.io/badge/Java-8.0-orange.svg)
![Neuroph](https://img.shields.io/badge/Neuroph-2.94-brightgreen.svg)

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

### PongNNet

#### Pongnnet package:

This package hosts the pong game, plus some neural network components.

##### PongNNet (Main class)

Starts the process of the game. Updates most of the program every 2 milliseconds, with the help of a swing timer.

##### Paddle

Handles the paddle movement and rendering.

##### Ball

The ball class contains the ball movement, collision, spawning, rendering, in addition to the testData output method.

##### Rendering

Class to help render different objects.

#### NeuralNetwork package:

This package contains the Neural Network part of the code. For the most part.

##### NN

This class contains the neural network.

##### MaxMinNormalizer.java

The only job of this class is to normalize the output of the pong game. This is so that the neural network has a easier time learning.

### Versioning

Current version: 1.1.3

We use [SemVer](http://semver.org/) for versioning. 

<br>

### The Neural Network

#### DataSet

The dataset is comprised of tree different values. These values are: 
	
1. Paddle1.y - (Paddle 1's y coordinates).
0. Ball.y - (The ball's y coordinates).
0. Score - (How many times the ball hit paddle1).

These values need to be normalized to have an easier time, working with them. The values varies between ≈-1 to 550.

##### Normalization of data

###### Paddle.y Min/Max

The paddle is straight forward (top/bottom).

	min: 170
	max: 550

###### Ball.y Min/Max

The ball sometimes has a negative value.

The lowest value seems to often be -1. But once it was -23.

	min: -1(-23)
	max: 676

###### Score Min/Max

There are no max value for the score variable.

	min: 0
	max: no max (Find solution)


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
We appreciate help bout on coding and interface as well as documentation. 

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

<br>

## Acknowledgements

* Hat tip to anyone who's code was used.
* Neuroph for having an open source neural network.
* etc
