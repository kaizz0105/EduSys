/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.polyit.edusys.dao;

import com.polyit.edusys.entity.HocVien;
import com.polyit.edusys.utils.XJbdc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nothi
 */
public class HocVienDAO extends EduSysDAO<HocVien, Integer>{
    String INSERT_SQL = "INSERT INTO HOCVIEN(MaKH, MaNH, Diem) VALUES(?,?,?)";
    String UPDATE_SQL = "UPDATE HOCVIEN SET MaKH = ?, MANH = ?, Diem = ? WHERE MaHV = ?";
    String DELETE_SQL = "DELETE FROM HOCVIEN WHERE MaHV = ?";
    String SELECT_ALL_SQL = "SELECT * FROM HOCVIEN";
    String SELECT_BY_ID_SQL = "SELECT * FROM HOCVIEN WHERE MaHV = ?";

    @Override
    public void insert(HocVien entity) {
        XJbdc.update(INSERT_SQL, entity.getMaKH(), entity.getMaNH(), entity.getDiem());
    }

    @Override
    public void update(HocVien entity) {
        XJbdc.update(UPDATE_SQL, entity.getMaKH(), entity.getMaNH(), entity.getDiem(), entity.getMaHV());
    }

    @Override
    public void delete(Integer key) {
        XJbdc.update(DELETE_SQL, key);
    }

    @Override
    public List<HocVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HocVien selectById(Integer key) {
        List<HocVien> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<HocVien> selectBySql(String sql, Object... args) {
        List<HocVien> list = new ArrayList<>();
        try {
            ResultSet rs = XJbdc.query(sql, args);
            while (rs.next()) {                
                HocVien entity = new HocVien();
                entity.setMaHV(rs.getInt("MaHV"));
                entity.setMaKH(rs.getInt("MaKH"));
                entity.setMaNH(rs.getString("MaNH"));
                entity.setDiem(rs.getDouble("Diem"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<HocVien> selectByKhoaHoc(int maKH){
        String sql =  "SELECT * FROM HOCVIEN WHERE MaKH=?";
        return this.selectBySql(sql, maKH);
    }

    
    
}
