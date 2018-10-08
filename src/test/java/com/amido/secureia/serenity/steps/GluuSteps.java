package com.amido.secureia.serenity.steps;

import net.thucydides.core.annotations.Step;
import com.amido.secureia.serenity.page.GluuPage;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by plyomdev on 13/03/17.
 */
public class GluuSteps {

    private GluuPage gluuPage;

    @Step("Should see first name \"{0}\" on heading")
    public void shouldSeeFirstNameOnHeading(String name) {
        assertThat(gluuPage.getTextFromNotification()).contains(name);
    }

    @Step("Should see Gluu URL")
    public void shouldSeeGluuURL(String currentURL) {
        assertThat(currentURL).contains(gluuPage.getPath());
    }

    @Step("User logs out from Gluu")
    public void logout() {
        gluuPage.logout();
    }
}
