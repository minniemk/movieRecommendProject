package muin.mvc.model.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import muin.mvc.model.dto.MovieDTO;
import muin.mvc.model.service.ApiJson;
import muin.mvc.model.service.ApiService;


@Controller
public class ApiController {
	
	
	@Autowired	 // api �����͸� ������ ��ü ����
	private ApiJson aJson;
	
	@Autowired
	private ApiService sv;
	
	
	/**
	 * ��ü api ������ ����
	 * */
	@RequestMapping("api/insert")
		public void insertAll() {
		List<MovieDTO> list = aJson.insertAll();
		sv.insertAll(list);
	}
	
	
	/**
	 * ��ü api ������ ��� ���
	 * */
	@RequestMapping("api/list")
		public ModelAndView selectAll() {
		List<MovieDTO> list = sv.selectAll();
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		return mv;
	}
	
	
	/**
	 * �󼼺��� api ������
	 * */
	@RequestMapping("api/movieDetail/{movieNo}")
	public ModelAndView detail(@PathVariable("movieNo") int movieNo, ModelAndView mv) {
	
		mv.setViewName("api/movieDetail");
		mv.addObject("MovieDTO",sv.detailMovie(movieNo));
		System.out.println("detail ���� . . .");
	//	System.out.println("���� �� "+request.getParameter("movieNo"));
		return mv;
	}
	
	
	/**
	 * ��� �󼼺���
	 * */
//	@RequestMapping("api/actiorDetail/{movieActor}")
//	public ModelAndView detailActor(@PathVariable("movieActor") String movieActor, ModelAndView mv) {
//		mv.setViewName("api/actiorDetail");
//		mv.addObject("MovieDTO",sv.detailMovie(movieActor));
//		System.out.println("Actor Detail Start . . .");
//		return mv;
//	}
	
}
