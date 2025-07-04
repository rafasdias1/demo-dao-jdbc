package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program2 {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);

        DepartmentDao departmentDao= DaoFactory.createDepartmentDao();

        System.out.println("===TEST 1: department findById===");
        Department department = departmentDao.findById(3);
        System.out.println(department);

        System.out.println();

        System.out.println("===TEST 2: department findAll===");

        List<Department> list= new ArrayList<>();
        list=departmentDao.findAll();

        for(Department obj:list){
            System.out.println(obj);
        }


        System.out.println();

        System.out.println("===TEST 3: department insert===");

        Department department1=new Department(null,"Constructions");
        departmentDao.insert(department1);
        System.out.println("Inserted! New id= "+department1.getId());


        System.out.println();

        System.out.println("===TEST 4: department update===");

        department1=departmentDao.findById(1);
        department1.setName("Human Resources");
        departmentDao.update(department1);

        System.out.println("Update complete");

        System.out.println();

        System.out.println("===TEST 5: department delete===");

        System.out.print("Enter id for delete:");
        int id=sc.nextInt();
        departmentDao.deleteById(id);

        System.out.println("Delete complete");



    }
}
