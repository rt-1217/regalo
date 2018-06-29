package com.internousdev.regalo.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.regalo.dao.AddressInfoDAO;
import com.internousdev.regalo.dao.BuyProductCompleteDAO;
import com.internousdev.regalo.dao.CartDAO;
import com.internousdev.regalo.dao.LoginDAO;
import com.internousdev.regalo.dao.ProductInfoDAO;
import com.internousdev.regalo.dao.UserCreateConfirmDAO;
import com.internousdev.regalo.dto.AddressInfoDTO;
import com.internousdev.regalo.dto.CartDTO;
import com.internousdev.regalo.dto.LoginDTO;
import com.internousdev.regalo.dto.ProductInfoDTO;
import com.internousdev.regalo.util.InputChecker;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction  extends ActionSupport implements SessionAware{

    private static final String SETTLEMENT = "settlement";
    private static final String MASTER = "master";
    private static final String ERRORPAGE ="errorPage";
    /*private categoryId;*/
    private String userId;
    private String password;
    private LoginDTO loginDTO;
    private String errorMessage;
    private String errorMessageId;
    private String errorMessagePassword;
    //エラーメッセージをリストに入れる。
    private List<String> idErrorMessageList = new ArrayList<>();
    private List<String> passwordErrorMessageList = new ArrayList<>();
    private String existUserIdErrorMessage;
    //ログイン情報を保持しているかの判定！
    private boolean saveLogin;

    private Map<String,Object> session;

    private List<AddressInfoDTO> addressInfoListDTO = new ArrayList<AddressInfoDTO>();
    private ArrayList<ProductInfoDTO> productInfoDTOList ;
    private List<CartDTO> CartDtoList = new ArrayList<CartDTO>();


    public String execute(){

        String result = ERROR;

        if((!session.containsKey("loginFlg"))){
        	return ERRORPAGE;
        }

        session.remove("passwordErrorMessageListSize");

    	//ID保存
		if(saveLogin) {
			session.put("saveId", userId);

		} else {
			session.remove("saveId");

		}

        //セッション切れに対応
        if(!(session.containsKey("loginFlg"))){
            return result;
        }

        LoginDAO loginDAO = new LoginDAO();
        CartDAO cartDAO = new CartDAO();
        InputChecker i = new InputChecker();

        boolean flg = false;

        System.out.println("LoginAction-----");
        //どっちも入力
        if((!(userId.equals(""))) && !(password.equals(""))) {
            System.out.println("入力されてます");

            idErrorMessageList = i.check("ID", userId, 1, 8, true, false, false, false, true, false, false);
            passwordErrorMessageList = i.check("パスワード", password, 1, 16, true, false, false, false, true, false, false);



            try {
                flg = loginDAO.existsUserId(userId,password);
            } catch (SQLException e) {
                e.printStackTrace();
            }



            if(flg){

                loginDTO = loginDAO.userInfo(userId);//入力したID

                session.put("loginDTO", loginDTO);//DTOの情報の格納
                session.put("loginFlg", true);//ログインしているかの確認（二択）
                session.put("userId", userId);//userIdにuserIdの保持
                session.put("saveLogin", userId);

                /*//カート情報を格納
                CartDtoList = cartDAO.getCartDtoList(userId);
                System.out.println("カートの件数:"+CartDtoList.size());
                session.put("CartDtoList", CartDtoList);

                session.put("CartDtoListSize", CartDtoList.size());*/


                //会員ランクを確認、sessionに格納
                BuyProductCompleteDAO buyProductCompleteDAO = new BuyProductCompleteDAO();
                int rank = 0;
                try {
                    rank = buyProductCompleteDAO.getRank(userId);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                session.put("rank", rank);

                if(rank == 0){
                    System.out.println("一般会員です");
                }
                if(rank == 1){
                    System.out.println("VIP会員です");
                }
                /*if(rank == 2){
                    System.out.println("ゴールド会員です");
                }*/


                //宛先情報取得 sessionに格納
                AddressInfoDAO addressInfoDAO = new AddressInfoDAO();
                try {
                    addressInfoListDTO = addressInfoDAO.getAddressInfo(session.get("userId").toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                session.put("addressInfoListDTO",addressInfoListDTO);
                System.out.println("addressInfoListDTO.size():"+addressInfoListDTO.size());

                //カートのユーザー情報を書き換える

                if(cartDAO.isExistsCart(session.get("tempUserId").toString())){

                    System.out.println("LoginAction.カート存在します");

                    cartDAO.linkToLoginId(session.get("tempUserId").toString(),userId);

                }

                /*みんながログイン成功しているかどうかをコンソールで確認するための記入。
                */
                System.out.println("loginFlg:"+session.get("loginFlg").toString());
                System.out.println("ログイン成功！");

                //管理者チェック
                try {
                    if(loginDAO.masterCheck(userId)){
                        //カートの情報を入れている。
                        ProductInfoDAO dao = new ProductInfoDAO();
                        productInfoDTOList = (ArrayList<ProductInfoDTO>) dao.getProductInfo();
                        //session.put("productInfoDTOList", productInfoDTOList);
                        session.put("masterId", "admin");
                        System.out.println("要素数 = " + productInfoDTOList.size());
                        System.out.println("管理者ログインしました");
                        result = MASTER;
                        return result;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                result = SUCCESS;


            } else {
                errorMessage = "入力されたパスワードが異なります。";

                passwordErrorMessageList.add(errorMessage);
                session.put("passwordErrorMessageListSize", passwordErrorMessageList.size());

            }

            if(session.containsKey("settlement")){


                if((boolean)session.get("settlement")){

                    result = SETTLEMENT;

                    session.put("settlement",false);

                }
            } else {

            }


        }
        //IDが空白の場合
        if(userId.equals("")){
            errorMessageId = "IDを入力してください。";

            System.out.println(errorMessageId);

            idErrorMessageList.add(errorMessageId);

        }
        //パスが空白の場合
        if(password.equals("")){
            errorMessagePassword = "パスワードを入力してください。";

            System.out.println(errorMessagePassword);

            passwordErrorMessageList.add(errorMessagePassword);
        }

        //ここは、ちゃんとこのファイルが実行しているかの確認のためのコード
        System.out.println("-----LoginAction");

        UserCreateConfirmDAO DAO = new UserCreateConfirmDAO();

        try {
			if (!(DAO.existUserId(userId))) {
				existUserIdErrorMessage = "存在しないIDです";
				idErrorMessageList.add(existUserIdErrorMessage);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}





        return result;


    }


    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public Map<String, Object> getSession() {
        return session;
    }


    public void setSession(Map<String, Object> session) {
        this.session = session;
    }


    public LoginDTO getLoginDTO() {
        return loginDTO;
    }


    public void setLoginDTO(LoginDTO loginDTO) {
        this.loginDTO = loginDTO;
    }


    public String getErrorMessage() {
        return errorMessage;
    }


    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }



    public String getErrorMessageId() {
        return errorMessageId;
    }


    public void setErrorMessageId(String errorMessageId) {
        this.errorMessageId = errorMessageId;
    }


    public String getErrorMessagePassword() {
        return errorMessagePassword;
    }


    public void setErrorMessagePassword(String errorMessagePassword) {
        this.errorMessagePassword = errorMessagePassword;
    }


    public boolean isSaveLogin() {
        return saveLogin;
    }


    public void setSaveLogin(boolean saveLogin) {
        this.saveLogin = saveLogin;
    }


    public ArrayList<ProductInfoDTO> getProductInfoDTOList() {
        return productInfoDTOList;
    }


    public void setProductInfoDTOList(ArrayList<ProductInfoDTO> productInfoDTOList) {
        this.productInfoDTOList = productInfoDTOList;
    }


    public List<CartDTO> getCartDtoList() {
        return CartDtoList;
    }


    public void setCartDtoList(List<CartDTO> cartDtoList) {
        CartDtoList = cartDtoList;
    }


	public List<String> getIdErrorMessageList() {
		return idErrorMessageList;
	}


	public void setIdErrorMessageList(List<String> idErrorMessageList) {
		this.idErrorMessageList = idErrorMessageList;
	}


	public List<String> getPasswordErrorMessageList() {
		return passwordErrorMessageList;
	}


	public void setPasswordErrorMessageList(List<String> passwordErrorMessageList) {
		this.passwordErrorMessageList = passwordErrorMessageList;
	}


	public String getExistUserIdErrorMessage() {
		return existUserIdErrorMessage;
	}


	public void setExistUserIdErrorMessage(String existUserIdErrorMessage) {
		this.existUserIdErrorMessage = existUserIdErrorMessage;
	}





}