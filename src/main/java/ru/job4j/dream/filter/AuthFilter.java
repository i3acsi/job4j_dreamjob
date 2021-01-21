package ru.job4j.dream.filter;

import ru.job4j.dream.model.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class AuthFilter implements Filter {
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest sreq, ServletResponse sresp, FilterChain chain) throws IOException, ServletException {
        System.out.println("auth filter " + counter.incrementAndGet());
        HttpServletRequest req = (HttpServletRequest) sreq;
        HttpServletResponse resp = (HttpServletResponse) sresp;
        String uri = req.getRequestURI();
        HttpSession hs = req.getSession();
        if (uri.endsWith("auth.do") || uri.endsWith("reg.do")) {
            chain.doFilter(sreq, sresp);
            return;
        }
        if (uri.contains("cities.do")) {
            String token = req.getParameter("token");
            if (req.getMethod().toString().equals("POST") && !(user.getEmail().equals("root@local"))) {
                System.out.println("redirect");
                resp.sendRedirect(req.getContextPath());
                return;
            }
        }
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        chain.doFilter(sreq, sresp);
    }

    @Override
    public void destroy() {

    }
}
