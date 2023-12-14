package ca.lambton.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;

import ca.lambton.project.dto.UserDto;
import ca.lambton.project.model.User;
import ca.lambton.project.repository.UserRepository;
import ca.lambton.project.serviceImpl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private ModelMapper mapper;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	public void testSaveNewUser() {
		// Mock data
		UserDto newUserDto = new UserDto(null, "Amrit Gyanwali", "amrtxgyawali@gmail.com", "123456789", "Test Address");
		User newUser = new User(null, "Amrit Gyanwali", "amrtxgyawali@gmail.com", "123456789", "Test Address");

		// Mocking the mapper behavior
		when(mapper.map(newUserDto, User.class)).thenReturn(newUser);
		when(mapper.map(newUser, UserDto.class)).thenReturn(newUserDto);

		// Mocking the userRepository behavior
		when(userRepository.save(newUser)).thenReturn(newUser);

		// Calling the actual method
		UserDto savedUserDto = userService.saveUser(newUserDto);

		// Verifying the result
		assertEquals(newUserDto, savedUserDto);
	}

	@Test
	public void testUpdateExistingUser() {
		// Mock data
		Long existingUserId = 1L;
		UserDto existingUserDto = new UserDto(existingUserId, "Amrit Gyanwali", "amrtxgyawali@gmail.com", "123456789",
				"Test Address");
		User existingUser = new User(existingUserId, "Amrit Gyanwali", "amrtxgyawali@gmail.com", "123456789",
				"Test Address");

		when(mapper.map(existingUser, UserDto.class)).thenReturn(existingUserDto);
		when(userRepository.findById(existingUserId)).thenReturn(Optional.of(existingUser));
		when(userRepository.save(existingUser)).thenReturn(existingUser);
		UserDto updatedUserDto = userService.saveUser(existingUserDto);
		assertEquals(existingUserDto, updatedUserDto);
	}

	@Test
	public void testGetFilteredUsers() {
		// Mock data
		String filterParam = "test";
		List<User> filteredUsers = Arrays.asList(
				new User(1L, "Amrit Gyanwali", "amrtxgyawali@gmail.com", "123456789", "Test Address"),
				new User(2L, "Indu Thapa", "induxgyawali@gmail.com", "987654321", "Tester Street"));

		// Mocking the userRepository behavior with a Specification
		when(userRepository.findAll(any(Specification.class))).thenReturn(filteredUsers);

		// Mocking the mapper behavior
		when(mapper.map(any(User.class), any())).thenAnswer(invocation -> {
			User sourceUser = invocation.getArgument(0);
			UserDto userDto = new UserDto();
			userDto.setId(sourceUser.getId());
			userDto.setName(sourceUser.getName());
			userDto.setEmail(sourceUser.getEmail());
			userDto.setPhoneNumber(sourceUser.getPhoneNumber());
			userDto.setAddress(sourceUser.getAddress());
			return userDto;
		});

		List<UserDto> result = userService.getFilteredUsers(filterParam);
		assertEquals(filteredUsers.size(), result.size());
	}

	@Test
	public void testGetUser() {
		long userId = 1L;
		User user = new User();
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(mapper.map(user, UserDto.class)).thenReturn(new UserDto());
		UserDto result = userService.getUser(userId);
		assertNotNull(result);
	}

	@Test
	public void testGetUserNotFound() {
		long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());
		UserDto result = userService.getUser(userId);
		assertNull(result);
	}

	@Test
	public void testDeleteUser() {
		long userId = 1L;
		userService.deleteUser(userId);
		verify(userRepository).deleteById(userId);
	}
}
