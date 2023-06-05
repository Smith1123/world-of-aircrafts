package hu.epam.mentoring.l3.zsf.util;

import hu.epam.mentoring.l3.zsf.engine.Game;
import hu.epam.mentoring.l3.zsf.model.Aircraft;
import hu.epam.mentoring.l3.zsf.model.Team;
import hu.epam.mentoring.l3.zsf.model.TeamColor;

import java.util.ArrayList;
import java.util.List;

import static hu.epam.mentoring.l3.zsf.util.GameConstants.COORDINATE_SYSTEM_SIZE;

public class GameUtil {
    public static List<Aircraft> initalizeAircraft(int size, TeamColor teamColor) {
        if (size > COORDINATE_SYSTEM_SIZE) {
            throw new IllegalStateException(
                "Team size (" + size + ") is bigger then coordinae system (" + COORDINATE_SYSTEM_SIZE + ")!"
            );
        }
        List<Aircraft> result = new ArrayList<>(size);

        int defaultX = calculateX(size);
        for (int i = 0; i < size; i++) {
            result.add(
                new Aircraft(
                    new Team.Point(defaultX + i, calculateY(teamColor), COORDINATE_SYSTEM_SIZE/2)
                )
            );
        }

        return result;
    }

    public static Aircraft getAircraft(List<Aircraft> aircrafts, Team.Point aircraftPoint) {
        var optionalAircraft =
                aircrafts.stream().filter(
                        aircraft -> aircraft.getCoordinate().equals(aircraftPoint)).findAny();
        if (optionalAircraft.isEmpty()) {
            throw  new IllegalStateException("Aircraft must be there (" + aircraftPoint + ")!");
        }

        return optionalAircraft.get();
    }

    public static Team getTeam(TeamColor teamColor) {
        return switch (teamColor) {
            case RED -> Game.getTeamRed();
            case BLUE -> Game.getTeamBlue();
        };
    }

    public static TeamColor getEnemyTeamColor(TeamColor ownTeamColor) {
        return switch (ownTeamColor) {
            case RED -> TeamColor.BLUE;
            case BLUE -> TeamColor.RED;
        };
    }

    private static int calculateX(int size) {
        return (COORDINATE_SYSTEM_SIZE - size)/2;
    }

    private static int calculateY(TeamColor teamColor) {
        if (TeamColor.RED == teamColor) {
            return 0;
        }

        if (TeamColor.BLUE == teamColor) {
            return COORDINATE_SYSTEM_SIZE - 1;
        }

        throw new UnsupportedOperationException(teamColor + " team hasn't been implemented yet!");
    }
}
