# How to do Dependency Injection


* Deciders: Menno Morsink
* Date: 2018-05-22

## Considered Options

* Dagger2
* Do it yourself DI

## Decision Outcome

Chosen option: "Dagger2", because .

## Pros and Cons of the Options

### Dagger2

* Good, because you write less boilerplate code
* Good, because saves time
* Good, because works compile time, no reflection
* Good, because maintained by Google
* Good, because it is tailored for Android (hard to inject because no constructors)
* Bad, because you need to learn how it works

### Do it yourself

* Good, because no need to learn an extra framework
* Bad, because you write all the boilerplate code yourself

## Links

* [Dagger2 Android](https://google.github.io/dagger/)

