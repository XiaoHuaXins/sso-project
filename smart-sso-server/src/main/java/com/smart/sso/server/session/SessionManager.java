package com.smart.sso.server.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.smart.sso.client.rpc.SsoUser;
import com.smart.sso.server.constant.AppConstant;
import com.smart.sso.server.util.CookieUtils;

/**
 * 服务端凭证管理
 * 
 * @author Joe
 */
@Component
public class SessionManager {
	
	@Autowired
	private AccessTokenManager accessTokenManager;
	@Autowired
	private TicketGrantingTicketManager ticketGrantingTicketManager;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 密码校验成功后调用此方法
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	public String setUser(SsoUser user, HttpServletRequest request, HttpServletResponse response) {
		//获取Cookie中的ticket grant ticket
		String tgt = getCookieTgt(request);
		//TODO 浏览器禁用Cookie怎么办？
		if (StringUtils.isEmpty(tgt)) {// cookie中没有
			//生成tgt凭证，并加入本地凭证管理器中
			tgt = ticketGrantingTicketManager.generate(user);
			
			// TGT存cookie，和Cas登录保存cookie中名称一致为：TGC
			// 为response添加TGC-Cookie
			CookieUtils.addCookie(AppConstant.TGC, tgt, "/", request, response);
		}
		/**
		 * 如果存在在本地能够找到该凭证并且未过期，那么则刷新该tgt的存在时间。
		 *
		 * 否则，重新生成一个tgt存入本地管理器
		 */
		else if(ticketGrantingTicketManager.getAndRefresh(tgt) == null){
			//重新生成tgt
			ticketGrantingTicketManager.create(tgt, user);
		}
		else {
			//更新该tgt凭证所对应的用户信息
			ticketGrantingTicketManager.set(tgt, user);
		}
		return tgt;
	}

	public SsoUser getUser(HttpServletRequest request) {
		String tgt = getCookieTgt(request);
		if (StringUtils.isEmpty(tgt)) {
			return null;
		}
		return ticketGrantingTicketManager.getAndRefresh(tgt);
	}

	public void invalidate(HttpServletRequest request, HttpServletResponse response) {
		String tgt = getCookieTgt(request);
		if (StringUtils.isEmpty(tgt)) {
			return;
		}
		// 删除登录凭证
		ticketGrantingTicketManager.remove(tgt);
		// 删除凭证Cookie
		CookieUtils.removeCookie(AppConstant.TGC, "/", response);
		// 删除所有tgt对应的调用凭证，并通知客户端登出注销本地session
	    accessTokenManager.remove(tgt);
	}

	public String getTgt(HttpServletRequest request) {
		String tgt = getCookieTgt(request);
		logger.info("从Cookie获取到的tgt为：{}",tgt);
		if (StringUtils.isEmpty(tgt) || ticketGrantingTicketManager.getAndRefresh(tgt) == null) {
			return null;
		}
		else {
			return tgt;
		}
	}
	
	private String getCookieTgt(HttpServletRequest request) {
		return CookieUtils.getCookie(request, AppConstant.TGC);
	}
}