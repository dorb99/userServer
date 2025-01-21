package servers.fullServer.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import servers.fullServer.models.User;
import servers.fullServer.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;

	// get one user (by id)
	public Optional<User> getById(Integer id) {
		return repository.findById(id);
	}
	
	// get one user (by name)
	public List<User> getByName(String name) {
		return repository.findByName(name);
	}
	
	// get all users
	public List<User> getAll() {
		return repository.findAll();
	}
	
	// create (register) user
	public String register(User user) {
		repository.save(user);
		return "User was created! "+user.getId();
	}
	
	// login (find by username, compare password)
	public boolean login(String username, String password) {
		boolean found = false;
		Optional<User> user = repository.findByUsername(username);
		try{
			if(user.get().getPassword().equals(password)) {
				found = true;
			}
		} catch (NoSuchElementException e) {
			return found;
		}
		return found;
		
	}
	// update (without password)
	public String update(User user) {
		repository.save(user);
		return "User was updated! "+user.getId();
	}
	
	// delete
	public String deleteById(int id) {
		repository.deleteById(id);
		return "User id "+id+" was deleted!";
	}
	
	// forgot password (get username and name (correct) -> update password)
	public boolean forgotPassword(String username, String name, String newPassword) {
		boolean found = false;
		Optional<User> user = repository.findByUsername(username);
		try{
			if(user.get().getName().equals(name)) {
				found = true;
				user.get().setPassword(newPassword);
				this.update(user.get());
			}
		} catch (NoSuchElementException e) {
			return found;
		}
		return found;
	}
}
