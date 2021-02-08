package net.afo.todoappdemo.rest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.afo.todoappdemo.dto.AdminUserDto;
import net.afo.todoappdemo.dto.AuthenticationRequestDto;
import net.afo.todoappdemo.model.EStatus;
import net.afo.todoappdemo.model.RoleEntity;
import net.afo.todoappdemo.model.UserEntity;
import net.afo.todoappdemo.service.UserService;

@RestController
@RequestMapping(value = "/api/admin/")
public class AdminRestController {

    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }
    
    

    @GetMapping(value = "users/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id) {
        UserEntity user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        AdminUserDto result = AdminUserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping(value = "users")
    public ResponseEntity<List<AdminUserDto>> getAll() {
        List<UserEntity> users = userService.getAll();

        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        List<AdminUserDto> result = new ArrayList<>();

        users.forEach(user -> {
            result.add(AdminUserDto.fromUser(user));
        });
        return new ResponseEntity<>(result, HttpStatus.OK);
        //AdminUserDto result = AdminUserDto.fromUser(user);

        //return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @PostMapping(value = "createadmin")
    public ResponseEntity<AdminUserDto> createAdmin(@RequestBody AuthenticationRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        
        UserEntity user = userService.findByUsername(username);

        if (user == null) {
            user = new UserEntity();                
            
            user.setEmail("test@test.t");//TODO maybe it will be good to send full data to server (not only log/pass)
            user.setFirstName(" ");
            user.setLastName(" ");
            
            user.setUsername(username);
            user.setPassword(password);
            user.setStatus(EStatus.ACTIVE);
            userService.register(user, true);//register user with user && admin roots
        }
        else 
            if (user.getStatus().equals(EStatus.NOT_ACTIVE) && user.getStatus().equals(EStatus.DELETED)) {
            	userService.setUserActive(user);
            }
            else return new ResponseEntity<AdminUserDto>(AdminUserDto.fromUser(user),HttpStatus.CONFLICT);
        
        return ResponseEntity.ok(AdminUserDto.fromUser(user));
        
    }
    
}
