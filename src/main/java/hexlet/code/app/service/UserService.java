package hexlet.code.app.service;

import hexlet.code.app.dto.UserCreateDTO;
import hexlet.code.app.dto.UserDTO;
import hexlet.code.app.dto.UserUpdateDTO;
import hexlet.code.app.exception.ResourceNotFoundException;
import hexlet.code.app.mapper.UserMapper;
import hexlet.code.app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper postMapper;

    public UserDTO create(UserCreateDTO userData) {
        var user = postMapper.map(userData);
        repository.save(user);
        return postMapper.map(user);
    }

    public List<UserDTO> readAll() {
        var users = repository.findAll();
        return users.stream().map(postMapper::map).toList();
    }

    public UserDTO readById(Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        return postMapper.map(user);
    }

    public UserDTO update(UserUpdateDTO userData, Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found" + id));
        postMapper.update(userData, user);
        repository.save(user);
        return postMapper.map(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
