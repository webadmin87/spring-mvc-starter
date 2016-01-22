package ru.rzncenter.webcore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.rzncenter.webcore.domains.User;
import ru.rzncenter.webcore.service.UserService;

/**
 * Рест контроллер пользователей
 */
@RestController
@RequestMapping("/admin/user/")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<User> list(
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam(defaultValue = "1", required = false) Integer pageSize,
            @RequestParam(defaultValue = "id", required = false) String sortField,
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection
    ) {

        return userService.findAll(page, pageSize, new Sort(sortDirection, sortField));

    }

    @RequestMapping(value = "{id}/", method = RequestMethod.GET)
    public User one(@PathVariable Long id) {

        return userService.findOne(id);

    }

    @RequestMapping(value = "{id}/", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {

        userService.delete(userService.findOne(id));

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public User save(@RequestBody User user) {

        return userService.save(user);

    }

}
