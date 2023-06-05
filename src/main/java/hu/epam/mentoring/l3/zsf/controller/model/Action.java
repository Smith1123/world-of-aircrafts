package hu.epam.mentoring.l3.zsf.controller.model;

import hu.epam.mentoring.l3.zsf.model.ActionEnum;
import hu.epam.mentoring.l3.zsf.model.Team;

import java.util.Objects;

public record Action(Team.Point aircraftPosition, ActionEnum actionEnum, Team.Point targetPoint) {
    public Action {
        Objects.requireNonNull(aircraftPosition);
        Objects.requireNonNull(actionEnum);
        Objects.requireNonNull(targetPoint);
    }
}