package com.bootcamp.Controllers;

import com.bootcamp.Dto.ProductDto.ViewCategoryDto;
import com.bootcamp.Entities.Product.Category;
import com.bootcamp.Entities.Product.CategoryMetadataField;
import com.bootcamp.Entities.Product.CategoryMetadataFieldValues;
import com.bootcamp.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@EnableAutoConfiguration
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/addCategoryMetadataField")
    public String addCategoryMetadataField(@Valid @RequestBody CategoryMetadataField categoryMetadataFields) {
        categoryService.addCategoryMetadataField(categoryMetadataFields);
        return "CategoryMetadataField is successfully created";
    }

    @GetMapping("/viewCategoryMetadataField")
    public List<CategoryMetadataField> viewCategoryMetadataField(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                                 @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                                 @RequestParam(name = "sortBy", defaultValue = "id") String sortBy){
        return categoryService.viewCategoryMetadataField(pageNo, pageSize, sortBy);
    }

    @PostMapping("/addNewCategory/{parent_category_id}")
    public String addNewSubCategory(@Valid @PathVariable(name = "parent_category_id") Long parent_category_id, @RequestBody Category category) {
        categoryService.addNewSubCategory(parent_category_id, category);
        return "subcategory added successfully";
    }


    @PostMapping("/addMainCategory")
    public ResponseEntity addMainCategory(@RequestBody Category category)
    {
        return categoryService.addMainCategory(category);
    }


    @GetMapping("/viewCategory/{id}")
    public List<ViewCategoryDto> viewCategory(@PathVariable Long id) {
        return categoryService.viewACategory(id);
    }


    @GetMapping("/viewAllCategories")
    public List<ViewCategoryDto> getAllCategories(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                  @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                  @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        return categoryService.viewAllCategories(pageNo, pageSize, sortBy);
    }


    @PutMapping("/updateCategory/{categoryId}")
    public String updateCategory(@Valid @RequestBody Category category, @PathVariable(name = "categoryId") Long categoryId) {
        categoryService.updateCategory(category, categoryId);
        return "Category successfully updated";
    }

    @PostMapping("/admin/addMetadataValues/{categoryId}/{metadataId}")
    public String addMetadataValues(@Valid @RequestBody CategoryMetadataFieldValues categoryMetadataFieldValues,
                                    @PathVariable(value = "categoryId") Long categoryId,
                                    @PathVariable(value = "metadataId") Long metadataId) {
        categoryService.addMetadataValues(categoryMetadataFieldValues, categoryId, metadataId);
        return "values added";
    }


//    @PutMapping("/admin/updateMetadataValues/{categoryId}/{metadataId}")
//    public void updateMetadataValues(@Valid @RequestBody CategoryMetadataFieldValues categoryMetadataFieldValues,
//                                     @PathVariable(value = "categoryId") Long categoryId,
//                                     @PathVariable(value = "metadataId") Long metadataId) {
//        categoryService.updateMetadataValues(categoryMetadataFieldValues, categoryId, metadataId);
//    }



}
