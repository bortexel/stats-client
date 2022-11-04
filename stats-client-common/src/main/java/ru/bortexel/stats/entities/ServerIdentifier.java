package ru.bortexel.stats.entities;

public record ServerIdentifier(String serverName, int season) {
    @Override
    public String toString() {
        return String.format("%s #%d", this.serverName(), this.season());
    }
}
