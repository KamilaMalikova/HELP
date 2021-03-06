package com.example.helptest.service;

import com.example.helptest.config.LocalPagination;
import com.example.helptest.exception.DuplicateException;
import com.example.helptest.exception.IllegalArgumentException;
import com.example.helptest.exception.NotFoundException;
import com.example.helptest.model.Category;
import com.example.helptest.model.Product;
import com.example.helptest.model.StockItemBalance;
import com.example.helptest.model.Units;
import com.example.helptest.repository.CategoryDao;
import com.example.helptest.repository.ProductDao;
import com.example.helptest.repository.UnitsDao;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UnitsDao unitsDao;

    public Product addProduct(Map<String, String> productParams) {
        try {
            System.out.println(productParams);
            Product product = new Product();
            product.setId(productDao.count() + 1);
            try {
                product.setCategory(categoryDao.findCategoryById(Integer.parseInt(productParams.get("category"))).get());
                product.setUnit(unitsDao.findUnitsById(Integer.parseInt(productParams.get("unit"))).get());
                if (productParams.containsKey("cost")) {
                    product.setCost(Double.parseDouble(productParams.get("cost")));
                } else {
                    product.setCost(0.0);
                }
            } catch (Exception ex2) {
                throw new NotFoundException(ex2.getLocalizedMessage());
            }

            product.setCreatedAt(LocalDateTime.now());
            //product.setInStockQty(Double.parseDouble(productParams.get("inStockQty")));
            product.setActiveStatus(true);
            product.setRestaurant(productParams.get("restaurant").equals("1"));
            product.setProductName(productParams.get("productName"));


            return productDao.save(product);
        } catch (Exception ex) {
            throw new DuplicateException(ex.getLocalizedMessage());
        }
    }

    public Page<Product> getProducts(int page) {
        try {
            return productDao.findAll(LocalPagination.getDefaultPageable(page));
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getLocalizedMessage());
        }

    }

    public Product getProduct(long productCode) {
        try {
            return productDao.findProductById(productCode).get();
        } catch (Exception ex) {
            throw new NotFoundException(ex.getLocalizedMessage());
        }
    }

    public Optional<Product> getOptionalProduct(long productCode) {
        try {
            return productDao.findProductById(productCode);
        } catch (Exception ex) {
            throw new NotFoundException(ex.getLocalizedMessage());
        }
    }

    public void setDeleted(String productCode) {

    }

    public Product updateProduct(long productCode, Map<String, String> parameters) {
        try {
            Product product = productDao.findProductById(productCode).get();
            if (parameters.containsKey("active")) product.setActiveStatus(parameters.get("active").equals("1"));
            if (parameters.containsKey("productName")) product.setProductName(parameters.get("productName"));
            if (parameters.containsKey("category")) {
                try {
                    Category category = categoryDao.findCategoryById(Integer.parseInt(parameters.get("category"))).get();
                    product.setCategory(category);
                } catch (Exception ex) {
                    throw new IllegalArgumentException(ex.getLocalizedMessage());
                }
            }
            if (parameters.containsKey("unit")) {
                try {
                    Units unit = unitsDao.findUnitsById(Integer.parseInt(parameters.get("unit"))).get();
                    product.setUnit(unit);
                } catch (Exception ex) {
                    throw new IllegalArgumentException(ex.getLocalizedMessage());
                }
            }
//            if (parameters.containsKey("quantity"))
//                product.setInStockQty(Double.parseDouble(parameters.get("quantity")));
            if (parameters.containsKey("restaurant")) product.setRestaurant(parameters.get("restaurant").equals("1"));
            if (parameters.containsKey("cost")) product.setCost(Double.parseDouble(parameters.get("cost")));
            return productDao.save(product);
        } catch (Exception ex) {
            throw new NotFoundException(ex.getLocalizedMessage());
        }

    }


    public Page<Product> getProducts(int page, String productName, Integer category, boolean activeStatus, boolean restaurant) {
        try {
            if (productName == null) {
                Category category1 = categoryDao.findCategoryById(category).get();
                return productDao.findAllByCategoryAndActiveStatusAndRestaurant(LocalPagination.getDefaultPageable(page), category1, activeStatus, restaurant);
            } else if (category == null) {
                try {
                    return productDao.findAllByProductNameContainingAndActiveStatusAndRestaurant(LocalPagination.getDefaultPageable(page), productName, activeStatus, restaurant);
                } catch (Exception ex) {
                    throw new IllegalArgumentException(ex.getLocalizedMessage());
                }
            } else {
                Category category1 = categoryDao.findCategoryById(category).get();
                return productDao.findAllByProductNameContainingAndCategoryAndActiveStatusAndRestaurant(LocalPagination.getDefaultPageable(page), productName, category1, activeStatus, restaurant);
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getLocalizedMessage());
        }

    }

    public Page<Product> getProducts(int page, boolean active) {
        try {
            return productDao.findAllByActiveStatus(LocalPagination.getDefaultPageable(page), active);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getLocalizedMessage());
        }

    }

    public List<Product> getMenu() {
        try {
            List<Product> productList = productDao.findAllByRestaurantIsTrue();
            return productList;
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    private Map<String, List<Product>> filterCategory(List<Product> products) {
        Map<String, List<Product>> filter = new HashMap<>();
        for (Product product : products) {
            if (filter.containsKey(product.getCategory().getCategory())) {
                List<Product> temp = filter.get(product.getCategory().getCategory());
                temp.add(product);
                filter.put(product.getCategory().getCategory(), temp);
            } else {
                List<Product> temp = new ArrayList<>();
                temp.add(product);
                filter.put(product.getCategory().getCategory(), temp);
            }
        }
        return filter;
    }

    public List<Product> getMenu(String productName, Integer category) {
        try {
            Category category1 = null;

            if (category != 0) {
                category1 = categoryDao.findCategoryById(category).get();
            }
            if (category1 == null) {
                return productDao.findAllByProductNameContainingAndRestaurantIsTrueAndActiveStatusIsTrue(productName);
            } else if (productName == null) return productDao.findAllByCategoryAndRestaurantIsTrueAndActiveStatusIsTrue(category1);
            else {
                return productDao.findAllByProductNameContainingAndCategoryAndRestaurantIsTrue(productName, category1);
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public Product addProduct(StockItemBalance stockItemBalance) {
        Product product = new Product();
        var ref = new Object() {
            long id = 0;
        };
        productDao.findFirstByOrderByIdDesc().ifPresent(product1 -> ref.id = product1.getId());
        product.setId(ref.id + 1);
        product.setProductName(stockItemBalance.getName());
        product.setRestaurant(stockItemBalance.isRestaurant());
        product.setInStockQty(0.0);
        product.setUnit(stockItemBalance.getUnit());
        product.setActiveStatus(true);
        product.setCreatedAt(LocalDateTime.now());
        product.setCategory(stockItemBalance.getCategory());
        return productDao.save(product);
    }

    public Product updateProduct(long productId, Product product) {
        try {
            //Product product1 = productDao.findProductById(productId).get();
            product.setId(productId);
            return productDao.save(product);
        } catch (Exception ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    public List<Product> getProducts(boolean active, boolean restaurant) {
        try {
            return productDao.findAllByActiveStatusAndRestaurant(active, restaurant);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getLocalizedMessage());
        }
    }

    public List<Product> getProductsList(String productName, Integer category, boolean active, boolean restaurant) {
        try {
            if (productName == null) {
                Category category1 = categoryDao.findCategoryById(category).get();
                return productDao.findAllByCategoryAndActiveStatusAndRestaurant(category1, active, restaurant);
            } else if (category == null) {
                try {
                    return productDao.findAllByProductNameContainingAndActiveStatusAndRestaurant(productName, active, restaurant);
                } catch (Exception ex) {
                    throw new IllegalArgumentException(ex.getLocalizedMessage());
                }
            } else {
                Category category1 = categoryDao.findCategoryById(category).get();
                return productDao.findAllByProductNameContainingAndCategoryAndActiveStatusAndRestaurant(productName, category1, active, restaurant);
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getLocalizedMessage());
        }

    }

    public Map<String, List<Product>> getMenuOrder() {
        return this.filterCategory(this.getMenu());
    }
}
