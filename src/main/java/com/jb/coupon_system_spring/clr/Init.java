package com.jb.coupon_system_spring.clr;

import com.jb.coupon_system_spring.beans.*;
import com.jb.coupon_system_spring.repos.*;
import com.jb.coupon_system_spring.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
@Order(1)
public class Init implements CommandLineRunner {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    TokenService tokenService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@ CATEGORIES @@@@@@@@@@@@@@@@@@@@@@@\n");
        CategoryEntity categoryEntity = new CategoryEntity();
        for (int i = 0; i < Category.values().length; i++) {
            categoryEntity.setId(0);
            categoryEntity.setCategory(Category.values()[i]);
            categoryRepository.save(categoryEntity);
            System.out.println(categoryEntity.getCategory());
        }
        //  System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@ COUPONS @@@@@@@@@@@@@@@@@@@@@@@\n");
        Coupon coupon1 = Coupon.builder()
                .category(Category.FOOD)
                .title("The offer of the season !!!")
                .description("Take a box of Coke for only 30 shekels")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(10)))
                .amount(100)
                .price(30)
                .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRZAuD2fbGMiTBt-UKwSi28Zo-QY3DrwAyJmQ&usqp=CAU")
                .build();
        Coupon coupon2 = Coupon.builder()
                .category(Category.CLOTHING)
                .title("Hot deal for only a few days !!")
                .description("Take a suit and a quality shirt for only 500 shekels")
                .startDate(Date.valueOf(LocalDate.now().minusDays(3)))
                .endDate(Date.valueOf(LocalDate.now()))
                .amount(30)
                .price(500)
                .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRj553G9VM9tdmYH8I1-NTht19RD87iPYaQSw&usqp=CAU")
                .build();
        Coupon coupon3 = Coupon.builder()
                .category(Category.HEALTH)
                .title("Enjoy yourself ...")
                .description("All vitamins in 20% off")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(7)))
                .amount(200)
                .price(120)
                .image("https://3.bp.blogspot.com/-Gvggvz2zB6I/Vcmu2SlEuyI/AAAAAAAALL4/FCO_y8SUDb0/s1600/2.jpg")
                .build();
        Coupon coupon4 = Coupon.builder()
                .category(Category.SPORT)
                .title("Dreams comes true...")
                .description("Quality bikes now in 40% off")
                .startDate(Date.valueOf(LocalDate.now().minusDays(10)))
                .endDate(Date.valueOf(LocalDate.now().plusDays(30)))
                .amount(30)
                .price(650)
                .image("https://lh3.googleusercontent.com/places/ANJU3Du2D5-e3bD49WwqI995CGBbbQrimIY1liK8vEcSU7wlXJvAblWxuJTJ_QU28C5QxNAxjqBt9eCp7dBhL1xreKrBJHpAfHYTLk4=s1600-w1280-h1280")
                .build();
        Coupon coupon5 = Coupon.builder()
                .category(Category.COMPUTER)
                .title("Computer + Printer - Offer that won't come back !")
                .description("computer processor i5 + large computer screen + printer for only 3500 Shekels")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(30)))
                .amount(100)
                .price(3500)
                .image("https://www.p1000.co.il/media/products/z_222886.jpg")
                .build();
        Coupon coupon6 = Coupon.builder()
                .category(Category.VACATION)
                .title("Dreaming vacation...")
                .description("Beautiful vacation at minimum price - only 700 shekels to one night in Leonardo hotels")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(21)))
                .amount(60)
                .price(700)
                .image("https://dynamic-media-cdn.tripadvisor.com/media/photo-o/29/07/7f/8b/leonardo-tiberia-building.jpg?w=1200&h=-1&s=1")
                .build();
        Coupon coupon7 = Coupon.builder()
                .category(Category.FOOD)
                .title("Mesi-bamba!!Start celebrating!!")
                .description("Mesibamba at the opening and end of each event - buy now! start the celebration! ")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(20)))
                .amount(100)
                .price(20)
                .image("https://osemcat.signature-it.com/images/Fittings/osem-hq/Upload_Pictures/Prod_Pic/6926822/Catalog/6926822_7290104508943_L_1_Enlarge.jpg")
                .build();
        Coupon coupon8 = Coupon.builder()
                .category(Category.CLOTHING)
                .title("Summer time shirts sale")
                .description("The best shirts ever now in 25% off")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(12)))
                .amount(300)
                .price(85)
                .image("https://furor.co.il/wp-content/uploads/2017/04/%D7%97%D7%95%D7%9C%D7%A6%D7%AA-%D7%A4%D7%95%D7%A8%D7%95%D7%A8-%D7%91%D7%9C%D7%95-%D7%99%D7%95%D7%A8%D7%93-%D7%A2%D7%9D-%D7%9B%D7%99%D7%A11.jpg")
                .build();
        Coupon coupon9 = Coupon.builder()
                .category(Category.COMPUTER)
                .title("The -best- SSD hard disk -lowest- price")
                .description("SSD hard disk with 500GB now only in 235 shekels")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(15)))
                .amount(150)
                .price(235)
                .image("https://img.ksp.co.il/item/104427/b_5.jpg?v=5")
                .build();
        Coupon coupon10 = Coupon.builder()
                .category(Category.FOOD)
                .title("New coffee-milk-cool drink * Enjoy * WakeUp *")
                .description("Our new coffee-milk cool drink now 1+1")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(7)))
                .amount(1000)
                .price(13)
                .image("https://b3059642.smushcdn.com/3059642/wp-content/uploads/2023/06/tnuva-shake-coffee-1_pr.jpg?lossy=2&strip=1&webp=1")
                .build();
        System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@ COMPANIES @@@@@@@@@@@@@@@@@@@@@@@\n");
        Company company1 = Company.builder()
                .name("Coca Cola")
                .email("cocacola@info.com")
                .password("2257")
                .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ3n3MqLreTOJZefCkYiklCRmeFia0wONGFfgCK_XjwQi3FX3wQ6Pb2-KQncp8m7JrXH3A&usqp=CAU")
                .coupon(coupon1)
                .build();
        coupon1.setCompany(company1);
        Company company2 = Company.builder()
                .name("Bagir")
                .email("bagir@info.com")
                .password("4646")
                .image("https://mit4mit.s3.amazonaws.com/uploads/biz/2925/big/1.jpg")
                .coupon(coupon2)
                .build();
        coupon2.setCompany(company2);
        Company company3 = Company.builder()
                .name("Super Farm")
                .email("superfarm@info.com")
                .password("8345")
                .image("https://upload.wikimedia.org/wikipedia/he/thumb/8/8d/Super_Pharm_Logo.svg/2560px-Super_Pharm_Logo.svg.png")
                .coupon(coupon3)
                .build();
        coupon3.setCompany(company3);
        Company company4 = Company.builder()
                .name("Aluf Hasport")
                .email("alufhasport@info.com")
                .password("7117")
                .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSq8RHUtHgVKCFpiQy5Xr8cn7vJv78CBRxX7mMrx-XRCJDKc2Y3BXoLD-HiTuu_GCVOYbo&usqp=CAU")
                .coupon(coupon4)
                .build();
        coupon4.setCompany(company4);
        Company company5 = Company.builder()
                .name("Bug")
                .email("bug@info.com")
                .password("8218")
                .image("https://shimeba.blob.core.windows.net/shimeba-new-container/ac12c595315f4dc895f79db78842f976.png")
                .coupon(coupon5)
                .build();
        coupon5.setCompany(company5);
        Company company6 = Company.builder()
                .name("Issta")
                .email("issta@info.com")
                .password("1023")
                .image("https://flyingoutblog.files.wordpress.com/2016/04/d790d799d7a1d7aad790-d79cd795d792d795.jpg?w=848")
                .coupon(coupon6)
                .build();
        coupon6.setCompany(company6);
        Company company7 = Company.builder()
                .name("Osem")
                .email("osem@info.com")
                .password("1362")
                .image("https://upload.wikimedia.org/wikipedia/he/2/22/Osem_Logo.svg")
                .coupon(coupon7)
                .build();
        coupon7.setCompany(company7);
        Company company8 = Company.builder()
                .name("Furor Shirts")
                .email("furor@info.com")
                .password("5698")
                .image("https://furor.co.il/wp-content/uploads/2018/11/cropped-logoFuror-2.png")
                .coupon(coupon8)
                .build();
        coupon8.setCompany(company8);
        Company company9 = Company.builder()
                .name("KSP")
                .email("ksp@info.com")
                .password("2936")
                .image("https://upload.wikimedia.org/wikipedia/he/thumb/e/e4/KSPLOGO.jpg/800px-KSPLOGO.jpg")
                .coupon(coupon9)
                .build();
        coupon9.setCompany(company9);
        Company company10 = Company.builder()
                .name("Strauss")
                .email("strauss@info.com")
                .password("6543")
                .image("https://pc-dev.co.il/wp-content/uploads/2011/02/strauss300.jpg")
                .coupon(coupon10)
                .build();
        coupon10.setCompany(company10);
        List<Company> companies = List.of(company1, company2, company3, company4, company5, company6, company7, company8, company9, company10);
        companyRepository.saveAll(companies);
        List<Coupon> coupons = List.of(coupon1, coupon2, coupon3, coupon4, coupon5, coupon6, coupon7, coupon8, coupon9, coupon10);
        couponRepository.saveAllAndFlush(coupons);
        companyRepository.findAll().forEach(c -> {
            System.out.println(c + " " + c.getCoupons());
        });

        System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@ CUSTOMERS @@@@@@@@@@@@@@@@@@@@@@@\n");
        Customer customer1 = Customer.builder()
                .firstName("David")
                .lastName("Cohen")
                .email("david@gmail.com")
                .password("7281")
                .coupons(List.of(coupon1, coupon2))
                .build();
        coupon1.setCustomer(List.of(customer1));
        Customer customer2 = Customer.builder()
                .firstName("Moshe")
                .lastName("Levi")
                .email("moshe@gmail.com")
                .password("3729")
                .coupons(List.of(coupon2))
                .build();
        coupon2.setCustomer(List.of(customer1, customer2));
        Customer customer3 = Customer.builder()
                .firstName("Gil")
                .lastName("Lasri")
                .email("gil@gmail.com")
                .password("2096")
                //   .coupons(List.of(coupon3))
                .build();
        //   coupon3.setCustomer(List.of(customer3));
        Customer customer4 = Customer.builder()
                .firstName("Idan")
                .lastName("Karadi")
                .email("idan@gmail.com")
                .password("3623")
                .coupons(List.of(coupon4))
                .build();
        coupon4.setCustomer(List.of(customer4));
        Customer customer5 = Customer.builder()
                .firstName("Yaakov")
                .lastName("Shrim")
                .email("yaakov@gmail.com")
                .password("2593")
                .coupons(List.of(coupon5))
                .build();
        coupon5.setCustomer(List.of(customer5));
        Customer customer6 = Customer.builder()
                .firstName("Sara")
                .lastName("Israeli")
                .email("sara@gmail.com")
                .password("5623")
                .coupons(List.of(coupon6))
                .build();
        coupon6.setCustomer(List.of(customer6));
        Customer customer7 = Customer.builder()
                .firstName("Dina")
                .lastName("Perets")
                .email("dina@gmail.com")
                .password("8564")
                .coupons(List.of(coupon7))
                .build();
        coupon7.setCustomer(List.of(customer7));
        Customer customer8 = Customer.builder()
                .firstName("Rachel")
                .lastName("Martsiano")
                .email("rachel@gmail.com")
                .password("8273")
                .coupons(List.of(coupon8))
                .build();
        coupon8.setCustomer(List.of(customer8));
        Customer customer9 = Customer.builder()
                .firstName("Ronen")
                .lastName("Abud")
                .email("ronen@gmail.com")
                .password("9384")
                .coupons(List.of(coupon9))
                .build();
        coupon9.setCustomer(List.of(customer9));
        Customer customer10 = Customer.builder()
                .firstName("Elyasaf")
                .lastName("Fridman")
                .email("elyasaf@gmail.com")
                .password("9938")
                .coupons(List.of(coupon10))
                .build();
        coupon10.setCustomer(List.of(customer10));
        List<Customer> customers = List.of(customer1, customer2, customer3, customer4, customer5, customer6, customer7, customer8, customer9, customer10);
        customerRepository.saveAll(customers);
        coupons = List.of(coupon1, coupon2, coupon3, coupon4, coupon5, coupon6, coupon7, coupon8, coupon9, coupon10);
        couponRepository.saveAllAndFlush(coupons);
        customerRepository.findAll().forEach(c -> {
            System.out.println(c + " " + c.getCoupons());
        });
        System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@ COUPONS @@@@@@@@@@@@@@@@@@@@@@@\n");
        couponRepository.findAll().forEach(System.out::println);
        System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@ USERS @@@@@@@@@@@@@@@@@@@@@@@\n");
        User admin = User.builder()
                .email("admin@admin.com")
                .password("admin")
                .clientType(ClientType.ADMINISTRATOR)
                .build();
        System.out.println(userRepository.save(admin));
        for (Company c : companies) {
            User user = User.builder()
                    .email(c.getEmail())
                    .password(c.getPassword())
                    .clientType(ClientType.COMPANY)
                    .build();
            System.out.println(userRepository.save(user));
        }
        for (Customer c : customers) {
            User user = User.builder()
                    .email(c.getEmail())
                    .password(c.getPassword())
                    .clientType(ClientType.CUSTOMER)
                    .build();
            System.out.println(userRepository.save(user));
        }
        System.out.println();
    }
}
