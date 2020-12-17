package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {
    private static final AtomicInteger POST_ID = new AtomicInteger(4);
    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(4);
    private static final AtomicInteger USER_ID = new AtomicInteger(2);

    private static final MemStore INST = new MemStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job"));
        posts.put(2, new Post(2, "Middle Java Job"));
        posts.put(3, new Post(3, "Senior Java Job"));
        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle Java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
        users.put(1, new User(1, "admin", "admin@admin.com", "password"));
    }

    public static MemStore instOf() {
        return INST;
    }

    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    public Post findPostById(int id) {
        return posts.get(id);
    }

    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
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
    public void save(User user) {
        if (user.getId() == 0) {
            user.setId(USER_ID.incrementAndGet());
        }
        users.put(user.getId(), user);
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
        return users.values().stream().filter(u->u.getEmail().equals(email)).findFirst().get();
    }

    @Override
    public void delete(User user) {
        users.remove(user.getId());
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }
}
