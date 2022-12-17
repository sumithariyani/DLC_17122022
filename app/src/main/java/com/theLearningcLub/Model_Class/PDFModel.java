package com.theLearningcLub.Model_Class;

/**
 * Created by HP on 5/10/2019.
 */

public class PDFModel {
    private String pdf_id,pdf_name,pdf_path,base_urls,date;

    public PDFModel(String pdf_id, String pdf_name, String pdf_path, String base_urls, String date) {
        this.pdf_id = pdf_id;
        this.pdf_name = pdf_name;
        this.pdf_path = pdf_path;
        this.base_urls = base_urls;
        this.date = date;
    }

    public String getPdf_id() {
        return pdf_id;
    }

    public void setPdf_id(String pdf_id) {
        this.pdf_id = pdf_id;
    }

    public String getPdf_name() {
        return pdf_name;
    }

    public void setPdf_name(String pdf_name) {
        this.pdf_name = pdf_name;
    }

    public String getPdf_path() {
        return pdf_path;
    }

    public void setPdf_path(String pdf_path) {
        this.pdf_path = pdf_path;
    }

    public String getBase_urls() {
        return base_urls;
    }

    public void setBase_urls(String base_urls) {
        this.base_urls = base_urls;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
