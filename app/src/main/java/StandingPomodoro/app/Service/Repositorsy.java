package StandingPomodoro.app.Service;

import StandingPomodoro.app.Entity.PomodoroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Repositorsy extends JpaRepository<PomodoroEntity, Long> {

}
