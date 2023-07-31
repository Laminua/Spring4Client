package com.example.springexercise3boot.controllers;

import com.example.springexercise3boot.dto.UserProfileDTO;
import com.example.springexercise3boot.services.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/")
public class AdminFrontController {

    private final AdminService adminService;

    @GetMapping("index")
    public ModelAndView showAdminPage() {
        log.debug("Front backend request: show Admin page");

        return new ModelAndView("admin/index");
    }

    @GetMapping(value = "users")
    public ResponseEntity<List<UserProfileDTO>> getUsers() {
        log.debug("Front backend request: requesting list of UserProfiles");

        return adminService.getUsers();
    }

    @GetMapping("addUserForm")
    public ModelAndView showAddUserForm() {
        log.debug("Front backend request: show \"Add user\" page");

        return new ModelAndView("admin/add-user");
    }

    @PostMapping("users")
    public ResponseEntity<String> addUser(UserProfileDTO profileDTO, BindingResult bindingResult)
            throws BindException {
        log.debug("Front backend request: requesting endpoint to create UserProfile");

        return adminService.addUser(profileDTO, bindingResult);
    }

    @GetMapping("updateUserForm")
    public ModelAndView showUpdateUserForm() {
        log.debug("Front backend request: show \"Update user\" page");

        return new ModelAndView("admin/update-user");
    }

    @PostMapping("updateUser")
    public ResponseEntity<String> updateUser(UserProfileDTO profileDTO, BindingResult bindingResult)
            throws BindException {
        log.debug("Front backend request: updating UserProfile in database. Name: {}, Email: {}",
                profileDTO.getName(), profileDTO.getEmail());

        return adminService.updateUser(profileDTO, bindingResult);
    }

    @GetMapping("users/{id}")
    public ResponseEntity getUserProfileById(@PathVariable long id) {
        log.debug("Front backend request: requesting UserProfile with id {}", id);

        return adminService.getUserProfileById(id);
    }

    @GetMapping("deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam("id") long id) {
        log.debug("Front backend request: requested deletion of UserProfile with id {}", id);

        return adminService.deleteUser(id);
    }
}