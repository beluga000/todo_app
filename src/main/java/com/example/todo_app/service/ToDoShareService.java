package com.example.todo_app.service;

import com.example.todo_app.model.ToDo;
import com.example.todo_app.model.ToDoShare;
import com.example.todo_app.model.User;
import com.example.todo_app.repository.ToDoRepository;
import com.example.todo_app.repository.ToDoShareRepository;
import com.example.todo_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToDoShareService {

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ToDoShareRepository toDoShareRepository;

    public ToDoShare shareToDoWithUser(Long toDoId, Long ownerId, Long sharedWithUserId) {
        // 1. toDoId로 To-Do 엔티티 조회
        Optional<ToDo> optionalToDo = toDoRepository.findById(toDoId);
        if (optionalToDo.isEmpty()) {
            throw new IllegalArgumentException("Invalid To-Do ID");
        }

        ToDo toDo = optionalToDo.get();

        // 2. ownerId가 실제로 To-Do 작성자인지 확인
        if (!toDo.getOwner().getId().equals(ownerId)) {
            throw new IllegalArgumentException("Owner ID does not match the To-Do's owner");
        }

        // 3. 공유받을 사용자 조회
        Optional<User> optionalUser = userRepository.findById(sharedWithUserId);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Invalid User ID");
        }

        User sharedUser = optionalUser.get();

        // 4. To-Do의 sharedUsers에 공유받을 사용자 추가
        toDo.getSharedUsers().add(sharedUser);
        toDoRepository.save(toDo);

        // 5. ToDoShare 엔티티 생성 및 저장
        ToDoShare toDoShare = new ToDoShare(toDoId, ownerId, sharedWithUserId);
        return toDoShareRepository.save(toDoShare);
    }

    
    public List<ToDoShare> getSharedToDosForUser(Long userId) {
        return toDoShareRepository.findBySharedWithUserId(userId);
    }

    public List<Long> getToDoIdsSharedWithUser(Long userId) {
        List<ToDoShare> shares = getSharedToDosForUser(userId);
        return shares.stream()
                     .map(ToDoShare::getToDoId)
                     .collect(Collectors.toList());
    }
}
