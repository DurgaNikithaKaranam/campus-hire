
package com.college.careerportal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        System.out.println("🔥 LOGOUT HIT"); // debug

        session.invalidate();

        return "redirect:/student/login";
    }
}