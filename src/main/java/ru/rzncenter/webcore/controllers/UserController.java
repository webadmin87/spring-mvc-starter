package ru.rzncenter.webcore.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.rzncenter.webcore.domains.User;
import ru.rzncenter.webcore.domains.UserFilter;
import ru.rzncenter.webcore.json.JsonUtils;
import ru.rzncenter.webcore.service.PageUtils;
import ru.rzncenter.webcore.service.UserService;
import ru.rzncenter.webcore.web.Resizer;

import javax.validation.Valid;
import java.util.List;

/**
 * Рест контроллер пользователей
 */
@RestController
@RequestMapping("/admin/user/")
public class UserController {

    @Autowired
    Resizer resizer;

    @Autowired
    UserService userService;

    @Autowired
    PageUtils pageUtils;

    @Autowired
    JsonUtils jsonUtils;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<User>> list(
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam(defaultValue = "1", required = false) Integer pageSize,
            @RequestParam(defaultValue = "id", required = false) String sortField,
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection,
            @RequestParam(required = false) String filter
    ) {

        UserFilter userFilter = jsonUtils.jsonToObject(filter, new TypeReference<UserFilter>() {});

        Page<User> userPage;

        if(userFilter == null)
            userPage = userService.findAll(page, pageSize, new Sort(sortDirection, sortField));
        else
            userPage = userService.findAll(userFilter, page, pageSize, new Sort(sortDirection, sortField));

        resizer.resize(userPage.getContent());

        HttpHeaders headers = new HttpHeaders();

        pageUtils.pageToHeareds(headers, userPage);

        return new ResponseEntity<>(userPage.getContent(), headers, HttpStatus.OK);

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
    public User insert(@RequestBody @Valid User user) {

        return userService.save(user);

    }

    @RequestMapping(value = "{id}/", method = RequestMethod.POST)
    public User update(@PathVariable Long id, @RequestBody @Valid User user) {

        return userService.save(user);

    }

    /**
     * Обработка ошибок валидации.
     * @param exception
     * @return
     */
    @ExceptionHandler
    public ResponseEntity<List<ObjectError>> handleValidationException(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(exception.getBindingResult().getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "roles/", method = RequestMethod.GET)
    public User.Role[] roles() {

        return User.Role.values();

    }

}
