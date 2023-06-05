package hu.epam.mentoring.l3.zsf.controller;

import hu.epam.mentoring.l3.zsf.controller.model.Action;
import hu.epam.mentoring.l3.zsf.engine.GameService;
import hu.epam.mentoring.l3.zsf.model.TeamColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("action")
public class ActionController {
    @Autowired
    private GameService gameService;

    @PostMapping(value = "/action", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> postAction(
        @RequestHeader(name = "team") TeamColor teamColor,
        @RequestBody List<Action> actions
    ) {
        return new ResponseEntity<>(
            gameService.calculateAction(teamColor, actions), HttpStatus.ACCEPTED
        );
    }
}
