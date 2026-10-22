/** Internal statistics snapshot used by the exporter and the facade. */
public record PlaylistSummary(int songCount, int durationSeconds, double averageBpm, double averageBpmJump) { }
