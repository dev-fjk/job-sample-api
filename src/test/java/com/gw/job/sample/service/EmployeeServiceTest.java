package com.gw.job.sample.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.gw.job.sample.converter.EmployeeResponseConverter;
import com.gw.job.sample.entity.doma.Employee;
import com.gw.job.sample.entity.response.EmployeeResponse;
import com.gw.job.sample.exception.ResourceNotFoundException;
import com.gw.job.sample.factory.EmployeeFactory;
import com.gw.job.sample.repository.EmployeeRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Junit使用時のサンプル
 * Junitはこちらのリポジトリに参考ソースをおいてます: https://github.com/pmcmember/order-api
 */
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService target;

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeResponseConverter employeeResponseConverter;
    @Mock
    private EmployeeFactory employeeFactory;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("findOne_社員が見つかった場合レスポンスが返される")
    public void 正常系_findOne() {
        // given
        var employeeId = 1L;
        var employee = new Employee();
        var expectedResponse = EmployeeResponse.builder().build();

        // mocks
        doReturn(Optional.of(employee)).when(employeeRepository).findOne(employeeId);
        doReturn(expectedResponse).when(employeeResponseConverter).convert(employee);

        // when
        var actual = target.findOne(employeeId);

        // then
        assertEquals(expectedResponse, actual);
        verify(employeeRepository, times(1)).findOne(employeeId);
        verify(employeeResponseConverter, times(1)).convert(employee);
    }

    @Test
    @DisplayName("findOne_社員が見つからない場合例外が返される")
    public void 異常系_findOne() {
        // given
        var employeeId = 1L;

        // mocks
        doReturn(Optional.empty()).when(employeeRepository).findOne(employeeId);

        // when - then
        assertThrows(ResourceNotFoundException.class, () -> target.findOne(employeeId));
        verify(employeeRepository, times(1)).findOne(employeeId);
    }
}