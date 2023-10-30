package study.datajpa.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class JpaEntity {

    @Column(updatable = false)
    private LocalDateTime createdDate;  // 이 값은 변경불가

    private LocalDateTime updatedDate;

    @PrePersist     // persist, 저장 하기 전 호출
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdDate = now;
        this.updatedDate = now;
    }

    @PreUpdate      // update, 업데이트 하기 전 호출
    public void preUpdate(){
        updatedDate = LocalDateTime.now();
    }
}