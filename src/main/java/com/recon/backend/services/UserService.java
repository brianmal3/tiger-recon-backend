package com.recon.backend.services;

import com.recon.backend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
     FireService fireService;



    public User findUserByFirebaseUid(String firebaseUid) {
        var m = fireService.findDataByProperty("users", "firebaseUid", firebaseUid, User.class);
        if (m.isEmpty()) {
            return null;
        }
        return (User) m.get(0);
    }
}
