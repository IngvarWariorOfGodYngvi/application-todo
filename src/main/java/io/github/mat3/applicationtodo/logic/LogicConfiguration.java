package io.github.mat3.applicationtodo.logic;

import io.github.mat3.applicationtodo.TaskConfigurationProperties;
import io.github.mat3.applicationtodo.model.ProjectRepository;
import io.github.mat3.applicationtodo.model.TaskGroupRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:applicationContext.xml")
class LogicConfiguration {
    
    @Bean
    ProjectService service(ProjectRepository repository, TaskGroupRepository taskGroupRepository, TaskConfigurationProperties config) {
        return new ProjectService(repository, taskGroupRepository, config);
    }
}
