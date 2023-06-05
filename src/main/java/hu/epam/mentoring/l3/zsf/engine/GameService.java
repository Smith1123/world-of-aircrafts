package hu.epam.mentoring.l3.zsf.engine;

import hu.epam.mentoring.l3.zsf.controller.model.Action;
import hu.epam.mentoring.l3.zsf.controller.model.FlyAction;
import hu.epam.mentoring.l3.zsf.controller.model.LocateAircraftAction;
import hu.epam.mentoring.l3.zsf.controller.model.ShootAction;
import hu.epam.mentoring.l3.zsf.model.Aircraft;
import hu.epam.mentoring.l3.zsf.model.Team;
import hu.epam.mentoring.l3.zsf.model.TeamColor;
import hu.epam.mentoring.l3.zsf.util.GameConstants;
import org.springframework.stereotype.Service;

import java.util.*;

import static hu.epam.mentoring.l3.zsf.util.GameConstants.COORDINATE_SYSTEM_SIZE;
import static hu.epam.mentoring.l3.zsf.util.GameUtil.getEnemyTeamColor;
import static hu.epam.mentoring.l3.zsf.util.GameUtil.getTeam;

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
        if (TeamColor.RED.equals(team.getTeamColor()) && Game.hasTeamRedMakeMove()) {
            return false;
        }

        if (TeamColor.BLUE.equals(team.getTeamColor()) && Game.hasTeamBlueMakeMove()) {
            return false;
        }

        return switch (action) {
            case FlyAction flyAction -> flyAction.fly(team);
            case LocateAircraftAction locateAircraftAction ->
                locateAircraftAction.locateAircraft(getEnemyTeamColor(team.getTeamColor()));
            case ShootAction shootAction ->
                shootAction.shoot(getEnemyTeamColor(team.getTeamColor()));
        };

    }

    private void validateActions(Team team, List<Action> actions) {
        long aircraftSize = team.getAircrafts().stream().filter(Aircraft::isAlive).count();
        if (aircraftSize != actions.size()) {
            throw new IllegalArgumentException(
                "Actions size must be " + aircraftSize + "!"
            );
        }
    }

    private void validateAction(Team team, Action action) {
        validateAircraftCoordinate(action.getAircraftPosition());
        validateAircraftExistence(action.getAircraftPosition(), team);
        validateAircraftCoordinate(action.getTargetPoint());

        if (Objects.requireNonNull(action) instanceof FlyAction flyAction) {
            validateMove(
                team.getTeamColor(), flyAction.getAircraftPosition(), flyAction.getTargetPoint()
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
        if (team.getAircrafts().stream().noneMatch(
            aircraft -> aircraft.getCoordinate().equals(point) && aircraft.isAlive()
        )) {
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
}
