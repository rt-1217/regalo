package com.internousdev.regalo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.internousdev.regalo.util.DBConnector;
import com.internousdev.regalo.util.DateUtil;

public class ProductInsertCompleteDAO {
	private DateUtil dateUtil = new DateUtil();
	private String sql = "INSERT INTO product_info(product_id,product_name,product_name_kana,price,product_stock,image_file_path,image_file_name,release_company,product_description,category_id,release_date,regist_date)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

	public int buyProductInfo(int productId, String productName, String productNameKana, String productDescription,
			int categoryId, int price, String imageFilePath, String fileName, String releaseCompany) throws SQLException {

		DBConnector dbConnector = new DBConnector();
		 Connection connection = dbConnector.getConnection();

		 int count = 0;

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);


			preparedStatement.setInt(1,productId);
			preparedStatement.setString(2, productName);
			preparedStatement.setString(3, productNameKana);
			preparedStatement.setInt(4, price);
			preparedStatement.setInt(5, 50);
			preparedStatement.setString(6, "./images");
			preparedStatement.setString(7, fileName);
			preparedStatement.setString(8, releaseCompany);
			preparedStatement.setString(9,productDescription);
			preparedStatement.setInt(10,categoryId);
			preparedStatement.setString(11,dateUtil.getDate());
			preparedStatement.setString(12, dateUtil.getDate());
			count = preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			connection.close();
		}

		return count;
	}





}
