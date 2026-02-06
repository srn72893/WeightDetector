package com.example.weightdetector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.weightdetector.dto.DeviceSetupCompleteRequest;
import com.example.weightdetector.dto.JanVerifyRequest;
import com.example.weightdetector.dto.JanVerifyResponse;
import com.example.weightdetector.service.JanVerifyService;
/**
 * JAN コード検索用 Controller クラス
 */
@Controller
@RequestMapping("/product/search")
public class JanVerifyController {
    private final JanVerifyService janVerifyService;

    //コンストラクタ
    @Autowired
    public JanVerifyController(JanVerifyService janVerifyService) {
        this.janVerifyService = janVerifyService;
    }

    /**
     * product/search.html の GET リクエストが来たらページを返す
     * @param model
     * @return
     */
    @GetMapping
    public String showProductSearch(Model model) {
        //request 用オブジェクト渡す
        model.addAttribute("janVerifyRequest", new JanVerifyRequest());
        return "product/search";
    }

    /**
     * JAN 入力フォームから POST リクエストが来たとき商品検索処理を行う
     * @param request
     * @param model
     * @param session
     * @return
     */
    @PostMapping
    public String searchProduct(@ModelAttribute JanVerifyRequest request, Model model) {
        //JAN に対応する商品を DB から探す
        JanVerifyResponse janResponse =  janVerifyService.findProduct(request);

        DeviceSetupCompleteRequest completeRequest = new DeviceSetupCompleteRequest();        
        //検索結果の JAN コードを、デバイスセットアップ request DTO にセット
        completeRequest.setJanCode(janResponse.getJanCode());

        //遷移先に渡すため 商品検索の結果 と 次処理用 request オブジェクトを渡す
        model.addAttribute("response", janResponse);
        model.addAttribute("deviceSetupCompleteRequest", completeRequest);
        return "product/confirm";
    }
}
