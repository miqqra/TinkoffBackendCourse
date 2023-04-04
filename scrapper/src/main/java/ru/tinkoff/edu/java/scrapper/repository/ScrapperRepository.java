package ru.tinkoff.edu.java.scrapper.repository;

import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Repository
public class ScrapperRepository {
    HashMap<Long, List<URI>> repository = new HashMap<>();

    public boolean add(Long id){
        if (repository.containsKey(id)){
            return false;
        } else {
            repository.put(id, new ArrayList<>());
            return true;
        }
    }

    public boolean add(Long id, URI link) {
        if (repository.containsKey(id)) {
            if (repository.get(id).contains(link)){
                return false;
            }
            List<URI> prevList = repository.get(id);
            prevList.add(link);
            repository.put(id, prevList);
        } else {
            repository.put(id, new ArrayList<>(Arrays.asList(link)));
        }
        return true;
    }

    public boolean delete(Long id) {
        if (repository.containsKey(id)) {
            repository.remove(id);
            return true;
        }
        return false;
    }

    public boolean delete(Long id, URI link){
        if (repository.get(id).contains(link)){
            repository.get(id).remove(link);
            return true;
        } else {
            return false;
        }
    }

    public List<URI> findAll(Long id) {
        return repository.get(id);
    }
}
