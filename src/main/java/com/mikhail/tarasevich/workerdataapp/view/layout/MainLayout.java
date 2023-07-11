package com.mikhail.tarasevich.workerdataapp.view.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.security.AuthenticationContext;

public class MainLayout extends AppLayout {

    private final transient AuthenticationContext authContext;

    public MainLayout(AuthenticationContext authContext) {
        this.authContext = authContext;
        Button logout = new Button("Logout", click -> this.authContext.logout());
        HorizontalLayout header = new HorizontalLayout();
        header.add(logout);
        addToNavbar(header);
    }

}
