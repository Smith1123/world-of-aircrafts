package hu.epam.mentoring.l3.zsf.engine;

import hu.epam.mentoring.l3.zsf.controller.model.Action;
import hu.epam.mentoring.l3.zsf.model.Team;
import hu.epam.mentoring.l3.zsf.model.TeamColor;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static hu.epam.mentoring.l3.zsf.util.GameConstants.TOTAL_ROUNDS;

public class Game implements Runnable {
    private static final String ROUND_STATE_LOG_TEMPLATE = """
            {0}. round results:
            Team {1} actions are:
            {2}
            """;
    private static final String ACTION_TEMPLATE = """
            Action: {0}
            Aircraft: {1}
            Target: {2}
            Result: {3}
            """;
    private static Team teamRed;

    private static Team teamBlue;

    private static boolean hasTeamRedMakeMove;

    private static boolean hasTeamBlueMakeMove;

    private static Map<TeamColor, String> teamRoundLog;

    private static volatile int currentRound;

    static {
        initialize();
    }

    private static void initialize() {
        teamRed = new Team(TeamColor.RED);
        teamBlue = new Team(TeamColor.BLUE);
        currentRound = 1;
        teamRoundLog = new HashMap<>();
    }

    public static boolean isRoundComplete() {
        return hasTeamRedMakeMove && hasTeamBlueMakeMove;
    }

    public static void logState(Team team, Map<Action, Boolean> actionResults) {
        var actionLogs = new ArrayList<>();
        actionResults.forEach(
            (action, aBoolean) ->
                actionLogs.add(
                    MessageFormat.format(
                        ACTION_TEMPLATE,
                        action.getType().getName(),
                        action.getAircraftPosition(),
                        action.getTargetPoint(),
                        aBoolean ? "SUCCESS" : "FAILED"
                    )
                )
        );
        teamRoundLog.put(
            team.getTeamColor(),
            MessageFormat.format(
                ROUND_STATE_LOG_TEMPLATE,
                currentRound,
                team.getTeamColor(),
                StringUtils.join(actionLogs, "\n")
            )
        );
    }

    @Override
    public void run() {
        while (true) {
            while (currentRound <= TOTAL_ROUNDS) Thread.onSpinWait();
            makeResult();
            initialize();
        }

    }

    private void makeResult() {

    }

    public static Team getTeamRed() {
        return teamRed;
    }

    public static Team getTeamBlue() {
        return teamBlue;
    }

    public static boolean hasTeamRedMakeMove() {
        return hasTeamRedMakeMove;
    }

    public static boolean hasTeamBlueMakeMove() {
        return hasTeamBlueMakeMove;
    }

    public static void setTeamCompletedRound(TeamColor teamColor) {
        if (TeamColor.RED.equals(teamColor)) {
            Game.hasTeamRedMakeMove = true;
        }

        if (TeamColor.BLUE.equals(teamColor)) {
            Game.hasTeamBlueMakeMove = true;
        }
    }

    public static synchronized void incrementRound() {
        hasTeamRedMakeMove = false;
        hasTeamBlueMakeMove = false;
        teamRoundLog.values().forEach(System.out::println);

        if (currentRound < TOTAL_ROUNDS) {
            currentRound++;
            System.out.println("Next round is " + currentRound);
        }
    }
}
