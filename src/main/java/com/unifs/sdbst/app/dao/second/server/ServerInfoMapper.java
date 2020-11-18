package com.unifs.sdbst.app.dao.second.server;

import com.unifs.sdbst.app.bean.server.ServerInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ServerInfoMapper {
    int insert(ServerInfo record);

    int insertSelective(ServerInfo record);
    List<ServerInfo> selectByType(ServerInfo record);
}