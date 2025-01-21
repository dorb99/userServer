package servers.fullServer.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import servers.fullServer.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	List<User> findByName(String name);
	Optional<User> findByUsername(String username);
}
