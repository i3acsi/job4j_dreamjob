package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.service.AuthenticationService;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/reg.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = AuthenticationService.encode(email, req.getParameter("password"));
        User newUser = new User(0, name, email, password);
        PsqlStore.instOf().save(newUser);
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
