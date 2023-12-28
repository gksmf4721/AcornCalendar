package acorn.calendar.com.member.domain.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name="JH_MEMBER")
public class  MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "M_SEQ")
    private long memberSeq;

    @Column(name = "M_NAME")
    private String memberName;

    @Column(name = "M_ID")
    private String memberId;

    @Column(name = "M_PW")
    private String memberPw;

    @Column(name = "M_NICKNAME")
    private String memberNickname;

    @Column(name = "M_EMAIL")
    private String memberEmail;

    @Column(name = "M_BIRTH")
    private String memberBirth;

    @Column(name = "M_DEL_YN")
    private String memberDelYn;
}
