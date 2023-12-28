package acorn.calendar.com.member.domain.security;

import acorn.calendar.com.member.domain.entity.MemberEntity;
import acorn.calendar.com.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberEntity> member = Optional.ofNullable(memberRepository.findByMemberId(username).orElseThrow(
                    () -> new UsernameNotFoundException("Invalid Authentication")
                ));

        return UserDetail.builder()
                .memberId(member.get().getMemberId())
                .memberPw(member.get().getMemberPw())
                .build();
    }
}
