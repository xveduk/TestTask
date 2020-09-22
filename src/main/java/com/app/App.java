package com.app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException {
        List<Service> resultList = new ArrayList<>();
        try (FileInputStream reader = new FileInputStream(args[0]); Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                Service newService = new Service(scanner.nextLine());
                if (!newService.isLongerThanHour()) {
                    boolean goodService = true;
                    for (int i = 0; i < resultList.size();) {
                        if (resultList.get(i).isBetterThan(newService)) {
                            goodService = false;
                            break;
                        } else if (newService.isBetterThan(resultList.get(i))) {
                            resultList.remove(i);
                        } else {
                            i++;
                        }
                    }
                    if (goodService) resultList.add(newService);
                }
            }
        }

        try (FileOutputStream writer = new FileOutputStream("output.txt"); PrintStream printer = new PrintStream(writer)) {
            resultList.stream().filter(Service::isPosh)
                    .sorted(Comparator.comparingInt(Service::getDeparture))
                    .forEach(printer::println);
            resultList.stream().filter(service -> !service.isPosh())
                    .sorted(Comparator.comparingInt(Service::getDeparture))
                    .forEach(service -> printer.printf("\n%s", service));
        }
    }
}
