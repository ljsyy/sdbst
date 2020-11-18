package com.unifs.sdbst.app.controller.life;

import com.unifs.sdbst.app.bean.life.KsEntity;
import com.unifs.sdbst.app.service.life.KsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "ks")
public class KsAllController {
	@Autowired
	private KsService ksService;
	//考试日历  查看
	@RequestMapping("2018zk")
	@ResponseBody
	public Map<String,Object> zkfirst(String ksh,String csrq, HttpServletRequest request) throws NoSuchAlgorithmException {
		Map<String,Object> map = new HashMap<String,Object>();
		List<KsEntity> list = new ArrayList<KsEntity>();
		List<KsEntity> ks = ksService.queryByType(ksh,csrq);

		map.put("ks", ks);
		return map;
	}
}