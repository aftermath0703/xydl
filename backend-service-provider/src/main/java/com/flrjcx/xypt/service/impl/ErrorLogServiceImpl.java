package com.flrjcx.xypt.service.impl;

import com.flrjcx.xypt.common.model.domain.log.ErrorLog;
import com.flrjcx.xypt.common.model.param.log.ErrorLogParam;
import com.flrjcx.xypt.mapper.ErrorLogMapper;
import com.flrjcx.xypt.service.ErrorLogService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author malaka
 * @Description TODO
 * @date 2022/11/5 20:59
 */
@Service
public class ErrorLogServiceImpl implements ErrorLogService {

    @Resource
    private ErrorLogMapper errorLogMapper;


    /**
     * 查询异常日志列表
     *
     * @param errorLogParam
     * @return
     */
    @Override
    public List<ErrorLog> errorList(ErrorLogParam errorLogParam) {
        System.out.println("MALAKA"+errorLogParam);
        if (Objects.isNull(errorLogParam)) {
            errorLogParam = new ErrorLogParam();
            errorLogParam.setPageNum(1);
            errorLogParam.setPageSize(10);
        }
        Integer pageNum = errorLogParam.getPageNum();
        Integer pageSize = errorLogParam.getPageSize();
        System.out.println("MALAKA" + pageNum);
        System.out.println("MALAKA" + pageSize);
        if (Objects.isNull(pageNum) || pageNum <= 0) {
            errorLogParam.setPageNum(1);
        }
        if (Objects.isNull(pageSize) || pageSize <= 0) {
            errorLogParam.setPageSize(10);
        }
        errorLogParam.setPageNum((errorLogParam.getPageNum() - 1) * errorLogParam.getPageSize());
        return errorLogMapper.selectErrorList(errorLogParam);
    }

    /**
     * 查询日志列表总数
     *
     * @param errorLogParam
     * @return
     */
    @Override
    public Long errorListTotal(ErrorLogParam errorLogParam) {
        return errorLogMapper.selectErrorListTotal(errorLogParam);
    }
}
