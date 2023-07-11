package com.mikhail.tarasevich.workerdataapp.view.auth;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("/login")  // Маппинг маршрута для этого представления
@PageTitle("Login")  // Заголовок страницы
@AnonymousAllowed  // Разрешение доступа к представлению без аутентификации
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm login = new LoginForm();  // Форма входа

    public LoginView() {
        addClassName("login-view");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        login.setAction("login");  // Установка действия формы входа

        add(new H1("Worker data application"), login);  // Добавление заголовка и формы входа в компоновку
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // Проверка наличия параметра "error" в URL-адресе
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);  // Установка флага ошибки в форме входа
        }
    }

}
