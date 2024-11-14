/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.polyit.edusys.dao;

import com.polyit.edusys.entity.ChuyenDe;
import com.polyit.edusys.utils.XJbdc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nothi
 */
public class ChuyenDeDAO extends EduSysDAO<ChuyenDe, String>{
    String INSERT_SQL = "INSERT INTO CHUYENDE(MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa) VALUES(?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE CHUYENDE SET TenCD = ?, HocPhi = ?, ThoiLuong = ?, Hinh = ?, Mota = ? WHERE MaCD = ?";
    String DELETE_SQL = "DELETE FROM CHUYENDE WHERE MaCD = ?";
    String SELECT_ALL_SQL = "SELECT * FROM CHUYENDE";
    String SELECT_BY_ID_SQL = "SELECT * FROM CHUYENDE WHERE MaCD = ?";
    @Override
    public void insert(ChuyenDe entity) {
        XJbdc.update(INSERT_SQL, entity.getMaCD(),entity.getTenCD(), entity.getHocPhi(),
                entity.getThoiLuong(), entity.getHinh(), entity.getMoTa());
    }

    @Override
    public void update(ChuyenDe entity) {
        XJbdc.update(UPDATE_SQL, entity.getTenCD(), entity.getHocPhi(), entity.getThoiLuong(), 
                entity.getHinh(), entity.getMoTa(), entity.getMaCD());
    }

    @Override
    public void delete(String key) {
        XJbdc.update(DELETE_SQL, key);
    }

    @Override
    public List<ChuyenDe> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public ChuyenDe selectById(String key) {
        List<ChuyenDe> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<ChuyenDe> selectBySql(String sql, Object... args) {
        List<ChuyenDe> list =  new ArrayList<>();
        try {
            ResultSet rs = XJbdc.query(sql, args);
            while (rs.next()) {                
                ChuyenDe entity =  new ChuyenDe();
                entity.setMaCD(rs.getString("MaCD"));
                entity.setTenCD(rs.getString("TenCD"));
                entity.setHocPhi(rs.getDouble("HocPhi"));
                entity.setThoiLuong(rs.getInt("ThoiLuong"));
                entity.setHinh(rs.getString("Hinh"));
                entity.setMoTa(rs.getString("MoTa"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<ChuyenDe> selectByKeyword(String keyword){
        String sql = "SELECT * FROM CHUYENDE WHERE MaCD LIKE ? OR TenCD LIKE ?";
         String wildcardKeyword = "%" + keyword + "%";
        return this.selectBySql(sql, wildcardKeyword, wildcardKeyword);
    }
    
}
