package ca.lambton.project.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.lambton.project.dto.UserDto;
import ca.lambton.project.model.User;
import ca.lambton.project.repository.UserRepository;
import ca.lambton.project.service.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public UserDto saveUser(UserDto userDto) {
		if (ObjectUtils.isNotEmpty(userDto.getId()) && userDto.getId() > 0) {
			Optional<User> user = userRepository.findById(userDto.getId());
			if (user.isPresent()) {
				User saveduser = user.get();
				saveduser.setName(userDto.getName());
				saveduser.setEmail(userDto.getEmail());
				saveduser.setPhoneNumber(userDto.getPhoneNumber());
				saveduser.setAddress(userDto.getAddress());
				userRepository.save(saveduser);
				return mapper.map(saveduser, UserDto.class);
			}
		} else {
			User user = mapper.map(userDto, User.class);
			User savedUser = userRepository.save(user);
			return mapper.map(savedUser, UserDto.class);
		}
		return null;
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDto> userList = users.stream().map(user -> mapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		return userList;
	}

	@Override
	public List<UserDto> getFilteredUsers(String filterParam) {
		List<User> users;
		if (filterParam != null && !filterParam.isEmpty()) {
			users = userRepository
					.findAll((Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
						Predicate predicate = criteriaBuilder.or(
								criteriaBuilder.like(root.get("name"), "%" + filterParam + "%"),
								criteriaBuilder.like(root.get("email"), "%" + filterParam + "%"),
								criteriaBuilder.like(root.get("phoneNumber"), "%" + filterParam + "%"),
								criteriaBuilder.like(root.get("address"), "%" + filterParam + "%"));
						return predicate;
					});
		} else {
			users = userRepository.findAll();
		}
		List<UserDto> userList = users.stream().map(user -> mapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		return userList;
	}

	@Override
	public UserDto getUser(long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			UserDto userDto = mapper.map(user.get(), UserDto.class);
			return userDto;

		} else
			return null;

	}

	@Override
	public void deleteUser(long id) {
		userRepository.deleteById(id);
		
	}
}
