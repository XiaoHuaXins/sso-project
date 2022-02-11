package com.smart.sso.demo.dao.permit;

import com.smart.sso.demo.entity.dto.PermitDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author xhx
 * @Date 2022/2/11 10:23
 */
@Mapper
public interface PermitDao {
    /**
     * 返回所有权限信息
     * @return
     */
    List<PermitDTO> findAllPermission();
}
