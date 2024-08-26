package com.yupGG.entity;

import com.yupGG.constant.Role;
import com.yupGG.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity // 나 엔티티야
@Table(name = "member")
@Data
public class Member extends BaseEntity {
    // 기본키 컬럼명 = member_id AI -> 데이터 저장시 1증가
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //알아서 설정
    private String name;
    private String number;

    /// 중복 불허.
    @Column(unique = true)
    private String email;
    //알아서
    private String password;

    private String picture;

    //Enum -> 컴 : 숫자 우리 : 문자
    //데이터베이스 문자 그대로 -> USER, ADMIN
    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto,
                                      PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());


        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.ADMIN);
        return member;
    }


    public Member() {

    }
    @Builder
    public Member(String name, String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        setRole(Role.USER);
    }



    public Member update(String name,String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        return this;
    }

    public void updateMember(String password){
        this.password = password;
    }

}
