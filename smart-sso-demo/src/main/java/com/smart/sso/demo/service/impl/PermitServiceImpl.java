package com.smart.sso.demo.service.impl;

import com.smart.sso.demo.dao.permit.PermitDao;
import com.smart.sso.demo.service.PermitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author xhx
 * @Date 2022/2/11 10:19
 */
@Service
public class PermitServiceImpl implements PermitService {
    @Autowired
    PermitDao permitDao;

}
