package ru.job4j.dream.servlet;

import ru.job4j.dream.model.City;
import ru.job4j.dream.service.Mapper;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class CityServlet extends HttpServlet {
    private final Store store = PsqlStore.instOf();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("cities", store.findAllCities().stream().map(Mapper::CityToJson).collect(Collectors.toList()));
        req.getRequestDispatcher("/city.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), StandardCharsets.UTF_8));
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String remove = req.getParameter("remove");
        City city = new City(Integer.parseInt(id), name);
        if ( remove!= null) {
            store.delete(city);
        } else {
            city = store.save(city);
            if (city.getId()==-1){
                resp.sendError(400, "Город с таким названием уже существует");
            }
        }
        String json = Mapper.CityToJson(city);
        writer.println(json);
        writer.flush();
    }
}
