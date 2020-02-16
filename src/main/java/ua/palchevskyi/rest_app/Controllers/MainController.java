package ua.palchevskyi.rest_app.Controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.palchevskyi.rest_app.Entity.Message;
import ua.palchevskyi.rest_app.Entity.Views;
import ua.palchevskyi.rest_app.Repos.DataRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MainController {
    private DataRepository dataRepository;

    @Autowired
    public MainController(DataRepository dataRepository){
        this.dataRepository = dataRepository;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> getAllMessages(){
        return dataRepository.findAll();
    }

    @GetMapping("{id")
    @JsonView(Views.FullMessage.class)
    public Message getMessageById(@PathVariable("id") Message message){
        return message;
    }

    @PostMapping
    public Message createMessage(@RequestBody Message message){
        message.setCreationDate(LocalDateTime.now());
        return dataRepository.save(message);
    }

    @PutMapping("{id}")
    public Message editMessage(@PathVariable("id") Message messageFromDb, @RequestBody Message message){
        BeanUtils.copyProperties(message,messageFromDb,"id");
        return dataRepository.save(messageFromDb);
    }

    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable("id") Message message){
        dataRepository.delete(message);
    }
}
