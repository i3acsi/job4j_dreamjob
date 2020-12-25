//package ru.job4j.dream.store;
//
//
//import org.junit.Assert;
//import ru.job4j.dream.model.Candidate;
//import ru.job4j.dream.model.Post;
//
//import java.util.ArrayList;
//import java.util.List;
//
//class PsqlStoreTest {
//    private Store store = PsqlStore.instOf();
//
//    public void saveAndUpdatePostTest() {
//        String name = "Test Java Job";
//        String newName = "new Name";
//        store.save(new Post(0, name));
//        List<Post> posts = new ArrayList<>(store.findAllPosts());
//        System.out.println("All posts contained in the database:");
//        posts.forEach(System.out::println);
//        Post resultPost = posts.get(posts.size() - 1);
//        int id = resultPost.getId();
//        Assert.assertEquals(resultPost.getName(), name);
//        System.out.println("Inserted new Post: ID " + resultPost);
//        resultPost.setName(newName);
//        store.save(resultPost);
//        resultPost = store.findAllPosts().stream().reduce((first, second) -> second)
//                .orElse(null);
//        Assert.assertEquals(resultPost.getName(), newName);
//        Assert.assertEquals(resultPost.getId(), id);
//        Assert.assertEquals(resultPost, store.findPostById(id));
//    }
//
//    public void saveAndUpdateCandidateTest() {
//        String name = "Test Java Candidate";
//        String newName = "new Candidate";
//        store.save(new Candidate(0, name, 0));
//        List<Candidate> candidates = new ArrayList<>(store.findAllCandidates());
//        System.out.println("All candidates contained in the database:");
//        candidates.forEach(System.out::println);
//        Candidate resultCandidate = candidates.get(candidates.size() - 1);
//        int id = resultCandidate.getId();
//        Assert.assertEquals(resultCandidate.getName(), name);
//        System.out.println("Inserted new Candidate: " + resultCandidate);
//        resultCandidate.setName(newName);
//        store.save(resultCandidate);
//        resultCandidate = store.findAllCandidates().stream().reduce((first, second) -> second)
//                .orElse(null);
//        Assert.assertEquals(resultCandidate.getName(), newName);
//        Assert.assertEquals(resultCandidate.getId(), id);
//        Assert.assertEquals(resultCandidate, store.findCandidateById(id));
//    }
//
//    public static void main(String[] args) {
//        PsqlStoreTest test = new PsqlStoreTest();
//        test.saveAndUpdatePostTest();
//        test.saveAndUpdateCandidateTest();
//    }
//}