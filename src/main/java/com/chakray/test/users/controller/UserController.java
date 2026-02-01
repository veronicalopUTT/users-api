package com.chakray.test.users.controller;

import com.chakray.test.users.model.User;
import com.chakray.test.users.service.UserService;
import org.springframework.web.bind.annotation.*;

import com.chakray.test.users.dto.CreateUserRequest;
import com.chakray.test.users.dto.UpdateUserRequest;
import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getAll(
            @RequestParam(required = false) String sortedBy,
            @RequestParam(required = false) String filter
    ) {

        if (filter != null && !filter.isBlank()) {
            return service.filterUsers(filter);
        }

        if (sortedBy != null && !sortedBy.isBlank()) {
            return service.getAllSorted(sortedBy);
        }

        return service.getAll();
    }

    @PostMapping
    public User create(
            @Valid @RequestBody CreateUserRequest request
    ) {
        return service.createUser(request);
    }

    @GetMapping("/search")
    public User getByEmail(@RequestParam String email) {
        return service.findByEmail(email);
    }

    @PatchMapping("/{id}")
    public User update(
            @PathVariable String id,
            @RequestBody UpdateUserRequest request
    ) {
        return service.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteUser(id);
    }



}
