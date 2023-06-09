package com.shoptech.admin.customer;

import com.shoptech.admin.AbstractExporter;
import com.shoptech.entity.Customer;
import com.shoptech.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.util.List;

public class CustomersCsvExporter extends AbstractExporter {
    public void export(List<Customer> listCustomers, HttpServletResponse response) throws IOException {

        super.setResponseHeader(response, "text/csv", ".csv","customers");

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"ID", "Email", "Full Name", "Phone Number", "Address 1 ", "Address 2","City","State","PostalCode" ,"Enabled"};
        String[] fieldMapping = {"id", "email", "fullName", "phoneNumber", "addressLine1","addressLine2","city","state","postalCode", "enabled"};

        csvWriter.writeHeader(csvHeader);

        for (Customer customer : listCustomers) {
            csvWriter.write(customer, fieldMapping);
        }

        csvWriter.close();

    }
}
