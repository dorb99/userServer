package servers.fullServer.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank @Size(min=2, max=10) 
	private String name;
	
	@NotBlank @Size(min=2)
	@Column(unique = true)
	private String username;
	
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$")
	// At least one upper case letter, one lower case letter, one special character and one number, at least 8 characters
	private String password;
	
	@Min(1)
	private int age;
	
	// create an empty constructor
	public User() {}
	
	public User(String name, String username, String password, int age) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.age = age;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User name "+this.name+" of age "+this.age+" and his data is: "
				+ "[username: "+this.username+", password: "+this.password+"]";
	}
	
	
}
