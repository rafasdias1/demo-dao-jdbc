package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn=conn;
    }


    @Override
    public void update(Seller obj) {
        PreparedStatement st=null;
        try {
            st=conn.prepareStatement(
                    "update seller "
                    +"set name=?, email=?, birthdate=?, basesalary=?, departmentid=? "
                    +"where id=? ");

            st.setString(1,obj.getName());
            st.setString(2,obj.getEmail());
            st.setDate(3,new Date(obj.getDate().getTime()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5,obj.getDepartment().getId());
            st.setInt(6,obj.getId());

            st.executeUpdate();
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }


    }

    @Override
    public void insert(Seller obj)
    {
        PreparedStatement st=null;
        try {
            st=conn.prepareStatement(
                    "insert into seller "
                            +"(name,email,birthdate,basesalary,departmentid) "
                            +"values "
                            +"(?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1,obj.getName());
            st.setString(2,obj.getEmail());
            st.setDate(3,new Date(obj.getDate().getTime()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5,obj.getDepartment().getId());


            int rowsAffected=st.executeUpdate();
            if(rowsAffected>0){
                ResultSet rs= st.getGeneratedKeys();
                if(rs.next()){
                    int id=rs.getInt(1);
                    obj.setId(id);
                    DB.closeResultSet(rs);
                }
            }else {
                throw new DbException("Unexpected Error! No rows affected");
            }
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }


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
                Department dep=instantiateDepartment(rs);
                Seller obj= instantiateSeller(rs,dep);
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

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller obj= new Seller();
        obj.setId(rs.getInt("id"));
        obj.setName(rs.getString("name"));
        obj.setEmail(rs.getString("email"));
        obj.setBaseSalary(rs.getDouble("basesalary"));
        obj.setDate(rs.getDate("birthdate"));
        obj.setDepartment(dep);
        return obj;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep= new Department();
        dep.setId(rs.getInt("departmentid"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st=null;
        ResultSet rs=null;

        try {
            st=conn.prepareStatement(
                    "select seller.*, department.name as DepName "
                            + "from seller inner join department "
                            +"on seller.departmentid=department.id "
                            +"order by name ");

            rs=st.executeQuery();

            List<Seller> list=new ArrayList<>();
            Map<Integer,Department> map=new HashMap<>();

            while(rs.next()){
                Department dep=map.get(rs.getInt("departmentid"));
                if(dep==null){
                    dep=instantiateDepartment(rs);
                    map.put(rs.getInt("departmentid"),dep);
                }

                Seller obj= instantiateSeller(rs,dep);
                list.add(obj);
            }
            return list;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);

        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st=null;
        ResultSet rs=null;

        try {
            st=conn.prepareStatement(
                    "select seller.*, department.name as DepName "
                            + "from seller inner join department "
                            +"on seller.departmentid=department.id "
                            +"where departmentid=? "
                            +"order by name ");

            st.setInt(1,department.getId());
            rs=st.executeQuery();

            List<Seller> list=new ArrayList<>();
            Map<Integer,Department> map=new HashMap<>();

            while(rs.next()){
                Department dep=map.get(rs.getInt("departmentid"));
                if(dep==null){
                    dep=instantiateDepartment(rs);
                    map.put(rs.getInt("departmentid"),dep);
                }

                Seller obj= instantiateSeller(rs,dep);
                list.add(obj);
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
