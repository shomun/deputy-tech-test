package com.monirul.deputy;

import com.monirul.deputy.model.Role;
import com.monirul.deputy.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDataServiceTest {

    private UserDataService classUnderTest;

    @BeforeEach
    void setUp() throws Exception {
        DataReader dataReader = new DataReader();
        classUnderTest = initUserDataInstance("userdata.json","roledata.json");
    }

    public  UserDataService initUserDataInstance(String userDataFileName, String roleDataFileName ) throws Exception{
        UserDataService userDataService = new UserDataService();
        DataReader dataReader = new DataReader();

        List<User> users = dataReader.readData(userDataFileName,User[].class);
        List<Role> roles = dataReader.readData(roleDataFileName,Role[].class);

        userDataService.setUsers(users);
        userDataService.setRoles(roles);
        return userDataService;
    }

//    @Test
//    void getSubOrdinates_should_find_2_subordinates() {
//        List<User> users =  classUnderTest.getSubOrdinates(3);
//
//    }

    @Test
    void getSubOrdinates_should_find_2_subordinates_for_user_id_3() {
        List<User> subordinates = classUnderTest.getSubOrdinates(3);
        assertNotNull(subordinates);
        assertEquals(2, subordinates.size());
        assertEquals("Emily Employee", subordinates.get(0).getName());
        assertEquals("Steve Trainer", subordinates.get(1).getName());
    }

    @Test
    void getSubOrdinates_should_find_4_subordinates_for_user_id_1() {
        List<User> subordinates = classUnderTest.getSubOrdinates(1);
        assertNotNull(subordinates);
        assertEquals(4, subordinates.size());

    }

    @Test
    void getSubOrdinates_should_find_no_subordinates_for_user_id_2() {
        List<User> subordinates = classUnderTest.getSubOrdinates(2);
        assertNull(subordinates);

    }

    @Test
    void getSubOrdinates_should_find_no_subordinates_for_unknown_user() {
        List<User> subordinates = classUnderTest.getSubOrdinates(20001);
        assertNull(subordinates);

    }
}