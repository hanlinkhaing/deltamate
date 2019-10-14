package com.deltamate.demo.reports;

import java.io.*;

public class WorkStandardXlsx {

    public static ByteArrayInputStream workStandardXlsxView(String path) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        File f = new File(path);
        FileInputStream file = new FileInputStream(f);
        int data;
        while ((data = file.read()) >= 0) {
            out.write(data);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

}
