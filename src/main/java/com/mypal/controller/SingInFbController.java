package com.mypal.controller;

import com.mypal.dao.UserDAO;
import com.mypal.entity.User;
import com.mypal.service.facebook.Facebook;
import com.mypal.service.facebook.FacebookService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URL;

@Controller
public class SingInFbController {

    @Autowired
    UserDAO userDAO;

    @RequestMapping(value = "/facebook/registration", method = RequestMethod.GET)
    public void registration(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {
        String code = request.getParameter("code");

        if(code == null) {
            response.sendRedirect(Facebook.getLoginRedirectURL());
        }
        else{
            String authURL = Facebook.getAuthURL(code);
            URL url = new URL(authURL);

            String result = FacebookService.readUrl(url);
            String accessToken = null;
            String[] pairs = result.split("&");
            for (String pair : pairs) {
                String[] kv = pair.split("=");
                if (kv.length != 2) {
                    throw new RuntimeException("Unexpected auth response");
                } else {
                    if (kv[0].equals("access_token")) {
                        accessToken = kv[1];
                    }
                }
            }

            JSONObject jsonObject = FacebookService.getFacebookUserData(accessToken);


            User user = new User();
            boolean isUserExist = userDAO.getByEmail(jsonObject.getString("email")) != null;

            user.setFirstName(jsonObject.getString("first_name"));
            user.setEmail(jsonObject.getString("email"));
            user.setFacebookId(jsonObject.getString("id"));
            user.setEnabled(true);
            user.setAuthorities("ROLE_USER");

            HttpSession session = request.getSession();

            if(!isUserExist) {
                userDAO.save(user);

                session.setAttribute("LoggedUser", userDAO.getByEmail(jsonObject.getString("email")));

                response.sendRedirect("http://localhost:8080/transaction/save");
            }
            else {
                session.setAttribute("LoggedUser", userDAO.getByEmail(jsonObject.getString("email")));

                response.sendRedirect("http://localhost:8080/transaction/save");
            }
        }
    }
}
