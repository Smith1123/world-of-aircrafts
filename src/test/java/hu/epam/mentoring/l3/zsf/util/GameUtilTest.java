package hu.epam.mentoring.l3.zsf.util;

import hu.epam.mentoring.l3.zsf.model.Aircraft;
import hu.epam.mentoring.l3.zsf.model.Team;
import hu.epam.mentoring.l3.zsf.model.TeamColor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static hu.epam.mentoring.l3.zsf.util.GameConstants.AIRCRAFT_SIZE;
import static hu.epam.mentoring.l3.zsf.util.GameConstants.COORDINATE_SYSTEM_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameUtilTest {
    @ParameterizedTest
    @MethodSource("createInitialAircraftTestCases")
    public void testInitialAircraft(TeamColor teamColor, List<Aircraft> expectedValues) {
        assertEquals(expectedValues, GameUtil.initalizeAircraft(AIRCRAFT_SIZE, teamColor));
    }

    public static Stream<Arguments> createInitialAircraftTestCases() {
        List<Arguments> testArguments = new ArrayList<>();

        for (TeamColor color : TeamColor.values()) {

            List<Aircraft> aircrafts = new ArrayList<>();
            int defaultX = (COORDINATE_SYSTEM_SIZE - AIRCRAFT_SIZE)/2;

            for (int i = 0; i < AIRCRAFT_SIZE; i++) {
                aircrafts.add(
                    new Aircraft(
                        new Team.Point(
                            defaultX + i,
                            TeamColor.RED.equals(color) ? 0 : 9,
                            COORDINATE_SYSTEM_SIZE/2
                        )
                    )
                );
            }

            testArguments.add(Arguments.of(color, aircrafts));
        }

        return testArguments.stream();
    }
}
