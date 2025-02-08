package com.example.security;

import com.example.dto.student.StudentDto;
import com.example.dto.teacher.TeacherDto;
import com.example.enums.Roles;
import com.example.dto.UserDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JwtService {

    private Key key;

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.duration}")
    private long duration;

    @PostConstruct
    private void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(TeacherDto userDto, List<Roles> roles) {
        final JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuedAt(new Date())
                .claim("id", userDto.getId())
                .claim("email",userDto.getEmail())
                .claim("roles",roles)
                .setExpiration(Date.from(Instant.now().plusSeconds(duration)))
                .setHeader(Map.of(Header.TYPE, Header.JWT_TYPE))
                .signWith(key, SignatureAlgorithm.HS512);
        return jwtBuilder.compact();
    }

    public String refreshToken(TeacherDto teacherDto) {
        final JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuedAt(new Date())
                .claim("id", teacherDto.getId())
                .setExpiration(Date.from(Instant.now().plusSeconds(duration)))
                .setHeader(Map.of(Header.TYPE, Header.JWT_TYPE))
                .signWith(key, SignatureAlgorithm.HS512);
        return jwtBuilder.compact();
    }

    public Authentication  validateAccessToken(String acssessToken) {
           Claims claims = parseToken(acssessToken);

           if(claims.getExpiration().before(new Date())) {
               log.info("JWT exp date is expired");
               throw new JwtException("Expired or invalid JWT token");
            }
           Long id =    claims.get("id", Long.class);
           String email = (String) claims.get("email");
           List<String> rolesList =  (List<String>) claims.get("roles");

           UserDto userDto = new UserDto();
           userDto.setId(id);
           userDto.setEmail(email);

           final  List<GrantedAuthority> authorities = new ArrayList<>();
           rolesList.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));


           Authentication authentication =
                   new UsernamePasswordAuthenticationToken(userDto, "",authorities); //aAuthentication obyektini yaradir

        return authentication;
    }

    private Claims parseToken(String accessToken) {     // accses tokenin bizim key le genarete olundugunu yoxlayiriq
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }

    public Long  validateRefreshToken(String refreshToken) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();
        if(claims.getExpiration().before(new Date())) {
            log.info("JWT exp date is expired");
            throw new JwtException("Expired or invalid JWT token");
        }
        return  claims.get("id", Long.class);


     }


    public String refreshToken(StudentDto studentDto) {
        return null;
    }

    public String generateToken(StudentDto studentDto, List<Roles> roles) {
        return null;
    }
}
