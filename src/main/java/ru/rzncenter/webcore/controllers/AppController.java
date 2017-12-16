package ru.rzncenter.webcore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер для рендера страницы приложения
 */
@Controller
public class AppController {

    private final Environment env;

    @Autowired
    public AppController(Environment env) {
        this.env = env;
    }

    @RequestMapping("")
    public String run(Model model) {
        String[] profiles = env.getActiveProfiles();
        if(profiles.length == 0) {
            profiles = env.getDefaultProfiles();
        }
        model.addAttribute("profiles", profiles);
        return "index";
    }

}
