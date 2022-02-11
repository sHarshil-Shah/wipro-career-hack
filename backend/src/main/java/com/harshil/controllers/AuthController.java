package com.harshil.controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshil.mail.SendMail;
import com.harshil.models.ERole;
import com.harshil.models.Role;
import com.harshil.models.User;
import com.harshil.payload.request.LoginRequest;
import com.harshil.payload.request.SignupRequest;
import com.harshil.payload.response.JwtResponse;
import com.harshil.payload.response.MessageResponse;
import com.harshil.repository.RoleRepository;
import com.harshil.repository.UserRepository;
import com.harshil.security.jwt.JwtUtils;
import com.harshil.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	UserController userController;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUser().getId(),
				userDetails.getUser().getUsername(), userDetails.getUser().getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest)
			throws AddressException, MessagingException, IOException {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(""/* signUpRequest.getPassword() */), signUpRequest.getName(), false);

		String strRoles = signUpRequest.getRole();
		Role role = new Role();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.LEVEL1)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			role = userRole;
		} else {
			switch (strRoles) {
			case "LEVEL2":
				Role adminRole = roleRepository.findByName(ERole.LEVEL2)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				role = adminRole;

				break;
			case "LEVEL3":
				Role modRole = roleRepository.findByName(ERole.LEVEL3)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				role = modRole;

				break;
			default:
				Role userRole = roleRepository.findByName(ERole.LEVEL1)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				role = userRole;
			}

		}

		user.setRole(role);
		user.setStatus(false);
		userController.createUser(user);
//		userRepository.save(user);
//		SendMail.sendmail(user.getEmail(), user.getId());

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
