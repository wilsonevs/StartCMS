package com.bytecode.tratcms.data.repository;

import java.util.List;

import com.bytecode.tratcms.data.mapper.GrupoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bytecode.tratcms.data.model.MGrupo;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Repository
public class GrupoRepository implements GrupoRep{
	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void postConstruct(){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public boolean save(MGrupo object) {
		try {
			String sql = String.format("insert into Grupo (Nombre) values ('%s')", object.getNombre());
			jdbcTemplate.execute(sql);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean update(MGrupo object) {
		if(object.getIdGrupo()>0) {
			String sql = String.format("update Grupo set Nombre='%s' where IdGrupo='%d'", object.getNombre(), object.getIdGrupo());
			jdbcTemplate.execute(sql);
			return true;
		}
		return false;
	}

	@Override
	public List<MGrupo> findAll(Pageable pageable) {
		return jdbcTemplate.query("select * from grupo", new GrupoMapper());
	}

	@Override
	public MGrupo findById(int Id) {
		Object[] params = new Object[] {Id};
		return jdbcTemplate.queryForObject("select * from grupo where IdGrupo = ?",
				params, new GrupoMapper());
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
