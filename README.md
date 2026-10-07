# Assignment 3 — Bridge Pattern

Name: Azamat Mustafin
Topic: Music Playback System

This project is a simple Music Playback System implemented in Java using the Bridge design pattern.

The project separates music player types from audio output implementations, so they can be changed independently.

## Structure

- `MusicPlayer` — abstraction
- `SingleTrackPlayer` — refined abstraction
- `PlaylistPlayer` — refined abstraction
- `AudioOutput` — implementor
- `SpeakersOutput` — concrete implementor
- `HeadphonesOutput` — concrete implementor
- `Main` — demonstrates the Bridge pattern

## Run

Compile:

```bash
javac *.java
```

Run:

```bash
java Main
```