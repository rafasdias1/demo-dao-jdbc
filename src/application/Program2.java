package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Scanner;

public class Program2 {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);

        DepartmentDao departmentDao= DaoFactory.createDepartmentDao();

        System.out.println("===TEST 1: department findById===");
        Department department = departmentDao.findById(3);
        System.out.println(department);

        System.out.println();
    }
}
