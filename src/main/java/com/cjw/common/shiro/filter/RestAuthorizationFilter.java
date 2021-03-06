package com.cjw.common.shiro.filter;

import com.cjw.common.util.ResultCode;
import com.cjw.common.util.ResultVO;
import com.cjw.common.util.WebUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RestAuthorizationFilter extends PermissionsAuthorizationFilter {
    private static final Logger log = LoggerFactory
            .getLogger(RestAuthorizationFilter.class);
    @Override
    protected boolean pathsMatch(String path, ServletRequest request) {
        String requestURI = this.getPathWithinApplication(request);
        String[] strings = path.split("==");
        if (strings.length <= 1) {
            // 普通的 URL, 正常处理
            return this.pathsMatch(strings[0], requestURI);
        } else {
            // 获取当前请求的 http method.
            String httpMethod = WebUtils.toHttp(request).getMethod().toUpperCase();
            // 匹配当前请求的 http method 与 过滤器链中的的是否一致
            return httpMethod.equals(strings[1].toUpperCase()) && this.pathsMatch(strings[0], requestURI);
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);
        // 如果未登录
        if (subject.getPrincipal() == null) {
            // AJAX 请求返回 JSON
            if (WebUtil.isAjaxRequest(WebUtils.toHttp(request))) {
                if (log.isDebugEnabled()) {
                    log.debug("用户: [{}] 请求 restful url : {}, 未登录被拦截.", subject.getPrincipal(), this.getPathWithinApplication(request));                }
                WebUtil.writeJson(ResultVO.fail(ResultCode.USER_AUTHENTICATION_ERROR),response);
            } else {
                // 其他请求跳转到登陆页面
                saveRequestAndRedirectToLogin(request, response);
            }
        } else {
            // 如果已登陆, 但没有权限
            // 对于 AJAX 请求返回 JSON
            if (WebUtil.isAjaxRequest(WebUtils.toHttp(request))) {
                if (log.isDebugEnabled()) {
                    log.debug("用户: [{}] 请求 restful url : {}, 无权限被拦截.", subject.getPrincipal(), this.getPathWithinApplication(request));
                }

                WebUtil.writeJson(ResultVO.fail(ResultCode.USER_AUTHORIZATION_ERROR),response);
            } else {
                // 对于普通请求, 跳转到配置的 UnauthorizedUrl 页面.
                // 如果未设置 UnauthorizedUrl, 则返回 401 状态码
                String unauthorizedUrl = getUnauthorizedUrl();
                if (StringUtils.hasText(unauthorizedUrl)) {
                    WebUtils.issueRedirect(request, response, unauthorizedUrl);
                } else {
                    WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }

        }
        return false;
    }
}
