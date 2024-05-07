package com.midsem.controller;

import com.midsem.model.User;
import com.midsem.service.UserInterface;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class AppController {
    @Autowired
    UserInterface userService;

    @GetMapping("")
    public String viewSignupPage() {
        return "index";
    }

    @PostMapping("/processsignup")
    public String processSignUp(User user, RedirectAttributes redirectAttrs) {
        user.setRole("Student");
        Boolean success = userService.createUser(user);
        if (success) {
            redirectAttrs.addFlashAttribute("successMessage", "Registration successful!");
            return "redirect:/login";
        } else {
            redirectAttrs.addFlashAttribute("errorMessage", "Registration failed. Username or email might already be in use.");
            return "redirect:/";
        }
    }

    @GetMapping("/login")
    public String viewSigninPage() {
        return "login";
    }

    @PostMapping("/processlogin")
    public String processLogin(User userDetails, RedirectAttributes redirectAttrs,
                               @RequestParam(defaultValue = "false") boolean rememberMe,
                               HttpSession httpSession, HttpServletResponse response) {
        Optional<User> userFoundOpt = userService.findByUsername(userDetails.getUsername());
        if (userFoundOpt.isPresent()) {
            User userFound = userFoundOpt.get();
            if (userDetails.getPassword().equals(userFound.getPassword())) {
                httpSession.setAttribute("user", userFound);
                // Set the session timeout
                httpSession.setMaxInactiveInterval(1800);


                if (rememberMe) {

                    Cookie cookie = new Cookie("rememberMe", userDetails.getUsername() + ":" + userDetails.getPassword());
                    cookie.setMaxAge(600);
                    ; // Cookie expires in 10 i
                    cookie.setPath("/"); // Set cookie path
                    response.addCookie(cookie);
                    System.out.println("The cookies " + cookie);
}

                if ("Admin".equals(userFound.getRole())) {
                    return "redirect:/admin/quizzes";
                } else {
                    return "redirect:/user/dashboard";
                }
            } else {
                redirectAttrs.addFlashAttribute("errorMessage", "Invalid password!");
                return "redirect:/login";
            }
        } else {
            redirectAttrs.addFlashAttribute("errorMessage", "User not found!");
            return "redirect:/login";
        }
    }
//
//    // Method to generate remember me token (for demonstration purposes)
//    private String generateRememberMeToken(User user) {
//        String username = user.getUsername(); // Assuming you have a method to get the username
//        String timestamp = String.valueOf(System.currentTimeMillis()); // Current timestamp
//
//        // Concatenate username and timestamp
//        String tokenData = username + timestamp;
//
//        // Hash the token data
//        String hashedToken = hashString(tokenData);
//
//        return hashedToken;
//    }
//
//    private String hashString(String data) {
//        try {
//            // Create SHA-256 message digest instance
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//
//            // Hash the data
//            byte[] hashedBytes = digest.digest(data.getBytes());
//
//            // Convert hashed bytes to base64 string
//            String base64Hash = Base64.getEncoder().encodeToString(hashedBytes);
//
//            return base64Hash;
//        } catch (NoSuchAlgorithmException e) {
//            // Handle hash algorithm not found exception
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    // Method to set remember me cookie
//    private void setRememberMeCookie(HttpServletResponse response, String rememberMeToken) {
//        Cookie cookie = new Cookie("rememberMeToken", rememberMeToken);
//        cookie.setMaxAge(30 * 24 * 60 * 60); // Cookie expires in 30 days (adjust as needed)
//        cookie.setPath("/"); // Apply to entire domain
//        response.addCookie(cookie);
//    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.invalidate();
//        Cookie rememberMeCookie = new Cookie("rememberMeToken", null);
//        rememberMeCookie.setMaxAge(0); // 0 means delete the cookie
//        rememberMeCookie.setPath("/"); // Apply to entire domain
//        response.addCookie(rememberMeCookie);
        return "/login";
    }
}
