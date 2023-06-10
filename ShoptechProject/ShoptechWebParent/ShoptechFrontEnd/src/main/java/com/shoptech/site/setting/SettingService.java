package com.shoptech.site.setting;

import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.shoptech.entity.Currency;
import com.shoptech.entity.Setting;
import com.shoptech.entity.SettingCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettingService{
    @Autowired private SettingRepository settingRepository;
	@Autowired private CurrencyRepository currencyRepo;

    public List<Setting> getGeneralSettings() {
        return settingRepository.findByTwoCategories(SettingCategory.GENERAL, SettingCategory.CURRENCY);
    }

    public EmailSettingBag getEmailSettings(){
        List<Setting> settings = settingRepository.findByCategory((SettingCategory.MAIL_SERVER));
        settings.addAll(settingRepository.findByCategory(SettingCategory.MAIL_TEMPLATES));
        return new EmailSettingBag(settings);
    }

    public List<Setting> getMailServerSettings() {
        return settingRepository.findByCategory(SettingCategory.MAIL_SERVER);
    }

    public List<Setting> getMailTemplateSettings() {
        return settingRepository.findByCategory(SettingCategory.MAIL_TEMPLATES);
    }

     public List<Setting> getCurrencySettings() {
        return settingRepository.findByCategory(SettingCategory.CURRENCY);
    }

    public PaymentSettingBag getPaymentSettings() {
        List<Setting> settings = settingRepository.findByCategory(SettingCategory.PAYMENT);
        return new PaymentSettingBag(settings);
    }
    public String getCurrencyCode() {
        Setting setting = settingRepository.findByKey("CURRENCY_ID");
        Integer currencyId = Integer.parseInt(setting.getValue());
        Currency currency = currencyRepo.findById(currencyId).get();

        return currency.getCode();
    }
}
