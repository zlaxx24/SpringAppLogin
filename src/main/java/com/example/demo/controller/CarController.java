package com.example.demo.controller;

import com.example.demo.dao.CarRepository;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.entity.Car;
import com.example.demo.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CarController {

    @Autowired
    private CarRepository carRepo;
    @Autowired
    private CustomerRepository cRepo;

    @GetMapping({"/listAll"})
    public ModelAndView showCars() {
        ModelAndView mav = new ModelAndView("list-cars");
        List<Car> list = carRepo.findAll();
        mav.addObject("car", list);
        return mav;
    }

        @GetMapping("/addCarForm")
        public ModelAndView addCarForm() {
            ModelAndView mav = new ModelAndView("add-car-form");
            Car newCar = new Car();
            mav.addObject("car", newCar);
            return mav;
        }

        @PostMapping("/saveCar")
        public String saveCar(@ModelAttribute Car car) {
            carRepo.save(car);
            return "redirect:/listAll";
        }


        @GetMapping("/showCarUpdateForm")
        public ModelAndView showUpdateForm(@RequestParam Long carId) {
            ModelAndView mav = new ModelAndView("add-car-form");
            Car car = carRepo.findById(carId).get();
            mav.addObject("car", car);
            return mav;
        }

        @GetMapping("/deleteCar")
        public String deleteCar(@RequestParam Long carId) {
            carRepo.deleteById(carId);
            return "redirect:/listAll";
        }
    @PostMapping("/addCustomerToCar")
    public String addCustomerToCar(@RequestParam Long carId, @RequestParam Long customerId) {
        Car car = carRepo.findById(carId).orElse(null);
        Customer customer = cRepo.findById(customerId).orElse(null);

        if (car != null && customer != null) {
            car.addCustomer(customer);
            carRepo.save(car);
        }

        return "redirect:/listAll";
    }

    }
