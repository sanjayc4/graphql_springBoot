package com.sanjutech.graphql.service;

import com.sanjutech.graphql.model.Player;
import com.sanjutech.graphql.model.Team;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PlayerService {

    public List<Player> players = new ArrayList<>();

    AtomicInteger id =  new AtomicInteger(0);

    public List<Player> findAll(){
        return players;
    }


    public Optional<Player> findOne(Integer id){
        return players.stream()
                .filter(player -> player.Id().equals(id))
                .findFirst();
    }

    public Player create(String name , Team team){
        Player player = new Player(id.incrementAndGet(), name, team);
        players.add(player);
        return player;
    }

    public Player delete(Integer id){
        Player player = findOne(id).orElseThrow();
        players.remove(player);
        return player;
    }

    public Player update(Integer id, String name, Team team) throws IllegalAccessException {
       Player updatedPlayer = new Player(id, name, team);
       Optional<Player> optional = players.stream().filter(p -> p.Id().equals(id)).findFirst();
       if(optional.isPresent()) {
           Player player = optional.get();
           players.remove(player);
           players.add(updatedPlayer);
       }else{
              throw new IllegalAccessException("Player not found");
       }
        return updatedPlayer;

    }

    @PostConstruct
    private void init(){
        players.add(new Player(id.incrementAndGet(), "MS Dhoni", Team.CSK));
        players.add(new Player(id.incrementAndGet(), "Rohit Sharma", Team.MI));
        players.add(new Player(id.incrementAndGet(), "Virat Kohli", Team.RCB));
        players.add(new Player(id.incrementAndGet(), "David Warner", Team.DC));
        players.add(new Player(id.incrementAndGet(), "Andre Russell", Team.KKR));
        players.add(new Player(id.incrementAndGet(), "KL Rahul", Team.PBKS));
    }
}
