package com.harshil.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.harshil.models.User;
import com.harshil.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/all")
	@PreAuthorize("hasAuthority('LEVEL1') or hasAuthority('LEVEL2') or hasAuthority('LEVEL3')")
	public List<User> allUsers() {
		List<User> users = userRepository.findAll();
		return users;
	}

	@PostMapping("/create")
	@PreAuthorize("hasAuthority('LEVEL2') or hasAuthority('LEVEL3')")
	public User createUser(@RequestBody User user) {
		user.setStatus(false);
		return userRepository.save(user);
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasAuthority('LEVEL1') or hasAuthority('LEVEL2') or hasAuthority('LEVEL3')")
	public User updateUser(@RequestBody User user, @PathVariable("id") long id) {
		User oldUser = userRepository.findById(id).get();
		oldUser.setPassword(user.getPassword());
		oldUser.setStatus(true);
		return userRepository.save(oldUser);
	}

	// Delete operation
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('LEVEL3')")
	public String deleteUserById(@PathVariable("id") long userId) {
		userRepository.deleteById(userId);
		return "Deleted Successfully";
	}
}
