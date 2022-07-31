package com.gw.job.sample.dao

import com.gw.job.sample.entity.selector.EmployeeListSelector
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito
import org.seasar.doma.boot.autoconfigure.DomaAutoConfiguration
import org.seasar.doma.jdbc.SelectOptions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import spock.lang.Specification

import javax.sql.DataSource
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement
import java.time.LocalDate

import static org.mockito.ArgumentMatchers.eq

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DomaAutoConfiguration.class)
@ComponentScan
class EmployeeDaoSpec extends Specification {

    @Autowired
    EmployeeDao target

    @Autowired
    DataSource dataSource

    @Captor
    ArgumentCaptor<String> captor

    def preparedStatement = Mock(PreparedStatement)
    def capturedValues = [:] as Map<Integer, String>
    def replaceRegex = "[ \\t\\n]+"

    def setup() {
        capturedValues.clear()
        def connection = dataSource.getConnection()
        preparedStatement.setString(*_) >> { index, val ->
            capturedValues.put(index, val)
        }
        preparedStatement.setInt(*_) >> { index, val ->
            capturedValues.put(index, val as String)
        }
        preparedStatement.setLong(*_) >> { index, val ->
            capturedValues.put(index, val as String)
        }
        preparedStatement.setObject(*_) >> { index, val ->
            capturedValues.put(index, val as String)
        }
        preparedStatement.setDate(*_) >> { index, val ->
            capturedValues.put(index, val as String)
        }
        preparedStatement.setNull(*_) >> { index, val ->
            capturedValues.put(index, null)
        }

        def resultSet = Mock(ResultSet)
        preparedStatement.executeQuery() >> resultSet

        def generatedResultSet = Mock(ResultSet)
        generatedResultSet.next() >> true
        preparedStatement.getGeneratedKeys() >> generatedResultSet

        Mockito.doReturn(preparedStatement).when(connection).prepareStatement(captor.capture())
        Mockito.doReturn(preparedStatement).when(connection).prepareStatement(
                captor.capture(), eq(Statement.RETURN_GENERATED_KEYS)
        )
    }

    def cleanup() {
        Mockito.reset(dataSource.getConnection())
    }

    def "findByEmployeeId"() {
        given:
        def employeeId = 1L
        def expectedSql = """
                          | ${CapturedSqlTemplate.EMPLOYEE_ALL_COLUMN}
                          | FROM
                          |    employee
                          | WHERE
                          |    employee_id = ?
                          """
                .stripMargin()
                .replaceAll(replaceRegex, " ")
                .strip()

        when:
        target.findByEmployeeId(employeeId)

        then:
        verifyPreparedStatement() // SQLの呼び出し回数
        capturedSql() == expectedSql // 発行されたSQL
        capturedParams() == ["1"] // SQLに動的にバインドされる値(?の部分)
    }

    def "findWithOptions オプション未設定の場合IDのみ指定される"() {
        given:
        def employeeId = 1L
        def expectedSql = """
                          | ${CapturedSqlTemplate.EMPLOYEE_ALL_COLUMN}
                          | FROM
                          |    employee
                          | WHERE
                          |    employee_id = ?
                          """
                .stripMargin()
                .replaceAll(replaceRegex, " ")
                .strip()

        when:
        target.findWithOptions(employeeId, SelectOptions.get())

        then:
        verifyPreparedStatement()
        capturedSql() == expectedSql
        capturedParams() == ["1"]
    }

    def "findWithOptions 悲観ロック用のoption設定"() {
        given:
        def employeeId = 1L
        def options = SelectOptions.get().forUpdate()
        def expectedSql = """
                          | ${CapturedSqlTemplate.EMPLOYEE_ALL_COLUMN}
                          | FROM
                          |    employee
                          | WHERE
                          |    employee_id = ?
                          | for update
                          """
                .stripMargin()
                .replaceAll(replaceRegex, " ")
                .strip()

        when:
        target.findWithOptions(employeeId, options)

        then:
        verifyPreparedStatement()
        capturedSql() == expectedSql
        capturedParams() == ["1"]
    }

    def "search 検索条件未設定"() {
        given:
        def selector = new EmployeeListSelector(
                employeeIds: [],
                departmentCodes: [],
                entryDateFrom: null,
                entryDateTo: null
        )
        def options = SelectOptions.get()
        def expectedSql = """
                          | ${CapturedSqlTemplate.EMPLOYEE_ALL_COLUMN}
                          | FROM
                          |    employee
                          | ORDER BY
                          |    employee_id
                          """
                .stripMargin()
                .replaceAll(replaceRegex, " ")
                .strip()

        when:
        target.search(selector, options)

        then:
        verifyPreparedStatement()
        capturedSql() == expectedSql
        capturedParams() == []
    }

    def "search 検索条件全設定"() {
        given:
        def selector = new EmployeeListSelector(
                employeeIds: [1L, 2L],
                departmentCodes: [3L, 4L],
                entryDateFrom: LocalDate.of(2022, 7, 30),
                entryDateTo: LocalDate.of(2022, 8, 1)

        )
        def options = SelectOptions.get().offset(0).limit(100)
        def expectedSql = """
                          | ${CapturedSqlTemplate.EMPLOYEE_ALL_COLUMN}
                          | FROM
                          |    employee
                          | WHERE
                          |    employee_id IN (?, ?)
                          | AND
                          |    department_code IN (?, ?)
                          | AND
                          |    entry_date >= ?
                          | AND
                          |    entry_date <= ?
                          | ORDER BY
                          |    employee_id
                          | limit 0, 100
                          """
                .stripMargin()
                .replaceAll(replaceRegex, " ")
                .strip()

        when:
        target.search(selector, options)

        then:
        verifyPreparedStatement()
        capturedSql() == expectedSql
        capturedParams() == [
                "1", "2",
                "3", "4",
                "2022-07-30",
                "2022-08-01"
        ]
    }


    private def verifyPreparedStatement(int times = 1) {
        def connection = dataSource.getConnection()
        Mockito.verify(connection, Mockito.times(times)).prepareStatement(captor.capture()) == null
    }

    private def verifyPreparedStatementAutoGeneratedKey(int times = 1) {
        def connection = dataSource.getConnection()
        Mockito.verify(connection, Mockito.times(times)).prepareStatement(
                captor.capture(), eq(Statement.RETURN_GENERATED_KEYS)
        ) == null
    }

    private def capturedSql() {
        captor.getValue().replaceAll("[ \t\n]+", " ")
    }

    private def capturedParams() {
        capturedValues.values() as List<String>
    }
}
