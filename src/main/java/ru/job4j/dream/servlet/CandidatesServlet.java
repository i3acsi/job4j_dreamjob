package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CandidatesServlet extends HttpServlet {
    private Store store = PsqlStore.instOf();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            store.delete( new Candidate(Integer.parseInt(id), "remove", 0));
        }
        req.setAttribute("candidates", store.findAllCandidateDto());
        req.getRequestDispatcher("/candidate/candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String cityName = req.getParameter("cityName");
        City city = store.findCityByName(cityName);
        if (city == null) {
            store.save(new City(0, cityName));
            city = store.findCityByName(cityName);
        }
        int cityId = city.getId();
        store.save(
                new Candidate(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name"),
                        cityId
                )
        );
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
