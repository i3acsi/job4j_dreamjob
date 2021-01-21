package ru.job4j.dream.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CORSFilter implements Filter {
    private final AtomicInteger counter = new AtomicInteger(0);
    private final List<String> allowedOrigins = Arrays.asList("http://localhost:8030");
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        System.out.println("cors filter " + counter.incrementAndGet());
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String origin = request.getHeader("Origin");
        System.out.println("origin" + origin);
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "http://localhost:8031");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Credentials", "true");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers","origin, content-type, accept, authorization");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");

        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("Set-Cookie"));
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        System.out.println("!!!!!");
        resp.getHeaderNames().forEach(System.out::println);
        System.out.println("!!!!!");
        request.getParameterMap().forEach((k,v)->{
            System.out.println(k);
            for (String value: v){
                System.out.print(value+ " ");
            }
        });
        for (Cookie cookie:request.getCookies()){
            System.out.println(cookie.getName());
            System.out.println(cookie.getValue());
        }
        if (request.getMethod().equals("OPTIONS")) {
            System.out.println("1234");
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }

        chain.doFilter(request, servletResponse);
    }

}
