package com.ServiceAggregatingEvents.project.service;

import com.ServiceAggregatingEvents.project.dto.UserDto;
import com.ServiceAggregatingEvents.project.entities.User;
import com.ServiceAggregatingEvents.project.entities.Role;
import com.ServiceAggregatingEvents.project.exceptions.ResourceNotFoundException;
import com.ServiceAggregatingEvents.project.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserMapper userMapper;



    public boolean authenticate(String email, String password) {
        Optional<User> optionalUser = userRepo.findByEmail(email);

        // Verificăm dacă utilizatorul există și dacă parola introdusă se potrivește cu cea din baza de date
        return optionalUser.isPresent() && optionalUser.get().getPassword().equals(password);
    }


    public List<UserDto> findAllUsers() {
        // Folosim stream pentru a transforma utilizatorii în UserDto
        return userRepo.findAll().stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> findByName(String name) {
        User user = userRepo.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("User with the name " + name + " not found"));
        return Optional.ofNullable(userMapper.convertToDto(user));
    }
    public Optional<UserDto> findByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with the email " + email + " not found"));
        return Optional.ofNullable(userMapper.convertToDto(user));
    }

    public UserDto createUser(UserDto userDto) {
        User user = userMapper.convertToEntity(userDto);
        if (userDto.getRoles() != null) {
            List<Role> roles = new ArrayList<>();
            for (Role role : userDto.getRoles()) {
                // Asigură-te că rolul este corect
                roles.add(role);
            }
            user.setRoles(roles);
        }

        userRepo.save(user);
        return userMapper.convertToDto(user);
    }

    public UserDto editUser(Long userId, UserDto userDto) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));

        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getPassword() != null
                && !userDto.getPassword().isBlank()
                && !userDto.getPassword().equals("***unchanged***")) {

            user.setPassword(userDto.getPassword());
        }


        if (userDto.getRoles() != null) {
            user.setRoles(userDto.getRoles());
        }


        if (userDto.getRoles() != null) {
            user.setRoles(userDto.getRoles());
        }

        userRepo.save(user);

        return userMapper.convertToDto(user);
    }


    public void deleteUser(String name) {
        User user = userRepo.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("User with the name " + name + " not found"));
        userRepo.delete(user);
    }
    public void deleteUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
        userRepo.delete(user);
    }

}

