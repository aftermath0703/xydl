package com.flrjcx.xypt.common.aop;

import com.flrjcx.xypt.common.enums.PageParam;
import com.github.pagehelper.PageHelper;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author malaka
 * @Description 分页aop
 * @date 2022/10/21 14:41
 */
@Aspect
@Component
public class PageAspect {

    @Pointcut("@annotation(com.flrjcx.xypt.common.annotation.OpenPage)")
    private void pt(){
    }

    @Before("pt()")
    public void myBefore() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Integer pageSize = null;
        Integer pageNum = null;
        try {
            pageNum = Integer.parseInt(request.getParameter(PageParam.PAGE_NUM_TAG)) ;
            pageSize = Integer.parseInt(request.getParameter(PageParam.PAGE_SIZE_TAG));
        } catch (NumberFormatException e) {
            pageNum = pageNum == null ? 1 : pageNum;
            pageSize = pageSize == null ? 10 : pageSize;
        }

        PageHelper.startPage(pageNum <= 0 ? 1 : pageNum, pageSize <=0 ? 10 : pageSize);
    }

}
