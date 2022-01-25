package com.smart.sso.demo.service.impl;

import com.smart.sso.demo.dao.catalogue.CatalogueDao;
import com.smart.sso.demo.entity.catalogue.Catalogue;
import com.smart.sso.demo.service.CatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author xhx
 * @Date 2022/1/25 15:51
 */
@Service
public class CatalogueImpl implements CatalogueService {
    @Autowired
    CatalogueDao catalogueDao;


}
