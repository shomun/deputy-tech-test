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

    }

    public void setUserDataFileName(String userDataFileName) {
        this.userDataFileName = userDataFileName;
    }

    public void setRoleDataFileName(String roleDataFileName) {
        this.roleDataFileName = roleDataFileName;
    }

    public static void main(String a[]){
        AppMain appMain = new AppMain();

        System.out.println();
        if(a.length < 1){
            System.out.println("Usage : <userid> <user_data_file> <role_data_file>");
            System.out.println("userid is required, if user_data_file and role_data_file is not provided, will use the default data");
            System.exit(1);
        }
        int userId = Integer.parseInt(a[0]);

        if(a.length>1){
            System.out.println("provided USER data file");
            appMain.setUserDataFileName(a[1]);
        }else  if(a.length>2){
            System.out.println("provided ROLE data file");
            appMain.setRoleDataFileName(a[2]);
        }
        try {

            appMain.initUserDataInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        appMain.findSubordinates(userId);
        //appMain.findSubordinates(1);

    }

    private  void findSubordinates( int userId) {
        System.out.println("\nFinding Subordinates of user with ID : " + userId);
        List<User> users = userDataService.getSubOrdinates(userId);
        if(users!= null){
            users.forEach(System.out::println);
        }else{
            System.out.println("No subordinates found");
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
