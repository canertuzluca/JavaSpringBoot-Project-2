package com.example.demo.service;


import com.example.demo.exception.InvalidDateFormatException;
import com.example.demo.exception.NewsBodyNotGood;
import com.example.demo.exception.NewsNotFoundException;
import com.example.demo.model.News;
import com.example.demo.model.NewsResponse;
import com.example.demo.repository.NewsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class NewsService {


    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }


    public NewsResponse getAllNews() {
        log.info("Fetching all news");
        List<News> newsList = newsRepository.findAll();
        long totalCount = newsRepository.count();
        log.info("Total news count: {}", totalCount);
        return new NewsResponse(totalCount, newsList);
    }

    public News getById(long id) {
        log.info("Fetching news by id: {}", id);
        News news = newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException("News not found"));
        log.info("News found: {}", news);
        if (news == null){
            log.warn("News not found {}", id);
        }
        return news;
    }

    public News saveNews(News incomingNews) {
        log.info("Saving new news entry with title:  {}", incomingNews.getTitle());
        if(incomingNews.getAuthor() == null || incomingNews.getTitle() == null || incomingNews.getContent() == null) {
            throw new NewsBodyNotGood("News should be contains Author and Title and Content");
        }
        else{
            incomingNews.setDate(LocalDateTime.now());
            return newsRepository.save(incomingNews);
        }
    }

    public void deleteNewsById(long id) {
        log.info("Deleting news by id: {}", id);
        if(newsRepository.findById(id).isEmpty()) {
            throw new NewsNotFoundException("News not found");
        }
        else {
            newsRepository.deleteById(id);
        }
    }

    public News updateNews(long id, News updatedNews){
        log.info("Updating news entry with id: {}", id);

        News databaseNews = newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException("News not found"));

        databaseNews.setDate(LocalDateTime.now());

        if(updatedNews.getContent() == null) {
            log.info("News content is empty");
        }
        else {
            databaseNews.setContent(updatedNews.getContent());
        }
        if(updatedNews.getAuthor() != null) {
            databaseNews.setAuthor(updatedNews.getAuthor());
        }
        if(updatedNews.getTitle() != null) {
            databaseNews.setTitle(updatedNews.getTitle());
        }

        return newsRepository.save(databaseNews);
    }

    public NewsResponse getBetweenDates(LocalDate start, LocalDate end) {
        log.info("Fetching news between{} and {}", start, end);

        if(start == null || end == null) {
            throw new InvalidDateFormatException("Invalid date format or missing date. Format: dd-MM-yyyy");
        }
        if(start.isAfter(end)) {
            throw new InvalidDateFormatException("Start date must be before end date. Format: dd-MM-yyyy");
        }

        List<News> newsList = newsRepository.findAllByDateBetween(start.atStartOfDay(), end.atTime(23,59,59));
        long totalCount = newsList.size();
        return new NewsResponse(totalCount, newsList);
    }

}
