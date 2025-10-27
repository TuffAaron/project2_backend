package com.example.demo.controller;

import com.example.demo.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

@Controller
class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/")
    String home() {
        return "home";
    }

    @GetMapping("/test")
    @ResponseBody
    String test() {
        return "App is running! Troubleshoot branch deployed.";
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    String dashboard(Model model, @AuthenticationPrincipal OAuth2User principal) {
        log.info("✅ Dashboard endpoint accessed");

        if (principal == null) {
            log.warn("No principal found, redirecting to login");
            return "redirect:/login";
        }

        String name = principal.getAttribute("name") != null ? principal.getAttribute("name") : principal.getAttribute("login");
        String email = principal.getAttribute("email") != null ? principal.getAttribute("email") : "No email";
        String avatar = principal.getAttribute("avatar_url") != null ? principal.getAttribute("avatar_url") : principal.getAttribute("picture");
        String provider = getProvider(principal);

        log.info("User logged in: {} via {}", name, provider);

        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("avatar", avatar);
        model.addAttribute("provider", provider);

        return "dashboard";
    }

    @GetMapping("/profile")
    String profile(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            model.addAttribute("user", principal.getAttributes());
            model.addAttribute("provider", getProvider(principal));
        }
        return "profile";
    }

    private String getProvider(OAuth2User principal) {
        if (principal.getAttribute("login") != null) return "GitHub";
        if (principal.getAttribute("picture") != null) return "Google";
        return "Unknown";
    }

    // -------------------------------
    // Mobile OAuth redirect endpoint
    // -------------------------------
    @GetMapping("/login/mobile/success")
    void mobileOAuthRedirect(HttpServletResponse response, @AuthenticationPrincipal OAuth2User principal) throws IOException {
        if (principal == null) {
            response.sendRedirect("/login");
            return;
        }

        String username = principal.getAttribute("name") != null ? principal.getAttribute("name") : principal.getAttribute("login");
        String token = JwtUtil.generateToken(username);

        String redirectUri = "exp://10.0.0.14:8081" +
                "?token=" + URLEncoder.encode(token, "UTF-8") +
                "&name=" + URLEncoder.encode(username, "UTF-8") +
                "&authenticated=true";

        log.info("Redirecting mobile user to: {}", redirectUri);
        response.sendRedirect(redirectUri);
    }
}

@RestController
class AuthRestController {

    @GetMapping("/api/user")
    Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return Map.of("error", "Not authenticated");
        }

        return Map.of(
                "name", principal.getAttribute("name") != null ? principal.getAttribute("name") : principal.getAttribute("login"),
                "email", principal.getAttribute("email") != null ? principal.getAttribute("email") : "No email provided",
                "avatar", principal.getAttribute("avatar_url") != null ? principal.getAttribute("avatar_url") : principal.getAttribute("picture"),
                "provider", getProvider(principal),
                "authenticated", true,
                "attributes", principal.getAttributes()
        );
    }

    @GetMapping("/api/public/status")
    Map<String, Object> publicStatus() {
        return Map.of(
                "status", "OK",
                "message", "Public endpoint accessible",
                "timestamp", new Date()
        );
    }

    private String getProvider(OAuth2User principal) {
        if (principal.getAttribute("login") != null) return "GitHub";
        if (principal.getAttribute("picture") != null) return "Google";
        return "Unknown";
    }
}
