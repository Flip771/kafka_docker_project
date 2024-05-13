package com.example.demo.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.example.demo.util.BcryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/v1")
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class UserRestController {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	UserService userService;

	@Autowired
	BcryptUtil bcryptUtil;

	private static final String TOPIC = "user-events";
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
	@GetMapping("/users")
	public List<User> getAllUsers(Authentication authentication) {
		
		return userService.findAllUsers();
		
	}
		
	@PostMapping("/users/create")
	public ResponseEntity<User> saveusers(@RequestBody User newUser,Authentication auth) {
		System.out.println(newUser.getUserName()+"  "+auth.getName());
		String hashedPassword = bcryptUtil.hashPassword(newUser.getPassword());
		newUser.setPassword(hashedPassword);
		kafkaTemplate.send(TOPIC, "User created " + newUser.getUserName() +" by "+auth.getName());
		return ResponseEntity.status(HttpStatus.CREATED).body((userService.saveUser(newUser)));
		
	}

	@PostMapping("/users/register")
	public ResponseEntity<User> registeruser(@RequestBody User newUser) {
		System.out.println(newUser.getUserName());
		String hashedPassword = bcryptUtil.hashPassword(newUser.getPassword());
		newUser.setPassword(hashedPassword);
		newUser.setRole("user");
		kafkaTemplate.send(TOPIC, "User created " + newUser.getUserName() +" by user registration");
		return ResponseEntity.status(HttpStatus.CREATED).body((userService.saveUser(newUser)));

	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") int userId) {
		System.out.println("Inside getuserbyid method");
		return ResponseEntity.ok().body(userService.findUserById(userId).get());
		
	}
	
	@PutMapping("/users/update/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable("userId") int UserId,@RequestBody User newUser) {
		String hashedPassword = bcryptUtil.hashPassword(newUser.getPassword());
		newUser.setPassword(hashedPassword);
		kafkaTemplate.send(TOPIC, "User updated" + newUser.getUserName() +" by user update");
		return ResponseEntity.ok().body(userService.updateUser(UserId,newUser));
		
	}

	@DeleteMapping("/users/delete/{userId}")
	public ResponseEntity<Object> deleteUser(Authentication authentication,@PathVariable("userId") int UserId) {
		 userService.deleteUser(UserId);
		kafkaTemplate.send(TOPIC, "User deleted "+UserId + " by "+ authentication.getName());
		 return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
	}
	
	@GetMapping("/users/search")
	@PostAuthorize("returnObject.body.userName==authenticated.user")
	public ResponseEntity<User> userDetails(Authentication authentication, @RequestParam("cname") String cName) throws Exception {
		System.out.println(authentication.getName().toString());
		User User=userService.findByUserName(cName);
		if(User==null) {
			ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		return ResponseEntity.ok().body(User);
		
	}

	
	

}
