package muin.mvc.model.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import muin.mvc.model.dto.MovieDTO;
import muin.mvc.model.service.ApiJson;
import muin.mvc.model.service.ApiService;
import muin.mvc.model.service.DetailJson;
import muin.mvc.model.service.DetailService;


@Controller
public class ApiController {
	
	
	@Autowired	 // api �����͸� ������ ��ü ����
	private ApiJson aJson;
	
	@Autowired
	private ApiService sv;
	
	@Autowired	 
	private DetailJson dJson;
	
	@Autowired
	private DetailService dsv;
	
	/**
	 * ��ü api ������ ����
	 * */
	@RequestMapping("/detail/list")
		public void insertAll() {
		List<MovieDTO> list = aJson.insertAll();
		sv.insertAll(list);
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
	}
	
	
	
	/**
	 * �󼼺��� api ������
	 * */
	@RequestMapping("detail/movieDetail")
	public String detail() {
	    String title="�õ庸��";	    
		  
			
	    MovieDTO dto =dJson.getJson(title);
			
			
	    dsv.insert(dto);
	    
	    Map<String, String> map = new HashMap<String, String>();
	    //editing detail page!! please just a moment! I will finish today!
			
		return "detail/movieDetail";
	}
	
}
