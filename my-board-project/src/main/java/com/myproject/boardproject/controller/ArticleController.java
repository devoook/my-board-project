package com.myproject.boardproject.controller;

import com.myproject.boardproject.domain.type.SearchType;
import com.myproject.boardproject.dto.response.ArticleResponse;
import com.myproject.boardproject.dto.response.ArticleWithCommentsResponse;
import com.myproject.boardproject.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size=10,sort="createdAt", direction= Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {// response 객체로 변환한다.
        map.addAttribute("articles",articleService.searchArticles(searchType,searchValue,pageable).map(ArticleResponse::from));
        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String articles(@PathVariable Long articleId, ModelMap map) {
        // response 객체로 변환한다.
        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticle(articleId));
        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.articleCommentsResponses());
        return "articles/detail";
    }

}
