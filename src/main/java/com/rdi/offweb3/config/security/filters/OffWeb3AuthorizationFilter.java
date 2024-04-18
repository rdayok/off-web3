package com.rdi.offweb3.config.security.filters;

import com.rdi.offweb3.enums.Role;
import com.rdi.offweb3.config.security.services.JwtService;
import com.rdi.offweb3.data.models.User;
import com.rdi.offweb3.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static com.rdi.offweb3.config.security.utils.SecurityUtils.getPublicGetEndPoints;
import static com.rdi.offweb3.config.security.utils.SecurityUtils.getPublicPostEndPoints;

@Component
@RequiredArgsConstructor
public class OffWeb3AuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean isRequestToPublicEndPoint =
                request.getMethod().equals("POST") && getPublicPostEndPoints().contains(request.getRequestURI()) ||
                request.getMethod().equals("GET") && getPublicGetEndPoints().contains(request.getRequestURI());
        if (isRequestToPublicEndPoint) filterChain.doFilter(request, response);
        else {
            String authorizationHeader = request.getHeader("Authorization");
            String token = authorizationHeader.substring("Bearer ".length());
            String username = jwtService.extractUsernameFrom(token);
            User user = userService.getUserBy(username);
            Role role = user.getRole();
            SimpleGrantedAuthority userRole = new SimpleGrantedAuthority(role.name());
            Collection<SimpleGrantedAuthority> authority = new ArrayList<>();
            authority.add(userRole);
            var authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null, authority);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }
    }
}

