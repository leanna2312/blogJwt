package com.example.blogjwt.articles.repository;

import com.example.blogjwt.articles.entity.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;



@Repository
public interface ArticlesRepository extends JpaRepository<Articles, Long> {

    List<Articles> findAllByOrderByCreateAtDesc();

    Articles findByTitle(String title);
}
