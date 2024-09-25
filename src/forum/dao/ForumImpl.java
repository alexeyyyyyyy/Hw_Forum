package forum.dao;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.function.Predicate;

import forum.model.Post;

public class ForumImpl implements Forum {
	private	Post[] posts;
	private int size;
	
	public ForumImpl(int capacity) {
		posts = new Post[capacity];
	}
	
	@Override
	public boolean addPost(Post post) {
		if(post == null || size == posts.length || getPostById(post.getPostId()) != null ) {
			return false;
		}
		posts[size++] = post; 
		
		return true;
	}

	@Override
	public boolean removePost(int postId) {
		for (int i = 0; i < size; i++) {
			if(posts[i].getPostId() == postId) {
				posts[i] = posts[size -1];
				size = size -1;
				posts[size] = null;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean updatePost(int postId, String content) {
		Post post = getPostById(postId);
			if(post == null) {
				return false;
			}
			post.setContent(content);
			return true;
		
	}
	

	@Override
	public Post getPostById(int postId) {
		for (int i = 0; i < size; i++) {
			if(posts[i].getPostId() == postId) {
				return posts[i];
			}
		}
		return null;
	}

	@Override
	public Post[] getPostsByAuthor(String author) {
		
		return findPostByPredicate(p -> p.getAuthor() == author);
	}

	@Override
	public Post[] getPostsByAuthor(String author, LocalDate dateFrom, LocalDate dateTo) {
	return	findPostByPredicate(p -> dateFrom.isBefore(p.getDate().toLocalDate())
				&& dateTo.isAfter(p.getDate().toLocalDate()));
		
	}


	@Override
	public int size() {
		return size;
	}
	
	private Post[] findPostByPredicate(Predicate<Post> predicate) {
		Post[] res =  new Post[size];
		int count = 0;
		for (int i = 0; i < size; i++) {
			if(posts[i] == null) {
				return null;
			}
			if(predicate.test(posts[i])) {
				res[count++] = posts[i];
			}
		}
		return Arrays.copyOf(res, count);
	}
	

}
