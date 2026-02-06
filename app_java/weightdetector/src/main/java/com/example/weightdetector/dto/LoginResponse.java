package com.example.weightdetector.dto;
import com.example.weightdetector.domain.Role;

import lombok.Getter;
/**
 * ログイン結果 DTO
 * ログイン処理の結果と、利用者の権限情報、トークンを所持するクラス
 */
@Getter
public class LoginResponse {
    private boolean success;  //処理結果（OK のとき true）
    private Role role;        //ユーザー権限（ADMIN or STAFF）
    private String message;   //処理結果メッセージ
    //トークンは今後実装予定

    //コンストラクタ
    private LoginResponse(boolean success, Role role, String message) {
        this.success = success;
        this.role = role;
        this.message = message;
    }

    /**
     * ログイン成功時のインスタンスを生成
     * @param token
     * @return
     */
    public static LoginResponse success(Role role) {
        return new LoginResponse(
            true, 
            role, 
            "ログインしました");
    }

    /**
     * ログイン失敗時のインスタンスを生成
     * @param message
     * @return
     */
    public static LoginResponse failure(String message) {
        return new LoginResponse(
            false, 
            null, 
            message);
    }
}