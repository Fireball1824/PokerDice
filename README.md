Poker Dice is a turn-based Android game built with Jetpack Compose and Kotlin, where two players (Human vs Human or Human vs AI) compete by rolling and holding dice to achieve the best poker-style hand.

The game simulates classic poker hand rankings using dice instead of cards, combining strategy, randomness, and decision-making.

Features

Game modes:

- Human vs Human

- Human vs AI

Dice mechanics:

- Roll up to 3 times per turn

- Select and hold dice between rolls

- Dynamic hand evaluation based on poker rules

AI opponent:

- Basic decision-making for holding dice

- Simulated turn-by-turn actions

- Roll and hold visualization

Turn-based gameplay:

- Alternating player turns

- Round system with multiple rounds per match

- Round result screen and match summary

Statistics tracking:

- Games played

- Games won/lost (AI)

- Persistent storage using DataStore

Modern UI:

- Built entirely with Jetpack Compose

- Themed UI using Material 3

- Clean and responsive layouts

Loading and transitions:

- Loading states between game phases

- Smooth transitions between turns, rounds, and match end

- AI action simulation with delays

Architecture:

- MVVM (Model-View-ViewModel)

- UI built with Jetpack Compose

- State management using StateFlow

- Game logic separated into a GameService

- Persistent storage using DataStore

- Clean separation between:

    - Domain layer

    - UI layer

    - Data layer

Technologies:

- Kotlin

- Jetpack Compose

- Android ViewModel

- Coroutines & Flow

- DataStore

- Material 3