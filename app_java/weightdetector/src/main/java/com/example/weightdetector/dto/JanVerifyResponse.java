package com.example.weightdetector.dto;

import lombok.Getter;

/**
 * JANコード検証結果 DTO
 * JANコード検証結果と、商品情報を保持するクラス
 */
@Getter
public class JanVerifyResponse {
    private String janCode;     //JANコード
    private String productName; //商品名

    //コンストラクタ
    public JanVerifyResponse(String janCode, String productName) {
        this.janCode = janCode;
        this.productName = productName;
    }
} 
