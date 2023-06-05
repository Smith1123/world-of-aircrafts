package hu.epam.mentoring.l3.zsf.model;

import java.util.Objects;

public class Aircraft {
    private Team.Point coordinate;

    private boolean isAlive;

    public Aircraft(Team.Point coordinate) {
        this.coordinate = coordinate;
        this.isAlive = true;
    }

    public Team.Point getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Team.Point coordinate) {
        this.coordinate = coordinate;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aircraft aircraft = (Aircraft) o;
        return Objects.equals(coordinate, aircraft.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate);
    }
}
