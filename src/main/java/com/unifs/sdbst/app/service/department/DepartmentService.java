package com.unifs.sdbst.app.service.department;


import com.unifs.sdbst.app.bean.department.DepartmentInfo;
import com.unifs.sdbst.app.bean.menus.Menu;
import com.unifs.sdbst.app.dao.primary.department.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    public DepartmentInfo getDepartmentInfoByDepName(String dep_name){
        return departmentMapper.getDepartmentInfoByDepName(dep_name);
    }

    public List<Menu> findChildsMenu(String id){
        return departmentMapper.findChildsMenu(id);
    }

    public List<String> getDepartmentType(){
        return departmentMapper.getDepartmentType();
    }

    public List<String> getDepartmentByType(String department_type){
        return departmentMapper.getDepartmentByType(department_type);
    }

    public Map<String,String> getDepartmentContactByDepName(String dep_name){
        return departmentMapper.getDepartmentContactByDepName(dep_name);
    }
}
