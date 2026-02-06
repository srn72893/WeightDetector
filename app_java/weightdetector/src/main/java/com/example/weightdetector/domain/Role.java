package com.example.weightdetector.domain;
/**
 * ログイン権限の enum クラス
 */
public enum Role {
    ADMIN,  //管理者
    STAFF,  //スタッフ
    ;

    /**
     * 状態が ADMIN なら true を返す
     * @return
     */
    public boolean isAdmin() {
        return this == ADMIN;
    }

    /**
     * 状態が STAFF なら true を返す
     * @return
     */
    public boolean isStaff() {
        return this == STAFF;
    }
}
