package com.patil.app.ws.userservice;

import com.patil.app.ws.ui.model.response.UserDetailsRequestModel;
import com.patil.app.ws.ui.model.response.UserRest;

public interface UserService {
	UserRest createUser(UserDetailsRequestModel userDetails);
	UserRest getUser(String userId);
}
