package com.springbootcode.store.controllers;

import com.springbootcode.store.config.JwtConfig;
import com.springbootcode.store.dto.JwtResponse;
import com.springbootcode.store.dto.LoginRequest;
import com.springbootcode.store.dto.UserDto;
import com.springbootcode.store.mappers.UserMapper;
import com.springbootcode.store.repositories.UserRepository;
import com.springbootcode.store.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
           @Valid @RequestBody LoginRequest request,
           HttpServletResponse response

    ){
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       request.getEmail(
),
                       request.getPassword()
               )
       );
       var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
       var accessToken = jwtService.generateAccessToken(user);
       var refreshToken = jwtService.generateRefreshToken(user);

       var cookie = new Cookie("refresh_token", refreshToken.toString());
       cookie.setPath("/auth/refresh");
       cookie.setHttpOnly(true);
       cookie.setSecure(true);
       cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
       response.addCookie(cookie);
        System.out.println(cookie.getValue());

        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(
            @CookieValue(value = "refresh_token") String refreshToken
    ) {
        var jwt = jwtService.parseToken(refreshToken);
        if( jwt==null || jwt.isExpired()) {
            System.out.println("Token validation failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var user = userRepository.findById(jwt.getUserId()).orElseThrow();
        var accessToken = jwtService.generateAccessToken(user);

        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));


    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long) authentication.getPrincipal();

        var user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var userDto = userMapper.toUserDto(user);
        return ResponseEntity.ok(userDto);

    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Void> handleBadCredentials(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
