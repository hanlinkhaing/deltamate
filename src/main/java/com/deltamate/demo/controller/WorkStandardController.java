package com.deltamate.demo.controller;

import com.deltamate.demo.model.Ex_Fty;
import com.deltamate.demo.model.StyleTable;
import com.deltamate.demo.model.WorkStandard;
import com.deltamate.demo.reports.WorkStandardPDF;
import com.deltamate.demo.reports.WorkStandardXlsx;
import com.deltamate.demo.service.StyleTableService;
import com.deltamate.demo.service.WorkStandardService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/workStandard")
public class WorkStandardController {

    private ServletContext servletContext;

    private static final String AJAX_HEADER_NAME = "X-Requested-With";
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";

    private WorkStandardService workStandardService;

    private StyleTableService styleTableService;

    private long styleID;

    public WorkStandardController(WorkStandardService workStandardService, ServletContext servletContext, StyleTableService styleTableService) {
        this.workStandardService = workStandardService;
        this.servletContext = servletContext;
        this.styleTableService = styleTableService;
    }

    @GetMapping
    public String viewAllWorkStandardPage(Model model) {
        Ex_Fty ex_fty = new Ex_Fty();
        StyleTable styleTable = new StyleTable();
        styleTable.getEx_ftys().add(ex_fty);
        model.addAttribute("styleTable", styleTable);
        model.addAttribute("styleTables", styleTableService.findAll());
        return "view/viewAllStyle";
    }

    @PostMapping
    public String searchByStyleTable(@ModelAttribute("styleTable") StyleTable styleTable, Model model) {
        List<StyleTable> styleTables = styleTableService.findStyleTableByNoExFtyDate(styleTable);
        model.addAttribute("styleTable", styleTable);
        model.addAttribute("styleTables",styleTables);
        return "view/viewAllStyle";
    }

    @GetMapping("/addWorkStandard")
    public String addWorkStandardPage(Model model) {
        Ex_Fty ex_fty = new Ex_Fty();
        StyleTable styleTable = new StyleTable();
        styleTable.getEx_ftys().add(ex_fty);
        WorkStandard workStandard = new WorkStandard();
        workStandard.setStyleTable(styleTable);
        model.addAttribute("workStandard", workStandard);
        return "view/addWorkStandard";
    }

    @PostMapping("/addWorkStandard")
    public String insertWorkStandardAction(@ModelAttribute("workStandard") WorkStandard workStandard, BindingResult bindingResult,
                                           RedirectAttributes redirectAttributes) throws Exception {
        workStandardService.insertWorkStandard(workStandard);
        redirectAttributes.addFlashAttribute("styleTables", styleTableService.findAll());
        return "redirect:/workStandard";
    }

    @GetMapping("/pdf/{style}")
    public ResponseEntity<InputStreamResource> workStandardPDFView(@PathVariable("style")long style) throws IOException {
        WorkStandard workStandard = workStandardService.searchWorkStandardByStyle(style);
        ByteArrayInputStream bis= WorkStandardPDF.workStandardPDFView(workStandard.getPath());
        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-Disposition"
                ,"inline;filename="+workStandard.getOrderNo()+".pdf");

        return  ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/excel/{style}")
    public ResponseEntity<InputStreamResource> workStandardExcelView(@PathVariable("style")long style) throws IOException {
        WorkStandard workStandard = workStandardService.searchWorkStandardByStyle(style);
        ByteArrayInputStream bis= WorkStandardXlsx.workStandardXlsxView(workStandard.getPath());
        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-Disposition"
                ,"inline;filename="+workStandard.getOrderNo()+".xlsx");

        return  ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(bis));
    }

    @PostMapping(path = "/addWorkStandard/addExFty")
    public String addStyle(WorkStandard workStandard, HttpServletRequest httpServletRequest, Model model) {
        Ex_Fty ex_fty = new Ex_Fty();
        StyleTable styleTable = workStandard.getStyleTable();
        styleTable.getEx_ftys().add(ex_fty);
        model.addAttribute("workStandard", workStandard);
        if(AJAX_HEADER_VALUE.equals(httpServletRequest.getHeader(AJAX_HEADER_NAME)))
            return "view/addWorkStandard::#exftyList";
        else return "view/addWorkStandard";
    }

    @PostMapping(path = "/editWorkStandard/{styleID}/addExFtyInEdit")
    public String addExftyInEdit(WorkStandard workStandard, HttpServletRequest httpServletRequest, Model model) {
        Ex_Fty ex_fty = new Ex_Fty();
        workStandard.getStyleTable().getEx_ftys().add(ex_fty);
        model.addAttribute("workStandard", workStandard);
        if(AJAX_HEADER_VALUE.equals(httpServletRequest.getHeader(AJAX_HEADER_NAME)))
            return "view/editWorkStandard::#exftyListInEdit";
        else return "view/editWorkStandard";
    }

    @GetMapping("/editWorkStandard/{styleID}")
    public String editWorkStandard(@PathVariable("styleID")long styleID,Model model) {
        WorkStandard workStandard = workStandardService.searchWorkStandardByStyle(styleID);
        model.addAttribute("workStandard", workStandard);
        this.styleID = styleID;
        return "view/editWorkStandard";
    }

    @PostMapping("/editWorkStandard")
    public String editWorkStandard(@ModelAttribute("workStandard") WorkStandard workStandard, BindingResult bindingResult,
                                           RedirectAttributes redirectAttributes) throws Exception {
        workStandardService.removeEx_FtiesByStyle(this.styleID);
        workStandardService.updateWorkStandard(this.styleID, workStandard);
        redirectAttributes.addFlashAttribute("styleTables", styleTableService.findAll());
        return "redirect:/workStandard";
    }
}
