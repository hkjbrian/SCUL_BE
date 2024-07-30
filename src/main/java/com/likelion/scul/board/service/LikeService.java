package com.likelion.scul.board.service;

import com.likelion.scul.board.domain.Like;
import com.likelion.scul.board.domain.Post;
import com.likelion.scul.board.repository.LikeRepository;
import com.likelion.scul.board.repository.PostRepository;
import com.likelion.scul.common.domain.User;
import com.likelion.scul.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Transactional
    public void addLike(String email, Long postId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (likeRepository.findByPostAndUser(post, user).isPresent()) {
            throw new RuntimeException("Like already exists");
        }

        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        likeRepository.save(like);
    }

    @Transactional
    public void removeLike(String email, Long postId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Like like = likeRepository.findByPostAndUser(post, user)
                .orElseThrow(() -> new RuntimeException("Like not found"));

        likeRepository.delete(like);
    }
}
