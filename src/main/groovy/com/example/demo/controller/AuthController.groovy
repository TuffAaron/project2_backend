package com.example.demo.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@Controller
class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class)

    @GetMapping("/")
    String home() {
        return "home"
    }

    @GetMapping("/test")
    @ResponseBody
    String test() {
        return "App is running! Troubleshoot branch deployed."
    }

    @GetMapping("/login")
    String login() {
        return "login"
    }

    @GetMapping("/dashboard")
    String dashboard(Model model, @AuthenticationPrincipal OAuth2User principal) {
        log.info("✅ Dashboard endpoint accessed")
        
        if (principal == null) {
            log.warn("No principal found, redirecting to login")
            return "redirect:/login"
        }
        
        // Extract user info safely
        String name = principal.getAttribute("name") ?: principal.getAttribute("login") ?: "User"
        String email = principal.getAttribute("email") ?: "No email"
        String avatar = principal.getAttribute("avatar_url") ?: principal.getAttribute("picture") ?: ""
        String provider = getProvider(principal)
        
        log.info("User logged in: {} via {}", name, provider)
        
        model.addAttribute("name", name)
        model.addAttribute("email", email)
        model.addAttribute("avatar", avatar)
        model.addAttribute("provider", provider)
        
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

    @GetMapping("/api/auth/success")
    Map<String, Object> loginSuccess(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return [
                authenticated: false,
                message: "Not authenticated"
            ]
        }
        
        return [
            authenticated: true,
            message: "Successfully authenticated",
            user: [
                name: principal.getAttribute("name") ?: principal.getAttribute("login"),
                email: principal.getAttribute("email"),
                avatar: principal.getAttribute("avatar_url") ?: principal.getAttribute("picture"),
                provider: getProvider(principal)
            ]
        ]
    }

    @GetMapping("/api/auth/status")
    Map<String, Object> authStatus(@AuthenticationPrincipal OAuth2User principal) {
        boolean isAuthenticated = principal != null
        
        return [
            authenticated: isAuthenticated,
            provider: isAuthenticated ? getProvider(principal) : null,
            message: isAuthenticated ? "User is authenticated" : "User is not authenticated"
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