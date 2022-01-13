package logic;

import enums.FriendStatus;
import models.FriendRequest;
import models.User;
import rest.FriendRequestRest;

public class FriendRequestService {

    private FriendRequestRest restService;

    public FriendRequestService(){
        restService = new FriendRequestRest();
    }

    public FriendRequest addFriend(User sender, User receiver){
        if(sender != null && receiver != null){
            if(!sender.getUsername().equals(receiver.getUsername())){
                return restService.create(sender.getId(), receiver.getId());
            }
        }

        return null;
    }

    public Object update(FriendRequest request, FriendStatus status){
        return restService.update(request.getId(), status);
    }
}
