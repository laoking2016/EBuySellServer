package com.greyu.ysj.mapper;

import com.greyu.ysj.entity.Favorite;
import org.apache.ibatis.annotations.Param;

public interface FavoriteMapper {
    
    
    /******************************************************************/
    
    Favorite findFavorite(@Param("goodId") Integer goodId, @Param("userId") Integer userId);
    void insertFavorite(Favorite favorite);
    void updateFavorite(Favorite favorite);
}