package com.unifs.sdbst.app.service.data;

import com.unifs.sdbst.app.bean.data.SmzcTerminalEntity;
import com.unifs.sdbst.app.bean.data.VSmzcTerminalEntity;
import com.unifs.sdbst.app.utils.StringUtils;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rxframework.base.model.OutPut;
import rxframework.base.service.impl.RootService;
import rxframework.persistent.cmd.ICondition;
import rxframework.persistent.cmd.IQueryResult;
import rxframework.persistent.cmd.ISelect;
import rxframework.persistent.cmd.OrderType;
import rxframework.persistent.cmd.impl.Condition;
import rxframework.persistent.cmd.impl.Select;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @title: SmzcTerminalService
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/10/31 15:49
 */
@Transactional
@Service
public class SmzcTerminalService {
    public <T extends SmzcTerminalEntity> List<T> getAllList(OutPut<Long> total, T entity, Map<String, Object> extraParams)
    {
        return getList(-1, -1, total, entity, extraParams);
    }

    public <T extends SmzcTerminalEntity> List<T> getList(int start, int limit, OutPut<Long> total, T entity, Map<String, Object> extraParams)
    {
        DataGrid dataGrid = new DataGrid();
        dataGrid.setRows(limit);
        dataGrid.setPage(start / limit + 1);
        List<T> list = getList(dataGrid, entity, extraParams);
        total.set(dataGrid.getTotal());
        return list;
    }

    public <T extends SmzcTerminalEntity> List<T> getList(DataGrid dataGrid, T entity, Map<String, Object> extraParams)
    {
        ICondition condition = new Condition();
        condition.like("ID", entity.getId());
        condition.like("TOWN_ID", entity.getTownId());
        condition.like("SERVICE_POINT", entity.getServicePoint());
        condition.like("ADDRESS", entity.getAddress());
        condition.eq("LNG", entity.getLng());
        condition.eq("LAT", entity.getLat());
        condition.like("CONTACTS", entity.getContacts());
        condition.like("MOBILE", entity.getMobile());
        condition.like("TERMINAL_NO", entity.getTerminalNo());
        condition.like("TERMINAL_TYPE", entity.getTerminalType());
        condition.like("OPENING_HOURS", entity.getOpeningHours());
        condition.like("REMARK", entity.getRemark());
        condition.like("CREATER", entity.getCreater());
        condition.eq("CREATE_TIME", entity.getCreateTime());
        condition.like("MODIFIER", entity.getModifier());
        condition.eq("MODIFY_TIME", entity.getModifyTime());
        condition.eq("RECORD_STATUS", entity.getRecordStatus());
        if (extraParams != null)
        {
            Map.Entry localEntry;
            for (Iterator localIterator = extraParams.entrySet().iterator(); localIterator.hasNext(); localEntry = (Map.Entry)localIterator.next()) {}
        }
        ISelect select = new Select()
                .forObject(entity.getClass()).setCondition(condition)
                .setPager(dataGrid.getStart(), dataGrid.getRows());

        String sort = dataGrid.getSort();
        SortDirection order = dataGrid.getOrder();
        if (!StringUtils.isNullOrEmpty(sort))
        {
            sort = StringUtils.toUnderLine(sort);
            if (order.equals(SortDirection.desc)) {
                select = select.order(sort, OrderType.DESC);
            } else {
                select = select.order(sort, OrderType.ASC);
            }
        }
        IQueryResult<T> qrList = select.selectForResult();
        dataGrid.setTotal(Long.valueOf(qrList.getTotal()));
        List<T> list = qrList.getList();
        return list;
    }

    public <T extends VSmzcTerminalEntity> List<T> getList(DataGrid dataGrid, T entity, Map<String, Object> extraParams)
    {
        ICondition condition = new Condition();
        condition.like("ID", entity.getId());
        condition.like("TOWN_ID", entity.getTownId());
        condition.like("SERVICE_POINT", entity.getServicePoint());
        condition.like("ADDRESS", entity.getAddress());
        condition.eq("LNG", entity.getLng());
        condition.eq("LAT", entity.getLat());
        condition.like("CONTACTS", entity.getContacts());
        condition.like("MOBILE", entity.getMobile());
        condition.like("TERMINAL_NO", entity.getTerminalNo());
        condition.like("TERMINAL_TYPE", entity.getTerminalType());
        condition.like("OPENING_HOURS", entity.getOpeningHours());
        condition.like("REMARK", entity.getRemark());
        condition.like("CREATER", entity.getCreater());
        condition.eq("CREATE_TIME", entity.getCreateTime());
        condition.like("MODIFIER", entity.getModifier());
        condition.eq("MODIFY_TIME", entity.getModifyTime());
        condition.eq("RECORD_STATUS", entity.getRecordStatus());
        condition.like("TOWN_NAME", entity.getTownName());
        condition.like("TYPE_NAME", entity.getTypeName());
        if (extraParams != null)
        {
            Map.Entry localEntry;
            for (Iterator localIterator = extraParams.entrySet().iterator(); localIterator.hasNext(); localEntry = (Map.Entry)localIterator.next()) {}
        }
        ISelect select = new Select()
                .forObject(entity.getClass()).setCondition(condition)
                .setPager(dataGrid.getStart(), dataGrid.getRows());

        String sort = dataGrid.getSort();
        SortDirection order = dataGrid.getOrder();
        if (!StringUtils.isNullOrEmpty(sort))
        {
            sort = StringUtils.toUnderLine(sort);
            if (order.equals(SortDirection.desc)) {
                select = select.order(sort, OrderType.DESC);
            } else {
                select = select.order(sort, OrderType.ASC);
            }
        }
        IQueryResult<T> qrList = select.selectForResult();
        dataGrid.setTotal(Long.valueOf(qrList.getTotal()));
        List<T> list = qrList.getList();
        return list;
    }
}
