package ca.lambton.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.lambton.project.utils.ProjectUtils;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api")
public class PageHitsController {


 @GetMapping("/page-hits")
 public Long getPageHits() {
     return ProjectUtils.pagehits;
 }
}

