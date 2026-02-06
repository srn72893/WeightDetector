package com.example.weightdetector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.weightdetector.dto.DeviceSetupCheckRequest;
import com.example.weightdetector.dto.DeviceSetupCheckResponse;
import com.example.weightdetector.dto.JanVerifyRequest;
import com.example.weightdetector.service.DeviceSetupService;

import jakarta.servlet.http.HttpSession;
/**
 * IoT デバイスのセットアップ状態検索用 Controller クラス
 */
@Controller
@RequestMapping("/device/setup")
public class DeviceSetupCheckController {
    private final DeviceSetupService deviceSetupService;

    //コンストラクタ
    @Autowired
    public DeviceSetupCheckController(DeviceSetupService deviceSetupService) {
        this.deviceSetupService = deviceSetupService;
    }

    /**
     * device/setup.html の GET リクエストが来たらページを返す
     * @param model
     * @return
     */
    @GetMapping
    public String showDeviceSetup(Model model) {
        //request DTO オブジェクトをセット
        model.addAttribute("deviceSetupCheckRequest", new DeviceSetupCheckRequest());
        return "device/setup";
    }
    
    /**
     * デバイスがセットアップ可能状態か調べる
     * @param entity
     * @return
     */
    @PostMapping("/check")
    public String deviceSetupCheck(@ModelAttribute DeviceSetupCheckRequest request, Model model, HttpSession session) {
        DeviceSetupCheckResponse response = deviceSetupService.checkSetupAvailability(request);
        //遷移先に渡す 結果 と request DTO オブジェクトをセット
        model.addAttribute("response", response);
        model.addAttribute("janVerifyRequest", new JanVerifyRequest(response.getDeviceId()));
        //DeviceSetupComplete で使うデバイスIDをセッションでセット
        session.setAttribute("deviceId", response.getDeviceId());
        return "product/search";
    }
}
