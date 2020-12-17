package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sc;
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if ("root@local".equals(email) && "root".equals(password)) {
            sc = req.getSession();
            User admin = new User(0, "Admin", "root@local", "root");
            sc.setAttribute("user", admin);
            resp.sendRedirect(req.getContextPath() + "/post/posts.do");
        } else {
            User user = PsqlStore.instOf().findUserByEmail(email);
            if (user != null && user.getPassword().equals(password)) {
                sc = req.getSession();
                sc.setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + "/index.do");
            } else {
                req.setAttribute("error", "Не верный email или пароль");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        }
    }
}