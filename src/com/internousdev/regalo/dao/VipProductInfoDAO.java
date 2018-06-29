package com.internousdev.regalo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.internousdev.regalo.dto.ProductInfoDTO;
import com.internousdev.regalo.util.DBConnector;

public class VipProductInfoDAO {
	private Map<String,Object> session;

	private List<ProductInfoDTO> productInfoList = new ArrayList<>();

	private List<ProductInfoDTO> relationList = new ArrayList<>();

	//VIPの商品情報取得
	public List<ProductInfoDTO> getProductInfo() {

		String sql = "SELECT * FROM product_info WHERE status=1 ";

		DBConnector db = new DBConnector();

		Connection con = db.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {

				ProductInfoDTO dto = new ProductInfoDTO();

				dto.setProductId(rs.getInt("product_id"));
				dto.setProductName(rs.getString("product_name"));
				dto.setProductNameKana(rs.getString("product_name_kana"));
				dto.setProductDescription(rs.getString("product_description"));
				dto.setCategoryId(rs.getInt("category_id"));
				dto.setPrice(rs.getInt("price"));
				dto.setImageFilePath((rs.getString("image_file_path"))+"/"+rs.getString("image_file_name"));
				dto.setImageFileName(rs.getString("image_file_name"));
				dto.setReleaseDate(rs.getDate("release_date"));
				dto.setReleaseCompany(rs.getString("release_company"));
				dto.setStatus(rs.getInt("status"));
				dto.setRegistDate(rs.getDate("regist_date"));
				dto.setUpdateDate(rs.getDate("update_date"));


				productInfoList.add(dto);

			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return productInfoList;
	}

	//productIdから商品情報取得
	public List<ProductInfoDTO> getVipProductInfoByProductId(int productId) {

		String sql = "SELECT * FROM product_info WHERE   product_id = ? and status=1";

		DBConnector db = new DBConnector();

		Connection con = db.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, productId);


			ResultSet rs = ps.executeQuery();

			if(rs.next()) {

				ProductInfoDTO dto = new ProductInfoDTO();

				dto.setProductId(Integer.parseInt(rs.getString("product_id")));
				dto.setProductName(rs.getString("product_name"));
				dto.setProductNameKana(rs.getString("product_name_kana"));
				dto.setProductDescription(rs.getString("product_description"));
				dto.setCategoryId(rs.getInt("category_id"));
				dto.setPrice(rs.getInt("price"));
				dto.setImageFilePath((rs.getString("image_file_path"))+"/"+rs.getString("image_file_name"));
				dto.setImageFileName(rs.getString("image_file_name"));
				dto.setReleaseDate(rs.getDate("release_date"));
				dto.setReleaseCompany(rs.getString("release_company"));
				dto.setStatus(rs.getInt("status"));
				dto.setRegistDate(rs.getDate("regist_date"));
				dto.setUpdateDate(rs.getDate("update_date"));



				productInfoList.add(dto);

			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return productInfoList;
	}

	//categoryIdから商品情報取得
		public List<ProductInfoDTO> getVipProductInfoByCategoryId(int categoryId) {

			String sql = "SELECT * FROM product_info WHERE category_id = ? and status = 1";

			DBConnector db = new DBConnector();

			Connection con = db.getConnection();

			try {
				PreparedStatement ps = con.prepareStatement(sql);

				ps.setInt(1, categoryId);


				ResultSet rs = ps.executeQuery();

				while(rs.next()) {

					ProductInfoDTO dto = new ProductInfoDTO();

					dto.setProductId(Integer.parseInt(rs.getString("product_id")));
					dto.setProductName(rs.getString("product_name"));
					dto.setProductNameKana(rs.getString("product_name_kana"));
					dto.setProductDescription(rs.getString("product_description"));
					dto.setCategoryId(rs.getInt("category_id"));
					dto.setPrice(rs.getInt("price"));
					dto.setImageFilePath((rs.getString("image_file_path"))+"/"+rs.getString("image_file_name"));
					dto.setImageFileName(rs.getString("image_file_name"));
					dto.setReleaseDate(rs.getDate("release_date"));
					dto.setReleaseCompany(rs.getString("release_company"));
					dto.setStatus(rs.getInt("status"));
					dto.setRegistDate(rs.getDate("regist_date"));
					dto.setUpdateDate(rs.getDate("update_date"));



					relationList.add(dto);

				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return relationList;
		}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
