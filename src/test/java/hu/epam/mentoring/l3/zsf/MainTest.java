package hu.epam.mentoring.l3.zsf;

import hu.epam.mentoring.l3.zsf.controller.model.Action;
import hu.epam.mentoring.l3.zsf.controller.model.FlyAction;
import hu.epam.mentoring.l3.zsf.controller.model.LocateAircraftAction;
import hu.epam.mentoring.l3.zsf.controller.model.ShootAction;
import hu.epam.mentoring.l3.zsf.engine.Game;
import hu.epam.mentoring.l3.zsf.engine.GameService;
import hu.epam.mentoring.l3.zsf.model.Aircraft;
import hu.epam.mentoring.l3.zsf.model.Team;
import hu.epam.mentoring.l3.zsf.model.TeamColor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = Main.class)
@WebAppConfiguration
@AutoConfigureMockMvc
public class MainTest {
    @Autowired
    private GameService gameService;

    @Test
    public void test() {
        round1();
        round2();
        round3();
        round4();
        round5();
    }

    private void round1() {
        gameService.calculateAction(TeamColor.RED, getRound1ActionRed());
        gameService.calculateAction(TeamColor.BLUE, getRound1ActionBlue());
    }

    private List<Action> getRound1ActionRed() {
        List<Action> actions = new ArrayList<>();
        Game.getTeamRed().getAircrafts().forEach(aircraft -> {
            if (aircraft.getCoordinate().equals(new Team.Point(3, 0, 5))) {
                actions.add(
                    new FlyAction(aircraft.getCoordinate(), new Team.Point(3, 1, 5))
                );
            } else if (aircraft.getCoordinate().equals(new Team.Point(4, 0, 5))) {
                actions.add(
                    new LocateAircraftAction(
                        aircraft.getCoordinate(), new Team.Point(4, 5, 5)
                    )
                );
            } else if (aircraft.getCoordinate().equals(new Team.Point(5, 0, 5))) {
                actions.add(
                    new ShootAction(aircraft.getCoordinate(), new Team.Point(5, 9, 5))
                );
            }
        });

        return actions;
    }

    private List<Action> getRound1ActionBlue() {
        List<Action> actions = new ArrayList<>();
        Game.getTeamBlue().getAircrafts().forEach(aircraft -> {
            if (aircraft.getCoordinate().equals(new Team.Point(3, 9, 5))) {
                actions.add(
                    new FlyAction(aircraft.getCoordinate(), new Team.Point(3, 8, 5))
                );
            } else if (aircraft.getCoordinate().equals(new Team.Point(4, 9, 5))) {
                actions.add(
                    new FlyAction(aircraft.getCoordinate(), new Team.Point(4, 8, 5))
                );
            }
        });

        return actions;
    }

    private void round2() {
        gameService.calculateAction(TeamColor.RED, getRound2ActionRed());
        gameService.calculateAction(TeamColor.BLUE, getRound2ActionBlue());
    }

    private List<Action> getRound2ActionRed() {
        List<Action> actions = new ArrayList<>();
        Game.getTeamRed().getAircrafts().forEach(aircraft -> {
            if (aircraft.getCoordinate().equals(new Team.Point(3, 1, 5))) {
                actions.add(
                    new LocateAircraftAction(
                        aircraft.getCoordinate(), new Team.Point(4, 8, 5)
                    )
                );
            } else if (aircraft.getCoordinate().equals(new Team.Point(4, 0, 5))) {
                actions.add(
                    new FlyAction(aircraft.getCoordinate(), new Team.Point(4, 1, 5))
                );
            } else if (aircraft.getCoordinate().equals(new Team.Point(5, 0, 5))) {
                actions.add(
                    new FlyAction(aircraft.getCoordinate(), new Team.Point(5, 1, 5))
                );
            }
        });

        return actions;
    }

    private List<Action> getRound2ActionBlue() {
        List<Action> actions = new ArrayList<>();
        Game.getTeamBlue().getAircrafts().forEach(aircraft -> {
            if (aircraft.getCoordinate().equals(new Team.Point(3, 8, 5))) {
                actions.add(
                    new ShootAction(aircraft.getCoordinate(), new Team.Point(5, 1, 5))
                );
            } else if (aircraft.getCoordinate().equals(new Team.Point(4, 8, 5))) {
                actions.add(
                    new LocateAircraftAction(
                        aircraft.getCoordinate(), new Team.Point(3, 2, 5)
                    )
                );
            }
        });

        return actions;
    }

    private void round3() {
        gameService.calculateAction(TeamColor.RED, getRound3ActionRed());
        gameService.calculateAction(TeamColor.BLUE, getRound3ActionBlue());
    }

    private List<Action> getRound3ActionRed() {
        List<Action> actions = new ArrayList<>();
        Game.getTeamRed().getAircrafts().forEach(aircraft -> {
            if (aircraft.getCoordinate().equals(new Team.Point(4, 1, 5))) {
                actions.add(
                    new LocateAircraftAction(
                        aircraft.getCoordinate(), new Team.Point(4, 8, 5)
                    )
                );
            } else if (aircraft.getCoordinate().equals(new Team.Point(3, 1, 5))) {
                actions.add(
                    new ShootAction(aircraft.getCoordinate(), new Team.Point(7, 9, 5))
                );
            }
        });

        return actions;
    }

    private List<Action> getRound3ActionBlue() {
        List<Action> actions = new ArrayList<>();
        Game.getTeamBlue().getAircrafts().forEach(aircraft -> {
            if (aircraft.getCoordinate().equals(new Team.Point(3, 8, 5))) {
                actions.add(
                    new FlyAction(aircraft.getCoordinate(), new Team.Point(3, 7, 5))
                );
            } else if (aircraft.getCoordinate().equals(new Team.Point(4, 8, 5))) {
                actions.add(
                    new FlyAction(aircraft.getCoordinate(), new Team.Point(4, 7, 5))
                );
            }
        });

        return actions;
    }

    private void round4() {
        gameService.calculateAction(TeamColor.RED, getRound4ActionRed());
        gameService.calculateAction(TeamColor.BLUE, getRound4ActionBlue());
    }

    private List<Action> getRound4ActionRed() {
        List<Action> actions = new ArrayList<>();
        Game.getTeamRed().getAircrafts().forEach(aircraft -> {
            if (aircraft.getCoordinate().equals(new Team.Point(4, 1, 5))) {
                actions.add(
                    new ShootAction(aircraft.getCoordinate(), new Team.Point(4, 7, 5))
                );
            } else if (aircraft.getCoordinate().equals(new Team.Point(3, 1, 5))) {
                actions.add(
                    new FlyAction(aircraft.getCoordinate(), new Team.Point(3, 2, 5))
                );
            }
        });

        return actions;
    }

    private List<Action> getRound4ActionBlue() {
        List<Action> actions = new ArrayList<>();
        Game.getTeamBlue().getAircrafts().forEach(aircraft -> {
            if (aircraft.getCoordinate().equals(new Team.Point(3, 7, 5))) {
                actions.add(
                    new ShootAction(aircraft.getCoordinate(), new Team.Point(3, 2, 5))
                );
            }
        });

        return actions;
    }

    private void round5() {
        gameService.calculateAction(TeamColor.RED, getRound5ActionRed());
        gameService.calculateAction(TeamColor.BLUE, getRound5ActionBlue());
    }

    private List<Action> getRound5ActionRed() {
        List<Action> actions = new ArrayList<>();
        Game.getTeamRed().getAircrafts().forEach(aircraft -> {
            if (aircraft.getCoordinate().equals(new Team.Point(4, 1, 5))) {
                actions.add(
                    new LocateAircraftAction(
                        aircraft.getCoordinate(), new Team.Point(3, 7, 5)
                    )
                );
            }
        });

        return actions;
    }

    private List<Action> getRound5ActionBlue() {
        List<Action> actions = new ArrayList<>();
        Game.getTeamBlue().getAircrafts().forEach(aircraft -> {
            if (aircraft.getCoordinate().equals(new Team.Point(3, 7, 5))) {
                actions.add(
                    new FlyAction(aircraft.getCoordinate(), new Team.Point(3, 6, 5))
                );
            }
        });

        return actions;
    }
}
