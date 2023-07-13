package com.mikhail.tarasevich.workerdataapp.view.user;

import com.mikhail.tarasevich.workerdataapp.model.dto.UserResponse;
import com.mikhail.tarasevich.workerdataapp.service.UserService;
import com.mikhail.tarasevich.workerdataapp.view.layout.MainLayout;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@Route(value = "/user", layout = MainLayout.class)
@PageTitle("User info")
@RolesAllowed("USER")
public class UserInfo extends VerticalLayout {

    private final transient UserService userService;

    private final transient AuthenticationContext authContext;

    private final Label nameLabel;
    private final Label emailLabel;
    private final Label phoneNumberLabel;
    private final Label birthDateLabel;

    @Autowired
    public UserInfo(UserService userService, AuthenticationContext authContext) {
        this.userService = userService;
        this.authContext = authContext;

        nameLabel = new Label();
        emailLabel = new Label();
        phoneNumberLabel = new Label();
        birthDateLabel = new Label();

        initLayout();

        fillUserData();
    }

    private void initLayout() {

        setAlignItems(Alignment.CENTER);

        H2 pageTitle = new H2("User information");
        add(pageTitle);

        FormLayout userFormLayout = new FormLayout();
        userFormLayout.setWidth("400px");
        userFormLayout.addFormItem(nameLabel, "Name:");
        userFormLayout.addFormItem(emailLabel, "Email:");
        userFormLayout.addFormItem(phoneNumberLabel, "Phone number:");
        userFormLayout.addFormItem(birthDateLabel, "Birth date:");
        add(userFormLayout);
    }

    private void fillUserData() {

        Optional<UserDetails> principal = authContext.getAuthenticatedUser(UserDetails.class);

        if (principal.isPresent()) {
            UserResponse user = userService.findByEmail(principal.get().getUsername());
            nameLabel.setText(user.getFirstName() + " " + user.getLastName() + " " + user.getPatronymic());
            emailLabel.setText(user.getEmail());
            phoneNumberLabel.setText(user.getPhoneNumber());
            birthDateLabel.setText(user.getBirthDate().toString());
        }
    }

}
