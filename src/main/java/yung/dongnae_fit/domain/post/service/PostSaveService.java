package yung.dongnae_fit.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.member.repository.MemberRepository;
import yung.dongnae_fit.domain.post.entity.Post;
import yung.dongnae_fit.domain.post.repository.PostRepository;
import yung.dongnae_fit.domain.postLike.entity.PostLike;
import yung.dongnae_fit.domain.postLike.repository.PostLikeRepository;
import yung.dongnae_fit.domain.postSave.entity.PostSave;
import yung.dongnae_fit.domain.postSave.repository.PostSaveRepository;
import yung.dongnae_fit.global.RequestScopedStorage;

@RequiredArgsConstructor
@Log4j2
@Service
public class PostSaveService {
    private final MemberRepository memberRepository;
    private final RequestScopedStorage requestScopedStorage;
    private final PostRepository postRepository;
    private final PostSaveRepository postSaveRepository;

    @Transactional
    public void toggleSave(Long postId) {
        String kakaoId = requestScopedStorage.getKakaoId();
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. Kakao ID: " + kakaoId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));

        postSaveRepository.findByPostAndMember(post, member)
                .ifPresentOrElse(
                        postSave -> postSaveRepository.deleteAllByPostAndMember(post, member),
                        () -> {
                            PostSave newPostSave = PostSave.builder()
                                    .post(post)
                                    .member(member)
                                    .build();
                            postSaveRepository.save(newPostSave);
                        });

    }
}