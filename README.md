# Music Album Builder

**Assignment #1 — Builder Pattern**
**Product chosen:** `Album` — a music album that requires step-by-step
assembly (title, artist, genre, track list, producer, explicit flag) and
benefits from reusable configurations (e.g. a Deluxe Edition vs. a Radio
Single Edition of the same underlying song).

## 1. Why Builder fits this product

An `Album` has several optional and required parts (tracks, producer,
genre, explicit flag) that are set incrementally. Using a single
telescoping constructor for all of these would be unreadable and error
prone. The Builder pattern lets the fields be set one at a time, through
a fluent, self-documenting API, and only turns the in-progress state into
a real `Album` once everything required is present.

## 2. Components

| Component | Class | Responsibility |
|---|---|---|
| Product | `music.model.Album` | The finished, immutable album. |
| Builder | `music.builder.AlbumBuilder` | Interface declaring every construction step. |
| ConcreteBuilder | `music.builder.StudioAlbumBuilder` | Implements the steps, validates state, produces the `Album`. |
| Director | `music.builder.AlbumDirector` | Knows two reusable recipes: Radio Single Edition and Deluxe Edition. |
| Client | `music.Main` | Uses the Director for a known recipe, and drives the builder directly for a bespoke album. |

## 3. Clean Code principles applied

### 3.1 Meaningful, intention-revealing names
Methods and classes are named after what they do, not how: `AlbumBuilder`,
`markExplicit()`, `requireAtLeastOneTrack()`. No `a`, `tmp`, `flag1`, etc.

```java
// Before (cryptic)
public AlbumBuilder e() { this.x = true; return this; }

// After (intention-revealing)
public AlbumBuilder markExplicit() { this.explicit = true; return this; }
```

### 3.2 Small methods that do one thing
`validateBeforeBuild()` in `StudioAlbumBuilder` does not itself check
conditions — it delegates each check to a single-purpose method
(`requireTitle`, `requireArtist`, `requireValidReleaseYear`,
`requireAtLeastOneTrack`), so each method is a few lines and easy to name.

```java
// Before (one big method mixing four checks)
private void validateBeforeBuild() {
    if (title == null || title.isBlank()) throw new IllegalStateException("title required");
    if (artist == null || artist.isBlank()) throw new IllegalStateException("artist required");
    if (releaseYear < 1900) throw new IllegalStateException("bad year");
    if (trackTitles.size() < 1) throw new IllegalStateException("need a track");
}

// After (delegates to single-purpose methods, see StudioAlbumBuilder.java)
private void validateBeforeBuild() {
    requireTitle();
    requireArtist();
    requireValidReleaseYear();
    requireAtLeastOneTrack();
}
```

### 3.3 One level of abstraction per function
`Album.toString()` stays at a high level (header + track list) instead of
mixing string-formatting details with the overall structure; the low-level
formatting lives in `formatHeader()` / `formatTrackList()` /
`formatTrackLine()`.

```java
// Before (mixes "what" and "how" in one function)
public String toString() {
    String s = title + " by " + artist + "\n";
    for (int i = 0; i < trackTitles.size(); i++) {
        s += "  " + (i + 1) + ". " + trackTitles.get(i) + "\n";
    }
    return s;
}

// After (each helper stays at one level of detail; see Album.java)
public String toString() {
    return formatHeader() + formatTrackList();
}
```

### 3.4 No magic numbers or strings
Constants such as `MIN_RELEASE_YEAR`, `MIN_TRACK_COUNT`, `DEFAULT_GENRE`
and `DEFAULT_PRODUCER` replace inline literals, so their meaning is named
once and reused.

```java
// Before
if (releaseYear < 1900) { throw new IllegalStateException("Release year must be 1900 or later."); }

// After
private static final int MIN_RELEASE_YEAR = 1900;
...
if (releaseYear < MIN_RELEASE_YEAR) {
    throw new IllegalStateException("Release year must be " + MIN_RELEASE_YEAR + " or later.");
}
```

### 3.5 Validated construction / fail fast
`build()` never returns a half-formed `Album`. It calls
`validateBeforeBuild()` first and throws a clear `IllegalStateException`
naming exactly what is missing, instead of producing a broken object or
returning a null/error code.

```java
@Override
public Album build() {
    validateBeforeBuild();
    return new Album(title, artist, genre, releaseYear, trackTitles, producer, explicit);
}
```

### 3.6 Avoiding long parameter lists (bonus)
Instead of one constructor taking seven positional arguments (easy to
mis-order), each field is set through a named, chainable method — the
classic motivation for Builder from a Clean Code argument-discipline
standpoint.

## 4. How to run

```bash
javac -d out $(find src -name "*.java")
java -cp out music.Main
```

## 5. Sample output

```
Neon Skyline (Deluxe Edition) by Aria Waves (2024, Pop) - Producer: M. Torres
  1. Intro
  2. Neon Skyline
  3. Neon Skyline (Acoustic Version)
  4. Bonus Track

Midnight Static by The Faraday Cage (2023, Alt Rock) - Producer: J. Kessler [Explicit]
  1. Wavelength
  2. Copper Wire
  3. Silent Circuit

Validation works as intended -> Artist name is required before calling build().
```

## 6. Commit history plan

1. `Add Album product and AlbumBuilder interface`
2. `Implement StudioAlbumBuilder with validation`
3. `Add AlbumDirector and Main client demo`
4. `Add README with Clean Code justification`
