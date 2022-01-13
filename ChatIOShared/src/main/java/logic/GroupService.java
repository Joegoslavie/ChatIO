package logic;

import models.Group;
import models.User;
import rest.GroupRest;

public class GroupService {

    private GroupRest restService = null;

    public GroupService(){
        this.restService = new GroupRest();
    }

    public boolean join(Group group, User user){
        if(group.isPrivate())
            return false;
        return restService.join(group.getId(), user.getId());
    }
}
