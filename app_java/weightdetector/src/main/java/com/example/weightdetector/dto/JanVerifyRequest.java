package com.example.weightdetector.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * JAN コード検証要求 DTO
 * JAN コードから商品検索を行う処理に使用されるデータ
 */
@Getter
@Setter
public class JanVerifyRequest {
    
    private String deviceId;    //商品を登録するIoTデバイス
    private String janCode;     //ユーザーが入力したJANコード

    public JanVerifyRequest() {

    }
    
    public JanVerifyRequest(String deviceId) {
        this.deviceId = deviceId;
    }
}