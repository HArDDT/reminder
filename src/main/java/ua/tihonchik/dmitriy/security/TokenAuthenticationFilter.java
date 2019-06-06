package ua.tihonchik.dmitriy.security;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends GenericFilterBean {

    private TokenAuthenticationService service;

    public TokenAuthenticationFilter(@Lazy @NotNull TokenAuthenticationService service) {
        this.service = service;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(
                    service.getAuthentication((HttpServletRequest) request).orElse(null));
        chain.doFilter(request, response);
    }

}
