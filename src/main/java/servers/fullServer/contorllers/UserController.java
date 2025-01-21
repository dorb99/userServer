package servers.fullServer.contorllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import servers.fullServer.models.User;
import servers.fullServer.services.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	@Autowired
	private UserService service;
	
	
	// get one user (by id)
	@GetMapping("/find/id")
	public ResponseEntity<User> getOneById(@RequestParam("id") String id) throws Exception{
		int identifier;
		try {
			System.out.println("DEBUG "+id);
			identifier = Integer.parseInt(id);
		} catch (NumberFormatException e) {
	        return ResponseEntity.badRequest().build();  // 400 Bad Request
	    }
		Optional<User> user = service.getById(identifier);
	    if (user.isPresent()) {
	        return ResponseEntity.ok(user.get());  // 200 OK
	    } else {
	        return ResponseEntity.notFound().build();  // 404 Not Found
	    }
	}
	
	// get one user (by name
	@GetMapping("/find/name")
	public ResponseEntity<List<User>> getOneByName(@RequestParam("name") String name){
		if(name.isBlank()) {
			return ResponseEntity.badRequest().build();
		}
		List<User> users = service.getByName(name);
		if(users.size() >= 1) {
			return ResponseEntity.ok().body(users);			
		}
		return ResponseEntity.notFound().build();  // 404 Not Found
	}
	
	// get all users
	@GetMapping
	public ResponseEntity<List<User>> getAll(){
		List<User> users = service.getAll();
		return ResponseEntity.status(401).body(users);
	}
	
	// create (register) user
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody @Valid User newUser) {
	    return ResponseEntity.status(201).body(service.register(newUser));
	}
	
	// login (find by username, compare password)
	@PostMapping("/login")
	public boolean login(@RequestBody Map<String, String> body) {
		String username = body.get("username");
		String password  = body.get("password");
		if(username.isBlank() || password.isBlank()) {
			return false;
		}
		return service.login(username, password);
	}
	
	// update (without password)
	@PutMapping("/update")
	public boolean updateOne(@RequestParam("id") String id, @RequestBody Map<String, String> body){
		try {
			int identifier = Integer.parseInt(id);
			Optional<User> user = service.getById(identifier);
			if(user.get() == null) {
				return false;
			}
			String name = body.get("name");
			String age = body.get("age");
			if(name != null) {
				user.get().setName(name);
			}
			if(age != null) {
				int newage = Integer.parseInt(age);
				user.get().setAge(newage);
			}
			service.update(user.get());
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	// delete
	@DeleteMapping("/delete")
	public String deleteOne(@RequestParam("id") String id){
		int identifier;
		try {
			identifier = Integer.parseInt(id);
		} catch (NoSuchElementException e) {
			return "User not found";
		}
		return service.deleteById(identifier);
	}
	
	// forgot password
//	public boolean ddd(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("name") String name) {
//		return service.login(user);
//	}
}
