package com.patil.app.ws.ui.controller;

import java.util.Map;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patil.app.ws.ui.model.response.UpdateUserDetailsRequestModel;
import com.patil.app.ws.ui.model.response.UserDetailsRequestModel;
import com.patil.app.ws.ui.model.response.UserRest;
import com.patil.app.ws.userservice.UserService;

@RestController
@RequestMapping("users")
public class UserController {

	Map<String, UserRest> users;
	@Autowired
	UserService userService;

	@GetMapping
	public String getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "15") int limit,
			@RequestParam(value = "sort", required = false) String sort) {
		return "GET users was called page = " + page + " limit = " + limit + " sort =" + sort;
	}

	@GetMapping(path = "/{userId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserRest> getUser(@PathVariable String userId) {
		UserRest returnValue = userService.getUser(userId);
		if (returnValue == null ) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(returnValue, HttpStatus.OK);
		}
	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {

		UserRest returnValue = userService.createUser(userDetails);

		return new ResponseEntity<UserRest>(returnValue, HttpStatus.OK);
	}

	@PutMapping(path = "/{userId}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public UserRest updateUser(@PathVariable String userId,
			@Valid @RequestBody UpdateUserDetailsRequestModel userDetails) {
		UserRest storedUserDetails = users.get(userId);
		storedUserDetails.setFirstName(userDetails.getFirstName());
		storedUserDetails.setLastName(userDetails.getLastName());
		users.put(userId, storedUserDetails);
		return storedUserDetails;

	}

	@DeleteMapping(path = "/{id}")
	public @ResponseBody ResponseEntity<Void> deleteUser(@PathVariable String id) {
		users.remove(id);
		return ResponseEntity.noContent().build();
	}
}
