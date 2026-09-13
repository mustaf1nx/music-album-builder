package music.builder;

import music.model.Album;

/**
 * Declares every construction step common to all album builders.
 * Every setter returns the builder itself (fluent API / method chaining),
 * so a client or a Director can chain calls without intermediate variables.
 */
public interface AlbumBuilder {

    AlbumBuilder title(String title);

    AlbumBuilder artist(String artist);

    AlbumBuilder genre(String genre);

    AlbumBuilder releaseYear(int year);

    AlbumBuilder addTrack(String trackTitle);

    AlbumBuilder producer(String producer);

    AlbumBuilder markExplicit();

    Album build();
}
