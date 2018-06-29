package com.internousdev.regalo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.internousdev.regalo.util.DBConnector;
import com.internousdev.regalo.util.DateUtil;

public class ProductUpdateCompleteDAO {
	private DateUtil dateUtil = new DateUtil();
	private String sql = "UPDATE product_info SET product_name = ?, product_name_kana = ?, price = ?, product_stock = ?, image_file_path = ?, image_file_name = ?, release_company = ?, product_description = ?, category_id = ?, release_date = ?, regist_date = ? WHERE product_id = ?";

	public int buyProductInfo(int productId, String productName, String productNameKana, String productDescription,
			int categoryId, int price, String imageFilePath, String fileName, String releaseCompany) throws SQLException {

		DBConnector dbConnector = new DBConnector();
		 Connection connection = dbConnector.getConnection();
		 int count = 0;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);



			preparedStatement.setString(1, productName);
			preparedStatement.setString(2, productNameKana);
			preparedStatement.setInt(3, price);
			preparedStatement.setInt(4, 50);
			preparedStatement.setString(5, "./images");
			preparedStatement.setString(6, fileName);
			preparedStatement.setString(7, releaseCompany);
			preparedStatement.setString(8,productDescription);
			preparedStatement.setInt(9,categoryId);
			preparedStatement.setString(10,dateUtil.getDate());
			preparedStatement.setString(11, dateUtil.getDate());
			preparedStatement.setInt(12,productId);
			count = preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}






}
