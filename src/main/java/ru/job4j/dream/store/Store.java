package ru.job4j.dream.store;

import ru.job4j.dream.dto.CandidateDTO;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    Post save(Post post);

    Post findPostById(int id);

    void save(Candidate candidate);

    Candidate findCandidateById(int id);

    void delete(Post post);

    void delete(Candidate candidate);

    User save(User user);

    Collection<User> findAllUsers();

    User findUserById(int id);

    User findUserByEmail(String email);

    void delete(User user);

    Collection<City> findAllCities();

    City findCityById(int id);

    City findCityByName(String name);

    City save(City city);

    void delete(City city);

    Collection<CandidateDTO> findAllCandidateDto();
}
