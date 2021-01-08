package com.young.mreview.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// 데이터의 등록시간, 수정시간을 자동으로 추가, 변경하는 컬럼을 만들기위한 클래스
@MappedSuperclass // 이 클래스는 테이블로 생성되지 않음
@EntityListeners(value = {AuditingEntityListener.class }) // JPA 내부에서 엔터티 객체가 생성/변경되는 것을 감지하는 역할
@Getter
abstract class BaseEntity {

    @CreatedDate
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime modDate;

}
