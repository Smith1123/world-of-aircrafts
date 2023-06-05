package hu.epam.mentoring.l3.zsf.engine;

import hu.epam.mentoring.l3.zsf.controller.model.Action;
import hu.epam.mentoring.l3.zsf.model.ActionEnum;
import hu.epam.mentoring.l3.zsf.model.Aircraft;
import hu.epam.mentoring.l3.zsf.model.Team;
import hu.epam.mentoring.l3.zsf.model.TeamColor;
import hu.epam.mentoring.l3.zsf.util.GameConstants;
import org.springframework.stereotype.Service;

import java.util.*;

import static hu.epam.mentoring.l3.zsf.util.GameConstants.COORDINATE_SYSTEM_SIZE;

@Service
public class GameService {
    public Map<Action, Boolean> calculateAction(TeamColor teamColor, List<Action> actions) {
        return processAction(getTeam(teamColor), actions);
    }

    private Map<Action, Boolean> processAction(Team team, List<Action> actions) {
        Map<Action, Boolean> result = new HashMap<>();
        validateActions(team, actions);
        actions.forEach(action -> {
            validateAction(team, action);
            result.put(action, makeAction(team, action));
        });

        Game.setTeamCompletedRound(team.getTeamColor());
        Game.logState(team, result);

        if (Game.isRoundComplete()) {
            Game.incrementRound();
        }

        return result;
    }

    private boolean makeAction(Team team, Action action) {
        if (TeamColor.RED.equals(team.getTeamColor()) && !Game.hasTeamRedActionInThisRound()) {
            return false;
        }

        if (TeamColor.BLUE.equals(team.getTeamColor()) && !Game.hasTeamBlueActionInThisRound()) {
            return false;
        }

        return switch (action.actionEnum()) {
            case FLY -> fly(team, action.aircraftPosition(), action.targetPoint());
            case LOCATE_AIRCRAFT ->
                locateAircraft(getEnemyTeamColor(team.getTeamColor()), action.targetPoint());
            case SHOOT -> shoot(getEnemyTeamColor(team.getTeamColor()), action.targetPoint());
        };

    }

    private boolean fly(Team team, Team.Point aircraftPoint, Team.Point targetPoint) {
        getAircraft(team.getAircrafts(), aircraftPoint).setCoordinate(targetPoint);
        return true;
    }

    private boolean locateAircraft(TeamColor targetTeamColor, Team.Point targetPoint) {
        return getTeam(targetTeamColor).getAircrafts().stream().anyMatch(
            aircraft -> aircraft.getCoordinate().equals(targetPoint)
        );
    }

    private boolean shoot(TeamColor targetTeamColor, Team.Point targetPoint) {
        var optionalAircraft =
            getTeam(targetTeamColor).getAircrafts().stream().filter(
                aircraft -> aircraft.getCoordinate().equals(targetPoint)
            ).findAny();

        if (optionalAircraft.isPresent()) {
            optionalAircraft.get().setAlive(false);
            return true;
        }

        return false;
    }

    private void validateActions(Team team, List<Action> actions) {
        if (team.getAircrafts().size() != actions.size()) {
            throw new IllegalArgumentException(
                "Actions size must be " + GameConstants.AIRCRAFT_SIZE + "!"
            );
        }
    }

    private void validateAction(Team team, Action action) {
        validateAircraftCoordinate(action.aircraftPosition());
        validateAircraftExistence(action.aircraftPosition(), team);
        validateAircraftCoordinate(action.targetPoint());

        if (Objects.requireNonNull(action.actionEnum()) == ActionEnum.FLY) {
            validateMove(
                team.getTeamColor(), action.aircraftPosition(), action.targetPoint()
            );
        }
    }

    private void validateAircraftCoordinate(Team.Point point) {
        if (point.x() < 0 || point.x() >= COORDINATE_SYSTEM_SIZE ||
                point.y() < 0 || point.y() >= COORDINATE_SYSTEM_SIZE ||
                point.z() < 0 || point.z() >= COORDINATE_SYSTEM_SIZE) {
            throw new IllegalArgumentException("Coordinate is invalid: " + point);
        }
    }

    private void validateAircraftExistence(Team.Point point, Team team) {
        if (team.getAircrafts().stream().noneMatch(aircraft -> aircraft.getCoordinate().equals(point))) {
            throw new IllegalArgumentException(
                    "Aircraft not exist in " + team.getTeamColor() + " team (" + point + ")"
            );
        }
    }
    private void validateMove(TeamColor teamColor, Team.Point aircraftPosition, Team.Point targetPosition) {
        var enemyTeam = getTeam(getEnemyTeamColor(teamColor));
        var enemyAircraft =
                enemyTeam.getAircrafts().stream().filter(
                        aircraft -> aircraft.getCoordinate().equals(targetPosition)
                ).findAny();
        if (enemyAircraft.isPresent()) {
            throw new IllegalArgumentException(
                "On target point is an enemy aircraft (" + targetPosition + ")"
            );
        }

//      The rule is that aircraft is being able to move just ahead and exactly 1 unit
        if ((teamColor == TeamColor.RED && targetPosition.x() - aircraftPosition.x() != 1 ||
             teamColor == TeamColor.BLUE && aircraftPosition.x() - targetPosition.x() != 1) ^
            (teamColor == TeamColor.RED && targetPosition.y() - aircraftPosition.y() != 1 ||
            teamColor == TeamColor.BLUE && aircraftPosition.y() - targetPosition.y() != 1) ^
            Math.abs(targetPosition.z() - aircraftPosition.z()) != 1
        ) {
            throw new IllegalArgumentException(
                "Can't move to " + targetPosition + "! Your position is " + aircraftPosition
            );
        }
    }

    private Team getTeam(TeamColor teamColor) {
        return switch (teamColor) {
            case RED -> Game.getTeamRed();
            case BLUE -> Game.getTeamBlue();
        };
    }

    private TeamColor getEnemyTeamColor(TeamColor ownTeamColor) {
        return switch (ownTeamColor) {
            case RED -> TeamColor.BLUE;
            case BLUE -> TeamColor.RED;
        };
    }

    private Aircraft getAircraft(List<Aircraft> aircrafts, Team.Point aircraftPoint) {
        var optionalAircraft =
            aircrafts.stream().filter(
                aircraft -> aircraft.getCoordinate().equals(aircraftPoint)).findAny();
        if (optionalAircraft.isEmpty()) {
            throw  new IllegalStateException("Aircraft must be there (" + aircraftPoint + ")!");
        }

        return optionalAircraft.get();
    }
}
