package com.example.demo.controller;


import com.example.demo.model.News;
import com.example.demo.model.NewsResponse;
import com.example.demo.service.NewsService;
import jakarta.persistence.GeneratedValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/news")
public class NewsController {


    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }


    @GetMapping
    public ResponseEntity<NewsResponse> getAllNews() {
        log.debug("Request to get all News");
        NewsResponse response = newsService.getAllNews();
        log.info("Returning response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable long id) {

        return ResponseEntity.ok(newsService.getById(id));
    }

    @PostMapping
    public ResponseEntity<News> createNews(@RequestBody News news) {
        return new ResponseEntity<>(newsService.saveNews(news), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNewsById(@PathVariable long id) {
        newsService.deleteNewsById(id);
        return new ResponseEntity<>("News Deleted",HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<News> updateNewsById(@PathVariable long id, @RequestBody News news) {

        News response = newsService.updateNews(id, news);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/dates")
    public ResponseEntity<NewsResponse> getNewsBetweenDates(
            @RequestParam(value = "startDate" , required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {
        NewsResponse response = newsService.getBetweenDates(startDate, endDate);

        return ResponseEntity.ok(response);

    }
}