package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        Scanner sc= new Scanner(System.in);

        SellerDao sellerDao= DaoFactory.createSellerDao();

        System.out.println("===TEST 1: seller findById===");
        Seller seller= sellerDao.findById(3);
        System.out.println(seller);

        System.out.println();

        System.out.println("===TEST 2: seller findByDepartment===");
        Department department= new Department(2,null);
        List<Seller> list= sellerDao.findByDepartment(department);
        for(Seller obj: list){
            System.out.println(obj);
        }

        System.out.println();

        System.out.println("===TEST 3: seller findAll===");
        list= sellerDao.findAll();
        for(Seller obj: list){
            System.out.println(obj);
        }

        System.out.println();

        System.out.println("===TEST 4: seller insert===");
        Seller seller1= new Seller(null,"Greg","greg@gmail.com",new Date(),4000.0,department);
        sellerDao.insert(seller1);
        System.out.println("Inserted! New id= "+seller1.getId());

        System.out.println();

        System.out.println("===TEST 5: seller update===");
        seller=sellerDao.findById(1);
        seller.setName("Martha Wein");
        sellerDao.update(seller);
        System.out.println("Update Complete");


        System.out.println();

        System.out.println("===TEST 6: seller delete===");
        System.out.print("Enter id for delete test:");
        int id= sc.nextInt();

        sellerDao.deleteById(id);

        System.out.println("Delete complete");

        sc.close();

    }
}
