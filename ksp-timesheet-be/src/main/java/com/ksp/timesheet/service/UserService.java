package com.ksp.timesheet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksp.timesheet.entity.User;
import com.ksp.timesheet.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository; 

	public void save(User user) {
		userRepository.save(user);
	}

	public List<User> getAll()  {
		return userRepository.findAll();

	}

	public User get(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		return userOptional.isPresent() ? userOptional.get() : null;
	}

	public User getByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public void delete(String username) {
		userRepository.deleteByUsername(username);
	}
}