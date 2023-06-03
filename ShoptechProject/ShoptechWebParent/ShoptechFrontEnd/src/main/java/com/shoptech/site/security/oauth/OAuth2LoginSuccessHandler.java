package com.shoptech.site.security.oauth;

import com.shoptech.entity.AuthenticationType;
import com.shoptech.entity.Customer;
import com.shoptech.site.customer.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired private CustomerService customerService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        CustomerOAuth2User oauth2User =(CustomerOAuth2User) authentication.getPrincipal();

        String name = oauth2User.getName();
        String email = oauth2User.getEmail();
        String countryCode = request.getLocale().getCountry();
        String clientName = oauth2User.getClientName();

        AuthenticationType authenticationType = getAuthenticationType(clientName);
        Customer customer = customerService.getCustomerByEmail(email);
        if (customer == null){
            customerService.addNewCustomerUponOAuthLogin(name, email, countryCode, authenticationType);
        }else {
            oauth2User.setFullName(customer.getFullName());
            customerService.updateAuthenticationType(customer, authenticationType);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
    private AuthenticationType getAuthenticationType(String clientName){
        if (clientName.equals("Google")){
            return AuthenticationType.GOOGLE;
        }else if(clientName.equals("Facebook")){
            return AuthenticationType.FACEBOOK;
        }else {
            return AuthenticationType.DATABASE;
        }
    }
}
