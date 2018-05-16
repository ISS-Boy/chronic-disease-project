package org.smartloli.kafka.eagle.web.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.smartloli.kafka.eagle.web.pojo.Menu;
import org.smartloli.kafka.eagle.web.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 */
@Controller
@RequestMapping(value="/menu")
public class MenuController{

	@Resource(name="menuService")
	private MenuService menuService;

	private Logger logger = Logger.getLogger(this.getClass().toString());
	/**
	 * 显示菜单列表
	 * @return
	 */
	@RequestMapping(value="/menu")
	public ModelAndView list()throws Exception{
		ModelAndView mv = new ModelAndView();
		try{
			List<Menu> menuList = menuService.listAllMenu();
			mv.addObject("menuList", menuList);
			mv.setViewName("/menu/menu_list");
		} catch(Exception e){
			logger.warning(e.toString());
		}
		
		return mv;
	}
	
	/**
	 * 请求新增菜单页面
	 * @return
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd()throws Exception{
		ModelAndView mv = new ModelAndView();
		try{
			List<Menu> menuList = menuService.listAllParentMenu();
			mv.addObject("menuList", menuList);
			mv.addObject("type", "addMenu");
			mv.setViewName("/menu/menu_edit");
		} catch(Exception e){
			logger.warning(e.toString());
		}
		return mv;
	}

	/**
	 * 保存新增菜单信息
	 * @param menu
	 * @return
	 */
	@RequestMapping(value="/addMenu")
	public ModelAndView addMenu(Menu menu)throws Exception{
		ModelAndView mv = new ModelAndView();
		menuService.saveAddMenu(menu);
		mv.addObject("msg", "success");
		mv.setViewName("/public/save_result");
		return mv;

	}

	/**
	 * 请求编辑菜单页面
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit(@RequestParam("id") int id, HttpServletRequest httpServletRequest)throws Exception{
		ModelAndView mv = new ModelAndView();
		try{
			Menu menu = menuService.findMenuById(id);
			List<Menu> menuList = menuService.listAllParentMenu();
			mv.addObject("menuList", menuList);
			mv.addObject("menu", menu);
			mv.addObject("type", "editMenu");
			mv.setViewName("/menu/menu_edit");
		} catch(Exception e){
			logger.warning(e.toString());
		}
		return mv;
	}


	/**
	 * 保存新增菜单信息
	 * @param menu
	 * @return
	 */
	@RequestMapping(value="/editMenu")
	public ModelAndView editMenu(Menu menu)throws Exception{
		ModelAndView mv = new ModelAndView();
		menuService.updateMenuById(menu);
		mv.addObject("msg", "success");
		mv.setViewName("/public/save_result");
		return mv;

	}
//
//	/**
//	 * 请求编辑菜单图标页面
//	 * @param
//	 * @return
//	 */
//	@RequestMapping(value="/toEditicon")
//	public ModelAndView toEditicon(String MENU_ID)throws Exception{
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			pd.put("MENU_ID",MENU_ID);
//			mv.addObject("pd", pd);
//			mv.setViewName("system/menu/menu_icon");
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}
//
//	/**
//	 * 保存菜单图标 (顶部菜单)
//	 * @param
//	 * @return
//	 */
//	@RequestMapping(value="/editicon")
//	public ModelAndView editicon()throws Exception{
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			pd = menuService.editicon(pd);
//			mv.addObject("msg","success");
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//			mv.addObject("msg","failed");
//		}
//		mv.setViewName("save_result");
//		return mv;
//	}
//
//
//	/**
//	 * 获取当前菜单的所有子菜单
//	 * @param menuId
//	 * @param response
//	 */
//	@RequestMapping(value="/sub")
//	public void getSub(@RequestParam String MENU_ID,HttpServletResponse response)throws Exception{
//		try {
//			List<Menu> subMenu = menuService.listSubMenuByParentId(MENU_ID);
//			JSONArray arr = JSONArray.fromObject(subMenu);
//			PrintWriter out;
//
//			response.setCharacterEncoding("utf-8");
//			out = response.getWriter();
//			String json = arr.toString();
//			out.write(json);
//			out.flush();
//			out.close();
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}
//	}
//
	/**
	 * 删除菜单
	 * @param id
	 * @param out
	 */
	@RequestMapping(value="/delMenu")
	public void delete(@RequestParam int id,PrintWriter out)throws Exception{

		try{
			menuService.deleteMenuById(id);
			out.write("success");
			out.flush();
			out.close();
		} catch(Exception e){
			logger.warning(e.toString());
		}

	}
}
