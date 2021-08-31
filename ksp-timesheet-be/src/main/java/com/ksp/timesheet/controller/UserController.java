package com.ksp.timesheet.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ksp.timesheet.dto.UserDto;
import com.ksp.timesheet.entity.User;
import com.ksp.timesheet.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = {"/",""})
	public ResponseEntity<UserDto> save(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(new UserDto(user.getId(), user.getUsername()),HttpStatus.OK);
    }

    @GetMapping(value = {"/",""})
    public ResponseEntity<List<UserDto>> getAll() {
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users.stream().map(user->
   	 new UserDto(user.getId(), user.getUsername())
   ).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> get(@PathVariable(value = "id") Long id) {
    	User user = userService.get(id);
        return new ResponseEntity<>( new UserDto(user.getId(), user.getUsername()), HttpStatus.OK);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDto> get(@PathVariable(value = "username") String username) {
    	User user = userService.getByUsername(username);
        return new ResponseEntity<>( new UserDto(user.getId(), user.getUsername()), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<User> delete(@PathVariable(value = "username") String username) {
        userService.delete(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}