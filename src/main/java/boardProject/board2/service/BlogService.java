package boardProject.board2.service;

import boardProject.board2.dto.blog.AddArticleRequest;
import boardProject.board2.dto.blog.UpdateArticleRequest;
import boardProject.board2.entity.Article;
import boardProject.board2.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public Article addArticle(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found"));
    }

    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest updateArticleRequest) {

        Article article = blogRepository.findById(id).orElseThrow(()->new IllegalArgumentException("not founded"));
        article.update(updateArticleRequest.getTitle(),updateArticleRequest.getContent());
        return article;
    }
}
