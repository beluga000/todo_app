package com.example.todo_app.service;

import com.example.todo_app.model.ToDo;
import com.example.todo_app.model.User;
import com.example.todo_app.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoService {

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private ToDoShareService toDoShareService;

    @Autowired
    private UserService userService;

    public ToDo createToDo(ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    public List<ToDo> getAllToDos() {
        return toDoRepository.findAll();
    }

    public Optional<ToDo> getToDoById(Long id) {
        return toDoRepository.findById(id);
    }

    public ToDo updateToDo(Long id, ToDo updatedToDo) {
        if (toDoRepository.existsById(id)) {
            updatedToDo.setId(id);
            return toDoRepository.save(updatedToDo);
        }
        return null;
    }

    public void deleteToDoById(Long id) {
        toDoRepository.deleteById(id);
    }

    public List<ToDo> getToDosByOwner(User owner) {
        return toDoRepository.findByOwner(owner);
    }

    public List<ToDo> getToDosSharedWithUser(User user) {
        List<Long> sharedToDoIds = toDoShareService.getToDoIdsSharedWithUser(user.getId());
        return toDoRepository.findAllById(sharedToDoIds);
    }

    public ToDo updateToDoStatus(Long id, boolean completed) {
        Optional<ToDo> optionalToDo = toDoRepository.findById(id);
        if (optionalToDo.isEmpty()) {
            throw new IllegalArgumentException("Invalid To-Do ID");
        }
    
        ToDo toDo = optionalToDo.get();
        toDo.setCompleted(completed);
        return toDoRepository.save(toDo); 
    }
}