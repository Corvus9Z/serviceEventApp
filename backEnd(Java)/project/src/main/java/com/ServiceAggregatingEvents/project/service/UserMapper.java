package com.ServiceAggregatingEvents.project.service;

import com.ServiceAggregatingEvents.project.dto.UserDto;
import com.ServiceAggregatingEvents.project.entities.Role;
import com.ServiceAggregatingEvents.project.entities.User;
import com.ServiceAggregatingEvents.project.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMapper implements Mapper<User, UserDto> {
    @Override
    public UserDto convertToDto(User entity) {
        UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
        userDto.setName(entity.getName());
        userDto.setLastName(entity.getLastName());
        userDto.setEmail(entity.getEmail());
        userDto.setPassword(entity.getPassword());

        // Adaugă rolurile, parcurgând lista
        List<Role> roles = new ArrayList<>();
        for (Role role : entity.getRoles()) {
            roles.add(role); // Adaugă fiecare rol în lista
        }
        userDto.setRoles(roles); // Setează lista de roluri în DTO
        return userDto;
    }


    @Override
    public User convertToEntity(UserDto dto) {
        User userEntity = new User();
        userEntity.setId(dto.getId());
        userEntity.setName(dto.getName());
        userEntity.setLastName(dto.getLastName());
        userEntity.setEmail(dto.getEmail());
        userEntity.setPassword(dto.getPassword());

        // Adaugă rolurile, parcurgând lista
        List<Role> roles = new ArrayList<>();
        for (Role role : dto.getRoles()) {
            roles.add(role); // Adaugă fiecare rol în lista
        }
        userEntity.setRoles(roles); // Setează lista de roluri în entitate
        return userEntity;
    }

    public List<UserDto> listConvertToDto(List<User> entity){
        List<UserDto> result = new ArrayList<>();
        for (User user:entity){
            UserDto userDto = convertToDto(user);
            result.add(userDto);
        }
        return result;
    }
    public List<User> listConvertToEntity(List<UserDto> dto){
        List<User> result = new ArrayList<>();
        for (UserDto userDto:dto){
            User convertToEntity = convertToEntity(userDto);
            result.add(convertToEntity);
        }
        return result;
    }
}
