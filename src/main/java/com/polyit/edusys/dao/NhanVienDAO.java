/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.polyit.edusys.dao;

import com.polyit.edusys.entity.NhanVien;
import com.polyit.edusys.utils.XJbdc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nothi
 */
public class NhanVienDAO extends EduSysDAO<NhanVien, String>{
    String INSERT_SQL = "INSERT INTO NHANVIEN(MaNV, MatKhau, HoTen, VaiTro, GioiTinh) VALUES(?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE NHANVIEN SET MatKhau = ?, HoTen = ?, VaiTro = ?, GioiTinh = ? WHERE MaNV = ?";
    String DELETE_SQL = "DELETE FROM NHANVIEN WHERE MaNV = ?";
    String SELECT_ALL_SQL = "SELECT * FROM NHANVIEN";
    String SELECT_BY_ID_SQL = "SELECT * FROM NHANVIEN WHERE MaNV = ?";
    @Override
    public void insert(NhanVien entity) {
        XJbdc.update(INSERT_SQL, entity.getMaNV(),entity.getMatKhau(), entity.getHoTen(), 
                entity.isVaiTro(),entity.getGioiTinh());
    }

    @Override
    public void update(NhanVien entity) {
        XJbdc.update(UPDATE_SQL, entity.getMatKhau(), entity.getHoTen(), entity.isVaiTro(),entity.getGioiTinh(), entity.getMaNV());
    }

    @Override
    public void delete(String key) {
        XJbdc.update(DELETE_SQL, key);
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NhanVien selectById(String key) {
        List<NhanVien> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list =  new ArrayList<>();
        try {
            ResultSet rs = XJbdc.query(sql, args);
            while (rs.next()) {                
                NhanVien entity =  new NhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setHoTen(rs.getString("HoTen"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));
                entity.setGioiTinh(rs.getString("GioiTinh"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
