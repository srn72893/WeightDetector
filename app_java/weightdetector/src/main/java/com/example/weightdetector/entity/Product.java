package com.example.weightdetector.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * DB の対象商品情報レコードを表す
 * Repository クラスが返す実態
 */
@Entity
@Setter
@Getter
@Table(name = "products")
public class Product {
    //JAN コード（主キー）
    @Id
    @Column(name = "jan_code")
    String janCode;

    //商品名
    @Column(name = "product_name")
    String productName;

    //デフォルトコンストラクタ
    protected Product() {}

    //コンストラクタ
    public Product(String janCode, String productName) {
        this.janCode = janCode;
        this.productName = productName;
    }
}
