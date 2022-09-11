package com.bootcamp.service;

import com.bootcamp.dto.product.ProductDto;
import com.bootcamp.dto.product.ProductVariationDto;
import com.bootcamp.dto.product.ViewProductDto;
import com.bootcamp.entities.product.Category;
import com.bootcamp.entities.product.CategoryMetadataField;
import com.bootcamp.entities.product.Product;
import com.bootcamp.entities.product.ProductVariation;
import com.bootcamp.entities.user.Seller;
import com.bootcamp.exceptions.NotFoundException;
import com.bootcamp.exceptions.NullException;
import com.bootcamp.repository.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepo;
    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepo;
    @Autowired
    SellerRepository sellerRepo;
    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    ProductRepository productRepo;
    @Autowired
    CategoryRepository categoryRepo;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TokenRepository tokenRepo;
    @Autowired
    private EmailService emailService;

    public Product toProduct(ProductDto productDto) {
        if (productDto == null)
            return null;
        return modelMapper.map(productDto, Product.class);
    }

    public ProductDto toProductDto(Product product) {
        if (product == null)
            return null;
        return modelMapper.map(product, ProductDto.class);
    }

    public ProductVariation toProductVariation(ProductVariationDto productVariationDto) {
        if (productVariationDto == null)
            return null;
        return modelMapper.map(productVariationDto, ProductVariation.class);
    }

    public ProductVariationDto toProductVariationDto(ProductVariation variation) {
        if (variation == null)
            return null;
        return modelMapper.map(variation, ProductVariationDto.class);
    }


    public String addProduct(Product product, Long categoryId) throws NotFoundException {
        Optional<Category> category = categoryRepo.findById(categoryId);
        if (category.isPresent() && categoryRepo.checkIfLeaf(categoryId) == 0) {
            if (product.getName().equals(categoryRepo.findById(categoryId).get().getName()) || product.getName().equals(product.getBrand())) {
                throw new NullException("not found");
            }
            Product product1 = new Product();
            product1 = modelMapper.map(product, Product.class);
            String sellername = currentUserService.getUser();
            Seller seller = sellerRepo.findByEmail(sellername);
            Set<Product> productSet = new HashSet<>();
            product1.setBrand(product.getBrand());
            product1.setIsActive(false);
            product1.setIsCancellable(product.getIsCancellable());
            product1.setDescription(product.getDescription());
            product1.setName(product.getName());
            product1.setSeller(seller);
            product1.setCategory(categoryRepo.findById(categoryId).get());
            productSet.add(product1);
            productRepo.save(product1);
        } else {
            throw new NotFoundException("not found");
        }
        return product.getName() + " Successfully added";
    }

    private void sendProductCreationMail(String email, Product product) {
        String subject = "product created";
        String content = "product with details \n" +
                " name: " + product.getName() +
                "category: " + product.getCategory().getName() +
                "brand: " + product.getBrand() + " has been created";
        emailService.sendSimpleMessage(email, subject, content);
    }


    public String activateProduct(Long id) {
        String message;
        Optional<Product> product = productRepo.findById(id);
        if (!product.isPresent()) {
            message = "product not found";
            return message;
        }
        Product product1 = product.get();
        if (product1.getIsActive()) {
            message = "product is already active";
        }
        productRepo.activateTheProduct(product1.getId());
        String email = product1.getSeller().getEmail();
        sendActDeactivationMail(email, product1, true);
        return "success";
    }

    public String deactivateProductById(Long id) {
        String message;
        Optional<Product> product = productRepo.findById(id);
        if (!product.isPresent()) {
            message = "product not found";
            return message;
        }
        Product product1 = product.get();
        if (!product1.getIsActive()) {
            message = "product is already deActive";
        }
        productRepo.deactivateTheProduct(product1.getId());
        String email = product1.getSeller().getEmail();
        sendActDeactivationMail(email, product1, false);
        return "success";
    }

    private void sendActDeactivationMail(String email, Product product1, boolean b) {
        String subject;
        if (b)
            subject = "product activation mail";
        else
            subject = "product deactivation mail";
        String content = "product with details " +
                "name: " + product1.getName() +
                "brand: " + product1.getBrand() + " is activated";
        emailService.sendSimpleMessage(email, subject, content);
    }

    public ProductDto getProduct(Long productId) throws NotFoundException {
        String email = currentUserService.getUser();
        Seller seller = sellerRepo.findByEmail(email);
        Optional<Product> product = productRepo.findById(productId);
        if (product.isPresent()) {
            if (/*(product.get().getSeller().getEmail()).equals(seller1.getEmail())*/(product.get().getSeller().getEmail()).equals(seller.getEmail())) {
                ProductDto viewProductDTO = new ProductDto();
                viewProductDTO.setBrand(product.get().getBrand());
                viewProductDTO.setActive(product.get().getIsActive());
                viewProductDTO.setIsCancellable(product.get().getIsCancellable());
                viewProductDTO.setDescription(product.get().getDescription());
                viewProductDTO.setName(product.get().getName());
                Optional<Category> category = categoryRepo.findById(productRepo.getCategoryId(productId));
                viewProductDTO.setCategoryName(category.get().getName());
                List<String> fields = new ArrayList<>();
                List<String> values = new ArrayList<>();
                List<Long> longList1 = categoryMetadataFieldValuesRepo.getMetadataId(category.get().getId());
                for (Long l1 : longList1) {
                    Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepo.findById(l1);
                    fields.add(categoryMetadataField.get().getName());
                    values.add(categoryMetadataFieldValuesRepo.getFieldValuesForCompositeKey(category.get().getId(), l1));
                }
                viewProductDTO.setFieldName(fields);
                viewProductDTO.setValues(values);
                return viewProductDTO;
            } else {
                throw new NotFoundException("not found");
            }

        } else {
            throw new NotFoundException("not found");
        }
    }

    public String deleteProductById(Long id, String email) {
        String message;
        Optional<Product> productOptional = productRepo.findById(id);
        if (!productOptional.isPresent()) {
            message = "product not found";
            return message;
        }
        Product product = productOptional.get();
        if (!product.getSeller().getEmail().equalsIgnoreCase(email)) {
            message = "product does not belong to this user";
            return message;
        }
        if (product.getIsDeleted()) {
            message = "product is already deleted";
            return message;
        }
        product.setIsDeleted(true);
        message = product.getName() + " is successfully deleted.";
        return message;
    }

    public void updateProductByProductId(Long id, String email, Product product) throws NotFoundException {
        Seller seller1 = sellerRepo.findByEmail(email);
        Optional<Product> productOptional = productRepo.findById(id);
        Long[] l = {};
        if (productOptional.isPresent()) {
            Product product1 = productOptional.get();
            if ((product1.getSeller().getEmail()).equals(seller1.getEmail())) {
                if (product.getName().equals(product1.getBrand()) || product.getName().equals(product1.getCategory().getName()) || product.getName().equals(seller1.getFirstName()) || product.getName().equals(product.getBrand())) {
                    throw new NullException("no product found");
                }
                if (product.getName() != null) {
                    product1.setName(product.getName());
                }
                if (product.getBrand() != null) {
                    product1.setBrand(product.getBrand());
                }
                if (product.getIsCancellable() != null) {
                    product1.setIsCancellable(product.getIsCancellable());
                }
                if (product.getIsReturnable() != null) {
                    product1.setIsReturnable(product.getIsReturnable());
                }
                if (product.getDescription() != null) {
                    product1.setDescription(product.getDescription());
                }
                if (product.getIsActive() != null) {
                    product1.setIsActive(product.getIsActive());
                }
                productRepo.save(product1);
            } else {
                throw new NullException("no product found");
            }
        } else {
            throw new NotFoundException("not found");
        }
    }

    public List<ViewProductDto> getProductDetails(Integer pageNo, Integer pageSize, String sortBy) throws NotFoundException {
        PageRequest paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        String email = currentUserService.getUser();
        Seller seller = sellerRepo.findByEmail(email);
        List<Long> longList = productRepo.getProductIdOfSeller(seller.getId(), paging);
        List<ViewProductDto> list = new ArrayList<>();
        for (Long l : longList) {
            list.add(viewSingleProductForSeller(productRepo.findById(l).get().getId()));
        }
        return list;
    }

    public ViewProductDto viewSingleProductForSeller(Long productId) throws NotFoundException {
        String email = currentUserService.getUser();
        Seller seller1 = sellerRepo.findByEmail(email);
        String sellerEmail = currentUserService.getUser();
        Seller seller = sellerRepo.findByEmail(sellerEmail);
        Optional<Product> product = productRepo.findById(productId);
        Long[] l = {};
        if (product.isPresent()) {
            if ((product.get().getSeller().getEmail()).equals(seller1.getEmail()) /*||(product.get().getSeller().getEmail()).equals(seller.getEmail())*/) {
                ViewProductDto viewProductDTO = new ViewProductDto();
                viewProductDTO.setId(product.get().getId());
                viewProductDTO.setBrand(product.get().getBrand());
                viewProductDTO.setIsActive(product.get().getIsActive());
                viewProductDTO.setIsCancellable(product.get().getIsCancellable());
                viewProductDTO.setDescription(product.get().getDescription());
                viewProductDTO.setProductName(product.get().getName());
                Optional<Category> category = categoryRepo.findById(productRepo.getCategoryId(productId));
                viewProductDTO.setProductName(category.get().getName());

                return viewProductDTO;
            } else {
                throw new NotFoundException("category not found");
            }

        } else {
            throw new NotFoundException("product not found");
        }
    }


}
