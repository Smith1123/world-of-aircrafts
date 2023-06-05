package hu.epam.mentoring.l3.zsf.controller.model;

import hu.epam.mentoring.l3.zsf.model.Team;
import hu.epam.mentoring.l3.zsf.model.TeamColor;

import static hu.epam.mentoring.l3.zsf.util.GameUtil.getTeam;

public final class ShootAction extends Action {
    public ShootAction(Team.Point aircraftPosition, Team.Point targetPoint) {
        super(aircraftPosition, targetPoint);
    }

    @Override
    public Class<? extends Action> getType() {
        return ShootAction.class;
    }

    public boolean shoot(TeamColor targetTeamColor) {
        var optionalAircraft =
            getTeam(targetTeamColor).getAircrafts().stream().filter(
                aircraft -> aircraft.getCoordinate().equals(this.getTargetPoint())
            ).findAny();

        if (optionalAircraft.isPresent() && optionalAircraft.get().isAlive()) {
            optionalAircraft.get().setAlive(false);
            return true;
        }

        return false;
    }
}
