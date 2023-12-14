package ca.lambton.project.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.lambton.project.dto.UserDto;
import ca.lambton.project.service.UserService;
import ca.lambton.project.utils.ProjectUtils;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	// return create user form view
	@GetMapping("/create")
	public String showForm(Model model) {
		ProjectUtils.updatePageHits();
		model.addAttribute("user", new UserDto());
		return "createUser";
	}

	// return create user view for edit
	@GetMapping("/edit/{id}")
	public String loadForm(@PathVariable long id, Model model) {
		ProjectUtils.updatePageHits();
		UserDto user = userService.getUser(id);
		model.addAttribute("user", user);
		return "createUser";
	}

	// saves/updates user details and returns user list view
	@PostMapping("/save")
	public String submitForm(@Valid @ModelAttribute UserDto user, BindingResult result) {
		if (result.hasErrors()) {
			return "createUser";
		}

		userService.saveUser(user);
		return "redirect:/user/list";
	}

	// deletes the user and returns userlist view
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable long id, Model model) {
		userService.deleteUser(id);
		return "redirect:/user/list";
	}

	// returns the userlist view with list of all user
	// if called with filter params, returns view with filtered user list
	@GetMapping("/list")
	public String showList(@RequestParam(required = false) String filterParam, Model model) {
		List<UserDto> users;
		ProjectUtils.updatePageHits();
		if (StringUtils.isNotBlank(filterParam)) {
			users = userService.getFilteredUsers(filterParam);
		} else {
			users = userService.getAllUsers();
		}

		model.addAttribute("users", users);
		model.addAttribute("filterParam", filterParam);
		return "userList";
	}

}
