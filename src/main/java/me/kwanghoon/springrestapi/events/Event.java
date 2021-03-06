package me.kwanghoon.springrestapi.events;

import lombok.*;
import me.kwanghoon.springrestapi.accounts.Account;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
@Entity
public class Event {

    @Id @GeneratedValue
    private Integer id;

    private String name;

    private String description;

    private LocalDateTime beginEnrollmentDateTime;

    private LocalDateTime closeEnrollmentDateTime;

    private LocalDateTime beginEventDateTime;

    private LocalDateTime endEventDateTime;

    private int limitOfEnrollment;

    private boolean offline;

    private boolean free;

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;

    private String location; // optional 이게 없으면 온라인 모임

    private int basePrice; // optional

    private int maxPrice; // optional

    @ManyToOne
    private Account manager;

    public void update() {
        if (this.basePrice == 0 && this.maxPrice == 0) {
            this.free = true;
        } else  {
            this.free = false;
        }

        /**
         * isBlank(): java 11에 추가됨, 공백도 비어있는 것으로 처리
         * isEmpty(): 공백을 비어있지 않은걸로 치리, trim() 으로 공백 제거하고 체크해줘야함
         * 참고: https://hilucky.tistory.com/217
         */
        if (this.location == null || this.location.isBlank()) {
            this.offline = false;
        } else {
            this.offline = true;
        }
    }
}
