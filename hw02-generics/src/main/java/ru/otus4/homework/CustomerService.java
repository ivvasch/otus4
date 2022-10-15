package ru.otus4.homework;

import java.util.Map;
import java.util.TreeMap;

public class CustomerService {
    TreeMap<Customer, String> sortedTreeMap = new TreeMap<>();
    //todo: 3. надо реализовать методы этого класса

    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    public void add(Customer customer, String data) {
        sortedTreeMap.put(customer, data);
    }

    public Map.Entry<Customer, String> getSmallest() {
        return copyCustomerEntry(sortedTreeMap.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> higherEntry = sortedTreeMap.higherEntry(customer);
        if (higherEntry != null) {
            return copyCustomerEntry(higherEntry);
        }
        return null;
    }

    private Map.Entry<Customer, String> copyCustomerEntry(Map.Entry<Customer, String> customerStringEntry) {
        return new Map.Entry<>() {
            @Override
            public Customer getKey() {
                return new Customer(customerStringEntry.getKey());
            }

            @Override
            public String getValue() {
                return customerStringEntry.getValue();
            }

            @Override
            public String setValue(String value) {
                return null;
            }
        };
    }
}
