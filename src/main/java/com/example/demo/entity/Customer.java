package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="customer")
@Data
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String email;
	
	private int age;
	
	private String password;

	@ManyToMany
	@JoinTable(
			name = "customer_car",
			joinColumns = @JoinColumn(name = "customer_id"),
			inverseJoinColumns = @JoinColumn(name = "car_id")
	)
	private Set<Car> cars = new HashSet<>();

	public Customer() {
	}
	
	public Customer(Long id, String name, String email, String password, int age ) {
		this.password=password;
		this.email=email;
		this.id=id;
		this.name =name;
		this.age =age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public String getName() {
		return name;
	}
	
	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public void addCar(Car car) {
		cars.add(car);
		car.getCustomers().add(this);
	}

	public void removeCar(Car car) {
		cars.remove(car);
		car.getCustomers().remove(this);
	}
}