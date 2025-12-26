package com.lauracercas.moviecards.controller;

import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.model.Movie;
import com.lauracercas.moviecards.service.actor.ActorService;
import com.lauracercas.moviecards.util.Messages;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
@Controller
public class ActorController {

    private static final String ATTR_ACTOR = "actor";
    private static final String ATTR_TITLE = "title";
    private static final String VIEW_FORM = "actors/form";
    private static final String VIEW_LIST = "actors/list";
    
    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("actors")
    public String getActorsList(Model model) {
        model.addAttribute("actors", actorService.getAllActors());
        return VIEW_LIST;
    }

    @GetMapping("actors/new")
    public String newActor(Model model) {
        model.addAttribute(ATTR_ACTOR, new Actor());
        model.addAttribute(ATTR_TITLE, Messages.NEW_ACTOR_TITLE);
        return VIEW_FORM;
    }

    @PostMapping("saveActor")
    public String saveActor(@ModelAttribute Actor actor, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return VIEW_FORM;
        }
        Actor actorSaved = actorService.save(actor);
        if (actor.getId() != null) {
            model.addAttribute("message", Messages.UPDATED_ACTOR_SUCCESS);
        } else {
            model.addAttribute("message", Messages.SAVED_ACTOR_SUCCESS);
        }

        model.addAttribute(ATTR_ACTOR, actorSaved);
        model.addAttribute(ATTR_TITLE, Messages.EDIT_ACTOR_TITLE);
        return VIEW_FORM;
    }

    @GetMapping("editActor/{actorId}")
    public String editActor(@PathVariable Integer actorId, Model model) {
        Actor actor = actorService.getActorById(actorId);
        List<Movie> movies = actor.getMovies();
        model.addAttribute(ATTR_ACTOR, actor);
        model.addAttribute("movies", movies);

        model.addAttribute(ATTR_TITLE, Messages.EDIT_ACTOR_TITLE);

        return VIEW_FORM;
    }


}
