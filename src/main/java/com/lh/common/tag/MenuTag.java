package com.lh.common.tag;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.lh.common.config.Global;
import com.lh.common.utils.SpringContextHolder;
import com.lh.modules.sys.entity.Menu;
import com.lh.modules.sys.utils.UserUtils;


/**
 * 
 * 类描述：菜单标签
 * 
 * 刘高峰
 * @date： 日期：2015-1-23 时间：上午10:17:45
 * @version 1.0
 */
public class MenuTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	protected Menu menu;//菜单Map
	
	

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			String menu = (String) this.pageContext.getSession().getAttribute("menu");
			if(menu!=null){
				out.print(menu);
			}else{
				menu=end().toString();
				out.print(menu);
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public StringBuffer end() {
		StringBuffer sb = new StringBuffer();
		sb.append(getChildOfTree(menu,0));
		
		System.out.println(sb);
		return sb;
		
	}
	
	private static String getChildOfTree(Menu parent, int level) {
		StringBuffer menuString = new StringBuffer();
		String href = "";
		if (!parent.hasPermisson())
			return "";
		if (level > 0) {//level 为0是功能菜单

			menuString.append("<li>");

			ServletContext context = SpringContextHolder.getBean(ServletContext.class);
			if (parent.getHref() != null && parent.getHref().length() > 0) {

				if(parent.getHref().endsWith(".html")&&!parent.getHref().endsWith("ckfinder.html")){//如果是静态资源并且不是ckfinder.html，直接访问不加adminPath
					href = context.getContextPath() + parent.getHref();
				}else{
					href = context.getContextPath() + Global.getAdminPath() + parent.getHref();
				}
			}
		}

		//判断该菜单是否有子菜单
		boolean hasChildren = false;
		for(Menu menu : UserUtils.getMenuList()){
			if(menu.getParentId().equals(parent.getId()) && menu.getIsShow().equals("1")){
				hasChildren = true;
			}
		}
		
		if (hasChildren) {
			if (level > 0) {
			menuString
					.append("<a href=\""
							+ href
							+ "\"><i class=\"fa "+parent.getIcon()+"\"></i> <span class=\"nav-label\">"
							+ parent.getName()
							+ "</span><span class=\"fa arrow\"></span></a>");
			}
			if (level == 1) {
				menuString.append("<ul class=\"nav nav-second-level\">");
			} else if (level == 2) {
				menuString.append("<ul class=\"nav nav-third-level\">");
			} else if (level == 3) {
				menuString.append("<ul class=\"nav nav-forth-level\">");
			} else if (level == 4) {
				menuString.append("<ul class=\"nav nav-fifth-level\">");
			}
			
			for(Menu menu : UserUtils.getMenuList()){
				if(menu.getParentId().equals(parent.getId()) && menu.getIsShow().equals("1")){
					menuString.append(getChildOfTree(menu, level + 1));
				}
			}
			
//			for (Menu child : parent.getChildren()) {
//				if (child.getIsShow().equals("1")) {
//					menuString.append(getChildOfTree(child, level + 1));
//				}
//			}
			if (level > 0) {
			menuString.append("</ul>");
			}
		} else {
			menuString.append("<a class=\"J_menuItem\"  href=\"" + href
					+ "\" ><i class=\"fa "+parent.getIcon()+"\"></i> <span class=\"nav-label\">"+parent.getName()+"</span></a>");
		}
		if (level > 0) {
			menuString.append("</li>");
		}

		return menuString.toString();
	}
	

	

}
