package io.github.mat3.applicationtodo.logic;

import io.github.mat3.applicationtodo.TaskConfigurationProperties;
import io.github.mat3.applicationtodo.model.ProjectRepository;
import io.github.mat3.applicationtodo.model.TaskGroup;
import io.github.mat3.applicationtodo.model.TaskGroupRepository;
import io.github.mat3.applicationtodo.model.projection.GroupReadModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    @Test
    @DisplayName("should throw IllegalStateException when configured to allow just 1 group the other undone group exists")
    void createGroup_noMultipleGroupsConfig_And_undoneGroupExists_throwIllegalStateException() {
        //given
        TaskGroupRepository mockGroupRepository = groupRepositoryReturning(true);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(true);
        //and
        TaskConfigurationProperties mockConfig = ConfigurationReturning(false);
        //system under test
        var toTest = new ProjectService(null, mockGroupRepository, mockConfig);

        //when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
        // then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("one undone group");
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when configuration ok and no project for given ID")
    void createGroup_configurationOk_And_noProjects_throwIllegalArgumentException() {
        //given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        TaskConfigurationProperties mockConfig = ConfigurationReturning(true);
        //system under test
        var toTest = new ProjectService(mockRepository, null, mockConfig);

        //when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ID not found");
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when configured to allow just 1 group and no groups and no project for given ID")
    void createGroup_noMultipleGroupsConfig_And_noUndoneGroupExists_noProjects_throwIllegalArgumentException() {
        //given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        TaskGroupRepository mockGroupRepository = groupRepositoryReturning(false);
        //and
        TaskConfigurationProperties mockConfig = ConfigurationReturning(true);
        //system under test
        var toTest = new ProjectService(mockRepository, null, mockConfig);

        //when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ID not found");
    }

    @Test
    @DisplayName("should create a new group from project")
    void createGroup_configurationOk_existingProject_createsAndSavesGroup() {
        //given
        var today = LocalDate.now().atStartOfDay();
        //and
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        InMemoryGroupRepository inMemoryGroupRepo = inMemoryGroupRepository();
        int countBeforeCall = inMemoryGroupRepo.count();
        //and
        TaskConfigurationProperties mockConfig = ConfigurationReturning(true);
        //system under test
        var toTest = new ProjectService(mockRepository, inMemoryGroupRepo, mockConfig);

        //when
        GroupReadModel result = toTest.createGroup(today, 1);

        //then
        assertThat(countBeforeCall + 1)
                .isNotEqualTo(inMemoryGroupRepo.count());

    }


    private TaskGroupRepository groupRepositoryReturning(boolean result) {
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(result);
        return mockGroupRepository;
    }

    private TaskConfigurationProperties ConfigurationReturning(boolean result) {
        var mockTemplate = mock(TaskConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(result);
        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        return mockConfig;
    }

    private InMemoryGroupRepository inMemoryGroupRepository() {
        return new InMemoryGroupRepository();
    }

    private static class InMemoryGroupRepository implements TaskGroupRepository {
        private int index = 0;
        private Map<Integer, TaskGroup> map;

        public int count() {
            return map.values().size();
        }

        @Override
        public List<TaskGroup> findAll() {
            return new ArrayList<>(map.values());
        }

        @Override
        public Optional<TaskGroup> findById(Integer id) {
            return Optional.ofNullable(map.get(id));
        }

        @Override
        public TaskGroup save(TaskGroup entity) {
            if (entity.getId() == 0) {
                try {
                    TaskGroup.class.getDeclaredField("id").set(entity, ++index);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            map.put(++index, entity);
            return entity;
        }

        @Override
        public boolean existsByDoneIsFalseAndProject_Id(Integer projectId) {
            return map.values().stream()
                    .filter(group -> !group.isDone())
                    .anyMatch(group -> group.getProject() != null && group.getProject().getId() == projectId);
        }
    }
}