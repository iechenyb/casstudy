package org.jasig.cas.web.flow;

import org.jasig.cas.authentication.Credential;
import org.jasig.cas.authentication.UsernamePasswordCaptchaCredential;
import org.jasig.cas.authentication.UsernamePasswordCaptchaSystemCredential;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.util.StringUtils;
import org.springframework.webflow.execution.RequestContext;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 验证码校验
 * Created by iechenyb on 2017/12/20.
 */
public class CaptchaVaditeAuthenticationViaFormAction extends AuthenticationViaFormAction {

    private static final String CAPTCHA_REQUIRED_MSG = "required.captcha";// 验证码必填
    private static final String CAPTCHA_ERROR_MSG = "error.captcha";// 验证码不正确

    public final String validate(final RequestContext context, final Credential credentials,
                                 final MessageContext messageContext) throws Exception {
        final HttpServletRequest request = WebUtils.getHttpServletRequest(context);
        HttpSession session = request.getSession();

        // 用户输入错误次数累计，次数大于3次则显示验证码
        int count;
        try {
            count = (Integer) context.getFlowScope().get("count");
        } catch (Exception e) {
            count = 0;
        }

        // 获取用户名密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        /*Object salt = session.getAttribute("loginTicket");
        System.out.println("前端盐值："+salt);*/
        // 用户名非空验证
        if (StringUtils.isEmpty(username)) {
            populateErrorsInstance("required.username", messageContext);
            count++;
            context.getFlowScope().put("count", count);
            return "error";
        }

        // 密码非空验证
        if (StringUtils.isEmpty(password)) {
            populateErrorsInstance("required.password", messageContext);
            count++;
            context.getFlowScope().put("count", count);
            return "error";
        }

        
      //1.0版本新增系统类型
        UsernamePasswordCaptchaSystemCredential upsc = (UsernamePasswordCaptchaSystemCredential) credentials;
        // 系统类非空验证
        if (StringUtils.isEmpty(upsc.getSystem())) {
            populateErrorsInstance("请输入系统类型！", messageContext);
            count++;
            context.getFlowScope().put("count", count);
            return "error";
        }else{
        	if(!upsc.getSystem().equals("iechenyb")){
        		populateErrorsInstance("系统类型不合法！", messageContext);
        		return "error";
        	}
        }
        // 验证码判断
        String showCaptcha = request.getParameter("showCaptcha");

        // 判断验证码是否显示
        if (StringUtils.hasText(showCaptcha)) {
            // 从session中获取验证码
            String authcode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
            session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
            UsernamePasswordCaptchaCredential upc = (UsernamePasswordCaptchaCredential) credentials;
            // 获取用户输入的验证码
            String submitAuthcode = upc.getCaptcha();
            // 验证码非空判断
            if (!StringUtils.hasText(submitAuthcode) || !StringUtils.hasText(authcode)) {
                populateErrorsInstance(CAPTCHA_REQUIRED_MSG, messageContext);
                count++;
                context.getFlowScope().put("count", count);
                return "error";
            }
            // 验证码正确性判断
            if (submitAuthcode.equals(authcode)) {
                return "success";
            } else {
                populateErrorsInstance(CAPTCHA_ERROR_MSG, messageContext);
                count++;
                context.getFlowScope().put("count", count);
                return "error";
            }
        }
        
        System.out.println("用户表单数据验证正确！新增系统类型参数为"+upsc.getSystem());
        return "success";
    }

    private void populateErrorsInstance(final String errorCode, final MessageContext messageContext) {

        try {
            messageContext.addMessage(new MessageBuilder().error().code(errorCode).defaultText(errorCode).build());
        } catch (final Exception fe) {
            logger.error(fe.getMessage(), fe);
        }
    }
}