package logic.collections;

import models.Group;
import rest.GroupRest;

import java.util.ArrayList;

public class GroupCollection {
    private GroupRest restService = null;

    public GroupCollection(){
        this.restService = new GroupRest();
    }

    public ArrayList<Group> getAll(int localUserId){
        return this.restService.getAll(localUserId);
    }
}
