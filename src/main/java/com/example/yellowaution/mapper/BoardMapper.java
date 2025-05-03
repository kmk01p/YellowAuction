package com.example.yellowaution.mapper;

import com.example.yellowaution.domain.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {

    /**
     * 제목 또는 기술 키워드로 검색
     * SQL: mapper/BoardMapper.xml 의 <select id="searchByKeyword"> 매핑
     */
    List<Board> searchByKeyword(@Param("keyword") String keyword);

    /**
     * 정렬 기준에 따라 정렬
     * SQL: mapper/BoardMapper.xml 의 <select id="sortByCriteria"> 매핑
     */
    List<Board> sortByCriteria(@Param("sort") String sort);
}
