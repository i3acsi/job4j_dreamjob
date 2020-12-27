package ru.job4j.dream.store;

import ru.job4j.dream.dto.CandidateDTO;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MemStore implements Store {
    private static final AtomicInteger POST_ID = new AtomicInteger(0);
    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(0);
    private static final AtomicInteger USER_ID = new AtomicInteger(0);
    private static final AtomicInteger CITY_ID = new AtomicInteger(0);

    private static final MemStore INST = new MemStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    private final Map<Integer, City> cities = new ConcurrentHashMap<>();

    private MemStore() {
    }

    public static MemStore instOf() {
        return INST;
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        return posts.put(post.getId(), post);
    }

    public Post findPostById(int id) {
        return posts.get(id);
    }

    public Candidate save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        return candidates.put(candidate.getId(), candidate);
    }

    public Candidate findCandidateById(int id) {
        return candidates.get(id);
    }

    @Override
    public void delete(Post post) {
        int id = post.getId();
        posts.remove(id);
    }

    @Override
    public void delete(Candidate candidate) {
        int id = candidate.getId();
        candidates.remove(id);
    }

    @Override
    public User save(User user) {
        if (user.getId() == 0) {
            user.setId(USER_ID.incrementAndGet());
        }
        return users.put(user.getId(), user);
    }

    @Override
    public Collection<User> findAllUsers() {
        return users.values();
    }

    @Override
    public User findUserById(int id) {
        return users.get(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return users.values().stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }

    @Override
    public void delete(User user) {
        users.remove(user.getId());
    }

    @Override
    public Collection<City> findAllCities() {
        return cities.values();
    }

    @Override
    public City findCityById(int id) {
        return cities.get(id);
    }

    @Override
    public City findCityByName(String name) {
        return cities.values().stream().filter(city -> city.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public City save(City city) {
        if (city.getId() == 0) {
            city.setId(CITY_ID.incrementAndGet());
        }
        return cities.put(city.getId(), city);
    }

    @Override
    public void delete(City city) {
        int id = city.getId();
        cities.remove(id);
    }

    @Override
    public Collection<CandidateDTO> findAllCandidateDto() {
        return candidates.values().stream().map(candidate -> new CandidateDTO(candidate, findCityById(candidate.getCityId()))).collect(Collectors.toList());
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }
}
