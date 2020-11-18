package com.unifs.sdbst.app.dao.primary.life;

import com.unifs.sdbst.app.bean.life.Vaccination;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface VaccinationMapper {

	List<Vaccination> getAll();

}
