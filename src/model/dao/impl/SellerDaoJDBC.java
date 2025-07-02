package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn=conn;
    }


    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st=null;
        ResultSet rs=null;

        try {
            st=conn.prepareStatement(
                    "select seller.*, department.name as DepName "
                    + "from seller inner join department "
                    +"on seller.departmentid=department.id "
                    +"where seller.id=? ");

            st.setInt(1,id);
            rs=st.executeQuery();

            if(rs.next()){
                Department dep=new Department();
                dep.setId(rs.getInt("departmentid"));
                dep.setName(rs.getString("DepName"));
                Seller obj= new Seller();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setEmail(rs.getString("email"));
                obj.setBaseSalary(rs.getDouble("basesalary"));
                obj.setDate(rs.getDate("birthdate"));
                obj.setDepartment(dep);
                return obj;
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
    public List<Seller> findAll() {
        return List.of();
    }
}
