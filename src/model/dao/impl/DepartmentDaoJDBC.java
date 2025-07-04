package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn){
        this.conn=conn;
    }
    @Override
    public void insert(Department obj) {
        PreparedStatement st=null;

        try {
            st=conn.prepareStatement(
                    "insert into department "
                    +"(name) "
                    +"values "
                    +"(?) ",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1,obj.getName());

            int rowsAffected=st.executeUpdate();

            if (rowsAffected>0){
                ResultSet rs=st.getGeneratedKeys();
                if(rs.next()){
                    int id=rs.getInt(1);
                    obj.setId(id);
                }
            }else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }

    }

    @Override
    public void update(Department obj) {

        PreparedStatement st=null;
        try {
            st=conn.prepareStatement(
                    "update department "
                    +"set name=? "
                    +"where id=? ");

            st.setString(1,obj.getName());
            st.setInt(2,obj.getId());

            st.executeUpdate();
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st=null;
        try {
            st=conn.prepareStatement(
                    "delete from department where id=? ");

            st.setInt(1,id);

            st.executeUpdate();
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st=null;
        ResultSet rs=null;

        try {
            st=conn.prepareStatement("select * from department where id=? ");
            st.setInt(1,id);
            rs= st.executeQuery();

            if(rs.next()){
                Department dep=new Department();
                dep.setId(rs.getInt("id"));
                dep.setName(rs.getString("name"));
                return dep;
            }
            return null;

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st=null;
        ResultSet rs=null;

        try {
            st=conn.prepareStatement("select * from department order by name ");
            rs= st.executeQuery();

            List<Department> list=new ArrayList<>();
            while(rs.next()){
                Department dep= new Department();
                dep.setId(rs.getInt("id"));
                dep.setName(rs.getString("name"));
                list.add(dep);
            }
            return list;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
