package com.example.weightdetector.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.weightdetector.entity.Product;
/**
 * DB の products デーブルへの処理を行う Repository インタフェース
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String>{
    /**
     * 引数の JanCode と一致するレコードを取得
     * @param janCode SELECT する Jan_code の値
     * @return
     */
    Optional<Product> findByJanCode(String janCode);

    /**
     * 引数の productName と一致するレコードを取得
     * 同じ商品が異なる JAN コードを持つ場合があるので、複数ヒット用に戻り値は List で受け取る
     * （未使用）
     * @param productName SELECT する product_name の値
     * @return
     */
    List<Product> findByProductName(String productName);
}
