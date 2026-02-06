package com.example.weightdetector.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.weightdetector.dto.LoginResponse;

import jakarta.servlet.http.HttpSession;
/**
 * ホーム画面用 Controller クラス
 */
@Controller
@RequestMapping
public class HomeController {

    /**
     * home.html の GET リクエストが来たらページを返す
     * @return
     */
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        //ログインしているユーザー情報取得
        LoginResponse user = (LoginResponse) session.getAttribute("loginUser");
        //null なら再ログイン
        if (user == null) {
            return "redirect:/login";
        }
        if (user.getRole() == null) {
            return "redirect:/login";
        }
        //権限保持
        model.addAttribute("role", user.getRole());
        return "home";
    }
}
