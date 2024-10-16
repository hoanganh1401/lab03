package vn.iotstar.services;

import java.util.List;

import vn.iotstar.entity.Category;

public interface ICategoryService {
	int count();

	List<Category> findAll(int page, int pageSize);

	Category findByCategoryname(String name) throws Exception;

	List<Category> findAll();

	Category findById(int cateid);

	void delete(int cateid) throws Exception;

	void update(Category category);

	void insert(Category category);

	List<Category> searchByName(String catname);
}
