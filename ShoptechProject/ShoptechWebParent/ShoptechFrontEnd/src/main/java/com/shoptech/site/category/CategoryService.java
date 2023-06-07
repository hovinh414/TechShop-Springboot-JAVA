package com.shoptech.site.category;

import com.shoptech.entity.Category;
import com.shoptech.exception.CategoryNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@EntityScan("com.shoptech.entity")
@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    /*public CategoryService(CategoryRepository repo) {
        super();
        this.repo = repo;
    }*/
    public List<Category> listAll(){
        List<Category> rootCategories = repo.findRootCategories();
        return listHierarchicalCategories(rootCategories);
    }
    private List<Category> listHierarchicalCategories(List<Category> rootCategories) {
        List<Category> hierarchicalCategories = new ArrayList<>();
        for (Category rootCategory : rootCategories){
            hierarchicalCategories.add(Category.copyFull(rootCategory));

            Set<Category> children = rootCategory.getChildren();
            for (Category subCategory : children) {
                String name = "" + subCategory.getName();
                hierarchicalCategories.add(Category.copyFull(subCategory, name));

                listSubHierarchicalCategories(hierarchicalCategories,subCategory,1);
            }
        }

        return hierarchicalCategories;
    }

    private void listSubHierarchicalCategories(List<Category> hierarchicalCategories,
                                               Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category subCategory : children){
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "";
            }
            name += subCategory.getName();
            hierarchicalCategories.add(Category.copyFull(subCategory, name));

            listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel);
        }
    }
    public Category getCategory(String alias) throws CategoryNotFoundException {
         Category category=repo.findByAliasEnabled(alias);
         if (category == null){
             throw new CategoryNotFoundException("Could not file category with alias" + alias);
         }
         return category;
    }

    public List<Category> getCategoryParents(Category child) {
        List<Category> listParents = new ArrayList<>();

        Category parent = child.getParent();

        while (parent != null) {
            listParents.add(0, parent);
            parent = parent.getParent();
        }

        listParents.add(child);

        return listParents;
    }
    private SortedSet<Category> sortSubCategories(Set<Category> children) {
        return sortSubCategories(children, "asc");
    }
    private SortedSet<Category> sortSubCategories(Set<Category> children, String sortDir) {
        SortedSet<Category> sortedChildren = new TreeSet<>(new Comparator<Category>() {

            @Override
            public int compare(Category cat1, Category cat2) {

                if (sortDir.equals("asc")) {
                    return cat1.getName().compareTo(cat2.getName());
                } else {
                    return cat2.getName().compareTo(cat1.getName());
                }
            }

        });

        sortedChildren.addAll(children);

        return sortedChildren;
    }
    public List<Category> listCategoriesUsedInForm() {

        List<Category> categoriesUsedInForm = new ArrayList<>();

        Iterable<Category> categoriesInDB = repo.findRootCategories(Sort.by("name").ascending());

        for (Category category : categoriesInDB) {

            // root category
            if (category.getParent() == null) {
                categoriesUsedInForm.add(Category.copyIdAndName(category));

                sortSubCategories(category.getChildren()).forEach(subCategory -> {
                    String name = "" + subCategory.getName();

                    categoriesUsedInForm.add(Category.copyIdAndNameAndAlias(subCategory.getId(), name, subCategory.getAlias()));
                    listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, 1);
                });
            }

        }

        return categoriesUsedInForm;
    }

    // recursive method
    private void listSubCategoriesUsedInForm(List<Category> categoriesUsedInForm, Category parent, int subLevel) {

        int newSubLevel = subLevel + 1;
        Set<Category> children = sortSubCategories(parent.getChildren());

        for (Category subCategory : children) {

            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "";
            }
            name += subCategory.getName();
            categoriesUsedInForm.add(Category.copyIdAndNameAndAlias(subCategory.getId(), name, subCategory.getAlias()));

            listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, newSubLevel);
        }

    }
}
