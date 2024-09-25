package forum.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import forum.dao.Forum;
import forum.dao.ForumImpl;
import forum.model.Post;

class ForumImplTest {
	Forum forum;
	Post[] posts;
	LocalDateTime dateTime = LocalDateTime.now();

	@BeforeEach
	void setUp() throws Exception {
		posts = new Post[7];
		posts[0] = new Post(1, "One1", "Luter", "One1");
		posts[1] = new Post(2, "Two2", "Two", "Two2");
		posts[2] = new Post(3, "Three3", "Three", "Three3");
		posts[3] = new Post(4, "Four4", "Four", "Four4");
		posts[4] = new Post(5, "Five5", "Five", "Five5");
		posts[5] = new Post(6, "Six6", "Luter", "Six6");
		posts[6] = new Post(7, "Seven7", "Seven", "Seven7");
		forum = new ForumImpl(7);
		for (int i = 0; i < posts.length - 1; i++) {
			forum.addPost(posts[i]);
		}
	}

	@Test
	void testAddPost() {
		assertFalse(forum.addPost(null));
		assertFalse(forum.addPost(posts[1]));
		assertEquals(6, forum.size());
		assertTrue(forum.addPost(posts[6]));
		assertEquals(7, forum.size());
		assertFalse(forum.addPost(new Post(0, null, null, null)));

	}

	@Test
	void testRemovePost() {
		assertFalse(forum.removePost(10));
		assertTrue(forum.removePost(4));
		assertEquals(5, forum.size());
	}

	@Test
	void testUpdatePost() {
		assertTrue(forum.updatePost(1, "SevenUp"));
		assertEquals("SevenUp", forum.getPostById(1).getContent());
	}

	@Test
	void testGetPostById() {
		assertEquals(posts[0], forum.getPostById(1));
		assertNull(forum.getPostById(15));
	}

	@Test
	void testGetPostsByAuthorString() {
		Post[] actual = forum.getPostsByAuthor("Luter");
		Post[] expected = { posts[0], posts[5] };
		assertArrayEquals(expected, actual);
	}

	@Test
	void testGetPostsByAuthorStringLocalDateLocalDate() {
	    LocalDate localDate = LocalDate.now();
	    
	    posts[0].setDate(LocalDateTime.now()); 
	    
	    posts[5].setDate(LocalDateTime.now()); 
	    
	    for(Post post : posts) {
	    	forum.addPost(post);
	    }
	    Post[] actual = forum.getPostsByAuthor("Luter",localDate.minusDays(10),localDate.minusDays(0));
	    Post[]expected = {posts[5],posts[0] };
	    
	    assertArrayEquals(expected, actual);
	    
	}

	@Test
	void testSize() {
		assertEquals(6, forum.size());
	}

}
