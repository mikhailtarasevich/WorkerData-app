package com.mikhail.tarasevich.workerdataapp.view.component;

import com.mikhail.tarasevich.workerdataapp.model.dto.UserRequest;
import com.mikhail.tarasevich.workerdataapp.service.UserService;
import com.mikhail.tarasevich.workerdataapp.service.mapper.UserMapper;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

@SpringComponent
@UIScope
@Validated
public class UserEditor extends VerticalLayout implements KeyNotifier {

    private final transient UserService userService;

    private final transient UserMapper userMapper;

    private final transient FormLayout formLayout = new FormLayout();

    private final TextField firstName = new TextField("First name");

    private final TextField lastName = new TextField("Last name");

    private final TextField patronymic = new TextField("Patronymic");

    private final DatePicker birthDate = new DatePicker("Birth date");

    private final EmailField email = new EmailField("Email");

    private final PasswordField password = new PasswordField("Password");

    private final PasswordField confirmedPassword = new PasswordField("Confirmed password");

    private final TextField phoneNumber = new TextField("Phone number");

    private final Button saveButton = new Button("Save");

    private final Button cancelButton = new Button("Cancel");

    private final Button deleteButton = new Button("Delete");

    private final HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton, deleteButton);

    private final Binder<UserRequest> binder = new BeanValidationBinder<>(UserRequest.class);

    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public UserEditor(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;

        add(formLayout, buttonLayout);

        formLayout.add(lastName, firstName, patronymic, birthDate, email, phoneNumber, password, confirmedPassword);

        binder.bindInstanceFields(this);

        setSpacing(true);

        saveButton.getElement().getThemeList().add("primary");
        deleteButton.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        saveButton.addClickListener(e -> save());
        deleteButton.addClickListener(e -> delete());
        cancelButton.addClickListener(e -> cancel());

        setVisible(false);
    }

    /**
     * Сохраняет данные пользователя, вызывая соответствующий метод UserService.
     * Показывает уведомление об успешном сохранении.
     * Показывает уведомление об ошибке, если возникли ошибки валидации.
     */
    private void save() {

        if (binder.validate().isOk()) {
            UserRequest user = binder.getBean();
            if (user.getId() == null) {
                userService.register(user);
            } else {
                userService.edit(user);
            }
            changeHandler.onChange();
            showNotification("User saved successfully.");
            clearForm();
        } else {
            showNotification("Please fill in all required fields.");
        }
    }

    /**
     * Удаляет пользователя, вызывая метод UserService deleteById().
     * Показывает уведомление об успешном удалении.
     */
    private void delete() {
        if (binder.getBean().getId() != null) {
            userService.deleteById(binder.getBean().getId());
            changeHandler.onChange();
            showNotification("User deleted successfully.");
            clearForm();
        }
    }

    /**
     * Отменяет редактирование пользователя.
     */
    private void cancel() {
        clearForm();
    }

    /**
     * Очищает форму и сбрасывает объект биндера.
     */
    private void clearForm() {
        binder.setBean(new UserRequest());
        setVisible(false);
    }

    /**
     * Устанавливает переданные данные пользователя для редактирования.
     * Если пользователь равен null, скрывает редактор.
     *
     * @param user данные пользователя для редактирования
     */
    public void editUser(UserRequest user) {
        if (user == null) {
            setVisible(false);
            return;
        }

        binder.setBean(user);
        setVisible(true);
        firstName.focus();
    }

    /**
     * Показывает уведомление с переданным сообщением.
     *
     * @param message сообщение для отображения в уведомлении
     */
    private void showNotification(String message) {
        Notification.show(message, 3000, Notification.Position.BOTTOM_CENTER);
    }

}
