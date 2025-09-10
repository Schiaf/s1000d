package fr.schiaf.s1000d.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import fr.schiaf.s1000d.model.User;
import fr.schiaf.s1000d.repository.UserRepository;
import io.jsonwebtoken.Jwts;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    public JwtAuthenticationFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws java.io.IOException, jakarta.servlet.ServletException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
            // Validate and parse the token
            String username = Jwts.parser()
                    .setSigningKey("EMfun9r2pwVV9VrfcTv5hBwbyyvvTbE6EMfun9r2pwV") // Replace with your secret key
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            if (username != null) {
                // Load user details from database or service
                UserDetails userDetails = loadUserByUsername(username); // Replace with your user service
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

    private UserDetails loadUserByUsername(String username) {
       User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRoles().name());

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(), List.of(authority));
        return userDetails; 
    }
}