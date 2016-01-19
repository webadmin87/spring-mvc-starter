package ru.rzncenter.webcore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер для рендера страницы приложения
 */
@Controller
public class AppController {

    @RequestMapping("")
    public String run(Model model) {

        return "index";

    }

}
