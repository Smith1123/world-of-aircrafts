package hu.epam.mentoring.l3.zsf.controller.model;

import hu.epam.mentoring.l3.zsf.model.Team;
import hu.epam.mentoring.l3.zsf.model.TeamColor;

import static hu.epam.mentoring.l3.zsf.util.GameUtil.getTeam;

public final class LocateAircraftAction extends Action {
    public LocateAircraftAction(Team.Point aircraftPosition, Team.Point targetPoint) {
        super(aircraftPosition, targetPoint);
    }

    @Override
    public Class<? extends Action> getType() {
        return LocateAircraftAction.class;
    }

    public boolean locateAircraft(TeamColor targetTeamColor) {
        return getTeam(targetTeamColor).getAircrafts().stream().anyMatch(
            aircraft -> aircraft.getCoordinate().equals(this.getTargetPoint())
        );
    }

}
