package com.workintech.demo.service;


import com.workintech.demo.entity.Member;
import com.workintech.demo.entity.Role;
import com.workintech.demo.repository.MemberRepository;
import com.workintech.demo.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthenticationService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public Member register(String email, String password) {
        Optional<Member> byEmail = memberRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            throw new RuntimeException("User already exist! with the email " + email);
        }

        String encodedPassword = passwordEncoder.encode(password);

        List<Role> roleList = new ArrayList<>();


        Optional<Role> roleAdmin = roleRepository.findByAuthority("ADMIN");
        if (!roleAdmin.isPresent()) {
            Role roleAdminEntity = new Role();
            roleAdminEntity.setAuthority("ADMIN");
            roleList.add(roleRepository.save(roleAdminEntity));
        } else {
            roleList.add(roleAdmin.get());
        }


        Member member = new Member();
        member.setEmail(email);
        member.setPassword(encodedPassword);
        member.setRoles(roleList);
        return memberRepository.save(member);

    }

}