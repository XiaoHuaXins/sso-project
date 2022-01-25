package com.smart.sso.demo.dao.catalogue;

import com.smart.sso.demo.entity.catalogue.Catalogue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author xhx
 * @Date 2022/1/25 15:49
 */
@Mapper
public interface CatalogueDao {
    List<Catalogue> findAllCatalogue();
}
