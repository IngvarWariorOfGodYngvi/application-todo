package io.github.mat3.applicationtodo.controller;

import io.github.mat3.applicationtodo.model.projection.ProjectWriteModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @GetMapping
    String showProjects(Model model) {
        ProjectWriteModel projectToEdit = new ProjectWriteModel();
        projectToEdit.setDescription("test");
        model.addAttribute("project", projectToEdit);
        return "projects";
    }

}
