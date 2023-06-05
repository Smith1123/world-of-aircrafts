package hu.epam.mentoring.l3.zsf.controller.model;

import hu.epam.mentoring.l3.zsf.model.Team;

import java.util.Objects;

public sealed abstract class Action permits FlyAction, LocateAircraftAction, ShootAction {
    private Team.Point aircraftPosition;

    private Team.Point targetPoint;

    public Action(Team.Point aircraftPosition, Team.Point targetPoint) {
        Objects.requireNonNull(aircraftPosition);
        Objects.requireNonNull(targetPoint);
        this.aircraftPosition = aircraftPosition;
        this.targetPoint = targetPoint;
    }

    public Team.Point getAircraftPosition() {
        return aircraftPosition;
    }

    public Team.Point getTargetPoint() {
        return targetPoint;
    }

    public abstract Class<? extends Action> getType();
}