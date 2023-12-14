package ca.lambton.project;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import ca.lambton.project.controller.UserController;
import ca.lambton.project.dto.UserDto;
import ca.lambton.project.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testShowForm() {
        Model model = mock(Model.class);

        String result = userController.showForm(model);

        assertEquals("createUser", result);
        verify(model).addAttribute(eq("user"), any(UserDto.class));
    }

    @Test
    public void testLoadForm() {
        Model model = mock(Model.class);
        long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        when(userService.getUser(userId)).thenReturn(userDto);

        String result = userController.loadForm(userId, model);

        assertEquals("createUser", result);
        verify(model).addAttribute(eq("user"), eq(userDto));
    }

    @Test
    public void testSubmitFormWithErrors() {
        UserDto userDto = new UserDto();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        String viewName = userController.submitForm(userDto, result);

        assertEquals("createUser", viewName);
        verify(userService, never()).saveUser(any(UserDto.class));
    }

    @Test
    public void testSubmitFormWithoutErrors() {
        UserDto userDto = new UserDto();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        String viewName = userController.submitForm(userDto, result);

        assertEquals("redirect:/user/list", viewName);
        verify(userService).saveUser(userDto);
    }

    @Test
    public void testDeleteUser() {
        long userId = 1L;

        String viewName = userController.deleteUser(userId, mock(Model.class));

        assertEquals("redirect:/user/list", viewName);
        verify(userService).deleteUser(userId);
    }

    @Test
    public void testShowListWithFilterParam() {
        String filterParam = "test";
        Model model = mock(Model.class);
        List<UserDto> filteredUsers = Collections.singletonList(new UserDto());
        when(userService.getFilteredUsers(filterParam)).thenReturn(filteredUsers);

        String viewName = userController.showList(filterParam, model);

        assertEquals("userList", viewName);
        verify(model).addAttribute(eq("users"), eq(filteredUsers));
        verify(model).addAttribute(eq("filterParam"), eq(filterParam));
    }

    @Test
    public void testShowListWithoutFilterParam() {
        Model model = mock(Model.class);
        List<UserDto> allUsers = Collections.singletonList(new UserDto());
        when(userService.getAllUsers()).thenReturn(allUsers);

        String viewName = userController.showList(null, model);

        assertEquals("userList", viewName);
        verify(model).addAttribute(eq("users"), eq(allUsers));
        verify(model, never()).addAttribute(eq("filterParam"), anyString());
    }
}
