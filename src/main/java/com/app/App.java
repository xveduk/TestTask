package com.app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeMap;

public class App {
    public static void main(String[] args) throws IOException {
        TreeMap<Integer, Service> serviceTree = new TreeMap<>();
        try (FileInputStream reader = new FileInputStream(args[0]); Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                Service newService = new Service(scanner.nextLine());
                if (!newService.isLongerThanHour()) {
                    Service oldService = serviceTree.get(newService.getDeparture());
                    if (oldService == null
                            || newService.getArrival() < oldService.getArrival()
                            || (newService.getArrival() == oldService.getArrival() && newService.isPosh()))
                        serviceTree.put(newService.getDeparture(), newService);
                }
            }
        }

        LinkedList<Service> sortedServices = new LinkedList<>(serviceTree.values());
        LinkedList<Service> poshResult = new LinkedList<>(), grottyResult = new LinkedList<>();

        while (!sortedServices.isEmpty()) {
            Service goodService = sortedServices.pollLast();
            if (goodService.isPosh()) poshResult.addFirst(goodService);
            else grottyResult.addFirst(goodService);
            while (!sortedServices.isEmpty() && sortedServices.peekLast().getArrival() >= goodService.getArrival())
                sortedServices.removeLast();
        }

        try (FileOutputStream writer = new FileOutputStream("output.txt"); PrintStream printer = new PrintStream(writer)) {
            poshResult.forEach(printer::println);
            grottyResult.forEach(service -> printer.printf("\n%s", service));
        }
    }
}
