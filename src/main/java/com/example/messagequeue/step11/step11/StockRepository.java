package com.example.messagequeue.step11.step11;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 기본 CRUD 자동 구현
 * 메서드 이름 기반의 쿼리 생성
 * 복잡한 쿼리 지원
 * 코드를 거의 안쓰고 DB 조작 가능
 **/

public interface StockRepository extends JpaRepository<StockEntity, Long> {
}
