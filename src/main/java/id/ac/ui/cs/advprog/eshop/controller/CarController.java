package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/car")
class CarController {
    @Autowired
    private CarService carService;

    @GetMapping("/createCar")
    public String createCarPage (Model model) {
        Car car = new Car();
        model.addAttribute("car", car);
        return "CreateCar";
    }

    @PostMapping("/createCar")
    public String createCarPost (@Valid @ModelAttribute Car car, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "CreateCar";
        }
        carService.create(car);
        return "redirect:/car/listCar";
    }

    @GetMapping("/listCar")
    public String carListPage (Model model) {
        List<Car> allCars = carService.findAll();
        model.addAttribute("cars", allCars);
        return "CarList";
    }

    @GetMapping("/editCar/{carId}")
    public String editCarPage (@PathVariable String carId, Model model) {
        Car car = carService.findById(carId);
        model.addAttribute("car", car);
        return "EditCar";
    }

    @PostMapping("/editCar")
    public String editCarPost (@Valid @ModelAttribute Car car, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "EditCar";
        }
        carService.update(car.getId(), car);
        return "redirect:/car/listCar";
    }

    @PostMapping("/deleteCar")
    public String deleteCar (@RequestParam("carId") String carId) {
        carService.deleteCarById(carId);
        return "redirect:/car/listCar";
    }
}