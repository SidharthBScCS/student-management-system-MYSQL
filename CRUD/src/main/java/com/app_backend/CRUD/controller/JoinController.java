package com.app_backend.CRUD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app_backend.CRUD.service.JoinQueryService;
import com.app_backend.CRUD.service.JoinQueryService.JoinQueryResult;

@Controller
public class JoinController {

    private final JoinQueryService joinQueryService;

    public JoinController(JoinQueryService joinQueryService) {
        this.joinQueryService = joinQueryService;
    }

    @GetMapping("/joins")
    public String joinPage(
            @RequestParam(defaultValue = "student") String table1,
            @RequestParam(defaultValue = "course") String table2,
            @RequestParam(defaultValue = "INNER JOIN") String joinType,
            Model model) {
        JoinQueryResult joinResult = joinQueryService.buildJoinResult(table1, table2, joinType);

        model.addAttribute("tables", joinQueryService.getTableNames());
        model.addAttribute("joinTypes", joinQueryService.getJoinTypes());
        model.addAttribute("joinResult", joinResult);

        return "joins";
    }
}
