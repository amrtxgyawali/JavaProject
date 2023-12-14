package ca.lambton.project.service;

import java.util.List;

import ca.lambton.project.dto.UserDto;

public interface UserService {
	UserDto saveUser(UserDto user);

	List<UserDto> getAllUsers();
	
	UserDto getUser(long id);

	List<UserDto> getFilteredUsers(String filterParam);

	void deleteUser(long id);
}
