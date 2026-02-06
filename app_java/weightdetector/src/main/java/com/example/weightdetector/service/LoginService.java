package com.example.weightdetector.service;
import org.springframework.stereotype.Service;

import com.example.weightdetector.domain.Role;
import com.example.weightdetector.dto.LoginRequest;
import com.example.weightdetector.dto.LoginResponse;
/**
 * ログイン処理に関する Serivice クラス
 */
@Service
public class LoginService {
    /**
     * 入力された情報（request）を受け取り、ログイン処理の結果（response）を返す
     * @param request
     * @return DTO（LoginResponse）クラスのインスタンス
     */
    public LoginResponse login(LoginRequest request) {
        //店舗IDが未入力 or ユーザーIDが未入力 のとき
        if (request.getStoreId() == null || request.getUserId() == null) {
            return LoginResponse.failure("店舗ID または ユーザーID が未入力です");
        }
        //DB にある店舗IDと照合（Repository クラス未実装、"STOR001" は仮想）
        // 存在しない ID のとき
        if (!"STORE001".equals(request.getStoreId())) {
            return LoginResponse.failure("存在しない店舗IDです");
        }
        //DB にあるユーザーIDと照合（Repository クラス未実装、各IDは仮想）
        // 対応する権限をセット
        else if ("manager001".equals(request.getUserId())) {
            return LoginResponse.success(Role.ADMIN);
        } 
        else if ("staff001".equals(request.getUserId())) {
            return LoginResponse.success(Role.STAFF);
            
        } 
        else {
            return LoginResponse.failure("存在しないユーザーです");
        }
    }
}
