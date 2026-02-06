package com.example.weightdetector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.weightdetector.dto.DeviceSetupCompleteRequest;
import com.example.weightdetector.dto.DeviceSetupCompleteResponse;
import com.example.weightdetector.service.DeviceSetupService;

import jakarta.servlet.http.HttpSession;
/**
 * IoT デバイスセットアップ処理用 Controller クラス
 */
@Controller
public class DeviceSetupCompleteController {
    private final DeviceSetupService deviceSetupService;

    //コンストラクタ
    @Autowired
    public DeviceSetupCompleteController(DeviceSetupService deviceSetupService) {
        this.deviceSetupService = deviceSetupService;
    }

    /**
     * デバイスセットアップの POST リクエストが来たとき
     * セットアップを行う
     * @param entity
     * @return
     */
    @PostMapping("/device/setup/complete")
    public String deviceSetupComplete(@ModelAttribute DeviceSetupCompleteRequest request, HttpSession session, Model model) {
        //ログイン時の 店舗ID と ユーザーID をログに使用するため受け取る（ログ処理未実装）
        String storeId = (String) session.getAttribute("storeId");
        String userId = (String) session.getAttribute("userId");
        //セットアップを行う デバイスID を受け取る
        String deviceId = (String) session.getAttribute("deviceId");
        
        //店舗ID ユーザーID が null なら再ログイン
        if (storeId == null || userId == null) {
            return "redirect:/login";
        }
        //デバイスID nullならデバイス検索へ
        if (deviceId == null) {
            return "redirect:/device/setup";
        }

        //Service のメソッドにデータを渡して処理
        DeviceSetupCompleteResponse response = deviceSetupService.completeSetup(
            deviceId,
            request.getJanCode(),
            userId,
            storeId
        );
        model.addAttribute("response", response);
        return "device/setupComplete";
    }
}
