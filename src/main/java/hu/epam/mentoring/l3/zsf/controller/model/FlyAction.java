package hu.epam.mentoring.l3.zsf.controller.model;

import hu.epam.mentoring.l3.zsf.model.Aircraft;
import hu.epam.mentoring.l3.zsf.model.Team;
import hu.epam.mentoring.l3.zsf.util.GameUtil;

public final class FlyAction extends Action {
    public FlyAction(Team.Point aircraftPosition, Team.Point targetPoint) {
        super(aircraftPosition, targetPoint);
    }

    @Override
    public Class<? extends Action> getType() {
        return FlyAction.class;
    }

    public boolean fly(Team team) {
        GameUtil.getAircraft(
            team.getAircrafts(), this.getAircraftPosition()).setCoordinate(this.getTargetPoint()
        );
        return true;
    }
}
