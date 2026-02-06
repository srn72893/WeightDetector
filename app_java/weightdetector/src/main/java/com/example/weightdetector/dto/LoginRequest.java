package com.example.weightdetector.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * ログイン要求 DTO
 * 店舗ID および ユーザーIDを用いて利用者を識別するための
 * 入力データを保持するクラス
 */
@Getter
@Setter
public class LoginRequest {
    private String storeId; //店舗ID
    private String userId;  //ユーザーID
}