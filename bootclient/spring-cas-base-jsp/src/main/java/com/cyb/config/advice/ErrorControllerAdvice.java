package com.cyb.config.advice;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kiiik.pub.bean.ResultBean;

import net.sf.json.JSONObject;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年5月15日
 */
@Controller
public class ErrorControllerAdvice implements ErrorController {
	Log log = LogFactory.getLog(ErrorControllerAdvice.class);
	private final static String ERROR_PATH = "/error";

	/**
	 * Supports the HTML Error View
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ERROR_PATH, produces = "text/html")
	@ResponseBody
	public String errorHtml(HttpServletRequest request, HttpServletResponse response, Exception e) {
		if (!isProduction) {
			return JSONObject.fromObject(buildBody(request, response, true)).toString();
		} else {
			return JSONObject.fromObject(buildBody(request, response, false)).toString();
		}
	}

	boolean isProduction = false;

	/**
	 * Supports other formats like JSON, XML
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ERROR_PATH, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Object error(HttpServletRequest request, HttpServletResponse response, Exception e) {
		if (!isProduction) {
			return buildBody(request, response, true);
		} else {
			return buildBody(request, response, false);
		}
	}

	/**
	 * Returns the path of the error page.
	 *
	 * @return the error path
	 */
	public String getErrorPath() {
		return ERROR_PATH;
	}

	@Autowired
	private ErrorAttributes errorAttributes;

	@SuppressWarnings({ "unused" })
	private ResultBean<String> buildBody(HttpServletRequest request, HttpServletResponse response,
			Boolean includeStackTrace) {
		Map<String, Object> errorAttributes = getErrorAttributes(request, includeStackTrace);
		Integer status = (Integer) errorAttributes.get("status");
		String path = (String) errorAttributes.get("path");
		String messageFound = (String) errorAttributes.get("message");
		String message = "";
		String trace = "";
		if (!StringUtils.isEmpty(path)) {
			message = String.format("[" + response.getStatus() + "]Requested path %s with result %s", path,
					messageFound);
		}
		if (includeStackTrace) {
			trace = (String) errorAttributes.get("trace");
			if (!StringUtils.isEmpty(trace)) {
				message += String.format(" and trace %s", trace);
			}
		}
		return new ResultBean<String>().refuse(path + " " + getResponseStatusDesc(response.getStatus()));
	}

	public String getResponseStatusDesc(int status) {
		switch (status) {
		case 401:
			return "没有授权！";
		case 402:
			return "Payment Required ";
		case 403:
			return "没有访问权限！";
		case 404:
			return "页面不存在！";
		case 405:
			return "方法不支持！";
		case 500:
			return "服务器故障！";
		case 503:
			return "服务器维护中！";
		default:
			return "错误码" + status + "未发现！";
		}
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
	}
}
