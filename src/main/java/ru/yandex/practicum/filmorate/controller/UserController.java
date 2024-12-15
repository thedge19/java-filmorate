package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.validator.interfaces.Created;
import ru.yandex.practicum.filmorate.validator.interfaces.Updated;

import java.util.Collection;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public Collection<User> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable long id) {
        return service.findById(id);
    }

    @PostMapping
    public User create(
            @RequestBody
            @Validated(Created.class)
            User user) {
        return service.create(user);
    }

    @PutMapping
    public User update(
            @RequestBody
            @Validated(Updated.class)
            User newUser) {
        return service.update(newUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId)
    {
        service.addFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getFriends(@PathVariable long id)
    {
        return service.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId)
    {
        return service.getCommonFriends(id, otherId);
    }

    @DeleteMapping( "/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable long id, @PathVariable long friendId)
    {
        service.removeFriend(id, friendId);
    }
}
