package ua.palchevskyi.rest_app.Controllers;

import org.springframework.web.bind.annotation.*;
import ua.palchevskyi.rest_app.Exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/messages")
public class MainController {

    private List<Map<String,String>> messages = new ArrayList<Map<String,String>>(){{
        add(new HashMap<String,String>(){{
            put("id","1");
            put("message", "Hello, this is test message");
        }});
        add(new HashMap<String,String>(){{
            put("id","2");
            put("message", "Hello, this is the second message");
        }});
    }};

    private int size = messages.size()+1;

    public Map<String,String> getMessage(String id){
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @GetMapping
    public List<Map<String,String>> getAllMessages(){
        return messages;
    }

    @GetMapping("{id")
    public Map<String,String> getMessageById(@PathVariable String id){
        return getMessage(id);
    }

    @PostMapping
    public Map<String,String> createMessage(@RequestBody Map<String, String> message){
        message.put("id", String.valueOf(size++));
        messages.add(message);
        return message;
    }

    @PutMapping("{id}")
    public Map<String,String> editMessage(@PathVariable String id, @RequestBody Map<String,String> message){
        Map<String, String> messageFromDb = getMessage(id);
        messageFromDb.putAll(message);
        messageFromDb.put("id", id);

        return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable String id){
        messages.remove(getMessage(id));
    }
}
