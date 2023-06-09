package com.shoptech.admin.product;

import com.shoptech.admin.AbstractExporter;
import com.shoptech.entity.Customer;
import com.shoptech.entity.Product;
import jakarta.servlet.http.HttpServletResponse;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.util.List;

public class ProductCsvExporter extends AbstractExporter {
    public void export(List<Product> listProducts, HttpServletResponse response) throws IOException {

        super.setResponseHeader(response, "text/csv", ".csv","products");

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"ID", "Name", "Category", "Cost", "Price","DiscountPercent","Length","Width","Height","Weight" ,"Enabled"};
        String[] fieldMapping = {"id", "name", "category", "cost","price","discountPercent","length","width","height","weight", "enabled"};

        csvWriter.writeHeader(csvHeader);

        for (Product product : listProducts) {
            csvWriter.write(product, fieldMapping);
        }

        csvWriter.close();

    }
}
