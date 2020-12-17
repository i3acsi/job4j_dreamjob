package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.logger.MyLogger;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {

    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            MyLogger.logException(e.getMessage());
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            MyLogger.logException(e.getMessage());
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    int id = it.getInt("id");
                    String name = it.getString("name");
                    if (id >= 0 && name != null && !name.isEmpty())
                        posts.add(new Post(id, name));
                }
            }
        } catch (Exception e) {
            MyLogger.logException(e.getMessage());
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    int id = it.getInt("id");
                    String name = it.getString("name");
                    if (id >= 0 && name != null && !name.isEmpty())
                        candidates.add(new Candidate(id, name));
                }
            }
        } catch (Exception e) {
            MyLogger.logException(e.getMessage());
        }
        return candidates;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO post(name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            String name = post.getName();
            if (name == null || name.isEmpty())
                throw new RuntimeException("empty name");
            ps.setString(1, name);
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            MyLogger.logException(e.getMessage());
        }
        return post;
    }

    private void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE post SET name = ? WHERE id = ?")
        ) {
            String name = post.getName();
            if (name == null || name.isEmpty())
                throw new RuntimeException("empty name");
            ps.setString(1, name);
            ps.setInt(2, post.getId());
            ps.execute();
        } catch (Exception e) {
            MyLogger.logException(e.getMessage());
        }
    }

    @Override
    public Post findPostById(int id) {
        Post result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT name FROM post WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next())
                    if (it.next()) {
                        String name = it.getString("name");
                        if (name != null && !name.isEmpty())
                            result = new Post(id, name);
                    }
            }
        } catch (Exception e) {
            MyLogger.logException(e.getMessage());
        }
        return result;
    }

    @Override
    public void delete(Post post) {
        int id = post.getId();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM post WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            MyLogger.logException(e.getMessage());
        }
    }


    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    private Candidate create(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO candidate (name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            String name = candidate.getName();
            if (name == null || name.isEmpty())
                throw new RuntimeException("empty name");
            ps.setString(1, name);
            ps.executeUpdate();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            MyLogger.logException(e.getMessage());
        }
        return candidate;
    }

    private void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE candidate SET name=? WHERE id=?")) {
            String name = candidate.getName();
            if (name == null || name.isEmpty())
                throw new RuntimeException("empty name");
            ps.setString(1, name);
            ps.setInt(2, candidate.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            MyLogger.logException(e.getMessage());
        }
    }

    @Override
    public Candidate findCandidateById(int id) {
        Candidate result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT name FROM candidate WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    String name = it.getString("name");
                    if (name != null && !name.isEmpty())
                        result = new Candidate(id, name);
                }
            }
        } catch (Exception e) {
            MyLogger.logException(e.getMessage());
        }
        return result;
    }

    @Override
    public void delete(Candidate candidate) {
        int id = candidate.getId();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM candidate WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            File file = new File("images" + File.separator + id);
            Files.deleteIfExists(file.toPath());
        } catch (Exception e) {
            MyLogger.logException(e.getMessage());
        }
    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            create(user);
        } else {
            update(user);
        }
    }

    private User create(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO user_account(name, email, password) VALUES (?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            String name = user.getName();
            if (name == null || name.isEmpty())
                throw new RuntimeException("empty name");
            String email = user.getEmail();
            if (email == null || email.isEmpty())
                throw new RuntimeException("empty email");
            User u = PsqlStore.instOf().findUserByEmail(email);
            if (u!=null)
                throw new RuntimeException("user with this email already exists");
            String password = user.getPassword();
            if (password == null || password.isEmpty())
                throw new RuntimeException("empty password");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            MyLogger.logException(e.getMessage());
        }
        return user;
    }

    private void update(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE user_account SET name = ?, email = ?, password = ? WHERE id = ?")
        ) {
            String name = user.getName();
            if (name == null || name.isEmpty())
                throw new RuntimeException("empty name");
            String email = user.getEmail();
            if (email == null || email.isEmpty())
                throw new RuntimeException("empty email");
            String password = user.getPassword();
            if (password == null || password.isEmpty())
                throw new RuntimeException("empty password");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setInt(4, user.getId());
            ps.execute();
        } catch (Exception e) {
            MyLogger.logException(e.getMessage());
        }
    }

    @Override
    public Collection<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM user_account")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    int id = it.getInt("id");
                    String name = it.getString("name");
                    ;
                    if (name == null || name.isEmpty())
                        throw new RuntimeException("empty name");
                    String email = it.getString("email");
                    if (email == null || email.isEmpty())
                        throw new RuntimeException("empty email");
                    String password = it.getString("password");
                    if (password == null || password.isEmpty())
                        throw new RuntimeException("empty password");
                    if (id >= 0)
                        users.add(new User(id, name, email, password));
                }
            }
        } catch (Exception e) {
            MyLogger.logException(e.getMessage());
        }
        return users;
    }

    @Override
    public User findUserById(int id) {
        User result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT name, email, password FROM user_account WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    String name = it.getString("name");
                    if (name == null || name.isEmpty())
                        throw new RuntimeException("empty name");
                    String email = it.getString("email");
                    if (email == null || email.isEmpty())
                        throw new RuntimeException("empty email");
                    String password = it.getString("password");
                    if (password == null || password.isEmpty())
                        throw new RuntimeException("empty password");

                    result = new User(id, name, email, password);
                }
            }
        } catch (Exception e) {
            MyLogger.logException(e.getMessage());
        }
        return result;
    }

    @Override
    public User findUserByEmail(String email) {
        User result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT id, name, password FROM user_account WHERE email = ?")
        ) {
            ps.setString(1, email);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    String name = it.getString("name");
                    if (name == null || name.isEmpty())
                        throw new RuntimeException("empty name");
                    int id = it.getInt("id");
                    String password = it.getString("password");
                    if (password == null || password.isEmpty())
                        throw new RuntimeException("empty password");
                    result = new User(id, name, email, password);
                }
            }
        } catch (Exception e) {
            MyLogger.logException(e.getMessage());
        }
        return result;
    }

    @Override
    public void delete(User user) {
        int id = user.getId();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM user_account WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            MyLogger.logException(e.getMessage());
        }
    }
}
