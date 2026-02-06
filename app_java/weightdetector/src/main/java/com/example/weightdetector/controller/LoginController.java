package com.example.weightdetector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.weightdetector.dto.LoginRequest;
import com.example.weightdetector.dto.LoginResponse;
import com.example.weightdetector.service.LoginService;

import jakarta.servlet.http.HttpSession;
/**
 * ログイン用 Controller クラス
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;

    //コンストラクタ
    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * login.html の GET リクエストが来たらページを返す
     * @param model
     * @return login.html
     */
    @GetMapping
    public String showLogin(Model model) {
        //request DTO オブジェクトをセット
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }
    
    /**
     * ログイン情報入力フォームから POST リクエスト時にログイン処理を行う
     * @param request
     * @param model
     * @param session
     * @return
     */
    @PostMapping
    public String login(@ModelAttribute LoginRequest request, Model model, HttpSession session) {
        //ログイン情報照合
        LoginResponse response = loginService.login(request);

        if (response.isSuccess()) {
            //デバイスセットアップ時にログとして残す 店舗ID と ユーザーID をセット（ログ処理未実装）
            session.setAttribute("storeId", request.getStoreId());
            session.setAttribute("userId", request.getUserId());
            //権限の保持（将来トークン使う拡張のためオブジェクトもセット）
            session.setAttribute("loginUser", response);
            session.setAttribute("role", response.getRole());
            return "redirect:/home";
        } else {
            //エラーメッセージをセット
            model.addAttribute("errorMessage", response.getMessage());
            //失敗 再度入力させる login.html を返す
            return "login";
        }
    }
}
