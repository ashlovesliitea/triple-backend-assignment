package com.example.triple_mileage.domain.entity;

import com.example.triple_mileage.domain.PointSituation;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long pointId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @NotNull
    private int changedPoint; //포인트 증감 내역

    @NotNull
    private PointSituation pointSituation;


    @Column(columnDefinition = "TIMESTAMP")
    @NotNull
    private ZonedDateTime date;


    public static PointHistory createPointHistory(User user, PointSituation pointSituation, int changed_point){
        PointHistory pointHistory=new PointHistory();
        pointHistory.setUser(user);
        pointHistory.setPointSituation(pointSituation);
        pointHistory.setChangedPoint(changed_point);
        pointHistory.setDate(ZonedDateTime.now(ZoneId.of("Asia/Seoul")));

        return pointHistory;
    }
}
