# Music Factory Patterns

**Assignment #2 — Factory Method & Abstract Factory**
**Domain chosen:** Music software — Part A builds audio-format players,
Part B builds compatible sets of band instruments.

## Part A — Factory Method

**Problem:** an app needs to play tracks stored in different audio
formats (MP3, WAV), and it should be easy to add more formats later
without touching the code that already plays tracks.

| Role | Class |
|---|---|
| Product | `music.player.AudioPlayer` |
| Concrete Product | `Mp3Player`, `WavPlayer` |
| Creator | `AudioPlayerFactory` (abstract class, declares `createPlayer()` and a template method `playTrack()`) |
| Concrete Creator | `Mp3PlayerFactory`, `WavPlayerFactory` |

The client (`Main`) only calls `factory.playTrack(title)`. It never
constructs `Mp3Player` or `WavPlayer` directly — adding a new format
(say, FLAC) only means adding `FlacPlayer` + `FlacPlayerFactory`, with
zero changes to existing classes (Open/Closed Principle).

## Part B — Abstract Factory

**Problem:** a virtual band needs a *consistent* set of instruments —
an acoustic guitar should never end up paired with a synth keyboard by
mistake. The system needs whole, compatible families of instruments,
not just one instrument at a time.

| Role | Class |
|---|---|
| Abstract Product (Guitar family member) | `music.band.Guitar` |
| Abstract Product (Keyboard family member) | `music.band.Keyboard` |
| Concrete Products — Acoustic family | `AcousticGuitar`, `AcousticPiano` |
| Concrete Products — Electric family | `ElectricGuitar`, `SynthKeyboard` |
| Abstract Factory | `BandInstrumentFactory` |
| Concrete Factories | `AcousticBandFactory`, `ElectricBandFactory` |
| Client | `BandSetupClient` — depends only on `BandInstrumentFactory`, `Guitar`, `Keyboard`; never references a concrete class |

Because `BandSetupClient` never names a concrete product, swapping
`new AcousticBandFactory()` for `new ElectricBandFactory()` changes the
*entire* instrument set consistently, with no risk of mixing families.

## How Part A and Part B relate

Factory Method decides **which single product** to create (one axis of
variation: file format). Abstract Factory decides **which whole family**
of related products to create (one axis of variation: acoustic vs.
electric), guaranteeing every product it returns belongs to the same
family. Part B could be built *from* several Factory Methods internally
(each `createGuitar()` / `createKeyboard()` is itself a small factory
method) — this is the standard relationship between the two patterns.

## Clean Code principles applied

### 1. Meaningful, intention-revealing names
Classes and methods say exactly what they do: `AudioPlayerFactory`,
`playTrack()`, `BandSetupClient`, `rehearse()`. No abbreviations like
`aFact`, `bsc`, `ctx`.

```java
// Before
AudioPlayerFactory f = new Mp3PlayerFactory();
f.pt("Song");

// After
AudioPlayerFactory mp3Factory = new Mp3PlayerFactory();
mp3Factory.playTrack("Neon Skyline (Deluxe Edition)");
```

### 2. Small methods, each doing one thing
`BandSetupClient`'s constructor does not itself decide what "valid"
means — it delegates to `requireFactory()`. `AudioPlayerFactory` splits
validation (`requireNonBlankTitle`) from the actual playback template
(`playTrack`), so each method is readable at a single level of detail.

```java
// Before (one method, two responsibilities)
public BandSetupClient(BandInstrumentFactory factory) {
    if (factory == null) throw new IllegalArgumentException("factory required");
    this.guitar = factory.createGuitar();
    this.keyboard = factory.createKeyboard();
}

// After (validation extracted, see BandSetupClient.java)
public BandSetupClient(BandInstrumentFactory factory) {
    requireFactory(factory);
    this.guitar = factory.createGuitar();
    this.keyboard = factory.createKeyboard();
}
```

### 3. Consistent formatting and small, focused classes
Every concrete product class (`AcousticGuitar`, `ElectricGuitar`,
`Mp3Player`, `WavPlayer`, ...) has exactly one responsibility and one
public method, following the same shape throughout the codebase. No
class mixes creation logic with playback logic, or product logic with
factory-selection logic.

### 4. Validated construction
Both `AudioPlayerFactory.playTrack()` and `BandSetupClient`'s
constructor fail fast with a clear `IllegalArgumentException` message
instead of silently producing a broken state (e.g., a client with a
`null` guitar).

```java
private void requireNonBlankTitle(String trackTitle) {
    if (trackTitle == null || trackTitle.isBlank()) {
        throw new IllegalArgumentException("Track title must not be blank.");
    }
}
```

### 5. No magic numbers or strings
Sample rates and gain levels are named constants
(`DEFAULT_SAMPLE_RATE_HZ`, `DEFAULT_GAIN_LEVEL`) instead of bare
literals scattered through `play()`/`strum()` methods.

```java
// Before
System.out.println("Playing at 44100 Hz: " + trackTitle);

// After
private static final int DEFAULT_SAMPLE_RATE_HZ = 44_100;
...
System.out.printf("Decoding MP3 stream at %d Hz and playing: %s%n",
        DEFAULT_SAMPLE_RATE_HZ, trackTitle);
```

### 6. Open/Closed Principle (bonus, ties Part A and B together)
Adding a new audio format or a new instrument family never requires
editing an existing class — only adding new ones (`FlacPlayer` +
`FlacPlayerFactory`, or `JazzBandFactory` + its products).

## How to run

```bash
javac -d out $(find src -name "*.java")
java -cp out music.Main
```

## Sample output

```
--- Part A: Factory Method ---
Decoding MP3 stream at 44100 Hz and playing: Neon Skyline (Deluxe Edition)
Reading raw PCM data at 48000 Hz and playing: Midnight Static

--- Part B: Abstract Factory ---
Acoustic guitar: warm, resonant strum from the wooden body.
Acoustic piano: hammer strikes the string directly.
Electric guitar: pickups send signal to the amp at gain 7.
Synth keyboard: oscillator generates a waveform electronically.
```

## Commit history plan

1. `Add AudioPlayer product hierarchy and Mp3/Wav factories (Part A)`
2. `Add band instrument family (Guitar/Keyboard) and abstract factories (Part B)`
3. `Add BandSetupClient, Main driver, and README with Clean Code justification`
