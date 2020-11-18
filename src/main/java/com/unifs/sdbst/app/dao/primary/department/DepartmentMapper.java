package com.unifs.sdbst.app.dao.primary.department;



import com.unifs.sdbst.app.bean.department.DepartmentInfo;
import com.unifs.sdbst.app.bean.menus.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface DepartmentMapper {

    public DepartmentInfo getDepartmentInfoByDepName(@Param("dep_name") String dep_name);

    public List<Menu> findChildsMenu(@Param("id") String id);

    public List<String> getDepartmentType();

    public List<String> getDepartmentByType(@Param("department_type") String department_type);

    public Map<String,String> getDepartmentContactByDepName(@Param("dep_name") String dep_name);
}
