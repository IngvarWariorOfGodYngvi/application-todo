package io.github.mat3.applicationtodo.logic;

import io.github.mat3.applicationtodo.model.TaskGroup;
import io.github.mat3.applicationtodo.model.TaskGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProjectServiceTest {

    @Test
    @DisplayName("should throw IllegalStateException when configured to allow just 1 group the other undone group exists")
    void createGroup_noMultipleGroupsConfig_And_undoneGroupExists_throwIllegalStateException() {
        //given
        var mockGroupRepository = 
        }
        //when
        //then
    }
}