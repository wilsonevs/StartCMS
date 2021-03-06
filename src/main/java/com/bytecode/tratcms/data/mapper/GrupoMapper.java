package com.bytecode.tratcms.data.mapper;

import com.bytecode.tratcms.data.model.MGrupo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GrupoMapper implements RowMapper<MGrupo> {
    @Override
    public MGrupo mapRow(ResultSet rs, int rowNum) throws SQLException {
        MGrupo MGrupo = new MGrupo();
        MGrupo.setIdGrupo(rs.getInt("IdGrupo"));
        MGrupo.setNombre(rs.getString("Nombre"));
        MGrupo.setFecha(rs.getDate("Fecha"));
        return MGrupo;
    }
}
