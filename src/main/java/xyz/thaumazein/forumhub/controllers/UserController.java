package xyz.thaumazein.forumhub.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import xyz.thaumazein.forumhub.dtos.RegisterUserRequest;
import xyz.thaumazein.forumhub.dtos.UpdatePasswordRequest;
import xyz.thaumazein.forumhub.dtos.UpdateUserRequest;
import xyz.thaumazein.forumhub.dtos.UserDto;
import xyz.thaumazein.forumhub.entities.Role;
import xyz.thaumazein.forumhub.mappers.UserMapper;
import xyz.thaumazein.forumhub.repositories.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public List<UserDto> getAllUsers(@RequestParam(required = false, defaultValue = "", name = "sort") String sortBy) {

        if (!Set.of("name", "email").contains(sortBy)) {
            sortBy = "name";
        }

        return userRepository.findAll(Sort.by(sortBy)).stream().map(userMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        var userDto = userMapper.toDto(user);

        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserRequest request, UriComponentsBuilder uriBuilder) {
        if (userRepository.existsByEmail(request.email())) {
            return ResponseEntity.badRequest().body(
                    Map.of("email", "Email is already registered.")
            );
        }

        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        var userDto = userMapper.toDto(user);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.id()).toUri();

        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        var userDto = userMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<UserDto> updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        if (!user.getPassword().equals(request.oldPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        user.setPassword(request.newPassword());
        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }


}
