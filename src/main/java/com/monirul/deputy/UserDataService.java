package com.monirul.deputy;


import com.monirul.deputy.model.Role;
import com.monirul.deputy.model.User;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class UserDataService {

    private List<User> users;

    private List<Role> roles;

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * find subordinates for a given user id, it will return all the users down the hierarchy
     * @param userId
     * @return
     */
    public List<User> getSubOrdinates(int userId){
        List<User> subordinates = null;
        User user = this.findUserById(userId);
        if(user != null){
            List<Integer> subRoleIds = getSubRoleIdsByParentRoleId(user.getRoleId());

            subordinates =  findAllUsersByRoles(subRoleIds);
        }
        return subordinates;
    }

    /**
     * Get the list of sub Role IDs for a given role id
     * @param parentRoleId
     * @return
     */
    private List<Integer> getSubRoleIdsByParentRoleId(int parentRoleId) {
        List<Integer> subRoleIds = null;
        List<Role> subRoles = findSubRolesByParentId(parentRoleId);
        if(subRoles != null && !subRoles.isEmpty()){
            subRoleIds = subRoles.stream().map(r->r.getId()).collect(Collectors.toList());
        }
        return subRoleIds;
    }



    /**
     * Find a user by user id
     * @param userId
     * @return
     */
    public User findUserById(int userId){
        Optional<User> user = users.stream().filter(u-> u.getId() == userId).findFirst();
        return (user.isPresent()) ?  user.get() : null;

    }

    /**
     * find list of users for given list of roleIds
     * @param roleIds
     * @return
     */
    public List<User> findAllUsersByRoles(List<Integer> roleIds){
        if(roleIds == null) return null;
        return users.stream().filter(user -> roleIds.contains(user.getRoleId())).collect(Collectors.toList());
    }


    /**
     * find a role for a given role id
     * @param roleId
     * @return
     */
    public Role findRoleById(int roleId){
        Optional<Role> role = roles.stream().filter(u-> u.getId() == roleId).findFirst();
        return (role.isPresent()) ? role.get() : null;
    }




    /**
     * find all sub roles (immediate and down the hierarchy ) for a fiven role id
     * @param parentRoleId
     * @return
     */
    public List<Role> findSubRolesByParentId(int parentRoleId){
        return findImmediateSubRoles(parentRoleId,roles);
    }

    /**
     * Find all the sub roles recursively util it reaches the last role(s) in the hierarchy
     * @param parentRoleId
     * @param roles
     * @return
     */
    private List<Role> findImmediateSubRoles(int parentRoleId, List<Role> roles){
        List<Role> subRoles = roles.stream().filter(r->r.getParentId() == parentRoleId).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        Iterator<Role> roleIterator = subRoles.iterator();
        subRoles.forEach( subRole->{
            List<Role> immidiateSubRoles = findImmediateSubRoles(subRole.getId(), roles);
            subRoles.addAll(immidiateSubRoles);
        });
        return subRoles;
    }
}
