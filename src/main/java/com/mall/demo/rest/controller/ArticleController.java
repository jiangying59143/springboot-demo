package com.mall.demo.rest.controller;

import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.mall.demo.common.annotation.LogAnnotation;
import com.mall.demo.common.constants.Base;
import com.mall.demo.common.constants.ResultCode;
import com.mall.demo.common.result.Result;
import com.mall.demo.model.blog.Article;
import com.mall.demo.model.blog.ArticleBody;
import com.mall.demo.model.blog.Tag;
import com.mall.demo.model.privilege.User;
import com.mall.demo.service.ArticleService;
import com.mall.demo.service.TagService;
import com.mall.demo.vo.ArticleVo;
import com.mall.demo.vo.PageVo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping(value = "/articles")
public class ArticleController {


    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @ApiIgnore
    @GetMapping
    @FastJsonView(
            exclude = {
                    @FastJsonFilter(clazz = Article.class, props = {"body", "category", "comments"}),
                    @FastJsonFilter(clazz = Tag.class, props = {"id", "avatar"})},
            include = {@FastJsonFilter(clazz = User.class, props = {"name"})})
    @LogAnnotation(module = "文章", operation = "获取所有文章")
    public Result listArticles(ArticleVo article, PageVo page) {
        System.out.println(article);
        System.out.println(page);
        List<Article> articles = articleService.listArticles(article, page);
        return Result.success(articles);
    }

    @ApiIgnore
    @GetMapping("/hot")
    @FastJsonView(include = {@FastJsonFilter(clazz = Article.class, props = {"id", "title"})})
    @LogAnnotation(module = "文章", operation = "获取最热文章")
    public Result listHotArticles() {
        int limit = 6;
        List<Article> articles = articleService.listHotArticles(limit);

        return Result.success(articles);
    }

    @ApiIgnore
    @GetMapping("/new")
    @FastJsonView(include = {@FastJsonFilter(clazz = Article.class, props = {"id", "title"})})
    @LogAnnotation(module = "文章", operation = "获取最新文章")
    public Result listNewArticles() {
        int limit = 6;
        List<Article> articles = articleService.listNewArticles(limit);

        return Result.success(articles);
    }


    @ApiIgnore
    @GetMapping("/{id}")
    @FastJsonView(
            exclude = {
                    @FastJsonFilter(clazz = Article.class, props = {"comments"}),
                    @FastJsonFilter(clazz = ArticleBody.class, props = {"contentHtml"})})
    @LogAnnotation(module = "文章", operation = "根据id获取文章")
    public Result getArticleById(@PathVariable("id") Long id) {

        Result r = new Result();

        if (null == id) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }

        Article article = articleService.getArticleById(id);

        r.setResultCode(ResultCode.SUCCESS);
        r.setData(article);
        return r;
    }

    @ApiIgnore
    @GetMapping("/view/{id}")
    @FastJsonView(
            exclude = {
                    @FastJsonFilter(clazz = Article.class, props = {"comments"}),
                    @FastJsonFilter(clazz = ArticleBody.class, props = {"contentHtml"}),
                    @FastJsonFilter(clazz = Tag.class, props = {"avatar"})},
            include = {@FastJsonFilter(clazz = User.class, props = {"id", "name", "avatar"})})
    @LogAnnotation(module = "文章", operation = "根据id获取文章，添加阅读数")
    public Result getArticleAndAddViews(@PathVariable("id") Long id) {

        Result r = new Result();

        if (null == id) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }

        Article article = articleService.getArticleAndAddViews(id);

        r.setResultCode(ResultCode.SUCCESS);
        r.setData(article);
        return r;
    }

    @ApiIgnore
    @GetMapping("/tag/{id}")
    @FastJsonView(
            exclude = {
                    @FastJsonFilter(clazz = Article.class, props = {"body", "category", "comments"}),
                    @FastJsonFilter(clazz = Tag.class, props = {"id", "avatar"})},
            include = {@FastJsonFilter(clazz = User.class, props = {"nickname"})})
    @LogAnnotation(module = "文章", operation = "根据标签获取文章")
    public Result listArticlesByTag(@PathVariable Integer id) {
        List<Article> articles = articleService.listArticlesByTag(id);

        return Result.success(articles);
    }


    @ApiIgnore
    @GetMapping("/category/{id}")
    @FastJsonView(
            exclude = {
                    @FastJsonFilter(clazz = Article.class, props = {"body", "category", "comments"}),
                    @FastJsonFilter(clazz = Tag.class, props = {"id", "avatar"})},
            include = {@FastJsonFilter(clazz = User.class, props = {"nickname"})})
    @LogAnnotation(module = "文章", operation = "根据分类获取文章")
    public Result listArticlesByCategory(@PathVariable Long id) {
        List<Article> articles = articleService.listArticlesByCategory(id);

        return Result.success(articles);
    }

    @PostMapping("/publish")
    @RequiresAuthentication
    @LogAnnotation(module = "文章", operation = "发布文章")
    public Result saveArticle(@Validated @RequestBody Article article) {

        Long articleId = articleService.publishArticle(article);

        Result r = Result.success();
        r.simple().put("articleId", articleId);
        return r;
    }

    @PostMapping("/update")
    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "文章", operation = "修改文章")
    public Result updateArticle(@RequestBody Article article) {
        Result r = new Result();

        if (null == article.getId()) {
            r.setResultCode(ResultCode.USER_NOT_EXIST);
            return r;
        }

        Long articleId = articleService.updateArticle(article);

        r.setResultCode(ResultCode.SUCCESS);
        r.simple().put("articleId", articleId);
        return r;
    }

    @GetMapping("/delete/{id}")
    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "文章", operation = "删除文章")
    public Result deleteArticleById(@PathVariable("id") Long id) {
        Result r = new Result();

        if (null == id) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }

        articleService.deleteArticleById(id);

        r.setResultCode(ResultCode.SUCCESS);
        return r;
    }

    @GetMapping("/listArchives")
    @LogAnnotation(module = "文章", operation = "获取文章归档日期")
    public Result listArchives() {
        return Result.success(articleService.listArchives());
    }


}
