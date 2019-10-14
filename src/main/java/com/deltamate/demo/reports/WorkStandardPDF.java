package com.deltamate.demo.reports;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class WorkStandardPDF {

    public static ByteArrayInputStream workStandardPDFView(String path) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        path = path.replace(".xlsx", ".pdf");

        File f = new File(path);
        FileInputStream file = new FileInputStream(f);
        int data;
        while ((data = file.read()) >= 0) {
            out.write(data);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
