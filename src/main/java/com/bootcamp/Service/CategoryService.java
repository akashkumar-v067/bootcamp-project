package com.bootcamp.Service;

import com.bootcamp.Dto.ProductDto.ViewCategoryDto;
import com.bootcamp.Entities.Product.Category;
import com.bootcamp.Entities.Product.CategoryMetadataField;
import com.bootcamp.Entities.Product.CategoryMetadataFieldValues;
import com.bootcamp.Exceptions.NotFoundException;
import com.bootcamp.Exceptions.NullException;
import com.bootcamp.Repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepo;
    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepo;

    @Autowired
    CategoryRepository categoryRepo;

    public void addCategoryMetadataField(CategoryMetadataField categoryMetadataField) {
        categoryMetadataFieldRepo.save(categoryMetadataField);
    }

    public List<CategoryMetadataField> viewCategoryMetadataField(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging= PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        if(categoryMetadataFieldRepo.findAll(paging).isEmpty()){
            throw new NotFoundException("there is no metadata field found");
        }
        else{
            Page<CategoryMetadataField> categoryMetadataFieldPage=categoryMetadataFieldRepo.findAll(paging);
            if(categoryMetadataFieldPage.hasContent())
                return categoryMetadataFieldPage.getContent();
            else
                throw new NotFoundException("no metadata field found");
        }
    }

    public void addNewSubCategory(long parent_category_id, Category category) {
        int result = categoryRepo.checkIfLeaf(parent_category_id);
        if (result == 1) {
            Optional<Category> category1 = categoryRepo.findById(parent_category_id);
            if (category1.isPresent()) {
                category.setParentCategory(category1.get());
                categoryRepo.save(category);
            } else {
                throw new NotFoundException("No parent category found");
            }
        } else {
            throw new NullPointerException("parent category you selected is already a leaf node");
        }
    }

    public ResponseEntity addMainCategory(Category category) {
        categoryRepo.save(category);
        return ResponseEntity.ok().body("category added successfully");
    }

    public List<ViewCategoryDto> viewACategory(Long category_id) {
        Optional<Category> category = categoryRepo.findById(category_id);
        List<ViewCategoryDto> list = new ArrayList<>();
        if (category.isPresent()) {
            if (categoryRepo.checkIfLeaf(category_id) == 0) {
                List<String> fields = new ArrayList<>();
                List<String> values = new ArrayList<>();

                ViewCategoryDto viewCategoriesDTO = new ViewCategoryDto();
                viewCategoriesDTO.setName(category.get().getName());

                List<Long> longList = categoryMetadataFieldValuesRepo.getMetadataId(category_id);
                for (Long l : longList) {
                    Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepo.findById(l);
                    fields.add(categoryMetadataField.get().getName());
                    values.add(categoryMetadataFieldValuesRepo.getFieldValuesForCompositeKey(category_id, l));
                }
                viewCategoriesDTO.setFieldName(fields);
                viewCategoriesDTO.setValues(values);
                list.add(viewCategoriesDTO);
            } else {
                ViewCategoryDto viewCategoriesDTO = new ViewCategoryDto();
                viewCategoriesDTO.setName(category.get().getName());
                list.add(viewCategoriesDTO);
                List<Long> longList = categoryRepo.getIdsOfSubcategories(category_id);

                if (!longList.isEmpty()) {
                    for (Long l : longList) {
                        Optional<Category> category1 = categoryRepo.findById(l);
                        if (categoryRepo.checkIfLeaf(category1.get().getId()) == 0) {
                            List<String> fields = new ArrayList<>();
                            List<String> values = new ArrayList<>();

                            ViewCategoryDto viewCategoriesDTO1 = new ViewCategoryDto();
                            viewCategoriesDTO1.setName(category1.get().getName());

                            List<Long> longList1 = categoryMetadataFieldValuesRepo.getMetadataId(category1.get().getId());
                            for (Long l1 : longList1) {
                                Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepo.findById(l1);
                                fields.add(categoryMetadataField.get().getName());
                                values.add(categoryMetadataFieldValuesRepo.getFieldValuesForCompositeKey(category1.get().getId(), l1));
                            }
                            viewCategoriesDTO1.setFieldName(fields);
                            viewCategoriesDTO1.setValues(values);
                            list.add(viewCategoriesDTO1);
                        } else {
                            ViewCategoryDto viewCategoriesDTO1 = new ViewCategoryDto();
                            viewCategoriesDTO1.setName(category1.get().getName());
                            list.add(viewCategoriesDTO1);
                        }
                    }
                }
            }
            return list;
        } else {
            Long[] l = {};
            throw new NotFoundException("not found");
        }
    }

        public List<ViewCategoryDto> viewAllCategories(int pageNo, int pageSize, String sortBy) {
            Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
            List<Long> longList = categoryRepo.getIdsOfCategory(paging);
            List<ViewCategoryDto> list = new ArrayList<>();
            for (Long l : longList) {
                Optional<Category> category = categoryRepo.findById(l);
                if (category.isPresent()) {
                    if (categoryRepo.checkIfLeaf(category.get().getId()) == 0) {
                        List<String> fields = new ArrayList<>();
                        List<String> values = new ArrayList<>();

                        ViewCategoryDto viewCategoriesDTO1 = new ViewCategoryDto();
                        viewCategoriesDTO1.setName(category.get().getName());

                        List<Long> longList1 = categoryMetadataFieldValuesRepo.getMetadataId(category.get().getId());
                        for (Long l1 : longList1) {
                            Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepo.findById(l1);
                            fields.add(categoryMetadataField.get().getName());
                            values.add(categoryMetadataFieldValuesRepo.getFieldValuesForCompositeKey(category.get().getId(), l1));
                        }
                        viewCategoriesDTO1.setFieldName(fields);
                        viewCategoriesDTO1.setValues(values);
                        list.add(viewCategoriesDTO1);

                    } else {
                        ViewCategoryDto viewCategoriesDTO = new ViewCategoryDto();
                        viewCategoriesDTO.setName(category.get().getName());
                        list.add(viewCategoriesDTO);
                    }

                } else {
                    throw new NotFoundException("not found");
                }
            }
            return list;
        }

    public void updateCategory(Category category, Long categoryId) {
        if (categoryRepo.findById(categoryId).isPresent()) {
            Category category1 = categoryRepo.findById(categoryId).get();
            category1.setName(category.getName());
            categoryRepo.save(category1);

        } else {
            throw new NotFoundException("No category exist to update");

        }
    }

    public void addMetadataValues(CategoryMetadataFieldValues categoryMetadataFieldValues, Long categoryId, Long metadataId) {
        if (categoryRepo.findById(categoryId).isPresent() && categoryRepo.checkIfLeaf(categoryId) == 0) {
            if (categoryMetadataFieldRepo.findById(metadataId).isPresent())
            {
                String checkIfAlreadyPresent = categoryMetadataFieldValuesRepo.getFieldValuesForCompositeKey(categoryId,metadataId);
                if (checkIfAlreadyPresent==null) {

                }
                else
                {
                    throw new NullException("values are already present for this category id and metadata id");
                }
            } else {
                throw new NotFoundException("not found");
            }
        } else {
            throw new NotFoundException("not found");
        }

    }







}
