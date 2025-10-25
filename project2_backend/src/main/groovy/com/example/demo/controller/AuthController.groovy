package com.example.demo.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@Controller
class AuthController {

    @GetMapping("/")
    String home() {
        return "home"
    }

    @GetMapping("/login")
    String login() {
        return "login"
    }

    @GetMapping("/dashboard")
    String dashboard(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            model.addAttribute("name", principal.getAttribute("name") ?: principal.getAttribute("login") ?: "User")
            model.addAttribute("email", principal.getAttribute("email") ?: "No email provided")
            model.addAttribute("avatar", principal.getAttribute("avatar_url") ?: principal.getAttribute("picture"))
            model.addAttribute("provider", getProvider(principal))
        }
        return "dashboard"
    }

    @GetMapping("/profile")
    String profile(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            model.addAttribute("user", principal.getAttributes())
            model.addAttribute("provider", getProvider(principal))
        }
        return "profile"
    }

    private String getProvider(OAuth2User principal) {
        // Determine provider based on attributes
        if (principal.getAttribute("login") != null) return "GitHub"
        if (principal.getAttribute("picture") != null) return "Google"
        return "Unknown"
    }
}

@RestController
class AuthRestController {

    @GetMapping("/api/user")
    Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return [error: "Not authenticated"]
        }
        
        return [
            name: principal.getAttribute("name") ?: principal.getAttribute("login") ?: "User",
            email: principal.getAttribute("email") ?: "No email provided",
            avatar: principal.getAttribute("avatar_url") ?: principal.getAttribute("picture"),
            provider: getProvider(principal),
            authenticated: true,
            attributes: principal.getAttributes()
        ]
    }

    @GetMapping("/api/public/status")
    Map<String, Object> publicStatus() {
        return [
            status: "OK",
            message: "Public endpoint accessible",
            timestamp: new Date()
        ]
    }

    private String getProvider(OAuth2User principal) {
        if (principal.getAttribute("login") != null) return "GitHub"
        if (principal.getAttribute("picture") != null) return "Google"
        return "Unknown"
    }
}