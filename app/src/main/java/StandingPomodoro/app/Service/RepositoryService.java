package StandingPomodoro.app.Service;

import StandingPomodoro.app.Entity.PomodoroEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RepositoryService {

    private final Repositorsy repositorsy;

    @Autowired
    public RepositoryService(Repositorsy repositorsy){
        this.repositorsy = repositorsy;
    }

    public void saveEntity(PomodoroEntity entity){
        this.repositorsy.save(entity);
    }

    public List<PomodoroEntity> loadEntity(){
        return this.repositorsy.findAll();
    }

    public void deleteEntity(long id){
        this.repositorsy.deleteById(id);
    }



}
