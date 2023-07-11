package com.mikhail.tarasevich.workerdataapp.view.user;

import com.mikhail.tarasevich.workerdataapp.view.component.UserEditor;
import com.mikhail.tarasevich.workerdataapp.model.dto.UserRequest;
import com.mikhail.tarasevich.workerdataapp.model.dto.UserResponse;
import com.mikhail.tarasevich.workerdataapp.service.UserService;
import com.mikhail.tarasevich.workerdataapp.service.mapper.UserMapper;
import com.mikhail.tarasevich.workerdataapp.view.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;

@Route(value = "/user/all", layout = MainLayout.class)
@PageTitle("All users")
@RolesAllowed("ADMIN")
public class UserList extends VerticalLayout {

    private final transient UserService userService;
    private final transient UserMapper userMapper;
    private final UserEditor userEditor;

    private final Grid<UserResponse> userGrid = new Grid<>(UserResponse.class);
    private final Button addNewButton = new Button("Add/edit user", VaadinIcon.PLUS.create());
    private final HorizontalLayout toolbar = new HorizontalLayout(addNewButton);

    @Autowired
    public UserList(UserService userService, UserMapper userMapper, UserEditor userEditor) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userEditor = userEditor;

        initGrid();
        initEditor();

        fillGrid();
    }

    /**
     * Инициализация сетки пользователей.
     * Устанавливаются видимые столбцы и слушатель выбора элемента.
     */
    private void initGrid() {

        userGrid.setColumns("id", "firstName", "lastName", "patronymic", "birthDate", "email", "phoneNumber");

        userGrid.asSingleSelect()
                .addValueChangeListener(e -> userEditor.editUser(userMapper.responseToRequest(e.getValue())));
    }

    /**
     * Инициализация редактора пользователя и панели инструментов.
     * Устанавливается слушатель клика на кнопку добавления/редактирования пользователя.
     */
    private void initEditor() {

        addNewButton.addClickListener(e -> userEditor.editUser(new UserRequest()));

        userEditor.setChangeHandler(this::handleEditorChange);

        add(toolbar, userGrid, userEditor);
    }

    /**
     * Заполнение сетки пользователями из базы данных.
     */
    private void fillGrid() {

        userGrid.setItems(userService.findAll());
    }

    /**
     * Обработчик изменений в редакторе пользователя.
     * Вызывается при сохранении, удалении или отмене редактирования пользователя.
     * Обновляет видимость редактора и перезаполняет сетку.
     */
    private void handleEditorChange() {

        userEditor.setVisible(false);
        fillGrid();
    }

}
