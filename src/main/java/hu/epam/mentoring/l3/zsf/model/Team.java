package hu.epam.mentoring.l3.zsf.model;

import hu.epam.mentoring.l3.zsf.util.GameUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static hu.epam.mentoring.l3.zsf.util.GameConstants.AIRCRAFT_SIZE;

public class Team {
    public record Point(int x, int y ,int z) {}
    private final TeamColor teamColor;

    private final List<Aircraft> aircrafts;

    public Team(TeamColor teamColor) {
        this.teamColor = teamColor;
        aircrafts =
            Collections.unmodifiableList(
                Objects.requireNonNull(GameUtil.initalizeAircraft(AIRCRAFT_SIZE, teamColor))
            );
        System.out.println("Team " + teamColor + " has been initalized:");
        aircrafts.forEach(aircraft -> System.out.println(aircraft.getCoordinate()));
    }

    public TeamColor getTeamColor() {
        return teamColor;
    }

    public List<Aircraft> getAircrafts() {
        return aircrafts;
    }
}
