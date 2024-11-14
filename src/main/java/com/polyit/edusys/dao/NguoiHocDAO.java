/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.polyit.edusys.dao;

import com.polyit.edusys.entity.NguoiHoc;
import com.polyit.edusys.utils.XJbdc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nothi
 */
public class NguoiHocDAO extends EduSysDAO<NguoiHoc, String>{
    String INSERT_SQL = "INSERT INTO NGUOIHOC(MaNH, HoTen, NgaySinh, GioiTinh, DienThoai, Email, GhiChu, MaNV, NgayDK) VALUES(?,?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE NGUOIHOC SET HoTen = ?, NgaySinh = ?, GioiTinh = ?, DienThoai = ?, Email = ?, GhiChu = ? , MaNV = ?, NgayDK = ? WHERE MaNH = ?";
    String DELETE_SQL = "DELETE FROM NGUOIHOC WHERE MaNH = ?";
    String SELECT_ALL_SQL = "SELECT * FROM NGUOIHOC";
    String SELECT_BY_ID_SQL = "SELECT * FROM NGUOIHOC WHERE MaNH = ?";
    @Override
    public void insert(NguoiHoc entity) {
        XJbdc.update(INSERT_SQL, entity.getMaNH(), entity.getHoTen(), entity.getNgaySinh(), entity.isGioiTinh(), entity.getDienThoai(),
                entity.getEmail(), entity.getGhiChu(), entity.getMaNV(), entity.getNgayDK());
    }

    @Override
    public void update(NguoiHoc entity) {
        XJbdc.update(UPDATE_SQL, entity.getHoTen(), entity.getNgaySinh(),entity.isGioiTinh(),entity.getDienThoai(),entity.getEmail(),
                entity.getGhiChu(),entity.getMaNV(), entity.getNgayDK(), entity.getMaNH());
    }

    @Override
    public void delete(String key) {
        XJbdc.update(DELETE_SQL, key);
    }

    @Override
    public List<NguoiHoc> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NguoiHoc selectById(String key) {
        List<NguoiHoc> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<NguoiHoc> selectBySql(String sql, Object... args) {
        List<NguoiHoc> list = new ArrayList<>();
        try {
            ResultSet rs = XJbdc.query(sql, args);
            while (rs.next()) {                
                NguoiHoc entity = new NguoiHoc();
                entity.setMaNH(rs.getString("MaNH"));
                entity.setHoTen(rs.getString("Hoten"));
                entity.setNgaySinh(rs.getDate("NgaySinh"));
                entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                entity.setDienThoai(rs.getString("DienThoai"));
                entity.setEmail(rs.getString("Email"));
                entity.setGhiChu(rs.getString("GhiChu"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setNgayDK(rs.getDate("NgayDK"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<NguoiHoc> selectByKeyword(String keyword){
        String sql = "SELECT * FROM NGUOIHOC WHERE MaNH LIKE ? OR HoTen LIKE ? OR DienThoai LIKE ?";
         String wildcardKeyword = "%" + keyword + "%";
        return this.selectBySql(sql, wildcardKeyword, wildcardKeyword, wildcardKeyword);
    }
    public List<NguoiHoc> selectNotInCourse(int makh, String keyword){
        String sql = "SELECT * FROM NGUOIHOC WHERE HoTen LIKE ? AND MaNH NOT IN (SELECT MaNH FROM HOCVIEN WHERE MaKH=?)";
        return this.selectBySql(sql, "%"+keyword+"%", makh);
    }
    
}
