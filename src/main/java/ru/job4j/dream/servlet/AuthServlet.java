package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.service.AuthenticationService;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.*;
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
        User user = PsqlStore.instOf().findUserByEmail(email);
        if (user != null && AuthenticationService.checkCredentials(user.getName(), password, user.getPassword())) {
            HttpSession hs = req.getSession();
            String token = AuthenticationService.getToken(user.getName(), user.getPassword());
            hs.setAttribute("token", token);
            resp.sendRedirect(req.getContextPath() + "/posts.do");
        } else {
            req.setAttribute("error", "Не верный email или пароль");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
