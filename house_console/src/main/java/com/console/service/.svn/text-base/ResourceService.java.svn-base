package com.console.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.console.entity.TSysResource;
import com.console.framework.dao.MyBatisDao;

@Service
public class ResourceService {
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	public List<TSysResource>  getAllResource()
	{
		List<TSysResource>  list=myBatisDao.getList("resourceMapper.getResource");
		return list;
	}
	
	
	public TSysResource	getResourceById(Integer id)
	{
		TSysResource resource=myBatisDao.getOne("resourceMapper.getResourceById", id);
		return resource;
	}
	
	public void addResource(TSysResource resource) throws Exception
	{
		TSysResource has=myBatisDao.getOne("resourceMapper.findResource",resource);
		if(has!=null)
			System.out.println("--------"+has.getName() + "---"+has.getPrivilege());
		if(has != null )
			throw new Exception("资源已存在！ name:" + resource.getName());
		myBatisDao.save("resourceMapper.insertResource", resource);
	}


	public void updateResource(TSysResource resource) {
		myBatisDao.update("resourceMapper.updateResource", resource);
	}

	
	public void deleteResource(Integer resource_id) throws Exception {
		TSysResource res=new TSysResource();
		res.setParent_id(resource_id);
		TSysResource childResource=myBatisDao.getOne("resourceMapper.findResource", res);
		if(childResource !=null)
			throw new Exception("存在下级资源，请先删除下级资源！");
		myBatisDao.delete("resourceMapper.deleteResource", resource_id);
	}

}
