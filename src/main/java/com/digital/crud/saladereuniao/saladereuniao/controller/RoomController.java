package com.digital.crud.saladereuniao.saladereuniao.controller;

import com.digital.crud.saladereuniao.saladereuniao.exception.ResourceNotFoundException;
import com.digital.crud.saladereuniao.saladereuniao.model.Room;
import com.digital.crud.saladereuniao.saladereuniao.repository.RoomRespository;

import org.hibernate.ResourceClosedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class RoomController {

    @Autowired
private RoomRespository roomRespository;


    @GetMapping ("/rooms")
    public List<Room> getAllRooms(){
        return roomRespository.findAll();
    }

    @GetMapping ("/rooms/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable(value = "id") long roomId)
            throws ResourceNotFoundException {

        Room room = roomRespository.findById(roomId).
                orElseThrow( () -> new ResourceNotFoundException("Room not found: " + roomId) );

        return ResponseEntity.ok().body(room);

    }

    @PostMapping("/rooms")
    public Room createRoom (@Valid @RequestBody Room room){
        return roomRespository.save(room);
    }


    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> atualizar(@Valid @PathVariable(value = "id") Long roomId, @RequestBody Room roomDetails)
    throws ResourceNotFoundException{

        Room room = roomRespository.findById(roomId)
                .orElseThrow(  () -> new ResourceNotFoundException("Room not found for this id::" + roomId));

        room.setName(roomDetails.getName());
        room.setDate(roomDetails.getDate());
        room.setStartHour(roomDetails.getStartHour());
        room.setEndHour(roomDetails.getEndHour());

        final Room updateRoom = roomRespository.save(room);
        return ResponseEntity.ok(updateRoom);

    }
    @DeleteMapping("/rooms/{id}")
    public Map<String,Boolean> deleteRoom(@PathVariable(value = "id") Long roomId) throws
            ResourceNotFoundException{
        Room room = roomRespository.findById(roomId)
                .orElseThrow( () -> new  ResourceNotFoundException("Room not found for this id:"+ roomId) );

        roomRespository.delete(room);
        Map<String,Boolean> response = new HashMap<>();
        response.put("delete",Boolean.TRUE);

        return response;
    }


}
