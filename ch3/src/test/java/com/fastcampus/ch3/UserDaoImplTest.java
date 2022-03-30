package com.fastcampus.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class UserDaoImplTest {

    @Autowired
    UserDao userDao;

    @Test
    public void deleteUser() {
    }

    @Test
    public void selectUser() {
    }

    @Test
    public void insertUser() {
    }

    @Test
    public void updateUser() throws Exception{
        Calendar cal = Calendar.getInstance();
        cal.clear();    // 모든 필드 초기화
        cal.set(2021, 1, 1);

        userDao.deleteAll();
        User user = new User("asdf", "1234", "홍길동", "123@naver.com", new Date(cal.getTimeInMillis()),"fb", new Date());
        int rowCnt = userDao.insertUser(user);
        assertTrue(rowCnt==1);

        user.setPwd("09876");
        user.setEmail("bbb@bbb.com");
        rowCnt = userDao.updateUser(user);
        assertTrue(rowCnt==1);

        User user2 = userDao.selectUser(user.getId());
        System.out.println("user = " + user);
        System.out.println("user2 = " + user2);

        assertTrue(user.equals(user2));

    }
}