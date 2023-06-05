package hu.epam.mentoring.l3.zsf.testutil;

import hu.epam.mentoring.l3.zsf.controller.model.Action;

import java.util.List;

public sealed abstract class ActionTestData permits RedTeamActionTestData, BlueTeamActionTestData {
    private List<Action> actions;

    public abstract void generateActions();

    public List<Action> getActions() {
        return actions;
    }
}
