package com.monirul.deputy;

import com.monirul.deputy.model.Role;
import com.monirul.deputy.model.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class AppMain {

    private UserDataService userDataService;

    private String userDataFileName = "userdata.json";

    private String roleDataFileName = "roledata.json";

    public AppMain() {
        try {
            initUserDataInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String a[]){
        AppMain appMain = new AppMain();
        appMain.findSubordinates(3);
        appMain.findSubordinates(1);

    }

    private  void findSubordinates( int userId) {
        System.out.println("\nFinding Subordinates of user with ID : " + userId);
        List<User> users = userDataService.getSubOrdinates(userId);
        if(users!= null){
            users.forEach(System.out::println);
        }
    }

    public  void initUserDataInstance() throws Exception{
        userDataService = new UserDataService();
        DataReader dataReader = new DataReader();

        List<User> users = dataReader.readData(userDataFileName,User[].class);
        List<Role> roles = dataReader.readData(roleDataFileName,Role[].class);

        userDataService.setUsers(users);
        userDataService.setRoles(roles);

    }
}
