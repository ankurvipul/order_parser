package com.orderParser;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.Gson;
import com.orderParser.Model.Order;


@SpringBootApplication
public class OrdersParserApplication {
	static AtomicInteger id = new AtomicInteger();

	public static void main(String[] filePaths) {
		SpringApplication.run(OrdersParserApplication.class, filePaths);
		
		for (String filePath : filePaths) {
			ExecutorService es = Executors.newSingleThreadExecutor();
			es.execute(new Runnable() {	
				@Override
				public void run() {
					try {
						File file = new File(filePath);
				            
						ArrayList<String> fileLines = new ArrayList<>();
						try (BufferedReader br = new BufferedReader(new FileReader(file))) {
							String st;
							while ((st = br.readLine()) != null) {
							    fileLines.add(st);
							}
						}
			            if (filePath.endsWith(".csv")) {
			            	parseCSVFile(file);
			            }
			            else {
			            	parseJsonFile(file);
			            }
					}
					catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
			            System.out.println("Please Enter input file path");
			        }
			        catch (FileNotFoundException fileNotFoundException) {
			            System.out.println("Please enter valid path");
			        }
			        catch (IOException ioException) {
			            System.out.println("Unable to read the file");
			        }	
				}
			});
			
			es.shutdown();
		}
	}
	
	private static void parseCSVFile(File file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String st;
			while ((st = br.readLine()) != null) {
				try {
					
				}
				catch (Exception e) {
					
				}
			    String[] params = st.split(",");
			    Order order = new Order(id.incrementAndGet(), Integer.parseInt(params[0]), Double.parseDouble(params[1]), params[2], params[3]);
			    order.setResult("OK");
			    System.out.println(new Gson().toJson(order));
			}
		}
		catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            System.out.println("Please Enter input file path");
        }
        catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Please enter valid path");
        }
        catch (IOException ioException) {
            System.out.println("Unable to read the file");
        }
		
	}
	
	private static void parseJsonFile(File file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String st;
			while ((st = br.readLine()) != null) {
			    Order order = new Gson().fromJson(st, Order.class);
			    order.setId(id.incrementAndGet());
			    order.setResult("OK");
			    System.out.println(new Gson().toJson(order));
			}
		}
		catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            System.out.println("Please Enter input file path");
        }
        catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Please enter valid path");
        }
        catch (IOException ioException) {
            System.out.println("Unable to read the file");
        }
		
	}
}
