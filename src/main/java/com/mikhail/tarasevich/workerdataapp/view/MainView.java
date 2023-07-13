package com.mikhail.tarasevich.workerdataapp.view;

import com.mikhail.tarasevich.workerdataapp.view.layout.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.security.RolesAllowed;

@Route(value = "", layout = MainLayout.class)
@RolesAllowed({"ADMIN", "USER"})
public class MainView extends Div implements AfterNavigationObserver {

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            UI.getCurrent().navigate("/user/all");
        } else {
            UI.getCurrent().navigate("/user");
        }
    }

}
