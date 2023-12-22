package com.black.common.filter;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.black.common.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtFilter implements HandlerInterceptor {
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws Exception {
        String headToken = request.getHeader("Token");
        if(StringUtils.isEmpty(headToken)) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", 20001);
            map.put("message", "未携带[Token]请求头");
            ErrorResponse(response, map);
            log.error("异常:请求未携带令牌");
            return false;
        }
        try {
            JwtUtil.verifyToken(headToken);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> map = new HashMap<>();
            map.put("code", 20002);
            map.put("message", "Invalid Authorization header " + e.getLocalizedMessage());
            ErrorResponse(response, map);
            log.info("用户:"+ JwtUtil.userNameByReq(request)+"令牌过期");
            return false;
        }
        return true;
    }

    // 被拦截的请求响应
    private void ErrorResponse(HttpServletResponse response, Map<String, Object> result){
        OutputStream out = null;
        JSONObject object = new JSONObject();
        object.put("result", result);
        try{
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(object.toString().getBytes());
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try {
                if (out != null) {
                    out.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}