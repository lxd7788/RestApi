package com.lxd.RestDemo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//第一步添加注解表示这是rest
@RestController
@RequestMapping("/testdemo")
public class RestDemoController {
	//get请求
	@GetMapping
	public List<PojoDemo> tset(){
		List<PojoDemo> result = new ArrayList<PojoDemo>();
		result.add(new PojoDemo("001","三三"));
		result.add(new PojoDemo("002","二二"));
		result.add(new PojoDemo("003", "四四"));
		return result;
	}
	//查看资源下的字资源
	//根据后面的参数进行映射，获取id值
	@GetMapping("/{id}")
	public List<PojoDemo> id(@PathVariable int id){
		System.out.println("id===是"+id);
		List<PojoDemo> result = new ArrayList<PojoDemo>();
		if(id==001) {
			result.add(new PojoDemo("001","三三"));
			return result;
		}else {
			result.add(new PojoDemo("002","三三"));
			return result;
		}
	}
	//post请求（添加）
	//@RequestBody PojoDemo pojoDemo 读取客户端发送的数据
	@PostMapping
	public PojoDemo post(@RequestBody PojoDemo pojoDemo) {
		return pojoDemo;
		
	}
	//put请求（更新）
	@PutMapping("/{id}")
	public PojoDemo put(@PathVariable int id,@RequestBody PojoDemo pojoDemo) {
		return pojoDemo;
	}
	//delete(删除)
	//url------Delete http://10.60.5.16:8080/testdemo/001?delete_reason=chongfu
	@DeleteMapping("/{id}")
	public Map<String,Object> del(@PathVariable int id,HttpServletRequest request,
			@RequestParam(value="delete_reason",required=false) String deletaReason){
		System.out.println(id+"被"+request.getRemoteAddr()+"删除，原因是"+deletaReason);
		//打印结果：001被10.60.5.16删除，原因是chongfu
		return null;		
	}
	//post请求上传文件
	@PostMapping(value="/{id}/photos", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public void postFile(@PathVariable int id,@RequestParam("photo")MultipartFile imgfile) throws Exception {
		//保存文件
		FileOutputStream fos =new FileOutputStream("地址");
		IOUtils.copy(imgfile.getInputStream(), fos);
		fos.close();
		
	}
}
