package com.login.api.security.config;

import com.login.api.entity.Usuario;
import com.login.api.repository.UsuarioRepository;
import com.login.api.security.userDetails.UserDetailsImplements;
import com.login.api.service.JWTTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Component
public class UserSecurityFilter extends OncePerRequestFilter {

    private JWTTokenService jwtTokenService;
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        System.out.println("URI acessada: " + uri);

        if(!isEndPointLivre(request)){
            System.out.println("Acesso liberado para: " + uri);
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("Acesso restrito para: " + uri);
        String token = recoverToken(request);
        if(Objects.isNull(token)){
                filterChain.doFilter(request, response);
                return;
        }
        String userName = jwtTokenService.recuperarUsuario(token);
        Usuario user = usuarioRepository.findByuserName(userName);

        if(Objects.isNull(user) && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetailsImplements userDetails = new UserDetailsImplements(user);
                if(IstokenValid(token, userDetails)){
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
        }

        filterChain.doFilter(request,response);
    }

    private boolean IstokenValid(String token, UserDetailsImplements userDetails) {
        return jwtTokenService.isTokenValid(token, userDetails);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

    private Boolean isEndPointLivre(HttpServletRequest request){
        var endpoint = request.getRequestURI();
        Boolean liberado = !Arrays.stream(SecurityConfig.ENDPOINTS_NO_REQUIRED_AUTH).toList().contains(endpoint);
        return liberado;
    }

}
