package yung.dongnae_fit.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import yung.dongnae_fit.domain.memberComment.entity.MemberComment;
import yung.dongnae_fit.domain.post.entity.Post;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profile;

    private String name;

    private String region;

    private String province;

    private String district;

    private Double latitude;

    private Double longitude;

    private String kakaoId;

    private String profileImage;

    private String refreshToken;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberComment> memberComment = new ArrayList<>();


    @Builder
    public Member(String kakaoId, String refreshToken) {
        this.kakaoId = kakaoId;
        this.refreshToken = refreshToken;
    }
}
