package ru.rzncenter.webcore.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-test.xml")
public class UserServiceImplTest {

    private static final String GOOD_USERNAME = "admin";
    private static final String BAD_USERNAME = "bigmack";

    private static final Long GOOD_ID = 1L;
    private static final Long BAD_ID = 1000L;

    @Autowired
    UserService userService;

    @Test
    public void findByUsernameTest() {
        Assert.notNull(userService.findByUsername(GOOD_USERNAME));
    }

    @Test
    public void findByUsernameErrorTest() {
        Assert.isNull(userService.findByUsername(BAD_USERNAME));
    }

    @Test
    public void findOneTest() {
        Assert.notNull(userService.findOne(GOOD_ID));
    }

    @Test
    public void findOneErrorTest() {
        Assert.isNull(userService.findOne(BAD_ID));
    }

    @Test
    public void findAllTest() {
        Assert.isTrue(userService.findAll().size() == 1);
    }

}
