package com.zosh.jwt;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.zosh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader=request.getHeader("Authorization");

        final String jwtToken;
        final String email;

        //Check if Authorization header is present and starts with "Bearer"
        if(authHeader==null || ! authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return ;
        }

        //Extract JWT Token from the header
        jwtToken=authHeader.substring(7);
        email=jwtService.extractEmail(jwtToken);


        //Check if we have a username and no authentication exists yet
        if(email!=null && SecurityContextHolder
                .getContext()
                .getAuthentication()==null) {


            //Get the user details for the database
            var userDetails=userRepository.findByEmail(email);

            //Validate the token
            if(jwtService.isTokenValid(jwtToken,userDetails)) {

                //Create the authentication with user roles
                List<SimpleGrantedAuthority>authorities=
                        userDetails.getRoles().stream()
                                .map(SimpleGrantedAuthority:: new)
                                .collect(Collectors.toList());


                UsernamePasswordAuthenticationToken  authToken =new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        authorities);


                //Set the authentication details
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //Update the security context
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }

        filterChain.doFilter(request, response);
    }



}